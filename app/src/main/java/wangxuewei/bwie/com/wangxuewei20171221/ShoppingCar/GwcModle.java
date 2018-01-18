package wangxuewei.bwie.com.wangxuewei20171221.ShoppingCar;

import com.google.gson.Gson;

import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.bean.CartBean;
import wangxuewei.bwie.com.wangxuewei20171221.utils.CallBack;
import wangxuewei.bwie.com.wangxuewei20171221.utils.OkHttpUtils;

/**
 * Created by jim on 2017/12/21.
 */

public class GwcModle {
    public void getCarShopData(String url, Map<String, String> map, final GwcPresenterAPI gwcPresenterAPI) {
        OkHttpUtils.getInstance().doPost(url, map, new CallBack() {
            @Override
            public void onSuccess(String json) {
                if (!json.equals("null")) {
                    Gson gson = new Gson();
                    CartBean shoppingCar = gson.fromJson(json, CartBean.class);
                    gwcPresenterAPI.getCarDataSuccess(shoppingCar);
                    gwcPresenterAPI.getNullCar(false);
                } else {
                    gwcPresenterAPI.getNullCar(true);
                }
            }

            @Override
            public void onFailed(String err) {
                gwcPresenterAPI.getCarDataFailed(err);
            }
        });
    }
}
