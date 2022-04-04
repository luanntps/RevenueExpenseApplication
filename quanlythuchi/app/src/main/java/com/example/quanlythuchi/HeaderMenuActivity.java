package com.example.quanlythuchi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HeaderMenuActivity extends AppCompatActivity {
    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_menu);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("KEY_NAME", "Default");
        tvName=findViewById(R.id.tvName);
        tvName.setText(name);
    }
}