package com.zdp.family.cookbook.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.zdp.family.cookbook.R;

public class CommentActivity extends Activity implements OnClickListener{
	private String username;
	private String password;
	private String phone;
	private String address;

	private EditText name;


	private Button reg;

	private String flag;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addbei);
		name = (EditText) findViewById(R.id.et_username);
		intent=getIntent();
		
		reg = (Button) findViewById(R.id.add_btn);
		reg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			username = name.getText().toString();
			
			reg(username, password, phone, address, flag);
			break;

		default:
			break;
		}

	}

	private void reg(String username2, String password2, String phone2,
			String address2, String flag2) {
		StringRequest request = new StringRequest(Request.Method.POST,
				Config.IP + "cuser?method=comment",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						if ("ok".equals(arg0)) {
							Toast.makeText(getApplicationContext(), "评论成功", 2)
									.show();
							finish();
						} else {
							Toast.makeText(getApplicationContext(), "评论失败", 2)
									.show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "评论失败", 2)
								.show();
					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> hashMap = new HashMap<String, String>();
				// 添加
				hashMap.put("name", username);
				hashMap.put("id", "0");
				hashMap.put("foodname", Config.RESTAURANT_NAME);
				
				hashMap.put(
						"username",
						BaseApplication.getcontext()
								.getSharedPreferences("user", 0)
								.getString("username", ""));
				return hashMap;
			}

		};
		BaseApplication.getRequestQueue().add(request);

	}

}
