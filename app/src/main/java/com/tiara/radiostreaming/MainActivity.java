package com.tiara.radiostreaming;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    Button play, stop;
    MediaPlayer mediaPlayer;
    String url_radio = ""; //tempat url radio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // UI = user interfcae = tampilan aplikasi
        UI();
        setPutar(); // membuat method baru

    }

    private void setPutar() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url_radio);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                progressBar.setIndeterminate(false);
                progressBar.setSecondaryProgress(100);
            }
        });
    }

    private void UI() {
        progressBar = (ProgressBar)findViewById(R.id.loading);
        progressBar.setMax(100);//for how many persen of loading
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);

        play = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);

        stop.setEnabled(false);
        url_radio = "http://103.16.199.47:9160/";


    }

    @Override
    public void onClick(View v) {
        if (v == play){
            Memutar();// membuat method baru
        }else if (v == stop){
            Berhenti(); // membuat method baru lagi
            play.setEnabled(true);
            stop.setEnabled(false);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void Berhenti() {
        if (mediaPlayer == null) return;
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
            setPutar();
        }catch (IllegalStateException e){

        }
    }

    private void Memutar() {
        play.setEnabled(false);
        stop.setEnabled(true);
        progressBar.setVisibility(View.VISIBLE);

        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}
