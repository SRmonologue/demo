package com.ohosure.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.OhoSure;
import com.ohosure.smart.core.callback.ConfigResponseCallback;
import com.ohosure.smart.core.callback.ControlResponseCallback;
import com.ohosure.smart.core.callback.InnerLoginResponseCallback;
import com.ohosure.smart.core.callback.LoginResponseCallback;
import com.ohosure.smart.core.callback.QueryRoomResponseCallback;
import com.ohosure.smart.core.callback.RoomResponseCallback;
import com.ohosure.smart.core.callback.SceneResponseCallback;
import com.ohosure.smart.core.callback.TimingTaskResponseCallback;
import com.ohosure.smart.core.callback.InfoResponseCallback;
import com.ohosure.smart.core.callback.ZigBeeInfoResponseCallback;
import com.ohosure.smart.database.devkit.log.MLog;
import com.ohosure.smart.zigbeegate.protocol.model.DBRoomArea;
import com.tapadoo.alerter.Alerter;

import java.util.List;

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

        findViewById(R.id.btn_addRoom).setOnClickListener(this);

        findViewById(R.id.btn_RemoveRoom).setOnClickListener(this);

        findViewById(R.id.btn_QueryRoom).setOnClickListener(this);

        findViewById(R.id.btn_mac_info).setOnClickListener(this);

        findViewById(R.id.btn_bind_user).setOnClickListener(this);

        findViewById(R.id.btn_get_token).setOnClickListener(this);

        findViewById(R.id.btn_get_gate_info).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mac_info://获取网关信息用与账号密码登陆和绑定网关
                OhoSure.getInstance(MainActivity.this).getZigbeeInfo(new ZigBeeInfoResponseCallback() {
                    @Override
                    public void onSuccess(String host, int port, String mac) {
                        Alerter.create(MainActivity.this)
                                .setTitle("网关信息")
                                .setText(host + "\n" + port + "\n" + mac)
                                .setDuration(20000)
                                .show();
                        Log.w(TAG, host + ":" + port + ":" + mac);
                    }

                    @Override
                    public void onError(String msg) {
                        Alerter.create(MainActivity.this)
                                .setTitle("查询区域")
                                .setText(msg)
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case R.id.btn_get_token://获取token
                OhoSure.getInstance(MainActivity.this).getToken("18817354579", "123456", new InfoResponseCallback() {
                    @Override
                    public void infoMsg(String msg) {
                        Alerter.create(MainActivity.this)
                                .setTitle("获取token信息")
                                .setText(msg)
                                .setDuration(20000)
                                .show();
                    }
                });
                break;
            case R.id.btn_bind_user://绑定网关
                OhoSure.getInstance(MainActivity.this).bindUser("18817354579", "00124b000421f290",
                        Const.ACCESS_TOKEN, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("绑定网关")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case R.id.btn_login://外网登陆
                OhoSure.getInstance(MainActivity.this).initLogin("18817354579", "123456",
                        "00124b000421f290", "tcp://mqtt.ohosureproj.com:1883", new LoginResponseCallback() {
                            @Override
                            public void onSuccess() {
                                Alerter.create(MainActivity.this)
                                        .setTitle("外网登陆")
                                        .setText("外网登陆成功")
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case R.id.btn_inner_login://内网登陆
                OhoSure.getInstance(MainActivity.this).initInnerLogin(new InnerLoginResponseCallback() {
                    @Override
                    public void onSuccess() {
                        Alerter.create(MainActivity.this)
                                .setTitle("内网登陆")
                                .setText("内网登陆成功")
                                .setDuration(1000)
                                .show();
                    }

                    @Override
                    public void onError(String res) {
                        Alerter.create(MainActivity.this)
                                .setTitle("内网登陆")
                                .setText("网关无响应")
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case R.id.btn_get_gate_info://获取网关列表
                OhoSure.getInstance(MainActivity.this).getGateWaysInfo("18817354579",
                        Const.ACCESS_TOKEN, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("获取网关列表")
                                        .setText(msg)
                                        .setDuration(10000)
                                        .show();
                            }
                        });
                break;
            case R.id.btn_lose_connect:  //退出登陆
                OhoSure.getInstance(MainActivity.this).loginOut(new LoginResponseCallback() {
                    @Override
                    public void onSuccess() {
                        Alerter.create(MainActivity.this)
                                .setTitle("退出登陆")
                                .setText("退出登陆成功")
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case R.id.btn_getScene://获取场景
                OhoSure.getInstance(MainActivity.this).getAllScene(new SceneResponseCallback() {
                    @Override
                    public void getSceneResponse(String res) {
                        MLog.w(TAG, res);
                        Alerter.create(MainActivity.this)
                                .setTitle("场景列表")
                                .setText(res)
                                .setDuration(10000)
                                .show();
                    }
                });
                break;
            case R.id.btn_timingTask://获取定时任务列表:
                OhoSure.getInstance(MainActivity.this).getTimingTask(new TimingTaskResponseCallback() {
                    @Override
                    public void getTimingTaskResponse(String res) {
                        Alerter.create(MainActivity.this)
                                .setTitle("定时任务列表")
                                .setText(res)
                                .setDuration(10000)
                                .show();
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_responseJson://获取入网设备信息
                OhoSure.getInstance(MainActivity.this).getConfigResponseJson(new ConfigResponseCallback() {
                    @Override
                    public void getConfigResponse(String res) {
                        Alerter.create(MainActivity.this)
                                .setTitle("获取入网设备信息")
                                .setText(res)
                                .setDuration(10000)
                                .enableProgress(true)
                                .show();
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_control://新万能模块单控
                OhoSure.getInstance(MainActivity.this).getControl(164, 1, 2,
                        5, Const.toBytes(3), new ControlResponseCallback() {
                            @Override
                            public void getControlResponse(int i) {
                                Toast.makeText(MainActivity.this, i + "", Toast.LENGTH_SHORT).show();
                                MLog.w(TAG, i + "");
                            }
                        });
                break;
            case R.id.btn_addRoom://添加编辑区域
                OhoSure.getInstance(MainActivity.this).saveRoomData(0, "嘻嘻", new RoomResponseCallback() {
                    @Override
                    public void onSuccess(String res) {
                        Alerter.create(MainActivity.this)
                                .setTitle("添加编辑区域")
                                .setText(res)
                                .setDuration(1000)
                                .show();
                        MLog.w(TAG, res);
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_RemoveRoom://删除区域
                OhoSure.getInstance(MainActivity.this).removeRoomData(35, new RoomResponseCallback() {
                    @Override
                    public void onSuccess(String res) {
                        Alerter.create(MainActivity.this)
                                .setTitle("删除区域")
                                .setText(res)
                                .setDuration(1000)
                                .show();
                        MLog.w(TAG, res);
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
                break;
            case R.id.btn_QueryRoom://查询区域
                OhoSure.getInstance(MainActivity.this).queryRoomData(new QueryRoomResponseCallback() {
                    @Override
                    public void getRoomListResponse(List<DBRoomArea> list) {
                        Alerter.create(MainActivity.this)
                                .setTitle("查询区域")
                                .setText(list.toString())
                                .setDuration(10000)
                                .show();
                        MLog.w(TAG, list.toString());
                    }
                });
                break;
            default:
                break;
        }
    }
}
