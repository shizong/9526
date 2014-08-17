package com.example.s500.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.s500.Config;

public class Publish {
      public Publish(String phone_md5,String token,String msg,final SuccessCallback successCallback,final FailCallback failCallback) {
    	  new NetConection(Config.SERVER_URL, HttpMethod.GET, new NetConection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj=new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback!=null){
							successCallback.onSuccess();
						}
						
						break;
					case Config.RESULT_STATUS_INVALID_TOKEN:
						
							if(failCallback!=null){
								failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
							}
						break;
					default:
						break;
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
					if(failCallback!=null){
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
				
			}
		}, new NetConection.FailCallback() {
			
		
			public void onFail() {
				if(failCallback!=null){
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
				
			}
		}, Config.KEY_ACTION,Config.ACTION_PUBLISH,
		Config.KEY_PHONE_MD5,phone_md5,
		Config.KEY_TOKEN,token,
		Config.KEY_MSG,msg);
    	  
	}
      public interface  SuccessCallback{
    	  void onSuccess();
      }
      public interface  FailCallback{
    	  void onFail(int errorCode);
      }
}
