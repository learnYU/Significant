package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CustomMediaController;
import io.vov.vitamio.widget.VideoView;


public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    private String videoUrl ="",title = "";
    @Bind(R.id.videoview) VideoView videoView;
    @Bind(R.id.probar) ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        loadData();
        initView();
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void loadData() {

        videoUrl = getIntent().getStringExtra("videoUrl");
        title = getIntent().getStringExtra("title");
        Logger.d(videoUrl);

    }

    @Override
    protected void initView() {
        CustomMediaController mediaController = new CustomMediaController(context,videoView,this);

        mediaController.setVideoName(title);
        videoView.setVideoPath(videoUrl);
        videoView.setMediaController(mediaController);
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        mediaController.show(5000);
        videoView.requestFocus();
        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });

        Logger.d(videoView.isPlaying());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Logger.d(extra+"----"+what);
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.start();
                progressBar.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                Logger.d(extra);
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        Logger.d(percent);
    }


}
