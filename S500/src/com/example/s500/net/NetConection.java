package com.example.s500.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.example.s500.Config;

public class NetConection {
     public NetConection(final String url , final HttpMethod  method,final SuccessCallback successCallBack ,final FailCallback failCallback,final String ...kvs) {
	
    	 
    	 
    	 System.out.println("2222222");
    	 new AsyncTask<Void, Void, String>(){
    	protected String doInBackground(Void... arg0) {
    		StringBuffer  paramsStr= new StringBuffer();
    		System.out.println("3333333");
    		for (int i = 0; i < kvs.length; i+=2) {
				paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
				
			}
    		
    		
    		try {
    			System.out.println("555555555555");
				URLConnection uc = null ;
				System.out.println("66666666666");
				switch (method) {
				case POST:
					uc=new URL(url).openConnection();
					uc.setDoInput(true);
					BufferedWriter  bw=new  BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),Config.CHARSET));
					bw.write(paramsStr.toString());
					bw.flush();
					break;

				 default:
					uc=new URL(url+"?"+paramsStr.toString()).openConnection();
			
					
					break;
				}
				
				System.out.println("Request url:"+uc.getURL());
				System.out.println("Request  data "+paramsStr);
//				HttpURLConnection ur=(HttpURLConnection) uc;
//				ur.setAllowUserInteraction(false);
//				ur.setInstanceFollowRedirects(true);
//				ur.setRequestMethod("GET");
//				uc.connect();
				BufferedReader   br= new   BufferedReader(new  InputStreamReader(uc.getInputStream(), Config.CHARSET));
				System.out.println(br.toString());
				String line=null;
				StringBuffer  result=new  StringBuffer();
				while((line=br.readLine())!=null){
					result.append(line);
				}
				System.out.println("Result:"+result);
				return result.toString();
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return null;
    	}
    	  
    		protected void onPostExecute(String result) {
    			if (result!=null) {
					if (successCallBack!=null) {
						successCallBack.onSuccess(result);
					}
				}else {if (failCallback!=null) {
					failCallback.onFail();
				}
					
				}
    			super.onPostExecute(result);
    		}	 
        	 
     }.execute(); 
     }
   
	public  static interface  SuccessCallback{
    	 void onSuccess(String  result);
     }
     public static  interface FailCallback{
    	 void onFail();
     }
     
}



