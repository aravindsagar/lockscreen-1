package com.zwh.lockscreen.addition;

import com.zwh.lockscreen.set.PreferenceInfo;
import android.content.Context;
import android.os.Vibrator;
/** 
 * ��
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class Vibrate{
	private Context context;
	private boolean openVibrate = true;//������
	public Vibrate(Context context){ 
		this.context = context;
		//�ж��Ƿ��ѿ�����
		openVibrate = new PreferenceInfo(context).isVibrateOpen();
	}
	/** 
	 * ��
	 * @param      
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	public void vibrate(){
		if(openVibrate){
			@SuppressWarnings("static-access")
			Vibrator vibrator=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
			vibrator.vibrate(new long[]{0,40}, -1); 
		}
	}
}
