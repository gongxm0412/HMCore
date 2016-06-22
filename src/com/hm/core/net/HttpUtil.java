package com.hm.core.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

/**
 * 
 * @author gongxm
 *
 */
public class HttpUtil {

	/**
	 * 网络连接超时时间
	 */
	public static final int CONNECTION_TIME_OUT_TIME = 20 * 1000;
	public static final String CONNECTION_TIME_OUT = "connection timed out";// 网络连接超时

	/**
	 * http get请求方式，请求内容为经过gzip压缩内容，返回字符串
	 */
	public static String getHttpBack(String url) {
		String seamStr = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				GZIPInputStream gunzip = new GZIPInputStream(in);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n;
				while ((n = gunzip.read(buffer)) >= 0) {
					out.write(buffer, 0, n);
				}
				byte[] b = out.toByteArray();
				out.close();
				gunzip.close();
				in.close();
				seamStr = new String(b);
				return seamStr;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * http get请求方式
	 */
	public static byte[] getHttpData(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n;
				while ((n = in.read(buffer)) >= 0) {
					out.write(buffer, 0, n);
				}
				byte[] b = out.toByteArray();
				out.close();
				in.close();
				return b;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * http get请求方式，请求内容为字符串
	 * 
	 * @param url
	 * @return
	 */
	public static String getHttpBackOrigion(String url) {
		String seamStr = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIME_OUT_TIME);
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n;
				while ((n = in.read(buffer)) >= 0) {
					out.write(buffer, 0, n);
				}
				byte[] b = out.toByteArray();
				out.close();
				in.close();
				seamStr = new String(b);
				return seamStr;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String get(String urlStr, boolean isGzip) {
		HttpClient httpClient = new DefaultHttpClient();

		// 连接超时时间
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
		// 等待数据超时时间
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);

		HttpGet httpGet = new HttpGet(urlStr);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getStrByHttpResponse(httpResponse, isGzip);
	}

	public static String postForm(String urlStr, boolean isGzip, Map<String, String> form) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
		HttpPost httpPost = new HttpPost(urlStr);
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded" + HTTP.CHARSET_PARAM + HTTP.UTF_8);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> key = form.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = it.next();
			params.add(new BasicNameValuePair(s, form.get(s)));
		}

		HttpResponse httpResponse = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getStrByHttpResponse(httpResponse, isGzip);
	}

	/***
	 * 
	 * @param srcPath
	 * @param urlstr
	 *            ="http://sssss.aspx?a=1&b=1"
	 * 
	 * @return
	 */
	public static String PostFileToService(String srcPath, String urlstr) {
		String twoHyphens = "--";
		String boundary = "******";
		String end = "\r\n";
		String seamStr = null;
		try {
			System.out.println(srcPath);
			URL url = new URL(urlstr);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			httpURLConnection.setRequestProperty("enctype", "multipart/form-data");
			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition:form-data;name=\"file\"; filename=\""
					+ srcPath.substring(srcPath.lastIndexOf("/") + 1) + "\"" + end);
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(srcPath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);

			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();
			dos.close();

			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = httpURLConnection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer1 = new byte[256];
				int n;
				while ((n = in.read(buffer1)) >= 0) {
					out.write(buffer1, 0, n);
				}
				byte[] b = out.toByteArray();
				in.close();
				seamStr = new String(b);
				System.out.println(seamStr);
			} else {
				seamStr = CONNECTION_TIME_OUT;
			}
		} catch (MalformedURLException e) {
			seamStr = CONNECTION_TIME_OUT;
		} catch (IOException e) {
			seamStr = CONNECTION_TIME_OUT;
		}
		return seamStr;

	}

	public static String httpGetValue(String urlStr) {
		String httpres = "";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
		HttpPost httpPost = new HttpPost(urlStr);
		HttpResponse httpResponse = null;
		try {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "text/xml");
			httpPost.setHeader("Content-Type", "application/xml");
			httpPost.setHeader("SOAPAction", urlStr);
			// httpPost.setEntity(new StringEntity(form, "UTF-8"));
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse == null) {
				return null;
			}
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					InputStream in = entity.getContent();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int n;
					while ((n = in.read(buffer)) >= 0) {
						out.write(buffer, 0, n);
					}
					byte[] b = out.toByteArray();
					in.close();
					out.close();
					httpres = new String(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return httpres;
	}

	/*
	 * post 请求 wcf接口
	 */
	public static String postWCF(String urlStr, boolean isGzip, String form) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
		HttpPost httpPost = new HttpPost(urlStr);
		HttpResponse httpResponse = null;
		try {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "text/xml");
			httpPost.setHeader("Content-Type", "application/xml");
			httpPost.setHeader("SOAPAction", urlStr);
			httpPost.setEntity(new StringEntity(form, "UTF-8"));
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getStrByHttpResponse(httpResponse, isGzip);
	}

	public static String getStrByHttpResponse(HttpResponse httpResponse, boolean isGzip) {
		if (httpResponse == null) {
			return null;
		}
		try {
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				InputStream in = entity.getContent();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n;
				if (isGzip) {
					GZIPInputStream gunzip = new GZIPInputStream(in);
					while ((n = gunzip.read(buffer)) >= 0) {
						out.write(buffer, 0, n);
					}
					gunzip.close();
				} else {
					while ((n = in.read(buffer)) >= 0) {
						out.write(buffer, 0, n);
					}
				}
				byte[] b = out.toByteArray();
				in.close();
				out.close();
				return new String(b);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
