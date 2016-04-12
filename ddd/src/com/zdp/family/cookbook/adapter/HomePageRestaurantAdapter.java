package com.zdp.family.cookbook.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.activity.CommentActivity;
import com.zdp.family.cookbook.entity.Food;

public class HomePageRestaurantAdapter extends BaseAdapter {

	private Context context;
	private List<Food> mlist;
	private LayoutInflater inflater;

	public HomePageRestaurantAdapter(Context context, List<Food> list) {
		this.context = context;
		this.mlist = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.restaurant_list_item_dev,
					null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.restaurant_list_item_name);
			holder.logo = (ImageView) convertView
					.findViewById(R.id.restaurant_list_item_logo);
			holder.item_msg = (TextView) convertView
					.findViewById(R.id.restaurant_list_item_msg);
			holder.buy_nums = (TextView) convertView
					.findViewById(R.id.restaurant_list_item_buynums);
			holder.rate = (RatingBar) convertView
					.findViewById(R.id.restaurant_list_item_rate);
			holder.rest = (ImageView) convertView
					.findViewById(R.id.restaurant_list_item_avaiable);
			holder.favor = (ImageView) convertView
					.findViewById(R.id.restaurant_list_item_favor);
			holder.btn = (TextView) convertView.findViewById(R.id.comment);
			holder.promotion = (LinearLayout) convertView
					.findViewById(R.id.restaurant_list_item_present_promotion_container);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(mlist.get(position).getName());
		holder.buy_nums.setText(mlist.get(position).getMonthcount());
		holder.item_msg.setText(mlist.get(position).getMemo());
		holder.rate.setNumStars(Integer.parseInt(mlist.get(position)
				.getMinutes()));
		holder.btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CommentActivity.class);
				intent.putExtra("name", mlist.get(position).getName());
				intent.putExtra("id", mlist.get(position).getId());
				context.startActivity(intent);

			}
		});

		

		return convertView;
	}

	public class ViewHolder {
		public ImageView logo;
		public TextView name;
		public TextView rate_numbers;
		public TextView buy_nums;
		public TextView item_msg;
		public ImageView rest;
		public ImageView favor;
		public RatingBar rate;
		TextView btn;
		public LinearLayout promotion;

	}

}
