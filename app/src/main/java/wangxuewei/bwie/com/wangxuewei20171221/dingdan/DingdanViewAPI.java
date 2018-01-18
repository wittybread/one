package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;

/**
 * Created by jim on 2017/12/21.
 */

public interface DingdanViewAPI {

    void onGetDdSuccess(DingdanBean dingdanBean);

    void onGetDdFailed(String err);

}
