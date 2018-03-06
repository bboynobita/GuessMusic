package com.bysj.guessmusic.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.bysj.guessmusic.R;
import com.bysj.guessmusic.data.Const;
import com.bysj.guessmusic.model.IAlertDialogButtonListener;
import com.bysj.guessmusic.model.IWordButtonClickListener;
import com.bysj.guessmusic.model.Song;
import com.bysj.guessmusic.model.WorldButton;
import com.bysj.guessmusic.myui.MyGridView;
import com.bysj.guessmusic.util.MyLog;
import com.bysj.guessmusic.util.MyPlayer;
import com.bysj.guessmusic.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements IWordButtonClickListener{
	public final static String TAG="MainActivity";
	//检测答案正确与否
	//正确
	public final static int STATUS_ANSWER_RIGHT=1;
	//错误
	public final static int STATUS_ANSWER_WRONG=2;
	//不完整
	public final static int STATUS_ANSWER_LACK=3;
	//闪烁次数
	public final static int SPASH_TIMES=6;
	
	public final static int ID_DIALOG_DELETE_WORD=1;
	public final static int ID_DIALOG_TIP_ANSWER=2;
	public final static int ID_DIALOG_LACK_COINS=3;
	
	//过关界面
	private View mPassView;
	
	public final static int COUNT_WORDS=24;
	// 唱片相关动画
	private Animation mPanAnim;
	private Animation mBarInAnim;
	private Animation mBarOutAnim;
	// 它代表动画运动速度  
	private LinearInterpolator mPanLin;
	private LinearInterpolator mBarLin;
	private LinearInterpolator mBarOutLin;
	//文字框的容器
	private ArrayList<WorldButton> mAllWords;
	private MyGridView mMyGridView;
	private ArrayList<WorldButton> mBtnSelectWords;
	//当前关索引
	private TextView mCurrentStagePassView;
	//已选择文字框UI容器
	private LinearLayout mViewWordsContainer;
	//播放的点击事件
	private ImageButton mBtnPlayStart;
	//
	private boolean mIsRunning=false;
	private Button back_to_mainmenu;
	//唱片的View
	private ImageView mViewPan;
	private ImageView mViewPanBar;
	//当前关的索引
	private int mCurrentStageIndex=-1;
	//
	private TextView mCurrentStageView;
	//当前歌曲名称
	private TextView mCurrentSongNamePassView;
	//当前金币数量
	private int mCurrentCoins=Const.TOTAL_COINS;
	//金币View
	private TextView mViewCurrentCoins;
	//当前的歌曲
	private Song mCurrentSong;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        //读取数据
        int[] datas=Util.loadData(this);
        mCurrentStageIndex=datas[Const.INDEX_LOAD_DATA_STAGE];
        mCurrentCoins=datas[Const.INDEX_LOAD_DATA_COINS];
        //初始化控件
        mMyGridView =(MyGridView) findViewById(R.id.gridview);
        mViewWordsContainer=(LinearLayout) findViewById(R.id.word_select_container);
        mViewCurrentCoins=(TextView) findViewById(R.id.txt_bar_coins);
        mViewCurrentCoins.setText(mCurrentCoins+ "");
        //注册监听
        mMyGridView.registOnWordButtonClick(this);
       back_to_mainmenu=(Button) findViewById(R.id.btn_bar_back);
        back_to_mainmenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent();
				i.setClass(MainActivity.this, MainMenu.class);
				startActivity(i);
			}
		});
        //初始化动画
        mPanAnim=AnimationUtils.loadAnimation(this, R.anim.rotate_45);
        mPanLin=new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        //设置动画的监听事件
        mPanAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mViewPanBar.setAnimation(mBarOutAnim);
			}
		});
        
        mBarInAnim=AnimationUtils.loadAnimation(this, R.anim.rotate);
        mBarLin=new LinearInterpolator();
       mBarInAnim.setFillAfter(true);
        mBarInAnim.setInterpolator(mBarLin);
        mBarInAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mViewPan.startAnimation(mPanAnim);
				
			}
		});
        
        
        mBarOutAnim=AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
        mBarOutLin=new LinearInterpolator();
       mBarOutAnim.setFillAfter(true);
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setAnimationListener(new AnimationListener() {
			
     			@Override
     			public void onAnimationStart(Animation animation) {
     				// TODO Auto-generated method stub
     				
     			}
     			
     			@Override
     			public void onAnimationRepeat(Animation animation) {
     				// TODO Auto-generated method stub
     				
     			}
     			
     			@Override
     			public void onAnimationEnd(Animation animation) {
     				// TODO Auto-generated method stub
     				//mIsRunning=false;
     				//mBtnPlayStart.setVisibility(View.VISIBLE);
     			}
     		});
        
        
        mViewPan=(ImageView) findViewById(R.id.imageView1);
        mViewPanBar=(ImageView) findViewById(R.id.imageView2);
        
        
        mBtnPlayStart=(ImageButton) findViewById(R.id.btn_play_start);
        mBtnPlayStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				handlePlayButton();
			}
		});
        //初始化游戏数据
        initCurrentStageData();
        //处理删除按键事件
        handleDeleteWord();
        //处理提示按键事件
        handleTipAnswer();
      
    }
	@Override
	public void onWordButtonClick(WorldButton wordButton) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, wordButton.mIndex+"", Toast.LENGTH_LONG).show();
		setSelectWord(wordButton);
		//获得答案状态
		int checkResult=checkTheAnswer();
		//检查答案
		if(checkResult==STATUS_ANSWER_RIGHT){
			//过关并获得奖励
			handlePassEvent();
		}else if(checkResult==STATUS_ANSWER_WRONG){
			//闪烁文字并提示用户
			sparkTheWrods();
		}else if(checkResult==STATUS_ANSWER_LACK){
			//设置文字颜色为白色，正常
			for(int i=0;i<mBtnSelectWords.size();i++){
				mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
			}
		}
	}
	//处理过关界面及事件
	private void handlePassEvent(){
		//显示过关界面
		mPassView=(LinearLayout)this.findViewById(R.id.pass_view);
		mPassView.setVisibility(View.VISIBLE);
		//停止未完成的动画
		mViewPan.clearAnimation();
		//停止正在播放的音乐
		MyPlayer.stopTheSong(MainActivity.this);
		//播放音效
		MyPlayer.playTone(MainActivity.this, MyPlayer.INDEX_STONE_COIN);
		
		//当前关的索引
		mCurrentStagePassView=(TextView) findViewById(R.id.text_current_stage_pass);
		if(mCurrentStagePassView !=null){
		}		mCurrentStagePassView.setText((mCurrentStageIndex + 1) + "");
		//显示歌曲名称
	mCurrentSongNamePassView=(TextView) findViewById(R.id.text_current_song_name_pass);
	if(mCurrentSongNamePassView!=null){
		mCurrentSongNamePassView.setText(mCurrentSong.getSongName());
	}
	//下一关按键处理
	ImageButton btnPass=(ImageButton) findViewById(R.id.btn_next);
	btnPass.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(judegAppPassed()){
				//进入通关界面
				Util.startActivity(MainActivity.this, AllPassView.class);
			}else{
				//开始新一关
				mPassView.setVisibility(View.GONE);
				//加载关卡数据
				initCurrentStageData();
			}
		}
	});
	}
	//判断是否通关
	private boolean judegAppPassed(){
		return(mCurrentStageIndex==Const.SONG_INFO.length-1);
	}
	//清除答案
	private void clearTheAnswer(WorldButton wordButton){
		wordButton.mViewButton.setText("");
		wordButton.mWroldString="";
		wordButton.isVisiable=false;
		//设置待选框可见性
		setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
	}
	private void setSelectWord(WorldButton wordButton){
		for(int i=0;i<mBtnSelectWords.size();i++){
			if(mBtnSelectWords.get(i).mWroldString.length()==0){
				//设置答案文字框内容及可见性
				mBtnSelectWords.get(i).mViewButton.setText(wordButton.mWroldString);
				mBtnSelectWords.get(i).isVisiable=true;
				mBtnSelectWords.get(i).mWroldString=wordButton.mWroldString;
				//记录索引
				mBtnSelectWords.get(i).mIndex=wordButton.mIndex;
				MyLog.d(TAG, mBtnSelectWords.get(i).mIndex+"");
				//设置待选框可见性
				setButtonVisiable(wordButton, View.INVISIBLE);
				break;
			}
		}
	}
	private void setButtonVisiable(WorldButton button,int visibility){
		button.mViewButton.setVisibility(visibility);
		button.isVisiable=(visibility==View.VISIBLE)? true : false;
		
		MyLog.d(TAG, button.isVisiable + "");
	}
private void handlePlayButton(){
	if(mViewPanBar!=null){
		//if(!mIsRunning){
			//mIsRunning=true;
			mViewPanBar.startAnimation(mBarInAnim);
			//mBtnPlayStart.setVisibility(View.INVISIBLE);
			//播放音乐
			MyPlayer.playSong(MainActivity.this, mCurrentSong.getSongFileName());
		//}
	}
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	//保存游戏数据
	Util.saveData(MainActivity.this, mCurrentStageIndex-1, mCurrentCoins);
	mViewPan.clearAnimation();
	//暂停音乐
	MyPlayer.stopTheSong(MainActivity.this);
	super.onPause();
}
private Song loadStageSongInfo(int stageIndex){
	Song song=new Song();
	String[] stage=Const.SONG_INFO[stageIndex];
	song.setSongFileName(stage[Const.INDEX_FILE_NAME]);
	song.setSongName(stage[Const.INDEX_SONG_NAME]);
	return song;
}
//
private void initCurrentStageData(){
	//读取当前关的歌曲信息
	mCurrentSong=loadStageSongInfo(++mCurrentStageIndex);
	//初始化已选择框
	mBtnSelectWords=initWorldSelect();
	LayoutParams params=new LayoutParams(100,100);
//清空原来的答案
	mViewWordsContainer.removeAllViews();
	for(int i=0;i<mBtnSelectWords.size();i++){
		mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton,params);
	}
	//显示当前关的索引
	mCurrentStageView=(TextView) findViewById(R.id.text_current_stage);
	if(mCurrentStageView!=null){
		mCurrentStageView.setText((mCurrentStageIndex+1)+"");
	}
	//获得数据
	mAllWords=initAllWord();
	//更新数据
	mMyGridView.updateData(mAllWords);
	  //一开始播放音乐
   // handlePlayButton();
}

//获取数据的方法
private ArrayList<WorldButton> initAllWord(){
	ArrayList<WorldButton> data=new ArrayList<WorldButton>();
	//获得待选文字
	//............
	String[] words=generateWords();
	for(int i=0;i<MyGridView.COUNT_WORDS;i++){
		WorldButton button=new WorldButton();
		button.mWroldString=words[i];
		data.add(button);
	}
	return data;
}
//初始化已选择文字框
private ArrayList<WorldButton> initWorldSelect(){
	ArrayList<WorldButton> data=new ArrayList<WorldButton>();
	for(int i=0;i<mCurrentSong.getNameLength();i++){
		View view=Util.getView(MainActivity.this, R.layout.self_ui_gridview_item);
	final WorldButton holder=new WorldButton();
		holder.mViewButton=(Button) view.findViewById(R.id.item_btn);
		//设置已选择文字框的背景颜色
		holder.mViewButton.setTextColor(Color.WHITE);
		holder.mViewButton.setText("");
		holder.isVisiable=false;
		holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
		holder.mViewButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearTheAnswer(holder);
				
			}
		});
		data.add(holder);
		
		
	}
	return data;
}
//生成所有的待选文字

private String[] generateWords(){
	Random random=new Random();
	String[] words=new String[MyGridView.COUNT_WORDS];
	//存入歌名
	for(int i=0;i<mCurrentSong.getNameLength();i++){
		words[i]=mCurrentSong.getNameCharacters()[i]+"";
		
	}
	//获取随机文字并存入数组
	for(int i=mCurrentSong.getNameLength();i<MyGridView.COUNT_WORDS;i++){
		words[i]=getRandomChar()+ "";
	}
	//打乱文字顺序：首先从所有元素中随机选取一个第一个元素进行交换
	//然后在第二个之后选择一个元素与第二个交换，知道最后一个元素
	//这样能够确保每个元素在每个位置的概率都是1/n
	for(int i=MyGridView.COUNT_WORDS-1;i>=0;i--){
		int index=random.nextInt(i+1);
		String buf=words[index];
		words[index]=words[i];
		words[i]=buf;		
	}
	return words;
}
private char getRandomChar(){
	String str="";
	int hightPos;
	int lowPos;
	
	Random random=new Random();
	hightPos=(176+Math.abs(random.nextInt(39)));
	lowPos=(161+Math.abs(random.nextInt(93)));
	
	byte[] b=new byte[2];
	b[0]=(Integer.valueOf(hightPos)).byteValue();
	b[1]=(Integer.valueOf(lowPos)).byteValue();
	
	try {
		str=new String(b,"GBK");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return str.charAt(0);
}
//检测答案
private int checkTheAnswer(){
	//先检查长度
	for(int i=0;i<mBtnSelectWords.size();i++){
		//如果有空，说明答案不完整
		if(mBtnSelectWords.get(i).mWroldString.length()==0){
			return STATUS_ANSWER_LACK;
		}
	}
	//答案完整，继续检查正确性
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<mBtnSelectWords.size();i++){
		sb.append(mBtnSelectWords.get(i).mWroldString);
		
	}
return (sb.toString().equals(mCurrentSong.getSongName()))?
		STATUS_ANSWER_RIGHT : STATUS_ANSWER_WRONG;
}
//文字闪烁
private void sparkTheWrods(){
	//定时器相关
	TimerTask task=new TimerTask(){
boolean mChange=false;
int mSpardTimes=0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(++mSpardTimes>SPASH_TIMES){
						return;
					}
					//执行闪烁逻辑，交替显示红色和白色文字
					for(int i=0;i<mBtnSelectWords.size();i++){
						mBtnSelectWords.get(i).mViewButton.setTextColor(
								mChange ? Color.RED: Color.WHITE
								);
					}
					mChange=!mChange;
				}});
		}};
		Timer timer=new Timer();
		timer.schedule(task, 1, 150);
}
//自动选择一个答案
private void tipAnswer(){

	boolean tipWord=false;
	for(int i=0;i<mBtnSelectWords.size();i++){
		if(mBtnSelectWords.get(i).mWroldString.length()==0){
			//根据当前的答案框条件选择对应的文字填入
			onWordButtonClick(findIsAnswerWord(i));
			tipWord=true;
			//减少金币数量
			if(!handleCoins(-getTipCoins())){
				//金币数量不够，显示对话框
				showConfirmDialog(ID_DIALOG_LACK_COINS);
				return;
			}
			break;
		}
	}
	//没有找到可以填充的答案
	if(!tipWord){
		//闪烁文字提示用户
		sparkTheWrods();
	}
}
//删除文字
private void deleteOneWord(){
	//减少金币
	if(!handleCoins(-getDeleteWordCoins())){
		//金币不够，显示对话框
		showConfirmDialog(ID_DIALOG_LACK_COINS);
		return;
	}
	//将这个索引对应的WordButton设置为不可见
	setButtonVisiable(findNotAnswerWord(),View.INVISIBLE);
}
//找到一个
private WorldButton findNotAnswerWord(){
	Random random=new Random();
	WorldButton buf=null;
	while(true){
		int index=random.nextInt(MyGridView.COUNT_WORDS);
		buf=mAllWords.get(index);
		if(buf.isVisiable && !isTheAnswerWord(buf)){
			return buf;
		}
	}
}
//找到一个答案文字 index 当前需要填入答案的索引
private WorldButton findIsAnswerWord(int index){
	WorldButton buf=null;
	for(int i=0;i<MyGridView.COUNT_WORDS;i++){
		buf=mAllWords.get(i);
		if(buf.mWroldString.equals(""+mCurrentSong.getNameCharacters()[index])){
			return buf;
		}
	}
	return null;
}
//判断某个文字是否为答案
private boolean isTheAnswerWord(WorldButton word){
	boolean result=false;
	for(int i=0;i<mCurrentSong.getNameLength();i++){
		if(word.mWroldString.equals(""+mCurrentSong.getNameCharacters()[i])){
			result=true;
			break;
		}
	}
	return result;
}
//增加或减少指定数量的金币
private boolean handleCoins(int data){
	//判断当前总的金币数量是否可被减少
	if(mCurrentCoins+data>=0){
		mCurrentCoins+=data;
		mViewCurrentCoins.setText(mCurrentCoins+"");
		return true;
	}else{
		//金币不够
		return false;
	}
}
private int getDeleteWordCoins(){
	return this.getResources().getInteger(R.integer.pay_delete_word);
}
private int getTipCoins(){
	return this.getResources().getInteger(R.integer.pay_tip_answer);
}
//处理删除待选文字事件
private void handleDeleteWord(){
	ImageButton button=(ImageButton) findViewById(R.id.btn_delete_word);
	button.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//deleteOneWord();
			showConfirmDialog(ID_DIALOG_DELETE_WORD);
		}
	});
}
//处理提示按键事件
private void handleTipAnswer(){
	ImageButton button=(ImageButton) findViewById(R.id.btn_tip_answer);
	button.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		showConfirmDialog(ID_DIALOG_TIP_ANSWER);
			//	tipAnswer();
			
		}
	});
}
//自定义alertDialog事件响应
//删除错误答案
private IAlertDialogButtonListener mBtnOkDeleteWordListener=new IAlertDialogButtonListener() {
	
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		//执行事件
		deleteOneWord();
		
	}
};
//答案提示
private IAlertDialogButtonListener mBtnOkTipAnswerListener=new IAlertDialogButtonListener() {
	
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		//执行事件
		tipAnswer();
		
	}
};
//金币不足
private IAlertDialogButtonListener mBtnOkLackCoinsListener=new IAlertDialogButtonListener() {
	
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		//执行事件
		
	}
};
private void showConfirmDialog(int id){
	switch(id){
	case ID_DIALOG_DELETE_WORD:
		Util.showDialog(MainActivity.this,
		"确认花掉"+getDeleteWordCoins()+"个金币去掉一个错误答案？", mBtnOkDeleteWordListener);
		break;
	case ID_DIALOG_TIP_ANSWER:
		Util.showDialog(MainActivity.this,
		"确认花掉"+getTipCoins()+"个金币获得一个文字提示？", mBtnOkTipAnswerListener);
		break;
	case ID_DIALOG_LACK_COINS:
		Util.showDialog(MainActivity.this,
		"金币不足，去商店补充？", mBtnOkLackCoinsListener);
		break;
	}
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }   
}
