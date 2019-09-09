package com.example.geetmeena.music;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewReso {

    private Button buttonplay;
   private   Button buttonnext;
    private Button buttonback;
    private TextView songName;
    private TextView seekDuretoin1;
    private TextView songDuretion;
    private TextView artistName;
    private ImageView imageView;
    private SeekBar seekBar;

    public Button getButtonplay() {
        return buttonplay;
    }

    public void setButtonplay(Button buttonplay) {
        this.buttonplay = buttonplay;
    }

    public Button getButtonnext() {
        return buttonnext;
    }

    public void setButtonnext(Button buttonnext) {
        this.buttonnext = buttonnext;
    }

    public Button getButtonback() {
        return buttonback;
    }

    public void setButtonback(Button buttonback) {
        this.buttonback = buttonback;
    }

    public TextView getSongName() {
        return songName;
    }

    public void setSongName(TextView songName) {
        this.songName = songName;
    }

    public TextView getSeekDuretoin1() {
        return seekDuretoin1;
    }

    public void setSeekDuretoin1(TextView seekDuretoin1) {
        this.seekDuretoin1 = seekDuretoin1;
    }

    public TextView getSongDuretion() {
        return songDuretion;
    }

    public void setSongDuretion(TextView songDuretion) {
        this.songDuretion = songDuretion;
    }

    public TextView getArtistName() {
        return artistName;
    }

    public void setArtistName(TextView artistName) {
        this.artistName = artistName;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public ViewReso( ImageView imageView, TextView songName,
                    TextView artistName, TextView songDuretion, TextView seekDuretoin1, SeekBar seekBar) {
        //this.buttonplay=bottomPlay;
        //this.buttonnext=buttonnext;
        //this.buttonback=buttonback;
        this.imageView=imageView;
        this.songName=songName;
        this.artistName=artistName;
        this.songDuretion=songDuretion;
        this.seekDuretoin1=seekDuretoin1;
        this.seekBar=seekBar;


    }
}
