package com.hm.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作帮组类
 * 
 * @author gongxm
 *
 */
public class DBUtils {
	private DBOpenHelp dbopen = null;
	private DBUtils dbutils = null;

	private DBUtils(Context context) {
		dbopen = new DBOpenHelp(context);
		dbopen = new DBOpenHelp(context);
	}

	public DBUtils getInstance(Context context) {
		if (dbutils == null) {
			dbutils = new DBUtils(context);
		}
		return dbutils;
	}

 

}
