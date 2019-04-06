package com.example.geetmeena.music;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jeetmeena on 1/23/2018.
 */

interface SolventRecyclerViewAdaptera {
    @SuppressLint("ResourceType")
    SolventRecyclerViewAdapter.SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType);

    void onPointerCaptureChanged(boolean hasCapture);


}
