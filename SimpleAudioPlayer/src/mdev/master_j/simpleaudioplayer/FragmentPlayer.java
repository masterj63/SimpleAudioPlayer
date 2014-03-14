package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	private boolean idle;
	private Button controlButton;
	private TextView statusTextView;
	
	private static final String CONTROL_BUTTON_PLAY = "Play";
	private static final String CONTROL_BUTTON_PAUSE = "Pause";
	
	private static final String STATUS_TEXTVIEW_IDLE = "Idle";
	private static final String STATUS_TEXTVIEW_PLAYING = "Playing";
	private static final String STATUS_TEXTVIEW_PAUSED = "Paused";
	
	void setControlButtonAndStatusTextView(Button controlButton, TextView statusTextView){
		this.controlButton = controlButton;
		this.statusTextView = statusTextView;
		updatePlaybackStatus();
	}
	
	private void updatePlaybackStatus(){
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = MediaPlayer.create(getActivity(), R.raw.mozart);
		idle = true;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		player.release();
	}
}
