package com.zwh.lockscreen.addition;

import com.zwh.lockscreen.R;
import com.zwh.lockscreen.set.PreferenceInfo;
import android.content.Context;
import android.media.MediaPlayer;
/** 
 * ������Ƶ����
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class PlaySound {
	private Context context;
	private boolean isOpen = true;     //��Ч�Ƿ��
	public PlaySound(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		//�ж���Ч�Ƿ��
		isOpen = new PreferenceInfo(context).isVoiceOpen();     
	}
	/** 
	 * ��ʼ����
	 * @param
	 * @return
	 * @exception  
	 * @see        
	 * @since 
	 */
	public void startPlayer(){
		if(isOpen){
			MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.open);
			mediaPlayer.start();
		}
	}
}
