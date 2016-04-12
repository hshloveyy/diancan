package com.zdp.family.cookbook.fragment;

import java.util.ArrayList;
import java.util.List;

import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.entity.Order;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter{

	private List<Order> list=new ArrayList<Order>();
	
	private Context context;
	
	public OrderAdapter(Context context,List<Order> list2) {
		this.list=list2;
		this.context=context;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item, null);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);
			holder.phone = (TextView) convertView.findViewById(R.id.phone);
			holder.id = (TextView) convertView.findViewById(R.id.id);
		//	holder.bt = (Button) convertView.findViewById(R.id.btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			holder.desc.setText(list.get(position).getContent());
		    holder.phone.setText(list.get(position).getFlag());
		    holder.id.setText(list.get(position).getId());
		    
		return convertView;
	}


	class ViewHolder {
		TextView desc;
		TextView phone;
		TextView id;
		Button bt;
	}
}
