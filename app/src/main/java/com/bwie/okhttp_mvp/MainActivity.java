package com.bwie.okhttp_mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bwie.okhttp_mvp.adapter.NewsAdapter;
import com.bwie.okhttp_mvp.bean.News;
import com.bwie.okhttp_mvp.inter.INetResult;
import com.bwie.okhttp_mvp.utils.HttpUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String url="http://www.xieast.com/api/news/news.php?page=1";

    private ListView lvNews;
    private Button btnRequest;
    private List<News.DataBean> list;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 控件
        btnRequest = findViewById(R.id.btn_request);
        lvNews = findViewById(R.id.lv_news);

        //2 创建list  和 Adapter
        list = new ArrayList<>();
        adapter = new NewsAdapter(this, list);
        lvNews.setAdapter(adapter);

        //3 设置点击事件
        btnRequest.setOnClickListener(this);
    }

    //3 设置点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request://点击按钮  进行请求网络数据
                 //点击之后进行网络请求数据
                 //1 创建一个type类型
                Type type = new TypeToken<News>(){}.getType();
                //调用工具类的方法进行数据请求
                HttpUtils.getIntance().get(url, new INetResult() {
                    @Override
                    public void onSuccess(Object obj) {
                        //得到请求成功的数据  进行数据强zhuan
                        News news = (News) obj;
                        //判断
                        if(news!=null){
                            //如果得到的数据不为空
                            List<News.DataBean> data = news.getData();
                            if(data!=null){
                                list.clear();
                                list.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                },type);

                break;
        }
    }


}
