package mdev.master_j.simpleaudioplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String FRAGMENT_PLAYER_KEY = "mdev.master_j.simpleaudioplayer.FragmentPlayer";
	
	private static final String CONTROL_BUTTON_PLAY = "Play";
	private static final String CONTROL_BUTTON_PAUSE = "Pause";
	
	private static final String STATUS_TEXTVIEW_IDLE = "Idle";
	private static final String STATUS_TEXTVIEW_PLAYING = "Playing";
	private static final String STATUS_TEXTVIEW_PAUSED = "Paused";
	
	private MediaPlayer player;
	private Button controlButton;
	private TextView statusTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		controlButton = (Button) findViewById(R.id.controlButton);
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		
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
	
	private void updatePlaybackStatus(boolean idle){
		if(player.isPlaying()){
			controlButton.setText(CONTROL_BUTTON_PAUSE);
			statusTextView.setText(STATUS_TEXTVIEW_PLAYING);
		}else{
			controlButton.setText(CONTROL_BUTTON_PLAY);
			if(idle)
				statusTextView.setText(STATUS_TEXTVIEW_IDLE);
			else
				statusTextView.setText(STATUS_TEXTVIEW_PAUSED);
		}
	}
}
