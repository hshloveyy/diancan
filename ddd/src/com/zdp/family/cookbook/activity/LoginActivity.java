package com.zdp.family.cookbook.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
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
import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.fragment.OrderFragment;
import com.zdp.family.cookbook.util.InjectView;
import com.zdp.family.cookbook.util.Injector;

public class LoginActivity extends Activity {
	@InjectView(R.id.iv_head_left)
	private ImageView head_left;
	@InjectView(R.id.linear_above_toHome)
	private LinearLayout above_toHome;
	@InjectView(R.id.tv_common_above_head)
	private TextView above_tittle;
	@InjectView(R.id.login_name)
	private EditText username;
	@InjectView(R.id.login_password)
	private EditText password;
	@InjectView(R.id.login_submit)
	private TextView login;

	@InjectView(R.id.reg)
	private TextView reg;

//	@InjectView(R.id.flag)
//	private CheckBox flag;

	private SharedPreferences sp;

	String ff = "user";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
//		flag=(CheckBox) findViewById(R.id.flag);
		sp = this.getSharedPreferences("user", 1);
//		flag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//
//				ff = "admin";
//
//			}
//		});
		Injector.get(this).inject();// init views
		initView();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		above_toHome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});

	}

	private void initView() {
		above_tittle.setText("登录");
		head_left.setImageResource(R.drawable.abc_ic_ab_back_holo_dark);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (check(LoginActivity.this)){//"user".equals(flag)) {
					StringRequest request = new StringRequest(
							Request.Method.POST, Config.IP
									+ "cuser?method=clogin",
							new Response.Listener<String>() {
								@Override
								public void onResponse(String arg0) {

									if ("user".equals(arg0)) {
										Toast.makeText(getApplicationContext(),
												"登录成功.", 2).show();
										saveUser(username.getText().toString(),
												username.getText().toString());
										Intent intent1 = new Intent(
												LoginActivity.this,
												HomePageActivity.class);
										startActivity(intent1);
									} else if ("admin".equals(arg0)) {
										Toast.makeText(getApplicationContext(),
												"登录成功!", 2).show();
										// saveUser(name2, pwd2);
										// Intent intent2 = new Intent(
										// MainActivity.this,
										// TeacherActivity.class);
										// startActivity(intent2);
										Intent intent1 = new Intent(
												LoginActivity.this,
												AdminActivity.class);
										startActivity(intent1);
									} else {
										Toast.makeText(getApplicationContext(),
												"用户名或密码错误", 2).show();
									}

								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError arg0) {
									Toast.makeText(getApplicationContext(),
											"登录失败", 2).show();
								}
							}) {
						protected Map<String, String> getParams()
								throws AuthFailureError {
							Map<String, String> hashMap = new HashMap<String, String>();
							// 添加
							hashMap.put("username", username.getText()
									.toString());
							hashMap.put("password", password.getText()
									.toString());
							return hashMap;
						}

					};
					BaseApplication.getRequestQueue().add(request);
				} else {
					StringRequest request = new StringRequest(
							Request.Method.POST, Config.IP
									+ "cuser?method=clogin",
							new Response.Listener<String>() {
								@Override
								public void onResponse(String arg0) {

									if ("user".equals(arg0)) {
										Toast.makeText(getApplicationContext(),
												"登录成功.", 2).show();
										saveUser(username.getText().toString(),
												username.getText().toString());
										Intent intent1 = new Intent(
												LoginActivity.this,
												HomePageActivity.class);
										startActivity(intent1);
									} else if ("admin".equals(arg0)) {
										Toast.makeText(getApplicationContext(),
												"登录成功!", 2).show();
										Intent intent1 = new Intent(
												LoginActivity.this,
												AdminActivity.class);
										startActivity(intent1);
									} else {
										Toast.makeText(getApplicationContext(),
												"用户名或密码错误", 2).show();
									}

								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError arg0) {
									Toast.makeText(getApplicationContext(),
											"登录失败", 2).show();
								}
							}) {
						protected Map<String, String> getParams()
								throws AuthFailureError {
							Map<String, String> hashMap = new HashMap<String, String>();
							// 添加
							hashMap.put("username", username.getText()
									.toString());
							hashMap.put("password", password.getText()
									.toString());
							return hashMap;
						}

					};
					BaseApplication.getRequestQueue().add(request);
				}
			}


		});

		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);

			}
		});

	}

	private boolean check(Context context) {
		String usernameStr = username.getText().toString();
		String passwordStr = password.getText().toString();
		
		if(TextUtils.isEmpty(usernameStr)){
			Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(passwordStr)){
			Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private void saveUser(String username, String pwd) {
		Editor ed = sp.edit();
		ed.putString("username", username);
		ed.putString("password", pwd);

		ed.commit();
	}
}
