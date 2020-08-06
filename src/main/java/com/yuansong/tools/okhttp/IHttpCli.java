package com.yuansong.tools.okhttp;

import java.io.File;
import java.util.Map;

public interface IHttpCli {
	
	/**
	 * get 请求
	 * @param url 请求地址
	 * @return
	 * @throws Exception 
	 */
	public String doGet(String url) throws Exception;
	
	/**
	 * get 请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 * @throws Exception 
	 */
	public String doGet(String url, Map<String, String> params) throws Exception;
	
	/**
	 * get 请求
	 * @param url 请求地址
	 * @param headers 请求头字段(k1,v1 k2,v2)
	 * @return
	 * @throws Exception 
	 */
	public String doGet(String url, String[] headers) throws Exception;
	
	/**
	 * get 请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param headers 请求头字段(k1,v1 k2,v2)
	 * @return
	 * @throws Exception 
	 */
	public String doGet(String url, Map<String, String> params, String[] headers) throws Exception;
	
	/**
	 * post 请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, Map<String, String> params) throws Exception;
	
//	public String doPost(String url, Map<String, String> params, String[] headers) throws Exception;
	
	/**
	 * post 请求
	 * @param url 请求地址
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public String doPostJson(String url, String json) throws Exception;
	
	/**
	 * post 请求
	 * @param url
	 * @param headers
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public String doPostJson(String url, String[] headers, String json) throws Exception;
	
	/**
	 * post 请求
	 * @param url 请求地址
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public String doPostXml(String url, String xml) throws Exception;
	
	public String doPostXml(String url, String[] headers, String xml) throws Exception;
	
	/**
	 * post 请求
	 * @param url	请求地址
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param data 请求数据
	 * @return
	 * @throws Exception
	 */
	public String doPostMulti(String url, Map<String, String> params, String[] headers, Map<String, File> data) throws Exception;
	
	public String doPostMulti(String url, Map<String, File> data) throws Exception;
	
	public String doPostMulti(String url, String[] headers, Map<String, File> data) throws Exception;
	
	public String doPostMulti(String url, Map<String, String> params, Map<String, File> data) throws Exception;
	

}
