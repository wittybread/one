package wangxuewei.bwie.com.wangxuewei20171221.ShoppingCar;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.bean.CartBean;

/**
 * Created by jim on 2017/12/21.
 */

public class GwcPresenter {
    private GwcViewAPI gwcViewAPI;
    private Context context;
    private final GwcModle gwcModle;

    public GwcPresenter(GwcViewAPI gwcViewAPI, Context context) {
        this.gwcViewAPI = gwcViewAPI;
        this.context = context;
        gwcModle = new GwcModle();

    }

    //请求数据
    public void getCarData(String url, String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        gwcModle.getCarShopData(url, map, new GwcPresenterAPI() {
            @Override
            public void getCarDataSuccess(CartBean shoppingCar) {
                gwcViewAPI.onGetCarDataSuccess(shoppingCar);
            }

            @Override
            public void getNullCar(Boolean empty) {
                gwcViewAPI.onGetNullCar(empty);
            }

            @Override
            public void getCarDataFailed(String s) {
                gwcViewAPI.onGetCarDataFailed(s);
            }
        });
    }

    //防止内存泄漏
    public void detatch() {
        if (gwcViewAPI != null) {
            gwcViewAPI = null;
        }
    }
}
