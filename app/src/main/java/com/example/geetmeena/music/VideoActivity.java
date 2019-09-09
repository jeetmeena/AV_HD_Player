package com.example.geetmeena.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geetmeena.music.Model.CommonVideo;
import com.example.geetmeena.music.fragment.VideoFragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
  //android:background="#11000000"
public class VideoActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ComponentListener componentListener;
    SimpleExoPlayerView simpleExoPlayerView;
   // SimpleExoPlayer simpleExoPlayer;
    SimpleExoPlayer simpleExoPlayer;
    ExoPlayer exoPlayer;
    Context context;
    int currentWindow=0;
    long playbackPosition=0;
    boolean playWhenReady=true;
    static  String file_path;
    RecyclerView recyclerView;

    ArrayList<CommonVideo> videoArrayListi;
    View viewVide;
    ImageButton screenMode;
      ImageButton screenLock;
      ImageButton subTitle;
    static Configuration configuration;
   static int a;
      private View brightNesLinear;
      private View volumeLinear;
      View controlerAboveBottom;
      SeekBar brightNesSeekBar;
      SeekBar volumeSeekBar;
      MediaSource videoSource,textSource,source;
      private static final String DEBUG_TAG = "Gestures";
      private GestureDetectorCompat mDetector;
      int b=0;
      private static int SWIPE_THRESHOLD=100;
      private static int SWIPE_VELOCITY_THRESHOLD=100;
      private int brightness;
      int MY_PERMISSION_WRITE_SETTINGS=10;
      boolean  settingCanwrite;
      int brightnessVolumeSiwpe;
      //Content resolver used as a handle to the system's settings

      private ContentResolver cResolver;
      AudioManager audioManager;
      //Window object, that will store a reference to the current window

      private Window window;
     int current_position;
     ImageButton exoPerv,exoNext;
    final static int FILE_SELECT=2;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        simpleExoPlayerView=findViewById(R.id.player_view);
        viewVide=findViewById(R.id.player_view);
        context=this;
        componentListener=new ComponentListener();
        Intent intent=getIntent();
         file_path=intent.getStringExtra("jeet");
         current_position=intent.getIntExtra("position",0);
        screenMode = findViewById(R.id.screenMode);
        screenLock=findViewById(R.id.screenLock);
        subTitle=findViewById(R.id.subTitle);
        exoNext=findViewById(R.id.exo_Nex);
        exoPerv=findViewById(R.id.exo_preve);

        controlerAboveBottom=findViewById(R.id.controlerAboveBottom);
           a=0;
          if (intent.getIntExtra("key",12) == 1) {
              VideoFragment videoFragment=VideoFragment.getVideoFragment();
              videoArrayListi=videoFragment.videosList;
          }

          else if(intent.getIntExtra("key",12)==0){
             FolderVideosActivity folderVideosActivity= FolderVideosActivity.getFolderVideosActivity();
              videoArrayListi=folderVideosActivity.videosList;
          }
         recyclerView=findViewById(R.id.video_recyclerView_list);
         recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
              VideoList videoList=new VideoList(videoArrayListi);
          //Get the content resolver

          cResolver =  getContentResolver();

//         Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
          if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_SETTINGS)!= PackageManager.PERMISSION_GRANTED){
              

              if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_SETTINGS)){

                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setMessage("We need read external storage permission to proceed")
                          .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  // FIRE ZE MISSILES!
                              }
                          })
                          .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  // User cancelled the dialog
                                  dialog.dismiss();
                              }
                          });
                  // Create the AlertDialog object
                  builder.create();
              }
              else {
                  ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_SETTINGS},MY_PERMISSION_WRITE_SETTINGS);
                  Toast.makeText(this, "requset", Toast.LENGTH_SHORT).show();

              }

          }
          else {
              Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
             Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
          }


         // Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                settingCanwrite= Settings.System.canWrite(this);
              Toast.makeText(this, ""+settingCanwrite, Toast.LENGTH_SHORT).show();
          }
          if(!settingCanwrite){
              Intent intent1=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
              context.startActivity(intent1);
          }
          audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);


          //Get the current window

          window = getWindow();
        //recyclerView.setAdapter(videoList);
        brightNesLinear=findViewById(R.id.brightNees_LinearLa);
        volumeLinear=findViewById(R.id.volume_LinearLa);
        brightNesSeekBar=findViewById(R.id.brightNees_SeekBar);
        volumeSeekBar=findViewById(R.id.volume_SeekBar);
        mDetector=new GestureDetectorCompat(this,this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        screenMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==0){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                   //int configuration= getRequestedOrientation();
                       screenMode.setBackgroundResource(R.drawable.ic_fullscreen_exit_black_24dp);
                       screenLock.setVisibility(View.VISIBLE);
                   // controlerAboveBottom.setVisibility(View.VISIBLE);

                }
               else if(a==1){
                   // controlerAboveBottom.setVisibility(View.INVISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    screenLock.setVisibility(View.INVISIBLE);
                     screenMode.setBackgroundResource(R.drawable.ic_fullscreen_black_24dp);

                }


            }
        });
        screenLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
             intent.setType("image/*|WebVTT/*|TTML=.ttxt/*|SMPTE-TT/*|SubRip/*|audio/*");
//WebVTT/*|TTML/*|SMPTE-TT=.xml/*|SubRip=.srt/*/.ssa/,ass/.sami
             intent.addCategory(Intent.CATEGORY_OPENABLE);
                                               try {
                 startActivityForResult(Intent.createChooser(intent,"SubTitle"),FILE_SELECT);

             }catch (android.content.ActivityNotFoundException e){
                 Toast.makeText(VideoActivity.this, "Please Install a FileManager", Toast.LENGTH_SHORT).show();
             }
            // startActivityForResult(Intent.createChooser(intent,"SubTitle"),FILE_SELECT);

            }
        });

        brightNesLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               mDetector.onTouchEvent(event);
                brightnessVolumeSiwpe=1;
                return true;
            }
        });
        volumeLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                brightnessVolumeSiwpe=0;
                return true;
            }
        });
        controlerAboveBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               // Toast.makeText(VideoActivity.this, "sing", Toast.LENGTH_SHORT).show();
                int action = MotionEventCompat.getActionMasked(event);

                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        if(exoPlayer.getPlayWhenReady()){
                            exoPlayer.setPlayWhenReady(false);

                        }
                        else {
                            exoPlayer.setPlayWhenReady(true);

                        }


                        return true;
                    default :
                        return VideoActivity.super.onTouchEvent(event);
                }

            }
        });

        exoPerv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTOPreivi();
            }
        });
        exoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNext();
            }
        });

        //exoPlayer=ExoPlayerFactory.newSimpleInstance();['''
      // simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);[
       //simpleExoPlayer.
       // SimpleExoPlayer player = ExoPlayerFactory.newS['['['[[[[[[[[[[[[[[[[[impleInstance(context);
      //    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                // taking action on event

                //lockScreenRotation(Configuration.ORIENTATION_PORTRAIT);
               // hideSystemUi();
               // recyclerView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParamsP=simpleExoPlayerView.getLayoutParams();
                layoutParamsP.width= ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParamsP.height= ViewGroup.LayoutParams.MATCH_PARENT;
                simpleExoPlayerView.setLayoutParams(layoutParamsP);
                a=0;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
              //  // taking action on event
               // hideSystemUiFullScreen();
                recyclerView.setVisibility(View.INVISIBLE);
                a=1;
             // simpleExoPlayerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ViewGroup.LayoutParams layoutParams=simpleExoPlayerView.getLayoutParams();
                layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
                     simpleExoPlayerView.setLayoutParams(layoutParams);

                // Apply the new height for ImageView programmatically
                //simpleExoPlayerView.getLayoutParams().height = ;

                // Apply the new width for ImageView programmatically
               // simpleExoPlayerView.getLayoutParams().width = ;
              //  lockScreenRotation(Configuration.ORIENTATION_LANDSCAPE);
                break;
            case Configuration.ORIENTATION_SQUARE:
                // taking action on event
               // lockScreenRotation(Configuration.ORIENTATION_SQUARE);
                break;
            default:
                try {
                    throw new Exception("Unexpected orientation!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private void lockScreenRotation(int orientation)
    {
        // Stop the screen orientation changing during an event
        switch (orientation)
        {

            case Configuration.ORIENTATION_PORTRAIT:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;



        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUiFullScreen() {
        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }



    private void initializePlayer() {
       exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        simpleExoPlayerView.setPlayer((SimpleExoPlayer) exoPlayer);


        exoPlayer.setPlayWhenReady(playWhenReady);

        exoPlayer.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(file_path);
          videoSource = buildVideoSource(uri);
         // textSource=buildTextSource(uri);
      // MediaSource  mergingSource=new MergingMediaSource(videoSource,textSource);
        exoPlayer.prepare(videoSource, true, false);
    }


    private MediaSource buildVideoSource(Uri uri) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(VideoActivity.this,
                Util.getUserAgent(VideoActivity.this, "com.exoplayerdemo"), bandwidthMeter);

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(uri,dataSourceFactory, extractorsFactory, null, null);
        return videoSource;
    }
    private MediaSource buildTextSource(Uri uri){
        //Format subTitelFormat=Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP,3,lang)
          MediaSource textsource=new ExtractorMediaSource(uri,null,null,null,null);

          return textsource;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializePlayer();
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE
                | SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void moveToNext(){
        exoPlayer.release();
        exoPlayer = null;
        playbackPosition=0;
        currentWindow=0;
        current_position=current_position+1;
        playWhenReady=true;
        if(current_position<videoArrayListi.size()){
            file_path=videoArrayListi.get(current_position).getVideoFilePath();
            Toast.makeText(this, "next"+current_position, Toast.LENGTH_SHORT).show();
        }
        else {
            //videoArrayListi.size()-1
            current_position=0;
            Toast.makeText(this, ""+current_position, Toast.LENGTH_SHORT).show();
            file_path=videoArrayListi.get(current_position).getVideoFilePath();
        }
        //   file_path=videoArrayListi.get(current_position+1).getVideoFilePath();
        initializePlayer();
    }
    private void moveTOPreivi(){
        exoPlayer.release();
        exoPlayer = null;
        playbackPosition=0;
        currentWindow=0;
        current_position=current_position-1;
        playWhenReady=true;
        if(current_position>=0){
            file_path=videoArrayListi.get(current_position).getVideoFilePath();
            Toast.makeText(this, "per"+current_position, Toast.LENGTH_SHORT).show();
        }
        else {
            //videoArrayListi.size()-1
            current_position=videoArrayListi.size()-1;
            file_path=videoArrayListi.get(current_position).getVideoFilePath();
            Toast.makeText(this, ""+current_position, Toast.LENGTH_SHORT).show();
        }
        initializePlayer();
    }


      @Override
      public boolean onDown(MotionEvent e) {
        //  Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
          return true;
      }

      @Override
      public void onShowPress(MotionEvent e) {

      }

      @Override
      public boolean onSingleTapUp(MotionEvent e) {

        if(exoPlayer.getPlayWhenReady()){
           exoPlayer.setPlayWhenReady(false);

        }
        else {
           exoPlayer.setPlayWhenReady(true);

        }


          return true;
      }



      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(distanceY>3* distanceX){
           // Toast.makeText(this, "yyy", Toast.LENGTH_SHORT).show();
        }
        else if(distanceX>3*distanceY){
            //Toast.makeText(this, "XXX", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(this, "XY", Toast.LENGTH_SHORT).show();
        }


          return true;
      }

      @Override
      public void onLongPress(MotionEvent e) {

      }

      @Override
      public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
          boolean result=false;
          if(brightnessVolumeSiwpe==1){

              try {
                  float diffY = e2.getY() - e1.getY();
                  float diffX = e2.getX() - e1.getX();
                  if (Math.abs(diffX) > Math.abs(diffY)) {
                      if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                          if (diffX > 0) {
                              onSwipeRight();
                          } else {
                              onSwipeLeft();
                          }
                          result = true;
                      }
                  }
                  else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                      if (diffY > 0) {
                          onSwipeBottom();
                      } else {
                          onSwipeTop();

                      }
                      result = true;
                  }
              } catch (Exception exception) {
                  exception.printStackTrace();
              }

          }
          else if(brightnessVolumeSiwpe==0){
              try {
                  float diffY = e2.getY() - e1.getY();
                  float diffX = e2.getX() - e1.getX();
                  if (Math.abs(diffX) > Math.abs(diffY)) {
                      if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                          if (diffX > 0) {
                              onSwipeRightVolume();
                          } else {
                              onSwipeLeftVolume();
                          }
                          result = true;
                      }
                  }
                  else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                      if (diffY > 0) {
                          onSwipeBottomVolume();
                      } else {
                          onSwipeTopVolume();

                      }
                      result = true;
                  }
              } catch (Exception exception) {
                  exception.printStackTrace();
              }
          }


          return result;


      }

      private void onSwipeTopVolume() {
          int media_current_Volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
          //Toast.makeText(this,""+media_current_Volume, Toast.LENGTH_SHORT).show();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,media_current_Volume+1,AudioManager.FLAG_SHOW_UI);


      }

      private void onSwipeLeftVolume() {
          //Toast.makeText(this,"left", Toast.LENGTH_SHORT).show();
      moveTOPreivi();
      }

      private void onSwipeBottomVolume() {
          int media_current_Volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
          //int getmax=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
         //Toast.makeText(this," "+media_current_Volume, Toast.LENGTH_SHORT).show();
          audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,media_current_Volume-2,AudioManager.FLAG_SHOW_UI);
         // audioManager.adjustVolume(AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
      }

      private void onSwipeRightVolume() {
         moveToNext();
         // Toast.makeText(this,"right     ", Toast.LENGTH_SHORT).show();
      }



      private void onSwipeTop() {

          // Toast.makeText(this,"top", Toast.LENGTH_SHORT).show();
          try

          {
              Settings.System.putInt(getContentResolver(),
                      Settings.System.SCREEN_BRIGHTNESS_MODE,
                      Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

              brightness = Settings.System.getInt(getContentResolver(),
                      Settings.System.SCREEN_BRIGHTNESS);
              //Get the current system brightness
              //  Toast.makeText(this,"curent   "+brightness, Toast.LENGTH_SHORT).show();
              int mode =Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE);
              //brightness = System.getProperty(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS)
              // Toast.makeText(this,"Mode_"+mode, Toast.LENGTH_SHORT).show();
              incressBrightNess();
          }

          catch (Settings.SettingNotFoundException e)

          {//Throw an error case it couldn't be retrieved
              chackSelfPremison();
              Log.e("Error", "Cannot access system brightness");

              e.printStackTrace();

          }

      }

      private void onSwipeLeft() {
      moveTOPreivi();
      //    Toast.makeText(this,"left", Toast.LENGTH_SHORT).show();
      }

      private void onSwipeBottom() {
          dicresingBrightNess();
          // Toast.makeText(this,"bottom", Toast.LENGTH_SHORT).show();
      }

      private void onSwipeRight() {
          moveToNext();
          //Toast.makeText(this,"right     ", Toast.LENGTH_SHORT).show();
      }


      public void incressBrightNess(){
           brightness=brightness +25;
          Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
          WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
          layoutpars.screenBrightness = brightness / (float)255;
          getWindow().setAttributes(layoutpars);
          //Toast.makeText(this,"curent   "+brightness, Toast.LENGTH_SHORT).show();

      }
      public void dicresingBrightNess(){
          brightness=brightness-25;
          Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
          WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
          layoutpars.screenBrightness = brightness / (float)255;
          getWindow().setAttributes(layoutpars);
          //Toast.makeText(this,"curent   "+brightness, Toast.LENGTH_SHORT).show();

      }


      public void chackSelfPremison(){

          if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_SETTINGS)!= PackageManager.PERMISSION_GRANTED){

              if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_SETTINGS)){

                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setMessage("We need Write setting  permission to proceed")
                          .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  // FIRE ZE MISSILES!
                              }
                          })
                          .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  // User cancelled the dialog
                                  dialog.dismiss();
                              }
                          });
                  // Create the AlertDialog object
                  builder.create();
              }
              else {
                  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS},MY_PERMISSION_WRITE_SETTINGS);


              }

          }

      }


      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {

          switch (requestCode){
              case FILE_SELECT:
                  if(resultCode==RESULT_OK){
                      Uri uriSub=data.getData();
                      Toast.makeText(this, ""+uriSub, Toast.LENGTH_SHORT).show();
                  }

                  break;

          }

          super.onActivityResult(requestCode, resultCode, data);
      }

      private class ComponentListener extends Player.DefaultEventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady,
                                         int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d("TAG", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }

    private class VideoList extends RecyclerView.Adapter<VideoAdapter> {

        public VideoList(ArrayList<CommonVideo> videoArrayListi) {
        }

        @NonNull
        @Override
        public VideoAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.video_recyc_list,null);
                VideoAdapter videoAdapter=new VideoAdapter(view);
            return videoAdapter;
        }

        @Override
        public void onBindViewHolder(@NonNull VideoAdapter holder, int position) {

            holder.videoName.setText(videoArrayListi.get(position).getVideoName());
          //  Uri uri = Uri.parse(videoArrayListi.get(position).getVideoFilePath());
            Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(videoArrayListi.get(position).getVideoFilePath(),MediaStore.Images.Thumbnails.MINI_KIND);

            holder.videoExoView.setImageBitmap(bitmap);
            holder.videoArttestText.setText(videoArrayListi.get(position).getVideoAlbum());
            holder.videoDurations.setText(dureTion(Integer.parseInt(videoArrayListi.get(position).getVideoDuration())));

        }



        @Override
        public int getItemCount() {
            return videoArrayListi.size();
        }
        public String dureTion(int d){
            double a= (d/1000.0)/60.0;
            String string = String.valueOf(a);

            String[] parts = string.split("\\.");

            String minutes = parts[0];
            int second = (d/1000)%60;

       /*
         // 004
       // 034556
       // a =Double.valueOf(part2);
       // a=a*60;

        String string2 = String.valueOf(second);
        //String[] parts2 = string2.split("\\.");
        //part2=parts2[0];



        int f=Integer.valueOf(string);
        //  songDuretion.setText(string);*/
            string = minutes + ":" + second;
            //Toast.makeText(mainActivity.context," song"+ string, Toast.LENGTH_SHORT).show();
            return string;
        }

    }

    private class VideoAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView videoName;
        TextView videoArttestText;
        TextView videoDurations;
        ImageView videoExoView;
        public VideoAdapter(View itemView) {
            super(itemView);
            videoName=itemView.findViewById(R.id.videoNameText);
            videoExoView=itemView.findViewById(R.id.videoExoView);
            videoArttestText=itemView.findViewById(R.id.videoArttestText);
            videoDurations=itemView.findViewById(R.id.videoDuration);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            exoPlayer.release();
            exoPlayer = null;
            playbackPosition=0;
            currentWindow=0;
            playWhenReady=true;
           file_path=videoArrayListi.get(getAdapterPosition()).getVideoFilePath();
            initializePlayer();

        }
    }





    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onBackPressed();
    }
}
