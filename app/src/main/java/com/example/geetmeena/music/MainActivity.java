package com.example.geetmeena.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geetmeena.music.Model.CommonModel;
import com.example.geetmeena.music.Model.CommonVideo;
import com.example.geetmeena.music.Service.ReadExtStoregData;
import com.example.geetmeena.music.fragment.AudioFragment;
import com.example.geetmeena.music.fragment.BlankFragment;
import com.example.geetmeena.music.fragment.MyMediaPlayer;
import com.example.geetmeena.music.fragment.VideoFolderFragment;
import com.example.geetmeena.music.fragment.VideoFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivitya, DrawerLayout.DrawerListener,
        NavigationView.OnNavigationItemSelectedListener,ActionBar.TabListener {
    public static MediaPlayer mMediaPlayer;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    RecyclerView recyclerView;
    public static ArrayList<CommonModel> gaggeredList;
    public static int horizontelScroll;
    Button bu;
    Toolbar toolbar;
   // Button fab;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    Button but;
    public static int count = 0;
    public Button bottomPlay;
    ///public Button bottom_song_list;
    Button start;
    NavigationView navigationView;
    public  static String PREF_FILE_NAME="JeetFirst";
    public static String USER_KEY_DRAWER="user key drawer";
    private DrawerLayout mDrawerLayout;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;
     static MainActivity instance;
    Context context;
    ArrayList<Integer> selected;
    ViewPager viewPager;

    private PagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    LinearLayout linearLayout_Bottom;
     BottomSheetBehavior behavior;
   int[] listOfLayout;
    private int[] tabIcons = {
            R.drawable.exo_controls_play,
            R.drawable.exo_controls_play,
            R.drawable.exo_controls_pause
    };
   TextView t;
   public View[] viewsList;
    int[] aaaID;
    ArrayList<ViewReso> viewResos;
    private static Context mContext;
    //public Uri newUei;
    public Button buttonplay;
    public Button buttonnext;
    public Button buttonback;
    public TextView bottomTitle;
    public  TextView seekDuretoin1;
    public TextView songDuretion;
    public TextView artistName;
    public ImageView imageView;
    public SeekBar seekBar;
    public TextView bottomSubTitle;
    public ImageView bottomSheetImageView;
   AudioFragment audioFragment;
    private IntentFilter intentFilter ;
    private  BecomingNoisyReceiver myNoisyAudioStreamReceiver;
   MediaSessionCompat sessionCompat;
   // String[] songListsName ;
   ArrayList<CommonModel> getAllSongPath;
   ArrayList<CommonVideo> getAllVideoPath;
    HashMap<Integer, Bitmap> hashMap=new HashMap<Integer, Bitmap>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return   true   ;
    }

    //#AARRGGBB,where AA is the Alpha channel,RR is the red channel, BB is the Green channel, BB is the Blue Channel
    @SuppressLint("RestrictedApi")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
       // toolbar = findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        viewPager=findViewById(R.id.pager);
       // intentFilter = new IntentFilter(AudioManager.ACTION_HEADSET_PLUG);
        //myNoisyAudioStreamReceiver = new  BecomingNoisyReceiver();

         viewResos=new ArrayList<>();
         getAllSongPath=new ArrayList<>();
         getAllVideoPath=new ArrayList<>();
        Toast.makeText(this,"01"+aaaID, Toast.LENGTH_SHORT).show();
         viewsList= new View[]{};
         //setViewList();
        setSupportActionBar(toolbar);//import android.support.v7.widget.Toolbar*** if use Toolbar Layout android.support.v7.widget.Toolbar;
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setuptab();
        linearLayout_Bottom=findViewById(R.id.linearLayout_Bottom);

        mPagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        actionbar.setHomeAsUpIndicator(R.drawable.ic_format_align_justify_black_24dp);

        buttonplay=  findViewById(R.id.play1);
        buttonnext=findViewById(R.id.next);
        buttonback= findViewById(R.id.back);
        imageView= findViewById(R.id.image2);
       // Toast.makeText(context,"AA", Toast.LENGTH_SHORT).show();
        bottomTitle= findViewById(R.id.bottom_Title);
        String s="giu";
        bottomTitle.setText(s);
        imageView.setImageResource(R.drawable.defaultd);
        artistName= findViewById(R.id.textView2);
        songDuretion= findViewById(R.id.textView3);
        //songDuretion.setText("00.0");
        seekDuretoin1=findViewById(R.id.textView4);
        seekBar= findViewById(R.id.seekBar);
         bottomPlay=findViewById(R.id.bottomPlay);
         bottomSubTitle=findViewById(R.id.bottom_SubTitle);
         bottomSheetImageView=findViewById(R.id.bottom_image);
        // bottomSheetText.setText("saffa");
            mContext=this;
       // viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        //DachshundTabLayout tabLayout = (DachshundTabLayout) findViewById(R.id.tab_layout);
       // tabLayout.setupWithViewPager(viewPager);
        bottumSheet();
        mDrawerLayout = findViewById(R.id.fragment_drawer_layout);
        mDrawerLayout.addDrawerListener( this);
         navigationView = findViewById(R.id.fragment_navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Menu menu1=navigationView.getMenu();
         Intent intent=getIntent();

        recyclerView = findViewById(R.id.recycler_view);
         recyclerView.setHasFixedSize(true);
        start=findViewById(R.id.start);

         instance=this;
         context=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor();
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("We need read external storage permission to proceed")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object
                builder.create();


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_SETTINGS  },
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }else {
            AsyincReadFile asyincReadFile=new AsyincReadFile(this);
            asyincReadFile.execute();
            setViewPager(viewPager);

        }
       try{
        int a=   intent.getIntExtra("key",0);
        if(a==1){
           selected=intent.getIntegerArrayListExtra("selected");

        }
       }catch(Exception e){

       }


       // registerReceiver( myNoisyAudioStreamReceiver, intentFilter );
             //registerComponentCallbacks((ComponentCallbacks) callback);

    }

    class AsyincReadFile extends AsyncTask<Void,Void,Void>{
        MyAppDataBase myAppDataBase;
        ArrayList<CommonModel> commonModelArrayList;
         public AsyincReadFile(Context context){
             myAppDataBase=new MyAppDataBase(context,"lastSongWasPlayed.db",null,1);
         }

        @Override
        protected Void doInBackground(Void... voids) {
         Cursor cursor=    myAppDataBase.getAllSongLists();
          if(cursor.getCount()>0&&cursor.moveToFirst()){
              do {

                  count++;
                  //myAppDataBase.isertReadSongLists(id,data,album,artist,duretion,titel,name,a);
                 // musicPathArrList.add(new CommonModel(titel, artist, album, duretion, data, id, name, count,a));
                  String id = cursor.getString(0);
                  String data = cursor.getString(1);
                  String album = cursor.getString(2);
                  String artist = cursor.getString(3);
                  String duretion = cursor.getString(4);
                  String titr1 = cursor.getString(5);
                  String name= cursor.getString(6);
                  byte[] a=cursor.getBlob(7);
                  new CommonModel(titr1, artist, album, duretion, data, id, name,count,a);

              } while (cursor.moveToNext());
          }else {
              ReadExtStoregData readExtStoregData=new ReadExtStoregData(MainActivity.this);
              readExtStoregData.AllMusicPathList();
              readExtStoregData.getAllVideo();
          }

               cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
            ReadExtStoregData readExtStoregData=new ReadExtStoregData(MainActivity.this);
            readExtStoregData.AllMusicPathList();
        }
    }


    public  void  runThread(){
        ReadExtStoregData readExtStoregData=new ReadExtStoregData(this);
        readExtStoregData.AllMusicPathList();
        readExtStoregData.getAllVideo();


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.theme_color_4));
    }


    public   class BecomingNoisyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyMediaPlayer jeet1 = new MyMediaPlayer();


            if (AudioManager.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        //audioManager.setSpeakerphoneOn(true);
                        Toast.makeText(context, "headphone plugOut", Toast.LENGTH_SHORT).show();
                      try {
                          jeet1.pauseMedia();

                      }catch (Exception e){}
                        break;
                    case 1:

                        break;
                    default:
                        //audioManager.setSpeakerphoneOn(true);
                }

            }

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST_READ_CONTACTS){
            if( grantResults[0]==PackageManager.PERMISSION_GRANTED){
                runThread();
                setViewPager(viewPager);
            }
            else {
                Toast.makeText(this, "Restart App And give Permission for the Process", Toast.LENGTH_SHORT).show();
            }
        } 
        
           
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        //unregisterComponentCallbacks((ComponentCallbacks) callback);
       // unregisterReceiver(myNoisyAudioStreamReceiver);
        super.onDestroy();
    }

    public static Context getmContext(){
        return mContext;
    }
    public void setViewList(){
      //SolventRecyclerViewAdapter vv=new SolventRecyclerViewAdapter(context,null,null);
      // vv.getResurs(viewsList);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
    }

    public void setuptab(){
      tabLayout.getTabAt(0).setIcon(tabIcons[0]);
      tabLayout.getTabAt(1).setIcon(tabIcons[1]);
      tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    public void setViewPager(ViewPager viewPager){
    ScreenSlidePagerAdapter adapter =new ScreenSlidePagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new AudioFragment("hii",context,viewResos),"Audio");
    adapter.addFragment(new VideoFragment("hii",context),"Videos");
    adapter.addFragment(new VideoFolderFragment(    "hii",context),"Video Folder");

    adapter.addFragment(new BlankFragment("hii",context),"Blank");
    viewPager.setAdapter(adapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int id = item.getItemId();
         switch (id){
             case android.R.id.home:
                 mDrawerLayout.openDrawer(GravityCompat.START);
                 return true;
             case R.id.horizontal:

                if(item.isChecked()){
                    item.setChecked(false);

                  //onClick();
                  }
                  else {
                    item.setChecked(true);
                    horizontelScroll=1;
                    //onClick();
                }
                 return true;

             case R.id.action_settings:
                 Toast toast = Toast.makeText(MainActivity.this,"Hiiiii",Toast.LENGTH_SHORT);
                 toast.show();
                 return true;

             default:

             return super.onOptionsItemSelected(item);
         }
        //noinspection SimplifiableIfStatement

    }

    @Override
    public void onClick() {
        if(horizontelScroll==1){ gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(gaggeredGridLayoutManager);
            horizontelScroll=0;
             }

            else  {
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gaggeredGridLayoutManager);
            }


      // fab.setColorNormal(R.color.fab_material_teal_900);
        try {
            ReadExtStoregData readExtStoregData=new ReadExtStoregData(this);
            gaggeredList = readExtStoregData.AllMusicPathList();


            try {

                Toast toast = Toast.makeText(this, "hii jee", Toast.LENGTH_SHORT);
                toast.show();

                // fab.setRippleColor(R.color.fab_material_amber_500);
                //SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(MainActivity.this, gaggeredList,null);

                //recyclerView.setAdapter(rcAdapter);
                //onClick(fabCancel);
                start.setEnabled(false);
            } catch (Exception e) {

                Toast toast1 = Toast.makeText(this, "hii jeet4", Toast.LENGTH_SHORT);
                toast1.show();
            }


        } catch (Exception e) {
            Log.i("rtuio", e.getMessage());
        }
    }





    @Override
    public void onClick(View view) {
           //fab.setColorRipple(R.color.fab_material_pink_900);

        AudioFragment  jeet = new AudioFragment("aaa");
        //mMediaPlayer=jeet.
        //jeet.getView();

      if(R.id.bottomPlay==view.getId()){
         // Toast.makeText(view.getContext(), "Clicke 4 = " , Toast.LENGTH_SHORT).show();
         jeet.globalAudio(view);

          }
      else if(R.id.linearLayout_Bottom==view.getId()){

                if(behavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                      behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                  }

               else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                }
               // Toast.makeText(view.getContext(), "Clicke 4 = " , Toast.LENGTH_SHORT).show();
                }
         else {


                 }



    }

    @Override
    public void onClick1(View view) {

        //onClick();



    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final Menu menu = navigationView.getMenu();



        switch (item.getItemId()) {




            case R.id.allSongLists:
              //  NavUtils.navigateUpFromSameTask(this);

            case R.id.favoritSong: ;

                break;


         }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        //Toast toast1 = Toast.makeText(this, "hii opened", Toast.LENGTH_SHORT);
        //toast1.show();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
       // Toast toast1 = Toast.makeText(this, "hii closed", Toast.LENGTH_SHORT);
        //toast1.show();
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }






    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class FlotingActionButton {

    }
    @Override
    public void onBackPressed() {
      /*  if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }



    public static void saveToPerferences(Context context, String perferencesName, String perferencesValue){
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(perferencesName,perferencesValue);
        editor.apply();
        Toast toast1 = Toast.makeText(context, "hii Test", Toast.LENGTH_SHORT);
        toast1.show();

    }

    public static String readFromPerferences(Context context,String perferencesName,String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(perferencesName,defaultValue);
    }
    /**
     *     Save and get ArrayList in SharedPreference
     */
    public void saveArray(String[] list, String key,int listCount){

        String list1 =String.valueOf(listCount);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!

        saveToPerferences(MainActivity.this,key,list1);

    }
    public void saveArrayList(ArrayList<String> list, String key,int listCount){

       String list1 =String.valueOf(listCount);
         prefs = PreferenceManager.getDefaultSharedPreferences(this);
         editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!

       saveToPerferences(MainActivity.this,key,list1);

    }
    public String[] getArray(String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key,null);
        Type type = new TypeToken<String[]>() {}.getType();

        //Toast toast2 = Toast.makeText(this, "hii Test", Toast.LENGTH_SHORT);
        //toast2.show();
        return gson.fromJson(json, type);
    }
    public ArrayList<String> getArrayList(String key){
         prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        //Toast toast2 = Toast.makeText(this, "hii Test", Toast.LENGTH_SHORT);
        //toast2.show();
        return gson.fromJson(json, type);
    }
   public   static MainActivity getInstance(){

       return instance;
   }

   public void jeeTq(){}


    public static ArrayList<Integer> arrayStringToIntegerArrayList(String arrayString){

        String removedBrackets = arrayString.substring(1, arrayString.length() - 1);
        String[] individualNumbers = removedBrackets.split(",");
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for(String numberString : individualNumbers){
            integerArrayList.add(Integer.parseInt(numberString.trim()));
        }
        return integerArrayList;
    }

   public  void bottumSheet(){
       CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
// The View with the BottomSheetBehavior

       View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);


       behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
           @Override
           public void onStateChanged(@NonNull View bottomSheet, int newState) {

               switch (newState) {
                   case BottomSheetBehavior.STATE_HIDDEN:
                       Toast.makeText(context, "Hidden" , Toast.LENGTH_SHORT).show();
                       break;
                   case BottomSheetBehavior.STATE_DRAGGING:
                       //Toast.makeText(context, "dragging " , Toast.LENGTH_SHORT).show();
                       break;

                   case BottomSheetBehavior.STATE_COLLAPSED:
                      // Toast.makeText(context, "Collapsed " , Toast.LENGTH_SHORT).show();

                       bottomPlay.setVisibility(View.VISIBLE);
                       bottomTitle.setSelected(false);
                        break;

                   case BottomSheetBehavior.STATE_EXPANDED:
                      // Toast.makeText(context, "expanded " , Toast.LENGTH_SHORT).show();
                       bottomPlay.setVisibility(View.GONE);
                       bottomTitle.setSelected(true);;
                         break;

                   case BottomSheetBehavior.STATE_SETTLING:
                       //Toast.makeText(context, "settling" , Toast.LENGTH_SHORT).show();

                       break;



               }

           }

           @Override
           public void onSlide(View bottomSheet, float slideOffset) {

           }
       });

   }

}
