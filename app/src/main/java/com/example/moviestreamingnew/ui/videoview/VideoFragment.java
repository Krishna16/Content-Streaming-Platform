package com.example.moviestreamingnew.ui.videoview;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.moviestreamingnew.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class VideoFragment extends Fragment {

    private VideoViewModel mViewModel;

    private View root;

    //private VideoView episode_view;

    private PlayerView exoplayer_video_view;
    private Player player;

    private String videoUrl;

    private BottomNavigationView bottomNavigationView;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public VideoFragment(){

    }

    public VideoFragment(String videoUrl){
        this.videoUrl = videoUrl;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        root = inflater.inflate(R.layout.video_fragment, container, false);

        //episode_view = root.findViewById(R.id.episode_video_view);
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        exoplayer_video_view = root.findViewById(R.id.exoplayer_video_view);

        //MediaController mediaController = new MediaController(root.getContext());
        //mediaController.setAnchorView(episode_view);

        //Uri video_uri = Uri.parse(videoUrl);

        /*episode_view.setMediaController(mediaController);
        episode_view.setVideoURI(video_uri);
        episode_view.requestFocus();
        episode_view.start();*/

        bottomNavigationView.setVisibility(View.GONE);

        TabLayout showMovieLayout = getActivity().findViewById(R.id.show_movie_tab_layout);
        showMovieLayout.setVisibility(View.GONE);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        player = new SimpleExoPlayer.Builder(root.getContext()).build();
        exoplayer_video_view.setPlayer(player);
        MediaItem video = MediaItem.fromUri(videoUrl);
        player.setMediaItem(video);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        exoplayer_video_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }
}