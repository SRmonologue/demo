package com.ohosure.smart.core;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Keep;
import android.util.Log;

import com.ohosure.smart.core.callback.ConfigResponseCallback;
import com.ohosure.smart.core.callback.ControlResponseCallback;
import com.ohosure.smart.core.callback.InfoResponseCallback;
import com.ohosure.smart.core.callback.InnerLoginResponseCallback;
import com.ohosure.smart.core.callback.LoginResponseCallback;
import com.ohosure.smart.core.callback.QueryRoomResponseCallback;
import com.ohosure.smart.core.callback.RoomResponseCallback;
import com.ohosure.smart.core.callback.SceneResponseCallback;
import com.ohosure.smart.core.callback.TimingTaskResponseCallback;
import com.ohosure.smart.core.callback.ZigBeeInfoResponseCallback;
import com.ohosure.smart.database.HSmartProvider;
import com.ohosure.smart.database.SoloDBOperator;
import com.ohosure.smart.net.RetrofitUtil;
import com.ohosure.smart.zigbeegate.protocol.H0101;
import com.ohosure.smart.zigbeegate.protocol.H0201;
import com.ohosure.smart.zigbeegate.protocol.H0202;
import com.ohosure.smart.zigbeegate.protocol.H0241;
import com.ohosure.smart.zigbeegate.protocol.H0242;
import com.ohosure.smart.zigbeegate.protocol.H0262;
import com.ohosure.smart.zigbeegate.protocol.H0280;
import com.ohosure.smart.zigbeegate.protocol.H0801;
import com.ohosure.smart.zigbeegate.protocol.H0901;
import com.ohosure.smart.zigbeegate.protocol.H0902;
import com.ohosure.smart.zigbeegate.protocol.H0941;
import com.ohosure.smart.zigbeegate.protocol.H0942;
import com.ohosure.smart.zigbeegate.protocol.H0962;
import com.ohosure.smart.zigbeegate.protocol.H0980;
import com.ohosure.smart.zigbeegate.protocol.HReceive;
import com.ohosure.smart.zigbeegate.protocol.HSend;
import com.ohosure.smart.zigbeegate.protocol.PrepareDomainHelper;
import com.ohosure.smart.zigbeegate.protocol.RequestConfig;
import com.ohosure.smart.zigbeegate.protocol.RequestTable;
import com.ohosure.smart.zigbeegate.protocol.business.Business;
import com.ohosure.smart.zigbeegate.protocol.model.DBRoomArea;
import com.ohosure.smart.zigbeegate.protocol.model.HAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 9527 on 2018/6/20.
 */
@Keep
public class OhoSure {
    private Context context;
    private static OhoSure sOhoSure;
    private OsApplication mApp;
    private Business mBusiness;

    private OhoSure(Context context) {
        this.context = context.getApplicationContext();
        mApp = (OsApplication) context.getApplicationContext();
    }

    public static OhoSure getInstance(Context context) {
        if (sOhoSure == null) {
            synchronized (OhoSure.class) {
                if (sOhoSure == null) {
                    sOhoSure = new OhoSure(context);
                }
            }
        }
        return sOhoSure;
    }

    /**
     * 获取网关信息
     *
     * @param callback
     */
    public void getZigbeeInfo(final ZigBeeInfoResponseCallback callback) {
        PrepareDomainHelper helper = new PrepareDomainHelper(context);
        helper.udpDiscoverGate();
        helper.setCallBackListener(new PrepareDomainHelper.NetCallBack() {
            @Override
            public void onDiscoverGate(int rescode, String description) {
                callback.onSuccess(Const.SERVER, Const.PORT, Const.GATE_MAC);
            }
        });
    }

    /**
     * 获取token
     *
     * @param name
     * @param password
     */
    public void getToken(String name, String password, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().getToken(name, password, "password", "ohosure")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        JSONObject jsonObject = null;
                        try {
                            if (response.code() == 200) {
                                jsonObject = new JSONObject(response.body().string());
                                Const.ACCESS_TOKEN = jsonObject.optString("access_token");
                                callback.infoMsg(Const.ACCESS_TOKEN);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                    }
                });
    }

    /**
     * 绑定网关
     *
     * @param name
     * @param mac
     * @param token
     */
    public void bindUser(final String name, String mac, String token, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().postBinding("Bearer " + token, name, mac).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            callback.infoMsg("绑定成功");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                    }
                });
    }

    /**
     * 管理员解除用户网关绑定
     *
     * @param name
     * @param mac
     * @param token
     */
    public void deleteBindUser(String name, String mac, String token, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().deleteUser("Bearer " + token, name, mac).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callback.infoMsg("解除绑定成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 修改网关名称，只有管理员可以修改
     *
     * @param gatewayname
     * @param mac
     * @param token
     */
    public void modifyGatewayName(String gatewayname, String mac, String token, final InfoResponseCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gatewayName", gatewayname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RetrofitUtil.createHttpApiInstance().putGatewayName("Bearer " + token, mac, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callback.infoMsg("修改成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 获取网关下用户列表
     *
     * @param token
     * @param mac
     */
    public void getGateUserList(String token, String mac, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().getGateUserList("Bearer" + token, mac).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        callback.infoMsg(jsonArray.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 获取网关列表
     *
     * @param name
     * @param token
     */
    public void getGateWaysInfo(String name, String token, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().getGateWaysInfo(name, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        callback.infoMsg(jsonArray.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * /成为管理员
     *
     * @param username
     * @param token
     * @param mac
     * @param callback
     */
    public void putAdmin(String username, String token, String mac, final InfoResponseCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        RetrofitUtil.createHttpApiInstance().putAdmin("Bearer " + token, mac, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callback.infoMsg("设置成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 解除管理员
     *
     * @param username
     * @param token
     * @param mac
     * @param callback
     */
    public void deleteGateAdmin(String username, String token, String mac, final InfoResponseCallback callback) {
        RetrofitUtil.createHttpApiInstance().deleteAdmin("Bearer " + token, mac, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callback.infoMsg("解除成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 管理员确认用户绑定
     *
     * @param username
     * @param token
     * @param mac
     * @param callback
     */
    public void verifyUser(String username, String token, String mac, final InfoResponseCallback callback) {
        JSONObject result = new JSONObject();
        try {
            result.put("roleName", "guest");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        RetrofitUtil.createHttpApiInstance().verifyUser("Bearer " + token, mac, username, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    callback.infoMsg("设置成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 账号密码登陆
     */
    public void initLogin(String name, String password, String mac, String host, final LoginResponseCallback callback) {
        Const.GATE_MAC = mac;
        Const.MQTT_HOST = host;
        mApp.startLogin(name, password);
        mBusiness = new Business() {
            @Override
            public void onResponseAuth(int rescode) {
                super.onResponseAuth(rescode);
                if (rescode == 0) {
                    callback.onSuccess();
                }
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);
    }

    /**
     * 内网登陆
     */
    public void initInnerLogin(final InnerLoginResponseCallback callback) {
        PrepareDomainHelper helper = new PrepareDomainHelper(context);
        helper.udpDiscoverGate();
        helper.setCallBackListener(new PrepareDomainHelper.NetCallBack() {
            @Override
            public void onDiscoverGate(int rescode, String description) {
                switch (rescode) {
                    case 0:
                        callback.onSuccess();
                        mApp.startLogin(Const.DEFAULT_BIND_USERNAME, Const.DEFAULT_BIND_PASSWORD);
                        break;
                    case 1:
                        callback.onError("网关无响应");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 退出登陆
     */
    public void loginOut(final LoginResponseCallback callback) {
        mApp.logout();
        mBusiness = new Business() {
            @Override
            public void onResponseCancel() {
                super.onResponseCancel();
                callback.onSuccess();
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);
    }

    /**
     * 获取所有场景
     *
     * @param callback
     */
    public void getAllScene(final SceneResponseCallback callback) {

        mBusiness = new Business() {
            @Override
            public void onRequestTable(String res) {
                super.onRequestTable(res);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String sceneAll = jsonObject.optString("queryInfo");
                if (!RequestTable.INFO_SCENEALL.equalsIgnoreCase(sceneAll)) {
                    return;
                }
                callback.getSceneResponse(res);
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);

        mApp.sendRequest(new RequestTable(RequestTable.httpBody(Const.CLIENT_SESSION,
                RequestTable.INFO_SCENEALL)));
    }

    /**
     * 开启场景
     *
     * @param sceneId
     * @param callback
     */
    public void startScene(int sceneId, final InfoResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0262Response(HReceive receive) {
                super.on0262Response(receive);
                H0262 h0262 = (H0262) receive;
                h0262.analyze();
                if (h0262.getResultCode() == 0) {
                    callback.infoMsg("开启场景成功");
                } else {
                    callback.infoMsg("开启场景失败");
                }
            }
        };
        mApp.addBusinessObserver(mBusiness);
        HSend hSend = new H0962(sceneId);
        mApp.sendControl(hSend);
    }

    /**
     * 新增或编辑场景
     *
     * @param sceneId
     * @param sceneName
     * @param desc
     * @param sceneType
     * @param overviewId
     */
    public void editScene(int sceneId, String sceneName, String desc, int sceneType, int overviewId,
                          final InfoResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0202Response(HReceive receive) {
                super.on0202Response(receive);
                H0202 h0202 = (H0202) receive;
                h0202.analyze();
                if (h0202.getResultCode() == 0) {
                    callback.infoMsg("操作成功");
                } else if (h0202.getResultCode() == 100) {
                    callback.infoMsg("该场景名称已经存在");
                }
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);
        try {
            HSend hSend = new H0902(sceneId, sceneName, desc, 0, sceneType, overviewId);
            mApp.sendControl(hSend);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某场景下的所有设备
     * @param sceneId
     */
    public void getSceneConfig(int sceneId) {
        mBusiness = new Business() {
            @Override
            public void onRequestTable(String res) {
                super.onRequestTable(res);
                Log.w("gz", res.toString());
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);
        mApp.sendRequest(new RequestTable(RequestTable.httpBody(Const.CLIENT_SESSION,
                RequestTable.INFO_SCENECONFIG, new HAction(sceneId, Const.ACTION_SCENE))));
    }

    /**
     * 删除某个场景
     *
     * @param sceneId
     * @param callback
     */
    public void deleteScene(int sceneId, final InfoResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0242Response(HReceive receive) {
                super.on0242Response(receive);
                H0242 h0242 = (H0242) receive;
                h0242.analyze();
                if (h0242.getResultCode() == 0) {
                    callback.infoMsg("删除成功");
                }
            }
        };

        mApp.addBusinessObserver(mBusiness);
        HSend hSend = new H0942(sceneId);
        mApp.sendControl(hSend);
    }

    /**
     * 获取所有定时任务
     *
     * @param callback
     */
    public void getTimingTask(final TimingTaskResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void onRequestTable(String res) {
                super.onRequestTable(res);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String task = jsonObject.optString("queryInfo");
                if (!RequestTable.INFO_TASK_TODAY.equalsIgnoreCase(task)) {
                    return;
                }
                callback.getTimingTaskResponse(res);
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);

        mApp.sendRequest(new RequestTable(RequestTable.httpBody(Const.CLIENT_SESSION,
                RequestTable.INFO_TASK_TODAY)));
    }

    /**
     * 获取所有入网设备信息
     *
     * @param callback
     */
    public void getConfigResponseJson(final ConfigResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0280Response(HReceive receive) {
                super.on0280Response(receive);
                H0280 h0280 = (H0280) receive;
                if (h0280.getResultCode() == 0) {
                    mApp.sendRequest(new RequestConfig(Const.CLIENT_SESSION));
                }
            }

            @Override
            public void onResponseConfigJson(String res) {
                super.onResponseConfigJson(res);
                callback.getConfigResponse(res);
                mBusiness = null;
            }
        };

        mApp.addBusinessObserver(mBusiness);

        mApp.sendControl(new H0980(1));
    }

    /**
     * 新万能模块单控
     *
     * @param deviceId
     * @param deviceType
     * @param originalType
     * @param featureType
     * @param value
     * @param callback
     */
    public void getControl(final long deviceId, final int deviceType, final int originalType,
                           final int featureType,
                           byte[] value, final ControlResponseCallback callback) {

        mBusiness = new Business() {
            @Override
            public void on0101Response(HReceive receive) {
                super.on0101Response(receive);
                H0101 h0101 = (H0101) receive;
                callback.getControlResponse(Const.byteArrayToNumber(h0101.getValue(),
                        h0101.getLengthOfValue()));
                mBusiness = null;
            }
        };
        mApp.addBusinessObserver(mBusiness);

        HSend hsend = new H0801(deviceId, deviceType, originalType, featureType, value);
        mApp.sendControl(hsend);
    }

    /**
     * 增加编辑区域
     *
     * @param roomId
     * @param room
     * @param callback
     */
    public void saveRoomData(int roomId, final String room, final RoomResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0201Response(HReceive receive) {
                super.on0201Response(receive);
                H0201 h0201 = (H0201) receive;
                h0201.analyze();
                if (h0201.getResultCode() == 0) {
                    callback.onSuccess("保存成功");
                    SoloDBOperator.getInstance().insertOrUpdatePtRoomArea(
                            h0201.getRoomAreaId(), room, "", 0);
                } else {
                    if (h0201.getResultCode() == 100) {
                        callback.onError("已存在同名区域划分");
                        return;
                    }
                }
                mBusiness = null;
            }
        };

        mApp.addBusinessObserver(mBusiness);
        HSend hsend = null;
        try {
            hsend = new H0901(roomId, room, "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mApp.sendControl(hsend);
    }

    /**
     * 删除区域
     *
     * @param roomId
     * @param callback
     */
    public void removeRoomData(final int roomId, final RoomResponseCallback callback) {
        mBusiness = new Business() {
            @Override
            public void on0241Response(HReceive receive) {
                super.on0241Response(receive);
                H0241 h0241 = (H0241) receive;
                h0241.analyze();
                if (h0241.getResultCode() == 0) {
                    callback.onSuccess("删除成功");
                    SoloDBOperator.getInstance().deleteFromPtRoomArea(roomId);
                } else {
                    callback.onError("删除失败");
                }
            }
        };

        mApp.addBusinessObserver(mBusiness);
        HSend hSend = new H0941(roomId);
        mApp.sendControl(hSend);
    }

    /**
     * 查询区域
     *
     * @param callback
     */
    public void queryRoomData(QueryRoomResponseCallback callback) {
        List<DBRoomArea> mList = new ArrayList<>();
        mList.clear();
        Cursor cursor = context.getContentResolver().query(HSmartProvider.MetaData.RoomArea.CONTENT_URI,
                new String[]{
                        HSmartProvider.MetaData.RoomArea._ID,
                        HSmartProvider.MetaData.RoomArea.ROOM_AREA_ID,
                        HSmartProvider.MetaData.RoomArea.ROOM_AREA_NAME,
                        HSmartProvider.MetaData.RoomArea.ROOM_AREA_DESCRIPTION
                }, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                mList.add(new DBRoomArea(cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), 0, 0));
            }
            cursor.close();
        }
        callback.getRoomListResponse(mList);
    }
}
