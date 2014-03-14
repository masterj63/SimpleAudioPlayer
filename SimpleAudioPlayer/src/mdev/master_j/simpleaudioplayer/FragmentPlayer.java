package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
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
		if(player == null){
			controlButton.setText(CONTROL_BUTTON_PLAY);
			statusTextView.setText(STATUS_TEXTVIEW_IDLE);
		}else if(player.isPlaying()){
			controlButton.setText(CONTROL_BUTTON_PAUSE);
			statusTextView.setText(STATUS_TEXTVIEW_PLAYING);
		}else{
			controlButton.setText(CONTROL_BUTTON_PLAY);
			statusTextView.setText(STATUS_TEXTVIEW_PAUSED);
		}
	}
	
	void onControlButtonClick(){
		if(player == null)
			player = MediaPlayer.create(getActivity(), R.raw.mozart);
		
		if(player.isPlaying())
			player.pause();
		else
			player.start();
		
		updatePlaybackStatus();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = MediaPlayer.create(getActivity(), R.raw.mozart);
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				player = null;
				updatePlaybackStatus();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updatePlaybackStatus();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(player != null)
			player.release();
	}
}
