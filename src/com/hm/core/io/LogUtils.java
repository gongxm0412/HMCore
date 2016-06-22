package com.hm.core.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import com.hm.core.date.DateUtil;

import android.os.Environment;
import android.util.Log;

/**
 * @author liyc
 * @time 2014-6-6 下午5:12:42
 * @annotation
 */
public class LogUtils {

	public static void Log2Storage(String log) {
		// Log.i("EMTest", log);

		String LogPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/log";
		try {
			String logName = DateUtil.convertDate(new Date(), "yyyy-MM-dd") + ".log";
			File file = new File(LogPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			PrintWriter pw = new PrintWriter(
					new BufferedOutputStream(new FileOutputStream(LogPath + "/" + logName, true)));
			pw.write("<EMLog>");
			String dataTag = "<time>" + DateUtil.convertDate(new Date(), DateUtil.DateFormat_24) + "</time>";
			pw.write(dataTag);
			pw.write(log);
			pw.write("</EMLog>");
			pw.write("\r\n");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void Log2Storage(String log, String filepath) {
		// Log.i("EMTest", log);
		String LogPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/log/" + filepath;
		try {
			String logName = DateUtil.convertDate(new Date(), "yyyy-MM-dd") + ".log";
			File file = new File(LogPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			PrintWriter pw = new PrintWriter(
					new BufferedOutputStream(new FileOutputStream(LogPath + "/" + logName, true)));
			pw.write("<EMLog>");
			String dataTag = "<time>" + DateUtil.convertDate(new Date(), DateUtil.DateFormat_24) + "</time>";
			pw.write(dataTag);
			pw.write(log);
			pw.write("</EMLog>");
			pw.write("\r\n");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
