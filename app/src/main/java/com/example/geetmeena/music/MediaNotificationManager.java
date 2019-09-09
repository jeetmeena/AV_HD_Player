package com.example.geetmeena.music;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.geetmeena.music.Model.CommonModel;
import com.example.geetmeena.music.Service.MediaPlayerService;

public class MediaNotificationManager extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 412;
    private static final int REQUEST_CODE = 100;

    private static final String ACTION_PAUSE = "com.example.geetmeena.music.pause";
    private static final String ACTION_PLAY = "com.example.geetmeena.music.play";
    private static final String ACTION_NEXT = "com.example.geetmeena.music.next";
    private static final String ACTION_PREV = "com.example.geetmeena.music.prev";

    private final MediaPlayerService mService;

    private final NotificationManager mNotificationManager;

    private final NotificationCompat.Action mPlayAction;
    private final NotificationCompat.Action mPauseAction;
    private final NotificationCompat.Action mNextAction;
    private final NotificationCompat.Action mPrevAction;

    private boolean mStarted;

    public MediaNotificationManager(MediaPlayerService service) {
        mService = service;

        String pkg = mService.getPackageName();
        PendingIntent playIntent =
                PendingIntent.getBroadcast(
                        mService,
                        REQUEST_CODE,
                        new Intent(ACTION_PLAY).setPackage(pkg),
                        PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pauseIntent =
                PendingIntent.getBroadcast(
                        mService,
                        REQUEST_CODE,
                        new Intent(ACTION_PAUSE).setPackage(pkg),
                        PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent nextIntent =
                PendingIntent.getBroadcast(
                        mService,
                        REQUEST_CODE,
                        new Intent(ACTION_NEXT).setPackage(pkg),
                        PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent prevIntent =
                PendingIntent.getBroadcast(
                        mService,
                        REQUEST_CODE,
                        new Intent(ACTION_PREV).setPackage(pkg),
                        PendingIntent.FLAG_CANCEL_CURRENT);

        mPlayAction =
                new NotificationCompat.Action(
                        R.drawable.playpnn,
                        mService.getString(R.string.label_play),
                        playIntent);
        mPauseAction =
                new NotificationCompat.Action(
                        R.drawable.pausepnn,
                        mService.getString(R.string.label_pause),
                        pauseIntent);
        mNextAction =
                new NotificationCompat.Action(
                        R.drawable.nextpnn,
                        mService.getString(R.string.label_next),
                        nextIntent);
        mPrevAction =
                new NotificationCompat.Action(
                        R.drawable.backpnn,
                        mService.getString(R.string.label_previous),
                        prevIntent);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PREV);

        mService.registerReceiver(this, filter);

        mNotificationManager =
                (NotificationManager) mService.getSystemService(Context.NOTIFICATION_SERVICE);

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        mNotificationManager.cancelAll();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        switch (action) {
            case ACTION_PAUSE:
                mService.mCallback.onPause();
                break;
            case ACTION_PLAY:
                mService.mCallback.onPlay();
                break;
            case ACTION_NEXT:
                mService.mCallback.onSkipToNext();
                break;
            case ACTION_PREV:
                mService.mCallback.onSkipToPrevious();
                break;

        }
     //   Toast.makeText( context, "resver" , Toast.LENGTH_SHORT).show();

    }

    public void update(
            MediaMetadataCompat metadata,
            PlaybackStateCompat state,
            MediaSessionCompat.Token token) {
        if (state == null
                || state.getState() == PlaybackStateCompat.STATE_STOPPED
                || state.getState() == PlaybackStateCompat.STATE_NONE) {
            mService.stopForeground(true);
            try {
                mService.unregisterReceiver(this);
            } catch (IllegalArgumentException ex) {
                // ignore receiver not registered
            }
            mService.stopSelf();
            return;
        }
        if (metadata == null) {
            return;
        }
        boolean isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;
       // NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mService);
        MediaDescriptionCompat description = metadata.getDescription();

     // Bitmap bitmap = (Bitmap) BitmapFactory.decodeResource(Resources.getSystem(),R.id.action_button);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mService, "Hello");

        builder
                // Add the metadata for the currently playing track
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setSubText(description.getDescription())
                .setLargeIcon(CommonModel.getAudioImageBitMap(description.getMediaId()))

                // Enable launching the player by clicking the notification
                .setContentIntent(createContentIntent())

                // Stop the service when the notification is swiped away
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mService,  PlaybackStateCompat.ACTION_STOP))

                // Make the transport controls visible on the lockscreen
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                // Add an app icon and set its accent color
                // Be careful about the color

                .setSmallIcon(R.mipmap.musiclogo)
                .setColor(ContextCompat.getColor(mService, R.color.theme_color_7))

                // Add a pause button


                // Take advantage of MediaStyle features
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(token)
                        .setShowActionsInCompactView(0,1,2)

                        // Add a cancel button
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mService,
                                PlaybackStateCompat.ACTION_STOP)));

// Display the notification and place the service in the foreground
         // If skip to next action is enabled
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0) {
            builder.addAction(mPrevAction);
        }

        builder.addAction(isPlaying ? mPauseAction : mPlayAction);

        // If skip to prev action is enabled
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0) {
            builder.addAction(mNextAction);
        }

//        Notification notification = (Notification) notificationBuilder.build();

        if (isPlaying && !mStarted) {
            mService.startService(new Intent(mService.getApplicationContext(), MediaPlayerService.class));
            mService.startForeground(NOTIFICATION_ID, builder.build());
            mStarted = true;
        } else {
            if (!isPlaying) {
                mService.stopForeground(false);
                mStarted = false;
            }
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private PendingIntent createContentIntent() {
        Intent openUI = new Intent(mService, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(
                mService, REQUEST_CODE, openUI, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}