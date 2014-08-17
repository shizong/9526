package com.example.s500.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.s500.Config;

public class GetCode {
     public GetCode(String  phone,  final SuccessCallback  successCallback ,final FailCallback failCallback) {
    		System.out.println("111111");

     new  NetConection(Config.SERVER_URL, HttpMethod.GET, new  NetConection.SuccessCallback() {
		
	   
		public void onSuccess(String result) {
		try {
			JSONObject  jsonObj=new  JSONObject(result);
			switch (jsonObj.getInt(Config.KEY_STATUS)) {
			case Config.RESULT_STATUS_SUCCESS:
				if (successCallback!=null) {
					successCallback.onSuceess();
				}
				break;

			default:
				if (failCallback!=null) {
					failCallback.onFail();
				}
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (failCallback!=null) {
				failCallback.onFail();
			}
		}
			
		}
	}, new NetConection.FailCallback() {
		
		@Override
		public void onFail() {
			if(failCallback!=null){
				failCallback.onFail();
			}
			
		}
	},Config.KEY_ACTION, Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM,phone);
     }   
     public static interface  SuccessCallback{
    	 void  onSuceess();
     }
     
     public  static  interface  FailCallback{
    	 void  onFail();
     }
     
     
}



