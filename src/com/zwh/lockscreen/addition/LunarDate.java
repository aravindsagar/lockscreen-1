package com.zwh.lockscreen.addition;
/** 
 * ũ�����ڵ���
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */ 
public class LunarDate {
	private int year;
	private int month;
	private int day;
	private int leapMonth;
	
	
	/** 
	 * ����ũ������
	 * @param
	 * @return
	 * @exception  
	 * @see        
	 * @since 
	 */
	public void setDate(int year,int month,int day){
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public void setYear(int year){
		this.year = year;
	}
	public void setMonth(int month){
		this.month = month;
	}
	public void setDay(int day){
		this.day = day;
	}
	
	/** 
	 * ��������
	 * @param
	 * @return
	 * @exception  
	 * @see        
	 * @since 
	 */
	public void setLeapMonth(int leapMonth){
		this.leapMonth = leapMonth;
	}
	
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	public int getDay(){
		return day;
	}
	public int leapMonth(){
		return leapMonth;
	}
}
