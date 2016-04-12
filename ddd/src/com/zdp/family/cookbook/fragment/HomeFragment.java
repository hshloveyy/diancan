package com.zdp.family.cookbook.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
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
import com.zdp.family.cookbook.activity.DianPingWebActivity;
import com.zdp.family.cookbook.activity.RestaurantDetailActivity;
import com.zdp.family.cookbook.adapter.FoodListAdapter;
import com.zdp.family.cookbook.adapter.HomePageRestaurantAdapter;
import com.zdp.family.cookbook.adapter.RestaurantDetailAdapter;
import com.zdp.family.cookbook.entity.Food;
import com.zdp.family.cookbook.entity.FoodVo;
import com.zdp.family.cookbook.util.InjectView;
import com.zdp.family.cookbook.util.RefreshableListView;
import com.zdp.family.cookbook.util.RefreshableListView.OnRefreshListener;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnClickListener {
	private View currentView;
	private LinearLayout openMenu;
	private RefreshableListView mListView;
//	private HomePageRestaurantAdapter adapter;
	private FoodListAdapter adapter;
	private List<Food> mlist;
	private int total = 21;
	private int step = 7;
	private int add = 7;
	private View listHeaderView;
	private ImageView head_pic;
//	@InjectView(R.id.food_list_shipping_fee)
//	private TextView food_list_shipping_fee;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
//				adapter = new HomePageRestaurantAdapter(getActivity(), mlist);
				adapter = new FoodListAdapter(getActivity(), mlist, (TextView)currentView.findViewById(R.id.food_list_shipping_fee));
				mListView.setAdapter(adapter);
				mListView.addHeaderView(listHeaderView);

				break;

			default:
				break;
			}
		};
	};

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.slidingpane_home_layout,
				container, false);
		mListView = (RefreshableListView) currentView
				.findViewById(R.id.mineList);
		openMenu = (LinearLayout) currentView
				.findViewById(R.id.linear_above_toHome);
		listHeaderView = getActivity().getLayoutInflater().inflate(
				R.layout.home_head_view, null);
		head_pic = (ImageView) listHeaderView.findViewById(R.id.iv_home_head);
		openMenu.setOnClickListener(this);
		getDate();
		setListener();
		return currentView;
	}

	public void setListener() {

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				new NewDataTask().execute();
			}
		});

//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
//				Intent intent = new Intent(getActivity(),
//						RestaurantDetailActivity.class);
//				Toast.makeText(getActivity(), position + "", 2).show();
//				Food food = ((Food) mListView.getItemAtPosition(position));
//				if (food == null) {
//					food = ((Food) mListView.getItemAtPosition(position + 1));
//				}
//				intent.putExtra("name", food.getName());
//
//				startActivity(intent);
//			}
//		});
		head_pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						DianPingWebActivity.class);
				startActivity(intent);
			}
		});
	}

	private class NewDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			int current = mListView.getAdapter().getCount();
			if (current < total) {
				add += step;
				mListView.removeHeaderView(listHeaderView);
				getDate();
			}

			mListView.completeRefreshing();

			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.linear_above_toHome):
			openMenu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) getActivity()
							.findViewById(R.id.slidingpanellayout);
					if (slidingPaneLayout.isOpen()) {
						slidingPaneLayout.closePane();
					} else {
						slidingPaneLayout.openPane();
					}
				}
			});

			break;
		}
	}

	private void getDate() {

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
