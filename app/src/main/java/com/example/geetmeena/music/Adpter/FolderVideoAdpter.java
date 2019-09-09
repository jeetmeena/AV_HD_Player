package com.example.geetmeena.music.Adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geetmeena.music.ItemClickListener;
import com.example.geetmeena.music.Model.CommonVideo;
import com.example.geetmeena.music.R;
import com.example.geetmeena.music.VideoActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FolderVideoAdpter extends RecyclerView.Adapter<FolderVideoAdpter.SolventHolders> {
    Context context;
    HashMap<Integer, Bitmap> hashMap;
    public ItemClickListener onItemClickListener;
    public ArrayList<CommonVideo> itemList;
    public FolderVideoAdpter(Context baseContext) {

        this.context = baseContext;
    }



    @NonNull
    @Override
    public  SolventHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,null);

        //fab2.show();
         SolventHolders rcv = new SolventHolders(layoutView);


        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull SolventHolders holder, final int position) {
        //  ImageView audioView2 = null;

        holder.countryName.setText(itemList.get(position).getVideoName());

        //Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(itemList.get(position).getVideoFilePath(),MediaStore.Images.Thumbnails.MINI_KIND);
        holder.video.setImageBitmap(hashMap.get(position));

    }


    public void setVideoList(ArrayList<CommonVideo> commonVideos, HashMap<Integer, Bitmap> hashMap){
        itemList=commonVideos;
        this.hashMap=hashMap;
        notifyDataSetChanged();


    }
    public void setClickListener(ItemClickListener clickListener){
        this.onItemClickListener=clickListener;


    }
    @Override
    public int getItemCount() {
        if(itemList!=null)return itemList.size();
        return 0;
    }




    public class SolventHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView countryName;
        // public ImageView countryPhoto;
        ImageView video;
        ArrayList<CommonVideo> videoslist;



        public SolventHolders(View itemView) {
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

            Intent intent=new Intent(context, VideoActivity.class);
            intent.putExtra("jeet", videoslist.get(getAdapterPosition()).getVideoFilePath());
            intent.putExtra("position",getAdapterPosition());
            intent.putExtra("key",0);
            // intent.putExtra("list",videosList);
            context.startActivity(intent);




        }




    }



}