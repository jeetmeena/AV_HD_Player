package com.example.geetmeena.music.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geetmeena.music.Adpter.FolderVideoAdpter;
import com.example.geetmeena.music.Model.CommonVideo;
import com.example.geetmeena.music.MyAppDataBase;
import com.example.geetmeena.music.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FolderVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FolderVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FolderVideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyAppDataBase myAppDataBase;
    private OnFragmentInteractionListener mListener;
    HashMap<Integer, Bitmap> hashMap;
    public   ArrayList<CommonVideo> videosList;
    String Folder;
    RecyclerView recyclerVideo;
    FolderVideoAdpter folderVideoAdpter;
   public static FolderVideoFragment folderVideoFragment;
     public FolderVideoFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public FolderVideoFragment(String folder) {
        // Required empty public constructor
        Folder=folder;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FolderVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FolderVideoFragment newInstance(String param1, String param2) {
        FolderVideoFragment fragment = new FolderVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_folder_video,container,false);
        recyclerVideo=view.findViewById(R.id.recycler_Video);
        myAppDataBase=new MyAppDataBase(getActivity(),"lastSongWasPlayed.db",null,1);
        hashMap=new HashMap<>();
        folderVideoFragment=this;
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerVideo.setLayoutManager(gaggeredGridLayoutManager);
          folderVideoAdpter=new FolderVideoAdpter(getActivity());
        recyclerVideo.setAdapter(folderVideoAdpter);
        AsyncVideoLoad asyncVideoLoad=new AsyncVideoLoad(getActivity(),folderVideoAdpter);
        asyncVideoLoad.execute();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public static FolderVideoFragment getInstance(){
        return folderVideoFragment;
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
            ContentResolver contentResolver = getActivity().getContentResolver();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
