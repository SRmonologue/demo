package com.ohosure.demo;

import com.ohosure.smart.core.OsApplication;

/**
 * 描述：
 * Created by 9527 on 2018/6/19.
 */
public class app extends OsApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initOhosure(getApplicationContext());
    }
}
