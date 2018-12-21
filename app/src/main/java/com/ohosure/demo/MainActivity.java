package com.ohosure.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.OhoSure;
import com.ohosure.smart.core.callback.ConfigResponseCallback;
import com.ohosure.smart.core.callback.ControlResponseCallback;
import com.ohosure.smart.core.callback.InfoResponseCallback;
import com.ohosure.smart.core.callback.InnerLoginResponseCallback;
import com.ohosure.smart.core.callback.LoginResponseCallback;
import com.ohosure.smart.core.callback.QueryRoomResponseCallback;
import com.ohosure.smart.core.callback.RoomResponseCallback;
import com.ohosure.smart.core.callback.SceneResponseCallback;
import com.ohosure.smart.core.callback.ZigBeeInfoResponseCallback;
import com.ohosure.smart.database.devkit.log.MLog;
import com.ohosure.smart.zigbeegate.protocol.model.DBRoomArea;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    private adapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mList.add("账号密码登陆");
        mList.add("内网登陆");
        mList.add("获取token");
        mList.add("获取网关信息");
        mList.add("绑定网关");
        mList.add("获取网关列表");
        mList.add("解除绑定");
        mList.add("管理员确认用户网关绑定");
        mList.add("网关下用户列表");
        mList.add("修改网关别名");
        mList.add("成为管理员");
        mList.add("解除管理员");
        mList.add("获取入网设备信息");
        mList.add("获取场景");
        mList.add("开启场景");
        mList.add("新增或编辑某个场景");
        mList.add("获取某场景下的所有设备");
        mList.add("删除某个场景");
        mList.add("定时任务");
        mList.add("查询区域");
        mList.add("添加区域");
        mList.add("删除区域");
        mList.add("单控");
        mList.add("退出登陆");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new adapter(R.layout.adapter_item, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String item = (String) adapter.getItem(position);
        switch (item) {
            case "账号密码登陆"://账号密码登陆
                OhoSure.getInstance(MainActivity.this).initLogin("18817354579", "123456",
                        "00124b00128090fb", "tcp://mqtt.ohosureproj.com:1883", new LoginResponseCallback() {
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
            case "内网登陆"://内网登陆
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
            case "获取token"://获取token:
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
            case "获取网关信息"://获取网关信息
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
                                .setTitle("网关信息")
                                .setText(msg)
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case "绑定网关"://绑定网关
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
            case "获取网关列表"://获取网关列表:
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
            case "解除绑定"://解除绑定:
                OhoSure.getInstance(MainActivity.this).deleteBindUser("18817354579", "00124b000421f290",
                        Const.ACCESS_TOKEN, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("管理员解除用户网关绑定")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case "管理员确认用户网关绑定"://管理员确认用户网关绑定
                OhoSure.getInstance(MainActivity.this).verifyUser("18205099078", Const.ACCESS_TOKEN,
                        "00124b000421f290", new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("管理员确认用户绑定")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case "网关下用户列表"://网关下用户列表:
                OhoSure.getInstance(MainActivity.this).getGateUserList(Const.ACCESS_TOKEN, "00124b000421f290",
                        new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("网关下用户列表")
                                        .setText(msg)
                                        .setDuration(10000)
                                        .show();
                            }
                        });
                break;
            case "修改网关别名"://修改网关别名
                OhoSure.getInstance(MainActivity.this).modifyGatewayName("测试", "00124b000421f290",
                        Const.ACCESS_TOKEN, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("修改网关名称，只有管理员可以修改")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case "成为管理员"://成为管理员:
                OhoSure.getInstance(MainActivity.this).putAdmin("18817354579", Const.ACCESS_TOKEN, "00124b000421f290",
                        new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("成为管理员")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case "解除管理员"://解除管理员:
                OhoSure.getInstance(MainActivity.this).deleteGateAdmin("18817354579", Const.ACCESS_TOKEN,
                        "00124b000421f290", new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("解除管理员")
                                        .setText(msg)
                                        .setDuration(1000)
                                        .show();
                            }
                        });
                break;
            case "获取入网设备信息"://获取入网设备信息:
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
            case "获取场景"://获取场景:
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
            case "开启场景":
                OhoSure.getInstance(MainActivity.this).startScene(74, new InfoResponseCallback() {
                    @Override
                    public void infoMsg(String msg) {
                        Alerter.create(MainActivity.this)
                                .setTitle("开启场景")
                                .setText(msg)
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case "新增或编辑某个场景":
                OhoSure.getInstance(MainActivity.this).editScene(0, "全开", "",
                        0, 0, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                                Alerter.create(MainActivity.this)
                                        .setTitle("新增或编辑某个场景")
                                        .setText(msg)
                                        .setDuration(10000)
                                        .show();
                            }
                        });
                break;
            case "获取某场景下的所有设备":
//                OhoSure.getInstance(MainActivity.this).getSceneConfig(75);
                break;
            case "删除某个场景":
                OhoSure.getInstance(MainActivity.this).deleteScene(94, new InfoResponseCallback() {
                    @Override
                    public void infoMsg(String msg) {
                        Alerter.create(MainActivity.this)
                                .setTitle("删除某个场景")
                                .setText(msg)
                                .setDuration(1000)
                                .show();
                    }
                });
                break;
            case "定时任务"://定时任务:
//                OhoSure.getInstance(MainActivity.this).getTimingTask(new TimingTaskResponseCallback() {
//                    @Override
//                    public void getTimingTaskResponse(String res) {
//                        Alerter.create(MainActivity.this)
//                                .setTitle("定时任务列表")
//                                .setText(res)
//                                .setDuration(10000)
//                                .show();
//                        MLog.w(TAG, res);
//                    }
//                });
                break;
            case "查询区域"://查询区域:
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
            case "添加区域"://添加区域:
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
            case "删除区域"://删除区域:
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
            case "单控"://单控:
                OhoSure.getInstance(MainActivity.this).getControl(130, 1, 2,
                        5, Const.toBytes(3), new ControlResponseCallback() {
                            @Override
                            public void getControlResponse(int i) {
                                Toast.makeText(MainActivity.this, i + "", Toast.LENGTH_SHORT).show();
                                MLog.w(TAG, i + "");
                            }
                        });
                break;
            case "退出登陆"://退出登陆
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
            default:
                break;
        }
    }
}
