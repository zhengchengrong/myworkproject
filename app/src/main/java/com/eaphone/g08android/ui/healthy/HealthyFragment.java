package com.eaphone.g08android.ui.healthy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.HealthyHeaderAdapter;
import com.eaphone.g08android.adapter.HealthyServiceAdapter;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HealthyPresenter;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 健康服务首页
 */
public class HealthyFragment extends CoreBaseFragment<HealthyPresenter> implements JiankangContracts.HealthyView {
    @BindView(R.id.lv_data)
    ListView lv_data;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.ll_header)
    LinearLayout ll_header;

    private HealthyHeaderAdapter mHeaderAdapter;

    private List<Family> list;

    private HealthyServiceAdapter mHealthyServiceAdapter;
    private List<Healthy> dataHomes;
    private String[] sersonTypeGetData = new String[]{Const.HEARTRATE, Const.BLODPRESSURE,/* Const.BLOOD_GLUCOSE, *//*Const.BLODPRESSURE_LOW,*/
            Const.BODYTEMPERATURE, Const.OXYGENATION/*, Const.ELECTROCARDIOGRAM*/};

    private IntentFilter filter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "NEW_MESSAGE":
                    if (LoginUtil.isLogin()) {
                        mPresenter.familyMember();
                        loadAnalysis();
                    }
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.healthy_fragment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        initView();
        initAdapter();
        filter = new IntentFilter("NEW_MESSAGE");
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(receiver);
    }

    private void initAdapter() {
        dataHomes = new ArrayList<>();
        list = new ArrayList<>();
        mPresenter.familyMember();

        mHealthyServiceAdapter = new HealthyServiceAdapter(dataHomes, getActivity());
        lv_data.setAdapter(mHealthyServiceAdapter);


        if (LoginUtil.isLogin()) {
            loadAnalysis();
            ll_header.setVisibility(View.VISIBLE);
            initHeaderAdapter();
            show(R.string.loading);
        } else {
            ll_header.setVisibility(View.GONE);
            setListData();
        }

    }

    private void initHeaderAdapter() {
        mHeaderAdapter = new HealthyHeaderAdapter(R.layout.healthy_family_item, list, getActivity(), mPresenter, setHealthyDataEnity());
        RecyclerViewHelper.initRecyclerViewH(getActivity(), recyclerView, false, mHeaderAdapter);
    }


    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);

        if (event != null) {
            switch (event.getCode()) {
                case EventCode.A:
                    loadAnalysis();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginUtil.isLogin()) {
            mPresenter.familyMember();
            loadAnalysis();
        }
    }

    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (LoginUtil.isLogin()) {
            mPresenter.familyMember();
            loadAnalysis();
        }
    }

    public void loadAnalysis() {
        mPresenter.analysis(setHealthyDataEnity(), LoginUtil.isFamilyMember());
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
    public void showError(String msg) {
        showToast(msg);
        dismiss();
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
        if (result.isSuccess()) {
            list.clear();
            Family family = new Family();
            family.setName("添加成员");

            Family family2 = new Family();
            family2.setName("我");
            family2.setUserId(PreferencesUtils.getSharePreStr(Const.USERID));
            list.add(family2);
            list.addAll(result.getData());
            list.add(family);
            if (mHeaderAdapter != null)
                mHeaderAdapter.setNewData(list);

        }
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
        Healthy.SourceBean bean = new Healthy.SourceBean();
        bean.setText("数据来源：--");

        Healthy HealthyDataHome2 = new Healthy();//血压
        HealthyDataHome2.setText("");
//        HealthyDataHome2.setValue_second("0");
        HealthyDataHome2.setSensorType(Const.BLODPRESSURE);
        HealthyDataHome2.setType("last");
        HealthyDataHome2.setTimestamp(null);
        HealthyDataHome2.setSource(bean);

        Healthy HealthyDataHome1 = new Healthy();//心率
        HealthyDataHome1.setText("");
        HealthyDataHome1.setSensorType(Const.HEARTRATE);
        HealthyDataHome1.setType("last");
        HealthyDataHome1.setTimestamp(null);
        HealthyDataHome1.setSource(bean);

        Healthy HealthyDataHome4 = new Healthy();//血氧
        HealthyDataHome4.setText("");
        HealthyDataHome4.setSensorType(Const.OXYGENATION);
        HealthyDataHome4.setType("last");
        HealthyDataHome4.setTimestamp(null);
        HealthyDataHome4.setSource(bean);

        Healthy HealthyDataHome3 = new Healthy();//体温
        HealthyDataHome3.setText("");
        HealthyDataHome3.setSensorType(Const.BODYTEMPERATURE);
        HealthyDataHome3.setType("last");
        HealthyDataHome3.setTimestamp(null);
        HealthyDataHome3.setSource(bean);

        Healthy HealthyDataHome5 = new Healthy();//心电图
        HealthyDataHome5.setText("");
        HealthyDataHome5.setSensorType(Const.ELECTROCARDIOGRAM);
        HealthyDataHome5.setType("last");
        HealthyDataHome5.setTimestamp(null);
        HealthyDataHome5.setSource(bean);

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
