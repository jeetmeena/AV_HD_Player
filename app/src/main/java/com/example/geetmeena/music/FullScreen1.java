package com.example.geetmeena.music;

import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Jeetmeena on 2/16/2018.
 */

interface FullScreen1 {
    void onProgressChanged(SeekBar seekBar, int progress,
                           boolean fromUser);

    void onStartTrackingTouch(SeekBar seekBar);

    void onStopTrackingTouch(SeekBar seekBar);

    void onClick(View view);

    void teSt();
}

