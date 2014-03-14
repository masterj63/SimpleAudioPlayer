package mdev.master_j.simpleaudioplayer;

import java.io.IOException;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
	
	void onControlButtonClick(){
		if(player.isPlaying())
			player.pause();
		else
			player.start();
		idle = false;
		updatePlaybackStatus();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = MediaPlayer.create(getActivity(), R.raw.mozart);
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				idle = true;
				Toast.makeText(getActivity(), "empty", Toast.LENGTH_SHORT).show();
				updatePlaybackStatus();
			}
		});
		idle = true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updatePlaybackStatus();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		player.release();
	}
}
