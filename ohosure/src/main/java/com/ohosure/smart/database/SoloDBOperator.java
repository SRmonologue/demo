package com.ohosure.autobee.home.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.core.App;
import com.core.HSmartProvider;

public class SoloDBOperator {

    private static SoloDBOperator instance;

    public static SoloDBOperator getInstance() {
        if (instance == null)
            instance = new SoloDBOperator();
        return instance;
    }

    private SoloDBOperator() {

    }

    public void insertOrUpdatePtHome(String gateMac, long lastTime) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.Home.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.Home._ID},
                        HSmartProvider.MetaData.Home.GATE_MAC + "=?",
                        new String[]{gateMac}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.Home.LAST_TIME, lastTime);
            App.getContext()
                    .getContentResolver()
                    .update(HSmartProvider.MetaData.Home.CONTENT_URI, cv,
                            HSmartProvider.MetaData.Home.GATE_MAC + "=?",
                            new String[]{gateMac});
        } else {
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.Home.GATE_MAC, gateMac);
            cv.put(HSmartProvider.MetaData.Home.LAST_TIME, lastTime);

            App.getContext().getContentResolver()
                    .insert(HSmartProvider.MetaData.Home.CONTENT_URI, cv);
        }

    }


    public void insertOrUpdatePtControlDevice(long controlDeviceId,
                                              String controlDeviceName, long roomAreaId, int controlDeviceType) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.ControlDevice.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.ControlDevice._ID},
                        HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_ID
                                + "=?",
                        new String[]{String.valueOf(controlDeviceId)}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_NAME,
                    controlDeviceName);
            cv.put(HSmartProvider.MetaData.ControlDevice.ROOM_AREA_ID,
                    roomAreaId);
            cv.put(HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_TYPE,
                    controlDeviceType);
            App.getContext()
                    .getContentResolver()
                    .update(HSmartProvider.MetaData.ControlDevice.CONTENT_URI,
                            cv,
                            HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_ID
                                    + "=?",
                            new String[]{String.valueOf(controlDeviceId)});
        } else {
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_ID,
                    controlDeviceId);
            cv.put(HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_NAME,
                    controlDeviceName);
            cv.put(HSmartProvider.MetaData.ControlDevice.ROOM_AREA_ID,
                    roomAreaId);
            cv.put(HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_TYPE,
                    controlDeviceType);

            App.getContext()
                    .getContentResolver()
                    .insert(HSmartProvider.MetaData.ControlDevice.CONTENT_URI,
                            cv);
        }

    }


    public void insertOrUpdatePtProduct(long productId, int productType,
                                        String productName, long roomAreaId) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.Product.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.Product._ID},
                        HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                        new String[]{String.valueOf(productId)}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_TYPE, productType);
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_NAME, productName);
            cv.put(HSmartProvider.MetaData.Product.ROOM_AREA_ID, roomAreaId);
            App.getContext()
                    .getContentResolver()
                    .update(HSmartProvider.MetaData.Product.CONTENT_URI, cv,
                            HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                            new String[]{String.valueOf(productId)});
        } else {
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_ID, productId);
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_TYPE, productType);
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_NAME, productName);
            cv.put(HSmartProvider.MetaData.Product.ROOM_AREA_ID, roomAreaId);

            App.getContext().getContentResolver()
                    .insert(HSmartProvider.MetaData.Product.CONTENT_URI, cv);
        }

    }

    public void updatePtProduct(long productId,
                                String productName, long roomAreaId) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.Product.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.Product._ID},
                        HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                        new String[]{String.valueOf(productId)}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.Product.PRODUCT_NAME, productName);
            cv.put(HSmartProvider.MetaData.Product.ROOM_AREA_ID, roomAreaId);
            App.getContext()
                    .getContentResolver()
                    .update(HSmartProvider.MetaData.Product.CONTENT_URI, cv,
                            HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                            new String[]{String.valueOf(productId)});
        }

    }

    public void updatePtProductSceneConfig(long productId,
                                           long sceneId) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.Product.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.Product._ID},
                        HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
                        new String[]{String.valueOf(productId)}, null);

//		if (cursor != null && cursor.getCount() > 0) {
//			cursor.close();
//			ContentValues cv = new ContentValues(); 
//			cv.put(HSmartProvider.MetaData.Product.SCENE_ID_CONFIG_TEMP, sceneId); 
//			App.getContext()
//					.getContentResolver()
//					.update(HSmartProvider.MetaData.Product.CONTENT_URI, cv,
//							HSmartProvider.MetaData.Product.PRODUCT_ID + "=?",
//							new String[] { String.valueOf(productId) });
//		}  

    }

    public void updatePtSceneProduct(long productId,
                                     long sceneId, int status) {

//			ContentValues cv = new ContentValues(); 
//			cv.put(HSmartProvider.MetaData.SceneProduct.CONFIG_STATUS, status); 
//			App.getContext()
//					.getContentResolver()
//					.update(HSmartProvider.MetaData.SceneProduct.CONTENT_URI, cv,
//							HSmartProvider.MetaData.SceneProduct.PRODUCT_ID + "=? and "
//							+HSmartProvider.MetaData.SceneProduct.SCENE_ID + "=? ",
//							new String[] { String.valueOf(productId),String.valueOf(sceneId) });
//		 

    }

    public void insertOrUpdatePtRoomArea(long roomAreaId, String roomAreaName,
                                         String roomAreaDescription, int floor) {

        Cursor cursor = App
                .getContext()
                .getContentResolver()
                .query(HSmartProvider.MetaData.RoomArea.CONTENT_URI,
                        new String[]{HSmartProvider.MetaData.RoomArea._ID},
                        HSmartProvider.MetaData.RoomArea.ROOM_AREA_ID + "=?",
                        new String[]{String.valueOf(roomAreaId)}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.RoomArea.ROOM_AREA_NAME,
                    roomAreaName);
            cv.put(HSmartProvider.MetaData.RoomArea.ROOM_AREA_DESCRIPTION,
                    roomAreaDescription);
            cv.put(HSmartProvider.MetaData.RoomArea.FLOOR, floor);
            App.getContext()
                    .getContentResolver()
                    .update(HSmartProvider.MetaData.RoomArea.CONTENT_URI,
                            cv,
                            HSmartProvider.MetaData.RoomArea.ROOM_AREA_ID
                                    + "=?",
                            new String[]{String.valueOf(roomAreaId)});
        } else {

            ContentValues cv = new ContentValues();
            cv.put(HSmartProvider.MetaData.RoomArea.ROOM_AREA_ID, roomAreaId);
            cv.put(HSmartProvider.MetaData.RoomArea.ROOM_AREA_NAME,
                    roomAreaName);
            cv.put(HSmartProvider.MetaData.RoomArea.ROOM_AREA_DESCRIPTION,
                    roomAreaDescription);
            cv.put(HSmartProvider.MetaData.RoomArea.FLOOR, floor);

            App.getContext().getContentResolver()
                    .insert(HSmartProvider.MetaData.RoomArea.CONTENT_URI, cv);
        }

    }


    public int deleteFromPtControlDevice(long controlDeviceId) {
        return App
                .getContext()
                .getContentResolver()
                .delete(HSmartProvider.MetaData.ControlDevice.CONTENT_URI,
                        HSmartProvider.MetaData.ControlDevice.CONTROL_DEVICE_ID
                                + "=?",
                        new String[]{String.valueOf(controlDeviceId)});
    }

    public int deleteFromPtRoomArea(long roomAreaId) {
        return App
                .getContext()
                .getContentResolver()
                .delete(HSmartProvider.MetaData.RoomArea.CONTENT_URI,
                        HSmartProvider.MetaData.RoomArea.ROOM_AREA_ID + "=?",
                        new String[]{String.valueOf(roomAreaId)});
    }
}

