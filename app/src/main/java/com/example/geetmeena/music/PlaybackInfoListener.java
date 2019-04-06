package com.example.geetmeena.music;

import android.support.v4.media.session.PlaybackStateCompat;

abstract class PlaybackInfoListener {
    public abstract void onPlaybackStateChange(PlaybackStateCompat state);
    public void onPlaybackCompleted() {
    }
}
