package wangxuewei.bwie.com.wangxuewei20171221.ShoppingCar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.MyAdapter;
import wangxuewei.bwie.com.wangxuewei20171221.R;
import wangxuewei.bwie.com.wangxuewei20171221.bean.CartBean;
import wangxuewei.bwie.com.wangxuewei20171221.dingdan.DingdanActivity;
import wangxuewei.bwie.com.wangxuewei20171221.utils.CallBack;
import wangxuewei.bwie.com.wangxuewei20171221.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity implements GwcViewAPI {
    private LinearLayout noLoginCar;
    private LinearLayout emptyCar;
    private LinearLayout llcar;
    private ExpandableListView exListView;
    private CheckBox all_chekbox;
    private TextView tv_total_price;
    private TextView tv_go_to_pay;
    private List<CartBean.DataBean> groupList = new ArrayList<>();
    private List<List<CartBean.DataBean.ListBean>> childList = new ArrayList<>();
    private MyAdapter myAdapter;
    private GwcPresenter gwcPresenter;
    private int pri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        initView();
        //调用p层获取数据
        gwcPresenter = new GwcPresenter(this, MainActivity.this);
        gwcPresenter.getCarData("https://www.zhaoapi.cn/product/getCarts", 72 + "");

        myAdapter = new MyAdapter(this, groupList, childList);

        all_chekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.changeAllListCbState(all_chekbox.isChecked());
            }
        });

        tv_go_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pri < 1) {
                    Toast.makeText(MainActivity.this, "请选择商品", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid", 71 + "");
                    map.put("price", pri + "");
                    OkHttpUtils.getInstance().doPost("https://www.zhaoapi.cn/product/createOrder", map, new CallBack() {
                        @Override
                        public void onSuccess(String json) {
                            Toast.makeText(MainActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DingdanActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(String err) {

                        }
                    });
                }

            }
        });


    }

    private void initView() {
        noLoginCar = (LinearLayout) findViewById(R.id.noLoginCar);
        emptyCar = (LinearLayout) findViewById(R.id.emptyCar);
        //有东西的购物车界面
        llcar = (LinearLayout) findViewById(R.id.llCar);
        //二级列表
        exListView = (ExpandableListView) findViewById(R.id.exListView);
        //全选
        all_chekbox = (CheckBox) findViewById(R.id.all_chekbox2);
        //合计
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        //去结算
        tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        all_chekbox.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        pri = event.getPrice();
        //结算显示
        tv_go_to_pay.setText("结算(" + event.getCount() + ")");
        tv_total_price.setText("￥" + event.getPrice());
    }

    @Override
    public void onGetCarDataSuccess(CartBean shoppingCar) {
        List<CartBean.DataBean> data = shoppingCar.getData();
        groupList.addAll(data);
        for (int i = 0; i < data.size(); i++) {
            List<CartBean.DataBean.ListBean> datas = data.get(i).getList();
            childList.add(datas);
        }
        exListView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        exListView.setGroupIndicator(null);
        //一级列表固定展开
        for (int i = 0; i < groupList.size(); i++) {
            exListView.expandGroup(i);
        }
    }

    @Override
    public void onGetNullCar(Boolean empty) {
        if (empty) {
            emptyCar.setVisibility(View.VISIBLE);
            noLoginCar.setVisibility(View.GONE);
            llcar.setVisibility(View.GONE);
        } else {
            emptyCar.setVisibility(View.GONE);
            noLoginCar.setVisibility(View.GONE);
            llcar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetCarDataFailed(String s) {

    }

    //防止内存溢出
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (gwcPresenter != null) {
            gwcPresenter.detatch();
        }
    }
}
