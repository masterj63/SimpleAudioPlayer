package mdev.master_j.simpleaudioplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends Activity {
	private static final String FRAGMENT_PLAYER_KEY = "mdev.master_j.simpleaudioplayer.FragmentPlayer";
	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentPlayer fragmentPlayer = (FragmentPlayer) fragmentManager.findFragmentByTag(FRAGMENT_PLAYER_KEY);
		if(fragmentPlayer == null){
			fragmentPlayer = new FragmentPlayer();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(fragmentPlayer, FRAGMENT_PLAYER_KEY);
			fragmentTransaction.commit();
		}
		player = fragmentPlayer.getMediaPlayer();
	}
}
