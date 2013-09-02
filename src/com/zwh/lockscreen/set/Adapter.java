package com.zwh.lockscreen.set;

import java.util.List;
import java.util.Map;

import com.zwh.lockscreen.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
/** 
 * �̳�SimpleAdapter
 * @param
 * @author ֣�Ļ�
 * @Date   2012��1��9��
 */
public class Adapter extends SimpleAdapter{
	boolean[] select;
	public Adapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to,boolean []select) {
		super(context, data, resource, from, to);
		//listview��ÿһ����Ƿ�ѡ��
		this.select = select;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		CheckBox checkbox = (CheckBox) view.findViewById(R.id.if_open);
		//����checkbox��״̬
		checkbox.setChecked(select[position]);
		return view;
	}
}

