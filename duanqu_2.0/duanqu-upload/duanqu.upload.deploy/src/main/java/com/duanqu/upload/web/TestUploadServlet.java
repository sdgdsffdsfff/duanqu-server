package com.duanqu.upload.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class TestUploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5860897876277381220L;
	private final static String DEFAULT_ENCODING = "ISO8859_1";
	private final static String BOUNDARY = "boundary";

/*	public void service(HttpServletRequest req, HttpServletResponse res) {
		int length = req.getContentLength();
		System.out.println("==========="+length);
		String type = req.getContentType();
		System.out.println("==========="+type);
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(
					"/home/tiger/request2.txt")));
			ServletInputStream in = req.getInputStream();
			int count = 0;
			int i = in.read();
			while (i != -1) {
				pw.print((char) i);
				count ++;
				i = in.read();
				if (count % 1000000 == 0){
					System.out.println("+++count="+count);
				}
			}
			System.out.println("===count="+count);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/
	
	
	public void service(HttpServletRequest req, HttpServletResponse res) {
		try {
			getInputData(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getInputByte(InputStream is){
		List byteList = new ArrayList();
		byte[] filebyte = null;
		int readbyte = 0;
		try{
			while ((readbyte = is.read()) != -1) {
				 byteList.add(new Byte((byte) readbyte));
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		filebyte = new byte[byteList.size()];
		for (int i = 0; i < byteList.size(); i++) {
			filebyte[i] = ((Byte) byteList.get(i)).byteValue();
		}
		return filebyte;
		
	}
	
	private String getInputData(HttpServletRequest req){
		byte[] sizes;
		try {
			sizes = this.getInputByte(req.getInputStream());
			String reals = new String(sizes, DEFAULT_ENCODING);
//			System.out.println(reals);
			String[] arrs = reals.split(this.getBoundary(req));
			for(String str : arrs){
				System.out.println("------------------------");
				System.out.println(str);
			}
			return reals;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getBoundary(HttpServletRequest req){
		String contentType = req.getContentType();
		if (contentType != null){
			String[] params = contentType.split(";");
			for (String param : params){
				if (StringUtils.hasText(param)){
					if (param.indexOf("boundary")>=0){
						String[] fields = param.split("=");
						if (fields.length == 2){
							return "--"+fields[1];
						}
					}
				}
			}
		}
		return null;
	}
}
