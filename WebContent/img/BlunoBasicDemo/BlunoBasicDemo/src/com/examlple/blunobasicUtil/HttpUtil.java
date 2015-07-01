package com.examlple.blunobasicUtil;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil{
	//public static final String BASE_URL="http://10.0.2.2:8080/ExpressSensorWeb/";
	public static final String BASE_URL="http://120.26.81.33:8080/GCWeb";
	public static HttpGet getHttpGet(String url){
		HttpGet request = new HttpGet(url);
		return request;
	}
	public static HttpPost getHttpPost(String url){
		HttpPost request= new HttpPost(url);
		return request;
	}
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	public static String queryStringForPost(String url){
		HttpPost request = HttpUtil.getHttpPost(url);
		String result=null;		
		try{
			HttpResponse response =HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity());
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}
		return result;
	}
	public static String queryStringForGet(String url){
		HttpGet request =HttpUtil.getHttpGet(url);
		String result = null;		
		try{
			HttpResponse response=HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity());
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}
		return result;
	}
	public static String queryStringForPost(HttpPost request){
		String result = null;
		
		try{
			HttpResponse response=HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity());
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result="Õ¯¬Á“Ï≥£";
			return result;
		}
		return result;
	}
	public static String InsertData(String temperater,String pulse,String location,String error){
		location = location.trim();
//		location = location.replace(""+"", ",");
		String queryStr = "t="+temperater.trim()+"&p="+pulse.trim()+"&l="+location.trim()+"&e="+error;
		String url = HttpUtil.BASE_URL+"/InsertData?"+queryStr;
		return HttpUtil.queryStringForPost(url);
		
	}
	public static String UpdateTradeProcessed(String id){
		String queryStr="id="+id;
		String url=HttpUtil.BASE_URL+"servlet/UpdateTradeProcessed?"+queryStr;
		return HttpUtil.queryStringForPost(url);
	}
}