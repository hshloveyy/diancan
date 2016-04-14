package com.zdp.family.cookbook.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.google.gson.Gson;
import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.entity.Order;
import com.zdp.family.cookbook.entity.OrderVo;

public class AdminActivity extends Activity {

	ListView listview;
	private List<Order> mlist;
	private AllOrderAdapter adapter;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter = new AllOrderAdapter(AdminActivity.this, mlist);
				listview.setAdapter(adapter);
				// mListView.addHeaderView(listHeaderView);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						
						Intent intent=new Intent(AdminActivity.this,OrderDetailActivity.class);
						Order order=(Order) listview.getItemAtPosition(arg2);
						if(order!=null)
						{
							intent.putExtra("id", order.getId());
							intent.putExtra("content", order.getContent());
							intent.putExtra("address", order.getAddress());
							intent.putExtra("phone", order.getPhone());
							intent.putExtra("name", order.getName());
							intent.putExtra("flag", order.getFlag());
							intent.putExtra("price", order.getPrice());
							
						}
						
						startActivity(intent);

					}
				});

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		listview = (ListView) findViewById(R.id.adminlist);
		loaddata();
	}

	private void loaddata() {

		mlist = new ArrayList<Order>();
		StringRequest request = new StringRequest(Request.Method.POST,
				Config.IP + "cuser?method=allorder",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						// System.out.println(arg0 + name + "  gggg");

						OrderVo user = gson.fromJson(arg0, OrderVo.class);
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
				hashMap.put(
						"username",
						BaseApplication.getcontext()
								.getSharedPreferences("user", 1)
								.getString("username", ""));
				return hashMap;
			}

		};
		BaseApplication.getRequestQueue().add(request);

	}

}
