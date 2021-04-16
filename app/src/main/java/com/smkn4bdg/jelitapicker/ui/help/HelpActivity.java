package com.smkn4bdg.jelitapicker.ui.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.main.MainActivity;

public class HelpActivity extends AppCompatActivity {

    MaterialCardView btnabout,btntutor,btntanya,btnkembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnkembali = findViewById(R.id.btn_kembali);
        btnabout = findViewById(R.id.card1);
        btntutor = findViewById(R.id.card2);
        btntanya = findViewById(R.id.card3);

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        btntutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, TutorialActivity.class);
                startActivity(i);
            }
        });

        btntanya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });
    }
}