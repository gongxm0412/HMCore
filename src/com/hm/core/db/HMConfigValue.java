package com.hm.core.db;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 应用配置键值对
 * 
 * @author gongxm 用 SharedPreferences 实现
 */
public class HMConfigValue {
	/**
	 * SharedPreferences名字 默认 hmconfig
	 */
	private static String SharedPreferencesName = "hmconfig";

	/**
	 * 获取具体配置项
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @param sharename
	 */
	public static void setConfigValue(Context context, String key, String value, String sharename) {
		if (sharename.equals("")) {
			sharename = SharedPreferencesName;
		}
		SharedPreferences settings = context.getSharedPreferences(sharename, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key.toLowerCase(), value);
		editor.commit();
		Log.i("readfile", "key:" + key + "\n" + "value:" + value);
	}

	/**
	 * 获取配置项
	 * 
	 * @param context
	 * @param key
	 * @param sharename
	 * @return
	 */
	public static String getConfigValue(Context context, String key, String sharename) {
		String value = "";
		if (sharename.equals("")) {
			sharename = SharedPreferencesName;
		}
		SharedPreferences settings = context.getSharedPreferences(sharename, 0);
		value = settings.getString(key.toLowerCase(), "");
		return value;
	}

	/**
	 * 获取所有配置项值
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, String> getAllConfigValue(Context context, String sharename) {
		if (sharename.equals("")) {
			sharename = SharedPreferencesName;
		}
		SharedPreferences settings = context.getSharedPreferences(sharename, 0);
		Map<String, String> allContent = (Map<String, String>) settings.getAll();
		return allContent;
	}

}
