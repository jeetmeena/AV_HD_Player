package com.example.geetmeena.music;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;

/**

 */
@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment implements  ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<CommonVideo> videosList=null;
    HashMap<Integer, Bitmap> hashMap=new HashMap<Integer, Bitmap>();

    private VideoView video;
    private MediaController ctlr;

    RecyclerView recyclerVideo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    @SuppressLint("ValidFragment")
    public VideoFragment(String s, Context context) {
        mParam1=s;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters

     public static VideoFragment videoFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_video,container,false);
        recyclerVideo=view.findViewById(R.id.recycler_Video);
        videoFragment=this;
        videoLoad();
        return view;
    }

    public static VideoFragment getVideoFragment(){
        return videoFragment;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
            }


    private void videoLoad() {

        if(videosList==null){
            videosList=getVideoList();
        }
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerVideo.setLayoutManager(gaggeredGridLayoutManager);

        try {


            Toast toast = Toast.makeText(getActivity().getBaseContext(), "hii jeet", Toast.LENGTH_SHORT);
            toast.show();



            SolventRecyclerViewAdapters rcAdapter = new SolventRecyclerViewAdapters(getActivity().getBaseContext(),videosList);

            recyclerVideo.setAdapter(rcAdapter);
            rcAdapter.setClickListener(this);





        } catch (Exception e) {
            Log.i("rtuio", e.getMessage());
        }

    }

    private void creatCacheBitmap(int a,String path){
        hashMap.put(a,ThumbnailUtils.createVideoThumbnail(path,MediaStore.Images.Thumbnails.MINI_KIND));

    }
    public ArrayList<CommonVideo> getVideoList(){
        ArrayList<CommonVideo> videList=new ArrayList<>();
        ContentResolver contentResolver = getActivity().getContentResolver();
       //String string = MediaStore.Video.Media.;
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
       Cursor cursorAudio = contentResolver.query(videoUri, projection, null, null, MediaStore.Video.Media.DISPLAY_NAME);
         if(cursorAudio!=null && cursorAudio.moveToFirst()){

             do{String videoTitle=cursorAudio.getString(0);
                 String videoArtist=cursorAudio.getString(1);
                 String videoAlbum=cursorAudio.getString(2);
                 String videoDuration=cursorAudio.getString(3);
                 String videoData=cursorAudio.getString(4);
                 String videoId=cursorAudio.getString(5);
                 String videoDisplayName=cursorAudio.getString(6);
                 videList.add(new CommonVideo(videoTitle,videoArtist,videoAlbum,videoDuration,videoData,videoId,videoDisplayName));
                  creatCacheBitmap(count,videoData);
                  count++;
             }while (cursorAudio.moveToNext());

         }
         else {
             Toast.makeText(getActivity().getBaseContext(),"No Video Faund",Toast.LENGTH_SHORT).show();
         }

        return  videList;
    }

    @Override
    public void onClick(View view, int position) {
        Toast toast = Toast.makeText(getActivity().getBaseContext(), "hii jeet2", Toast.LENGTH_SHORT);
        toast.show();

      //  Intent intent=new Intent(MainActivity.getmContext(),VideoActivity.class);
      //  intent.putExtra("jeet",videosList.get(position).getVideoFilePath());
        // intent.putExtra("list",videosList);
        //MainActivity.getmContext().startActivity(intent);
    }

    public class SolventRecyclerViewAdapters extends RecyclerView.Adapter<SolventRecyclerViewAdapters.SolventHolders> {
        Context context;
        public ItemClickListener onItemClickListener;
        public  ArrayList<CommonVideo> itemList;
        public SolventRecyclerViewAdapters(Context baseContext,ArrayList<CommonVideo> videoslis) {
             this.itemList = videoslis;
             this.context = baseContext;
        }



        @NonNull
        @Override
        public SolventHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,null);

            //fab2.show();
            SolventHolders rcv = new SolventHolders(layoutView,itemList);


            return rcv;
        }

        @Override
        public void onBindViewHolder(@NonNull SolventHolders holder, final int position) {
            //  ImageView audioView2 = null;

            holder.countryName.setText(itemList.get(position).getVideoName());

            //Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(itemList.get(position).getVideoFilePath(),MediaStore.Images.Thumbnails.MINI_KIND);
            holder.video.setImageBitmap(hashMap.get(position));

        }


    public void videoActivity(int d){


    }
        public void setClickListener(ItemClickListener clickListener){
            this.onItemClickListener=clickListener;


        }
        @Override
        public int getItemCount() {
            return itemList.size();
        }




        public class SolventHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView countryName;
            // public ImageView countryPhoto;
            ImageView video;
            ArrayList<CommonVideo> videoslist;



            public SolventHolders(View itemView, ArrayList<CommonVideo > itemList) {
                super(itemView);
                videoslist=itemList;
                countryName = (TextView) itemView.findViewById(R.id.country_name);
                //countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
                video=itemView.findViewById(R.id.videoView);
                itemView.setTag(this);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
               // Toast toast = Toast.makeText(getActivity().getBaseContext(), ""+getAdapterPosition(), Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent=new Intent(MainActivity.getmContext(),VideoActivity.class);
                intent.putExtra("jeet",videosList.get(getAdapterPosition()).getVideoFilePath());
                intent.putExtra("position",getAdapterPosition());
                // intent.putExtra("list",videosList);
                MainActivity.getmContext().startActivity(intent);
                if(onItemClickListener==null){

                    onItemClickListener.onClick(v,getAdapterPosition());
                    Toast.makeText(getActivity().getBaseContext(),""+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                // int i=   getAdapterPosition();
                }



            }




        }

    }


}
