package com.cmcc.application.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.*;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.common.utils.Tools;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {
	private static Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);
	/** 
	 * HttpClient 模拟POST请求 * 方法说明 
	 * @Discription:扩展说明
	 * @param url 
	 * @param params
	 * @return String 
	 * @Author: baiyanmin 
	 */ 
	public static String postRequest(String url, Map<String,String> params) { 
		//构造HttpClient的实例 
		HttpClient httpClient = new HttpClient(); 
		//创建POST方法的实例 
		PostMethod postMethod = new PostMethod(url); 
		//设置请求头信息 
		postMethod.setRequestHeader("Connection", "close"); 
		postMethod.setRequestHeader("token", "123123");
		//添加参数 
		for (Map.Entry<String,String> entry : params.entrySet()) { 
			postMethod.addParameter(entry.getKey(), entry.getValue()); 
		} 
		//使用系统提供的默认的恢复策略,设置请求重试处理，用的是默认的重试处理：请求三次 
		httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false); 
		httpClient.getParams().setContentCharset("UTF-8");
		//接收处理结果 
		String result = null; 
		try { 
			//执行Http Post请求 
			httpClient.executeMethod(postMethod); 
			//返回处理结果 
			result = postMethod.getResponseBodyAsString(); 
		} catch (HttpException e) { 
			// 发生致命的异常，可能是协议不对或者返回的内容有问题 
			log.info("请检查输入的URL!"); 
			e.printStackTrace(); 
		} catch (IOException e) { 
			// 发生网络异常 
			log.info("发生网络异常!"); 
			e.printStackTrace(); 
		} finally { 
			//释放链接 postMethod.releaseConnection(); 
			//关闭HttpClient实例 
			if (httpClient != null) { 
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown(); 
				httpClient = null; 
			} 
		} 
		return result; 
	}

	public static JSONObject httpGet(String url,String token,Map<String,Object> params){
		JSONObject jsonObject = null;
		String address = "";
		//拼接参数
		String paramStr = "";
		if(params!=null){
			for (String key : params.keySet()) {
				paramStr = paramStr + "&" + key + "=" +params.get(key);
			}
			paramStr = paramStr.substring(1);
			address = url + "?" + paramStr;
		}else{
			address = url;
		}
		HttpGet httpGet = new HttpGet(address);
		/**公共参数添加至httpGet*/
		/**header中通用属性*/
		if(Tools.notEmpty(token)){
			httpGet.setHeader("X-Token", token);
			httpGet.setHeader("X-ACCESS-TOKEN", token);
		}
		httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");

		try {
			// 获取连接客户端工具
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			// 使用Apache提供的工具类进行转换成字符串
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.parseObject(entityStr);
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/** 
	 * HttpClient 模拟GET请求 
	 * 方法说明 
	 * @Discription:扩展说明
	 * @param url 
	 * @param params 
	 * @return String 
	 * @Author: baiyanmin
	 * */ 
	public static String getRequest(String url, Map<String,String> params) { 
		//构造HttpClient实例 
		HttpClient client = new HttpClient(); 
		//拼接参数 
		String paramStr = ""; 
		for (String key : params.keySet()) { 
			paramStr = paramStr + "&" + key + "=" +params.get(key); 
		} 
		paramStr = paramStr.substring(1); 
		//创建GET方法的实例 
		GetMethod method = new GetMethod(url + "?" + paramStr); 
		//接收返回结果 
		String result = null; 
		try { 
			//执行HTTP GET方法请求 
			client.executeMethod(method); 
			//返回处理结果 
			result = method.getResponseBodyAsString(); 
		} catch (HttpException e) { 
			// 发生致命的异常，可能是协议不对或者返回的内容有问题 
			log.debug("请检查输入的URL!");
			e.printStackTrace(); 
		} catch (IOException e) { 
			// 发生网络异常 
			log.debug("发生网络异常!");
			e.printStackTrace(); } finally { 
			//释放链接
			method.releaseConnection(); 
			//关闭HttpClient实例 
			if (client != null) { 
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				client = null; 
			} 				
		} 
		log.info(result);
		return result; 
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[]{};
		}
	}

	/**
	 * 发起https请求并获取结果
	 * @author baiyanmin
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param bodyParameters 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, Map<String, Object> bodyParameters,String token) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestProperty("Content-Type","application/json; charset=utf-8");
			httpUrlConn.setRequestProperty("X-ACCESS-TOKEN",token);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

//			if ("GET".equalsIgnoreCase(requestMethod))
//				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != bodyParameters) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				String outputStr = com.alibaba.fastjson.JSONObject.toJSONString(bodyParameters);
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
	public static void main(String[] arg) {
		Map<String, Object> bodyParameters = new HashMap<>();
		bodyParameters.put("meeting_operate_type", "0");
		bodyParameters.put("meeting_theme", "开发自测会议");
		bodyParameters.put("meeting_ifmute", false);
		bodyParameters.put("meeting_length", 21600);
		bodyParameters.put("meeting_mode", "HD");
		bodyParameters.put("meeting_starttime", "2020-09-27 09:00:00");
		bodyParameters.put("meeting_type", "0");
		bodyParameters.put("meeting_attendee", new ArrayList<>());
		System.out.println(httpRequest("https://meeting.125339.com.cn/mixapi/createMeeting", "POST",bodyParameters,"804c9dc7b91a73c2aa281d7128aade4a"));
	}
}
