package com.example.geetmeena.music;

import android.net.Uri;

class CommonVideo {
    private String videoTtile;
    private String videoAlbum;
    private String videoArtist;
    private String videoDuration;
    private String videoFilePath;
    private String videoId;
    public CommonVideo(String videoTitle, String videoArtist, String videoAlbum, String videoDuration, String videoData, String videoId, String videoDisplayName) {
        this.videoTtile=videoTitle;
        this.videoAlbum=videoAlbum;
        this.videoArtist=videoArtist;
        this.videoDuration=videoDuration;
        this.videoFilePath=videoData;
        this.videoId=videoId;
        this.videoName=videoDisplayName;
    }
    public String getVideoTtile() {
        return videoTtile;
    }

    public void setVideoTtile(String videoTtile) {
        this.videoTtile = videoTtile;
    }

    public String getVideoAlbum() {
        return videoAlbum;
    }

    public void setVideoAlbum(String videoAlbum) {
        this.videoAlbum = videoAlbum;
    }

    public String getVideoArtist() {
        return videoArtist;
    }

    public void setVideoArtist(String videoArtist) {
        this.videoArtist = videoArtist;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    private String videoName;


}
