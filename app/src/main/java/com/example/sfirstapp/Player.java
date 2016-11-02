package com.example.sfirstapp;

import com.example.sfirstapp.model.Recording;

/**
 * Created by Moritz on 31.10.2016.
 */

public interface Player {
    public void play(String fileName);
    public void play(Recording recording);
}
