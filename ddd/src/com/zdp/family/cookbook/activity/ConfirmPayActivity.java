package com.zdp.family.cookbook.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.zdp.aseo.content.AseoZdpAseo;
import com.zdp.family.cookbook.R;

public class ConfirmPayActivity extends Activity {
	Button returnOrder;

	List<Map<String, Object>> data = null;
	TextView stationCode, startStaion, startTime, arriveStation, arriveTime,
			name, costTime, price, date, address, username;
	Button payBtn, cancelBtn;
	String names = "";
	ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_pay);

		findViews();
		setText();
		setListener();

	}

	private void setListener() {
		// 取消按钮
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = getIntent();
				intent.setClass(ConfirmPayActivity.this, HomePageActivity.class);
				startActivity(intent);
			}
		});

		// 返回
		returnOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 确认提交
		payBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
				SharedPreferences spf = getSharedPreferences("checkLogin",
						MODE_PRIVATE);
				final String username = spf.getString("username", "");
				final String phone = spf.getString("phone", "");
				final String address = spf.getString("address", "");
				
				if(TextUtils.isEmpty(username)){
					Toast.makeText(ConfirmPayActivity.this, "请登录之后再点餐", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent (ConfirmPayActivity.this, HomePageActivity.class);	
					startActivity(intent);
					finish();
				}
				//
				// final String code = intent.getStringExtra("code");
				// // String username = intent.getStringExtra("username");
				// final String date1 = intent.getStringExtra("date1");
				// final String startstation = intent
				// .getStringExtra("startstation");
				// final String sTime = intent.getStringExtra("startTime");
				// final String endstation =
				// intent.getStringExtra("endstation");
				// final String usetime = intent.getStringExtra("usetime");
				// final String endtime = intent.getStringExtra("endtime");
				// final String prices = intent.getStringExtra("price");

				StringRequest request = new StringRequest(Request.Method.POST,
						Config.IP + "cuser?method=order",
						new Response.Listener<String>() {
							@Override
							public void onResponse(String arg0) {
								if ("ok".equals(arg0)) {
									Toast.makeText(getApplicationContext(),
											"提交成功,我们将尽快通知后厨制作！", 2).show();
									finish();
								} else {
									Toast.makeText(getApplicationContext(),
											"提交失败", 2).show();
								}

							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								Toast.makeText(getApplicationContext(), "提交失败",
										2).show();
							}
						}) {
					protected Map<String, String> getParams()
							throws AuthFailureError {
						Map<String, String> hashMap = new HashMap<String, String>();
						// 添加
						hashMap.put("username", username);
						hashMap.put("address", address);
						hashMap.put("phone", phone);
						hashMap.put("content", names);
						hashMap.put("price", getIntent().getIntExtra("foodPrice", 0) + "");
						return hashMap;
					}

				};
				BaseApplication.getRequestQueue().add(request);

				// intent.setClass(ConfirmPayActivity.this,
				// FinishedPayActivity.class);
				// startActivity(intent);
			}
		});
	}

	private void setText() {
		Intent intent = getIntent();
		names = intent.getStringExtra("foodList");

//		for (int i = 0; i < list.size(); i++) {
//
//			names += list.get(i) + "; ";
//		}
		SharedPreferences spf = getSharedPreferences("checkLogin", MODE_PRIVATE);
		stationCode.setText(names + "");
		address.setText(spf.getString("address", ""));
		username.setText(spf.getString("username", ""));
		costTime.setText(spf.getString("phone", ""));
		
		// String username = intent.getStringExtra("username");
		// String date1 = intent.getStringExtra("date1");
		// String startstation = intent.getStringExtra("startstation");
		// String sTime = intent.getStringExtra("startTime");
		// String endstation = intent.getStringExtra("endstation");
		// String usetime = intent.getStringExtra("usetime");
		// String endtime = intent.getStringExtra("endtime");
		// String prices = intent.getStringExtra("price");
		// stationCode.setText(code);
		// startStaion.setText(startstation);
		// startTime.setText(sTime);
		// arriveStation.setText(endstation);
		// arriveTime.setText(endtime);
		// costTime.setText(usetime);
		// price.setText(prices);
		// name.setText(username);
		// date.setText(date1);

	}

	private void findViews() {
		payBtn = (Button) findViewById(R.id.payBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		stationCode = (TextView) findViewById(R.id.foodname);

		address = (TextView) findViewById(R.id.address);

		username = (TextView) findViewById(R.id.username);
		costTime = (TextView) findViewById(R.id.phone);
	
		returnOrder = (Button) findViewById(R.id.returnConfirmOrder);
	}
}
