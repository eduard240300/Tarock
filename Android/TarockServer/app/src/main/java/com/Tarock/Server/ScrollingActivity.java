package com.Tarock.Server;

import android.os.Bundle;

import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Server.Service.CommunicationService;
import com.Tarock.Server.databinding.ActivityScrollingBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import java.io.FileNotFoundException;

public class ScrollingActivity extends AppCompatActivity {
    public boolean isRunning = false;
    public CommunicationService communicationService = null;
    public static ActivityScrollingBinding binding;
    public static AppCompatActivity activity;

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning)
                {
                    isRunning = true;
                    binding.fab.setImageResource(android.R.drawable.ic_delete);
                    binding.textView.append("Started server ! \n");

                    try {
                        PHPConnection.initPHPConnection();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

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
            }
        });
    }
}