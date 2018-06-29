package com.ohosure.smart.core;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Keep;

import com.ohosure.smart.core.callback.ConfigResponseCallback;
import com.ohosure.smart.core.callback.ControlResponseCallback;
import com.ohosure.smart.core.callback.InnerLoginResponseCallback;
import com.ohosure.smart.core.callback.QueryRoomResponseCallback;
import com.ohosure.smart.core.callback.RoomResponseCallback;
import com.ohosure.smart.core.callback.SceneResponseCallback;
import com.ohosure.smart.core.callback.TimingTaskResponseCallback;
import com.ohosure.smart.database.HSmartProvider;
import com.ohosure.smart.database.SoloDBOperator;
import com.ohosure.smart.zigbeegate.protocol.H0101;
import com.ohosure.smart.zigbeegate.protocol.H0201;
import com.ohosure.smart.zigbeegate.protocol.H0241;
import com.ohosure.smart.zigbeegate.protocol.H0280;
import com.ohosure.smart.zigbeegate.protocol.H0801;
import com.ohosure.smart.zigbeegate.protocol.H0901;
import com.ohosure.smart.zigbeegate.protocol.H0941;
import com.ohosure.smart.zigbeegate.protocol.H0980;
import com.ohosure.smart.zigbeegate.protocol.HReceive;
import com.ohosure.smart.zigbeegate.protocol.HSend;
import com.ohosure.smart.zigbeegate.protocol.PrepareDomainHelper;
import com.ohosure.smart.zigbeegate.protocol.RequestConfig;
import com.ohosure.smart.zigbeegate.protocol.RequestTable;
import com.ohosure.smart.zigbeegate.protocol.business.Business;
import com.ohosure.smart.zigbeegate.protocol.model.DBRoomArea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
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
     * 账号密码登陆
     */
    public void initLogin() {
        Const.GATE_MAC = "00124b000421f290";//test9527
        //连接
        mApp.startLogin("18817354579", "123456");
    }

    /**
     * 内网登陆
     */
    public void initInnerLogin(final InnerLoginResponseCallback callback) {
        PrepareDomainHelper helper = new PrepareDomainHelper(context);
        helper.udpDiscoverGate();
        helper.setCallBackListener(new PrepareDomainHelper.NetCallBack() {
            @Override
            public void onSerchServer(int rescode, String description) {

            }

            @Override
            public void onBindUser(int rescode, String description) {

            }

            @Override
            public void onSubmiteRegister(int rescode, String description) {

            }

            @Override
            public void onDiscoverGate(int rescode, String description) {
                switch (rescode) {
                    case 0:
                        callback.onSuccess();
                        mApp.startLogin(Const.DEFAULT_BIND_USERNAME, Const.DEFAULT_BIND_PASSWORD);
                        break;
                    case 1:
                        callback.onError("家庭网关无响应");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onChangePassword(int rescode, String description) {

            }

            @Override
            public void onSearchGates(int rescode, String description, JSONArray jArray) {

            }

            @Override
            public void onSearchAccount(int rescode, String description, JSONObject jObj) {

            }
        });
    }

    /**
     * 退出登陆
     */
    public void loginOut() {
        mApp.logout();
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
