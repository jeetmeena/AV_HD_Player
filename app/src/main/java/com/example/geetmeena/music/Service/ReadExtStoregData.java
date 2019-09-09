package com.example.geetmeena.music.Service;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.geetmeena.music.Model.CommonModel;
import com.example.geetmeena.music.Model.CommonVideo;
import com.example.geetmeena.music.MyAppDataBase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ReadExtStoregData {
    private static byte[] art;
    Context context;
    MyAppDataBase myAppDataBase;

     int count=0;
    public ReadExtStoregData(Context context,String audio){
        this.context=context;
        myAppDataBase=new MyAppDataBase(context,"lastSongWasPlayed.db",null,1);
    }
    public ReadExtStoregData(Context context){
        this.context=context;
        myAppDataBase=new MyAppDataBase(context,"lastSongWasPlayed.db",null,1);

    }
    @SuppressLint("ResourceAsColor")
    public ArrayList<CommonModel> AllMusicPathList() {
        final ArrayList<CommonModel> musicPathArrList = new ArrayList<>();
        final String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        final String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,    // filepath of the audio file
                MediaStore.Audio.Media._ID,     // context id/ uri id of the file
                MediaStore.Audio.Media.DISPLAY_NAME,


        };
        byte[] art;
        Bitmap audioView = null;
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        final Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;



            //Cursor cursorAlbum;

            String jeet = null;



                // if ( cursorAudio.moveToFirst()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Cursor cursorAudio = context.getContentResolver().query(songUri, projection, selection, null, MediaStore.Audio.Media.DISPLAY_NAME);
                        if (cursorAudio != null && cursorAudio.moveToFirst()) {
                        try {
                            myAppDataBase.deleteTableData("ReadSongListData");
                            do {

                            count++;
                            // Long albumId = Long.valueOf(cursorAudio.getString(cursorAudio.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                            //cursorAlbum = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            //new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                            // MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                            String titel = cursorAudio.getString(0);
                            String artist = cursorAudio.getString(1);
                            String album = cursorAudio.getString(2);
                            String duretion = cursorAudio.getString(3);
                            String data = cursorAudio.getString(4);
                            String id = cursorAudio.getString(5);
                            String name = cursorAudio.getString(6);

                            //   String  uri=cursorAudio.getExtras();
                     /*   try{
                          audioView=  audioImage(data);
                           jeet =String.valueOf(audioView);
                        }catch(Exception e){
                            Toast toast1=Toast.makeText(this,"hii jeet2",Toast.LENGTH_SHORT);
                        }*/
                           byte[] a=getImagebyet(data);
                           String[] foldname=data.split("/");
                            //int ID, String PATHOFSONG,String KEY_ALBUM,String KEY_ARTIST,String KEY_DURATION,String KEY_TITLE,String KEY_DISPLAY_NAME,byte[] SONGPROFILEIMG
                            myAppDataBase.isertReadSongLists(id,data,album,artist,duretion,titel,name,a,foldname[foldname.length-2]);
                            musicPathArrList.add(new CommonModel(titel, artist, album, duretion, data, id, name, count,a));
                            Log.e("ths",""+a);

                        } while (cursorAudio.moveToNext());
                    } catch (Exception e) {
                        Log.e("rtuio", e.getMessage());
                        Toast toast1 = Toast.makeText(context, "Error In read Data From stores", Toast.LENGTH_SHORT);
                        toast1.show();

                    }

                        }
                        cursorAudio.close();
                        //Toast.makeText(context, "Loop", Toast.LENGTH_SHORT).show();

                    }
                }).start();
                //  }


       // Toast.makeText(context, "Hii Ther", Toast.LENGTH_SHORT).show();



        return musicPathArrList;
    }


    public  ArrayList<CommonVideo> getAllVideo(){
        final ArrayList<CommonVideo> videoArrayList=new ArrayList<>();




         new Thread(new Runnable() {
             @Override
             public void run() {

                 ContentResolver contentResolver = context.getContentResolver();
                 //String string = MediaStore.Video.Media.;
                 myAppDataBase.deleteTableData("ReadVideoListData");
                 int count=0;
                 String[] projection={
                         MediaStore.Audio.Media.TITLE,
                         MediaStore.Audio.Media.ARTIST,
                         MediaStore.Audio.Media.ALBUM,
                         MediaStore.Audio.Media.DURATION,
                         MediaStore.Audio.Media.DATA,    // filepath of the audio file
                         MediaStore.Audio.Media._ID,     // context id/ uri id of the file
                         MediaStore.Audio.Media.DISPLAY_NAME,};
                 Uri videoUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                 MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
                 Cursor cursorAudio =  contentResolver.query(videoUri, projection, null, null, MediaStore.Video.Media.DISPLAY_NAME);
                 if(cursorAudio!=null && cursorAudio.moveToFirst()){

                     do{String videoTitle=cursorAudio.getString(0);
                         String videoArtist=cursorAudio.getString(1);
                         String videoAlbum=cursorAudio.getString(2);
                         String videoDuration=cursorAudio.getString(3);
                         String videoData=cursorAudio.getString(4);
                         String videoId=cursorAudio.getString(5);
                         String videoDisplayName=cursorAudio.getString(6);
                         String[] strings=videoData.split("/");
                           videoArrayList.add(new CommonVideo(videoTitle,videoArtist,videoAlbum,videoDuration,videoData,videoId,videoDisplayName));
                           myAppDataBase.isertReadVideoLists(videoId,videoData,videoAlbum,videoArtist,videoDuration,videoTitle,videoDisplayName,bitmapToByte(videoData),strings[strings.length-2]);
                         //creatCacheBitmap(count,videoData);
                         // solventRecyclerViewAdapters.setVideoList(videList);
                         count++;
                     }while (cursorAudio.moveToNext());
                     cursorAudio.close();

                 }
                 else {
                    // Toast.makeText(context,"No Video Faund",Toast.LENGTH_SHORT).show();
                 }
             }
         }).start();
     return videoArrayList;
    }
     public static byte[] bitmapToByte(String videoData){
        Bitmap bmp = ThumbnailUtils.createVideoThumbnail(videoData,MediaStore.Images.Thumbnails.MINI_KIND);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }
    public static   byte[] getImagebyet(String mediaPath){
        String path = mediaPath;
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();



        try {
            mediaMetadata.setDataSource(path);
            art =mediaMetadata.getEmbeddedPicture();


        }catch (Exception e){

            //audioView1.setBackgroundColor(R.color.fab_material_amber_500);
         }
        return  art;
    }
}
