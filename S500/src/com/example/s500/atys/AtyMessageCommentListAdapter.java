package com.example.s500.atys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.s500.R;

import com.example.s500.net.Comment;


public class AtyMessageCommentListAdapter extends BaseAdapter {

	            
				public AtyMessageCommentListAdapter(Context context) {
					 this.context=context;
				}
	public int getCount() {
		
		return comments.size();
	}

	
	public Comment getItem(int arg0) {
		
		return comments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}
    public  void  addAll(List<Comment> data){
    	
    	comments.addAll(data);
    	notifyDataSetChanged();
    }
	public void clear(){
		comments.clear();
		notifyDataSetChanged();
	}
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		System.out.println("kokokokok");
		if(convertView==null){
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell,null);
			convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.tvCellLabel)));
		}
		
	
		ListCell lc= (ListCell) convertView.getTag();
		Comment comment=getItem(arg0);
		
		lc.getTvCellLabel().setText(comment.getContent());
		
		
		
		
		
		return convertView;
	}
	private Context getContext() {
		
		return context;
	}
	private  Context context;
	private  List<Comment> comments= new ArrayList<Comment>();
	private static class ListCell{
		public ListCell(TextView tvCellLabel) {
			this.tvCellLabel=tvCellLabel;
		}
		private TextView tvCellLabel;
		public TextView getTvCellLabel(){
			return tvCellLabel;
		}
		
	}

}
