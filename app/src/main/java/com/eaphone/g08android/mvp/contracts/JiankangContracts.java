package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.Device;
import com.eaphone.g08android.bean.DeviceBindEntity;
import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.DeviceName;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.HandInDataEnity;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.bean.HealthyWarn;
import com.eaphone.g08android.bean.HealthyWarnDetail;
import com.eaphone.g08android.bean.JianKang;
import com.eaphone.g08android.bean.LineBean;
import com.eaphone.g08android.bean.MessageWarnDetail;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 9:40
 * 修改人：Administrator
 * 修改时间：2017/8/23 9:40
 * 修改备注：
 */
public interface JiankangContracts {

    //健康首页
    interface HealthyView extends CoreBaseView {
        void getAnalysis(ResultBase<List<Healthy>> result);
        void getFamilyMember(ResultBase<List<Family>> result);
    }

    abstract class HealthyPresenter extends CoreBasePresenter<HealthyView> {
        public abstract void analysis(HealthyDataEnity data,String userId);

        public abstract void familyMember();
    }

    //我的设备列表
    interface DeviceView extends CoreBaseView {
        void getAnalysis(ResultBase<List<Device>> result);
    }

    abstract class DevicePresenter extends CoreBasePresenter< DeviceView> {
        public abstract void analysis();
    }


    //设备详情

    interface DeviceDetailView extends CoreBaseView {
        void getDetail(ResultBase<DeviceDetail> result);

        void getDeviceUnbind(ResultBase result);

        void getDeviceRename(ResultBase result);
    }

    abstract class DeviceDetailPresenter extends CoreBasePresenter< DeviceDetailView> {
        public abstract void detail(String deviceId);

        public abstract void deviceUnbind(String device_id);

        public abstract void deviceRename(String device_id, DeviceName name);
    }

    //最新上传数据

    interface LastDataView extends CoreBaseView {
        void getLastData(ResultBase<List<Healthy>> result);

        void getHistoryData(ResultBase<List<Healthy>> result);
    }

    abstract class LastDataPresenter extends CoreBasePresenter< LastDataView> {
        public abstract void lastData(HealthyDataEnity healthy,String userId);

        public abstract void historyData(String sensor_type, String begin_time, String end_time,String userId);
    }

    //获取日周月年

    interface HistoryDataView extends CoreBaseView {
        void getHistoryData(ResultBase<List<Healthy>> result);

        void getTimeTypeData(ResultBase<List<LineBean>> result);
    }

    abstract class HistoryDataPresenter extends CoreBasePresenter< HistoryDataView> {
        public abstract void historyData(String sensor_type, String begin_time, String end_time,String userId);

        public abstract void timeTypeData(String sensor_type, String time_type, String firstDay,String userId);
    }

    //手动输入数据

    interface HandInView extends CoreBaseView {
        void getHandIn(ResultBase resultBase);
    }

    abstract class HandInPresenter extends CoreBasePresenter< HandInView> {
        public abstract void handIn(String sensorType, HandInDataEnity enity);
    }

    //告警周月年

    interface WarnView extends CoreBaseView {
        void getHealthyWarn(ResultBase<List<HealthyWarn>> resultBase);
    }

    abstract class WarnPresenter extends CoreBasePresenter< WarnView> {
        public abstract void healthyWarn(String sersonType, String userId);
    }

    //告警列表

    interface WarnListView extends CoreBaseView {
        void getWarnList(ResultBase<List<HealthyWarnDetail>> resultBase);
    }

    abstract class WarnListPresenter extends CoreBasePresenter< WarnListView> {
        public abstract void warnList(String sensor_type, String page_index,
                                      String page_size, String end_time, String begin_time,String userId);
    }

    //告警详情

    interface WarnDetailView extends CoreBaseView{
        void getWarnDetail(ResultBase<MessageWarnDetail> result);
    }

    abstract class WarnDetailPresenter extends CoreBasePresenter<WarnDetailView>{
        public abstract void warnDetail(String id);
    }


    interface DeviceBindUserView extends CoreBaseView {

        void getFamilyMember(ResultBase<List<Family>> result);

        void getBindDevice(ResultBase<DeviceDetail> result);

        void getDeviceDelete(ResultBase result);

        void getDetail(ResultBase<DeviceDetail> result);
    }

    abstract class DeviceBindUserPresenter extends CoreBasePresenter< DeviceBindUserView> {
        public abstract void familyMember();

        public abstract void deviceBind(DeviceBindEntity entity);

        public abstract void deviceDelete(String deviceId, String channelName);

        public abstract void detail(String deviceId);
    }

    interface JiankangView extends CoreBaseView{
        void getJiankang(ResultBase<JianKang> result);
        void getParchJiankang(ResultBase<JianKang> result);
    }

    abstract class JiankangPresenter extends CoreBasePresenter<JiankangView>{
        public abstract void jiankang(String userId);
        public abstract void jiankang(String userId,JianKang jianKang);
    }

}
