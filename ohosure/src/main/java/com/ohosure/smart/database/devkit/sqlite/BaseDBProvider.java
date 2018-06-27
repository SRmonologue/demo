package com.ohosure.smart.database.devkit.sqlite;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.ohosure.smart.database.devkit.constant.Const;
import com.ohosure.smart.database.devkit.log.MLog;

import java.lang.reflect.Field;
import java.util.Vector;


public abstract class BaseDBProvider extends ContentProvider implements
		ProviderParam {

	protected String DB_NAME;
	protected int VERSION;
	protected static String AUTHORITY;
	protected UriMatcher matcher;
	protected Vector<String> tables = new Vector<String>();
	protected Context mContext;

	private DBHelper dbHelper;
	private SQLiteDatabase db;

	 public DBHelper getDbHelper() {
		return dbHelper;
	}
	
	@Override
	public boolean onCreate() {
		mContext = this.getContext();
		tables.clear();
		initParam(getChildClass());
		MLog.i(getChildTag(), "ContentProvider onCreate");
		MLog.d(getChildTag(), DB_NAME);
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		for (int i = 0; i < tables.size(); i++) {
			if (i % 2 == 0)
				matcher.addURI(AUTHORITY, tables.get(i) + "/#", i);
			else
				matcher.addURI(AUTHORITY, tables.get(i), i);
		}

		dbHelper = new DBHelper(this.getContext(), DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		return true;
	}

	//供外部应用从ContentProvider中获取数据
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
		MLog.i(getChildTag(), "db QUERY");
		MLog.d(getChildTag(), "uri:" + uri.toString());
		Cursor cursor = null;
		int tableCode = matcher.match(uri);
		if (tableCode != UriMatcher.NO_MATCH) {
			if (tableCode % 2 == 0) {
				long _id = ContentUris.parseId(uri);
				selection = TextUtils.isEmpty(selection) ? " _id=" + _id
						: selection + " and _id=" + _id;
			}
			String tableName = tables.get(tableCode);
			cursor = db.query(tableName, projection, selection, selectionArgs,
					null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		} else {
			throw new IllegalArgumentException("uri unknown " + uri
					+ "\nmaybe not exists");
		}
	}

	//返回当前Uri所代表数据的MIME类型
	@Override
	public String getType(Uri uri) {
		MLog.i(getChildTag(), "getType");
		MLog.d(getChildTag(), "uri:" + uri.toString());
		int tableCode = matcher.match(uri);
		if (tableCode != UriMatcher.NO_MATCH) {
			if (tableCode % 2 == 0) {
				return "vnd.android.cursor.item/" + tables.get(tableCode);
			} else {
				return "vnd.android.cursor.dir/" + tables.get(tableCode);
			}
		} else {
			throw new IllegalArgumentException("uri unknown " + uri
					+ "\nmaybe not exists");
		}

	}

	//供外部应用向ContentProvider添加数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		MLog.i(getChildTag(), "db INSERT");
		MLog.d(getChildTag(), "uri:" + uri.toString());
		int tableCode = matcher.match(uri);
		if (tableCode != UriMatcher.NO_MATCH) {
			String tableName = tables.get(tableCode);
			long rowId = db.insert(tableName, null, values);
			if (rowId > 0) {
				Uri wordUri = ContentUris.withAppendedId(uri, rowId);
				getContext().getContentResolver().notifyChange(uri, null);
				return wordUri;
			}
			throw new SQLException("failed to insert " + uri);
		} else {
			throw new IllegalArgumentException("uri unknown " + uri
					+ "\nmaybe not exists");
		}
	}

	//供外部应用从ContentProvider删除数据
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		MLog.i(getChildTag(), "db DELETE");
		MLog.d(getChildTag(), "uri:" + uri.toString());
		int tableCode = matcher.match(uri);
		if (tableCode != UriMatcher.NO_MATCH) {
			if (tableCode % 2 == 0) {
				long _id = ContentUris.parseId(uri);
				selection = TextUtils.isEmpty(selection) ? " _id=" + _id
						: selection + " and _id=" + _id;
			}
			String tableName = tables.get(tableCode);
			int referNum = db.delete(tableName, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return referNum;
		} else {
			throw new IllegalArgumentException("uri unknown " + uri
					+ "\nmaybe not exists");
		}
	}

	//供外部应用更新ContentProvider中的数据
	@Override
	public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
		MLog.i(getChildTag(), "db UPDATE");
		MLog.d(getChildTag(), "uri:" + uri.toString());
		int tableCode = matcher.match(uri);
		if (tableCode != UriMatcher.NO_MATCH) {
			if (tableCode % 2 == 0) {
				long _id = ContentUris.parseId(uri);
				selection = TextUtils.isEmpty(selection) ? " _id=" + _id
						: selection + " and _id=" + _id;
			}
			String tableName = tables.get(tableCode);
			int referNum = db.update(tableName, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return referNum;
		} else {
			throw new IllegalArgumentException("uri unknown " + uri
					+ "\nmaybe not exists");
		}
	}

	private void initParam(Class<?> cls) {
		ComponentName cn = new ComponentName(mContext, cls);
		try {
			ProviderInfo info = mContext.getPackageManager().getProviderInfo(
					cn, PackageManager.GET_META_DATA);
			VERSION = info.metaData.getInt(Const.DB_VERSION);
			AUTHORITY = info.metaData.getString(Const.DB_AUTHORITY);
			DB_NAME = info.metaData.getString(Const.DB_NAME);
		} catch (NameNotFoundException e) {
			MLog.e(getChildTag(), e.getMessage());
			try {
				throw new Exception(
						"authority undefined in manifest,please check");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		try {
			Class<?> c = getChildMetaDataClass();

			for (Class<?> classz : c.getClasses()) {
				Field field = classz.getField("TABLE");
				String tableName = String
						.valueOf(field.get(getChildMetaData()));
				tables.add(tableName);
				tables.add(tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
