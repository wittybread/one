package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import com.google.gson.Gson;

import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;
import wangxuewei.bwie.com.wangxuewei20171221.utils.CallBack;
import wangxuewei.bwie.com.wangxuewei20171221.utils.OkHttpUtils;

/**
 * Created by jim on 2017/12/21.
 */

public class DingdanModel {

    public void getDingdanData(String url, Map<String, String> map, final DingdanPresenterAPI dingdanPresenterAPI) {
        OkHttpUtils.getInstance().doPost(url, map, new CallBack() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                DingdanBean dingdanBean = gson.fromJson(json, DingdanBean.class);
                dingdanPresenterAPI.getDdSuccess(dingdanBean);
            }

            @Override
            public void onFailed(String err) {
                dingdanPresenterAPI.getDdFailed(err);
            }
        });
    }

}
