package com.zwh.lockscreen.bubble;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.zwh.lockscreen.R;
import com.zwh.lockscreen.addition.UnlockInsert;
import com.zwh.lockscreen.addition.Vibrate;
import com.zwh.lockscreen.set.PreferenceInfo;
/** 
 * ����Ⱥ
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class BallGroup {
	private int max_radius = 80;		//���ݵ����뾶
	private final int MAX_VELOCITY = 6; //���ݵ�����ٶȵ�2��
	public Ball[] balls;				//ȫ��������
	private int numBalls ; 				//���ݵ���Ŀ
	public Ball ballBeClick = null; 	//��ǰ�����е�����
	public int  ballindex = 0; 			//��ǰ�����е�����
	private Vibrate vibrate;
	private Context context;

	private static int[] bmp;
	/** 
	 * ������
	 * @param divisor 
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public BallGroup(Context context, Resources resources, Paint paint, RectF rect, float divisor) {
		max_radius*=divisor;              //�������ݵ����뾶
		this.context = context;
		vibrate = new Vibrate(context);
		init(resources, paint, rect);
	}

	/** 
	 * ��ʼ������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	private void init(Resources resources, Paint paint, RectF rect){
		UnlockInsert insert = new UnlockInsert(context);
		numBalls = insert.getCount(); 
		if(0>=numBalls){
			numBalls++;			//���ٺ���һ������
		}
		balls = new Ball[numBalls];
		Random random = new Random();
		InitBallPosition iPosition = new InitBallPositionStar(rect, numBalls, max_radius);
		Ball ball;
		Bitmap bitmap; 
		Bitmap icon; 
		int halfMaxVelocity = MAX_VELOCITY/2; 
		float vx;
		float vy;

		PreferenceInfo info = new PreferenceInfo(context);
		switch (info.getBubble()) {
		case 0:
			bmp = new int[]{
					R.drawable.ba,
					R.drawable.bb,
					R.drawable.bc,
					R.drawable.bd,};
			break;
		case 1:
			bmp = new int[]{R.drawable.e};
			break;
		case 2:
			bmp = new int[]{R.drawable.g};
			break;
		case 3:
			bmp = new int[]{
					R.drawable.j,
					R.drawable.k,
					R.drawable.l,
					R.drawable.m,
					R.drawable.n,};
			break;
		case 4:
			bmp = new int[]{R.drawable.f};
			break;
		case 5:
			bmp = new int[]{R.drawable.h};
			break;
		case 6:
			bmp = new int[]{R.drawable.i};
			break;
		default:
			bmp = new int[]{
					R.drawable.ba,
					R.drawable.bb,
					R.drawable.bc,
					R.drawable.bd,};
			break;
		}

		for (int i = 0; i < numBalls; i++) {
			bitmap = BitmapFactory.decodeResource(resources, bmp[i%bmp.length]);
			ball = new Ball(vibrate,max_radius, bitmap, paint, rect);

			PointF position = iPosition.getPosition(i);
			ball.setPosition(position);
			icon = insert.getBitmap(i);
			ball.setIcon(icon);
			vx = random.nextInt(MAX_VELOCITY)-halfMaxVelocity;
			vy = random.nextInt(MAX_VELOCITY)-halfMaxVelocity;
			ball.setVelocity(vx, vy); 

			balls[i] = ball; 
		} 
	}

	/** 
	 * ��������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void onDraw(Canvas canvas){
		for (int i = 0; i < numBalls; i++) {
			balls[i].onDraw(canvas);
		}
	}

	/** 
	 * �������ݵ��ٶ�
	 * @param 
	 * @return    
	 * @exception
	 * @see        
	 * @since      
	 */
	public boolean setClickBallVelocity(float vx, float vy){
		boolean result = false;
		if( null != ballBeClick){	
			ballBeClick.setVelocity(vx, vy);
			result = true;
		}
		return result;
	}

	/** 
	 * ���õ������ݵ�λ��
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public boolean setClickBallPosition(float cx, float cy){
		float xDistance;
		float yDistance;
		float distanceSquare;
		boolean result = false;
		Ball ball;
		int i;
		if( null != ballBeClick){
			for( i = 0; i < numBalls; i++ ){ 
				ball = balls[i];

				if( ball == ballBeClick){
					continue;
				}
				//x�᷽���������ݵľ��� ��x
				xDistance = ball.getX() - cx;
				//y�᷽���������ݵľ��� ��y
				yDistance = ball.getY() - cy;
				//�����ݵľ����ƽ�� ��d*d
				distanceSquare = xDistance * xDistance + yDistance * yDistance;

				if( distanceSquare < (ball.getRadius() * ball.getRadius())){
					break;
				}
			}
			if( i == numBalls ){
				ballBeClick.setPosition(cx, cy);
				result = true;
			}
		}
		return result;
	}

	public int getClickIndex(){
		return ballindex;
	}

	/** 
	 * �ͷű����������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void freeClickBall(){
		if( null != ballBeClick ){
			ballBeClick.setMass(1);
			ballBeClick = null;
			ballindex = 0;
		}
	} 

	/** 
	 * λ���Ƿ���ĳ��������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void contains( float cx, float cy){
		freeClickBall();
		Ball ball = null;
		for (int i = 0; i < numBalls; i++) {
			ball = balls[i];
			//		for( Ball ball : balls ){  
			if( ball.contains(cx, cy)){
				ballindex = i;
				ballBeClick = ball;
				ballBeClick.setMass(0xffff);
				vibrate.vibrate();
				break;
			}
		}
	}

	/** 
	 * �ƶ�����
	 * ��������֮�����ײ
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	public void moveStep(){
		//�ƶ����ݡ�
		for (Ball ball : balls) {
			ball.moveStep();
		}

		//��������֮�����ײ
		Ball ball; 
		for (int i = 0; i < numBalls - 1; i++) {
			ball = balls[i];
			for (int j = i + 1; j < numBalls; j++) {
				handleCollision(ball, balls[j]);
			}
		}
	}

	/** 
	 * �����������ݵ���ײ��������ײ�������غ㣬�����غ㡣
	 * �������������ײ����ȷ��������true����������false�����ݵĲ������ı�
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	private void handleCollision( Ball ball0, Ball ball1){
		float dx = ball1.getX() - ball0.getX();
		float dy = ball1.getY() - ball0.getY();
		float dist = (float) Math.sqrt( dx*dx + dy*dy);

		if (dist<ball0.getRadius()+ball1.getRadius()) {
			// ����ǶȺ�������ֵ 
			float angle = (float) Math.atan2(dy,dx);

			float sin = (float) Math.sin(angle);
			float cos = (float) Math.cos(angle);
			// ��ת ball0 ��λ�� 
			PointF pos0 = new PointF(0,0);
			// ��ת ball1 ���ٶ� 
			PointF pos1 = rotate(dx,dy,sin,cos,true);
			// ��ת ball0 ���ٶ� 
			PointF vel0 = rotate(ball0.getVX(),ball0.getVY(),sin,cos,true);
			// ��ת ball1 ���ٶ� 
			PointF vel1 = rotate(ball1.getVX(),ball1.getVY(),sin,cos,true);
			// ��ײ�������� 
			float vxTotal = vel0.x-vel1.x;
			vel0.x = ((ball0.getMass() - ball1.getMass()) * vel0.x + 2 * ball1.getMass() * vel1.x) / (ball0.getMass() + ball1.getMass());
			vel1.x = vxTotal+vel0.x;
			// ����λ�� 
			float absV = Math.abs(vel0.x)+Math.abs(vel1.x);
			if( 0 == absV){
				return;
			}
			float overlap = (ball0.getRadius() + ball1.getRadius()) - Math.abs(pos0.x - pos1.x);

			pos0.x += vel0.x/absV*overlap;
			pos1.x += vel1.x/absV*overlap;

			// ��λ����ת���� 
			PointF pos0F =rotate(pos0.x,pos0.y,sin,cos,false);
			PointF pos1F =rotate(pos1.x,pos1.y,sin,cos,false);

			// ��λ�õ���Ϊ��Ļ��ʵ��λ�� 
			ball1.setPosition(ball0.getX()+pos1F.x, ball0.getY()+pos1F.y);
			ball0.setPosition(ball0.getX()+pos0F.x, ball0.getY()+pos0F.y);

			// ���ٶ���ת���� 
			PointF vel0F = rotate(vel0.x,vel0.y,sin,cos,false);
			PointF vel1F = rotate(vel1.x,vel1.y,sin,cos,false);

			ball0.setVelocity(vel0F.x, vel0F.y);
			ball1.setVelocity(vel1F.x, vel1F.y);
			// ��
			// vibrate.vibrate();
		}
	}
	/** 
	 * ��ת���ݵ�λ��
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	private PointF rotate(float x, float y, float sin, float cos, boolean reverse){
		PointF result = new PointF();
		if (reverse) {
			result.x = x*cos + y*sin;
			result.y = y*cos - x*sin;
		}
		else {
			result.x = x*cos - y*sin;
			result.y = y*cos + x*sin;
		}
		return result;
	}
}
