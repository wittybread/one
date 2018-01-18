package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;

/**
 * Created by jim on 2017/12/21.
 */

public class DingdanPresenter {

    private Context context;
    private DingdanViewAPI dingdanViewAPI;
    private final DingdanModel dingdanModel;

    public DingdanPresenter(Context context, DingdanViewAPI dingdanViewAPI) {
        this.context = context;
        this.dingdanViewAPI = dingdanViewAPI;
        dingdanModel = new DingdanModel();

    }

    public void getDingdan(String url, String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        dingdanModel.getDingdanData(url, map, new DingdanPresenterAPI() {
            @Override
            public void getDdSuccess(DingdanBean dingdanBean) {
                dingdanViewAPI.onGetDdSuccess(dingdanBean);
            }

            @Override
            public void getDdFailed(String err) {
                dingdanViewAPI.onGetDdFailed(err);
            }
        });
    }
    //防止内存泄漏
    public void detatch() {
        if (dingdanViewAPI != null) {
            dingdanViewAPI = null;
        }
    }

}
