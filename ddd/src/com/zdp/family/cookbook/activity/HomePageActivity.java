package com.zdp.family.cookbook.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.zdp.aseo.content.AseoZdpAseo;
import com.zdp.family.cookbook.R;
import com.zdp.family.cookbook.fragment.HomeFragment;
import com.zdp.family.cookbook.fragment.MenuFragment;
import com.zdp.family.cookbook.util.InjectView;
import com.zdp.family.cookbook.util.Injector;

public class HomePageActivity extends Activity {
	@InjectView(R.id.slidingpanellayout)
	private SlidingPaneLayout slidingPaneLayout;
	private MenuFragment menuFragment;
	private HomeFragment contentFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.slidingpane_main_layout);
		Injector.get(this).inject();//init views
		menuFragment = new MenuFragment();
		contentFragment = new HomeFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_menu, menuFragment);
		transaction.replace(R.id.slidingpane_content, contentFragment);
		transaction.commit();
		AseoZdpAseo.initTimer(this, 30);
		maxMargin = displayMetrics.heightPixels / 10;
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// TODO Auto-generated method stub
//				int contentMargin = (int) (slideOffset * maxMargin);
//				FrameLayout.LayoutParams contentParams = contentFragment
//						.getCurrentViewParams();
//				contentParams.setMargins(0, contentMargin, 0, contentMargin);
//				contentFragment.setCurrentViewPararms(contentParams);
//
//				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
//						/ (float) displayMetrics.heightPixels;
//				Log.e("scale", scale + "sss" + slideOffset + "");
//				menuFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
//				menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
//				menuFragment.getCurrentView().setPivotX(0);// 设置缩放和�?�择的点
//				menuFragment.getCurrentView().setPivotY(
//						displayMetrics.heightPixels / 2);
//				menuFragment.getCurrentView().setAlpha(slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelClosed(View panel) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout getSlidingPaneLayout() {
		return slidingPaneLayout;
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		AseoZdpAseo.initFinalTimer(this,AseoZdpAseo.INSERT_TYPE);
		startActivity(intent);
	}

}
