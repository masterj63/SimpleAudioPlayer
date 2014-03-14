package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	private boolean idle;
	
	private static final String CONTROL_BUTTON_PLAY = "Play";
	private static final String CONTROL_BUTTON_PAUSE = "Pause";
	
	private static final String STATUS_TEXTVIEW_IDLE = "Idle";
	private static final String STATUS_TEXTVIEW_PLAYING = "Playing";
	private static final String STATUS_TEXTVIEW_PAUSED = "Paused";
	
	String getControlButtonText(){
		if(player.isPlaying())
			return CONTROL_BUTTON_PAUSE;
		else
			return CONTROL_BUTTON_PLAY;
	}
	
	String getStatusTextViewText(){
		if(idle)
			return STATUS_TEXTVIEW_IDLE;
		if(player.isPlaying())
			return STATUS_TEXTVIEW_PLAYING;
		return STATUS_TEXTVIEW_PAUSED;
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
