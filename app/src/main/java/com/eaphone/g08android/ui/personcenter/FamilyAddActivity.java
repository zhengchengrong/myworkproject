package com.eaphone.g08android.ui.personcenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alley.van.VanGogh;
import com.alley.van.activity.VanCropActivity;
import com.alley.van.model.VanConfig;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.FamilyAddMemberEntity;
import com.eaphone.g08android.bean.FileResult;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.FamilyAddPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.TimeDown;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.CircleImageView;
import com.eaphone.g08android.widget.CustomHeaderAndFooterPicker;
import com.eaphone.g08android.widget.picture.ItemDialogFragment;
import com.eaphone.g08android.widget.picture.PictrueConfig;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.kevin.crop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MultipartBody;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/22 15:11
 * 修改人：Administrator
 * 修改时间：2017/8/22 15:11
 * 修改备注：
 */
public class FamilyAddActivity extends CoreBaseActivity<FamilyAddPresenter> implements PassportContracts.FamilyAddView, View.OnClickListener {

    @BindView(R.id.edt_phone)
    EditText mEdtPhone;

    @BindView(R.id.tv_relation)
    TextView mTvRelation;

    @BindView(R.id.relative_relation)
    RelativeLayout mRtlRelation;

    @BindView(R.id.rtl_avager)
    RelativeLayout mRtlAvager;

    @BindView(R.id.relative_code)
    RelativeLayout mRelativeCode;

    @BindView(R.id.relative_name)
    RelativeLayout mRelativeName;

    @BindView(R.id.relative_card)
    RelativeLayout mRelativeCard;

    @BindView(R.id.relative_sex)
    RelativeLayout mRelativeSex;

    @BindView(R.id.relative_date)
    RelativeLayout mRelativeDate;

    @BindView(R.id.edt_code)
    EditText mEdtCode;

    @BindView(R.id.edt_name)
    EditText mEdtName;

    @BindView(R.id.edt_card)
    EditText mEdtCard;

    @BindView(R.id.btn_code)
    Button mBtnCode;

    @BindView(R.id.tv_sex)
    TextView mTvSex;

    @BindView(R.id.tv_date)
    TextView mTvDate;

    @BindView(R.id.iv_why)
    ImageView mIvWhy;

    @BindView(R.id.iv_avager)
    CircleImageView mIvAvager;


    private String source;
    private String rightText;
    private boolean isCiclk = false;
    private TitleBuilder builder;
    private String title;

    private String userId;

    private boolean isRegist = false;

    @Override
    public int getLayoutId() {
        return R.layout.family_add_activity;
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        source = getIntent().getExtras().getString(Const.SOURCE);

        rightText = "保存";
        title = "添加成员";

        builder = initBackTitle(title).setRightText(rightText).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mEdtPhone.getText().toString())) {
                    showToast("请输入手机号");
                    return;
                }
                if (!FormatUtil.isMobileNO(mEdtPhone.getText().toString())) {
                    showToast("请输入正确手机号");
                    return;
                }

                if (TextUtils.isEmpty(mTvRelation.getText().toString())) {
                    showToast("请选择关系");
                    return;
                }
                String phone = mEdtPhone.getText().toString();


                FamilyAddMemberEntity family = new FamilyAddMemberEntity();

                family.setPhone(phone);

                family.setRelationship(mTvRelation.getText().toString());

                if (mRelativeCode.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(mEdtCode.getText().toString())) {
                        showToast(getResources().getString(R.string.input_code));
                        return;
                    }
                }

                family.setAuthcode(mEdtCode.getText().toString());

                if (!TextUtils.isEmpty(mTvDate.getText().toString())) {
                    family.setBirthday(mTvDate.getText().toString());
                }
                if (!TextUtils.isEmpty(mTvSex.getText().toString())) {
                    family.setGender(mTvSex.getText().toString());
                }

                if (!TextUtils.isEmpty(mEdtCard.getText().toString())) {
                    String card = mEdtCard.getText().toString();
                    family.setIdentity(card);
                }

                if (!TextUtils.isEmpty(mEdtName.getText().toString())) {
                    family.setName(mEdtName.getText().toString());
                }
                if (!TextUtils.isEmpty(imgUrl)) {
                    family.setAvatar(imgUrl);
                }
                mPresenter.addMember(family);
            }
        });
        builder.build();

        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    if (FormatUtil.isMobileNO(charSequence.toString())) {
//                        if (source.equals(SOURCE_FAMILY))
                            mPresenter.isExist(charSequence.toString());
                        mEdtPhone.setEnabled(false);
                    } else {
                        showToast("请输入正确手机号");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addAction();
    }
    private TimeDown timeDown;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_relation:
                CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(FamilyAddActivity.this, new String[]{"祖辈", "父母", "夫妻", "子女", "兄弟姐妹", "孙辈"});
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        mTvRelation.setText(option);
                    }
                });
                picker.show();
                break;
            case R.id.btn_code:
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())) {
                    showToast("请输入手机号");
                } else if (!FormatUtil.isMobileNO(mEdtPhone.getText().toString())) {
                    showToast("请输入正确手机号");
                } else {
                    timeDown = new TimeDown(60 * 1000, 1000, mBtnCode);
                    timeDown.start();
                    mPresenter.addCode(mEdtPhone.getText().toString());
                }
                break;
            case R.id.relative_sex:
                CustomHeaderAndFooterPicker sexPicker = new CustomHeaderAndFooterPicker(FamilyAddActivity.this, new String[]{"男", "女"});
                sexPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        mTvSex.setText(option);
                    }
                });
                sexPicker.show();
                break;
            case R.id.relative_date:
                DatePicker datePicker = new DatePicker(FamilyAddActivity.this);
                datePicker.setRange(1900, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));
                datePicker.setSelectedItem(Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)),
                        Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_11)),
                        Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_15)));
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mTvDate.setText(year + "-" + month + "-" + day);
                    }
                });
                datePicker.show();
                break;
            case R.id.iv_why:
                initAlertDialog("为什么要用身份证", getResources().getString(R.string.why_idcard))
                        .setNegativeButton("好的", null)
                        .create()
                        .show();
                break;
            case R.id.iv_avager:
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
                PictrueConfig.fromPhoto(FamilyAddActivity.this);
                break;

            case 0x0002://从相册选择

                PictrueConfig.formTakePhoto(FamilyAddActivity.this, true);
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
            mIvAvager.setImageBitmap(PictrueConfig.handleCropResult(data, FamilyAddActivity.this));
            Uri resultUri = UCrop.getOutput(data);

            loadData(resultUri);

        } else if (requestCode == UCrop.RESULT_ERROR) {//拍照并裁剪失败
            showToast(PictrueConfig.handleCropError(data));

        } else if (requestCode == PictrueConfig.REQUEST_CODE_CHOOSE) {//从相册选择
            ImageLoader.displayLocalImage(VanGogh.obtainResult(data).get(0), mIvAvager);

            loadData(VanGogh.obtainResult(data).get(0));

        } else if (requestCode == PictrueConfig.REQUEST_CODE_CAMERA) {//拍照
            Uri contentUri = VanGogh.obtainCamera();
            if (contentUri == null) {
                return;
            }
            if (!VanConfig.getInstance().cropEnable) {
                ArrayList<Uri> selected = new ArrayList<>();
                selected.add(contentUri);
                ImageLoader.displayLocalImage(contentUri, mIvAvager);

                loadData(contentUri);

            } else {//拍照之后跳转到裁剪页面
                startCropActivity(contentUri);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timeDown!=null)
        timeDown.cancel();
    }

    private void loadData(Uri uri) {
        MultipartBody.Part file = ImageLoader.upLoadImage(uri);
        mPresenter.upLoadAvager(file);
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

    private void addAction() {
        mRtlRelation.setOnClickListener(this);
        mBtnCode.setOnClickListener(this);
        mRelativeDate.setOnClickListener(this);
        mRelativeSex.setOnClickListener(this);
        mIvWhy.setOnClickListener(this);
        mIvAvager.setOnClickListener(this);
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
    public void getIsExist(ResultBase<Boolean> result) {
        mEdtPhone.setEnabled(true);
        if (result.isSuccess()) {
            if (result.getData()) {//已经注册，则发送邀请消息
                setCodeVisibility(View.GONE);
                setVisibility(View.GONE);
                isRegist = true;
            } else {//未注册则走添加家庭成员的接口，发送验证码
                showToast("该用户还未注册，请为TA注册吧");
                setVisibility(View.VISIBLE);
                setCodeVisibility(View.VISIBLE);
                isRegist = false;
            }
        }
    }

    private void setVisibility(int visible) {
        mRtlAvager.setVisibility(visible);
        mRelativeName.setVisibility(visible);
        mRelativeCard.setVisibility(visible);
        mRelativeSex.setVisibility(visible);
        mRelativeDate.setVisibility(visible);
    }


    private void setCodeVisibility(int visible) {
        mRelativeCode.setVisibility(visible);
    }

    @Override
    public void getSendInvite(ResultBase result) {
        if (result.isSuccess()) {

        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getAddCode(ResultBase result) {
        if (result.isSuccess()) {
            initAlertDialog("", "验证码已成功发送至" + mEdtPhone.getText().toString() + "，请查收")
                    .setNegativeButton("好的", null)
                    .create()
                    .show();
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getAddMember(ResultBase resultBase) {
        if (resultBase.isSuccess()) {
            if (source.equals(Const.SOURCE_FAMILY)) {
                if (isRegist) {
                    setResult(2);
                    FamilyAddActivity.this.finish();
                    showToast("邀请已发出，请等待对方回复");
                } else {
                    setResult(2);
                    FamilyAddActivity.this.finish();
                }
            } else {

                if (isRegist) {
                    FamilyAddActivity.this.finish();
                    showToast("邀请已发出，请等待对方回复");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", (String) resultBase.getData());
                    startActivity(JiankangActivity.class, bundle);
                }

            }
            showToast(resultBase.getMessage());

        } else {
            showToast(resultBase.getMessage());
        }
    }

    private String imgUrl = "";

    @Override
    public void getAvaget(ResultBase<FileResult> resultBase) {
        if (resultBase.isSuccess()) {
            imgUrl = resultBase.getData().getUrl();
        }
    }

    @Override
    public void getMsg(ResultBase resultBase) {
        if (resultBase.isSuccess()) {
            showToast("保存成功");
        } else {
            showToast(resultBase.getMessage());
        }
    }

    @Override
    public void getUserMsg(ResultBase<UserInfo> resultBase) {
        if (resultBase.isSuccess()) {
            UserInfo info = resultBase.getData();
            if (!TextUtils.isEmpty(info.getName()))
                mEdtName.setText(info.getName());

            if (!TextUtils.isEmpty(info.getPhone()))
                mEdtPhone.setText(info.getPhone());

            if (!TextUtils.isEmpty(info.getIdentity()))
                mEdtCard.setText(info.getIdentity());

//            mTvRelation.setText(info.getR);
            if (!TextUtils.isEmpty(info.getBirthday()))
                mTvDate.setText(info.getBirthday());

            if (!TextUtils.isEmpty(info.getSex()))
                mTvSex.setText(info.getSex());
        } else {
            showToast(resultBase.getMessage());
        }
    }
}
