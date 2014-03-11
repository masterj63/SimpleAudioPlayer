package mdev.master_j.simpleaudioplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	static final int SPLASH_RUNNING_TIME = 2000;
	private Handler handler;
	private Runnable homeActivityInvoker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		handler = new Handler();
		homeActivityInvoker = new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}
		};
		handler.postDelayed(homeActivityInvoker, SPLASH_RUNNING_TIME);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(homeActivityInvoker);
	}
}