package com.zwh.lockscreen.bubble;

import com.zwh.lockscreen.set.PreferenceInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ImageView;
/** 
 * ��������View
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class LockView extends ImageView implements Runnable {
	private static final int MESSAGE_RUN = 0x101;	//ˢ�»�������Ϣ
	public static final int MESSAGE_FINISH = 0x102;	
	private Paint paint;       						//���廭��   
	private VelocityTracker vTracker;  				//
	private Handler handler;						//��������Ϣ����
	private RefreshHandler mHandler;  				//ˢ�»�������Ϣ����
	private BallGroup bubble;						//Ball Group
	private boolean run = false;
	private boolean draw = false;
	private Context context;
	PreferenceInfo info = null;						//����������Ϣ
	
	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	public LockView(Context context) {
		super(context,null);
		this.context = context;
	}
	public LockView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
		this.context = context;
	}
	public void setHandler(Handler handler){
		this.handler = handler;
		mHandler = new RefreshHandler();
		setFocusable(true); 
		//�����߳�
		new Thread(this).start();
		run = true;
	}
	
	/** 
	 * ��ʼ��
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	private void init(){
		initPaint();
		float width = getWidth();
		float height = getHeight();

		float xDivisor = width/480; 
		float yDivisor = height/800;
		float divisor = xDivisor<yDivisor ? xDivisor:yDivisor;	//��������

		RectF rect = new RectF(0, 0, width, height);

		bubble = new BallGroup(context, getResources(), paint, rect,divisor);
		info = new PreferenceInfo(context);
		vTracker = VelocityTracker.obtain();
	}
	/** 
	 * ��ʼ������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	private void initPaint(){
		paint = new Paint();  
		//�����������   
		paint.setAntiAlias(true);  
	}  
	
	//True if the event was handled, false otherwise.
	@Override
	public boolean onTouchEvent(MotionEvent event){
		boolean result = true;
		float cx,cy;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			cx =  event.getX();
			cy =  event.getY();
			//�Ƿ��������
			bubble.contains(cx, cy);
			if(null!=bubble.ballBeClick){
				//��������
				vTracker.clear();
				//���õ�������ٶ�Ϊ0����ֹ�����
				bubble.setClickBallVelocity(0, 0);
				vTracker.addMovement(event);
			}
			else{
				//û�е�����
				result = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			cx = event.getX();
			cy = event.getY();
			if(bubble.setClickBallPosition(cx, cy)){
				vTracker.addMovement(event);
				vTracker.computeCurrentVelocity(10);
				bubble.setClickBallVelocity(vTracker.getXVelocity(), vTracker.getYVelocity());
			}
			break;  
		case MotionEvent.ACTION_UP:
			cx = event.getX();
			cy = event.getY();
			if(bubble.setClickBallPosition(cx, cy)){
				vTracker.addMovement(event);
				vTracker.computeCurrentVelocity(10);
				bubble.setClickBallVelocity(vTracker.getXVelocity(), vTracker.getYVelocity());
				unlock();
			}
			bubble.freeClickBall();
			break;
		default:
			break;
		}  
		return result;  
	} 
	/** 
	 * ���������͹㲥
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	private void unlock(){
		Message message = new Message();
		message.what = MESSAGE_FINISH;
		message.arg1 = bubble.getClickIndex();
		handler.sendMessage(message);
		/*if(info.showBattery()){
			battery.unregisterReceiver(context);
		}*/
		run = false;
	}

	public void run() {
		// TODO Auto-generated method stub
		while( !Thread.currentThread().isInterrupted() && run){
			//ͨ��������Ϣ���½���
			Message message = new Message();
			message.what = MESSAGE_RUN ;
			mHandler.sendMessage(message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//����СԲ��ΪС��
		if(!draw){
			draw = true;
			init();
		}
		bubble.moveStep(); 				//�ƶ���
		//paint.setTextSize(36);
		bubble.onDraw(canvas);
	}
	
	/** 
	 * ���½��洦����
	 * @param
	 * @author ֣�Ļ�
	 * @Date   2012��1��15��
	 */ 
	class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message message){
			if( MESSAGE_RUN  == message.what ){
				LockView.this.invalidate();
			}
			super.handleMessage(message);
		}
	}
}
