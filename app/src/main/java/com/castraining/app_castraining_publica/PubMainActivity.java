package com.castraining.app_castraining_publica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.castraining.app_castraining_publica.databinding.ActivityPubMainBinding;

public class PubMainActivity extends AppCompatActivity  {

    private ActivityPubMainBinding pubMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pubMainBinding = ActivityPubMainBinding.inflate(getLayoutInflater());
        setContentView(pubMainBinding.getRoot());


    }
}