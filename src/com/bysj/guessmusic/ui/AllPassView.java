package com.bysj.guessmusic.ui;

import com.bysj.guessmusic.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class AllPassView extends Activity{
	private Button BtnBack;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.all_pass_view);
	BtnBack=(Button) findViewById(R.id.btn_bar_back);
	BtnBack.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
			
		}
	});
	
	//隐藏右上角的金币按钮
	FrameLayout view=(FrameLayout) findViewById(R.id.layout_bar_coin);
	view.setVisibility(View.INVISIBLE);
	
}
}
