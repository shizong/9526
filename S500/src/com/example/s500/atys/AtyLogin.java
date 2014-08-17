package com.example.s500.atys;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s500.Config;
import com.example.s500.R;
import com.example.s500.net.GetCode;
import com.example.s500.net.Login;
import com.example.s500.tools.MD5Tool;


public class AtyLogin extends Activity {
  
    protected void onCreate(Bundle savedInstanceState) {
    
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.aty_login);  
    	final EditText etPhone = (EditText)findViewById(R.id.etPhoneNum);
        final       EditText   etCode=(EditText)findViewById(R.id.etCode);
    	findViewById(R.id.btnGetCode).setOnClickListener(new  View.OnClickListener() {
			
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(etPhone.getText())){
					Toast.makeText(AtyLogin.this,R.string.phone_num_can_not_be_null, Toast.LENGTH_LONG).show();
					return;
				}
				final  ProgressDialog  pd=ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting),  getResources().getString(R.string.connecting_to_server));
				new  GetCode(etPhone.getText().toString(), new  GetCode.SuccessCallback() {
					
				    
					public void onSuceess() {
						pd.dismiss();
						Toast.makeText(AtyLogin.this,R.string.suc_to_get_code, Toast.LENGTH_LONG).show();
						
					}
				}, new GetCode.FailCallback() {
					
					@Override
					public void onFail() {
						pd.dismiss();
						Toast.makeText(AtyLogin.this,R.string.fail_to_get_code, Toast.LENGTH_LONG).show();
						
					}
				});
			}
		});
    findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(TextUtils.isEmpty(etPhone.getText())){
				Toast.makeText(AtyLogin.this,R.string.phone_num_can_not_be_null, Toast.LENGTH_LONG).show();
				return;
			}
				
			if(TextUtils.isEmpty(etCode.getText())){
				Toast.makeText(AtyLogin.this, R.string.code_can_not_be_null,Toast.LENGTH_LONG).show();
			}
			final  ProgressDialog  pd=ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting),  getResources().getString(R.string.connecting_to_server));
			new  Login(MD5Tool.md5(etPhone.getText().toString()),etCode.getText().toString(),new Login.SuccessCallback() {
				
				@Override
				public void onSuccess(String token) {
					pd.dismiss();
				Config.cacheToken(AtyLogin.this, token);
				Config.cachePhoneNum(AtyLogin.this, etPhone.getText().toString());
				Intent i= new Intent(AtyLogin.this,AtyTimeline.class);
				i.putExtra(Config.KEY_TOKEN,token);
				i.putExtra(Config.KEY_PHONE_NUM,etPhone.getText().toString());
				startActivity(i);
				finish();
				
					
				}
			},new Login.FailCallback() {
				
				
				public void onFail() {
					pd.dismiss();
					Toast.makeText(AtyLogin.this, R.string.fail_to_login_in,Toast.LENGTH_LONG).show();
				}
			});
		}
	});
}
}