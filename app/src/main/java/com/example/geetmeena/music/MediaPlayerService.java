package com.example.geetmeena.music;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;

import java.util.List;

public class MediaPlayerService  extends MediaBrowserServiceCompat {
    MyAppDataBase appDataBase;
    private MediaSessionCompat mSession;
    Context contexts;
    static String PREF_FILE_NAME="JeetSec";
    String songPathIndex="0";
    double resumeSongPos=0;
    int onss=1;
    //AudioFragment.SolventRecyclerViewAdapters  solventRecyclerViewAdapters=new AudioFragment.SolventRecyclerViewAdapters();
   MyMediaPlayer myMediaPlayBack;
    final MediaSessionCompat.Callback mCallback =
            new MediaSessionCompat.Callback() {
                @Override
                public void onPlayFromMediaId(String mediaId, Bundle extras) {

                    mSession.setActive(true);
                    MediaMetadataCompat metadata =
                            CommonModel.getMetadata(MediaPlayerService.this, mediaId);
                    mSession.setMetadata(metadata);
                    myMediaPlayBack.play(metadata);
                 // ifAppRunFirstT();

                }

                @Override
                public void onPrepareFromMediaId(String mediaId, Bundle extras) {



                    try {
                        mSession.setActive(true);
                        MediaMetadataCompat  metadata = CommonModel.getMetadata(MediaPlayerService.this, mediaId);
                        mSession.setMetadata(metadata);
                        myMediaPlayBack.prepareMediaPlayer(metadata);
                        }catch (Exception a){}

                 
                }

                @Override
                public void onPlay() {
                    if (myMediaPlayBack.getCurrentMediaId() != null) {
                        onPlayFromMediaId(myMediaPlayBack.getCurrentMediaId(), null);
                    }
                   // Toast.makeText(MediaPlayerService.this, "play" , Toast.LENGTH_SHORT).show();

                }



                @Override
                public void onPause() {
                    myMediaPlayBack.pauseMedia();
                    appDataBase.upDateData("1",myMediaPlayBack.getCurrentMediaId(),myMediaPlayBack.getCurrentStreamPosition());
                    Cursor cursor= appDataBase.getData();
                    StringBuilder buffer=new StringBuilder();
                    while (cursor.moveToNext()){
                        buffer.append("ID").append(cursor.getString(0)).append("\n");
                        buffer.append("PATHOFSONG").append(cursor.getString(1)).append("\n");
                     //   mCurrentMetadata=CommonModel.getMetadata(MediaPlayerService.this,cursor.getString(1));

                    }

                    //Toast.makeText(MediaPlayerService.this, "pause" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSeekTo(long pos) {
                    //myMediaPlayBack.resumeMedia((int) pos);
                }

                @Override
                public void onStop() {
                    //Toast.makeText(MediaPlayerService.this, "MeddiaButtonStop", Toast.LENGTH_SHORT).show();

                    stopSelf();
                }

                @Override
                public void onSkipToNext() {

                    onPlayFromMediaId(CommonModel.getNextSong(myMediaPlayBack.getCurrentMediaId()), null);
                }

                @Override
                public void onSkipToPrevious() {
                    onPlayFromMediaId(CommonModel.getPreviousSong(myMediaPlayBack.getCurrentMediaId()), null);

                }
            };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         //Toast.makeText(this, "MeddiaButtonRes", Toast.LENGTH_SHORT).show();
        MediaButtonReceiver.handleIntent(mSession, intent);
        if(Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())){
            KeyEvent ke = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if(ke.getAction()==KeyEvent.keyCodeFromString( "ACTION_STOP"))
            { mCallback.onStop();
               // Toast.makeText(this, "MeddiaButton "+ ke, Toast.LENGTH_SHORT).show();
            }

        }
        String s=intent.getAction();



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();



        mSession = new MediaSessionCompat(this, "MusicService");
        mSession.setCallback(mCallback);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        setSessionToken(mSession.getSessionToken());

        // TODO: Uncomment the following line to show a notification.
        final MediaNotificationManager mediaNotificationManager =
                new MediaNotificationManager(this);
     myMediaPlayBack=new  MyMediaPlayer(
                       this, new MyMediaPlayer.CallBackToService() {
            @Override
            public void OnPlaybackStateChanged(PlaybackStateCompat stateCompat) {
                mSession.setPlaybackState(stateCompat);
                mediaNotificationManager.update(
                        myMediaPlayBack.getCurrentMedia(), stateCompat, getSessionToken());
                                  }

         @Override
         public void OnPlaybackToNext() {
             mCallback.onSkipToNext();
         }
     });
        appDataBase=new MyAppDataBase(this,"lastSongWasPlayed.db",null,1);

        // Start a new MediaSession.
        String s= readFromPerferences(this,"firsttimeRun","false");
        if(s.equals("false")){
            ifAppRunFirstT();
            saveToPerferences(this,"firsttimeRun","true");
        }
        else  {
            onss=0;
            try {
                Cursor cursor= appDataBase.getData();
                //StringBuffer buffer=new StringBuffer();
                while (cursor.moveToNext() ){
                    String id=cursor.getString(0);
                    songPathIndex=cursor.getString(1);
                    //  resumeSongPos=Long.getLong(cursor.getString(3));
                    //buffer.append("ID"+cursor.getString(0)+"\n");
                    //  buffer.append("SOngPAth"+cursor.getString(1)+"\n");
                    // mCurrentMetadata=CommonModel.getMetadata(getActivity().getBaseContext(),CommonModel.getMediaItems().get(0).getMediaId());
                //    Toast.makeText(MediaPlayerService.this, ""+id+songPathIndex, Toast.LENGTH_SHORT).show();
                    // updateMetadata(mCurrentMetadata);
                    mCallback.onPrepareFromMediaId(songPathIndex,null);
                    // mCallback.onSeekTo((long) resumeSongPos);
                }
            }catch (Exception e){}
        }

    }


    public void ifAppRunFirstT(){
         try {
             boolean re=  appDataBase.insertData("0",0.0);
             if(re=true){
                 // Toast.makeText(this, "isIns", Toast.LENGTH_SHORT).show();
             }
             else {
                 //Toast.makeText(this, "isNotInserted", Toast.LENGTH_SHORT).show();
             }
         }catch (Exception e){}
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(this, "ServiceDestroy", Toast.LENGTH_SHORT).show();
        myMediaPlayBack.stopMedia();
        mSession.setActive(false);
        mSession.release();

        // stopService()
    }


    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
        return new BrowserRoot(CommonModel.getRoot(), null);
    }

    @Override
    public void onLoadChildren(final String parentMediaId, final Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(CommonModel.getMediaItems());
    }

    public static void saveToPerferences(Context context, String perferencesName, String perferencesValue){
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(perferencesName,perferencesValue);
        editor.apply();
        //Toast toast1 = Toast.makeText(context, "hii Test", Toast.LENGTH_SHORT);
        //toast1.show();

    }

    public static String readFromPerferences(Context context,String perferencesName,String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(perferencesName,defaultValue);
    }
}
