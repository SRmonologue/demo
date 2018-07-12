#### 权限
在你的清单文件中加入如下权限
```
<!-- 连接网络权限 -->
<uses-permission android:name="android.permission.INTERNET"/>
<!-- 查看网络状态 -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<!--wifi获取组播-->
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE"/>
<!-- 防止设备休眠 -->
<uses-permission android:name="android.permission.WAKE_LOCK"/>
<!-- 获取机型信息权限 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
```
在安卓6.0的手机上要给予相应的权限适配
在你的清单文件中添加
```
<service 
     android:name="com.ohosure.smart.net.CoreService"
     android:exported="false">
     <intent-filter android:priority="1000">
     <action android:name="devkit.mj.net.CoreService.action"/>
     </intent-filter>
</service>
<service android:name="org.eclipse.paho.android.service.MqttService"/>
```
在你的`libs`文件夹中添加我们提供的`aar`包：`ohosure-release.aar`
然后在你app的`build.gradle`中添加如下依赖，然后编译运行
```
 implementation(name: 'ohosure-release', ext: 'aar')
 implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0'
 implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
```
在你的清单文件中的`application`的`name`标签处引用`OsApplication`，如果已经有自己的`application`，可以使用你的`application`继承`OsApplication`，然后调用` initOhosure(getApplicationContext());`进行初始化
####Android API
___
#####API - initLogin
账号密码登陆，在自己的`Application`中的`onCreate`中调用
#####接口定义
```
OhoSure.getInstance(context).initLogin(name, password, mac, host, new LoginResponseCallback() {
                            @Override
                            public void onSuccess() {
                            }
                        });
```
#####参数说明
* context：应用的`ApplicationContext`
* name：用户名
* password：密码
* mac：网关id
* host：主机地址

#####API - initInnerLogin
内网登陆
#####接口定义
```
OhoSure.getInstance(context).initInnerLogin(new InnerLoginResponseCallback() {
                    @Override
                    public void onSuccess() {
                        MLog.w(TAG, "内网登陆成功");
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext

#####API - getToken
获取token信息
#####接口定义
```
 OhoSure.getInstance(context).getToken(name, password, new InfoResponseCallback() {
                    @Override
                    public void infoMsg(String msg) {
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* password：密码

#####API - loginOut
退出登陆状态，断开与网关的连接状态
#####接口定义
```
OhoSure.getInstance(context).loginOut();
```
#####参数说明
* context：应用的ApplicationContext

#####API -  getZigbeeInfo
获取网关信息
#####接口定义
```
OhoSure.getInstance(context).getZigbeeInfo(new ZigBeeInfoResponseCallback() {
                    @Override
                    public void onSuccess(String host, int port, String mac{
                        Log.w(TAG, host + ":" + port + ":" + mac);
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
```
#####返回参数说明
* context：应用的ApplicationContext
* host：主机地址
* port：端口号
* mac：网关id

#####API - bindUser
绑定网关到云端，网关下的成员中如果有管理员则需要管理员同意后才能控制网关下的设备，如果没有管理员则直接绑定成功
#####接口定义
```
 OhoSure.getInstance(context).bindUser(name, mac, token, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* mac：网关id
* token：token信息

#####API - getGateWaysInfo
获取网关列表，包含网关的信息
#####接口定义
```
OhoSure.getInstance(context).getGateWaysInfo(name, token, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* token：token信息

#####API -  deleteBindUser
解除绑定，只有管理员有权限可以解除绑定
#####接口定义
```
 OhoSure.getInstance(context).deleteBindUser(name, mac, token, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* mac：网关id
* token：token信息

####API - verifyUser
管理员确认用户网关绑定，只有管理员有权限
#####接口定义
```
 OhoSure.getInstance(context).verifyUser(name, token, mac, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* token：token信息
* mac：网关id

#####API - getGateUserList
网关下用户列表，包含用户的信息
#####接口定义
```
 OhoSure.getInstance(context).getGateUserList(token, mac, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* token：token信息
* mac：网关id

#####API - modifyGatewayName
修改网关别名，只有管理员有权限修改
#####接口定义
```
OhoSure.getInstance(context).modifyGatewayName(gatewayname, mac, token, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* gatewayname：要修改的网关别名
* mac：网关id
* token：token信息

#####API - putAdmin
成为管理员，普通用户成为管理员
#####接口定义
```
OhoSure.getInstance(context).putAdmin(name, token, mac, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数定义
* context：应用的ApplicationContext
* name：用户名
* token：token信息
* mac：网关id

#####API - deleteGateAdmin
解除管理员，管理员解除自己的管理员身份
#####接口定义
```
 OhoSure.getInstance(context).deleteGateAdmin(name, token, mac, new InfoResponseCallback() {
                            @Override
                            public void infoMsg(String msg) {
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* name：用户名
* token：token信息
* mac：网关id


#####API - getAllScene
获取所有的场景信息
#####接口定义
```
OhoSure.getInstance(context).getAllScene(new SceneResponseCallback() {
                    @Override
                    public void getSceneResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext

#####API - deleteScene
删除某个场景
#####接口定义
```
OhoSure.getInstance(context).deleteScene(sceneId, new InfoResponseCallback() {
                    @Override
                    public void infoMsg(String msg) {
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext
* sceneId：场景的id

#####API - getTimingTask
获取所有的定时任务
#####接口定义
```
OhoSure.getInstance(context).getTimingTask(new TimingTaskResponseCallback() {
                    @Override
                    public void getTimingTaskResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext

#####API - getConfigResponseJson
获取入网设备信息，最长时间延迟在20s左右
#####接口定义
```
 OhoSure.getInstance(context).getConfigResponseJson(new ConfigResponseCallback() {
                    @Override
                    public void getConfigResponse(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext

#####API - getControl
新万能模块单控，返回值1代表开状态，返回值0代表关状态
#####接口定义
```
  OhoSure.getInstance(context).getControl(deviceId, deviceType, originalType,
                        , Const.toBytes(3), new ControlResponseCallback() {
                            @Override
                            public void getControlResponse(int i) {
                                MLog.w(TAG, i + "");
                            }
                        });
```
#####参数说明
* context：应用的ApplicationContext
* deviceId：设备的id
* deviceType：设备的类型
* originalType：通道类型

#####API - saveRoomData
添加区域或修改区域名称
```
 OhoSure.getInstance(context).saveRoomData(roomId,room, new SaveRoomResponseCallback() {
                    @Override
                    public void onSuccess(String res) {
                        MLog.w(TAG, res);
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext
* roomId：新增区域时传0即可，修改区域时传入该区域的id
* room：区域的名称

#####API - queryRoomData
查询已有的区域，以集合的方式返回数据
#####接口定义
```
 OhoSure.getInstance(context).queryRoomData(new QueryRoomResponseCallback() {
                    @Override
                    public void getRoomListResponse(List<DBRoomArea> list) {
                        MLog.w(TAG, list.toString());
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext

#####API - removeRoomData
删除区域
#####接口定义
```
 OhoSure.getInstance(context).removeRoomData(roomId, new RoomResponseCallback() {
                    @Override
                    public void onSuccess(String res) {
                        MLog.w(TAG, res);
                    }

                    @Override
                    public void onError(String res) {
                        MLog.w(TAG, res);
                    }
                });
```
#####参数说明
* context：应用的ApplicationContext
* roomId：要删除区域的id


