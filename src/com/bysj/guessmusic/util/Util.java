package com.bysj.guessmusic.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bysj.guessmusic.R;
import com.bysj.guessmusic.data.Const;
import com.bysj.guessmusic.model.IAlertDialogButtonListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Util{
	private static AlertDialog mAlertDialog;
public static View getView( Context context ,int layoutId){
	LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View layout=inflater.inflate(layoutId, null);
	return layout;
}
//������ת
public static void startActivity(Context context,Class desti){
		Intent intent=new Intent();
		intent.setClass(context, desti);
		context.startActivity(intent);
		//�رյ�ǰ��Activity
		((Activity)context).finish();
		
	}
	public static void showDialog( final Context context,String message,final IAlertDialogButtonListener listener){
	View dialogView=null;
	AlertDialog.Builder bulider=new AlertDialog.Builder(context,R.style.Theme_Transparent);
	dialogView=getView(context, R.layout.dialog_view);
	ImageButton btnOkView=(ImageButton) dialogView.findViewById(R.id.btn_dialog_ok);
	ImageButton btnCancelView=(ImageButton) dialogView.findViewById(R.id.btn_dialog_cancle);
	TextView txtMessageView=(TextView) dialogView.findViewById(R.id.text_dialog_message);
	txtMessageView.setText(message);
	btnOkView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//�رնԻ���
			if(mAlertDialog!=null){
				mAlertDialog.cancel();
			}
			//�¼��ص�
			if(listener!=null){
				listener.onClick();
			}
			//������Ч
			MyPlayer.playTone(context, MyPlayer.INDEX_STONE_ENTER);
		}
	});
btnCancelView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(mAlertDialog!=null){
				mAlertDialog.cancel();
			}
		}
	});
MyPlayer.playTone(context, MyPlayer.INDEX_STONE_CANCEL);
	//Ϊdialog����view
bulider.setView(dialogView);
mAlertDialog=bulider.create();
//��ʾ�Ի���
mAlertDialog.show();
}
	//��Ϸ���ݱ���
public static void saveData(Context context,int stageIndex,int coins){
	FileOutputStream fis=null;
	try {
		fis=context.openFileOutput(Const.FILE_NAME_SAVE_DATA, Context.MODE_PRIVATE);
		DataOutputStream dos=new DataOutputStream(fis);
		dos.writeInt(stageIndex);
		dos.writeInt(coins);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
//��ȡ��Ϸ����                                                                                                                                                                                                                                                                                                                                                            
public static int[] loadData(Context context){
	
	FileInputStream fis=null;
	int[] datas={-1,Const.TOTAL_COINS};
	try {
		fis=context.openFileInput(Const.FILE_NAME_SAVE_DATA);
		DataInputStream dis=new DataInputStream(fis);
		datas[Const.INDEX_LOAD_DATA_STAGE]=dis.readInt();
		datas[Const.INDEX_LOAD_DATA_COINS]=dis.readInt();

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return datas;
}
	}
