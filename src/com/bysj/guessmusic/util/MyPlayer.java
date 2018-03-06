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
//��Ч���ļ���
	private final static String[] SONG_NAMES={"enter.mp3","cancel.mp3","coin.mp3"};
	//��Ч
	private static MediaPlayer[] mToneMediaPlayer=new MediaPlayer[SONG_NAMES.length];
	
	
	//��������
	private static MediaPlayer mMusicMediaPlayer;
	//������Ч����ť��������������뷴������
	public static void playTone(Context context,int toneIndex){
		//��������
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
	//���Ÿ���
	public static void playSong(Context context,String fileName){
	if(mMusicMediaPlayer==null){
		mMusicMediaPlayer=new MediaPlayer();
	}	
	//ǿ������
	mMusicMediaPlayer.reset();
	//���������ļ�
	AssetManager assetManager=context.getAssets();
	
	try {
		AssetFileDescriptor fileDescripyor=assetManager.openFd(fileName);
	mMusicMediaPlayer.setDataSource(fileDescripyor.getFileDescriptor(),
		fileDescripyor.getStartOffset(),
		fileDescripyor.getLength());
		mMusicMediaPlayer.prepare();
		//��������
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
