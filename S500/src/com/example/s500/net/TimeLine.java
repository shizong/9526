package com.example.s500.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.s500.Config;

public class TimeLine {
    public TimeLine(String phone_md5,String token ,int page,int perpage ,final SuccessCallback successCallback,final FailCallback failCallback) {
		 new NetConection(Config.SERVER_URL, HttpMethod.GET, new NetConection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
			   try {
				JSONObject obj=new JSONObject(result);
				switch(obj.getInt(Config.KEY_STATUS)){
				case  Config.RESULT_STATUS_SUCCESS:
					if(successCallback!=null){
						List<Message> timeline=new ArrayList<Message>();
						
						JSONArray msgJSONArray=obj.getJSONArray(Config.KEY_TIMELINE);
						JSONObject  msgObj;
						for(int i=0;i<msgJSONArray.length();i++){
							msgObj=msgJSONArray.getJSONObject(i);
							timeline.add(new Message(msgObj.getString(Config.KEY_MSG_ID),msgObj.getString(Config.KEY_MSG),msgObj.getString(Config.KEY_PHONE_MD5)));
						}
						
						successCallback.onSuccess(obj.getInt(Config.KEY_PAGE), obj.getInt(Config.KEY_PERPAGE),timeline);
						
					}
					
					break;
				case Config.RESULT_STATUS_INVALID_TOKEN:
					if(failCallback!=null){
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
					
				default:
					if(failCallback!=null){
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
			} catch (JSONException e) {
				if(failCallback!=null){
					failCallback.onFail(Config.RESULT_STATUS_FAIL);}
				e.printStackTrace();
			}
				
			}
		}, new NetConection.FailCallback() {
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				
			}
		}, Config.KEY_ACTION,Config.ACTION_TIMELINE,
		Config.KEY_PHONE_MD5,phone_md5,
		Config.KEY_TOKEN,token,
		Config.KEY_PAGE,page+"",
		Config.KEY_PERPAGE,perpage+"");
	}
    public  static interface SuccessCallback{
    	void onSuccess(int page, int perpage, List<Message> timeline);
    }
    public  static  interface FailCallback{
    	void onFail(int errorCode);
    }
}

