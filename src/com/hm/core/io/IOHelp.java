package com.hm.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


/**
 * @author liyc
 * @time @2013-5-5 下午11:28:10
 * @annomation io帮助类
 */
public class IOHelp {

	/**
	 * 文件转base64
	 */
	public static String File2Base64(File f) {
		byte[] b = IOHelp.File2Byte(f);
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	// Bitmap转为二进制
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// 对象转为二进制
	public static byte[] obj2bytes(Object obj) throws IOException {
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		return fos.toByteArray();
	}

	// 二进制转为对象
	public static Object bytes2obj(byte[] b) throws OptionalDataException,
			ClassNotFoundException, IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}

	// 向一个文本中续写数据
	public static void TextFileContinueWrite(String path, String log) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(
					new FileOutputStream(path, true)));
			pw.write("\r\n");
			pw.write(log);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 流转为二进制
	public static byte[] InputStreamToByte(InputStream is) {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 文件转二进制
	 */
	public static byte[] File2Byte(File f) {
		try {
			InputStream is = new FileInputStream(f);
			return InputStreamToByte(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 创建文件夹
	public static void FoldCreate(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	/**
	 * 删除文件，包括子文件夹
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	}


	/**
	 * 拷贝文件
	 * 
	 * @param con
	 * @param assfilename
	 * @param destpathname
	 * @throws IOException
	 */
	public static void copyFile(String assfilename, String destpathname)
			throws IOException {
		FileInputStream fis = new FileInputStream(assfilename);
		FileOutputStream fos = new FileOutputStream(destpathname);
		byte data[] = new byte[1024];
		int count;
		while ((count = fis.read(data)) != -1) {
			fos.write(data, 0, count);
		}
		fos.flush();
		fos.close();
		fis.close();
	}

	// 读取文件中字符串
	public static String readtxt(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str = "";
			String r = br.readLine();
			while (r != null) {
				str += r;
				r = br.readLine();
			}
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将文本写入某路径
	 */
	public static void writeText2Path(String path, String msg) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(msg.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存文件
	 */
	public static boolean saveFile(byte[] b, String filepath) {
		try {
			File f = new File(filepath);

			File pdir = new File(f.getParent());
			if (!pdir.exists()) {
				pdir.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(filepath);
			out.write(b);
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 拷贝文件
	public static void copyFileTemp(String src, String dest) {
		InputStream is = null;
		OutputStream os = null;
		char ch[] = src.toCharArray();

		// ************新添加的代码,获取文件名**********
		int pos = 0;
		for (int i = ch.length - 1; i >= 0; i--) {
			if (ch[i] == '\\') {
				if (i > pos)
					pos = i;
			}
		}
		String temp = src.substring(pos);
		dest = dest + temp;
		System.out.println("dest=" + dest);

		try {
			is = new BufferedInputStream(new FileInputStream(src));
			os = new BufferedOutputStream(new FileOutputStream(dest));

			byte[] b = new byte[1024];
			int len = 0;

			try {
				while ((len = is.read(b)) != -1) {
					os.write(b, 0, len);
				}
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从assets拷贝文件到文件夹
	 * 
	 * @param con
	 * @param assfilename
	 * @param destpathname
	 * @throws IOException
	 */
	public static void copyFileFromAss2Dir(Context con, String assfilename,
			String destpathname) throws IOException {
		InputStream fis = con.getResources().getAssets().open(assfilename);
		FileOutputStream fos = new FileOutputStream(destpathname);
		byte data[] = new byte[1024];
		int count;
		while ((count = fis.read(data)) != -1) {
			fos.write(data, 0, count);
		}
		fos.flush();
		fos.close();
		fis.close();
	}

	public static String do_exec(String cmd) {
		String s = "/n";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				s += line + "/n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmd + "result=" + s;
	}

	public static String throwException2String(Throwable ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		ex.printStackTrace(printStream);
		byte[] data = baos.toByteArray();
		final String info = new String(data);
		data = null;
		printStream.close();
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
}
