package mdev.master_j.simpleaudioplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

public class FragmentPlayer extends Fragment {
	private MediaPlayer player;
	
	MediaPlayer getMediaPlayer(){
		return player;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = MediaPlayer.create(getActivity(), R.raw.mozart);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		player.release();
	}
}
