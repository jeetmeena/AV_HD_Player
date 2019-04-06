package com.example.geetmeena.music;

/**
 * Created by Jeetmeena on 1/19/2018.
 */
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;


public class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventRecyclerViewAdapter.SolventViewHolders> implements SolventRecyclerViewAdaptera,
        SeekBar.OnSeekBarChangeListener,View.OnClickListener {
public static MediaPlayer mMediaPlayer;
    static  private int resumePosition;
    MainActivity mainActivity = new MainActivity();
    public  ArrayList<CommonModel> itemList;
    public static ArrayList<Integer> itemListSelect=new ArrayList<Integer>();
    static boolean stateOfplay=true;
    int coun=0;
    Context context;
  static  Bitmap blurred= null;
    public  static  int sele=0;
   static String ab;
  //public   static  int j=0;
  View bv;
    static  RelativeLayout layout;
       View layoutView;
   static Bitmap finalBitma = null;
    static byte[] artt;
    static Bitmap longClick = null;
    Bitmap bitmapMaster;
    Canvas canvasMaster;
    private AttributeSet atrr;

       int count=0;
    public static String perpath=null;
    Button fabCancel;
    Button fab;
    static  View viewfab;
    MediaMetadataRetriever mediaMetadataa = new MediaMetadataRetriever();
   public Uri newUei;
   static int vie=0;



   public  ArrayList<String> j;
    //private Context context;

    public static   int posis1=0;

    private com.melnykov.fab.FloatingActionButton fab2;


    //public Uri newUei;
   public Button buttonplay;
   public Button buttonnext;
   public Button buttonback;
    public TextView songName;
    public  TextView seekDuretoin1;
    public TextView songDuretion;
    public TextView artistName;
    public ImageView imageView;
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
    int selectedLastInd;
    public View[] viewsList;
    ArrayList<ViewReso> aaaID=null;

    View views;
     //int a=2;

    public SolventRecyclerViewAdapter(View v) {
       onClick( v);
    }

    public SolventRecyclerViewAdapter(Context baseContext, ArrayList<CommonModel> audioList,ArrayList<ViewReso> aaaID) {
        this.itemList = audioList;
        this.context = baseContext;
        this.aaaID=aaaID;


    }


    @NonNull
    @SuppressLint("ResourceType")
    @Override
    public SolventViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenaa_main,parent,false);

        views=  LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet,null);
        //fab2.show();

        SolventViewHolders rcv = new SolventViewHolders(layoutView,itemList,views);


        return rcv;
    }

    private void getResurs(){

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        // views.setOnClickListener(this);
        //View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

       if(views!=null){
           Log.e("TAG","RECS");
           buttonplay=  views.findViewById(R.id.play1);
           buttonnext= views.findViewById(R.id.next);
           buttonback= views.findViewById(R.id.back);
           imageView= views.findViewById(R.id.image2);
           Toast.makeText(context,"AA"+views, Toast.LENGTH_SHORT).show();
           songName= views.findViewById(R.id.textView);
           String s="giu";
           songName.setText(s);
           imageView.setImageResource(R.drawable.ashok_galaxyn);
           artistName= views.findViewById(R.id.textView2);
           songDuretion= views.findViewById(R.id.textView3);
           songDuretion.setText("00.0");
           seekDuretoin1=views.findViewById(R.id.textView4);
           seekBar= views.findViewById(R.id.seekBar);

           seekBar.setOnSeekBarChangeListener(this);
           buttonplay.setBackgroundResource(R.drawable.pausepnn);
           buttonplay.setOnClickListener(this);
           buttonback.setOnClickListener(this);
           buttonnext.setOnClickListener(this);

       }



    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SolventViewHolders holder, int position) {

        ImageView audioView2 = null;


        try {



            //  view.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
            Bitmap bit=audioImage(itemList.get(position).getFilePath1(),0);
            String nam=name(itemList.get(position).getQueryName());

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

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Clicke 2 = " , Toast.LENGTH_SHORT).show();
                    }
                });

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



    }


    public  void floaTing(){


    }



    public Uri newUrie(Uri uri){

        return uri;
    }

    @Override
    public int getItemCount() {
        // getResurs(viewsList);
        //returns the number of elements the RecyclerView will display
        return this.itemList.size();
    }
    public void selectedItem(){






        }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, CommonModel data) {
        itemList.add(position, data);

        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(CommonModel data) {
        int position = itemList.indexOf(data);
        itemList.remove(position);
        notifyItemRemoved(position);
    }




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
        int f=Integer.valueOf(string);
      //  songDuretion.setText(string);
        return string;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if(fromUser) {

            mMediaPlayer.seekTo(progress);
            resumePosition=progress;
            next=String.valueOf(dureTion(progress));
            //seekDuretoin1.setText(next);
            Toast.makeText(context,"seek", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
     //   Toast.makeText(context,"seekbar touch stopped!", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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




    @SuppressLint("ResourceAsColor")
    public MediaPlayer palay(int index){

         final
        String media_path = String.valueOf(itemList.get(index).getFilePath1());

         mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        // Toast.makeText(view.getContext(),jee2 + "  hii", Toast.LENGTH_SHORT).show();
        try {




              if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();

                  mMediaPlayer.setDataSource(media_path);
                  mMediaPlayer.prepare();
                mMediaPlayer.start();




            }
      else if(!mMediaPlayer.isPlaying() || mMediaPlayer==null) {
                  mMediaPlayer.stop();
                  mMediaPlayer.setDataSource(media_path);


           perpath=media_path;
           mMediaPlayer.prepare();
           mMediaPlayer.start();
           mMediaPlayer.isPlaying();

       }

        } catch (IOException e) {
            //  mediaPlayer[0].reset();
            // mediaPlayer[0].release();
            //  mediaPlayer[0] = null;



           }catch (Exception e){
          }
         return  mMediaPlayer;
      }


    public void palay1( int po){
        String media_path = null;
        posis1=po;

        try {



                media_path = itemList.get(po).getFilePath1();



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

        String nameSong= itemList.get(po).getQueryName();
      //  Toast.makeText(context, "  nameSong= "+nameSong, Toast.LENGTH_SHORT).show();
        //songName= (TextView) viewsList[4];
        songName.setText(songName(nameSong));
        String nameArtist=String.valueOf(itemList.get(po).getQueryArtist());
        // artistName.setText(arttistName(nameArtist));
        String nameDuretion=String.valueOf(itemList.get(po).getQueryDuration());

        mMediaPlayer = new MediaPlayer();
        try {
            int a=Integer.valueOf(nameDuretion);
            nameDuretion=dureTion(a);
        }catch (Exception a){

        }

        //
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      //  songDuretion= (TextView) viewsList[6];
       songDuretion.setText(nameDuretion);
       //seekBar= (SeekBar) viewsList[8];
        //    Toast.makeText(context," songDuretion"+nameDuretion, Toast.LENGTH_SHORT).show();
        try {



            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();

                mMediaPlayer.setDataSource(media_path);
                mMediaPlayer.prepare();

                mMediaPlayer.start();
               seekBar.setMax(mMediaPlayer.getDuration());
               seekUpdation();

            }

            else if(!mMediaPlayer.isPlaying()){


                mMediaPlayer.setDataSource(media_path);
                mMediaPlayer.prepare();

                mMediaPlayer.start();
                mMediaPlayer.isPlaying();

                // mediaPlayer.isLooping();
                seekBar.setMax(mMediaPlayer.getDuration());
                seekUpdation();

            } else if(mMediaPlayer==null) {
                //  mediaPlayer.stop();
                try{mMediaPlayer.stop();
                    mMediaPlayer.release();}catch(Exception e){}
                mMediaPlayer.setDataSource(media_path);
                mMediaPlayer.prepare();

                mMediaPlayer.start();
                mMediaPlayer.isPlaying();

                // mediaPlayer.isLooping();
               seekBar.setMax(mMediaPlayer.getDuration());
                seekUpdation();

            }




        } catch (IOException e) {
            //  mediaPlayer[0].reset();
            // mediaPlayer[0].release();
            //  mediaPlayer[0] = null;



        }catch (Exception e){
        }

        //buttonback.setClickable(true);
       // buttonnext.setClickable(true);


    }

    public void seekUpdation() {
        String j;
        murrentPosition = mMediaPlayer.getCurrentPosition();
        //seekBar= (SeekBar) viewsList[8];
        //seekDuretoin1= (TextView) viewsList[7];
        seekBar.setProgress(murrentPosition);

        j=String.valueOf(dureTion(murrentPosition));
        seekDuretoin1.setText(j);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {


                            if (rean == 0) {
                                int a = posis1 + 1;
                                posis1 = a;

                                int b=itemList.size();


                                if (posis1+1>=b) {
                                    posis1=0;
                                    palay1(posis1);


                                }
                                else {

                                    palay1(a);
                                //    Toast ad =      Toast.makeText(context,"sfsff12",Toast.LENGTH_LONG);
                                    //ad.show();
                                }


                            }

                    }
                });
                seekUpdation();

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









    private void playMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void stopMedia() {
        if (mMediaPlayer == null) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    private void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            //Toast.makeText(context, "  pause2  = "+stateOfplay, Toast.LENGTH_SHORT).show();
            resumePosition = mMediaPlayer.getCurrentPosition();
        }
    }

    private void resumeMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(resumePosition);
            mMediaPlayer.start();
        }
    }




    private void movePerv(){
        int g;
        int ss=selected.size();
        int s=itemList.size();


            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                g= posis1 - 1;
                posis1 = g;
                if (posis1 >=0) {
                    palay1(g);
                } else if (posis1 <0) {
                    posis1 = s-1;
                    palay1(posis1);


                }

            } else if (!mMediaPlayer.isPlaying()) {
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
    private void moveNext(){
        int b;
        int ss=selected.size();
        int s=itemList.size();


            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                b = posis1 + 1;
                posis1 = b;
                if (posis1 < s) {
                    palay1(b);
                } else if (posis1 >= s) {
                    posis1 = 0;
                    palay1(posis1);


                }

            } else if (!mMediaPlayer.isPlaying()) {
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


    public void notPlay(int po2){

        String media_path = String.valueOf(itemList.get(po2).getFilePath1());
        Bitmap bit = audioImage(media_path);
        if(bit!=null) {
            imageView.setImageBitmap(bit);
        }
        else {

            imageView.setImageResource(R.drawable.fullscreenim);
        }
        String nameSong=String.valueOf(itemList.get(po2).getQueryName());
        songName.setText(songName(nameSong));
        String nameArtist=String.valueOf(itemList.get(po2).getQueryArtist());
        // artistName.setText(arttistName(nameArtist));
        String nameDuretion;//=String.valueOf(itemLists.get(po2).getQueryDuration());

        Toast ad =      Toast.makeText(context,""+posis1,Toast.LENGTH_LONG);
        ad.show();

        //
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Toast.makeText(view.getContext(),jee2 + "  hii", Toast.LENGTH_SHORT).show();
        try {




            //  mediaPlayer.stop();
            int a;
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(media_path);
            mMediaPlayer.prepare();
            seekBar.setProgress(0);
            a=mMediaPlayer.getDuration();
            seekDuretoin1.setText(String.valueOf("0:0"));
            seekBar.setMax(mMediaPlayer.getDuration());
            resumePosition=mMediaPlayer.getCurrentPosition();
            nameDuretion=dureTion(a);
            songDuretion.setText(nameDuretion);





        } catch (IOException e) {




        }catch (Exception e){

        }
    }

    public ArrayList<String> test(int z){
         ArrayList<String> filePath = null;
         for(int i=0;i<=coun;i++){
           filePath.set(i, (itemList.get(i).getFilePath1()));

         }


return filePath;
   }
    public void globalAudio(View view){


        if (mMediaPlayer.isPlaying()) {
           // Toast.makeText(view.getContext(), "  pause  = "+stateOfplay, Toast.LENGTH_SHORT).show();
            view.setBackgroundResource(R.drawable.playpnn);
            //stateOfplay=false;
            pauseMedia();






        }
        else if(!mMediaPlayer.isPlaying())  {
            stateOfplay=true;
            view.setBackgroundResource(R.drawable.pausepnn);
            //view.setBackgroundResource(R.drawable.playpnn);
          //  Toast.makeText(view.getContext(), "  resume = ", Toast.LENGTH_SHORT).show();
            resumeMedia();

        }
        else if (mMediaPlayer==null) {


            Toast.makeText(view.getContext(), "  Hii Ashok  = ", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play1:
                if (mMediaPlayer.isPlaying()) {
                  //  Toast.makeText(v.getContext(), "  pause  = "+stateOfplay, Toast.LENGTH_SHORT).show();
                    if(stateOfplay){
                        stateOfplay=false;
                        pauseMedia();


                    }



                }
                else if(!mMediaPlayer.isPlaying())  {
                    stateOfplay=true;
                    v.setBackgroundResource(R.drawable.pausepnn);
                    v.setBackgroundResource(R.drawable.playpnn);
                  //  Toast.makeText(v.getContext(), "  resume = ", Toast.LENGTH_SHORT).show();
                    resumeMedia();

                }

                //  Toast.makeText(view.getContext(), "  play", Toast.LENGTH_SHORT).show();
                break;
            case R.id.next:
                if(rean==0) {
                    moveNext();

                }
                else if(rean==1){

                    try {

                    }catch(Exception e){}
                }
               // Toast.makeText(v.getContext(), "  next", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                if(rean==0) {
                    movePerv();

                }
                else  if(rean==1){
                    try {

                    }catch(Exception e){}

                }
               // Toast.makeText(v.getContext(), "  back", Toast.LENGTH_SHORT).show();
                break;
            default: break;

        }

        //viewfab.setBackgroundResource(R.drawable.dpalyai);

    }


    public class SolventViewHolders extends RecyclerView.ViewHolder implements SolventViewHolders1{
        SeekBar seekBar;
        private TextView countryName;
        private ImageView countryPhoto;
       CoordinatorLayout coordinatorLayout;
        private String[] filePathStrings;
        private ArrayList<CommonModel> g;
        int i =0;
      // public GestureDetector gestureDetector;
        String jee2;
        TextView jee1;
        Button button;
        Annotation  anim;
      private SolventViewHolders(View itemView, ArrayList<CommonModel> jee, View aaaID) {

            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
                 g=jee;
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
            button=aaaID.findViewById(R.id.play1);
             jee1=aaaID.findViewById(R.id.textView);
             jee1.setText("dsffds");

           // FloatingActionButton fab = new FloatingActionButton.DragShadowBuilder().bulid();
           //coordinatorLayout = (CoordinatorLayout) itemView.findViewById(R.id.main_contents);
// The View with the BottomSheetBehavior

              //  gestureDetector = new GestureDetector(context1, new GestureListener());
            //

        }

        @Override
        public void onClick2(View view) {
           // jee2 = jee1.getText().toString();


           try {

           if (R.id.bottomPlay ==view.getId()) {
                   Toast.makeText(view.getContext(), "Clicked Position = " , Toast.LENGTH_SHORT).show();

               // media.stop();
             }
            } catch (Exception e) {

            }
        }

       @Override
       public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



               Toast.makeText(view.getContext(), "Clicke 4 = " , Toast.LENGTH_SHORT).show();

           return false;
       }
       @Override
       public void onLongClick(View view){
           Toast.makeText(view.getContext(), "Clicke 5 = " , Toast.LENGTH_SHORT).show();

       }

       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(final View v) {


           i++;
            final int ind = getPosition();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(i==1){

                        Toast.makeText(v.getContext(), "Clicke 1" , Toast.LENGTH_SHORT).show();
                        if(sele==0){
                            try {
                                mMediaPlayer.stop();
                            }catch (Exception e){}

                            palay1(ind);
                        }
                        else  if(sele==1){

                       // Bitmap b=  selecTed(ind);
                     //       layout=v.findViewById(R.id.relativpadding);
                       //     layout.setBackgroundColor(Color.RED);
                       // countryPhoto.setImageBitmap(b);
                            //countryPhoto.setBackgroundColor(Color.RED);
                        }

                    }
                    else if(i==2){


                    playFull(ind);

                    }
                    i=0;
                }
            },500);



          }






   }

    public void playFull(int a){
        final  Intent intent ;
        intent = new Intent(context,FullScreen.class);

        intent.putExtra("jeet3",coun);
       intent.putExtra("jeet2",a);
          intent.putExtra("jeet3",j);
        if (sele==1){

            intent.putExtra("jeet5",sele);
           intent.putExtra("jeet6",itemListSelect);
           intent.putExtra("jeet7",j);
        }
        context.startActivity(intent);
        // intent.putExtra("jeet5",g);
       // Toast.makeText(context, "Clicke 4 = " , Toast.LENGTH_SHORT).show();
          if(a==1){

           }
         sele=0;
         itemListSelect.clear();

    }




}

/*public  Bitmap selecTed(int p){
        MainActivity mainActivity=new MainActivity();
          Bitmap a=null;
       try {
        //   int e=j+1;


        ab = itemList.get(p).getFilePath1();
       itemListSelect.add(p);
        a = audioImage(ab, 2);
        if (a == null) {
            Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.asdfss, null);
            Bitmap myLogo = ((BitmapDrawable) vectorDrawable).getBitmap();
            a= imageSlect(myLogo);
        }

    } catch (Exception e) {
    }



     return a;
    } */