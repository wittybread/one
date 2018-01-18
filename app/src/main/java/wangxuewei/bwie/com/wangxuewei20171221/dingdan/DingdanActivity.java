package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import wangxuewei.bwie.com.wangxuewei20171221.R;

public class DingdanActivity extends AppCompatActivity {
    private TabLayout myTab;
    private ViewPager viewPage;
    private List<String> tab_list;
    private List<Fragment> fr_list;
    private ImageView cutImg;
    private Button btnDai2;
    private Button btnDai1;
    private Button btnDai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);

        initView();
        initTab();
        //设置适配器
        viewPage.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //进行关联
        myTab.setupWithViewPager(viewPage);
        //显示的页数
        viewPage.setOffscreenPageLimit(fr_list.size());

        cutImg.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                View inflate = View.inflate(DingdanActivity.this, R.layout.pop, null);
                //创建对象
                PopupWindow popupWindow = new PopupWindow(inflate, 80, 320);
                //点击外部可消失
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                //获取焦点
                popupWindow.setFocusable(true);
                //设置位置
                popupWindow.showAsDropDown(cutImg, 300, 0);
            }
        });
    }

    private void initView() {
        myTab = (TabLayout) findViewById(R.id.myTab);
        viewPage = (ViewPager) findViewById(R.id.viewPage);
        cutImg = (ImageView) findViewById(R.id.img);

    }

    //适配器
    class TabAdapter extends FragmentPagerAdapter {


        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tab_list.get(position);
        }

        @Override
        public Fragment getItem(int position) {

            return fr_list.get(position);
        }

        @Override
        public int getCount() {

            return fr_list.size();
        }


    }

    private void initTab() {
        tab_list = new ArrayList<>();
        tab_list.add("待支付");
        tab_list.add("已支付");
        tab_list.add("已取消");
        fr_list = new ArrayList<>();
        //存放的是数据类型是Fragment
        fr_list.add(new PayFragment());
        fr_list.add(new okFragment());
        fr_list.add(new cancleFragment());

    }

}
