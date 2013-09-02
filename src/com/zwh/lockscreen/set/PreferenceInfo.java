package com.zwh.lockscreen.set;
/** 
 * ����������Ϣ�Ĵ���
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
public class PreferenceInfo {
	private SharedPreferences settings;
	static final String SHOW_CHOICE_DEFAULT_VALUE = "1/2/3/4/5/6";
	private Context context;
	private String choice;

	public PreferenceInfo(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		settings = PreferenceManager.getDefaultSharedPreferences(context); 
	}

	//�õ���������
	@SuppressLint("WorldReadableFiles")
	public String[] getLockSet(){
		String lockSet = settings.getString("lock_set", null);
		
		SharedPreferences preference = context.getSharedPreferences("LockScreenPackageList", Context.MODE_WORLD_READABLE);
		String packageList = preference.getString("packageList", "null");
		
		if(null==lockSet){
			LockSetMultiSelectListPreference l = new LockSetMultiSelectListPreference(context);
			return l.getDefaultString();
		}
		else{
			return new String[]{lockSet,packageList};
		}
	}

	//�õ�����ͼƬ
	public int getBackgroundPicture(){
		String picture = settings.getString("background_picture", "1");
		// zhengwenhui note
		int pic = Integer.valueOf(picture);
		return pic;
	}
	
	//�õ�����ͼƬ
	public int getBubble(){
		int pic = settings.getInt("lock_screen_method", 0);
		//int pic = Integer.valueOf(bubble);
		return pic;
	}

	//�õ�������ɫ
	public int getBackgroundColor(){
		int color = settings.getInt("colorpiker", 0);
		int alpha = settings.getInt("background_color_alpha", 100);
		alpha = (0xff * (100 - alpha)) / 100;
		color = (color & 0xffffff) | (alpha<<24);
		return color;
	}

	//�Ƿ���ʾ״̬��
	public boolean showStatusBar(){
		return getChoiceItem("0");
	}

	//�Ƿ���ʾʱ��
	public boolean showTime(){
		return getChoiceItem("1");
	}
	//�Ƿ���ʾ����
	public boolean showDate(){
		return getChoiceItem("2");
	}
	//�Ƿ���ʾũ������
	public boolean showLunarDate(){
		return getChoiceItem("3");
	}
	//�Ƿ���ʾ����
	public boolean showWeek(){
		return getChoiceItem("4");
	}
	
	public boolean aboutTime(){
		return showTime()|showDate()|showLunarDate()|showWeek();
	}
	
	//�Ƿ���ʾ�����Ϣ�����������ʱ��ʾ"�����"�����ȡ���ѹ���ߵȣ�
	public boolean showBattery(){
		return getChoiceItem("5");
	}
	//�Ƿ���ʾ����
	public boolean showAlarm(){
		return getChoiceItem("6");
	}
	
	private boolean getChoiceItem(String item){
		if(null == choice){
			choice = settings.getString("show_choice", SHOW_CHOICE_DEFAULT_VALUE);	
		}
		return choice.contains(item);
	}

	//��ʾλ������
	public int getShowSet(){
		int set = settings.getInt("show_set", 0xffffffff);
		return set;
	}	

	//�Ƿ������
	public boolean isVibrateOpen(){
		boolean open = settings.getBoolean("vibrate", true);
		return open;
	}

	//�Ƿ������Ч
	public boolean isVoiceOpen(){
		boolean open = settings.getBoolean("voice", true);
		return open;
	}
	
	//�Ƿ��Զ��������ʽ
	public boolean isDefine(){
		return settings.getBoolean("set_lock_screen", false);
	}
	
}
