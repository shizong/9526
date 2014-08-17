package com.example.s500.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.s500.Config;

public class GetComment {
    public GetComment(String phone_md5,String token,String msgId,int page ,int perpage,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConection(Config.SERVER_URL, HttpMethod.GET, new NetConection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj= new JSONObject(result);
					System.out.println("??????????");
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback!=null){
					 List<Comment> comments=new ArrayList<Comment>();
						   JSONArray commentsJsonArray=obj.getJSONArray(Config.KEY_COMMENTS);
						  
						for (int i = 0; i < commentsJsonArray.length(); i++) {
							JSONObject commentObj=commentsJsonArray.getJSONObject(i);
							 System.out.println("!!!!!!!!!!!!!!!!!!");
							comments.add(new Comment(commentObj.getString(Config.KEY_CONTENT),commentObj.getString(Config.KEY_PHONE_MD5)));
							System.out.println(commentObj.getString(Config.KEY_CONTENT));
							
						}
						
			
//						successCallback.onSuccess(obj.getString(Config.KEY_MSG_ID),obj.getInt(Config.KEY_PAGE),obj.getInt(Config.KEY_PERPAGE), comments);
						successCallback.onSuccess("123", 1, 20, comments);
						
						}
					 
						break;
						
					case Config.RESULT_STATUS_INVALID_TOKEN:
						if(failCallback!=null){
							failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
						}
						
						break;
					default:
						if(failCallback!=null){
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
						
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
			
			@Override
			public void onFail() {
				if(failCallback!=null){
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
				
			}
		},Config.KEY_ACTION,Config.ACTION_GET_COMMENT,
		Config.KEY_TOKEN,token,
		Config.KEY_MSG_ID,msgId,
		Config.KEY_PHONE_MD5,phone_md5,
		Config.KEY_PAGE,page+"",
		Config.KEY_PERPAGE,perpage+"");
	}
    
    public interface SuccessCallback{
    	void onSuccess(String msgId,int page ,int perpage ,List<Comment> comments);
    	
    }
    public interface FailCallback{
    	void onFail(int errorCode);
    }
}
