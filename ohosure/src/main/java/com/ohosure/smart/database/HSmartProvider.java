package com.ohosure.smart.database;

import android.net.Uri;

import com.ohosure.smart.database.devkit.sqlite.BaseDBProvider;


public class HSmartProvider extends BaseDBProvider {

    private static final String TAG = HSmartProvider.class.getName();

    public static class MetaData {


        public static class Home {
            public final static String TABLE = "pt_home";
            public final static Uri CONTENT_URI = Uri.parse("content://"
                    + AUTHORITY + "/" + TABLE);
            public final static String _ID = "_id";
            public final static String GATE_MAC = "gate_mac";
            public final static String LAST_TIME = "last_time";
            public final static String CONFIGURATION = "configuration";
            public final static String GATE_VERSION = "gate_version";
        }

        public static class RoomArea {
            public final static String TABLE = "pt_roomArea";
            public final static Uri CONTENT_URI = Uri.parse("content://"
                    + AUTHORITY + "/" + TABLE);
            public final static String _ID = "_id";
            public final static String ROOM_AREA_ID = "room_area_id";
            public final static String ROOM_AREA_NAME = "room_area_name";
            public final static String ROOM_AREA_DESCRIPTION = "room_area_description";
            public final static String FLOOR = "floor";
        }

        public static class Product {
            public final static String TABLE = "pt_product";
            public final static Uri CONTENT_URI = Uri.parse("content://"
                    + AUTHORITY + "/" + TABLE);
            public final static String _ID = "_id";
            public final static String PRODUCT_ID = "product_id";
            public final static String PRODUCT_TYPE = "product_type";
            public final static String PRODUCT_NAME = "product_name";
            public final static String ROOM_AREA_ID = "room_area_id";
            public final static String PRODUCT_STATE = "product_state";
            public final static String SW_VERSION = "sw_version";
            public final static String HW_VERSION = "hw_version";
        }


        public static class ControlDevice {
            public final static String TABLE = "pt_controlDevice";
            public final static Uri CONTENT_URI = Uri.parse("content://"
                    + AUTHORITY + "/" + TABLE);
            public final static String _ID = "_id";
            public final static String CONTROL_DEVICE_ID = "control_device_id";
            public final static String CONTROL_DEVICE_NAME = "control_device_name";
            public final static String ROOM_AREA_ID = "room_area_id";
            public final static String CONTROL_DEVICE_TYPE = "control_device_type";
            public final static String PRODUCT_ID = "product_id";
        }

        public static class RealData {
            public final static String TABLE = "pt_realData";
            public final static Uri CONTENT_URI = Uri.parse("content://"
                    + AUTHORITY + "/" + TABLE);
            public final static String _ID = "_id";
            public final static String DEVICE_ID = "device_id";
            public final static String DEVICE_TYPE = "device_type";
            public final static String DEVICE_ORIGINAL = "device_original";
            public final static String FEATURE_TYPE = "feature_type";
            public final static String FEATURE_VALUE = "feature_value";

        }


    }

    @Override
    public Class<?> getChildClass() {
        // TODO Auto-generated method stub
        return HSmartProvider.class;
    }

    @Override
    public Class<?> getChildMetaDataClass() {
        // TODO Auto-generated method stub
        return MetaData.class;
    }

    @Override
    public Object getChildMetaData() {
        // TODO Auto-generated method stub
        return new MetaData();
    }

    @Override
    public String getChildTag() {

        // TODO Auto-generated method stub
        return TAG;
    }

}
