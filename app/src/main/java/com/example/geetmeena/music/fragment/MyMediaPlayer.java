package com.example.geetmeena.music.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.geetmeena.music.Model.CommonModel;
import com.example.geetmeena.music.MainActivity;
import com.example.geetmeena.music.Service.MediaPlayerService;

import java.io.IOException;

import static com.example.geetmeena.music.fragment.AudioFragment.audioList;
import static com.example.geetmeena.music.fragment.AudioFragment.mMediaPlayer;
import static com.example.geetmeena.music.fragment.AudioFragment.posis1;
import static com.example.geetmeena.music.fragment.AudioFragment.resumePosition;
import static com.example.geetmeena.music.fragment.AudioFragment.selected;

public class MyMediaPlayer implements MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener,MediaPlayer.OnCompletionListener{
    private int mState;
    private boolean mPlayOnFocusGain;
    private volatile MediaMetadataCompat mCurrentMedia;
    private  AudioManager mAudioManager;
      Context context;
   private CallBackToService callBackToService;
   private MediaPlayerService mediaPlayerService;
   private MainActivity mainActivity;
   private AudioFragment audioFragment;

    String string;



    public MyMediaPlayer(Context context, CallBackToService callBackToService ) {
        this.context=context;
        this.mAudioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.callBackToService=callBackToService;
        mainActivity=MainActivity.getInstance();
        mMediaPlayer = new MediaPlayer();
        mediaPlayerService=new MediaPlayerService();
        //
        audioFragment=new AudioFragment("aa");
    }
    public MyMediaPlayer() {
    }

    public boolean isPlaying() {

        return mPlayOnFocusGain || (mMediaPlayer != null && mMediaPlayer.isPlaying());
    }

    public MediaMetadataCompat getCurrentMedia() {
        return mCurrentMedia;
    }

    public String getCurrentMediaId() {
        return mCurrentMedia == null ? null : mCurrentMedia.getDescription().getMediaId();
    }

    public int getCurrentStreamPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    public void play(MediaMetadataCompat metadata) {
        String mediaId = metadata.getDescription().getMediaId();
        boolean mediaChanged = (mCurrentMedia == null || !getCurrentMediaId().equals(mediaId));

        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setWakeMode(
                    context.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                }
            });
        } else {
            if (mediaChanged) {
                mMediaPlayer.reset();
            }
        }

        if (mediaChanged) {
            mCurrentMedia = metadata;
            try {  //Uri.parse(MusicLibrary.getSongUri(mediaId))
                mMediaPlayer.setDataSource(context.getApplicationContext(), Uri.parse(CommonModel.getFilePath(mediaId)) );
                mMediaPlayer.prepare();
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }

        if (tryToGetAudioFocus()) {
            mPlayOnFocusGain = false;
           playMedia();
            mState = PlaybackStateCompat.STATE_PLAYING;
            updatePlaybackStates();
        } else {
            mPlayOnFocusGain = true;
        }
    }
     public void prepareMediaPlayer(MediaMetadataCompat metadataCompat){
         String mediaId = metadataCompat.getDescription().getMediaId();

         boolean mediaChanged = (mCurrentMedia == null || !getCurrentMediaId().equals(mediaId));
         mMediaPlayer = new MediaPlayer();
         mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
         mMediaPlayer.setWakeMode(
                 context.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
         try {  //Uri.parse(MusicLibrary.getSongUri(mediaId))
             mMediaPlayer.setDataSource(context.getApplicationContext(),Uri.parse(CommonModel.getFilePath(mediaId)) );
             mMediaPlayer.prepare();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
    public void playMedia() {
        // MediaPlayerService.MySessionCallback mediaPlayerService=new MediaPlayerService.MySessionCallback();
        // mediaPlayerService.onPlay();
        //  Intent intent = new Intent( getActivity(), MediaPlayerService.class );
        // intent.setAction( MediaPlayerService.ACTION_PLAY );
        // appContext.startService(intent);
        // mediaPlayerService.mSession.setPlaybackState(PlaybackStateCompat.fromPlaybackState(PlaybackStateCompat.STATE_PLAYING));
      //  Toast.makeText(context, "Hiii", Toast.LENGTH_SHORT).show();
        //Toast.makeText(mainActivity, ""+CommonModel.getMediaItems(), Toast.LENGTH_SHORT).show();
        mMediaPlayer.start();
        mMediaPlayer.isPlaying();
        mMediaPlayer.setOnCompletionListener(this);



    }

    public void stopMedia() {
        mState = PlaybackStateCompat.STATE_STOPPED;
        updatePlaybackStates();
        // Give up Audio focus
        mAudioManager.abandonAudioFocus(this);
        // Relax all resources
        releaseMediaPlayer();
    }

    public  void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            //Toast.makeText(context, "  pause2  = "+stateOfplay, Toast.LENGTH_SHORT).show();
             resumePosition = mMediaPlayer.getCurrentPosition();
            mAudioManager.abandonAudioFocus(this);
        }
        mState = PlaybackStateCompat.STATE_PAUSED;
        updatePlaybackStates();
    }

    public   void resumeMedia(int resumePosition) {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(resumePosition);
            mMediaPlayer.start();
        }


    }



    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    public void movePerv(){
        int g;
        int ss=selected.size();
        int s= audioList.size();


        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            g= posis1 - 1;
            posis1 = g;
            if (posis1 >=0) {
                audioFragment.palay1(g);
            } else if (posis1 <0) {
                posis1 = s-1;
                audioFragment.palay1(posis1);


            }

        } else if (!mMediaPlayer.isPlaying()) {
            g = posis1 + 1;
            posis1 = g;

            if (g >=0) {
                audioFragment.notPlay(g);
            } else if (g <0) {
                posis1 = s-1;
                audioFragment.notPlay(posis1);


            }
        }

    }
    public void moveNext(){
        int b;
        int ss=selected.size();
        int s=audioList.size();


        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            b = posis1 + 1;
            posis1 = b;
            if (posis1 < s) {
                audioFragment.palay1(b);
            } else if (posis1 >= s) {
                posis1 = 0;
                audioFragment.palay1(posis1);


            }

        }
        else if (!mMediaPlayer.isPlaying()) {
            b = posis1 + 1;
            posis1 = b;

            if (b < s) {
                audioFragment.notPlay(b);
            } else if (b >= s) {
                posis1 = 0;
                audioFragment.notPlay(posis1);


            }
        }

    }
    private boolean tryToGetAudioFocus() {
        int result =
                mAudioManager.requestAudioFocus(
                        this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        boolean gotFullFocus = false;
        boolean canDuck = false;
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            gotFullFocus = true;

        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS
                || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            // We have lost focus. If we can duck (low playback volume), we can keep playing.
            // Otherwise, we need to pause the playback.
            canDuck = focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
        }

        if (gotFullFocus || canDuck) {
            if (mMediaPlayer != null) {
                if (mPlayOnFocusGain) {
                    mPlayOnFocusGain = false;
                    mMediaPlayer.start();
                    mState = PlaybackStateCompat.STATE_PLAYING;
                    updatePlaybackStates();
                }
                float volume = canDuck ? 0.2f : 1.0f;
                mMediaPlayer.setVolume(volume, volume);
            }
        } else if (mState == PlaybackStateCompat.STATE_PLAYING) {
            mMediaPlayer.pause();
            mState = PlaybackStateCompat.STATE_PAUSED;
            updatePlaybackStates();
        }
    }
    @PlaybackStateCompat.Actions
    private long getAvailableActions() {
        long actions =
                PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                        | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                        | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
        if (isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        }
        return actions;
    }

    private void updatePlaybackStates() {
        if (callBackToService == null) {
            return;
        }
        PlaybackStateCompat.Builder stateBuilder =
                new PlaybackStateCompat.Builder().setActions(getAvailableActions());

        stateBuilder.setState(
                mState, getCurrentStreamPosition(), 1.0f, SystemClock.elapsedRealtime());
        callBackToService.OnPlaybackStateChanged(stateBuilder.build());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
           mState=PlaybackStateCompat.STATE_SKIPPING_TO_NEXT;
           updatePlaybackStates();
           callBackToService.OnPlaybackToNext();

       // Toast.makeText(context, "Media is Complited", Toast.LENGTH_SHORT).show();
    }

    public interface CallBackToService {
        public void OnPlaybackStateChanged(PlaybackStateCompat stateCompat);
        public    void OnPlaybackToNext();
    }
}
