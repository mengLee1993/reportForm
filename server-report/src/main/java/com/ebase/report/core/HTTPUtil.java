package com.ebase.report.core;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * <p>
 * 发送http请求
 * </p>
 *
 * @project core
 * @class HTTPUtil
 */
public class HTTPUtil {

	/**
	 * http post
	 * 
	 * @param url
	 * @param requestBody
	 * @return
	 */
	public static String postBody(String url, String requestBody) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httppost = new HttpPost(url);
			StringEntity reqEntity = new StringEntity(requestBody, "UTF-8");
			httppost.setEntity(reqEntity);
			httppost.addHeader("Content-type", "application/json; charset=utf-8");
			httppost.setHeader("Accept", "application/json");
			response = httpclient.execute(httppost);
			return EntityUtils.toString(response.getEntity());
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("http请求出现错误!", ex);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpclient);
		}
	}

	/**
	 * post
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public static String postParams(String url,String token, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			RequestBuilder requestBuilder = RequestBuilder.post().setUri(new URI(url));
			for (Iterator<String> itr = params == null ? null : params.keySet().iterator(); itr != null && itr.hasNext();) {
				String key = itr.next();
				requestBuilder.addParameter(key, params.get(key));
			}
			HttpUriRequest request = requestBuilder.build();
			request.addHeader("Content-type", "application/json; charset=utf-8");
			request.setHeader("Accept", "application/json");
			if(token!=null){
				request.addHeader("Authorization", token);
			}
			response = httpclient.execute(request);
			return EntityUtils.toString(response.getEntity());
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("http请求出现错误!", ex);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpclient);
		}
	}

	/**
	 * get httpjson
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public static String get(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			RequestBuilder requestBuilder = RequestBuilder.post().setUri(new URI(url));
			for (Iterator<String> itr = params == null ? null : params.keySet().iterator(); itr != null && itr.hasNext();) {
				String key = itr.next();
				requestBuilder.addParameter(key, params.get(key));
			}
			HttpUriRequest request = requestBuilder.build();
			request.addHeader("Content-type", "application/json; charset=utf-8");
			request.setHeader("Accept", "application/json");
			response = httpclient.execute(request);
			return EntityUtils.toString(response.getEntity());
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("http请求出现错误!", ex);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpclient);
		}
	}

}
