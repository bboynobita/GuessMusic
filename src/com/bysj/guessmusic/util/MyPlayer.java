package com.bysj.guessmusic.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

public class MyPlayer {
	public final static int INDEX_STONE_ENTER=0;
	public final static int INDEX_STONE_CANCEL=1;
	public final static int INDEX_STONE_COIN=2;
//音效的文件名
	private final static String[] SONG_NAMES={"enter.mp3","cancel.mp3","coin.mp3"};
	//音效
	private static MediaPlayer[] mToneMediaPlayer=new MediaPlayer[SONG_NAMES.length];
	
	
	//歌曲播放
	private static MediaPlayer mMusicMediaPlayer;
	//播放音效，按钮点击的声音，无须反复加载
	public static void playTone(Context context,int toneIndex){
		//加载声音
		AssetManager assetManager=context.getAssets();
		if(mToneMediaPlayer[toneIndex]==null){
			mToneMediaPlayer[toneIndex]=new MediaPlayer();
			try {
				AssetFileDescriptor fileDescriptor=assetManager.openFd(SONG_NAMES[toneIndex]);
				mToneMediaPlayer[toneIndex].setDataSource(fileDescriptor.getFileDescriptor(),
						fileDescriptor.getStartOffset(),fileDescriptor.getLength());
				mToneMediaPlayer[toneIndex].prepare();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mToneMediaPlayer[toneIndex].start();
	}
	//播放歌曲
	public static void playSong(Context context,String fileName){
	if(mMusicMediaPlayer==null){
		mMusicMediaPlayer=new MediaPlayer();
	}	
	//强制重置
	mMusicMediaPlayer.reset();
	//加载声音文件
	AssetManager assetManager=context.getAssets();
	
	try {
		AssetFileDescriptor fileDescripyor=assetManager.openFd(fileName);
	mMusicMediaPlayer.setDataSource(fileDescripyor.getFileDescriptor(),
		fileDescripyor.getStartOffset(),
		fileDescripyor.getLength());
		mMusicMediaPlayer.prepare();
		//声音播放
		mMusicMediaPlayer.start();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public static void stopTheSong(Context context){
		if(mMusicMediaPlayer!=null){
			mMusicMediaPlayer.stop();
		}
	}
}
