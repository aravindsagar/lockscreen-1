package com.zwh.lockscreen.lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/** 
 * ������BroadcastReceiver����
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class BootCompletedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context,LockService.class);
		//��������
		context.startService(i);
		Intent b=new Intent(context,LockActivity.class);
		b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//����Activity
		context.startActivity(b); 
	}
}
