package com.smkn4bdg.jelitapicker.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.smkn4bdg.jelitapicker.R;

public class EditPasswordActivity extends AppCompatActivity {

    MaterialButton simpanPw;
    MaterialButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        findView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpanPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditPasswordActivity.this, EditSuccessActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void findView(){
        simpanPw = findViewById(R.id.simpan_pw);
        back = findViewById(R.id.btnkembali);
    }
}