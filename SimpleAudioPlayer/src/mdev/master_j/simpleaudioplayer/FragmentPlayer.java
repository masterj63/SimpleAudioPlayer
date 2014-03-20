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
	private ImageView controlButton;
	private TextView statusTextView;
	
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
	
	private void updatePlaybackStatus(){
		if(player == null){
			controlButton.setImageResource(R.drawable.play);
			statusTextView.setText(statusTextViewIdle);
		}else if(player.isPlaying()){
			controlButton.setImageResource(R.drawable.pause);
			statusTextView.setText(statusTextViewPlaying);
		}else{
			controlButton.setImageResource(R.drawable.play);
			statusTextView.setText(statusTextViewPaused);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		statusTextViewIdle = getResources().getString(R.string.status_textview_idle);
		statusTextViewPlaying = getResources().getString(R.string.status_textview_playing);
		statusTextViewPaused = getResources().getString(R.string.status_textview_paused);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		controlButton = (ImageView) getActivity().findViewById(R.id.controlButton);
		statusTextView = (TextView) getActivity().findViewById(R.id.statusTextView);
		
		controlButton.setOnClickListener(onControlButtonClickListener);
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
