package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.HealthyServiceAdapter;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HealthyPresenter;
import com.eaphone.g08android.ui.healthy.HealthyDetailActivity;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.LoginUtil;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/28 10:21
 * 修改人：Administrator
 * 修改时间：2017/9/28 10:21
 * 修改备注：
 */
public class FamilyDataActivity extends CoreBaseActivity<HealthyPresenter> implements JiankangContracts.HealthyView {
    @BindView(R.id.lv_data)
    ListView lv_data;

    private HealthyServiceAdapter mHealthyServiceAdapter;
    private List<Healthy> dataHomes;
    private String[] sersonTypeGetData = new String[]{Const.HEARTRATE, Const.BLODPRESSURE,/* Const.BLOOD_GLUCOSE, *//*Const.BLODPRESSURE_LOW,*/
            Const.BODYTEMPERATURE, Const.OXYGENATION, Const.ELECTROCARDIOGRAM};
    private String name;
    @Override
    public int getLayoutId() {
        return R.layout.activity_family_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        name = getIntent().getExtras().getString("name");
        initBackTitle("健康数据").build();

        initView();
        initAdapter();
    }


    private void initAdapter() {
        dataHomes = new ArrayList<>();
        show(R.string.loading);
        mHealthyServiceAdapter = new HealthyServiceAdapter(dataHomes, mContext);
        lv_data.setAdapter(mHealthyServiceAdapter);
        setListData();
        loadAnalysis();

    }

    public void loadAnalysis() {
        mPresenter.analysis(setHealthyDataEnity(), getIntent().getExtras().getString("userId"));
    }

    public HealthyDataEnity setHealthyDataEnity() {
        HealthyDataEnity data = new HealthyDataEnity();
        List<HealthyDataEnity.DataBean> list = new ArrayList<>();
        for (int i = 0; i < sersonTypeGetData.length; i++) {
            HealthyDataEnity.DataBean enity = new HealthyDataEnity.DataBean();
            enity.setSensorType(sersonTypeGetData[i]);
            enity.setType("last");
            list.add(enity);
        }
        data.setData(list);
        return data;
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
    public void getAnalysis(ResultBase<List<Healthy>> result) {
        dismiss();
        setListData();

        if (result.isSuccess()) {
            setResult(result);
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getFamilyMember(ResultBase<List<Family>> result) {

    }


    private void setResult(ResultBase<List<Healthy>> result) {
        if (result.isSuccess()) {
            List<Healthy> list = result.getData();
            if (list != null && list.size() > 0) {
                for (int m = 0; m < dataHomes.size(); m++) {
                    for (Healthy healthyDataHome : list) {
                        if (Const.BLODPRESSURE.equals(dataHomes.get(m).getSensorType()) && Const.BLODPRESSURE_LOW.equals(healthyDataHome.getSensorType())) {
                            dataHomes.get(m).setText(healthyDataHome.getText());
                        } else {

                            if (dataHomes.get(m).getSensorType().equals(healthyDataHome.getSensorType())) {
                                dataHomes.get(m).setText(healthyDataHome.getText());
                                dataHomes.get(m).setTimestamp(healthyDataHome.getTimestamp());
                                dataHomes.get(m).setStatus(healthyDataHome.getStatus());

                                if (healthyDataHome.getSource() != null) {
                                    Healthy.SourceBean sourceBean = new Healthy.SourceBean();
                                    sourceBean.setText(healthyDataHome.getSource().getText());
                                    sourceBean.setType(healthyDataHome.getSource().getType());
                                    dataHomes.get(m).setSource(sourceBean);
                                }
                            }
                        }
                    }
                }
                mHealthyServiceAdapter.notifyDataSetChanged();
            }
        } else {
            showToast(result.getMessage());
        }
    }


    private void initView() {
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (LoginUtil.isLogin()) {
                    if (dataHomes.get(position).getSensorType().equals(Const.ELECTROCARDIOGRAM)) {
                        showToast("敬请期待...");
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("type", dataHomes.get(position).getSensorType());
                        bundle.putString("userId",getIntent().getExtras().getString("userId"));
                        bundle.putString("name",name);
                        startActivity(HealthyDetailActivity.class, bundle);
                    }
                } else {
                    startActivity(LoginActivity.class);
                }
//                    IntentUtils.turnToForResult(getActivity(), GuanxinTelepathyActivity.class, null, 0);
            }
        });
    }

    private void setListData() {
        dataHomes.clear();

        Healthy HealthyDataHome2 = new Healthy();//血压
        HealthyDataHome2.setText("");
//        HealthyDataHome2.setValue_second("0");
        HealthyDataHome2.setSensorType(Const.BLODPRESSURE);
        HealthyDataHome2.setType("last");
        HealthyDataHome2.setTimestamp(null);

        Healthy HealthyDataHome1 = new Healthy();//心率
        HealthyDataHome1.setText("");
        HealthyDataHome1.setSensorType(Const.HEARTRATE);
        HealthyDataHome1.setType("last");
        HealthyDataHome1.setTimestamp(null);

        Healthy HealthyDataHome4 = new Healthy();//血氧
        HealthyDataHome4.setText("");
        HealthyDataHome4.setSensorType(Const.OXYGENATION);
        HealthyDataHome4.setType("last");
        HealthyDataHome4.setTimestamp(null);

        Healthy HealthyDataHome3 = new Healthy();//体温
        HealthyDataHome3.setText("");
        HealthyDataHome3.setSensorType(Const.BODYTEMPERATURE);
        HealthyDataHome3.setType("last");
        HealthyDataHome3.setTimestamp(null);

        Healthy HealthyDataHome5 = new Healthy();//心电图
        HealthyDataHome5.setText("");
        HealthyDataHome5.setSensorType(Const.ELECTROCARDIOGRAM);
        HealthyDataHome5.setType("last");
        HealthyDataHome5.setTimestamp(null);

/*        Healthy HealthyDataHome6 = new Healthy();//血糖
        HealthyDataHome6.setValue("0");
        HealthyDataHome6.setSensorType(Const.BLOOD_GLUCOSE);
        HealthyDataHome6.setType("last");
        HealthyDataHome6.setTimestamp(null);*/

        dataHomes.add(HealthyDataHome2);
        dataHomes.add(HealthyDataHome1);
//        dataHomes.add(HealthyDataHome6);
        dataHomes.add(HealthyDataHome4);
        dataHomes.add(HealthyDataHome3);
        dataHomes.add(HealthyDataHome5);

        mHealthyServiceAdapter.notifyDataSetChanged();
    }
}
