package com.zdp.family.cookbook.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.google.gson.Gson;

import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.activity.AdminActivity;
import com.zdp.family.cookbook.activity.OrderDetailActivity;
import com.zdp.family.cookbook.entity.Order;
import com.zdp.family.cookbook.entity.OrderVo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;

@SuppressLint("NewApi")
public class OrderFragment extends Fragment implements OnClickListener {
	private View currentView;
	private LinearLayout openMenu;

	private ListView listview;

	List<Order> list;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				listview.setAdapter(new OrderAdapter(getActivity(), list));
				listview.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						Order vo = (Order) listview.getItemAtPosition(arg2);
						if (vo != null) {
							ping(vo.getId());
						}

						return false;
					}

					private void ping(final String id) {
						StringRequest request = new StringRequest(
								Request.Method.POST, Config.IP
										+ "cuser?method=ping",
								new Response.Listener<String>() {
									@Override
									public void onResponse(String arg0) {

										if ("ok".equals(arg0)) {
											Toast.makeText(getActivity(),
													"好评成功", 2).show();

										} else {
											Toast.makeText(getActivity(),
													"好评失败", 2).show();
										}

									}
								}, new Response.ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError arg0) {
										Toast.makeText(getActivity(), "好评失败", 2)
												.show();
									}
								}) {
							protected Map<String, String> getParams()
									throws AuthFailureError {
								Map<String, String> hashMap = new HashMap<String, String>();
								// 添加
								hashMap.put("id", id);
								return hashMap;
							}

						};
						BaseApplication.getRequestQueue().add(request);

					}
				});
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
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_order_layout,
				container, false);
		openMenu = (LinearLayout) currentView
				.findViewById(R.id.linear_above_toHome);
		listview = (ListView) currentView.findViewById(R.id.listview);
		loaddata();
		openMenu.setOnClickListener(this);
		return currentView;
	}

	private void loaddata() {
		StringRequest request = new StringRequest(Request.Method.POST,
				Config.IP + "cuser?method=myorder",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();

						OrderVo user = gson.fromJson(arg0, OrderVo.class);
						System.out.println(user);
						list = user.getData();
						if (list != null) {
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
				hashMap.put("username",
						getActivity().getSharedPreferences("user", 1)
								.getString("username", ""));
				return hashMap;
			}

		};
		BaseApplication.getRequestQueue().add(request);

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

}
