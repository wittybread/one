package wangxuewei.bwie.com.wangxuewei20171221.dingdan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wangxuewei.bwie.com.wangxuewei20171221.R;
import wangxuewei.bwie.com.wangxuewei20171221.bean.DingdanBean;
import wangxuewei.bwie.com.wangxuewei20171221.utils.CallBack;
import wangxuewei.bwie.com.wangxuewei20171221.utils.OkHttpUtils;

/**
 * Created by jim on 2017/12/21.
 */

public class MyDingdanAdapter extends RecyclerView.Adapter<MyDingdanAdapter.ViewHolder> {

    private List<DingdanBean.DataBean> list;
    private Context context;

    public MyDingdanAdapter(List<DingdanBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyDingdanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_dingdan, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyDingdanAdapter.ViewHolder holder, final int position) {
        int status = list.get(position).getStatus();

        if (status == 0) {
            holder.ddState.setTextColor(Color.RED);
            holder.ddState.setText("待支付");
            holder.btndd.setText("取消订单");
        } else if (status == 1) {
            holder.ddState.setTextColor(Color.BLACK);
            holder.ddState.setText("已支付");
            holder.btndd.setText("查看订单");
        } else if (status == 2) {
            holder.ddState.setTextColor(Color.BLACK);
            holder.ddState.setText("已取消");
            holder.btndd.setText("查看订单");
        }
        holder.ddName.setText(list.get(position).getTitle());
        holder.ddPrice.setText("价格:" + list.get(position).getPrice());
        String createtime = list.get(position).getCreatetime();
        String[] ts = createtime.split("T");
        holder.ddtime.setText("创建时间:" + ts[0] + " " + ts[1]);


        holder.btndd.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                String text = (String) holder.btndd.getText();
                if (text.equals("取消订单")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("确认取消订单？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("uid", 71 + "");
                            map.put("status", list.get(position).getStatus() + "");
                            map.put("orderId", list.get(position).getOrderid() + "");
                            OkHttpUtils.getInstance().doPost("https://www.zhaoapi.cn/product/updateOrder?uid=71&status=0&orderId=1446", map, new CallBack() {
                                @Override
                                public void onSuccess(String json) {
                                    Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailed(String err) {

                                }
                            });

                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                } else if (text.equals("查看订单")) {
                    Toast.makeText(context, "查看订单", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ddName;
        private final TextView ddState;
        private final TextView ddPrice;
        private final TextView ddtime;
        private final Button btndd;

        public ViewHolder(View itemView) {
            super(itemView);

            ddName = itemView.findViewById(R.id.ddname);
            ddState = itemView.findViewById(R.id.ddState);
            ddPrice = itemView.findViewById(R.id.ddPrice);
            ddtime = itemView.findViewById(R.id.ddtime);
            btndd = itemView.findViewById(R.id.btndd);
        }
    }

}
