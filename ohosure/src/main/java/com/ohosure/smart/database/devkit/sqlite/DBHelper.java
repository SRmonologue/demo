package com.ohosure.smart.database.devkit.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.ohosure.smart.database.devkit.log.MLog;

import java.util.Vector;



public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getName();
    private StringBuilder dbCreateStringBuilder;
    private Vector<String> dbModifyList;
    private Context mContext;
    private static final String DATABASE_INIT_FILE = "create.SQL";
    private static final String DATABASE_MODIFY_FILE = "modify?.SQL";
    private static final boolean DEV_MODE = true;

    @SuppressLint("NewApi")
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.mContext = context;
    }

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
//			dbCreateStringBuilder = FileOperator.readStringFromAssets(mContext,
//					DATABASE_INIT_FILE);
            dbCreateStringBuilder = new StringBuilder();
            dbCreateStringBuilder.append("pragma foreign_keys = off;");
            dbCreateStringBuilder.append("drop table if exists [pt_controlDevice];");
            dbCreateStringBuilder.append("drop table if exists [pt_home];");
            dbCreateStringBuilder.append("CREATE TABLE [pt_home] (");
            dbCreateStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
            dbCreateStringBuilder.append("  [gate_mac] TEXT, ");
            dbCreateStringBuilder.append("  [last_time] TEXT, ");
            dbCreateStringBuilder.append("  [configuration] INTEGER, ");
            dbCreateStringBuilder.append("  [gate_version] TEXT);");
            dbCreateStringBuilder.append("drop table if exists [pt_roomArea];");
            dbCreateStringBuilder.append("CREATE TABLE [pt_roomArea] (");
            dbCreateStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
            dbCreateStringBuilder.append("  [room_area_id] INTEGER, ");
            dbCreateStringBuilder.append("  [room_area_name] TEXT, ");
            dbCreateStringBuilder.append("  [room_area_description] TEXT, ");
            dbCreateStringBuilder.append("  [floor] INTEGER);");
            dbCreateStringBuilder.append("drop table if exists [pt_product];");
            dbCreateStringBuilder.append("CREATE TABLE [pt_product] (");
            dbCreateStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
            dbCreateStringBuilder.append("  [product_id] INTEGER, ");
            dbCreateStringBuilder.append("  [product_type] INTEGER, ");
            dbCreateStringBuilder.append("  [product_name] TEXT, ");
            dbCreateStringBuilder.append("  [room_area_id] INTEGER, ");
            dbCreateStringBuilder.append("  [product_state] INTEGER, ");
            dbCreateStringBuilder.append("  [sw_version] TEXT, ");
            dbCreateStringBuilder.append("  [hw_version] TEXT);");
            dbCreateStringBuilder.append("CREATE TABLE [pt_controlDevice] (");
            dbCreateStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
            dbCreateStringBuilder.append("  [control_device_id] INTEGER, ");
            dbCreateStringBuilder.append("  [control_device_name] TEXT, ");
            dbCreateStringBuilder.append("  [product_id] INTEGER, ");
            dbCreateStringBuilder.append("  [room_area_id] INTEGER, ");
            dbCreateStringBuilder.append("  [control_device_type] INTEGER);");
            dbCreateStringBuilder.append("drop table if exists [pt_realData];");
            dbCreateStringBuilder.append("CREATE TABLE [pt_realData] (");
            dbCreateStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
            dbCreateStringBuilder.append("  [device_id] INTEGER, ");
            dbCreateStringBuilder.append("  [device_type] INTEGER, ");
            dbCreateStringBuilder.append("  [device_original] INTEGER, ");
            dbCreateStringBuilder.append("  [feature_type] INTEGER, ");
            dbCreateStringBuilder.append("  [feature_value] INTEGER);");
            dbCreateStringBuilder.append("pragma foreign_keys = on");

            db.beginTransaction();
            for (String sql : dbCreateStringBuilder.toString().split(";")) {
                db.execSQL(sql);

            }

            db.setTransactionSuccessful();
            MLog.d(TAG, "sqlscript length is " + dbCreateStringBuilder.length());
            MLog.v(TAG, dbCreateStringBuilder.toString());
            MLog.i(TAG, "datebase create success!");

        } catch (Exception e) {
            MLog.e(TAG, "datebase create failed! sqlscript length is "
                    + dbCreateStringBuilder.length());
            MLog.e(TAG, e.getMessage());

        }finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // if (DEV_MODE) {
        // StringBuilder dbModifyStringBuilder = new StringBuilder();
        // dbModifyList = new Vector<String>();
        //
        // for (int i = oldVersion; i < newVersion; i++) {
        // dbModifyList.add(DATABASE_MODIFY_FILE.replace("?",
        // String.valueOf(i + 1)));
        // }
        //
        // try {
        // for (String filename : dbModifyList) {
        // dbModifyStringBuilder = FileOperator.readStringFromAssets(
        // mContext, filename);
        //
        // for (String sql : dbModifyStringBuilder.toString().split(
        // ";")) {
        // db.execSQL(sql);
        // }
        // MLog.d(TAG,
        // "sqlscript length is "
        // + dbModifyStringBuilder.length());
        // MLog.v(TAG, dbModifyStringBuilder.toString());
        // MLog.d(TAG, "excute filename is " + filename);
        // MLog.i(TAG, "datebase modify success!");
        // }
        //
        // } catch (Exception e) {
        // MLog.e(TAG, "dateBase modify failed! sqlscript length is "
        // + dbModifyStringBuilder.length() + " oldversion is "
        // + oldVersion + " newversion is " + newVersion);
        // MLog.e(TAG, e.getMessage());
        //
        // }
        //
        // } else {
        // // release mode
        // // to be continue...
        // }

        StringBuilder dbModifyStringBuilder = new StringBuilder();
        switch (oldVersion) {
            case 1:
                dbModifyStringBuilder
                        .append("alter table pt_home add gate_version TEXT;");
                dbModifyStringBuilder
                        .append("alter table pt_product add sw_version TEXT;");
                dbModifyStringBuilder
                        .append("alter table pt_product add hw_version TEXT;");
            case 2:
                dbModifyStringBuilder
                        .append("alter table pt_controlDevice add product_id INTEGER;");
                dbModifyStringBuilder.append("CREATE TABLE [pt_realData] (");
                dbModifyStringBuilder.append("  [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
                dbModifyStringBuilder.append("  [device_id] INTEGER, ");
                dbModifyStringBuilder.append("  [device_type] INTEGER, ");
                dbModifyStringBuilder.append("  [device_original] INTEGER, ");
                dbModifyStringBuilder.append("  [feature_type] INTEGER, ");
                dbModifyStringBuilder.append("  [feature_value] INTEGER);");
                dbModifyStringBuilder.append("drop table if exists [pt_channel];");
                dbModifyStringBuilder.append("drop table if exists [pt_channelFeature];");
                dbModifyStringBuilder.append("drop table if exists [pt_key];");
                dbModifyStringBuilder.append("drop table if exists [pt_scene];");
                dbModifyStringBuilder.append("drop table if exists [pt_sceneConfig];");
            default:
                break;
        }

        try {
            db.beginTransaction();
            for (String sql : dbModifyStringBuilder.toString().split(";")) {
                if (sql.length() > 1)
                    db.execSQL(sql);
            }
            MLog.d(TAG, "sqlscript length is " + dbModifyStringBuilder.length());
            MLog.v(TAG, dbModifyStringBuilder.toString());
            MLog.i(TAG, "datebase modify success!" + " oldversion is "
                    + oldVersion + " newversion is " + newVersion);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            MLog.e(TAG, "dateBase modify failed! sqlscript length is "
                    + dbModifyStringBuilder.length() + " oldversion is "
                    + oldVersion + " newversion is " + newVersion);
            e.printStackTrace();

        }finally {
            db.endTransaction();
        }
    }


}
