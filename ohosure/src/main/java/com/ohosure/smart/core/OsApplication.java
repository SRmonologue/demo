package com.ohosure.smart.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.ohosure.smart.core.communication.HttpOutMessage;
import com.ohosure.smart.database.HSmartProvider;
import com.ohosure.smart.database.devkit.log.MLog;
import com.ohosure.smart.database.devkit.sqlite.DBHelper;
import com.ohosure.smart.database.devkit.utils.BitReader;
import com.ohosure.smart.database.devkit.utils.DigitUtil;
import com.ohosure.smart.net.AbstractBusinessCallback;
import com.ohosure.smart.net.ConnectInfo;
import com.ohosure.smart.net.CoreService;
import com.ohosure.smart.net.HttpMessage;
import com.ohosure.smart.zigbeegate.protocol.H0100;
import com.ohosure.smart.zigbeegate.protocol.H0101;
import com.ohosure.smart.zigbeegate.protocol.H0102;
import com.ohosure.smart.zigbeegate.protocol.H0201;
import com.ohosure.smart.zigbeegate.protocol.H0202;
import com.ohosure.smart.zigbeegate.protocol.H0203;
import com.ohosure.smart.zigbeegate.protocol.H0204;
import com.ohosure.smart.zigbeegate.protocol.H0205;
import com.ohosure.smart.zigbeegate.protocol.H0206;
import com.ohosure.smart.zigbeegate.protocol.H0207;
import com.ohosure.smart.zigbeegate.protocol.H0208;
import com.ohosure.smart.zigbeegate.protocol.H0209;
import com.ohosure.smart.zigbeegate.protocol.H020a;
import com.ohosure.smart.zigbeegate.protocol.H020b;
import com.ohosure.smart.zigbeegate.protocol.H0211;
import com.ohosure.smart.zigbeegate.protocol.H0212;
import com.ohosure.smart.zigbeegate.protocol.H0213;
import com.ohosure.smart.zigbeegate.protocol.H0214;
import com.ohosure.smart.zigbeegate.protocol.H0215;
import com.ohosure.smart.zigbeegate.protocol.H0216;
import com.ohosure.smart.zigbeegate.protocol.H0217;
import com.ohosure.smart.zigbeegate.protocol.H0221;
import com.ohosure.smart.zigbeegate.protocol.H0222;
import com.ohosure.smart.zigbeegate.protocol.H0223;
import com.ohosure.smart.zigbeegate.protocol.H0230;
import com.ohosure.smart.zigbeegate.protocol.H0231;
import com.ohosure.smart.zigbeegate.protocol.H0241;
import com.ohosure.smart.zigbeegate.protocol.H0242;
import com.ohosure.smart.zigbeegate.protocol.H0243;
import com.ohosure.smart.zigbeegate.protocol.H0244;
import com.ohosure.smart.zigbeegate.protocol.H0245;
import com.ohosure.smart.zigbeegate.protocol.H0246;
import com.ohosure.smart.zigbeegate.protocol.H0260;
import com.ohosure.smart.zigbeegate.protocol.H0261;
import com.ohosure.smart.zigbeegate.protocol.H0262;
import com.ohosure.smart.zigbeegate.protocol.H0265;
import com.ohosure.smart.zigbeegate.protocol.H0280;
import com.ohosure.smart.zigbeegate.protocol.H0281;
import com.ohosure.smart.zigbeegate.protocol.H0282;
import com.ohosure.smart.zigbeegate.protocol.H0283;
import com.ohosure.smart.zigbeegate.protocol.H0284;
import com.ohosure.smart.zigbeegate.protocol.H02a1;
import com.ohosure.smart.zigbeegate.protocol.H02a2;
import com.ohosure.smart.zigbeegate.protocol.H02a3;
import com.ohosure.smart.zigbeegate.protocol.H02b0;
import com.ohosure.smart.zigbeegate.protocol.H02b1;
import com.ohosure.smart.zigbeegate.protocol.H0960;
import com.ohosure.smart.zigbeegate.protocol.H0965;
import com.ohosure.smart.zigbeegate.protocol.H09b0;
import com.ohosure.smart.zigbeegate.protocol.HProduct;
import com.ohosure.smart.zigbeegate.protocol.HSend;
import com.ohosure.smart.zigbeegate.protocol.HSmartDevice;
import com.ohosure.smart.zigbeegate.protocol.RequestAuth;
import com.ohosure.smart.zigbeegate.protocol.RequestConfig;
import com.ohosure.smart.zigbeegate.protocol.RequestSend;
import com.ohosure.smart.zigbeegate.protocol.RequestUpdate;
import com.ohosure.smart.zigbeegate.protocol.business.Business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ohosure.smart.core.Const.DEFAULT_BIND_USERNAME;


@SuppressLint("SimpleDateFormat")
public class OsApplication extends Application {

    private static final String TAG = OsApplication.class.getSimpleName();
    /**
     * Application实例
     */
    public static Context context;

    /**
     * 业务回调列表
     */
    //    ArrayList<Business> listBusiness = new ArrayList<Business>();
    Business mBusiness = new Business();

    /**
     * 固定线程池length=4，辅助Activity获取各种数据
     */
    private ExecutorService extFixedThreadPool;
    /**
     * 震动
     */
    Vibrator vibrator;
    //用户个性化配置
    SharedPreferences sharedPreferences;
    //后台服务代理
    public CoreService.BusinessManager businessManager;

    public void initOhosure(Context context) {
        this.context = context;
        Intent intentAction = new Intent();
        intentAction.setAction("devkit.mj.net.CoreService.action");
        intentAction.setPackage(context.getPackageName());
        context.bindService(intentAction, conn, Service.BIND_AUTO_CREATE);
    }

    public static Context getContext() {
        return context;
    }

    public ExecutorService getExtFixedThreadPool() {
        return this.extFixedThreadPool;
    }

    public void executeRunnable(Runnable runnable) {
        extFixedThreadPool.execute(runnable);
    }


    public boolean isLogin() {
        if (businessManager == null)
            return false;
        return businessManager.isLogin();
    }

    public boolean isInternalNet() {
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "");
        if (name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {
            //内网登陆
            return true;
        } else {
            //外网登陆
            return false;
        }

    }

    public String loginName() {
        if (businessManager == null)
            return "";
        return businessManager.getConnectInfo().getName();
    }

    public void logout() {
        if (businessManager == null)
            return;
        businessManager.cancelLogin();
    }

    public void startLogin(String name, String password) {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name", name);
        edit.putString("password", password);
        edit.commit();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        Const.CLIENT_ID = "MQTTService" + DEVICE_ID;
        ConnectInfo connectInfo = new ConnectInfo();
        if (name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {
            //内网登陆
            connectInfo.setHost(Const.SERVER);
        } else {
            //外网登陆
            connectInfo.setHost(Const.MQTT_HOST);
        }
        connectInfo.setPort(Const.PORT);
        connectInfo.setMac(Const.GATE_MAC);
        connectInfo.setClientId(Const.CLIENT_ID);
        connectInfo.setName(name);
        connectInfo.setPassword(password);
        Intent intent = new Intent();
        intent.putExtra("ConnectInfo", connectInfo);
        intent.setClass(context, CoreService.class);
        context.startService(intent);
    }


    private AbstractBusinessCallback serverCallback = new AbstractBusinessCallback() {
        @Override
        public void onMqttFeedback(String mqttMessage) {//外网mqtt

            mparse(mqttMessage);
        }

        //服务器Response过来的
        @Override
        public void onServerFeedback(HttpMessage inMessage) {//方法最后还是调用mparse()方法
            String matchURI = inMessage.getHeaders().get("match-request");
            MLog.i("APP", "---------matchURI--------->>:" + matchURI);
            if (matchURI.indexOf("requestUpdateDescription") > 0) {
                JSONObject json;
                try {
                    json = new JSONObject(inMessage.getResponse());
                    //                    for (int i = 0; i < listBusiness.size(); i++) {
                    //                        listBusiness.get(i).onRequestUpdateDescription(json);
                    //                    }
                    mBusiness.onRequestUpdateDescription(json);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            } else
                // 检查配置信息更新
                if (matchURI.indexOf("requestUpdate") > 0) {
                    JSONObject jsonObject;
                    int updateMode = 0;
                    int result = 0;
                    try {

                        jsonObject = new JSONObject(inMessage.getResponse());
                        result = jsonObject.optInt("result");
                        if (result != 0) {
                            reportResultCode(RequestSend.class.getSimpleName()
                                    + "prev", result);
                        }
                        jsonObject = jsonObject.getJSONObject("updateInfo");
                        updateMode = jsonObject.getInt("configUpdateMode");

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    //                    for (int i = 0; i < listBusiness.size(); i++) {
                    //                        listBusiness.get(i).onResponseUpdate(updateMode);
                    //                    }
                    mBusiness.onResponseUpdate(updateMode);
                    if (updateMode != 0) {
                        sendRequest(new RequestConfig(Const.CLIENT_SESSION));
                    } else {
                        checkDeviceStatus();
                    }
                } else
                /**
                 * 设备配置更新
                 */
                    if (matchURI.indexOf("requestConfig") > 0) {

                        SQLiteDatabase db = null;
                        JSONObject jconfig = null;
                        try {
                            JSONObject jobj = new JSONObject(
                                    inMessage.getResponse());
                            // 更新失败分析
                            int res = jobj.optInt("result");
                            if (res != 0) {
                                reportResultCode(
                                        RequestConfig.class.getSimpleName(), res);

                                //                                for (int i = 0; i < listBusiness.size(); i++) {
                                //                                    listBusiness.get(i).onResponseConfig(1);
                                //                                }
                                mBusiness.onResponseConfig(1);

                                return;
                            }
                            MLog.d(TAG, "开始配置更新" + System.currentTimeMillis());
                            mBusiness.onResponseConfigJson(inMessage.getResponse());
                            ContentProvider cp = getContentResolver()
                                    .acquireContentProviderClient(
                                            HSmartProvider.class.getName())
                                    .getLocalContentProvider();

                            DBHelper dbHelper = ((HSmartProvider) cp).getDbHelper();
                            db = dbHelper.getWritableDatabase();
                            db.execSQL("delete from pt_home ");
                            db.execSQL("delete from pt_roomArea");
                            db.execSQL("delete from pt_product");
                            db.execSQL("delete from pt_controlDevice");
                            db.beginTransaction();
                            db.execSQL("delete from sqlite_sequence");
                            jconfig = jobj.getJSONObject("config");

                            JSONArray arrayData;
                            JSONObject obj;
                            arrayData = jconfig.getJSONArray("home");
                            for (int i = 0; i < arrayData.length(); i++) {
                                obj = arrayData.getJSONObject(i);
                                db.execSQL(
                                        "insert into pt_home(gate_mac,last_time,configuration,gate_version) values(?,?,?,?)",
                                        new Object[]{obj.getString("gate_mac"),
                                                obj.getString("last_time"),
                                                obj.getString("configuration"),
                                                obj.getString("gate_version")});
                                MLog.d("APP", "网关版本号" + obj.getString("gate_version"));

                            }
                            arrayData = jconfig.getJSONArray("roomArea");
                            for (int i = 0; i < arrayData.length(); i++) {
                                obj = arrayData.getJSONObject(i);

                                db.execSQL(
                                        "insert into pt_roomArea(room_area_id,room_area_name,room_area_description,floor) values(?,?,?,?)",
                                        new Object[]{
                                                obj.getString("room_area_id"),
                                                obj.getString("room_area_name"),
                                                obj.getString("room_area_description"),
                                                obj.getString("floor")});
                            }

                            arrayData = jconfig.getJSONArray("product");
                            for (int i = 0; i < arrayData.length(); i++) {
                                obj = arrayData.getJSONObject(i);
                                db.execSQL(
                                        "insert into pt_product(product_id,product_type,product_name,room_area_id,product_state,sw_version,hw_version) values(?,?,?,?,?,?,?)",
                                        new Object[]{obj.getString("product_id"),
                                                obj.getString("product_type"),
                                                obj.getString("product_name"),
                                                obj.getString("room_area_id"),
                                                obj.optInt("product_state"),
                                                obj.optInt("sw_version"),
                                                obj.optInt("hw_version")

                                        });

                            }
                            arrayData = jconfig.getJSONArray("controlDevice");
                            for (int i = 0; i < arrayData.length(); i++) {
                                obj = arrayData.getJSONObject(i);
                                db.execSQL(
                                        "insert into pt_controlDevice(control_device_id,control_device_name,room_area_id,control_device_type,product_id) values(?,?,?,?,?)",
                                        new Object[]{
                                                obj.getString("control_device_id"),
                                                obj.getString("control_device_name"),
                                                obj.getString("room_area_id"),
                                                obj.getString("control_device_type"),
                                                obj.getString("product_id")
                                        });

                            }
                            db.setTransactionSuccessful();
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onResponseConfig(0);
                            // 通知应用层刷新数据

                            // listBusiness.get(i).onDataRefresh();
                            //                            }
                            mBusiness.onResponseConfig(0);
                            MLog.v(TAG, "配置更新成功" + System.currentTimeMillis());

                            //开始同步设备状态数据
                            checkDeviceValue();

                        } catch (Exception e) {
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onResponseConfig(1);
                            //                            }
                            mBusiness.onResponseConfig(1);
                            reportResultCode(RequestConfig.class.getSimpleName(), 404);
                            MLog.e(TAG, "配置更新失败");
                            e.printStackTrace();

                        } finally {
                            if (db != null)
                                db.endTransaction();
                        }

                    } else if (matchURI.indexOf("requestTable") > 0) {
                        //TODO
                        mBusiness.onRequestTable(inMessage.getResponse());
                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).onRequestTable(inMessage.getResponse());
                        //                        }


                    } else if (matchURI.indexOf("requestEnergy") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestEnergy(json);
                            //                            }
                            mBusiness.onRequestEnergy(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestDeviceList") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestDeviceList(json);
                            //                            }
                            mBusiness.onRequestDeviceList(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestAddDevice") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestAddDevice(json);
                            //                            }
                            mBusiness.onRequestAddDevice(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestVerifyUser") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestVerifyUser(json);
                            //                            }
                            mBusiness.onRequestVerifyUser(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestDelDevice") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestDelDevice(json);
                            //                            }
                            mBusiness.onRequestDelDevice(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestUserList") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestUserList(json);
                            //                            }
                            mBusiness.onRequestUserList(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestAddUser") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestAddUser(json);
                            //                            }
                            mBusiness.onRequestAddUser(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestDelUser") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestDelUser(json);
                            //                            }
                            mBusiness.onRequestDelUser(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestUnbindUser") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestUnbindUser(json);
                            //                            }
                            mBusiness.onRequestUnbindUser(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestSendSmsCode") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestSendSmsCode(json);
                            //                            }
                            mBusiness.onRequestSendSmsCode(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestPutAdmin") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestPutAdmin(json);
                            //                            }
                            mBusiness.onRequestPutAdmin(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestRenameAlias") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestRenameAlias(json);
                            //                            }
                            mBusiness.onRequestRenameAlias(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestAlarm") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestAlarm(json);
                            //                            }
                            mBusiness.onRequestAlarm(json);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    } else if (matchURI.indexOf("requestPushIRData") > 0) {
                        JSONObject json;
                        try {
                            json = new JSONObject(inMessage.getResponse());
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestPushIRData(json);
                            //                            }
                            mBusiness.onRequestPushIRData(json);
                        } catch (JSONException e) {
                            reportResultCode(RequestSend.class.getSimpleName() + "prev", 444);
                            e.printStackTrace();
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).onRequestPushIRData(null);
                            //                            }
                            mBusiness.onRequestPushIRData(null);
                        }

                    } else
                    /**
                     * 二进制报文处理 控制、配置反馈
                     */
                        if (matchURI.indexOf("requestSend") > 0) {
                            JSONObject jsonObject, jobj = null;
                            try {
                                jsonObject = new JSONObject(inMessage.getResponse());
                                int result = jsonObject.optInt("result");
                                if (result != 0) {
                                    //                                    for (int i = 0; i < listBusiness.size(); i++) {
                                    //                                        listBusiness.get(i).onRemoteIssure(result);
                                    //                                    }
                                    mBusiness.onRemoteIssure(result);

                                    reportResultCode(RequestSend.class.getSimpleName()
                                            + "prev", result);
                                    return;
                                }
                                jobj = jsonObject.getJSONObject("msg");

                                MLog.v(TAG, "Response from server:/requestSend \n"
                                        + DigitUtil.getHexString(Base64.decode(
                                        jobj.getString("value"), 0),
                                        false));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }

                            mparse(jobj.optString("value"));

                        }
        }

        /**
         * 处理二进制报文
         *
         * @param value
         */
        private void mparse(String value) {
            BitReader bReader = BitReader.newInstance(Base64.decode(value, 0));
            int messageCode, messageBodyLegth;
            if (bReader.getBYTE() == 0x7f) {
                messageCode = bReader.getWORD();
                messageBodyLegth = bReader.getWORD();
                switch (messageCode) {
                    case 0x0100:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0100Response(
                        //                                    new H0100(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0100Response(new H0100(bReader.getByteArray()));

                        break;
                    case 0x0201:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0201Response(
                        //                                    new H0201(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0201Response(new H0201(bReader.getByteArray()));

                        break;
                    case 0x0202:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0202Response(
                        //                                    new H0202(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0202Response(new H0202(bReader.getByteArray()));

                        break;
                    case 0x0203:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0203Response(
                        //                                    new H0203(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0203Response(new H0203(bReader.getByteArray()));

                        break;
                    case 0x0204:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0204Response(
                        //                                    new H0204(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0204Response(new H0204(bReader.getByteArray()));

                        break;
                    case 0x0205:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0205Response(
                        //                                    new H0205(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0205Response(new H0205(bReader.getByteArray()));

                        break;
                    case 0x0206:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0206Response(
                        //                                    new H0206(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0206Response(new H0206(bReader.getByteArray()));

                        break;
                    case 0x0207:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0207Response(
                        //                                    new H0207(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0207Response(new H0207(bReader.getByteArray()));

                        break;
                    case 0x0208:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0208Response(
                        //                                    new H0208(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0208Response(new H0208(bReader.getByteArray()));
                        break;
                    case 0x0209:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0209Response(
                        //                                    new H0209(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0209Response(new H0209(bReader.getByteArray()));

                        break;
                    case 0x020a:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on020aResponse(
                        //                                    new H020a(bReader.getByteArray()));
                        //                        }
                        mBusiness.on020aResponse(new H020a(bReader.getByteArray()));

                        break;
                    case 0x020b:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on020bResponse(
                        //                                    new H020b(bReader.getByteArray()));
                        //                        }
                        mBusiness.on020bResponse(new H020b(bReader.getByteArray()));

                        break;
                    case 0x0241:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0241Response(
                        //                                    new H0241(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0241Response(new H0241(bReader.getByteArray()));

                        break;
                    case 0x0242:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0242Response(
                        //                                    new H0242(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0242Response(new H0242(bReader.getByteArray()));
                        break;
                    case 0x0243:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0243Response(
                        //                                    new H0243(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0243Response(new H0243(bReader.getByteArray()));
                        break;
                    case 0x0244:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0244Response(
                        //                                    new H0244(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0244Response(new H0244(bReader.getByteArray()));

                        break;
                    case 0x0245:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0245Response(
                        //                                    new H0245(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0245Response(new H0245(bReader.getByteArray()));

                        break;
                    case 0x0246:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0246Response(
                        //                                    new H0246(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0246Response(new H0246(bReader.getByteArray()));
                        break;
                    case 0x0211:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0211Response(
                        //                                    new H0211(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0211Response(new H0211(bReader.getByteArray()));
                        break;
                    case 0x0212:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0212Response(
                        //                                    new H0212(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0212Response(new H0212(bReader.getByteArray()));
                        break;
                    case 0x0213:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0213Response(
                        //                                    new H0213(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0213Response(new H0213(bReader.getByteArray()));

                        break;
                    case 0x0214:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0214Response(
                        //                                    new H0214(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0214Response(new H0214(bReader.getByteArray()));

                        break;
                    case 0x0215:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0215Response(
                        //                                    new H0215(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0215Response(new H0215(bReader.getByteArray()));

                        break;
                    case 0x0216:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0216Response(
                        //                                    new H0216(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0216Response(new H0216(bReader.getByteArray()));

                        break;
                    case 0x0217:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0217Response(
                        //                                    new H0217(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0217Response(new H0217(bReader.getByteArray()));

                        break;
                    case 0x0261:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0261Response(
                        //                                    new H0261(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0261Response(new H0261(bReader.getByteArray()));

                        break;
                    case 0x0221:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0221Response(
                        //                                    new H0221(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0221Response(new H0221(bReader.getByteArray()));

                    case 0x0222:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0222Response(
                        //                                    new H0222(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0222Response(new H0222(bReader.getByteArray()));
                        break;
                    case 0x0223:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0223Response(
                        //                                    new H0223(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0223Response(new H0223(bReader.getByteArray()));

                        break;
                    case 0x0262:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0262Response(
                        //                                    new H0262(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0262Response(new H0262(bReader.getByteArray()));

                        break;
                    case 0x0265: {
                        H0265 h0265 = new H0265(bReader.getByteArray());
                        h0265.analyze();
                        if (h0265.getResultCode() == 0) {
                            Cursor cursor = null;

                            for (HSmartDevice hSmartDevice : h0265.getList()) {
                                cursor = getContentResolver().query(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                        new String[]{HSmartProvider.MetaData.RealData.DEVICE_ID},
                                        HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                        new String[]{String.valueOf(hSmartDevice.getDeviceId()),
                                                String.valueOf(hSmartDevice.getDeviceType()),
                                                String.valueOf(hSmartDevice.getDeviceOriginal()),
                                                String.valueOf(hSmartDevice.getFeatureType())}, null);
                                if (cursor != null) {
                                    ContentValues cv = new ContentValues();
                                    cv.put(HSmartProvider.MetaData.RealData.DEVICE_ID, hSmartDevice.getDeviceId());
                                    cv.put(HSmartProvider.MetaData.RealData.DEVICE_TYPE, hSmartDevice.getDeviceType());
                                    cv.put(HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL, hSmartDevice.getDeviceOriginal());
                                    cv.put(HSmartProvider.MetaData.RealData.FEATURE_TYPE, hSmartDevice.getFeatureType());
                                    cv.put(HSmartProvider.MetaData.RealData.FEATURE_VALUE, hSmartDevice.getFeatureValue());
                                    if (cursor.getCount() > 0) {
                                        getContentResolver().update(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                                cv,
                                                HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                        + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                        + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                        + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                                new String[]{
                                                        String.valueOf(hSmartDevice.getDeviceId()),
                                                        String.valueOf(hSmartDevice.getDeviceType()),
                                                        String.valueOf(hSmartDevice.getDeviceOriginal()),
                                                        String.valueOf(hSmartDevice.getFeatureType())});
                                    } else {
                                        getContentResolver().insert(HSmartProvider.MetaData.RealData.CONTENT_URI, cv);
                                    }
                                    cursor.close();
                                }
                            }

                        }


                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0265Response(h0265.getResultCode());
                        //                        }
                        mBusiness.on0265Response(h0265.getResultCode());
                    }
                    break;

                    /*
                     * 设备状态查询反馈
                     */
                    case 0x0260: {
                        H0260 h0260 = new H0260(bReader.getByteArray());
                        h0260.analyze();
                        ContentProvider cp = getContentResolver()
                                .acquireContentProviderClient(
                                        HSmartProvider.class.getName())
                                .getLocalContentProvider();
                        DBHelper dbHelper = ((HSmartProvider) cp).getDbHelper();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();
                        try {
                            for (HProduct hProduct : h0260.getList()) {
                                db.execSQL("update pt_product set product_state=?,product_type=? where product_id=?", new Object[]{
                                        hProduct.getProductState(), hProduct.getProductType(), hProduct.getProductId()
                                });

                            }
                            db.setTransactionSuccessful();
                            MLog.d("App", "设备运行状态同步成功");
                        } catch (Exception e) {
                            MLog.e("App", "设备运行状态更新异常");
                            e.printStackTrace();
                        } finally {
                            if (db != null)
                                db.endTransaction();
                        }

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0260Response(
                        //                                    new H0260(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0260Response(new H0260(bReader.getByteArray()));

                        break;
                    }
                    case 0x0280:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0280Response(
                        //                                    new H0280(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0280Response(new H0280(bReader.getByteArray()));

                        break;
                    case 0x0281:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0281Response(
                        //                                    new H0281(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0281Response(new H0281(bReader.getByteArray()));

                        break;
                    case 0x0282:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0282Response(
                        //                                    new H0282(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0282Response(new H0282(bReader.getByteArray()));

                        break;
                    case 0x0283:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0283Response(
                        //                                    new H0283(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0283Response(new H0283(bReader.getByteArray()));

                        break;
                    case 0x0284:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0284Response(
                        //                                    new H0284(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0284Response(new H0284(bReader.getByteArray()));

                        break;
                    case 0x0285:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0285Response(
                        //                                    new H0284(bReader.getByteArray()));
                        //                        }
                        mBusiness.on0285Response(new H0284(bReader.getByteArray()));

                        break;
                    case 0x02a1:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on02a1Response(
                        //                                    new H02a1(bReader.getByteArray()));
                        //                        }
                        mBusiness.on02a1Response(new H02a1(bReader.getByteArray()));

                        break;
                    case 0x02a2:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on02a2Response(
                        //                                    new H02a2(bReader.getByteArray()));
                        //                        }
                        mBusiness.on02a2Response(new H02a2(bReader.getByteArray()));

                        break;
                    case 0x02a3:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on02a3Response(
                        //                                    new H02a3(bReader.getByteArray()));
                        //                        }
                        mBusiness.on02a3Response(new H02a3(bReader.getByteArray()));
                        break;

                    case 0x02b0:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on02b0Response(
                        //                                    new H02b0(bReader.getByteArray()));
                        //                        }
                        mBusiness.on02b0Response(new H02b0(bReader.getByteArray()));

                        break;
                    case 0x02b1:

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on02b1Response(
                        //                                    new H02b1(bReader.getByteArray()));
                        //                        }
                        mBusiness.on02b1Response(new H02b1(bReader.getByteArray()));

                        break;
                    case 0x0101://设备状态反馈
                        H0101 h0101 = new H0101(bReader.getByteArray());
                        h0101.analyze();
                        Cursor cursor = null;
                        switch (h0101.getFeatureType()) {
                            case 5:
                            case 8:
                            case 11:
                            case 14:
                                cursor = getContentResolver().query(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                        new String[]{HSmartProvider.MetaData.RealData.DEVICE_ID},
                                        HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                        new String[]{String.valueOf(h0101.getDeviceId()),
                                                String.valueOf(h0101.getDeviceType()),
                                                String.valueOf(h0101.getOriginalType()),
                                                String.valueOf(h0101.getFeatureType())}, null);
                                break;
                        }

                        if (h0101.getFeatureType() == 14 && Const.byteArrayToNumber(h0101.getValue()) == 1) {
                            Cursor deviceInfo = null;
                            ContentProvider cp = getContentResolver()
                                    .acquireContentProviderClient(HSmartProvider.class.getName())
                                    .getLocalContentProvider();
                            DBHelper dbHelper = ((HSmartProvider) cp).getDbHelper();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            StringBuilder sb = new StringBuilder();
                            if (h0101.getOriginalType() == 1) {
                                sb.append("select b.[room_area_name] as room,a.[product_name] as name from pt_product a left join pt_roomArea b on a.[room_area_id]=b.[room_area_id] where a.[product_id] = ?");
                            } else {
                                sb.append("select b.[room_area_name] as room,a.[control_device_name] as name from pt_controlDevice a left join pt_roomArea b on a.[room_area_id]=b.[room_area_id] where a.[control_device_id] = ?");
                            }
                            deviceInfo = db.rawQuery(sb.toString(), new String[]{String.valueOf(h0101.getDeviceId())});

                            if (deviceInfo != null && deviceInfo.getCount() > 0) {
                                String tip = "";
                                int state = Const.byteArrayToNumber(h0101.getValue());
                                if (h0101.getOriginalType() == 1) {
                                    switch (h0101.getDeviceType()) {
                                        case 70:
                                            tip = state == 1 ? "发现报警信号" : "已恢复正常";
                                            break;
                                        case 71:
                                            tip = state == 1 ? "发现燃气泄漏" : "已恢复正常";
                                            break;
                                        case 72:
                                            tip = state == 1 ? "发现浓度超标" : "已恢复正常";
                                            break;
                                        case 73:
                                            tip = state == 1 ? "发现有人经过" : "已离开";
                                            break;
                                        case 74:
                                            tip = state == 1 ? "发现防区有物体闯入" : "已离开";
                                            break;
                                        case 75:
                                            tip = state == 1 ? "达到风光雨阈值" : "已恢复正常";
                                            break;
                                        case 76:
                                            tip = state == 1 ? "发现紧急报警" : "已恢复正常";
                                            break;
                                        case 77:
                                            tip = state == 1 ? "发现玻璃破碎" : "已恢复正常";
                                            break;
                                        case 78:
                                            tip = state == 1 ? "发现漏水" : "已恢复正常";
                                            break;
                                        case 79:
                                            tip = state == 1 ? "发现有人经过" : "已离开";
                                            break;

                                    }
                                } else if (h0101.getOriginalType() == 2) {
                                    switch (h0101.getDeviceType()) {
                                        case 11:
                                            tip = state == 1 ? "有人打开" : "已关闭";
                                            break;
                                        case 12:
                                            tip = state == 1 ? "发现有人经过" : "已离开";
                                            break;
                                        case 15:
                                            tip = state == 1 ? "发现烟雾浓度超标" : "恢复正常";
                                            break;
                                        case 16:
                                            tip = state == 1 ? "发现漏水" : "恢复正常";
                                            break;
                                    }
                                }
                                deviceInfo.moveToNext();
                                boolean audioNotify = sharedPreferences.getBoolean("audioNotify", true);
                                boolean messageNotify = sharedPreferences.getBoolean("messageNotify", true);
                                //                                if (messageNotify)
                                //                                    NotificationUtil.notiy(getApplicationContext(), "安防提醒", "[" + deviceInfo.getString(0) + "]" + "[" + deviceInfo.getString(1) + "] " + tip, (int) System.currentTimeMillis());
                                //                                if (audioNotify) {//语音合成
                                //                                    SpeechSynthesizerUtil.lists.add(deviceInfo.getString(0) + "," + deviceInfo.getString(1) + "," + tip);
                                //                                    SpeechSynthesizerUtil.play();
                                //                                }
                                deviceInfo.close();
                            }
                        }
                        if (cursor != null) {
                            ContentValues cv = new ContentValues();
                            cv.put(HSmartProvider.MetaData.RealData.DEVICE_ID, h0101.getDeviceId());
                            cv.put(HSmartProvider.MetaData.RealData.DEVICE_TYPE, h0101.getDeviceType());
                            cv.put(HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL, h0101.getOriginalType());
                            cv.put(HSmartProvider.MetaData.RealData.FEATURE_TYPE, h0101.getFeatureType());
                            cv.put(HSmartProvider.MetaData.RealData.FEATURE_VALUE, Const.byteArrayToNumber(h0101.getValue(), h0101.getLengthOfValue()));
                            if (cursor.getCount() > 0) {
                                getContentResolver().update(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                        cv,
                                        HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                        new String[]{
                                                String.valueOf(h0101.getDeviceId()),
                                                String.valueOf(h0101.getDeviceType()),
                                                String.valueOf(h0101.getOriginalType()),
                                                String.valueOf(h0101.getFeatureType())});
                            } else {
                                getContentResolver().insert(HSmartProvider.MetaData.RealData.CONTENT_URI, cv);
                            }
                            cursor.close();
                        }
                        //

                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).on0101Response(h0101);
                        //                        }
                        mBusiness.on0101Response(h0101);

                        break;

                    case 0x0102:
                        H0102 h0102 = new H0102(bReader.getByteArray());
                        List<H0101> h0101List = h0102.getHDeviceList();
                        for (int k = 0; k < h0101List.size(); k++) {
                            H0101 mH0101 = h0101List.get(k);
                            Cursor mCursor = null;
                            switch (mH0101.getFeatureType()) {
                                case 5:
                                case 8:
                                case 11:
                                case 14:
                                    mCursor = getContentResolver().query(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                            new String[]{HSmartProvider.MetaData.RealData.DEVICE_ID},
                                            HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                    + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                    + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                    + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                            new String[]{String.valueOf(mH0101.getDeviceId()),
                                                    String.valueOf(mH0101.getDeviceType()),
                                                    String.valueOf(mH0101.getOriginalType()),
                                                    String.valueOf(mH0101.getFeatureType())}, null);
                                    break;
                            }

                            if (mH0101.getFeatureType() == 14 && Const.byteArrayToNumber(mH0101.getValue()) == 1) {
                                Cursor deviceInfo = null;
                                ContentProvider cp = getContentResolver()
                                        .acquireContentProviderClient(HSmartProvider.class.getName())
                                        .getLocalContentProvider();
                                DBHelper dbHelper = ((HSmartProvider) cp).getDbHelper();
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                StringBuilder sb = new StringBuilder();
                                if (mH0101.getOriginalType() == 1) {
                                    sb.append("select b.[room_area_name] as room,a.[product_name] as name from pt_product a left join pt_roomArea b on a.[room_area_id]=b.[room_area_id] where a.[product_id] = ?");
                                } else {
                                    sb.append("select b.[room_area_name] as room,a.[control_device_name] as name from pt_controlDevice a left join pt_roomArea b on a.[room_area_id]=b.[room_area_id] where a.[control_device_id] = ?");
                                }
                                deviceInfo = db.rawQuery(sb.toString(), new String[]{String.valueOf(mH0101.getDeviceId())});

                                if (deviceInfo != null && deviceInfo.getCount() > 0) {
                                    String tip = "";
                                    int state = Const.byteArrayToNumber(mH0101.getValue());
                                    if (mH0101.getOriginalType() == 1) {
                                        switch (mH0101.getDeviceType()) {
                                            case 70:
                                                tip = state == 1 ? "发现报警信号" : "已恢复正常";
                                                break;
                                            case 71:
                                                tip = state == 1 ? "发现燃气泄漏" : "已恢复正常";
                                                break;
                                            case 72:
                                                tip = state == 1 ? "发现浓度超标" : "已恢复正常";
                                                break;
                                            case 73:
                                                tip = state == 1 ? "发现有人经过" : "已离开";
                                                break;
                                            case 74:
                                                tip = state == 1 ? "发现防区有物体闯入" : "已离开";
                                                break;
                                            case 75:
                                                tip = state == 1 ? "达到风光雨阈值" : "已恢复正常";
                                                break;
                                            case 76:
                                                tip = state == 1 ? "发现紧急报警" : "已恢复正常";
                                                break;
                                            case 77:
                                                tip = state == 1 ? "发现玻璃破碎" : "已恢复正常";
                                                break;
                                            case 78:
                                                tip = state == 1 ? "发现漏水" : "已恢复正常";
                                                break;
                                            case 79:
                                                tip = state == 1 ? "发现有人经过" : "已离开";
                                                break;

                                        }
                                    } else if (mH0101.getOriginalType() == 2) {
                                        switch (mH0101.getDeviceType()) {
                                            case 11:
                                                tip = state == 1 ? "有人打开" : "已关闭";
                                                break;
                                            case 12:
                                                tip = state == 1 ? "发现有人经过" : "已离开";
                                                break;
                                            case 15:
                                                tip = state == 1 ? "发现烟雾浓度超标" : "恢复正常";
                                                break;
                                            case 16:
                                                tip = state == 1 ? "发现漏水" : "恢复正常";
                                                break;
                                        }
                                    }
                                    deviceInfo.moveToNext();
                                    boolean audioNotify = sharedPreferences.getBoolean("audioNotify", true);
                                    boolean messageNotify = sharedPreferences.getBoolean("messageNotify", true);
                                    //                                    if (messageNotify)
                                    //                                        NotificationUtil.notiy(getApplicationContext(), "安防提醒", "[" + deviceInfo.getString(0) + "]" + "[" + deviceInfo.getString(1) + "] " + tip, (int) System.currentTimeMillis());
                                    //                                    if (audioNotify) {//语音合成
                                    //                                        SpeechSynthesizerUtil.lists.add(deviceInfo.getString(0) + "," + deviceInfo.getString(1) + "," + tip);
                                    //                                        SpeechSynthesizerUtil.play();
                                    //                                    }
                                    deviceInfo.close();
                                }
                            }
                            if (mCursor != null) {
                                ContentValues cv = new ContentValues();
                                cv.put(HSmartProvider.MetaData.RealData.DEVICE_ID, mH0101.getDeviceId());
                                cv.put(HSmartProvider.MetaData.RealData.DEVICE_TYPE, mH0101.getDeviceType());
                                cv.put(HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL, mH0101.getOriginalType());
                                cv.put(HSmartProvider.MetaData.RealData.FEATURE_TYPE, mH0101.getFeatureType());
                                cv.put(HSmartProvider.MetaData.RealData.FEATURE_VALUE, Const.byteArrayToNumber(mH0101.getValue(), mH0101.getLengthOfValue()));
                                if (mCursor.getCount() > 0) {
                                    getContentResolver().update(HSmartProvider.MetaData.RealData.CONTENT_URI,
                                            cv,
                                            HSmartProvider.MetaData.RealData.DEVICE_ID + "=? and "
                                                    + HSmartProvider.MetaData.RealData.DEVICE_TYPE + "=? and "
                                                    + HSmartProvider.MetaData.RealData.DEVICE_ORIGINAL + "=? and "
                                                    + HSmartProvider.MetaData.RealData.FEATURE_TYPE + "=? ",
                                            new String[]{
                                                    String.valueOf(mH0101.getDeviceId()),
                                                    String.valueOf(mH0101.getDeviceType()),
                                                    String.valueOf(mH0101.getOriginalType()),
                                                    String.valueOf(mH0101.getFeatureType())});
                                } else {
                                    getContentResolver().insert(HSmartProvider.MetaData.RealData.CONTENT_URI, cv);
                                }
                                mCursor.close();
                            }
                            //
                            //                            for (int i = 0; i < listBusiness.size(); i++) {
                            //                                listBusiness.get(i).on0101Response(mH0101);
                            //                            }
                            mBusiness.on0101Response(mH0101);
                        }
                        break;

                    case 0x0230: {//设备上下线变化
                        H0230 h0230 = new H0230(bReader.getByteArray());
                        h0230.analyze();
                        h0230.updateDatabase(getContentResolver());
                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i).onRequestDeviceOnlineChange();
                        //                        }
                        mBusiness.onRequestDeviceOnlineChange();
                    }
                    break;
                    case 0x0231: {//设备辅助定位
                        H0231 h0231 = new H0231(bReader.getByteArray());
                        h0231.analyze();
                        //                        for (int i = 0; i < listBusiness.size(); i++) {
                        //                            listBusiness.get(i)
                        //                                    .onRequestLocation(h0231.getDevice());
                        //                        }
                        mBusiness.onRequestLocation(h0231.getDevice());
                    }
                    break;

                    default:
                        MLog.w(TAG, "不能识别的命令");
                        break;
                }
            } else {

                MLog.w(TAG, "收到异常包");
            }
        }

        //服务器主动请求过来
        @Override
        public void onServerIn(HttpMessage inMessage) {


            try {
                JSONObject jsonObject;
                jsonObject = new JSONObject(inMessage.getResponse());
                JSONObject jobj = jsonObject.getJSONObject("msg");
                mparse(jobj.getString("value"));

            } catch (Exception e) {
                MLog.w(TAG, "Json数据格式异常");
                e.printStackTrace();
            }

        }

        @Override
        public void onLoginResult(int result) {
            if (result == 0) {
                //获取登录session
                Const.CLIENT_SESSION = businessManager.getSession();
                //获取登录信息
                ConnectInfo connectInfo = businessManager.getConnectInfo();
                Const.SERVER = connectInfo.getHost();
                Const.PORT = connectInfo.getPort();
                Const.GATE_MAC = connectInfo.getMac();
                //                if (!KookongSDK.init(context, APP_KEY_KK, Const.GATE_MAC)) {
                //                    MLog.e("APP", "KookongSDK初始化失败");
                //                }
                checkConfigUpdate();
            } else {
                //提示登录错误
                reportResultCode(RequestAuth.class.getSimpleName(), result);
            }
            //            for (int i = 0; i < listBusiness.size(); i++) {
            //                listBusiness.get(i).onResponseAuth(result);
            //            }
            mBusiness.onResponseAuth(result);

        }

        @Override
        public void onLoginOut() {
            //            for (int i = 0; i < listBusiness.size(); i++) {
            //                listBusiness.get(i).onResponseCancel();
            //            }
            mBusiness.onResponseCancel();
        }

        @Override
        public void onConnectLost() {
            //            for (int i = 0; i < listBusiness.size(); i++) {
            //                listBusiness.get(i).onResponseCancel();
            //            }
            mBusiness.onResponseCancel();

        }
    };

    private ServiceConnection conn = new ServiceConnection() {

        //Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            businessManager = (CoreService.BusinessManager) service;
            businessManager.setBusinessCallback(serverCallback);
            MLog.d("App", "后台服务绑定成功");
            if (businessManager.isLogin()) {
                ConnectInfo cif = businessManager.getConnectInfo();
                Const.SERVER = cif.getHost();
                Const.PORT = cif.getPort();
                Const.GATE_MAC = cif.getMac();
                Const.CLIENT_SESSION = businessManager.getSession();
                MLog.d("App", "已登录获取相关信息");
            }
            //尝试用已存在的登录信息进行登录
            Intent intent = new Intent();
            intent.setClass(context, CoreService.class);
            context.startService(intent);
            MLog.d("App", "尝试自动连接");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("user_setting", MODE_PRIVATE);
        //        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //activity获取网关数据线程池实例化
        extFixedThreadPool = Executors.newFixedThreadPool(4);

        //        Intent intentAction = new Intent();
        //        intentAction.setAction("devkit.mj.net.CoreService.action");
        //        intentAction.setPackage(getPackageName());
        //        bindService(intentAction, conn, Service.BIND_AUTO_CREATE);
        MLog.d("App", "application onCreate 结束");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unbindService(conn);
    }

    public void addBusinessObserver(Business business) {
        //        listBusiness.add(business);
        mBusiness = business;
    }

    public void removeBusinessObserver(Business business) {
        //        listBusiness.remove(business);
    }

    //登录成功后检查更新
    public void checkConfigUpdate() {
        Cursor cursor = getContentResolver().query(
                HSmartProvider.MetaData.Home.CONTENT_URI,
                new String[]{HSmartProvider.MetaData.Home.LAST_TIME, HSmartProvider.MetaData.Home.GATE_MAC}, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String lastUpdateTime = cursor
                        .getString(cursor
                                .getColumnIndex(HSmartProvider.MetaData.Home.LAST_TIME));
                String dbMac = cursor
                        .getString(cursor
                                .getColumnIndex(HSmartProvider.MetaData.Home.GATE_MAC));
                if (dbMac.equalsIgnoreCase(Const.GATE_MAC))
                    sendRequest(new RequestUpdate(Const.CLIENT_SESSION,
                            lastUpdateTime));
                else
                    sendRequest(new RequestConfig(Const.CLIENT_SESSION));

            }

            cursor.close();
        } else {

            sendRequest(new RequestConfig(Const.CLIENT_SESSION));

        }
    }

    //检查设备类型及在线情况
    public void checkDeviceStatus() {
        // 查询所有original为1的设备在线状态
        Cursor cursor = getContentResolver().query(HSmartProvider.MetaData.Product.CONTENT_URI,
                new String[]{HSmartProvider.MetaData.Product.PRODUCT_ID},
                null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                ArrayList<Long> listProductId = new ArrayList<Long>();
                while (cursor.moveToNext()) {
                    if (!listProductId.contains(cursor.getLong(0)))
                        listProductId.add(cursor.getLong(0));
                }
                sendControl(new H0960(listProductId));
            }
            cursor.close();
        }

        checkDeviceValue();
    }

    //获取设备状态值
    private void checkDeviceValue() {
        ContentProvider cp = getContentResolver()
                .acquireContentProviderClient(HSmartProvider.class.getName())
                .getLocalContentProvider();

        DBHelper dbHelper = ((HSmartProvider) cp).getDbHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        //开关值
        sb.append("select ");
        sb.append("  a.control_device_id as _id, ");
        sb.append("  a.control_device_id as device_id, ");
        sb.append("  a.control_device_type as device_type, ");
        sb.append("  2 as device_original, ");
        sb.append("  a.control_device_name as device_name, ");
        sb.append("  c.room_area_id as room_area_id, ");
        sb.append("  c.room_area_name as room_area_name, ");
        sb.append("  5 as feature_type, ");
        sb.append("  d.feature_value as feature_value, ");
        sb.append("  b.product_id as product_id, ");
        sb.append("  b.product_state as device_state ");
        sb.append("from ");
        sb.append("  pt_controlDevice a ");
        sb.append("  inner join pt_product b on a.[product_id] = b.[product_id] ");
        sb.append("  left join pt_roomArea c on a.[room_area_id] = c.[room_area_id] ");
        sb.append("  left join pt_realData d on a.[control_device_id] = d.[device_id] ");
        sb.append("  and a.[control_device_type] = d.[device_type] ");
        sb.append("  and d.[device_original] = 2 ");
        sb.append("  and d.[feature_type] = 5 ");
        sb.append("where ");
        sb.append("  a.[control_device_type] = 1 ");
        sb.append(" union ");
        //调光值
        sb.append("select ");
        sb.append("  a.control_device_id as _id, ");
        sb.append("  a.control_device_id as device_id, ");
        sb.append("  a.control_device_type as device_type, ");
        sb.append("  2 as device_original, ");
        sb.append("  a.control_device_name as device_name, ");
        sb.append("  c.room_area_id as room_area_id, ");
        sb.append("  c.room_area_name as room_area_name, ");
        sb.append("  11 as feature_type, ");
        sb.append("  d.feature_value as feature_value, ");
        sb.append("  b.product_id as product_id, ");
        sb.append("  b.product_state as device_state ");
        sb.append("from ");
        sb.append("  pt_controlDevice a ");
        sb.append("  inner join pt_product b on a.[product_id] = b.[product_id] ");
        sb.append("  left join pt_roomArea c on a.[room_area_id] = c.[room_area_id] ");
        sb.append("  left join pt_realData d on a.[control_device_id] = d.[device_id] ");
        sb.append("  and a.[control_device_type] = d.[device_type] ");
        sb.append("  and d.[device_original] = 2 ");
        sb.append("  and d.[feature_type] = 11 ");
        sb.append("where ");
        sb.append("  a.[control_device_type] = 2 ");
        sb.append(" union ");
        //单路值
        sb.append("select ");
        sb.append("  a.product_id as _id, ");
        sb.append("  a.product_id as device_id, ");
        sb.append("  a.product_type as device_type, ");
        sb.append("  1 as device_original, ");
        sb.append("  a.product_name as device_name, ");
        sb.append("  b.room_area_id as room_area_id, ");
        sb.append("  b.room_area_name as room_area_name, ");
        sb.append("  5 as feature_type, ");
        sb.append("  c.feature_value as feature_value, ");
        sb.append("  a.product_id as product_id, ");
        sb.append("  a.product_state as device_state ");
        sb.append("from ");
        sb.append("  pt_product a ");
        sb.append("  left join pt_roomArea b on a.[room_area_id] = b.[room_area_id] ");
        sb.append("  left join pt_realData c on a.[product_id] = c.[device_id] ");
        sb.append("  and a.[product_type] = c.[device_type] ");
        sb.append("  and c.[device_original] = 1 ");
        sb.append("  and c.[feature_type] = 5 ");
        sb.append("where ");
        sb.append("  a.[product_type]  =91 ");
        sb.append(" union ");
        //插座值
        sb.append("select ");
        sb.append("  a.product_id as _id, ");
        sb.append("  a.product_id as device_id, ");
        sb.append("  a.product_type as device_type, ");
        sb.append("  1 as device_original, ");
        sb.append("  a.product_name as device_name, ");
        sb.append("  b.room_area_id as room_area_id, ");
        sb.append("  b.room_area_name as room_area_name, ");
        sb.append("  5 as feature_type, ");
        sb.append("  c.feature_value as feature_value, ");
        sb.append("  a.product_id as product_id, ");
        sb.append("  a.product_state as device_state ");
        sb.append("from ");
        sb.append("  pt_product a ");
        sb.append("  left join pt_roomArea b on a.[room_area_id] = b.[room_area_id] ");
        sb.append("  left join pt_realData c on a.[product_id] = c.[device_id] ");
        sb.append("  and a.[product_type] = c.[device_type] ");
        sb.append("  and c.[device_original] = 1 ");
        sb.append("  and c.[feature_type] = 5 ");
        sb.append("where ");
        sb.append("  a.[product_type]  between 30 and 39 ");
        sb.append(" union ");
        //窗帘值
        sb.append("select ");
        sb.append("  a.product_id as _id, ");
        sb.append("  a.product_id as device_id, ");
        sb.append("  a.product_type as device_type, ");
        sb.append("  1 as device_original, ");
        sb.append("  a.product_name as device_name, ");
        sb.append("  b.room_area_id as room_area_id, ");
        sb.append("  b.room_area_name as room_area_name, ");
        sb.append("  8 as feature_type, ");
        sb.append("  0 as feature_value, ");
        sb.append("  a.product_id as product_id, ");
        sb.append("  a.product_state as device_state ");
        sb.append("from ");
        sb.append("  pt_product a ");
        sb.append("  left join pt_roomArea b on a.[room_area_id] = b.[room_area_id] ");
        sb.append("  left join pt_realData c on a.[product_id] = c.[device_id] ");
        sb.append("  and c.[feature_type] = 8 ");
        sb.append("where ");
        sb.append("  a.[product_type] between 50 and 59 ");
        Cursor cursor = db.rawQuery(sb.toString(), null);

        ArrayList<HSmartDevice> listDevice = new ArrayList<HSmartDevice>();
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    listDevice.add(new HSmartDevice(cursor.getInt(1), cursor.getInt(2),
                            cursor.getInt(3), cursor.getInt(7), 0));
                }

                //                sendControl(new H0965(listDevice));
                if (listDevice.size() < 25)
                    sendControl(new H0965(listDevice));
                for (int i = 0; i < listDevice.size() / 25; i++) {
                    sendControl(new H0965(new ArrayList<HSmartDevice>(listDevice.subList(i * 25, (i + 1) * 25))));
                }
                if (listDevice.size() % 25 != 0)
                    sendControl(new H0965(new ArrayList<HSmartDevice>(listDevice.subList(listDevice.size() - listDevice.size() % 25, listDevice.size()))));
            }
            cursor.close();
        }
    }

    /**
     * 发送分布式协议请求
     *
     * @param httpSendMessage
     */
    public void sendRequest(HttpOutMessage httpSendMessage) {

        if (isInternalNet()) {
            //内网
            if (businessManager != null)
                businessManager.sendControl(httpSendMessage.getString().getBytes());
        } else {
            //外网
            //businessManager.getHttpMsg(httpSendMessage);
            try {
                String string = httpSendMessage.getString();
                int i = string.indexOf("{");
                String data = string.substring(i);

                int Y = string.indexOf("HTTP");
                int X = string.indexOf("/");
                String key = string.substring(X + 1, Y);

                System.out.println("-----------key----------->>" + key);
                System.out.println("-----------value----------->>" + data);

                String value = "{ \"key\" : \"" + key.trim() + "\", \"data\" : " + data.trim() + " }";
                businessManager.publishHttpMsg(value.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 发送分布式协议请求中的，控制二进制报文请求
     *
     * @param hsend
     */
    public void sendControl(HSend hsend) {
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "");

        try {
            //红外命令配合震动
            if (hsend instanceof H09b0) {
                //                vibrator.vibrate(60);
            }
            if (name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {
                //内网登陆
                if (businessManager != null) {
                    businessManager.sendControl(new RequestSend(Const.CLIENT_SESSION, Base64
                            .encodeToString(hsend.getBytes(), Base64.NO_WRAP), hsend
                            .getBufferSize(), true, Const.HOST_IDENTITY).getString().getBytes());
                }

            } else {
                //外网登陆
                businessManager.publishMsg(Base64.encodeToString(hsend.getBytes(), Base64.NO_WRAP).getBytes());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常操控过程中的错误提示
     */
    public void reportResultCode(String requestType, int errorCode) {

        if (errorCode == 1001)//系统忙不提示
            return;
    }
}
