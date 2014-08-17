package com.example.s500;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.s500.atys.AtyLogin;
import com.example.s500.atys.AtyTimeline;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     
        
        String token = Config.getCachedToken(this);
        String phone_number=Config.getCachedPhoneNum(this);
        
	
		
		if (token!=null) {
			Intent i =new Intent(this, AtyTimeline.class);
			i.putExtra(Config.KEY_TOKEN, token);
			i.putExtra(Config.KEY_PHONE_NUM,phone_number);
			startActivity(i);
		}else{
			startActivity(new Intent(this, AtyLogin.class));
		}
		System.out.println("。。。。。。。。");
	
		finish();
	}
    
    }
      
  


  