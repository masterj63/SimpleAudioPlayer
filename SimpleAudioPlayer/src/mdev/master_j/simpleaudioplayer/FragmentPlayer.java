package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	private ImageView playPauseButton;
	private TextView statusTextView;
	
	private String playButtonContentDescription;
	private String pauseButtonContentDescription;
	
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
	
	private OnClickListener onStopButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(player == null)
				return;
			player.release();
			player = null;
			updatePlaybackStatus();
		}
	};
	
	private void updatePlaybackStatus(){
		if(player == null){
			playPauseButton.setImageResource(R.drawable.play);
			playPauseButton.setContentDescription(playButtonContentDescription);
			statusTextView.setText(statusTextViewIdle);
		}else if(player.isPlaying()){
			playPauseButton.setImageResource(R.drawable.pause);
			playPauseButton.setContentDescription(pauseButtonContentDescription);
			statusTextView.setText(statusTextViewPlaying);
		}else{
			playPauseButton.setImageResource(R.drawable.play);
			playPauseButton.setContentDescription(playButtonContentDescription);
			statusTextView.setText(statusTextViewPaused);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		statusTextViewIdle = getResources().getString(R.string.status_textview_idle);
		statusTextViewPlaying = getResources().getString(R.string.status_textview_playing);
		statusTextViewPaused = getResources().getString(R.string.status_textview_paused);
		
		playButtonContentDescription = getResources().getString(R.string.contentDescription_playButton);
		pauseButtonContentDescription = getResources().getString(R.string.contentDescription_pauseButton);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		playPauseButton = (ImageView) getActivity().findViewById(R.id.playPauseButton);
		statusTextView = (TextView) getActivity().findViewById(R.id.statusTextView);
		
		playPauseButton.setOnClickListener(onControlButtonClickListener);
		
		ImageView stopButton = (ImageView) getActivity().findViewById(R.id.stopButton);;
		stopButton.setOnClickListener(onStopButtonClickListener);
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
