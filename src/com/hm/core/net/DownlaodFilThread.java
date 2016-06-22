package com.hm.core.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * 下载帮助类
 * 
 * @author gongxm
 *
 */
public class DownlaodFilThread extends Thread {

	/**
	 * 文件下载URL
	 */
	String DownloadUrl = "";
	/**
	 * 文件保存地址
	 */
	String FilePath = "";
	/**
	 * 下载进度条
	 */
	private ProgressDialog progressDialog;
	private Handler handler = new Handler();
	private HttpClient httpClient;

	private Context context;
	/**
	 * 下载完成接口
	 */
	DownloadCompleted notify;
	/**
	 * 是否显示进度条
	 */
	Boolean isShowprogress = false;

	int fileSize = 0 ;
	
	/**
	 * 
	 * @author gongxm
	 * @param mcon
	 *            上下文
	 * @param url
	 *            下载URL
	 * @param filepath
	 *            文件保存地址
	 * @param mdown
	 *            下载完成回调
	 */
	public DownlaodFilThread(Context mcon, String url, String filepath, DownloadCompleted mdown) {
		this.context = mcon;
		this.DownloadUrl = url;
		this.FilePath = filepath;
		this.notify = mdown;
		this.isShowprogress = false;
		init();
	}

	/**
	 * @author gongxm
	 * @param mcon
	 *            上下文
	 * @param url
	 *            下载URL
	 * @param filepath
	 *            文件保存地址
	 * @param mdown
	 *            下载完成回调
	 * @param isshowwait
	 *            是否显示下载进度 默认不显示
	 */
	public DownlaodFilThread(Context mcon, String url, String filepath, DownloadCompleted mdown, boolean isshowwait) {
		this.context = mcon;
		this.DownloadUrl = url;
		this.FilePath = filepath;
		this.notify = mdown;
		this.isShowprogress = isshowwait;
		init();
	}

	public void init() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				System.out.println("中断下载！");
				httpClient.getConnectionManager().shutdown();
			}
		});
		File f = new File(FilePath);
		File fdir = new File(f.getParent());
		if (!fdir.exists()) {
			fdir.mkdirs();
		}
	}

	@Override
	public void run() {
		try {
			handler.post(new Runnable() {

				@Override
				public void run() {
					if (isShowprogress) {
						progressDialog.show();
					}
				}
			});

			httpClient = new DefaultHttpClient();

			// 自动跳转
			httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, true);
			HttpGet httpGet = new HttpGet(DownloadUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception("文件下载失败:" + httpResponse.getStatusLine().getStatusCode());
			}

			HttpEntity entity = httpResponse.getEntity();
			final long fileLength = entity.getContentLength();
			System.out.println("remote file length:" + fileLength);

			if (fileLength == 0) {
				throw new Exception("服务器文件为空");
			}

			int count;
			InputStream input = entity.getContent();
			OutputStream output = new FileOutputStream(FilePath);

			fileSize = 0;
			byte data[] = new byte[1024];
			int index  =1;
			while ((count = input.read(data)) != -1) {
				fileSize += count;
				output.write(data, 0, count);
				Log.i("filelength", String.valueOf(index++));
				handler.post(new Runnable() {
					@Override
					public void run() {
						progressDialog.setProgress((int) (fileSize / fileLength) * 100);
					}
				});
			}
			output.flush();
			output.close();
			input.close();
			notify.downloadComleted(FilePath, true);
		} catch (Exception e) {
			e.printStackTrace();
			notify.downloadComleted("error:"+e.toString(), false);
		} finally {
			handler.post(new Runnable() {

				@Override
				public void run() {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				}
			});
		}
	}

	/**
	 * 下载完成接口
	 * 
	 * @author gongxm
	 *
	 */
	public interface DownloadCompleted {
		public void downloadComleted(String  filepath, boolean issuccess);
	}

}
