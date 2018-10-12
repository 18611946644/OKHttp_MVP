package com.bwie.okhttp_mvp.utils;

import android.os.Handler;

import com.bwie.okhttp_mvp.R;
import com.bwie.okhttp_mvp.inter.INetResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 工具类
 * Created by DELL on 2018/10/12.
 */

public class HttpUtils {

    //1 一个单例模式（懒汉单例模式）
    private static volatile  HttpUtils intance;

    //3 创建一个线程
    private Handler handler = new Handler();

    //4 引入框架定义一个okHttp框架
    private OkHttpClient client;

    //2 无惨构造  不写默认也有
    private HttpUtils(){
        //初始化
        client = new OkHttpClient();
    }

    //5 创建一个单例
    public static HttpUtils getIntance(){
        if(intance==null){
            synchronized (HttpUtils.class){
                 if(intance == null){
                     intance = new HttpUtils();
                 }
            }
        }
        return intance;
    }

    //6 okhttp请求网络方法
    public void get(String url, final INetResult result, final Type type){
        //6.2创建的对象在单例模式中初始化的
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //6.3 通过初始化client  和 request 获取一个call
        Call call = client.newCall(request);

        //6.4 使用enqueue方式请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //请求失败
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      result.onFailed(e);//调用失败方法
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 //请求成功   接口回调
                 String s = response.body().string();//得到数据
                 //进行解析
                Gson gson = new Gson();
                final Object o = gson.fromJson(s, type);
                //调用成功方法
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.onSuccess(o);
                    }
                });
            }
        });
    }


}
