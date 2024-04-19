package com.example.lodingview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private LoadingView loadingSearchView;
    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingSearchView = findViewById(R.id.v_loading);
        loadingSearchView.startLoading();
        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);

        start.setOnClickListener(v -> loadingSearchView.startLoading());
        stop.setOnClickListener(v -> loadingSearchView.stopLoading());
    }
}