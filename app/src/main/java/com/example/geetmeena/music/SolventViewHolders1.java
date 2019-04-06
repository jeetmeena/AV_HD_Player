package com.example.geetmeena.music;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Jeetmeena on 1/25/2018.
 */

interface SolventViewHolders1 extends View.OnClickListener {
    void onClick2(View view);


    boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id);

    void onLongClick(View view);
}
