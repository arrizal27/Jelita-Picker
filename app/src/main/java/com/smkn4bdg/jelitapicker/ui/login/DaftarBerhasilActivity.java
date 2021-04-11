package com.smkn4bdg.jelitapicker.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.main.MainActivity;

public class DaftarBerhasilActivity extends AppCompatActivity {
    Button btnsuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_berhasil);

        findView();

        btnsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DaftarBerhasilActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void findView(){
        btnsuccess = findViewById(R.id.btn_daftar_berhasil);

        btnsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBerhasilActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}