package com.ohosure.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.OhoSure;
import com.ohosure.smart.core.callback.ConfigResponseCallback;
import com.ohosure.smart.core.callback.ControlResponseCallback;
import com.ohosure.smart.core.callback.InnerLoginResponseCallback;
import com.ohosure.smart.core.callback.SceneResponseCallback;
import com.ohosure.smart.core.callback.TimingTaskResponseCallback;
import com.ohosure.smart.database.devkit.log.MLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_lose_connect).setOnClickListener(this);

        findViewById(R.id.btn_getScene).setOnClickListener(this);

        findViewById(R.id.btn_control).setOnClickListener(this);

        findViewById(R.id.btn_login).setOnClickListener(this);

        findViewById(R.id.btn_inner_login).setOnClickListener(this);

        findViewById(R.id.btn_timingTask).setOnClickListener(this);

        findViewById(R.id.btn_responseJson).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://账号密码登陆
                OhoSure.getInstance(MainActivity.this).initLogin();
                break;
            case R.id.btn_inner_login://内网登陆
                OhoSure.getInstance(MainActivity.this).initInnerLogin(new InnerLoginResponseCallback() {
                    @Override
                    public void onSuccess() {
                        MLog.w(TAG, "内网登陆成功");
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_lose_connect:  //退出登陆
                OhoSure.getInstance(MainActivity.this).loginOut();
                break;
            case R.id.btn_getScene:     //获取场景
                OhoSure.getInstance(MainActivity.this).getAllScene(new SceneResponseCallback() {
                    @Override
                    public void getSceneResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_timingTask://获取定时任务列表:
                OhoSure.getInstance(MainActivity.this).getTimingTask(new TimingTaskResponseCallback() {
                    @Override
                    public void getTimingTaskResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_responseJson://获取入网设备信息
                OhoSure.getInstance(MainActivity.this).getConfigResponseJson(new ConfigResponseCallback() {
                    @Override
                    public void getConfigResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_control://新万能模块单控
                OhoSure.getInstance(MainActivity.this).getControl(164, 1, 2,
                        5, Const.toBytes(3), new ControlResponseCallback() {
                            @Override
                            public void getControlResponse(int i) {
                                MLog.w(TAG, i + "");
                            }
                        });
                break;
            default:
                break;
        }
    }
}
