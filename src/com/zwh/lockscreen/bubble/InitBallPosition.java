package com.zwh.lockscreen.bubble;

import android.graphics.PointF;
import android.graphics.RectF;
/** 
 * ����λ�÷ֲ��ĳ�����
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public abstract class InitBallPosition {
	protected int num;          	//���ݵ���Ŀ
	protected int radius;			//���ݵİ뾶
	protected int xCenter;			//��Ļˮƽ�����ϵ��е�
	protected int yCenter;			//��Ļ��ֱ�����ϵ��е�
	public InitBallPosition(RectF rect, int num, int radius){
		this.num = num;
		this.radius = radius;
		xCenter = (int) ((rect.right - rect.left)/2);
		yCenter = (int) ((rect.bottom - rect.top)/2);
	}
	/** 
	 * ������
	 * �õ�λ������
	 * @param  
	 * @return    
	 * @exception  
	 * @see        
	 * @since      
	 */
	public abstract PointF getPosition( int index);
}
