package com.example.s500.atys;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.s500.Config;
import com.example.s500.R;
import com.example.s500.ld.MyContacts;
import com.example.s500.net.Message;
import com.example.s500.net.TimeLine;
import com.example.s500.net.UploadContacts;
import com.example.s500.tools.MD5Tool;

public class AtyTimeline extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.aty_timeline);
    	adapter=new  AtyTimelineMessageListAdapter(this);
    	setListAdapter(adapter);
    	token=getIntent().getStringExtra(Config.KEY_TOKEN);
    	phone_number=getIntent().getStringExtra(Config.KEY_PHONE_NUM);
    	phone_md5=MD5Tool.md5(phone_number);
    	final ProgressDialog pd=ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
    	
    	new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallback() {
			
			
			public void onSuccess() {
				pd.dismiss();
				loadMessage();
			}
		}, new UploadContacts.FailCallback() {
			
			
			public void onFail(int errorCode) {
				pd.dismiss();
				if(errorCode==Config.RESULT_STATUS_INVALID_TOKEN){
					startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
					finish();
				}else{
					loadMessage();
				}
		
				
			}
		}
		);
    }
    private void  loadMessage(){
    	final ProgressDialog pd=ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
    
    	 new TimeLine(phone_md5, token, 1, 20, new TimeLine.SuccessCallback() {
			
			@Override
			public void onSuccess(int page, int perpage, List<Message> timeline) {
				pd.dismiss();
				System.out.println("wu shuai shuai ");
				adapter.addAll(timeline);
				
			}
		}, new TimeLine.FailCallback() {
			
			
			public void onFail(int errorCode) {
			pd.dismiss();
			if(errorCode==Config.RESULT_STATUS_INVALID_TOKEN){
				startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
				finish();
			}else{
			
			Toast.makeText(AtyTimeline.this, R.string.fail_to_load_timeline, Toast.LENGTH_LONG).show();
			}
			}
		});
    	System.out.println(">>>>>>>>>>>>>>>loadMessage");
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	Message msg=adapter.getItem(position);
    	Intent i= new Intent(this,AtyMessage.class);
    	i.putExtra(Config.KEY_MSG, msg.getMsg());
    	i.putExtra(Config.KEY_MSG_ID, msg.getMsgId());
    	i.putExtra(Config.KEY_PHONE_MD5,msg.getPhone_md5());
    	i.putExtra(Config.KEY_TOKEN,token);
    	startActivity(i);
    	
    }
    private  String token,phone_number,phone_md5;
    private  AtyTimelineMessageListAdapter adapter;
    
}
