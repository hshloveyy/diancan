package com.zdp.family.cookbook.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.google.gson.Gson;
import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.adapter.FoodListAdapter;
import com.zdp.family.cookbook.adapter.RestaurantDetailAdapter;
import com.zdp.family.cookbook.entity.Food;
import com.zdp.family.cookbook.entity.FoodVo;
import com.zdp.family.cookbook.util.InjectView;
import com.zdp.family.cookbook.util.Injector;
import com.zdp.family.cookbook.widget.stickylistheaders.StickyListHeadersListView;

public class RestaurantDetailActivity extends Activity implements
		AdapterView.OnItemClickListener,
		StickyListHeadersListView.OnHeaderClickListener,
		StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
		StickyListHeadersListView.OnStickyHeaderChangedListener {

	private RestaurantDetailAdapter mAdapter;
	private FoodListAdapter foodListAdapter;
	private boolean fadeHeader = true;
	@InjectView(R.id.linear_above_toHome)
	private LinearLayout above_toHome;
	@InjectView(R.id.tv_common_above_head)
	private TextView above_tittle;
	@InjectView(R.id.iv_head_left)
	private ImageView head_left;
	@InjectView(R.id.tv_common_above_head)
	private TextView head_tittle;
	@InjectView(R.id.food_list_shipping_fee)
	private TextView order_cart;
	@InjectView(R.id.food_list_take_order_button)
	private TextView food_list_take_order_button;

	private String restaurant_name;

	Intent intent;

	private StickyListHeadersListView stickyList;

	List<String> list = new ArrayList<String>();
	List<Food> mlist = new ArrayList<Food>();
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
//				mAdapter = new RestaurantDetailAdapter(this, order_cart);
				foodListAdapter = new FoodListAdapter(RestaurantDetailActivity.this, mlist, order_cart);
				// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
				stickyList = (StickyListHeadersListView) findViewById(R.id.list_restaurant_detail);
				stickyList.setOnItemClickListener(RestaurantDetailActivity.this);
				stickyList.setOnHeaderClickListener(RestaurantDetailActivity.this);
				stickyList.setOnStickyHeaderChangedListener(RestaurantDetailActivity.this);
				stickyList.setOnStickyHeaderOffsetChangedListener(RestaurantDetailActivity.this);
				stickyList.addHeaderView(getLayoutInflater().inflate(
						R.layout.restaurant_list_header, null));
				// stickyList.addFooterView(getLayoutInflater().inflate(
				// R.layout.restaurant_list_footer, null));
				stickyList.setDrawingListUnderStickyHeader(true);
				stickyList.setAreHeadersSticky(true);
				stickyList.setAdapter(foodListAdapter);

				// stickyList.setStickyHeaderTopOffset(-20);
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.restaurant_detail_main);
		Injector.get(this).inject();// init views
		intent = getIntent();
		
		restaurant_name = intent.getStringExtra("name");
		initView();
		getData();
		setListener();
		food_list_take_order_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent.putExtra("foodList",
						foodListAdapter.getShopCar());
				intent.putExtra("foodPrice", foodListAdapter.getTotal());
                intent.setClass(RestaurantDetailActivity.this, ConfirmPayActivity.class);
                startActivity(intent);
			}
		});

	}

	private void initView() {
		above_tittle.setText(restaurant_name);
		head_left.setImageResource(R.drawable.abc_ic_ab_back_holo_dark);

	}

	private void setListener() {
		// TODO Auto-generated method stub
		above_toHome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});

//		mAdapter = new RestaurantDetailAdapter(this, order_cart);
//		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
//		stickyList = (StickyListHeadersListView) findViewById(R.id.list_restaurant_detail);
//		stickyList.setOnItemClickListener(this);
//		stickyList.setOnHeaderClickListener(this);
//		stickyList.setOnStickyHeaderChangedListener(this);
//		stickyList.setOnStickyHeaderOffsetChangedListener(this);
//		stickyList.addHeaderView(getLayoutInflater().inflate(
//				R.layout.restaurant_list_header, null));
//		// stickyList.addFooterView(getLayoutInflater().inflate(
//		// R.layout.restaurant_list_footer, null));
//		stickyList.setDrawingListUnderStickyHeader(true);
//		stickyList.setAreHeadersSticky(true);
//		stickyList.setAdapter(mAdapter);
//
//		// stickyList.setStickyHeaderTopOffset(-20);

	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		list.add(mAdapter.getItem(position) + "");
	}

	@Override
	public void onHeaderClick(StickyListHeadersListView l, View header,
			int itemPosition, long headerId, boolean currentlySticky) {
		Toast.makeText(this,
				"Header " + headerId + " currentlySticky ? " + currentlySticky,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onStickyHeaderOffsetChanged(StickyListHeadersListView l,
			View header, int offset) {
		if (fadeHeader
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onStickyHeaderChanged(StickyListHeadersListView l, View header,
			int itemPosition, long headerId) {
		header.setAlpha(1);
	}
	
	private void getData() {
		mlist = new ArrayList<Food>();
		StringRequest request = new StringRequest(Request.Method.POST,
				Config.IP + "food?method=find",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						// System.out.println(arg0 + name + "  gggg");

						FoodVo user = gson.fromJson(arg0, FoodVo.class);
						System.out.println(user.getData() + "  gggg");
						mlist = user.getData();
						if (mlist != null) {
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> hashMap = new HashMap<String, String>();
				// hashMap.put("content", name);
				hashMap.put("status", "0");
				return hashMap;
			}

		};
		BaseApplication.getRequestQueue().add(request);
		
	}

}