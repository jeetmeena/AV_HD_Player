package com.example.geetmeena.music.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geetmeena.music.Adpter.FolderAdpter;
import com.example.geetmeena.music.MyAppDataBase;
import com.example.geetmeena.music.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFolderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFolderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    FolderAdpter folderAdpter;
    MyAppDataBase myAppDataBase;
    @SuppressLint("ValidFragment")
    public VideoFolderFragment(String hii, Context context) {
        // Required empty public constructor
    }

    public VideoFolderFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFolderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFolderFragment newInstance(String param1, String param2) {
        VideoFolderFragment fragment = new VideoFolderFragment();
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
        View view =inflater.inflate(R.layout.fragment_video_folder, container, false);
        recyclerView=view.findViewById(R.id.recyler_folderView);
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager( gaggeredGridLayoutManager);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        folderAdpter=new FolderAdpter(getActivity(),null,transaction);
        recyclerView.setAdapter(folderAdpter);
        myAppDataBase=new MyAppDataBase(getActivity(),"lastSongWasPlayed.db",null,1);

          AsyncVideoLoad asyncVideoLoad=new AsyncVideoLoad(getActivity(),folderAdpter);
          asyncVideoLoad.execute();

        // Inflate the layout for this fragment
        return  view;
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
    public void getAllFolserNa(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                ArrayList<String> string=new ArrayList<>();
                Cursor cursorAudio =   myAppDataBase.getUnikValueFromColumn("FOLDER_NAME");
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
                        string.add(folderName);
                      }while (cursorAudio.moveToNext());
                    folderAdpter.setFolderStrgs(string);

                }
                else {
                    //Toast.makeText(getActivity().getBaseContext(),"No Video Faund",Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
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
    class AsyncVideoLoad extends AsyncTask<Void,Void, ArrayList<String>> {
        ArrayList<String> videList;
        Context context;
        FolderAdpter folderAdpter;
        public  AsyncVideoLoad(Context context, FolderAdpter folderAdpter){
            this.context=context;
            this.folderAdpter=folderAdpter;
            videList=new ArrayList<>();
        }
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            int count=0;
            Cursor cursorAudio =   myAppDataBase.getUnikValueFromColumn("FOLDER_NAME");
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
                    videList.add(folderName);
                }while (cursorAudio.moveToNext());


            }
            else {
                //Toast.makeText(getActivity().getBaseContext(),"No Video Faund",Toast.LENGTH_SHORT).show();
            }
            return videList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> commonModels) {
            super.onPostExecute(commonModels);
            folderAdpter.setFolderStrgs(commonModels);
            // String[] strings=commonModels.get(0).getVideoFilePath().split("/");
            //Toast.makeText(context, ""+strings[strings.length-2], Toast.LENGTH_SHORT).show();
         }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
