package wangxuewei.bwie.com.wangxuewei20171221.ShoppingCar;

import wangxuewei.bwie.com.wangxuewei20171221.bean.CartBean;

/**
 * Created by jim on 2017/12/21.
 */

public interface GwcPresenterAPI {
    void getCarDataSuccess(CartBean shoppingCar);

    void getNullCar(Boolean empty);

    void getCarDataFailed(String s);
}
