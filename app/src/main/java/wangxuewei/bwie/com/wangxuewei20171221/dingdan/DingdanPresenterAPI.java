package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;

/**
 * Created by jim on 2017/12/21.
 */

public interface DingdanPresenterAPI {

    void getDdSuccess(DingdanBean dingdanBean);

    void getDdFailed(String err);
}
