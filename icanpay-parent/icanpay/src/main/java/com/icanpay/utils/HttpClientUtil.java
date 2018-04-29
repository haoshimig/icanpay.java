package com.icanpay.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

	static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * Post请求
	 * 
	 * @param url
	 * @param data
	 * @param contentType
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, String data, String contentType) {
		BasicHttpClientConnectionManager connManager;
		connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null,
				null);

		HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(ICanPayConfig.getHttpReadTimeoutMs())
				.setConnectTimeout(ICanPayConfig.getHttpConnectTimeoutMs()).build();
		httpPost.setConfig(requestConfig);

		StringEntity postEntity = new StringEntity(data, "UTF-8");
		httpPost.addHeader("Content-Type", contentType);

		httpPost.setEntity(postEntity);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			return EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Get请求
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doGet(String url) {
		BasicHttpClientConnectionManager connManager;
		connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null,
				null);

		HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

		HttpGet httpget = new HttpGet(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(ICanPayConfig.getHttpReadTimeoutMs())
				.setConnectTimeout(ICanPayConfig.getHttpConnectTimeoutMs()).build();
		httpget.setConfig(requestConfig);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpget);
			HttpEntity httpEntity = httpResponse.getEntity();
			return EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}