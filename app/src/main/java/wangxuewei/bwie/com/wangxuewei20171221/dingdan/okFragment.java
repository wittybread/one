package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wangxuewei.bwie.com.wangxuewei20171221.R;
import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;

/**
 * Created by jim on 2017/12/21.
 */

public class okFragment extends Fragment implements DingdanViewAPI {

    private View view;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fr_pay, null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();//初始化数据

        DingdanPresenter dingdanPresenter = new DingdanPresenter(getContext(), this);
        dingdanPresenter.getDingdan("https://www.zhaoapi.cn/product/getOrders", 71 + "");


    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
    }


    @Override
    public void onGetDdSuccess(DingdanBean dingdanBean) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //添加布局管理器
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MyDingdanAdapter(dingdanBean.getData(), getContext()));

    }

    @Override
    public void onGetDdFailed(String err) {

    }
}
