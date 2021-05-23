package com.yuansong.tools.okhttp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
class HttpUtilImpl implements IHttpUtil {
	
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
	private static final MediaType FORM_DATA = MediaType.parse("multipart/form-data");
	private static final MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");
	
	@Autowired
	private OkHttpClient okHttpClient;

	@Override
	public String doGet(String url, String[] headers, Map<String, String> params) throws Exception {
		url = url + this.getParamsStr(params);
		Builder builder = new Request.Builder();
		builder.addHeader("Connection", "close");
		builder = this.addHeaders(builder, headers);
		
		Request request = builder.url(url).build();
		return this.execute(request);
	}

	@Override
	public String doPostForm(String url, String[] headers, Map<String, String> params, Map<String, String> form)
			throws Exception {
		url = url + this.getParamsStr(params);
		FormBody.Builder bodyBuilder = new FormBody.Builder();
		if(params != null && params.keySet().size() > 0) {
			for(String key : params.keySet()) {
				bodyBuilder.add(key, params.get(key));
			}
		}
		Request.Builder builder = new Request.Builder();
		builder.addHeader("Connection", "close");
		builder = this.addHeaders(builder, headers);
		Request request = builder.url(url).post(bodyBuilder.build()).build();
		return this.execute(request);
	}

	@Override
	public String doPostJson(String url, String[] headers, Map<String, String> params, String json) throws Exception {
		url = url + this.getParamsStr(params);
		return this.exectPost(url, headers, json, JSON);
	}

	@Override
	public String doPostXml(String url, String[] headers, Map<String, String> params, String xml) throws Exception {
		url = url + this.getParamsStr(params);
		return this.exectPost(url, headers, xml, XML);
	}

	@Override
	public String doPostMulti(String url, String[] headers, Map<String, String> params, Map<String, File> data) throws Exception {
		url = url + this.getParamsStr(params);
		MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
		bodyBuilder.setType(FORM_DATA);
		if(data != null &&data.size() > 0) {
			for(String key : data.keySet()) {
				bodyBuilder.addFormDataPart(key, data.get(key).getName(), RequestBody.create(OCTET_STREAM, data.get(key)));
			}
		}
		Request.Builder builder = new Request.Builder();
		builder.addHeader("Connection", "close");
		builder = this.addHeaders(builder, headers);
		Request request = builder.url(url).post(bodyBuilder.build()).build();
		return this.execute(request);
	}
	
	private String exectPost(String url, String[] headers,String data, MediaType contentType) throws Exception {
		RequestBody requestBody = RequestBody.create(contentType, data);
		Builder builder = new Request.Builder();
		builder.addHeader("Connection", "close");
		builder = this.addHeaders(builder, headers);
		
		Request request = builder.url(url).post(requestBody).build();
		return this.execute(request);
	}
	
	private Builder addHeaders(Builder builder, String[] headers) {
		if(builder != null && headers != null && headers.length > 1) {
			for(int i = 0; i < headers.length - 1; i = i + 2) {
				builder.addHeader(headers[i], headers[i + 1]);
			}
		}
		return builder;
	}
	
	private String execute(Request request) throws Exception {
		Response response = null;
		try {
			response = this.okHttpClient.newCall(request).execute();
			return response.body().string();
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}

	private String getParamsStr(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if(params != null && params.keySet().size() > 0) {
			boolean firstFlag = true;
			for(String key : params.keySet()) {
				sb.append(firstFlag?"?":"&").append(URLEncoder.encode(key, "utf-8")).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
				firstFlag = false;				
			}
		}
		return sb.toString();
	}
}
