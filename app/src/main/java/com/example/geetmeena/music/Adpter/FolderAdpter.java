package com.example.geetmeena.music.Adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geetmeena.music.FolderVideosActivity;
import com.example.geetmeena.music.R;

import java.util.ArrayList;

public class FolderAdpter extends RecyclerView.Adapter<FolderAdpter.ViewHolder> {
    Context context;
    ArrayList<String> folders;
    FragmentTransaction fragmentTransaction;
    public FolderAdpter(Context context, ArrayList<String> folder, FragmentTransaction transaction){
        this.context=context;
        folders=folder;
        fragmentTransaction=transaction;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.folder_recycler_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder ;
    }

    @Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.textView.setText(folders.get(position));
    }

    @Override
    public int getItemCount() {
        if (folders!=null)return folders.size();
        return 0;
    }

    public void setFolderStrgs(ArrayList<String> string) {
        folders=string;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView1,imageView2,imageView3;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView=itemView.findViewById(R.id.folderName);
        }

        @Override
        public void onClick(View v) {

             Intent intent=new Intent(context, FolderVideosActivity.class);
            intent.putExtra("folderName",folders.get(getAdapterPosition()));
            context.startActivity(intent);

            //  Toast.makeText(context, ""+folders.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }

    }
}
