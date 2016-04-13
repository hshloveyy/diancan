package com.zdp.family.cookbook.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BaseApplication;
import com.example.Config;
import com.zdp.family.cookbook.R;

public class RegisterActivity extends Activity {

	private EditText username;

	private EditText password;

	private EditText address;

	private EditText phone;

	private EditText realname;

	private TextView reg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		phone = (EditText) findViewById(R.id.phone);
		address = (EditText) findViewById(R.id.address);
		realname = (EditText) findViewById(R.id.realname);

		reg = (TextView) findViewById(R.id.reg_submit);
		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(check(RegisterActivity.this)){
					StringRequest request = new StringRequest(Request.Method.POST,
							Config.IP + "cuser?method=reg",
							new Response.Listener<String>() {
								@Override
								public void onResponse(String arg0) {
	
									if ("ok".equals(arg0)) {
										Toast.makeText(getApplicationContext(),
												"注册成功！请登录", 2).show();
										 saveUser(username.getText().toString(),
										 address.getText().toString(),phone.getText().toString());
										Intent intent1 = new Intent(
												RegisterActivity.this,
												LoginActivity.class);
										startActivity(intent1);
									} else {
										Toast.makeText(getApplicationContext(),
												"注册失败,请稍后再试", 2).show();
										// saveUser(name2, pwd2);
										// Intent intent2 = new Intent(
										// MainActivity.this,
										// TeacherActivity.class);
										// startActivity(intent2);
									}
								}
	
								private void saveUser(String string, String string2, String string3) {
									 SharedPreferences spf = getSharedPreferences("checkLogin",
												MODE_PRIVATE);
									 Editor e=spf.edit();
									 e.putString("username", string);
									 e.putString("address", string2);
									 e.putString("phone", string3);
									 e.commit();
									
								}
							}, new Response.ErrorListener() {
	
								@Override
								public void onErrorResponse(VolleyError arg0) {
									Toast.makeText(getApplicationContext(), "注册失败",
											2).show();
								}
							}) {
						protected Map<String, String> getParams()
								throws AuthFailureError {
							Map<String, String> hashMap = new HashMap<String, String>();
							// 添加
							hashMap.put("username", username.getText().toString());
							hashMap.put("password", password.getText().toString());
							hashMap.put("address", address.getText().toString());
							hashMap.put("phone", phone.getText().toString());
							hashMap.put("realname", realname.getText().toString());
							hashMap.put("role", "user");
							return hashMap;
						}
	
					};
					BaseApplication.getRequestQueue().add(request);
				}
			}

		});
	}
	
	private boolean check(Context context){
		boolean flag = true;
		String usernameStr = username.getText().toString();
		String passwordStr = password.getText().toString();
		String addressStr = address.getText().toString();
		String phoneStr = phone.getText().toString();
		String realnameStr = realname.getText().toString();
		
		if(TextUtils.isEmpty(usernameStr)){
			Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(passwordStr)){
			Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			if(passwordStr.length() < 6 || passwordStr.length() > 11){
				Toast.makeText(context, "密码长度在6-12个字符串", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		if(TextUtils.isEmpty(addressStr)){
			Toast.makeText(context, "请输入桌号", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(phoneStr)){
			Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			if(phoneStr.length() != 11){
				Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		if(TextUtils.isEmpty(realnameStr)){
			Toast.makeText(context, "请输入真实姓名", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return flag;
	}

}
