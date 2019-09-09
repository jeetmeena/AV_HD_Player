 package com.example.geetmeena.music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.geetmeena.music.Adpter.FolderVideoAdpter;
import com.example.geetmeena.music.Model.CommonVideo;

import java.util.ArrayList;
import java.util.HashMap;

 public class FolderVideosActivity extends AppCompatActivity {
    MyAppDataBase myAppDataBase;
     HashMap<Integer, Bitmap> hashMap;
     public   ArrayList<CommonVideo> videosList;
     String Folder;
     RecyclerView recyclerVideo;
     FolderVideoAdpter folderVideoAdpter;
    static FolderVideosActivity folderVideosActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_videos);
         folderVideosActivity=this;

        Intent intent=getIntent();
    Folder=intent.getStringExtra("folderName");

        recyclerVideo=findViewById(R.id.recycler_Video);
        myAppDataBase=new MyAppDataBase( this,"lastSongWasPlayed.db",null,1);
        hashMap=new HashMap<>();
         StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerVideo.setLayoutManager(gaggeredGridLayoutManager);
        folderVideoAdpter=new FolderVideoAdpter(this);
        recyclerVideo.setAdapter(folderVideoAdpter);
      AsyncVideoLoad asyncVideoLoad=new  AsyncVideoLoad(this,folderVideoAdpter);
        asyncVideoLoad.execute();

    }

    public static FolderVideosActivity getFolderVideosActivity(){
        return folderVideosActivity;
    }

     class AsyncVideoLoad extends AsyncTask<Void,Void, ArrayList<CommonVideo>> {
         ArrayList<CommonVideo> videList;
         Context context;
         FolderVideoAdpter solventRecyclerViewAdapters;


         public  AsyncVideoLoad(Context context, FolderVideoAdpter solventRecyclerViewAdapters){
             this.context=context;
             this.solventRecyclerViewAdapters=solventRecyclerViewAdapters;
             videList=new ArrayList<>();
         }
         @Override
         protected ArrayList<CommonVideo> doInBackground(Void... voids) {
             ContentResolver contentResolver = getContentResolver();
             //String string = MediaStore.Video.Media.;
             int count=0;
             Uri videoUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
             MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
             Cursor cursorAudio =   myAppDataBase.getAllVideoByFolderName(Folder);
             if(cursorAudio.getCount()>0 && cursorAudio.moveToFirst()){

                 do{
                     String videoId=cursorAudio.getString(0);
                     String videoData=cursorAudio.getString(1);
                     String videoAlbum=cursorAudio.getString(2);
                     String videoArtist=cursorAudio.getString(3);
                     String videoDuration=cursorAudio.getString(4);
                     String videoTitle=cursorAudio.getString(5);

                     String videoDisplayName=cursorAudio.getString(6);
                     byte[] imagebyte=cursorAudio.getBlob(7);

                     String folderName=cursorAudio.getString(8);

                     videList.add(new CommonVideo(videoTitle,videoArtist,videoAlbum,videoDuration,videoData,videoId,videoDisplayName));
                     creatCacheBitmap( count,imagebyte);
                     // solventRecyclerViewAdapters.setVideoList(videList);
                     count++;
                 }while (cursorAudio.moveToNext());

             }
             else {
                 //Toast.makeText(getActivity().getBaseContext(),"No Video Faund",Toast.LENGTH_SHORT).show();
             }
             return videList;
         }

         @Override
         protected void onPostExecute(ArrayList<CommonVideo> commonModels) {
             super.onPostExecute(commonModels);
             solventRecyclerViewAdapters.setVideoList(commonModels,hashMap);
             // String[] strings=commonModels.get(0).getVideoFilePath().split("/");
             Toast.makeText(context, "s"+commonModels.size(), Toast.LENGTH_SHORT).show();
             videosList=commonModels;
         }
     }
     private void creatCacheBitmap(int a, byte[] path){

         hashMap.put(a, BitmapFactory.decodeByteArray(path,0,path.length));

     }
}
