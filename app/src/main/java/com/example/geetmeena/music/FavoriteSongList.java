package com.example.geetmeena.music;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteSongList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteSongList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteSongList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> arrayList;
    private OnFragmentInteractionListener mListener;
    RecyclerView favoriteRecycalerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    public FavoriteSongList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteSongList.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteSongList newInstance(String param1, String param2) {
        FavoriteSongList fragment = new FavoriteSongList();
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
     View v=   inflater.inflate(R.layout.fragment_favorite_song_list, container, false);
     favoriteRecycalerView=v.findViewById(R.id.favoriteRecycalerView);
         mLayoutManager=new LinearLayoutManager(getActivity());
         favoriteRecycalerView.setLayoutManager(mLayoutManager);
        MyAppDataBase myAppDataBase=new MyAppDataBase(getActivity().getBaseContext(),"lastSongWasPlayed.db",null,1);
                  Cursor cursor= myAppDataBase.getFavoriteSongListData();
                  while (cursor.moveToNext()){
                      arrayList.add(cursor.getString(1));
                  }
        mAdapter=new MyFavoriteListAdpter(arrayList);
      favoriteRecycalerView.setAdapter(mAdapter);
        return v;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    private class MyFavoriteListAdpter extends RecyclerView.Adapter<FavoriteViewHolder> {
        public MyFavoriteListAdpter(ArrayList<String> arrayList) {
        }

        @NonNull
        @Override
        public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(getActivity()).inflate(R.layout.favoritesonglistitem,parent);
            FavoriteViewHolder favoriteViewHolder=new FavoriteViewHolder(v);
            return favoriteViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
          //holder.textView.setText(arrayList.get());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    private class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public FavoriteViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.favoriteItemImage);
            textView=itemView.findViewById(R.id.favoriteItemText);
        }
    }
}
