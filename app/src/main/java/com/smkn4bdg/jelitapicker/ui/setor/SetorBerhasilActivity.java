package com.smkn4bdg.jelitapicker.ui.setor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.request.RiwayatActivity;

public class SetorBerhasilActivity extends AppCompatActivity {
    MaterialButton success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor_berhasil);

        success = findViewById(R.id.setor_success);
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SetorBerhasilActivity.this, RiwayatActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}