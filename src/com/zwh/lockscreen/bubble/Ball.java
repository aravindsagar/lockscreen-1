package com.zwh.lockscreen.bubble;

import com.zwh.lockscreen.addition.Vibrate;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
/** 
 * ����
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class Ball {
	private int radius;      	 		//�뾶
	private Bitmap bitmap;   	 		//ͼ��Դ
	private Bitmap icon;		 		//ͼ����Դ
	private Paint paint;     			//����

	private float x = 0;      	 		//Բ��x������
	private float y = 0;      	 		//Բ��y������
	private RectF screenRect;			//Բ��λ�÷�Χ

	private float vx = 0;    	 		//x���ٶ�
	private float vy = 0;    	 		//y���ٶ�

	private float mass = 1;   	 		//���� 
	private float bounce = -1.0f;		//����
	
	private float MAX_VELOCITY = 5.5f;  //����ٶ�
	private float MIN_VELOCITY = -5.5f; //��С�ٶ�

	//���ڱ�ʾ������������ǽ
	public static enum wall{
		NULL,
		TOP,
		BOTTOM,
		RIGHT,
		LEFT
	}
	/** 
	 * ������
	 * @param vibrate �񶯣�radius �뾶,  bitmap ���ݣ�paint ���ʣ�rect ����Բ�Ŀ��ƶ��ķ�Χ��  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	public Ball(Vibrate vibrate, int radius , Bitmap bitmap ,Paint paint, RectF rect) {
		this.radius = radius;
		this.bitmap = bitmap;
		this.paint = paint;
//		this.paint.setAntiAlias(true);
		//this.vibrate = vibrate;
		//����Բ������ķ�Χ
		setScreenRect(rect);
	}

	/** 
	 * ������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */ 
	public void onDraw(Canvas canvas) {
		RectF rect = new RectF(x-radius, y-radius, x+radius, y+radius);
		canvas.drawBitmap(bitmap, null, rect, paint);
		
//		canvas.drawBitmap(bitmap, x-radius, y-radius, paint);
		
		if(null!=icon){
			int xd = icon.getWidth()/2;
			int yd = icon.getHeight()/2; 
			canvas.drawBitmap(icon, x-xd, y-yd, paint);
		}
	}

	/** 
	 * ����Բ������ķ�Χ
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setScreenRect( RectF rect){
		if(null == screenRect){
			screenRect = new RectF();
		}
		float left = rect.left+radius;
		float top = rect.top+radius;
		float right = rect.right - radius;
		float bottom = rect.bottom - radius;

		screenRect.set(left, top, right, bottom);
	}

	/** 
	 * �������Խ�磨����λ�ã��������ٶȣ�
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	private wall handleWalls() {
		wall whichWall = wall.NULL;
		//ˮƽ������
		if(x < screenRect.left){
			//��߽�
			x = screenRect.left;
			vx *= bounce;
			whichWall = wall.LEFT;
			// ��
			//vibrate.vibrate();
		}
		else if(x > screenRect.right){
			//�ұ߽�
			x = screenRect.right;
			vx *= bounce;
			whichWall = wall.RIGHT;
			// ��
			//			vibrate.vibrate();
		}

		//��ֱ������
		if(y < screenRect.top){
			//�ϱ߽�
			y = screenRect.top;
			vy *= bounce;
			whichWall = wall.TOP;
			// ��
			//			vibrate.vibrate();
		}
		else if(y > screenRect.bottom){
			//�±߽�
			y = screenRect.bottom;
			vy *= bounce;
			whichWall = wall.BOTTOM;
			// ��
			//			vibrate.vibrate();
		}
		return whichWall;
	}

	/** 
	 * �����ٶȣ�������һ��λ�ã��Զ�����Խ�硣
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public wall moveStep(){
		x += vx;
		y += vy;
		return handleWalls();
	}

	/** 
	 * �жϸ�����λ���Ƿ���Բ��
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public boolean contains(float cx, float cy){
		boolean contains = false;

		float disX = x - cx;
		float disY = y - cy;
		//�㵽Բ�ĵľ���
		float dis = (float) Math.sqrt( disX*disX + disY*disY );

		if(dis<radius){
			contains = true;
		}
		return contains;
	}

	/** 
	 * ����λ�ã����Խ�磬�Զ�������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setPosition(float x,float y){
		this.x = x;
		this.y = y;
	}

	/** ����λ�ã����Խ�磬�Զ�������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setPosition(PointF position){
		this.x = position.x;
		this.y = position.y;
	}

	/** �õ�xֵ
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getX(){
		return x;
	}

	/** 
	 * �õ�yֵ
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getY(){
		return y;
	}
	/** 
	 * �����ٶ�
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public boolean setVelocity(float vx,float vy){
		if(vx>MAX_VELOCITY){
			vx = MAX_VELOCITY-0.5f;
		}
		else if(vx<MIN_VELOCITY){
			vx = MIN_VELOCITY+0.5f;
		}
		
		if(vy>MAX_VELOCITY){
			vy = MAX_VELOCITY-0.5f;
		}
		else if(vy<MIN_VELOCITY){
			vy = MIN_VELOCITY+0.5f;
		}
		
		this.vx = vx;
		this.vy = vy;
		return true;
	}
	/** 
	 * �õ�x������ٶ�ֵ
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getVX(){
		return vx;
	}
	/** 
	 * ��y������ٶ�ֵ
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getVY(){
		return vy;
	}
	/** 
	 * �����������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setMass(float mass){
		this.mass = mass;
	}
	/** 
	 * �����������
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getMass(){
		return mass;
	}
	/** 
	 * ������İ뾶
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setRadius(int radius){
		this.radius = radius;
	}
	/** 
	 * ������İ뾶
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public float getRadius(){
		return radius;
	}
	/** ����ͼ��
	 * @param 
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public void setIcon(Bitmap icon){
		this.icon = icon;
	}
}
