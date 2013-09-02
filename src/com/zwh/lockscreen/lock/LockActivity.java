package com.zwh.lockscreen.lock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zwh.lockscreen.R;
import com.zwh.lockscreen.addition.BackGround;
import com.zwh.lockscreen.addition.PlaySound;
import com.zwh.lockscreen.addition.UnlockInsert;
import com.zwh.lockscreen.addition.Vibrate;
import com.zwh.lockscreen.bubble.LockView;
import com.zwh.lockscreen.set.PreferenceInfo;

/** 
 * ����������
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
@SuppressLint("HandlerLeak")
public class LockActivity extends Activity {
	/** Called when the activity is first created. */
	//	private final int SET_SHOW_ITEM = Menu.FIRST; 
	private boolean showStatusBar = true; 			//�Ƿ���ʾ״̬��
	
	private Handler handler = null;
	private TextView timeDisplay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//�ж��Ƿ���ʾ״̬��
		showStatusBar = new PreferenceInfo(this).showStatusBar();
		if(!showStatusBar){
			//Remove status bar
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		handler = new RefreshHandlerLock();
		setContentView(R.layout.lock_screen);
		
		timeDisplay = (TextView) findViewById(R.id.timeDisplay);
		timeDisplay.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueLTPro-Th.otf"));
		
		LockView view = (LockView)this.findViewById(R.id.bubblesView);
		view.setHandler(handler);
		View vieww = (View)this.findViewById(R.id.root);
		new BackGround().set(this, vieww);
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		//android�ײ�ϵͳ��HOME�������ˣ��������������TYPE_KEYGUARD��Ĵ��壬�򲻻���ˡ����԰�Activity�޸ĳ�TYPE_KEYGUARD
		//��ͺ���
		if(showStatusBar){
			this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD); 
		}
		super.onAttachedToWindow();
	}
	@Override  
	public boolean dispatchKeyEvent(KeyEvent event) { 
		return true;   
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	/** 
	 * ����������Ϣ����
	 * @param      
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	
	class RefreshHandlerLock extends Handler {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message message){
			switch (message.what) {
			case LockView.MESSAGE_FINISH:
				finish();
				new UnlockInsert(LockActivity.this).startActivity(message.arg1);
				new Vibrate(LockActivity.this).vibrate();
				new PlaySound(LockActivity.this).startPlayer();
				/*unregisterReceiver(receiver);*/
				break;
			}
				
			super.handleMessage(message);
		}
	}
}