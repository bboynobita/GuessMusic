package com.bysj.guessmusic.ui;

import com.bysj.guessmusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainMenu extends Activity {
	private Button start_game;
	private Button quit_game;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);

		start_game=(Button) findViewById(R.id.start_game);
		quit_game=(Button) findViewById(R.id.quit_game);
		start_game.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent();
				i.setClass(MainMenu.this, MainActivity.class);
				startActivity(i);
			}
		});
	quit_game.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
			
		}
	});
	}
	
}
