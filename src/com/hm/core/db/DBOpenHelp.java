package com.hm.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelp extends SQLiteOpenHelper {

	public static final int VERSION = 1;
	public static final String DBName = "hm_DB";
	// 下载进程表name
	public static final String ThreadTableName = "thread_info";
	public static final String OrderTableName = "order_info";

	public DBOpenHelp(Context context) {
		super(context, DBName, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 断点续传表
		// 下载进程的表
		db.execSQL("create table " + ThreadTableName + " (_id integer auto_increment," + "thread_id integer,"
				+ "url text," + "start integer," + "end integer," + "finished integer" + ")");

		// 指令保存表
		db.execSQL("create table " + OrderTableName 
				+ "(" 
				+ "rowguid text," 
				+ "cmd_sn text," 
				+ "cmd_type text," 
				+ "sql_type text," 
				+ "order_detail text,"
				+ "isfinished text" + ")");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
