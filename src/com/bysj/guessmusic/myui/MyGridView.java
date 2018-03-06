package com.bysj.guessmusic.myui;

import java.util.ArrayList;

import com.bysj.guessmusic.R;
import com.bysj.guessmusic.model.IWordButtonClickListener;
import com.bysj.guessmusic.model.WorldButton;
import com.bysj.guessmusic.util.Util;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class MyGridView extends GridView{
	private ArrayList<WorldButton> mArrayList=new ArrayList<WorldButton>();
	private MyGridViewAdapter mAdapter;
	private Context mContext;
	private Animation mScaleAnimation;
	private IWordButtonClickListener mWordButtonListener;
	
	public final static int COUNT_WORDS=24;

	public MyGridView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		mContext=context;
		mAdapter=new MyGridViewAdapter();
		this.setAdapter(mAdapter);
		// TODO Auto-generated constructor stub
	}
	public void updateData(ArrayList<WorldButton> list){
		mArrayList=list;
		setAdapter(mAdapter);
		
	}
	
	class MyGridViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mArrayList.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return mArrayList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public View getView(int pos, View v, ViewGroup p) {
			// TODO Auto-generated method stub
			final WorldButton holder;
			if(v==null){
				v=Util.getView(mContext, R.layout.self_ui_gridview_item);
				
				holder=mArrayList.get(pos);
				//���ض���
				mScaleAnimation=AnimationUtils.loadAnimation(mContext, R.anim.scale);
				//���ö������ӳ�ʱ��
				mScaleAnimation.setStartOffset(pos * 100);
				holder.mIndex=pos;
				holder.mViewButton=(Button) v.findViewById(R.id.item_btn);
				//���ö����ĵ���¼�
				holder.mViewButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mWordButtonListener.onWordButtonClick(holder);
						
					}
				});
				v.setTag(holder);
			}
			else{
				holder=(WorldButton)v.getTag();
			}
			holder.mViewButton.setText(holder.mWroldString);
			//���Ŷ���
			v.startAnimation(mScaleAnimation);
			return v;
		}
		
	}
	//ע������ӿ�
	public void registOnWordButtonClick(IWordButtonClickListener listener){
		mWordButtonListener=listener;
		
	}

}
