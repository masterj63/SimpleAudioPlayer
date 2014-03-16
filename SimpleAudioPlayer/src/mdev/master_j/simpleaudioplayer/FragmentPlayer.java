package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	private Button controlButton;
	private TextView statusTextView;
	
	private String controlButtonPlay;
	private String controlButtonPause;
	
	private String statusTextViewIdle;
	private String statusTextViewPlaying;
	private String statusTextViewPaused;
	
	private OnCompletionListener onCompletionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			player.release();
			player = null;
			updatePlaybackStatus();
		}
	};
	
	private OnClickListener onControlButtonClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(player == null){
				player = MediaPlayer.create(getActivity(), R.raw.mozart);
				player.setOnCompletionListener(onCompletionListener);
			}
			
			if(player.isPlaying())
				player.pause();
			else
				player.start();
			
			updatePlaybackStatus();
		}
	};
	
	void setControlButtonAndStatusTextView(Button controlButton, TextView statusTextView){
		this.controlButton = controlButton;
		this.statusTextView = statusTextView;
		
		controlButton.setOnClickListener(onControlButtonClickListener);
	}
	
	private void updatePlaybackStatus(){
		if(player == null){
			controlButton.setText(controlButtonPlay);
			statusTextView.setText(statusTextViewIdle);
		}else if(player.isPlaying()){
			controlButton.setText(controlButtonPause);
			statusTextView.setText(statusTextViewPlaying);
		}else{
			controlButton.setText(controlButtonPlay);
			statusTextView.setText(statusTextViewPaused);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		controlButtonPlay = getResources().getString(R.string.control_button_play);
		controlButtonPause = getResources().getString(R.string.control_button_pause);
		
		statusTextViewIdle = getResources().getString(R.string.status_textview_idle);
		statusTextViewPlaying = getResources().getString(R.string.status_textview_playing);
		statusTextViewPaused = getResources().getString(R.string.status_textview_paused);
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
