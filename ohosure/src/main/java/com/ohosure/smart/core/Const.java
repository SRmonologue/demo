package com.ohosure.smart.core;


import android.support.annotation.Keep;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Keep
public class Const {

    public static final int UDP_PORT = 9888;
    public static final String ROOT_SERVER = "";
    //公司自己服务器
//        public static final String MQTT_HOST = "tcp://121.40.143.92:1883";
//        public static final String BASE_URL = "https://api.ohosure.com/";

    //绿地项目
    public static final String MQTT_HOST = "tcp://mqtt.ohosureproj.com:1883";
    public static final String BASE_URL = "https://api.ohosureproj.com/";

    public static String ACCESS_TOKEN = "";
    public static String REFRESH_TOKEN = "";
    public static String CLIENT_SESSION = "";
    //        	public static final String ROOT_SERVER = "172.16.1.151";
    //        	public static final String ROOT_SERVER = "121.40.143.92";
    public static final int ROOT_PORT = 9888;
    public static final String ROOT = ROOT_SERVER + ":" + ROOT_PORT;

    public static int PORT = 0;
    public static String SERVER = "";
    public static String CLIENT_ID = "";
    public static final int HTTP_CONNECT_TIMEOUT = 5000;
    public static final int UDP_CONNECT_TIMEOUT = 3000;
    // 一般不设置此参数
    public static final int HTTP_READ_TIMEOUT = 5000;
    public static final int UDP_READ_TIMEOUT = 3000;

    public static final String UDB_MESSAGE = "AutoBee";
    public static String GATE_MAC = "";
    public static final String CHARSET = "UTF-8";
    public static final String HOST_IDENTITY = "and_ab";

    public static final String DEFAULT_BIND_USERNAME = "00000000000";
    public static final String DEFAULT_BIND_PASSWORD = "000000";

    public static String BANNER_INFO = "http://www.ohosure.com/mobieAPI/BannerInfo";
    public static String APP_UPDATE = "http://www.ohosure.com/mobieAPI/AndroidUpdate";
    public static String APP_DOWNLOAD = "http://a.app.qq.com/o/simple.jsp?pkgname=com.floraison.smarthome";
    public static final String PREF_BANNER_INFO = "BANNER_INFO";

    /**
     * share preference
     */
    public static final String PREF_FILE_NAME = "PREF_HSMART";
    public static final String FILE_NAME_WEATHER = "FILE_HSMART_WEATHER";
    public static final String PREF_LOACTION_PROVINCE = "loaction_province";
    public static final String PREF_LOACTION_CITY = "loaction_city";
    public static final String PREF_LOACTION_DISTRICT = "loaction_district";
    public static final String PREF_NEWS = "news";
    public static final String PREF_LAST_USERNAME = "username";
    public static final String PREF_LAST_PASSWORD = "password";
    public static final String PREF_LAST_GATE = "last_gate";
    public static final String PREF_LAST_HOST = "last_host";
    public static final String PREF_LAST_PORT = "last_port";

    public enum FeatureType {
        BMSG_FEATURE_TYPE_SWITCH, /* switch on/off: 0 off/1 on/3 reverse */
        BMSG_FEATURE_TYPE_SCENE, /* scene: 1-64 */
        BMSG_FEATURE_TYPE_REL_DIM, /* relative dimming */
        BMSG_FEATURE_TYPE_DIM, /* dimming:0-100% */
        BMSG_FEATURE_TYPE_REL_CURTAIN, /* relative position */
        BMSG_FEATURE_TYPE_CURTAIN, /* position:0-100% */
        BMSG_FEATURE_TYPE_REL_BLIND, /* relative position */
        BMSG_FEATURE_TYPE_BLIND, /* position:0-100% */
        BMSG_FEATURE_TYPE_REL_BLIND_ANGLE, /* relative angle */
        BMSG_FEATURE_TYPE_BLIND_ANGLE, /* angle:0-90 */
        BMSG_FEATURE_TYPE_GAS_SWITCH, /* gas switch on/off */
        BMSG_FEATURE_TYPE_GAS_SENSOR, /* gas sensor */
        BMSG_FEATURE_TYPE_DOOR_SENSOR, /* door/window sensor */
        BMSG_FEATURE_TYPE_IR; /* infrared */

        public static FeatureType getEnumFeatureType(int type) {
            FeatureType ft;
            switch (type) {
                case 1:
                    ft = FeatureType.BMSG_FEATURE_TYPE_SWITCH;
                    break;
                case 6:
                    ft = FeatureType.BMSG_FEATURE_TYPE_CURTAIN;
                    break;
                case 8:
                    ft = FeatureType.BMSG_FEATURE_TYPE_BLIND;
                    break;
                default:
                    ft = FeatureType.BMSG_FEATURE_TYPE_SWITCH;
                    break;
            }
            return ft;
        }
    }

    public enum ResultCode {
        RESULT_CODE_OK(0), /* OK */
        RESULT_CODE_FAILED(1), /* failed */
        RESULT_CODE_ZIGBEE_TIMEOUT(2), /* zigbee timeout */
        RESULT_CODE_NAME_EXIST(100), /* same name already exists */
        RESULT_CODE_ROOM_INVALID(101), /* specified room does not exist */
        RESULT_CODE_SCENE_INVALID(102), /* specified scene does not exist */
        RESULT_CODE_PRODUCT_INVALID(103), /* specified product does not exist */
        RESULT_CODE_KEY_INVALID(104), /* specified key does not exist */
        RESULT_CODE_CHANNEL_INVALID(105), /* specified channel does not exist */
        RESULT_CODE_CTRLDEV_INVALID(106), /*
         * specified control device does not
         * exist
         */
        RESULT_CODE_EXCEED_MAXSCENE(107), /*
         * exceeds the max number(64) of scenes
         * supported
         */
        RESULT_CODE_EXCEED_MAXTIMER(108), /*
         * exceeds the max number(16) of timers
         * supported by each product
         */
        RESULT_CODE_EXCEED_MAXDATE(109), /*
         * exceeds the max number(4) of dates
         * supported by each product
         */
        RESULT_CODE_FCFV_ERROR(110), /*指令错误*/
        RESULT_CODE_PORT_EXIST(160);/*端口已存在
         */
        private int value;

        private ResultCode(int value) {
            this.value = value;
        }

        public int getValue() {

            return this.value;

        }

        public static ResultCode getEnumResultCode(int code) {
            ResultCode rc;
            switch (code) {
                case 107:
                    rc = ResultCode.RESULT_CODE_EXCEED_MAXSCENE;
                    break;
                case 106:
                    rc = ResultCode.RESULT_CODE_CTRLDEV_INVALID;
                    break;
                case 105:
                    rc = ResultCode.RESULT_CODE_CHANNEL_INVALID;
                    break;
                case 104:
                    rc = ResultCode.RESULT_CODE_KEY_INVALID;
                    break;
                case 103:
                    rc = ResultCode.RESULT_CODE_PRODUCT_INVALID;
                    break;
                case 102:
                    rc = ResultCode.RESULT_CODE_SCENE_INVALID;
                    break;
                case 101:
                    rc = ResultCode.RESULT_CODE_ROOM_INVALID;
                    break;
                case 100:
                    rc = ResultCode.RESULT_CODE_NAME_EXIST;
                    break;
                case 2:
                    rc = ResultCode.RESULT_CODE_ZIGBEE_TIMEOUT;
                    break;
                case 1:
                    rc = ResultCode.RESULT_CODE_FAILED;
                    break;
                case 0:
                default:
                    rc = ResultCode.RESULT_CODE_OK;
            }
            return rc;
        }

    }

    public static byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    public static int byteArrayToInt(byte[] byteArray) {
        int n = 0;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(byteArray);
            DataInputStream dataInput = new DataInputStream(byteInput);
            n = dataInput.readInt();
            dataInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    public static int byteArrayToNumber(byte[] byteArray, int length) {
        int n = 0;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(byteArray);
            DataInputStream dataInput = new DataInputStream(byteInput);
            switch (length) {
                case 1:
                    n = dataInput.readByte();
                    break;
                case 4:
                    n = dataInput.readInt();
                    break;
            }
            dataInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    public static int byteArrayToNumber(byte[] byteArray) {
        int n = 0;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(byteArray);
            DataInputStream dataInput = new DataInputStream(byteInput);
            switch (byteArray.length) {
                case 1:
                    n = dataInput.readByte();
                    break;
                case 4:
                    n = dataInput.readInt();
                    break;
            }
            dataInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    public final static int DEVICE_TYPE_ZIGBEE_SWITCH_PANEL = 6;
    public final static int DEVICE_TYPE_ZIGBEE = 31;
    public final static int DEVICE_TYPE_CURTAIN = 51;
    public final static int DEVICE_TYPE_ACMOTOR = 52;
    public final static int DEVICE_TYPE_SMOKE = 71;
    public final static int DEVICE_TYPE_SINGLE = 91;
    public final static int DEVICE_TYPE_IR_GATE = 101;
    public final static int DEVICE_TYPE_RF_GATE = 111;
    public final static int DEVICE_TYPE_RF_DOOR_GATE = 121;

    public final static int DEVICE_TYPE_CHANNEL = 1;

    public final static int ORIGINAL_PRODUCT = 1;
    public final static int ORIGINAL_CONTROL_DEVICE = 2;
    public final static int ORIGINAL_CONTROL_EQUIPMENT = 3;

    public final static int ACTION_KEY = 1;
    public final static int ACTION_SCENE = 2;
    public final static int ACTION_TASK = 3;
    public final static int ACTION_LINKAGE = 4;

    public final static int FEATURE_POWER = 5;
    public final static int FEATURE_CURTAIN = 8;
    public final static int FEATURE_CURTAIN_OPEN = 201;
    public final static int FEATURE_CURTAIN_CLOSE = 202;
    public final static int FEATURE_CURTAIN_STOP = 200;
    public final static int FEATURE_CURTAIN_REVERSAL = 9;
    public final static int FEATURE_CURTAIN_RESET = 10;
    public final static int FEATURE_CURTAIN_TIME = 22;
    public final static int FEATURE_DIM = 11;
    public final static int FEATURE_LEARN = 13;
    public final static int FEATURE_STATE = 14;
    public final static int FEATURE_SEARCH = 16;
    public final static int FEATURE_DOOR = 17;
}
