package com.bwie.okhttp_mvp.inter;

/**
 * 创建的一个就口
 * Created by DELL on 2018/10/12.
 */

public interface INetResult {
    //两个方法  一个成功的  一个失败的
    void onSuccess(Object obj);

    void onFailed(Exception e);
}
