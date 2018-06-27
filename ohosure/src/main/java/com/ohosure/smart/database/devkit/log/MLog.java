package com.ohosure.smart.database.devkit.log;

import android.util.Log;

import com.ohosure.smart.database.devkit.constant.Const;


public class MLog {

	public static void v(String tag, String info) {
		if (Const.DEBUG)
			Log.v(Const.TAG_PREFIX + tag, info);
	}

	public static void d(String tag, String info) {
		if (Const.DEBUG)
			Log.d(Const.TAG_PREFIX + tag, info);
	}

	public static void i(String tag, String info) {
		if (Const.DEBUG)
		Log.i(Const.TAG_PREFIX + tag, info);
	}

	public static void w(String tag, String info) {

		Log.w(Const.TAG_PREFIX + tag, info);
	}

	public static void e(String tag, String info) {

		Log.e(Const.TAG_PREFIX + tag, info);
	}

}
