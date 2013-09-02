package com.zwh.lockscreen.bubble;

import android.graphics.PointF;
import android.graphics.RectF;
/** 
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class InitBallPositionStar extends InitBallPosition {
	/** 
	 * ������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public InitBallPositionStar(RectF rect, int num, int radius) {
		super(rect, num, radius);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * �õ���index�����ݵ�λ������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public PointF getPosition( int index){
		PointF position = null;
		//��������������������ݵ�λ�����꣨��ͬ��Ŀ��������ɲ�ͬ����״����
		switch (num) {
		case 5:
			position = posionFive(index);
			break;
		case 4:
			position = posionFour(index);
			break;
		case 3:
			position = posionThree(index);
			break;
		case 2:
		case 1:
			position = posionTwo(index);
			break;
		default:
			break;
		}
		return position; 
	}
	/** 
	 * ����Ⱥ������Ϊ2ʱ��ÿ�����ݵ�λ������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public PointF posionTwo(int index){
		PointF position = null;
		if(index>=0 && index<num){
			int x = 0;
			int y = 0;
			switch (index) {
			case 0:
				x = xCenter-radius;
				y = yCenter-radius;
				break;
			case 1:
				x = xCenter+radius;
				y = yCenter+radius;
				break;
			default:
				break;
			}
			position = new PointF(x, y);
		}
		return position; 
	}
	
	public PointF posionThree(int index){
		PointF position = null;
		if(index>=0 && index<num){
			int x = 0;
			int y = 0;
			int R = (int) (1.8*radius);
			switch (index) {
			case 0:
				x = xCenter-radius;
				y = yCenter;
				break;
			case 1:
				x = xCenter+radius;
				y = yCenter;
				break;
			case 2:
				x = xCenter;
				y = yCenter-R;
				break;		
			default:
				break;
			}
			position = new PointF(x, y);
		}
		return position; 
	}
	
	public PointF posionFour(int index){
		PointF position = null;
		if(index>=0 && index<num){
			int x = 0;
			int y = 0;
			int R = (int) (1.8*radius);
			switch (index) {
			case 0:
				x = xCenter-radius;
				y = yCenter;
				break;
			case 1:
				x = xCenter+radius;
				y = yCenter;
				break;
			case 2:
				x = xCenter;
				y = yCenter+R;
				break;		
			case 3:
				x = xCenter;
				y = yCenter-R;
				break;	
			default:
				break;
			}
			position = new PointF(x, y);
		}
		return position; 
	}
	public PointF posionFive(int index){
		PointF position = null;
		if(index>=0 && index<num){
			int x = 0;
			int y = 0;
			int R = (int) (1.7*radius);
			switch (index) {
			case 0:
				x = xCenter;
				y = yCenter - R;
				break;
			case 1:
				x = (int) (xCenter - R*0.951);
				y = (int) (yCenter - R*0.309);
				break;
			case 2:
				x = (int) (xCenter + R*0.951);
				y = (int) (yCenter - R*0.309);
				break;		
			case 3:
				x = (int) (xCenter - R*0.588);
				y = (int) (yCenter + R*0.809);
				break;	
			case 4:
				x = (int) (xCenter + R*0.588);
				y = (int) (yCenter + R*0.809);
				break;
			default:
				break;
			}
			position = new PointF(x, y);
		}
		return position; 
	}
}
