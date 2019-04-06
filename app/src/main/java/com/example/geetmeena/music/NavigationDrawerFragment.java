package com.example.geetmeena.music;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

public class NavigationDrawerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public  static String PREF_FILE_NAME="JeetFirst";
    public static String USER_KEY_DRAWER="user key drawer";
    public  boolean mUserLernedDrawer;
    public boolean mFromSavedInstanceState;
    public   View contentView;

   private ActionBarDrawerToggle mDrawerToggle;
 private DrawerLayout mDrawerLayout;
    private OnFragmentInteractionListener mListener;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try { mUserLernedDrawer=  Boolean.valueOf(readFromPerferences(getActivity(),USER_KEY_DRAWER,"false"));
            if (savedInstanceState != null) {
                mFromSavedInstanceState=true;
            }

        }catch (Exception e){}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = null;
        try {
            view1 = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        } catch (Exception e) {
        }
        return view1;
    }

    // TODO: Rename method, update argument and hook method into UI event




    public void setUp(int fragmentID, final DrawerLayout mdrawerLayout, Toolbar toolbar) {
        mDrawerLayout =mdrawerLayout;
         contentView=getActivity().findViewById(fragmentID);
       // mdrawerLayout=toolbar;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // creates call to onPrepareOptionsMenu()
                getActivity().invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               if(!mUserLernedDrawer){
                   mUserLernedDrawer=true;
                   saveToPerferences(getActivity(),USER_KEY_DRAWER,mUserLernedDrawer+"");
               }
               getActivity().invalidateOptionsMenu();
              //  invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        if(!mUserLernedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(contentView);


           }

             // Set the drawer toggle as the DrawerListener
           mDrawerLayout.setDrawerListener((DrawerLayout.DrawerListener) mDrawerToggle);
           mDrawerLayout.post(new Runnable() {
               @Override
               public void run() {
                   mDrawerToggle.syncState();
               }
           });
       }



public static void saveToPerferences(Context context,String perferencesName,String perferencesValue){
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(perferencesName,perferencesValue);
        editor.apply();


}

    public static String readFromPerferences(Context context,String perferencesName,String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(perferencesName,defaultValue);
    }




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
}
