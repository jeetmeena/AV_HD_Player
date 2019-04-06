package com.example.geetmeena.music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeetmeena on 1/20/2018.
 */

public class CommonModel {
    private String queryTtile;
    private String queryAlbum;
    private String queryArtist;
    private String queryDuration;
    private String filePath1;
    private String _id;
    private String queryName;
    private String songImage;
    private Uri uri;
    String j;
    int numberSong;
    boolean selected;
    long duration;
    TimeUnit timeUnit;
    private static final TreeMap<String, MediaMetadataCompat> music = new TreeMap<>();
    private static final HashMap<String, Integer> albumRes = new HashMap<>();
    private static final HashMap<String, String> musicFilePath = new HashMap<>();
    public String getAlbumCoverPath() {
        return albumCoverPath;
    }

    public void setAlbumCoverPath(String albumCoverPath) {
        this.albumCoverPath = albumCoverPath;
    }

    private String albumCoverPath;

    public CommonModel(String queryTtile, String queryAlbum, String queryArtist,
                       String duretion, String filepath,
                       String _id, String name, int coun) {
        this.queryTtile = queryTtile;
        numberSong = coun;

        this.queryAlbum = queryAlbum;
        this.queryArtist = queryArtist;
        queryDuration = duretion;
        filePath1 = filepath;
        this._id = _id;
        queryName = name;
        this.duration= Long.parseLong(duretion);
        timeUnit=TimeUnit.MILLISECONDS;
        uri=Uri.fromFile(new File(filepath));
        //  j=jeet;
        // u=uri;
        //songImage=bitmap;
        String mediaId=String.valueOf(coun);
        createMediaMataDataCompat(mediaId,queryTtile,queryArtist,
                queryAlbum, name, duration, timeUnit,queryName,_id, filePath1,uri);
    }



    private static void createMediaMataDataCompat(String mediaId,String queryTtile,String queryArtist,
                                                  String queryAlbum,String key_genre,long duration,
                                                  TimeUnit timeUnit,String queryName,String _id,
                                                  String filePath1,Uri mediaUri){

           music.put(mediaId,new MediaMetadataCompat.Builder()
                   .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId)
                   .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, queryAlbum)
                   .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, queryArtist)
                   .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,duration)
                   .putString(MediaMetadataCompat.METADATA_KEY_GENRE, key_genre)
                   .putString(
                           MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,filePath1)
                   .putString(
                           MediaMetadataCompat.METADATA_KEY_MEDIA_URI,filePath1)
                   .putString(MediaMetadataCompat.METADATA_KEY_TITLE, queryTtile)

                   .build());
       // albumRes.put(mediaId, );
        musicFilePath.put(mediaId, filePath1);
    }


    private static String getAlbumArtUri(String albumArtFilePath) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                BuildConfig.APPLICATION_ID +albumArtFilePath ;
    }
    public static String getFilePath(String musicId){
        return musicFilePath.get(musicId);
    }

    public static List<MediaBrowserCompat.MediaItem> getMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        for (MediaMetadataCompat metadata : music.values()) {
            result.add(
                    new MediaBrowserCompat.MediaItem(
                            metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }
        return result;
    }

    public static String getRoot() {
        return "root";
    }

    public static MediaMetadataCompat getMetadata(Context mediaPlayerService, String mediaId) {
        MediaMetadataCompat metadataWithoutBitmap = music.get(mediaId);
        Bitmap albumArt = getAudioImageBitMap( mediaId);

        // Since MediaMetadataCompat is immutable, we need to create a copy to set the album art.
        // We don't set it initially on all items so that they don't take unnecessary memory.
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        for (String key :
                new String[]{
                        MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                        MediaMetadataCompat.METADATA_KEY_ALBUM,
                        MediaMetadataCompat.METADATA_KEY_ARTIST,
                        MediaMetadataCompat.METADATA_KEY_GENRE,
                        MediaMetadataCompat.METADATA_KEY_TITLE
                }) {
            builder.putString(key, metadataWithoutBitmap.getString(key));
        }
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                metadataWithoutBitmap.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt);
        return builder.build();
    }




    public static   Bitmap getAudioImageBitMap(String musicId){
             String path =  musicFilePath.get(musicId);
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        byte[] art;
        ImageView audioView1 = null;
        Bitmap songImage = null;



        try {
            mediaMetadata.setDataSource(path);
            art=mediaMetadata.getEmbeddedPicture();
            songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            if (songImage==null){
                //mainActivity.imageView.setImageResource(R.drawable.fullscreenim);
                //mainActivity.bottomSheetImageView.setImageResource(R.drawable.fullscreenim);
                songImage= BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ashok_galaxyn);

            }

        }catch (Exception e){

            //audioView1.setBackgroundColor(R.color.fab_material_amber_500);
            songImage= BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ashok_galaxyn);
        }
        return  songImage;
    }


    public static String getPreviousSong(String currentMediaId) {
        String prevMediaId = music.lowerKey(currentMediaId);
        if (prevMediaId == null) {
            prevMediaId = music.firstKey();
        }
        return prevMediaId;
    }

    public static String getNextSong(String currentMediaId) {
        String nextMediaId = music.higherKey(currentMediaId);
        if (nextMediaId == null) {
            nextMediaId = music.firstKey();
        }
        return nextMediaId;
    }

    public void setSelected(){
        this.selected=selected;
    }
    public void setQueryTtile(String queryTtile) {
        this.queryTtile = queryTtile;
    }

    public void setQueryDuration(String queryDuration) {
        this.queryDuration = queryDuration;
    }

    public String getQueryTtile() {
        return queryTtile;
    }

    public String getQueryAlbum() {
        return queryAlbum;
    }

    public String getQueryArtist() {
        return queryArtist;
    }

    public String getAudioView1() {
        songImage = j;
        return songImage;
    }


    public boolean isSelected(){
        return selected;
    }
    public Uri getU() {
        return uri;
    }

    public String getFilePath1() {
        return filePath1;
    }

    public void setFilepath1(String filepath1) {
        this.filePath1 = filepath1;
    }

    public String get_id() {
        return queryArtist;
    }

    public String getName() {
        return queryArtist;
    }

    public String getQueryName() {
        return queryName;
    }
    public String getQueryDuration(){return queryDuration;}







}