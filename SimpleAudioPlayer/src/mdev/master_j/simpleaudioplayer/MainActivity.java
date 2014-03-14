package mdev.master_j.simpleaudioplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String FRAGMENT_PLAYER_KEY = "mdev.master_j.simpleaudioplayer.FragmentPlayer";
	
	private Button controlButton;
	private TextView statusTextView;
	private FragmentPlayer fragmentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		controlButton = (Button) findViewById(R.id.controlButton);
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		
		FragmentManager fragmentManager = getFragmentManager();
		fragmentPlayer = (FragmentPlayer) fragmentManager.findFragmentByTag(FRAGMENT_PLAYER_KEY);
		if(fragmentPlayer == null){
			fragmentPlayer = new FragmentPlayer();
			fragmentPlayer.setRetainInstance(true);
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(fragmentPlayer, FRAGMENT_PLAYER_KEY);
			fragmentTransaction.commit();
		}
		fragmentPlayer.setControlButtonAndStatusTextView(controlButton, statusTextView);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
}
