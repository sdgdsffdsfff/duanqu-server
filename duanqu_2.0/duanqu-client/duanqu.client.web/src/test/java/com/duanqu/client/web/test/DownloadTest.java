package com.duanqu.client.web.test;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class DownloadTest {

	public static void main(String... strings) {
		try {

			String host = "http://www.igobuy.com.cn";
			URL url = new URL("http://211.155.224.10:8083/1.mp4");
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();

			httpConnection.setRequestProperty("User-Agent", "NetFox");
			// 设置断点续传的开始位置
			httpConnection.setRequestProperty("RANGE", "bytes=0-1");
			// 获得输入流
			//InputStream input = httpConnection.getInputStream();
			Map map = httpConnection.getHeaderFields();
			Iterator it = map.entrySet().iterator();
			long contentLength = 0;
			for (int i = 0;;i++){
				String key = httpConnection.getHeaderFieldKey(i);
				if (key != null && key.equalsIgnoreCase("Content-Length")){
					contentLength = Long.parseLong(httpConnection.getHeaderField(key)); 
					break;
				}
			}
			
			
			System.out.println(contentLength);
			httpConnection.disconnect();
			httpConnection.setRequestProperty("RANGE", "bytes=0-"+(contentLength-1));
			httpConnection.connect();
			InputStream input = httpConnection.getInputStream();
			
			RandomAccessFile oSavedFile = new RandomAccessFile("/home/wanghaihua/1.mp4", "rw");
			//long nPos = 2000070;
			// 定位文件指针到 nPos 位置
			//oSavedFile.seek(nPos);
			byte[] b = new byte[1024];
			int nRead;
			// 从输入流中读入字节流，然后写到文件中
			while ((nRead = input.read(b, 0, 1024)) > 0) {
				oSavedFile.write(b, 0, nRead);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
