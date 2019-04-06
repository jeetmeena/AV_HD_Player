package com.example.geetmeena.music;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import com.melnykov.fab.FloatingActionButton;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.app.Notification;
import android.app.PendingIntent;
import static android.support.v7.media.MediaControlIntent.ACTION_STOP;
import static com.example.geetmeena.music.SolventRecyclerViewAdapter.mMediaPlayer;
public class FullScreen   extends AppCompatActivity implements FullScreen1, SeekBar.OnSeekBarChangeListener {
  private NotificationManager notificationManager;
          public    NotificationCompat.Builder builder;
          private int notification_id;
          public RemoteViews remoteViews;
          public Context context1;
        public  static MediaPlayer mediaPlayer=null;
        static int p=22222;
        private static int resumePosition;
    static  int unpliugged=0;
    public static final String NOTIFY_PREVIOUS = "com.example.geetmeena.music.previous";
    public static final String NOTIFY_DELETE = "com.example.geetmeena.music.delete";
    public static final String NOTIFY_PAUSE = "com.example.geetmeena.music.pause";
    public static final String NOTIFY_PLAY = "com.example.geetmeena.music.play";
    public static final String NOTIFY_NEXT = "com.example.geetmeena.music.next";
        private ArrayList<CommonModel> itemLists;
        public  ArrayList<String> j;
        private Context context;
        private  int posis;
        public static   int posis1;
        private AttributeSet atrr;
        private FloatingActionButton fab2;
        public static   int count=0;
        public static String perpath=null;
        public Uri newUei;
        Button buttonplay;
        Button buttonnext;
        Button buttonback;
     Button notibuttonplay;
     Button notibuttonnext;
     Button notbuttonback;
        Button shuffle;
        TextView songName;
       public  TextView seekDuretoin1;
        TextView songDuretion;
        TextView artistName;
        ImageView imageView;
        SeekBar seekBar;
        Bundle bundle;
        String  posi1;
        public  Runnable run;
         Handler handler;
         public  int lastIndex;
         public static   String next;
          static   int murrentPosition;
            static boolean onOff;
            public  static  int rean;
            static  int rand_int1;
            static ArrayList<Integer> selected=new ArrayList<Integer>();
          public  static String PREF_FILE_NAME="JeetFirst2";
            int a;
            int selectedLastInd;
        MainActivity mainActivity =new MainActivity();
        public int listCount = 0;
        @SuppressLint("RestrictedApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_full_screen);

            Intent previous = new Intent(NOTIFY_PREVIOUS);
            Intent delete = new Intent(NOTIFY_DELETE);
            Intent pause = new Intent(NOTIFY_PAUSE);
            Intent next = new Intent(NOTIFY_NEXT);
            Intent play = new Intent(NOTIFY_PLAY);

            buttonplay=findViewById(R.id.play1);
            buttonnext=findViewById(R.id.next);
            buttonback=findViewById(R.id.back);

            imageView=findViewById(R.id.image2);
            songName=findViewById(R.id.textView);
            artistName=findViewById(R.id.textView2);
            songDuretion=findViewById(R.id.textView3);
            seekDuretoin1=findViewById(R.id.textView4);
            seekBar=findViewById(R.id.seekBar);

            seekBar.setOnSeekBarChangeListener(this);
            buttonplay.setBackgroundResource(R.drawable.pausepnn);
            Toolbar toolbar=findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
            context1=this;


            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            remoteViews=new RemoteViews(context1.getPackageName(),R.layout.notification);
            remoteViews.setImageViewResource(R.id.image,R.mipmap.ic_launcher);

            remoteViews.setTextViewText(R.id.nsongName,"JDSJFHH");
            notification_id= (int) System.currentTimeMillis();
            builder=new NotificationCompat.Builder(context1);

         // remoteViews.setOnClickPendingIntent(R.id.nplay,p_button_intent);


            Intent button_intent=new Intent(context1,FullScreen.class);

            button_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            button_intent.putExtra("id",notification_id);

            try {
                PendingIntent pendingIntent=PendingIntent.getActivity(context1,0,button_intent,PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setCustomBigContentView(remoteViews);
                builder.setContentIntent(pendingIntent);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setAutoCancel(true);
                builder.setContentTitle("Music Player");
                builder.setContentText("Control Audio");
                builder.getBigContentView().setTextViewText(R.id.nsongName,"Jeet");


             //  startForegroundService();
                setListeners(remoteViews,context1);
                //sendBroadcast(button_intent);

               // startForeground(notification_id, notification);
            //    PendingIntent pendingIntent=PendingIntent.getBroadcast(context1,123,button_intent,0);
            }
            catch(Exception e){}
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

            Intent intent = getIntent();

           // j=intent.getStringArrayListExtra("4");

            try {
                mediaPlayer=SolventRecyclerViewAdapter.mMediaPlayer;
                mediaPlayer.stop();
                mediaPlayer.release();
                a = intent.getIntExtra("jeet5", 0);

            }catch (Exception e){}
            itemLists =   mainActivity.gaggeredList;
           //selected=itemListSelect;
           //selectedLastInd=solventRecyclerViewAdapter.j;
            posis1=posis;
            if(a==1){
                selected=intent.getIntegerArrayListExtra("jeet6");
                selectedLastInd=intent.getIntExtra("jeet7",4);
               posis1= 0;
               palay(posis1);
            }
            else {
                posis=intent.getIntExtra("jeet2",12);
                lastIndex=intent.getIntExtra("jeet3",0);
                palay(posis);
            }


            onOff=true;

          rean=0;
                //  j=intent.getStringArrayListExtra("jeet3");
               //posi1=intent.getStringExtra("jeet4");

        }




    public void setListeners(RemoteViews view, Context context) {
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);
        Toast tost=Toast.makeText(FullScreen.this,"Headset is unplugged",Toast.LENGTH_SHORT);
        tost.show();
        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.nback, pPrevious);

        // PendingIntent pDelete = PendingIntent.getBroadcast(getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
        // view.setOnClickPendingIntent(R.id.btnDelete, pDelete);

        //PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        //view.setOnClickPendingIntent(R.id.btnPause, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.nnext, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.nplay,pPlay);
        Log.e("Error23","hii ");
        builder.addAction(R.drawable.backpnn,"back",pPrevious);
        builder.addAction(R.drawable.nextpnn,"next",pNext);
        builder.addAction(R.drawable.pausepnn,"play",pPlay);
        builder.build();

        notificationManager.notify(notification_id,builder.build());
    }


     public class MusickNotification extends BroadcastReceiver {
         final PendingResult pendingResult = goAsync();
         @Override
         public void onReceive(Context context, Intent intent) {
             Log.e("Error","hii ");
             if(intent.getAction().equals(FullScreen.NOTIFY_PLAY)){

                 Toast.makeText(context, "Button_clicked=11", Toast.LENGTH_SHORT).show();

             }

        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(intent.getExtras().getInt("id"));
            Toast.makeText(context, "Button_clicked=12", Toast.LENGTH_SHORT).show();

        }catch (Exception e){}



         }


     }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
     final MainActivity mainActivity2=MainActivity.getInstance();

        if(a==1) {


       String string=   readFromPerferences(FullScreen.this,"listCount","0");
        try {
             listCount=Integer.valueOf(string);

        if(listCount<5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setTitle("Music");
            builder.setMessage("Save Song As Favourite  PlayList");
            builder.setIcon(R.mipmap.musiclogo);

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //mainActivity2.editor.putString("jeet2","hiiiiiiii");
                    // User clicked OK button
                    /* Specify the size of the list up front to prevent resizing. */

                    ArrayList<String> newList = new ArrayList<String>(selected.size());
                    for (Integer myInt : selected) {
                        newList.add(String.valueOf(myInt));
                    }

                    mainActivity2.saveArrayList(newList, "key", listCount);
                    final int finalListCount = listCount+1;

                   String string =String.valueOf(finalListCount);
                 /*   Intent intent = new Intent(FullScreen.this, MainActivity.class);

                    intent.putExtra("selected", selected);
                    intent.putExtra("key", a);
                    startActivity(intent);
                    Toast toast = Toast.makeText(FullScreen.this, "bact presse", Toast.LENGTH_SHORT);
                    toast.show();*/
                 saveToPerferences(FullScreen.this,"listCount",string);

                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // User cancelled the dialog
                }
            });
            // Set other dialog properties


            // Create the AlertDialog

            builder.show();
            mediaPlayer.stop();
            a = 0;
        }

        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setTitle("Music");
            builder.setMessage("Remove Save List From Drawer");
            builder.setIcon(R.mipmap.musiclogo);


            // Set other dialog properties


            // Create the AlertDialog

            builder.show();
            mediaPlayer.stop();
            a = 0;
        }
        }catch (Exception e){}
        }
        else if (!mediaPlayer.isPlaying()) {
            mMediaPlayer = mediaPlayer;
            super.onBackPressed();
        } else if (mediaPlayer.isPlaying()) {
            mMediaPlayer = mediaPlayer;
            mMediaPlayer.start();
            super.onBackPressed();
        }

    }

    public void Back(View v) {
        onBackPressed();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.home){
            mMediaPlayer=mediaPlayer;
            mediaPlayer.stop();

        }

        return super.onOptionsItemSelected(item);
    }





        public String songName(String name){
            String jeet = null;

            char[] namea=name.toCharArray();

            if(namea.length>=51){
                name=name.substring(0,50);
                jeet=new String(name)+"..";
            }
            else {
                jeet = new String(name);
            }
            return jeet;
        }
        public String arttistName(String s){
            String art=null;
            char[] artt=s.toCharArray();
            if(artt.length>=23){
                s=s.substring(0,22);
                art=new String(s)+"..";
            }
            else {
                art = new String(s);
            }

            return art;
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
                   imageView.setImageResource(R.drawable.fullscreenim);
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
          String part1 = parts[0]; // 004
          String part2 = "0."+parts[1]; // 034556
          a =Double.valueOf(part2);
          a=a*60;

          String string2 = String.valueOf(a);
          String[] parts2 = string2.split("\\.");
          part2=parts2[0];
          if (part2.length()>2){

            string=part1+":"+part2.substring(0,2);
          }
          else {
              string = part1 + ":" + part2;
          }

          return string;
      }


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if(fromUser) {

                mediaPlayer.seekTo(progress);
                resumePosition=progress;
                next=String.valueOf(dureTion(progress));
                seekDuretoin1.setText(next);
        }
            }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Toast.makeText(FullScreen.this,"seekbar touch stopped!", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }



        public void palay( int po){
            String media_path = null;

            try {

                if (a == 1) {
                    int f = selected.get(po);
                    media_path = String.valueOf(itemLists.get(f).getFilePath1());
                }
                else {
                    media_path = String.valueOf(itemLists.get(po).getFilePath1());
                }


            }catch (Exception e){

                
            }
            Bitmap bit = audioImage(media_path);
            try {
                if (bit != null) {
                    imageView.setImageBitmap(bit);
                } else  {
                    imageView.setImageResource(R.drawable.fullscreenim);

                }
            }catch (Exception e){}

          String nameSong=String.valueOf(itemLists.get(po).getQueryName());
             songName.setText(songName(nameSong));
            String nameArtist=String.valueOf(itemLists.get(po).getQueryArtist());
           // artistName.setText(arttistName(nameArtist));
            String nameDuretion=String.valueOf(itemLists.get(po).getQueryDuration());

             mediaPlayer = new MediaPlayer();
             try {
                 int a=Integer.valueOf(nameDuretion);
                 nameDuretion=dureTion(a);
             }catch (Exception a){

             }

            //
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            songDuretion.setText(nameDuretion);
             // Toast.makeText(view.getContext(),jee2 + "  hii", Toast.LENGTH_SHORT).show();
            try {


                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    mediaPlayer.setDataSource(media_path);
                    mediaPlayer.prepare();

                    mediaPlayer.start();
                   seekBar.setMax(mediaPlayer.getDuration());
                   seekUpdation();

                }

                else if(!mediaPlayer.isPlaying()){


                    mediaPlayer.setDataSource(media_path);
                    mediaPlayer.prepare();

                    mediaPlayer.start();
                    mediaPlayer.isPlaying();

                    // mediaPlayer.isLooping();
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekUpdation();

                } else if(mediaPlayer==null) {
                    //  mediaPlayer.stop();
                    try{mediaPlayer.stop();
                        mediaPlayer.release();}catch(Exception e){}
                    mediaPlayer.setDataSource(media_path);
                    mediaPlayer.prepare();

                    mediaPlayer.start();
                    mediaPlayer.isPlaying();

                    // mediaPlayer.isLooping();
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekUpdation();

                }




            } catch (IOException e) {
                //  mediaPlayer[0].reset();
                // mediaPlayer[0].release();
                //  mediaPlayer[0] = null;



            }catch (Exception e){
            }

        buttonback.setClickable(true);
            buttonnext.setClickable(true);


        }


           public void seekUpdation() {
               String j;
               murrentPosition = mediaPlayer.getCurrentPosition();

               seekBar.setProgress(murrentPosition);

                j=String.valueOf(dureTion(murrentPosition));
               seekDuretoin1.setText(j);

                   Handler handler = new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                               @Override
                               public void onCompletion(MediaPlayer mp) {
                                   if(a==1){
                                       if (rean == 0) {
                                           int a = posis1 + 1;
                                           posis1 = a;
                                           int b=selected.size();

                                               if (posis1+1>=b) {
                                                   posis1=0;
                                                  palay(posis1);


                                               }
                                               else {
                                                   Toast ad =      Toast.makeText(FullScreen.this,"sfsff",Toast.LENGTH_LONG);
                                                   ad.show();
                                                    palay(a);
                                               }





                                       }

                                   }
                                   else {
                                       if (rean == 0) {
                                           int a = posis1 + 1;
                                           posis1 = a;

                                           int b=itemLists.size();


                                           if (posis1+1>=b) {
                                               posis1=0;
                                               palay(posis1);


                                           }
                                            else {

                                                 palay(a);
                                           Toast ad =      Toast.makeText(FullScreen.this,"sfsff12",Toast.LENGTH_LONG);
                                           ad.show();
                                            }


                                       }
                                   }
                               }
                           });
                           seekUpdation();

                       }
                   },1000);



            }





       @Override
        public void onClick(View view) {
           if (view.getId() == R.id.play1) {

                    if (mediaPlayer.isPlaying()) {
                        buttonplay.setBackgroundResource(R.drawable.playpnn);
                        pauseMedia();
                       p=posis1;

                    } else if (!mediaPlayer.isPlaying()) {
                        if(p==posis1) {
                            buttonplay.setBackgroundResource(R.drawable.pausepnn);
                            resumeMedia();
                        }
                        else {
                            buttonplay.setBackgroundResource(R.drawable.pausepnn);
                            palay(posis1);
                        }


                    }
                    Toast.makeText(view.getContext(), "  Hii Ashok  = ", Toast.LENGTH_SHORT).show();

                } else if (view.getId() == R.id.next) {
                    if(rean==0) {
                        moveNext();

                    }
                    else if(rean==1){

                        try {

                        }catch(Exception e){}
                    }

                } else if (view.getId() == R.id.back) {
                    if(rean==0) {
                        movePerv();

                    }
                    else  if(rean==1){
                        try {

                        }catch(Exception e){}

                    }
                }






        }




        private void pauseMedia() {
            if (mediaPlayer.isPlaying()) {

                mediaPlayer.pause();
                resumePosition = mediaPlayer.getCurrentPosition();

            }
        }
    private void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();

         }
        }
        private void movePerv(){
            int g;
            int ss=selected.size();
            int s=itemLists.size();
            if(a==1){
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    g= posis1 - 1;
                    posis1 = g;
                    if (posis1 >=0) {
                        palay(g);
                    } else if (posis1 <0) {
                        posis1 = ss-1;
                        palay(posis1);


                    }

                } else if (!mediaPlayer.isPlaying()) {
                    g = posis1 + 1;
                    posis1 = g;

                    if (g >=0) {
                        notPlay(g);
                    } else if (g <0) {
                        posis1 = ss-1;
                        notPlay(posis1);


                    }
                }
            }
            else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    g= posis1 - 1;
                    posis1 = g;
                    if (posis1 >=0) {
                        palay(g);
                    } else if (posis1 <0) {
                        posis1 = s-1;
                        palay(posis1);


                    }

                } else if (!mediaPlayer.isPlaying()) {
                    g = posis1 + 1;
                    posis1 = g;

                    if (g >=0) {
                        notPlay(g);
                    } else if (g <0) {
                        posis1 = s-1;
                        notPlay(posis1);


                    }
                }
            }
        }
        private void moveNext(){
            int b;
            int ss=selected.size();
            int s=itemLists.size();
            if(a==1){
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    b = posis1 + 1;
                    posis1 = b;
                    if (posis1 <ss) {


                        palay(b);
                    } else if (posis1 >= ss) {
                        posis1 = 0;
                        palay(posis1);


                    }

                } else if (!mediaPlayer.isPlaying()) {
                    b = posis1 + 1;
                    posis1 = b;

                    if (b < ss) {
                        notPlay(b);
                    } else if (b >= ss) {
                        posis1 = 0;
                        notPlay(posis1);


                    }
                }
            }
            else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    b = posis1 + 1;
                    posis1 = b;
                    if (posis1 < s) {
                        palay(b);
                    } else if (posis1 >= s) {
                        posis1 = 0;
                        palay(posis1);


                    }

                } else if (!mediaPlayer.isPlaying()) {
                    b = posis1 + 1;
                    posis1 = b;

                    if (b < s) {
                        notPlay(b);
                    } else if (b >= s) {
                        posis1 = 0;
                        notPlay(posis1);


                    }
                }
            }
        }







        public void notPlay(int po2){

            String media_path = String.valueOf(itemLists.get(po2).getFilePath1());
            Bitmap bit = audioImage(media_path);
            if(bit!=null) {
                imageView.setImageBitmap(bit);
            }
            else {

                imageView.setImageResource(R.drawable.fullscreenim);
            }
            String nameSong=String.valueOf(itemLists.get(po2).getQueryName());
            songName.setText(songName(nameSong));
            String nameArtist=String.valueOf(itemLists.get(po2).getQueryArtist());
            // artistName.setText(arttistName(nameArtist));
            String nameDuretion;//=String.valueOf(itemLists.get(po2).getQueryDuration());

            Toast ad =      Toast.makeText(FullScreen.this,""+posis1,Toast.LENGTH_LONG);
            ad.show();

            //
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // Toast.makeText(view.getContext(),jee2 + "  hii", Toast.LENGTH_SHORT).show();
            try {




                    //  mediaPlayer.stop();
                     int a;
                     mediaPlayer.reset();
                    mediaPlayer.setDataSource(media_path);
                    mediaPlayer.prepare();
                     seekBar.setProgress(0);
                     a=mediaPlayer.getDuration();
                     seekDuretoin1.setText(String.valueOf("0:0"));
                     seekBar.setMax(mediaPlayer.getDuration());
                    resumePosition=mediaPlayer.getCurrentPosition();
                     nameDuretion=dureTion(a);
                     songDuretion.setText(nameDuretion);





            } catch (IOException e) {




            }catch (Exception e){

            }
        }

    public static void saveToPerferences(Context context, String perferencesName, String perferencesValue){
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(perferencesName,perferencesValue);
        editor.apply();


    }

    public static String readFromPerferences(Context context,String perferencesName,String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(perferencesName,defaultValue);
    }


        public void teSt(){Toast.makeText(FullScreen.this, "Clicke 3 = " , Toast.LENGTH_SHORT).show();}




    }
