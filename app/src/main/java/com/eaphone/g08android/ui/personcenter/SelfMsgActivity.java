package com.eaphone.g08android.ui.personcenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alley.van.VanGogh;
import com.alley.van.activity.VanCropActivity;
import com.alley.van.model.VanConfig;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.IdcardInfoExtractor;
import com.eaphone.g08android.bean.SelfMsgEnity;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.UpdateMsgPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.IdcardValidatorUtils;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.widget.CircleImageView;
import com.eaphone.g08android.widget.CustomHeaderAndFooterPicker;
import com.eaphone.g08android.widget.picture.ItemDialogFragment;
import com.eaphone.g08android.widget.picture.PictrueConfig;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.kevin.crop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MultipartBody;


public class SelfMsgActivity extends CoreBaseActivity<UpdateMsgPresenter> implements PassportContracts.UpdateMsgView, View.OnClickListener {

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.iv_icon)
    CircleImageView iv_icon;

    @BindView(R.id.iv_why)
    ImageView iv_why;

    @BindView(R.id.relative_sex)
    RelativeLayout relative_sex;

    @BindView(R.id.relative_date)
    RelativeLayout relative_date;

    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.edt_name)
    EditText edt_name;

    @BindView(R.id.edt_card)
    EditText edt_card;


    private String source;
    private String userId;
    private String rightText;

    private boolean isClick = false;
    private boolean isHadCard = false;
    private TitleBuilder builder;

    private void setEnabled(boolean isEnable) {
        edt_name.setEnabled(isEnable);
        edt_card.setEnabled(isEnable);
        iv_icon.setEnabled(isEnable);
        relative_sex.setEnabled(isEnable);
        relative_date.setEnabled(isEnable);
    }

    @Override
    public int getLayoutId() {
        return R.layout.self_msg_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        source = getIntent().getExtras().getString(Const.SOURCE);
        userId = getIntent().getExtras().getString(Const.USERID);
        mPresenter.downLoadUserInfo(userId);
        if (source.equals(Const.SOURCE_FAMILY_MEMBER)) {
            rightText = "编辑";
            setEnabled(false);
            iv_why.setVisibility(View.GONE);
        } else {
            rightText = "保存";
        }

        /**
         *  有个人信息和家庭成员信息
         *
         *  个人信息：填写之后按照身份证计算的获取年龄和性别,为必填项
         *
         *  家庭成员：家庭成员未填写身份证则填写，年龄性别为必填
         *
         */
        builder = initBackTitle("个人信息").setRightText(rightText).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelfMsgEnity enity = new SelfMsgEnity();
                if (TextUtils.isEmpty(edt_name.getText())) {
                    showToast("请输入姓名");
                    return;
                }
                enity.setUserId(userId);
                enity.setName(edt_name.getText().toString());

                if (source.equals(Const.SOURCE_FAMILY_MEMBER)) {
                    if (!isClick) {
                        rightText = "保存";
                        isClick = true;
                        setEnabled(true);
                    } else {
                        rightText = "编辑";
                        isClick = false;

                        if (TextUtils.isEmpty(tv_sex.getText())) {
                            showToast("请选择性别");
                            return;
                        }
                        enity.setSex(tv_sex.getText().toString());
                        if (TextUtils.isEmpty(tv_date.getText())) {
                            showToast("请选择年龄");
                            return;
                        }
                        enity.setAge(Integer.parseInt(tv_date.getText().toString()));
                        if (!TextUtils.isEmpty(edt_card.getText())) {
                            if (!IdcardValidatorUtils.isValidate18Idcard((edt_card.getText().toString()))) {
                                showToast("请输入正确身份证");
                                return;
                            }
                            enity.setIdentity(edt_card.getText().toString());
                            if (!TextUtils.isEmpty(tv_sex.getText()))
                                enity.setSex(tv_sex.getText().toString());
                            if (!TextUtils.isEmpty(tv_date.getText()))
                                enity.setAge(Integer.parseInt(tv_date.getText().toString()));
                        }

                        mPresenter.loadmsg(enity);
                        setEnabled(false);
                    }
                    builder.setRightText(rightText).build();
                } else {
                    if (TextUtils.isEmpty(edt_card.getText())) {
                        showToast("请输入身份证");
                        return;
                    }
                    if (!TextUtils.isEmpty(edt_card.getText())) {
                        if (!edt_card.getText().toString().contains("*")) {
                            if (!IdcardValidatorUtils.isValidate18Idcard((edt_card.getText().toString()))) {
                                showToast("请输入正确身份证");
                                return;
                            }
                            enity.setIdentity(edt_card.getText().toString());
                            if (!TextUtils.isEmpty(tv_sex.getText()))
                                enity.setSex(tv_sex.getText().toString());
                            if (!TextUtils.isEmpty(tv_date.getText()))
                                enity.setAge(Integer.parseInt(tv_date.getText().toString()));
                        }
                    }
                    mPresenter.loadmsg(enity);
                }

            }
        });
        builder.build();

        iv_icon.setOnClickListener(this);
        relative_sex.setOnClickListener(this);
        relative_date.setOnClickListener(this);
        iv_why.setOnClickListener(this);
        ImageLoader.displayImage(Const.getAvater(userId), iv_icon);
//
//        edt_card.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (PreferencesUtils.getSharePreStr(Const.USERID).equals(userId))
//                    if (edt_card.isFocusable()) {
//                        edt_card.setText("");
//                        edt_card.setText(PreferencesUtils.getSharePreStr(Const.IDENTITY));
//                    }
//            }
//        });

        edt_card.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(edt_card.getText())) {
                    if (charSequence.length() == 18) {
                        if(!edt_card.getText().toString().contains("*")){
                            if (IdcardValidatorUtils.isValidate18Idcard(charSequence.toString())) {
                                setAgeAndSex(charSequence.toString());
                            } else {
                                setCanChoose();
                                showToast("请输入正确身份证号");
                            }
                        }
                    } else {
                        setCanChoose();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void setAgeAndSex(String card) {
        IdcardInfoExtractor extractor = new IdcardInfoExtractor(card);
        tv_sex.setText(extractor.getGender());
        tv_date.setText(extractor.getAge() + "");
        relative_sex.setEnabled(false);
        relative_date.setEnabled(false);
    }

    private void setCanChoose(){
        tv_sex.setText("");
        tv_date.setText("");
        relative_sex.setEnabled(true);
        relative_date.setEnabled(true);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getMsg(ResultBase result) {
        if (result.isSuccess()) {
            showToast("保存成功");
            if (source.equals("person")) {
                PreferencesUtils.putSharePre(Const.NAME, edt_name.getText().toString());
                PreferencesUtils.putSharePre(Const.IDENTITY, edt_card.getText().toString());
                PreferencesUtils.putSharePre(Const.SEX, tv_sex.getText().toString());
                PreferencesUtils.putSharePre(Const.AGE, tv_date.getText().toString());
            }

            SelfMsgActivity.this.finish();
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getUserInfo(ResultBase<UserInfo> result) {
        if (result.isSuccess()) {
            if (TextUtils.isEmpty(result.getData().getIdentity())) {
                isHadCard = false;
            } else {
                isHadCard = true;
                if (!userId.equals(PreferencesUtils.getSharePreStr(Const.USERID))) {
                    edt_card.setEnabled(false);
                }
            }
            edt_name.setText(result.getData().getName());

            tv_sex.setText(result.getData().getSex());
            tv_date.setText(result.getData().getAge());
            tv_phone.setText(FormatUtil.getSecretPhone(result.getData().getPhone()));
            if (!TextUtils.isEmpty(result.getData().getIdentity())) {
                edt_card.setText(FormatUtil.getSecretCard(result.getData().getIdentity()));
                edt_card.setEnabled(false);
                relative_date.setEnabled(false);
                relative_sex.setEnabled(false);
                edt_name.setEnabled(false);
            }

//            if (source.equals(Const.SOURCE_FAMILY_MEMBER)) {
                if (!TextUtils.isEmpty(edt_name.getText())) {
                    if (!TextUtils.isEmpty(edt_card.getText())) {
                        if (!TextUtils.isEmpty(tv_sex.getText())) {
                            if (!TextUtils.isEmpty(tv_date.getText())) {
                                if (!TextUtils.isEmpty(tv_phone.getText())) {
                                    builder.setRightText("");
                                }
                            }
                        }
                    }
//                }
            }

        } else {
            showToast(result.getMessage());
        }

    }

    @Override
    public void getAvager(ResultBase result) {
        if (result.isSuccess()) {

            //添加更新头像版本（头像相关使用302重定向跳转，所以需要使用一个标志来更新头像）
            int a = PreferencesUtils.getSharePreInt(Const.VERSION_CACHE);
            a = a + 1;
            PreferencesUtils.putSharePre(Const.VERSION_CACHE, a);
            CoreEvent event = new CoreEvent(EventCode.F);
            EventBusUtils.sendEvent(event);
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_icon:
                Bundle bundle = new Bundle();
                bundle.putString(ItemDialogFragment.DIALOG_CANCEL, "取消");
                final ItemDialogFragment dialogFragment = ItemDialogFragment.newInstance(ItemDialogFragment.class, bundle);
                dialogFragment.setOnItemClickDialogListener(new ItemDialogFragment.OnItemClickDialogListener() {
                    @Override
                    public void onItemClick(int position, String content) {
                        if (position == 0) {//拍照
                            pictureForCamera();
                        } else if (position == 1) {//从相册选择
                            pictureForVanGogh();
                        }
                        dialogFragment.dismiss();
                    }

                    @Override
                    public void onCancel(TextView tvCancel) {
                        dialogFragment.dismiss();
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "");
                break;

            case R.id.relative_sex:
                CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(SelfMsgActivity.this, new String[]{"男", "女"});
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_sex.setText(option);
                    }
                });
                picker.show();
                break;
            case R.id.relative_date:
                String[] ages = new String[120];
                for (int i = 0; i < 120; i++) {
                    ages[i] = i + "";
                }
                CustomHeaderAndFooterPicker pickerAges = new CustomHeaderAndFooterPicker(SelfMsgActivity.this, ages);
                pickerAges.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_date.setText(option);
                    }
                });
                pickerAges.show();
                break;

            case R.id.iv_why:
                AlertDialog.Builder builder = initAlertDialog("为什么要用身份证", getResources().getString(R.string.why_idcard));
                builder.setNegativeButton("好的", null);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
        }

    }

    private void pictureForCamera() {//拍照
        requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0x0001);
    }

    private void pictureForVanGogh() {//从相册选择
        requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0x0002);
    }

    @Override
    public void permissionGrant(boolean isGranted, int requestCode) {
        super.permissionGrant(isGranted, requestCode);
        if (!isGranted) {
            return;
        }

        switch (requestCode) {
            case 0x0001://拍照
                PictrueConfig.fromPhoto(SelfMsgActivity.this);
                break;

            case 0x0002://从相册选择

                PictrueConfig.formTakePhoto(SelfMsgActivity.this, true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == UCrop.REQUEST_CROP) {//拍照并裁剪成功
            iv_icon.setImageBitmap(PictrueConfig.handleCropResult(data, SelfMsgActivity.this));
            Uri resultUri = UCrop.getOutput(data);

            loadData(resultUri);

        } else if (requestCode == UCrop.RESULT_ERROR) {//拍照并裁剪失败
            showToast(PictrueConfig.handleCropError(data));

        } else if (requestCode == PictrueConfig.REQUEST_CODE_CHOOSE) {//从相册选择
            ImageLoader.displayLocalImage(VanGogh.obtainResult(data).get(0), iv_icon);

            loadData(VanGogh.obtainResult(data).get(0));

        } else if (requestCode == PictrueConfig.REQUEST_CODE_CAMERA) {//拍照
            Uri contentUri = VanGogh.obtainCamera();
            if (contentUri == null) {
                return;
            }
            if (!VanConfig.getInstance().cropEnable) {
                ArrayList<Uri> selected = new ArrayList<>();
                selected.add(contentUri);
                ImageLoader.displayLocalImage(contentUri, iv_icon);

                loadData(contentUri);

            } else {//拍照之后跳转到裁剪页面
                startCropActivity(contentUri);
            }
        }
    }

    private void loadData(Uri uri) {
        MultipartBody.Part file = ImageLoader.upLoadImage(uri);
        mPresenter.upLoadAvager(file, userId);
    }


    /**
     * 跳转到裁剪页面
     *
     * @param source 需要裁剪的图片
     */
    private void startCropActivity(Uri source) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String imageFileName = "IMG_" + dateFormat.format(new Date());

        Uri uri = Uri.fromFile(new File(getCacheDir(), imageFileName.concat(".jpeg")));
        UCrop.of(source, uri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1024, 1024)
                .withTargetActivity(VanCropActivity.class)
                .start(this);
    }
}
