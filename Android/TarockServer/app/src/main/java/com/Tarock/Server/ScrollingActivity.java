package com.Tarock.Server;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Server.Service.CommunicationService;
import com.Tarock.Server.databinding.ActivityScrollingBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollingActivity extends AppCompatActivity {
    public boolean isRunning = false;
    public CommunicationService communicationService = null;
    @SuppressLint("StaticFieldLeak")
    public static ActivityScrollingBinding binding;
    public static AppCompatActivity activity;
    public static ScrollingActivity instance = new ScrollingActivity();

    public void log(String message){
        runOnUiThread((Runnable) () -> binding.textView.append(message + "\n"));
    }

    public static ScrollingActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            if (!isRunning)
            {
                isRunning = true;
                binding.fab.setImageResource(android.R.drawable.ic_delete);
                binding.textView.append("Started server ! \n");

                PHPConnection.initPHPConnection();

                communicationService = new CommunicationService();
                communicationService.start();
            }
            else
            {
                isRunning = false;
                binding.fab.setImageResource(android.R.drawable.ic_media_play);
                binding.textView.append("Stopped server ! \n");

                communicationService.interrupt();
                communicationService = null;
            }
        });
    }
}