package com.example.s500.ld;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.s500.Config;
import com.example.s500.tools.MD5Tool;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class MyContacts {
       


	public static  String  getContactsJSONString(Context  context){
    	   Cursor c=context.getContentResolver().query(Phone.CONTENT_URI, null, null,null, null);
    	   String phoneNum;
    	   JSONArray  jsonArr=new  JSONArray();
    	   JSONObject  jsonObj= new  JSONObject();
    	   
    	   while(c.moveToNext()){
    		   phoneNum=c.getString(c.getColumnIndex(Phone.NUMBER));
    		  
    		   if(phoneNum.charAt(0)=='+'&&phoneNum.charAt(1)=='8'&&phoneNum.charAt(2)=='6'){
    			   phoneNum=phoneNum.substring(3);
    		   }
    		   
    		  
    		
    		   try {
				jsonObj.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNum));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		   
    		  jsonArr.put(jsonObj); 
    		  
    		
    	   }
    	   return  jsonArr.toString();
       }
}
