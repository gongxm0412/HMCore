package com.hm.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;

import android.util.Log;

public class FileHelp {
	
	
	
	public static void main(String[] args) {
//		FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pset.ini";
//		Log.i("filePath", FilePath);
//		readFile();
	}

	// 读取文件
	public static void readFile(final String FilePath) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					File file = new File(FilePath, "");
					BufferedReader br = new BufferedReader(new FileReader(file));
					String readline = "";
					StringBuffer sb = new StringBuffer();
					while ((readline = br.readLine()) != null) {
//						System.out.println("readline:" + readline);
//						sb.append(readline);
						
						
					}
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	
	/**
	 * 文字格式转换
	 */
	public void wenzizhuanhuan(String bs){
		try {
			byte [] bsbytes = bs.getBytes("utf-8");
			bs = new String(bsbytes,"utf-8");
			Log.i("bs", bs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
