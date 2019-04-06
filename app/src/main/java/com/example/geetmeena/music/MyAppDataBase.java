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
  private String SONGLIST="SongLists";


    public MyAppDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
       // this.TABLENAME=name;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLELSPD+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PATHOFSONG TEXT,RESUMESONGDATE REAL )");
        db.execSQL("create table "+SONGLISTDATA+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PATHOFSONG TEXT)");
        //db.execSQL("create table "+SONGLIST+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, SONGLISTNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("create table "+TABLELSPD);
         db.execSQL("create table "+SONGLISTDATA);
        // db.execSQL("create table "+SONGLIST);
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
    public boolean insertFavoriteSongList(String songPath){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column_2,songPath);

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

    public void deleteSongList(String iD){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(SONGLIST,"ID=?",new String[]{iD});
    }
    public void deleteSongListData(String iD){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(SONGLIST,"ID=?",new String[]{iD});
    }
}
