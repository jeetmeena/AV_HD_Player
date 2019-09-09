package com.example.geetmeena.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAppDataBase extends SQLiteOpenHelper {
 private String TABLELSPD="LastSongPlayData";
 private String TABLESS="SelectedSong";
  private String column_1="ID";
  private  String column_2="PATHOFSONG";
  private  String column_3="RESUMESONGDATE";
  private String SONGLISTDATA="UserSongListData";
    private String READSONGLISTDATA="ReadSongListData";
    private String READVIDEOLISTDATA="ReadVideoListData";

    private String SONGLIST="SongLists";
    private  String column_33="KEY_ALBUM";

    private  String column_4="KEY_ARTIST";
    private  String column_5="KEY_DURATION";
    private  String column_6="KEY_TITLE";
    Context context;


    public MyAppDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
       // this.TABLENAME=name;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLELSPD+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PATHOFSONG TEXT,RESUMESONGDATE REAL,SONGPROFILEIMG BLOB)");
        db.execSQL("create table "+SONGLISTDATA+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PATHOFSONG TEXT,KEY_ALBUM TEXT,KEY_ARTIST TEXT,KEY_DURATION TEXT,KEY_TITLE TEXT,SONGPROFILEIMG BLOB)");
        db.execSQL("create table "+READSONGLISTDATA+"(ID TEXT PRIMARY KEY, PATHOFSONG TEXT,KEY_ALBUM TEXT,KEY_ARTIST TEXT,KEY_DURATION TEXT,KEY_TITLE TEXT,KEY_DISPLAY_NAME TEXT,SONGPROFILEIMG BLOB,FOLDER_NAME TEXT)");
        //db.execSQL("create table "+SONGLIST+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, SONGLISTNAME TEXT)");
        db.execSQL("create table "+READVIDEOLISTDATA+"(ID TEXT PRIMARY KEY, PATHOFVIDEO TEXT,KEY_ALBUM TEXT,KEY_ARTIST TEXT,KEY_DURATION TEXT,KEY_TITLE TEXT,KEY_DISPLAY_NAME TEXT,VIDEOPROFILEIMG BLOB,FOLDER_NAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("create table "+TABLELSPD);
         db.execSQL("create table "+SONGLISTDATA);
         db.execSQL("create table "+READSONGLISTDATA);
         db.execSQL("create table "+READVIDEOLISTDATA);
         onCreate(db);
    }

    public boolean insertSongLists(String songLists){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column_2,songLists);
         long result= sqLiteDatabase.insert(TABLELSPD,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    };
    public boolean isertReadSongLists(String ID, String PATHOFSONG, String KEY_ALBUM, String KEY_ARTIST, String KEY_DURATION, String KEY_TITLE, String KEY_DISPLAY_NAME, byte[] SONGPROFILEIMG,String FOLDER_NAME){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",ID);
        contentValues.put("PATHOFSONG",PATHOFSONG);
        contentValues.put("KEY_ALBUM",KEY_ALBUM);
        contentValues.put("KEY_ARTIST",KEY_ARTIST);
        contentValues.put("KEY_DURATION",KEY_DURATION);
        contentValues.put("KEY_TITLE",KEY_TITLE);
        contentValues.put("KEY_DISPLAY_NAME",KEY_DISPLAY_NAME);
        contentValues.put("SONGPROFILEIMG",SONGPROFILEIMG);
        contentValues.put("FOLDER_NAME",FOLDER_NAME);
        long result= sqLiteDatabase.insert(READSONGLISTDATA,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    };
    public boolean isertReadVideoLists(String ID, String PATHOFVIDEO,String KEY_ALBUM,String KEY_ARTIST,String KEY_DURATION,String KEY_TITLE,String KEY_DISPLAY_NAME,byte[] VIDEOPROFILEIMG,String FOLDER_NAME){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",ID);
        contentValues.put("PATHOFVIDEO",PATHOFVIDEO);
        contentValues.put("KEY_ALBUM",KEY_ALBUM);
        contentValues.put("KEY_ARTIST",KEY_ARTIST);
        contentValues.put("KEY_DURATION",KEY_DURATION);
        contentValues.put("KEY_TITLE",KEY_TITLE);
        contentValues.put("KEY_DISPLAY_NAME",KEY_DISPLAY_NAME);
        contentValues.put("VIDEOPROFILEIMG",VIDEOPROFILEIMG);
        contentValues.put("FOLDER_NAME",FOLDER_NAME);
        long result= sqLiteDatabase.insert(READVIDEOLISTDATA,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    };

    public boolean insertFavoriteSongList(String songPath,String KEY_ALBUM, String KEY_ARTIST, String KEY_DURATION, String KEY_TITLE ){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column_2,songPath);
        contentValues.put(column_33,KEY_ALBUM);
        contentValues.put(column_4,KEY_ARTIST);
        contentValues.put(column_5,KEY_DURATION);
        contentValues.put(column_6,KEY_TITLE);
        long result= sqLiteDatabase.insert(SONGLISTDATA,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertData(String songPath,double songpos){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column_2,songPath);
        contentValues.put(column_3,songpos);
       long result= sqLiteDatabase.insert(TABLELSPD,null,contentValues);
       if(result==-1){
         return false;
       }
       else {
           return true;
       }
    }
    public Cursor getSongLists(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
  return   sqLiteDatabase.rawQuery("select * from "+TABLELSPD,null);
    }
    public Cursor getAllSongLists(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        return   sqLiteDatabase.rawQuery("select * from "+READSONGLISTDATA,null);
    }
    public Cursor getAllVideoLists(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        return   sqLiteDatabase.rawQuery("select * from "+READVIDEOLISTDATA,null);
    }
    public Cursor getVideosFolderLists(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        return   sqLiteDatabase.rawQuery("select * from "+READVIDEOLISTDATA,null);
    }

    public Cursor getFavoriteSongListData(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return   sqLiteDatabase.rawQuery("select * from "+SONGLISTDATA,null);

    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       Cursor res= sqLiteDatabase.rawQuery("select * from "+TABLELSPD,null);
     return res;
    }

     public boolean upDateData(String id,String songPath,double resume){
         long result=-6;
      try {
          SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
          ContentValues contentValues=new ContentValues();
          contentValues.put(column_1,id);
          contentValues.put(column_2,songPath);
          contentValues.put(column_3,resume);
          result=  sqLiteDatabase.update(TABLELSPD,contentValues,"ID = ?",new String[]{id});

      }catch (Exception e){}
         if(result==-1){return  false;}
         else {
             return true;
         }
    }
    public void deleteTableData(String tableName){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+tableName);
    }
    public void deleteSongList(String iD){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(SONGLIST,"ID=?",new String[]{iD});
    }
    public void deleteSongListData(String iD){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(SONGLIST,"ID=?",new String[]{iD});
    }
    public Cursor getUnikValueFromColumn(String column){
         SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res= sqLiteDatabase.rawQuery("select * from "+READVIDEOLISTDATA+" Group By "+column,null);
        return res;
    }
    public Cursor getAllVideoByFolderName(String folderName){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res= sqLiteDatabase.rawQuery("select * from "+READVIDEOLISTDATA+" where FOLDER_NAME=?",new String[]{folderName});
        return res;
    }
    public Cursor getFolderVideoFileIcon(String folderName){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res= sqLiteDatabase.rawQuery("select VIDEOPROFILEIMG  from "+READVIDEOLISTDATA+" where FOLDER_NAME=?",new String[]{folderName});
        return res;
    }
}
