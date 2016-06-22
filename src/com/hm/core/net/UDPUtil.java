package com.hm.core.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPUtil {

	/**
	 * 网络连接超时时间
	 */
	public static final int CONNECTION_TIME_OUT_TIME = 3 * 1000;
	public static final String CONNECTION_TIME_OUT = "connection timed out";// 网络连接超时

	public static String getDate(String url, int port, String UDP_Cat) {
		String data = "";
		DatagramSocket ds = null; // 建立套间字udpsocket服务
		try {
			ds = new DatagramSocket(8999); // 实例化套间字，指定自己的port
			ds.setSoTimeout(CONNECTION_TIME_OUT_TIME);
		} catch (SocketException e) {
			System.out.println(" 错误 Cannot open port!");
			System.exit(1);
		}
		byte[] buf = UDP_Cat.getBytes(); // 数据
		InetAddress destination = null;
		try {
			destination = InetAddress.getByName(url); // 需要发送的地址
		} catch (UnknownHostException e) {
			System.out.println(" 错误 !!!  Cannot open findhost!");
			System.exit(1);
		}
		DatagramPacket dp = new DatagramPacket(buf, buf.length, destination, port);
		// 打包到DatagramPacket类型中（DatagramSocket的send()方法接受此类，注意10000是接受地址的端口，不同于自己的端口！）
		try {
			ds.send(dp); // 发送数据
			// 接收部分
			byte[] buf2 = new byte[1024];// 接受内容的大小，注意不要溢出
			DatagramPacket dp2 = new DatagramPacket(buf2, 0, buf2.length);// 定义一个接收的包
			ds.receive(dp2);// 将接受内容封装到包中
			data = new String(dp2.getData(), 0, dp2.getLength());// 利用getData()方法取出内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		return data;
	}

}
