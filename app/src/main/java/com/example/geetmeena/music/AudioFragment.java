package com.example.geetmeena.music;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.geetmeena.music.AudioFragment.audioList;
import static com.example.geetmeena.music.AudioFragment.mMediaPlayer;
import static com.example.geetmeena.music.AudioFragment.posis1;
import static com.example.geetmeena.music.AudioFragment.resumePosition;
import static com.example.geetmeena.music.AudioFragment.selected;
/**
 * A simple {@link Fragment} subclass.

 */
//
@SuppressLint("ValidFragment")
public  class AudioFragment extends Fragment   {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerAudio;
     MainActivity mainActivity;
    public View[] viewsList;
    public ArrayList<ViewReso> aaaID;
    public static List<MediaBrowserCompat.MediaItem> audioList;
    Context c;
    MediaBrowserCompat mediaBrowser;
    MediaBrowserCompat.SubscriptionCallback subscriptionCallback;
    public static MediaPlayer mMediaPlayer;

    String songPathIndex="0";
    MyAppDataBase myAppDataBase;
    public static ArrayList<Integer> itemListSelect=new ArrayList<Integer>();
    static boolean stateOfplay=true;
    int coun=0;
    Context context;
    static Bitmap blurred= null;
    public  static  int sele=0;
    static String ab;
    //public   static  int j=0;
    View bv;
    static RelativeLayout layout;
    View layoutView;
    static Bitmap finalBitma = null;
    static byte[] artt;
    static Bitmap longClick = null;
    Bitmap bitmapMaster;
    //Canvas canvasMaster;
    private AttributeSet atrr;

    int count=0;
    public static String perpath=null;
    Button fabCancel;
    Button fab;
    static  View viewfab;
    MediaMetadataRetriever mediaMetadataa = new MediaMetadataRetriever();
    public Uri newUei;
    static int vie=0;
    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

    //private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

     AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    int id=123;
    // Get the session's metadata
    private MediaSessionCompat mSessionF;
    MediaSessionCompat.Token token;
    MediaControllerCompat controller ;
    MediaControllerCompat.Callback controlCallBack;
    MediaMetadataCompat mediaMetadata ;
    MediaMetadataCompat mCurrentMedia;
    MediaDescriptionCompat description ;
    private MediaMetadataCompat mCurrentMetadata;
    private PlaybackStateCompat mCurrentState;
    static int resumePosition;
    private MediaBrowserCompat mMediaBrowser;
   // int mCurrentState;
    PlaybackStateCompat pbState;
    String mParentId;
    public  ArrayList<String> j;
    //private Context context;

    public static  int posis1=0;

    private com.melnykov.fab.FloatingActionButton fab2;

    LinearLayout linearLayout_Bottom;
    BottomSheetBehavior behavior;
    //public Uri newUei;

    SeekBar seekBar;
    Bundle bundle;
    String  posi1;
    public  Runnable run;
    Handler handler;
    public  int lastIndex;
    public static   String next;
    static   int murrentPosition;
    static boolean onOff;
    public  static  int rean=0;
    static  int rand_int1;
    static ArrayList<Integer> selected=new ArrayList<Integer>();
    public  static String PREF_FILE_NAME="JeetFirst2";
    int a=0;
    MediaPlayerService mediaPlayerService;
    MediaSessionManager mediaSessionManager;
    int selectedLastInd;
    CoordinatorLayout coordinatorLayout;
    public static AudioFragment audioFragmentInstent;
    View views;
    View view1;
    MyMediaPlayer myMediaPlayer;
    Context appContext;
    String[] songListsName ;
    String lsn;
    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mSubscriptionCallback);
                    try {
                        MediaControllerCompat mediaController =
                                new MediaControllerCompat(getActivity(), mMediaBrowser.getSessionToken());
                        updatePlaybackState(mediaController.getPlaybackState());
                        updateMetadata(mediaController.getMetadata());
                        mediaController.registerCallback(mMediaControllerCallback);
                        MediaControllerCompat.setMediaController(
                                getActivity(), mediaController);

                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            };

    // Receives callbacks from the MediaController and updates the UI state,
    // i.e.: Which is the current item, whether it's playing or paused, etc.
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    updateMetadata(metadata);

                   // mBrowserAdapter.notifyDataSetChanged();

                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    updatePlaybackState(state);
                  //  mBrowserAdapter.notifyDataSetChanged();


                }

                @Override
                public void onSessionDestroyed() {
                    updatePlaybackState(null);
                   // mBrowserAdapter.notifyDataSetChanged();
                }
            };

    private final MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(
                        String parentId, List<MediaBrowserCompat.MediaItem> children) {
                    onMediaLoaded(children);
                }
            };

    private void onMediaLoaded(List<MediaBrowserCompat.MediaItem> media) {
        audioList=   media;
        StaggeredGridLayoutManager    gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerAudio.setLayoutManager(gaggeredGridLayoutManager);

        try {


            //Toast toast = Toast.makeText(getActivity().getBaseContext(), "Media Item Load" , Toast.LENGTH_SHORT);toast.show();



            SolventRecyclerViewAdapters rcAdapter = new SolventRecyclerViewAdapters(getActivity().getBaseContext(),audioList,aaaID);

            recyclerAudio.setAdapter(rcAdapter);

            buildTransportControls();



        } catch (Exception e) {
            Log.i("rtuio", e.getMessage());
        }
       // mBrowserAdapter.clear();
       // mBrowserAdapter.addAll(media);
       // mBrowserAdapter.notifyDataSetChanged();
    }

    private void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
        if (item.isPlayable()) {
            MediaControllerCompat.getMediaController(getActivity())
                    .getTransportControls()
                    .playFromMediaId(item.getMediaId(), null);
        }
    }
    @SuppressLint("ValidFragment")

    public AudioFragment(String s, Context context, ArrayList<ViewReso> aaaID) {
        c=context;
        this.aaaID=aaaID;
      //  this.viewsList=viewsList;
        // Required empty public constructor
    }

    public AudioFragment(String aaa) {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       mainActivity=MainActivity.getInstance();
       //mediaPlayerService=new MediaPlayerService(getActivity().getBaseContext());
        appContext=getActivity();
      //  Log.e("AudioFrag","connected");
     //   Log.d("AudioFraf","Conecté al servicio");
     getNameOFSongLists();
      myMediaPlayer=new MyMediaPlayer();
       myAppDataBase=new MyAppDataBase(getActivity(),"lastSongWasPlayed.db",null,1);

     }
    private String getParentId() {

        return mParentId != null ? mParentId : mediaBrowser.getRoot();
    }
    private void buildTransportControls() {
      MainActivity  mainActivity1=MainActivity.getInstance();
       // Toast.makeText(getActivity().getBaseContext(), "buildTransportcon", Toast.LENGTH_SHORT).show();

        mainActivity1.bottomPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly
               // Toast.makeText(getActivity().getBaseContext(), "bottom", Toast.LENGTH_SHORT).show();
                final int state = mCurrentState == null ? PlaybackStateCompat.STATE_NONE : mCurrentState.getState();
                if (state == PlaybackState.STATE_PAUSED
                        || state == PlaybackState.STATE_STOPPED
                        || state == PlaybackState.STATE_NONE) {

                    if (mCurrentMetadata == null) {
                        mCurrentMetadata =
                                CommonModel.getMetadata(
                                       getActivity().getBaseContext(),
                                        CommonModel.getMediaItems().get(0).getMediaId());
                        updateMetadata(mCurrentMetadata);
                    }
                    MediaControllerCompat.getMediaController(getActivity())
                            .getTransportControls()
                            .playFromMediaId(
                                    mCurrentMetadata.getDescription().getMediaId(), null);
                    v.setBackgroundResource(R.drawable.exo_controls_pause);


                } else {
                    MediaControllerCompat.getMediaController(getActivity())
                            .getTransportControls()
                            .pause();

                    v.setBackgroundResource(R.drawable.exo_controls_play);

                }
            }
        });

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(getActivity());

        // Display the initial state
        mediaMetadata= mediaController.getMetadata();
         pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
      //  MediaControllerCompat.Callback controllerCallback;
        mediaController.registerCallback(controlCallBack);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_audio, container, false);
        recyclerAudio=view.findViewById(R.id.recycler_Audio);
       // Toast.makeText(getActivity().getBaseContext(),"1!"+aaaID, Toast.LENGTH_SHORT).show();

        view1=inflater.inflate(R.layout.bottom_sheet, container, false);
            // audioFragmentInstent=this;
        //TextView t=view1.findViewById(R.id.bottom_AudioText);
        //t.setText("sdf");

        audioLoad();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // mainActivity.songDuretion.setText("0:0");


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }




    private void updatePlaybackState(PlaybackStateCompat state) {
        mCurrentState = state;
        if (state == null
                || state.getState() == PlaybackState.STATE_PAUSED
                || state.getState() == PlaybackState.STATE_STOPPED) {
            //Toast.makeText(getActivity().getBaseContext(), "pause", Toast.LENGTH_SHORT).show();

            mainActivity.buttonplay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_play));
            mainActivity.bottomPlay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_play));
            // mPlayPause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_arrow_black_36dp));
        } else {
                mainActivity.seekBar.setMax(mMediaPlayer.getDuration());

            mainActivity.buttonplay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_pause));
            mainActivity.bottomPlay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_pause));
            seekUpdation();
          //  Toast.makeText(getActivity().getBaseContext(), "play", Toast.LENGTH_SHORT).show();
            // mPlayPause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_black_36dp));
        }
        //mPlaybackControls.setVisibility(state == null ? View.GONE : View.VISIBLE);
    }

    private void updateMetadata(MediaMetadataCompat metadata) {
        mCurrentMetadata = metadata;
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();

       try {

           mediaMetadataRetriever.setDataSource(CommonModel.getFilePath(metadata.getDescription().getMediaId()));
           mainActivity.songDuretion.setText(dureTion(Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))));

       }catch (Exception e){}

        //Toast.makeText(getActivity().getBaseContext(), "UpDateMataData", Toast.LENGTH_SHORT).show();
        mainActivity.bottomTitle.setText(metadata == null ? "" : metadata.getDescription().getTitle());
        mainActivity.bottomSubTitle.setText(metadata == null ? "" : metadata.getDescription().getSubtitle());
        mainActivity.bottomSheetImageView.setImageBitmap(metadata == null ? BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.nightclub_w)
                : CommonModel.getAudioImageBitMap( metadata.getDescription().getMediaId()));
        mainActivity.imageView.setImageBitmap(metadata== null ? BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.nightclub_w) :CommonModel.getAudioImageBitMap(
                metadata.getDescription().getMediaId()));

        //mBrowserAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();

        mMediaBrowser =
                new MediaBrowserCompat(
                        getActivity(),
                        new ComponentName(getActivity(), MediaPlayerService.class),
                        mConnectionCallback,
                        null);
        mMediaBrowser.connect();
       // MediaControllerCompat.TransportControls transportContro=controller.getTransportControls();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaBrowser.disconnect();
      try {
          handler.removeCallbacksAndMessages(null);
      }catch (Exception e){}
       // Toast.makeText(getActivity().getBaseContext(), "destroy", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop() {
        super.onStop();
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            controller.unregisterCallback(mMediaControllerCallback);
        }
        if (mMediaBrowser != null && mMediaBrowser.isConnected()) {
            if (mCurrentMetadata != null) {
                mMediaBrowser.unsubscribe(mCurrentMetadata.getDescription().getMediaId());
            }

        }
        //Toast.makeText(getActivity().getBaseContext(), "stop", Toast.LENGTH_SHORT).show();

       try {
           handler.removeCallbacksAndMessages(null);
       }catch (Exception e){}

    }

    public void audioLoad(){
        mainActivity=MainActivity.getInstance();
        //audioList=mainActivity.AllMusicPathList();
        if(audioList==null){
         //ArrayList<CommonModel>   audioList = mainActivity.AllMusicPathList();
        }


    }

public void mediaControlCall(){
   }
   public void setAudioFocusRequest(){

         audioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                // Pause playback
                    //pauseMedia();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                    // Raise it back to normal
                    //resumeMedia();
                //
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
               // mAudioManager.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                   // mAudioManager.abandonAudioFocus(audioFocusChangeListener);
                    // Stop playback
            }
            else  if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Lower the volume
                    Toast.makeText(getActivity().getBaseContext(), "AudioFocus Can Duck", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mAudioManager=(AudioManager)
                getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int result=mAudioManager.requestAudioFocus(audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
              //MyMediaPlayer.playMedia();
              buildTransportControls();
        }
       int result1 = mAudioManager.requestAudioFocus(audioFocusChangeListener,
               // Use the music stream.
               AudioManager.STREAM_MUSIC,
               // Request permanent focus.
               AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
   }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public class SolventRecyclerViewAdapters extends RecyclerView.Adapter<SolventHolders> implements
             View.OnClickListener {
        Context context;

        public List<MediaBrowserCompat.MediaItem> itemList;
        public SolventRecyclerViewAdapters(Context baseContext, List<MediaBrowserCompat.MediaItem> audioList, ArrayList<ViewReso> aaaID) {
            itemList = audioList;
            this.context = baseContext;
        }



        @NonNull
        @Override
        public SolventHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenaa_main,null);
            views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet,null);
            //fab2.show();
            SolventHolders rcv = new SolventHolders(layoutView,itemList,views);
              getResurs();

            return rcv;
        }

        public void getResurs(){
         TextView au;
         ImageView a;
            LayoutInflater inflater = (LayoutInflater) MainActivity.getmContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            // views.setOnClickListener(this);
      // View views  =   inflater.inflate(R.layout.bottom_sheet,AudioFragment.this,true);
        // View   views=  LayoutInflater.from(MainActivity.getmContext()).inflate(R.layout.bottom_sheet,null);
        mainActivity.buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(v.getContext(),"Prev", Toast.LENGTH_SHORT).show();

                MediaControllerCompat.getMediaController(getActivity())
                        .getTransportControls().skipToPrevious();
            }
        });
        mainActivity.buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // Toast.makeText(getActivity().getBaseContext(), "bottom", Toast.LENGTH_SHORT).show();
                final int state = mCurrentState == null ? PlaybackStateCompat.STATE_NONE : mCurrentState.getState();
                if (state == PlaybackState.STATE_PAUSED
                        || state == PlaybackState.STATE_STOPPED
                        || state == PlaybackState.STATE_NONE) {

                    if (mCurrentMetadata == null) {
                        mCurrentMetadata =
                                CommonModel.getMetadata(
                                        getActivity().getBaseContext(),
                                        CommonModel.getMediaItems().get(0).getMediaId());
                        updateMetadata(mCurrentMetadata);
                        v.setBackgroundResource(R.drawable.exo_controls_pause);
                    }
                    MediaControllerCompat.getMediaController(getActivity())
                            .getTransportControls()
                            .playFromMediaId(
                                    mCurrentMetadata.getDescription().getMediaId(), null);
                } else {
                    MediaControllerCompat.getMediaController(getActivity())
                            .getTransportControls()
                            .pause();
                    v.setBackgroundResource(R.drawable.exo_controls_play);
                }
            }
        });
        mainActivity.buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity().getBaseContext(), "next", Toast.LENGTH_SHORT).show();
                MediaControllerCompat.getMediaController(getActivity())
                        .getTransportControls().skipToNext();
            }
        });
        mainActivity.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {

                    mMediaPlayer.seekTo(progress);
                    resumePosition=progress;
                    //next=String.valueOf(dureTion(progress));
                    mainActivity.seekDuretoin1.setText(dureTion(progress));
                   // Toast.makeText(co,"seek", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        }


        @Override
        public void onBindViewHolder(@NonNull SolventHolders holder, final int position) {
            //  ImageView audioView2 = null;

            ImageView audioView2 = null;
            MediaMetadataRetriever mediaMetadataRetriever =new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(CommonModel.getFilePath(itemList.get(position).getMediaId()));
           // MediaMetadataCompat matadta=CommonModel.getMetadata(getActivity().getBaseContext(),);

            try {



                //  view.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
                Bitmap bit=audioImage(CommonModel.getFilePath(itemList.get(position).getMediaId()),0);
                String nam=name( mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
               // holder.jee1.setText("ghjsb dfgs ");
                //To enable or disable Shadow Responsive Effect:
                //holder.jee1.setText(itemList.get(position).getFilepath1());
                //holder.countryPhoto.setImageResource(Integer.parseInt(itemList.get(position).getDuretion()));
                if(bit==null) {
                    BitmapFactory.Options res;

                    holder.countryPhoto.setBackgroundColor(Color.TRANSPARENT);
                    holder.countryPhoto.setImageResource(R.drawable.asdfss);

                    holder.countryName.setText(nam);
                }else {

                    holder.countryPhoto.setBackgroundColor(Color.TRANSPARENT);
                    holder.countryName.setText(nam);
                    holder.countryPhoto.setImageBitmap(bit);



                    //holder.countryPhoto.setImageURI(Uri.parse((itemList.get(position).getFilepath1())));
                }

          /*  holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(sele==0) {
                        try {
                            sele=1;
                         int   abc =position;
                            //viewfab.setEnabled(true);
                            //fabCancel=viewfab.findViewById(R.id.fabcancel);
                            //fabCancel.setEnabled(true);
                            //viewfab.setBackgroundResource(R.drawable.cancelpn);
                            blurred = selecTed(abc);
                            if (blurred == null) {

                                Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.asdfss, null);
                                Bitmap myLogo = ((BitmapDrawable) vectorDrawable).getBitmap();
                                blurred = imageSlect(myLogo);


                            }

                        } catch (Exception e) {
                        }
                         holder.countryPhoto.setBackgroundColor(Color.RED);
                        holder.countryPhoto.setImageBitmap(blurred);
                        Toast.makeText(v.getContext(), "Clicke 2 = " , Toast.LENGTH_SHORT).show();
                    }



                    return true;
                }
            }); */

            }catch (Exception e){
                Log.i("jee",e.getMessage());
            }


            //holder.countryPhoto.setImageURI(Uri.parse((itemList.get(position).getFilepath1())));


        }
         public void ond(View v){
            //onClick(v);
            //getView();
         }
        @Override
        public int getItemCount() {
            return itemList.size();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.play1:


                    //  Toast.makeText(view.getContext(), "  play", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.next:

                    // Toast.makeText(v.getContext(), "  next", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.back:

                    // Toast.makeText(v.getContext(), "  back", Toast.LENGTH_SHORT).show();
                    break;
                default: break;

            }

            //viewfab.setBackgroundResource(R.drawable.dpalyai);
        }


    }


    private class SolventHolders extends RecyclerView.ViewHolder implements View.OnClickListener,ImageView.OnLongClickListener {
        public TextView countryName;
        public ImageView countryPhoto;
        private ImageButton buttonOfShowDi;
        String jee2;
        TextView jee1;
        Button button;
        List<MediaBrowserCompat.MediaItem> videoslist;
        int i =0;


        public SolventHolders(View itemView, List<MediaBrowserCompat.MediaItem> itemList, View views) {
            super(itemView);
            videoslist=itemList;
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
            buttonOfShowDi=itemView.findViewById(R.id.buttonOfShowDi);
            buttonOfShowDi.setTag(R.id.buttonOfShowDi,itemView);
            buttonOfShowDi.setOnClickListener(this);
            //  jee1=views.findViewById(R.id.textView);
            //video=itemView.findViewById(R.id.videoView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(final View v) {
            final int ind = getAdapterPosition();
           if(v.getId() == buttonOfShowDi.getId()) {
              showListOfOption(ind);
           }
           else {
               i++;

               if (sele == 0) {
                   try {
                       mMediaPlayer.stop();
                   } catch (Exception e) {
                   }

                   palay1(ind);
                   //mainActivity.behavior.setPeekHeight(230 );

                   mainActivity.buttonplay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_pause));
                   mainActivity.bottomPlay.setBackground(mainActivity.getResources().getDrawable(R.drawable.exo_controls_pause));
                   // mainActivity.notiFication();

                   //  MediaPlayerService mediaPlayerService=new MediaPlayerService(getActivity().getBaseContext());
                   //  mediaPlayerService.onCreate();


               }
           }




        }



        @Override
        public boolean onLongClick(View v) {
            return false;
        }


    }
    private String[] getNameOFSongLists() {

        String[]  sName=mainActivity.getArray("SongListsName");
        if ( sName==null ){
               lsn=null;
            //  Toast.makeText(instance, ""+songListsName[0], Toast.LENGTH_SHORT).show();
        }
        else {songListsName=sName;}

         return sName;
    }
    private void showListOfOption(final int itemPostion) {
        String[] s=new String[]{"Add To PlayList","Add As Favorite"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(s, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0){
                 showSongLists(itemPostion);
                }
                else if (i==1){
                     myAppDataBase.insertFavoriteSongList(CommonModel.getFilePath(audioList.get(itemPostion).getMediaId()));

                }
                //Toast.makeText(mainActivity, "selected"+i, Toast.LENGTH_SHORT).show();

                  }

        });
        builder.show();
     }

    private void showSongLists(final int itemPosition) {
     final  int a=0;
        //MainActivitya mainActivitya= new MainActivity();
        String[] s=new String[]{};
        if(songListsName!=null){
           s=songListsName;

        }
         AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        if(songListsName!=null){
            builder.setItems(s, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // Toast.makeText(mainActivity, "selected"+i, Toast.LENGTH_SHORT).show();
                    ArrayList<String> itemOfSongList=new ArrayList<>();
                    if(mainActivity.getArrayList(songListsName[i])==null){
                        itemOfSongList.add(CommonModel.getFilePath(audioList.get(itemPosition).getMediaId()));
                        mainActivity.saveArrayList(itemOfSongList,songListsName[i],4);
                    }
                    else {
                        itemOfSongList=mainActivity.getArrayList(songListsName[i]);
                        itemOfSongList.add(CommonModel.getFilePath(audioList.get(itemPosition).getMediaId()));
                        mainActivity.saveArrayList(itemOfSongList,songListsName[i],4);

                    }

                }

            });
        }
        builder.setPositiveButton("CREATPLAYLIST", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   if(songListsName!=null){
                       creatSongListDialog(songListsName.length);
                       //Toast.makeText(mainActivity, ""+songListsName.length, Toast.LENGTH_SHORT).show();
                   }
                   else {
                       creatSongListDialog(0);
                      // Toast.makeText(mainActivity, ""+s.length, Toast.LENGTH_SHORT).show();
                   }
            }
        });
        builder.setNegativeButton("CNACEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

             }
        });
         builder.show();

    }

    private void creatSongListDialog(final int a) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter PlayList Name");
        final String[] s=new String[]{};
        LayoutInflater layoutInflater=requireActivity().getLayoutInflater();
        final View v=layoutInflater.inflate(R.layout.alert_dialog,null);
        builder.setView(v);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //int a=s.length;
                //creatSongListDialog(a);
                EditText textView=v.findViewById(R.id.songListName);
                String namList=textView.getText().toString();


                if(namList.isEmpty()){
                    creatSongListDialog(a);
                }
                else if(a==0) {
                    String[] ss=new String[]{namList};
                 //   Toast.makeText(mainActivity, ""+ songListsName, Toast.LENGTH_SHORT).show();
                    mainActivity.saveArray( ss,"SongListsName",a);
                   getNameOFSongLists();
                }
                else {
                    int a= songListsName.length;
                    int b=a+1;
                    String[]  sName=new String[b];
                    int j=0;
                    while (j<a){
                        //Toast.makeText(mainActivity, ""+ songListsName[j], Toast.LENGTH_SHORT).show();

                        sName[j]=songListsName[j];
                       j++;
                    }
                     sName[a]=namList;
                  //  Toast.makeText(mainActivity, ""+ a+"list", Toast.LENGTH_SHORT).show();
                    mainActivity.saveArray( sName,"SongListsName",a);
                    getNameOFSongLists();
                }

            }
        });
        builder.setNegativeButton("CNACEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }


    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if( mMediaPlayer != null && mMediaPlayer.isPlaying() ) {
                mMediaPlayer.pause();
            }
        }
    };




    public String name(String name){
        String jeet = null;

        char[] namea=name.toCharArray();

        if(namea.length>=23){
            name=name.substring(0,22);
            jeet=new String(name)+"..";
        }
        else {
            jeet = new String(name);
        }
        return jeet;
    }

    public String songName(String name){
        String jeet = null;

        char[] namea=name.toCharArray();

        if(namea.length>=51){
            name=name.substring(0,50);
            jeet=name+"..";
        }
        else {
            jeet = name;
        }
        //songName.setText(jeet);

        return jeet;
    }

    @SuppressLint("ResourceAsColor")
    public  Bitmap audioImage(String data,int ac){
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        byte[] art;
        ImageView audioView1 = null;
        Bitmap songImage = null;



        try {
            if(ac==0) {

                mediaMetadata.setDataSource(data);
                art = mediaMetadata.getEmbeddedPicture();
                songImage = BitmapFactory.decodeByteArray(art, 0, art.length);

            }
            else if(ac==2) {
                mediaMetadata.setDataSource(data);
                art = mediaMetadata.getEmbeddedPicture();
                songImage= BitmapFactory.decodeByteArray(art, 0, art.length);

                songImage=imageSlect(songImage);
            }



        }catch (Exception e){

            //audioView1.setBackgroundColor(R.color.fab_material_amber_500);
            songImage= BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.asdfss);
        }
        return songImage;
    }

    public  Bitmap audioImage(String data){

        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        byte[] art;
        ImageView audioView1 = null;
        Bitmap songImage = null;



        try {
            mediaMetadata.setDataSource(data);
            art=mediaMetadata.getEmbeddedPicture();
            songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            if (songImage==null){
                mainActivity.imageView.setImageResource(R.drawable.fullscreenim);
                mainActivity.bottomSheetImageView.setImageResource(R.drawable.fullscreenim);
            }

        }catch (Exception e){

            //audioView1.setBackgroundColor(R.color.fab_material_amber_500);
            songImage= BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.nightclub_w);
        }
        return  songImage;
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


    public String dureTionTwo(int d){
        double a= (d/1000.0)/60.0;
        String string = String.valueOf(a);

        String[] parts = string.split("\\.");

        String minutes = parts[0];
        int second = (d/1000)%60;
        //Toast.makeText(mainActivity.context," song"+second, Toast.LENGTH_SHORT).show();
       /*
         // 004
       // 034556
       // a =Double.valueOf(part2);
       // a=a*60;

        String string2 = String.valueOf(second);
        //String[] parts2 = string2.split("\\.");
        //part2=parts2[0];

            string = minutes + ":" + second;

        int f=Integer.valueOf(string);
        //  songDuretion.setText(string);*/
        return string;
    }
    @SuppressLint("ResourceAsColor")
    public  Bitmap imageSlect(Bitmap b){
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        byte[] art;
        ImageView audioView1 = null;
        Bitmap songImage = null;
        Bitmap bitmapResult=null;
        int w = b.getWidth();
        int h = b.getHeight();
        RectF rectF = new RectF(w/4, h/4, w*3/4, h*3/4);
        float blurRadius = 100.0f;



        try {
            // mediaMetadata.setDataSource(data);
            // art=mediaMetadata.getEmbeddedPicture();
            //  songImage = BitmapFactory.decodeByteArray(art, 10, art.length);

            //layout.setBackgroundColor(R.color.fab_material_amber_500);
            bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvasResult = new Canvas(bitmapResult);

            Paint blurPaintOuter = new Paint();
            blurPaintOuter.setColor(0xFFffffff);
            blurPaintOuter.setMaskFilter(new
                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.INNER));
            canvasResult.drawBitmap(b, 0, 0, blurPaintOuter);
            Paint blurPaintInner = new Paint();
            blurPaintInner.setColor(0xFFffffff);
            blurPaintInner.setMaskFilter(new
                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
            canvasResult.drawRect(rectF, blurPaintInner);


        }catch (Exception e){
            //audioView1.setBackgroundColor(R.color.fab_material_amber_500);
            songImage= BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.nightclub_w);
        }
        return bitmapResult ;
    }






    public void palay1( int po){
        String media_path = null;
        posis1=po;



        String nameSong= (String) audioList.get(po).getDescription().getTitle();
        // Toast.makeText(context, "  nameSong= "+nameSong, Toast.LENGTH_SHORT).show();
        //songName= (TextView) viewsList[4];
       // mainActivity.songName.setText(songName(nameSong));
        // String nameArtist=String.valueOf();
    //    mainActivity.bottomSheetText.setText((String) audioList.get(po).getDescription().getTitle());
        //  String nameDuretion =  audioList.get(po).getQueryDuration();

        //  songDuretion= (TextView) viewsList[6];
        //mainActivity.songDuretion.setText(dureTion(Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))));
        //seekBar= (SeekBar) viewsList[8];
        MediaControllerCompat.getMediaController(getActivity()).getTransportControls().playFromMediaId(audioList.get(po).getMediaId(),null);






        //buttonback.setClickable(true);
        // buttonnext.setClickable(true);


    }

    public void seekUpdation() {
        String j;
        murrentPosition = mMediaPlayer.getCurrentPosition();
        //seekBar= (SeekBar) viewsList[8];
        //seekDuretoin1= (TextView) viewsList[7];
        mainActivity.seekBar.setProgress(murrentPosition);

        //j=String.valueOf(dureTion(murrentPosition));
        mainActivity.seekDuretoin1.setText(dureTion(murrentPosition));

         handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(mainActivity.context," songDuretion" , Toast.LENGTH_SHORT).show();
                seekUpdation();
                // dureTion(mMediaPlayer.getCurrentPosition());
            }
        },1000);



    }

    public void isStop(){

        mMediaPlayer.stop();
        count--;

    }
    public void  isPlay() throws IOException {

        mMediaPlayer.prepare();
        mMediaPlayer.start();
        mMediaPlayer.isPlaying();
        mMediaPlayer.isLooping();
    }












    public void notPlay(int po2){

        String media_path = String.valueOf(CommonModel.getFilePath(String.valueOf(po2)));
        Bitmap bit = audioImage(media_path);
        if(bit!=null) {
            mainActivity.imageView.setImageBitmap(bit);
            mainActivity.bottomSheetImageView.setImageBitmap(bit);
        }
        else {

            mainActivity.imageView.setImageResource(R.drawable.fullscreenim);
            mainActivity.bottomSheetImageView.setImageResource(R.drawable.fullscreenim);
        }
        String nameSong=String.valueOf(audioList.get(po2).getDescription().getTitle());
        nameSong=songName(nameSong);
       // mainActivity.songName.setText(nameSong );
       // mainActivity.bottomSheetText.setText(nameSong);
        String nameArtist=String.valueOf(audioList.get(po2).getDescription().getSubtitle());
        // artistName.setText(arttistName(nameArtist));
        String nameDuretion;//=String.valueOf(itemLists.get(po2).getQueryDuration());

        // Toast ad =      Toast.makeText(context,""+posis1,Toast.LENGTH_LONG);
        //ad.show();

        //
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Toast.makeText(view.getContext(),jee + "  hii", Toast.LENGTH_SHORT).show();
        try {




            //  mediaPlayer.stop();
            int a;
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(media_path);
            mMediaPlayer.prepare();
            mainActivity.seekBar.setProgress(0);
            a=mMediaPlayer.getDuration();
            mainActivity.seekDuretoin1.setText(String.valueOf("0:0"));
            mainActivity.seekBar.setMax(mMediaPlayer.getDuration());
            resumePosition=mMediaPlayer.getCurrentPosition();
            nameDuretion=dureTion(a);
            mainActivity.songDuretion.setText(nameDuretion);





        } catch (IOException e) {




        }catch (Exception e){

        }
    }

    public ArrayList<String> test(int z){
        ArrayList<String> filePath = null;
        for(int i=0;i<=coun;i++){
            filePath.set(i, (CommonModel.getFilePath(String.valueOf(i))));

        }


        return filePath;
    }
    public void globalAudio(View view){


        if (mMediaPlayer.isPlaying()) {
            // Toast.makeText(view.getContext(), "  pause  = "+stateOfplay, Toast.LENGTH_SHORT).show();
            // view.setBackgroundResource(R.drawable.playpnn);
            //stateOfplay=false;
             myMediaPlayer.pauseMedia();
            view.setBackgroundResource(R.drawable.exo_controls_play);
            //mainActivity.buttonplay.setBackground(mainActivity.getResources().getDrawable(R.drawable.playpnn));


        }
        else if(!mMediaPlayer.isPlaying())  {
            stateOfplay=true;
            //view.setBackgroundResource(R.drawable.pausepnn);
            view.setBackgroundResource(R.drawable.exo_controls_pause);
            //mainActivity.buttonplay.setBackground(mainActivity.getResources().getDrawable(R.drawable.pausepnn));
            //view.setBackgroundResource(R.drawable.playpnn);
            //  Toast.makeText(view.getContext(), "  resume = ", Toast.LENGTH_SHORT).show();
            myMediaPlayer.resumeMedia(resumePosition);

        }
        else if (mMediaPlayer==null) {


            Toast.makeText(view.getContext(), "  Hii Ashok  = ", Toast.LENGTH_SHORT).show();
        }

    }

    private class MyConnectionCallback extends MediaBrowserCompat.ConnectionCallback {

            @Override
            public void onConnected() {
                Log.e("AudioFrag","connected");
                Log.d("AudioFraf","Conecté al servicio");
                Log.d("AudioFrag","token:" + mediaBrowser.getSessionToken().toString());
                super.onConnected();
                Toast.makeText(getActivity().getBaseContext(), "connected", Toast.LENGTH_SHORT).show();

                try {
                    // Ah, here’s our Token again
                    token=mediaBrowser.getSessionToken();
                    // This is what gives us access to everything
                    controller = new MediaControllerCompat(getActivity() , token);
                    // Convenience method to allow you to use
                    // MediaControllerCompat.getMediaController() anywhere
                    MediaControllerCompat.setMediaController( getActivity(), controller);
                    buildTransportControls();
                    mediaBrowser.subscribe(getParentId(), new MySubscriptionCallback());

                   // mediaBrowser.subscribe(mediaBrowser.getRoot(),subscriptionCallback);



                } catch (RemoteException e) {
                    Log.e("AudioFrag",
                            "Error creating controller", e);
                }
            }
            @Override
            public void onConnectionSuspended() {
                super .onConnectionSuspended();
                Toast.makeText(getActivity().getBaseContext(), "connectsuspend", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onConnectionFailed() {
                super.onConnectionFailed();
                Toast.makeText(getActivity().getBaseContext(), "connectionFaile", Toast.LENGTH_SHORT).show();
            }

    }






    private class MySubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback {

            @Override
            public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
                super.onChildrenLoaded(parentId, children);
                for (MediaBrowserCompat.MediaItem item : children) {
                    Toast.makeText(getActivity().getBaseContext(), ""+item, Toast.LENGTH_SHORT).show();
                    //mBrowserAdapter.add(item);
                }
               // Toast.makeText(getActivity().getBaseContext(), "subscription", Toast.LENGTH_SHORT).show();

            }

    }
}
class MyMediaPlayer implements MediaPlayer.OnErrorListener,AudioManager.OnAudioFocusChangeListener,MediaPlayer.OnCompletionListener{
    private int mState;
    private boolean mPlayOnFocusGain;
    private volatile MediaMetadataCompat mCurrentMedia;
    private  AudioManager mAudioManager;
      Context context;
   private CallBackToService callBackToService;
   private  MediaPlayerService mediaPlayerService;
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
                mMediaPlayer.setDataSource(context.getApplicationContext(),Uri.parse(CommonModel.getFilePath(mediaId)) );
                mMediaPlayer.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
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