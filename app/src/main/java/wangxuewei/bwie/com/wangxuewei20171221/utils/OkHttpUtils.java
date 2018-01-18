package wangxuewei.bwie.com.wangxuewei20171221.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jim on 2017/12/21.
 */

public class OkHttpUtils {
    private Handler handler = new Handler();
    public static OkHttpUtils okHttpUtils;

    public static OkHttpUtils getInstance() {

        if (null == okHttpUtils) {
            synchronized (OkHttpUtils.class) {
                okHttpUtils = new OkHttpUtils();
            }
        }

        return okHttpUtils;
    }

    //get请求
    public void okGet(String url, Map<String, String> map, final CallBack callback) {

        //对url和参数做拼接处理
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(url);
        //判断是否存在?   if中是存在
        if (stringBuffer.indexOf("?") != -1) {
            //判断?是否在最后一位    if中是不在最后一位
            if (stringBuffer.indexOf("?") != stringBuffer.length() - 1) {
                stringBuffer.append("&");
            }
        } else {
            stringBuffer.append("?");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuffer.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        //判断是否存在&   if中是存在
        if (stringBuffer.indexOf("&") != -1) {
            stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("&"));
        }


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().get().url(stringBuffer.toString()).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailed(e.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String string = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(string);
                    }
                });

            }
        });
    }

    //post请求
    public void doPost(String url, Map<String, String> map, final CallBack callBack) {
        //1:创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new logger()).build();
        //2:提供post请求需要的body对象
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody body = builder.build();
        //3:创建Request对象
        final Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //4:创建Call对象
        Call call = okHttpClient.newCall(request);
        //5:请求网络
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e.toString());
                    }
                });
            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                //当前是在子线程,回到主线程中
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        });
    }

}
