package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	private ImageView playPauseButton;
	private TextView statusTextView;
	private ProgressBar playbackProgressBar;
	private SeekBar volumeSeekBar;
	
	private String playButtonContentDescription;
	private String pauseButtonContentDescription;
	
	private String statusTextViewIdle;
	private String statusTextViewPlaying;
	private String statusTextViewPaused;
	
	private static final int MAX_VOLUME = 100;
	private static final int PLAYBACK_PROGRESS_UPDATE_PERIOD_MS = 100;
	
	private OnCompletionListener onCompletionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			player.release();
			player = null;
			updatePlaybackStatus();
		}
	};
	
	private OnSeekBarChangeListener onVolumeSeekBarChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			updateVolume();
		}
	};
	
	private OnClickListener onControlButtonClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(player == null){
				player = MediaPlayer.create(getActivity(), R.raw.mozart);
				player.setOnCompletionListener(onCompletionListener);
				updateVolume();
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
	
	private Runnable playbackProgressUpdater = new Runnable() {
		@Override
		public void run() {
			try{
				while(true){
					int max = 0, pos = 0;
					if(player != null){
						max = player.getDuration();
						pos = player.getCurrentPosition();
					}
					playbackProgressBar.setMax(max);
					playbackProgressBar.setProgress(pos);
					Thread.sleep(PLAYBACK_PROGRESS_UPDATE_PERIOD_MS);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	};

	private void updateVolume(){
		if(player == null)
			return;
		float volume = 1.0f * volumeSeekBar.getProgress() / volumeSeekBar.getMax();
		player.setVolume(volume, volume);
	}
	
	
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
		playbackProgressBar = (ProgressBar) getActivity().findViewById(R.id.playbackProgressBar);
		volumeSeekBar = (SeekBar) getActivity().findViewById(R.id.volumeControlSeekBar);
		
		volumeSeekBar.setMax(MAX_VOLUME);
		volumeSeekBar.setProgress(MAX_VOLUME);
		volumeSeekBar.setOnSeekBarChangeListener(onVolumeSeekBarChangeListener);
		
		playPauseButton.setOnClickListener(onControlButtonClickListener);
		
		ImageView stopButton = (ImageView) getActivity().findViewById(R.id.stopButton);;
		stopButton.setOnClickListener(onStopButtonClickListener);
		
		new Thread(playbackProgressUpdater).start();
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
