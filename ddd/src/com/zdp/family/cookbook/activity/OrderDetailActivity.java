package com.zdp.family.cookbook.activity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.zdp.family.cookbook.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {

	TextView id;
	TextView content;
	TextView username;
	TextView phone;
	TextView flag;
	TextView address;
	TextView price;
	Intent intent;

	Button jiedan;

	Button peisong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		id = (TextView) findViewById(R.id.id);
		jiedan = (Button) findViewById(R.id.jiedan);
		peisong = (Button) findViewById(R.id.peisong);
		intent = getIntent();
		id.setText(intent.getStringExtra("id"));
		content = (TextView) findViewById(R.id.foodname);
		content.setText(intent.getStringExtra("content"));
		username = (TextView) findViewById(R.id.username);
		username.setText(intent.getStringExtra("name"));
		phone = (TextView) findViewById(R.id.phone);
		phone.setText(intent.getStringExtra("phone"));
		flag = (TextView) findViewById(R.id.status);
		flag.setText(intent.getStringExtra("flag"));
		address = (TextView) findViewById(R.id.address);
		address.setText(intent.getStringExtra("address"));
		price = (TextView) findViewById(R.id.pricex);
	    price.setText(intent.getStringExtra("price"));

		if ("已提交".equals(intent.getStringExtra("flag"))) {
			peisong.setVisibility(View.GONE);
		} else {
			jiedan.setVisibility(View.GONE);
		}

		jiedan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changOrder("jiedan", intent.getStringExtra("id"));
			}

		});

		peisong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changOrder("peisong", intent.getStringExtra("id"));
			}

		});

	}

	private void changOrder(final String string, final String stringExtra) {
		StringRequest request = new StringRequest(Request.Method.POST,
				Config.IP + "cuser?method=jie",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {

						if ("ok".equals(arg0)) {
							Toast.makeText(getApplicationContext(), "操作成功", 2)
									.show();
							Intent intent = new Intent(
									OrderDetailActivity.this,
									AdminActivity.class);
							startActivity(intent);

						} else {
							Toast.makeText(getApplicationContext(), "操作失败", 2)
									.show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "操作失败", 2)
								.show();
					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> hashMap = new HashMap<String, String>();
				// 添加
				hashMap.put("id", stringExtra);
				hashMap.put("flag", string);
				return hashMap;
			}

		};
		BaseApplication.getRequestQueue().add(request);

	}
}
