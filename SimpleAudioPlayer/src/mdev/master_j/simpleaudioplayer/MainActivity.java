package mdev.master_j.simpleaudioplayer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {
	private static final String FRAGMENT_PLAYER_KEY = "mdev.master_j.simpleaudioplayer.FragmentPlayer";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_PLAYER_KEY);
		if(fragment == null){
			fragment = new FragmentPlayer();
			fragment.setRetainInstance(true);
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(fragment, FRAGMENT_PLAYER_KEY);
			fragmentTransaction.commit();
		}
	}
}
