package com.smkn4bdg.jelitapicker.ui.setor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
import com.smkn4bdg.jelitapicker.Models.SpinnerPengepul;
import com.smkn4bdg.jelitapicker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SetorActivity extends AppCompatActivity{
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView fotoBukti;
    Spinner listPengepul, listMetodeBayar;
    String currentPhotoPath;
    MaterialButton btnUpload;
    MaterialButton btnBack;
    MaterialButton btnSetorNow;
    EditText test;

    DatabaseReference dbPengepul ;
    DatabaseReference dbRequestSetor ;
    DatabaseReference dbRequestSetorFinal;
    DatabaseReference dbUser;
    private StorageReference storageReference;
    private Uri imageUri;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        findView();

        dbPengepul = FirebaseDatabase.getInstance().getReference("pengepul");
        dbRequestSetor = FirebaseDatabase.getInstance().getReference("requestSetorUser");
        dbRequestSetorFinal = dbRequestSetor.push();
        dbUser = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("imageBukti");
        fetchData();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });

        btnSetorNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button setor sekarang diklik
                //uploadImage();
                storeDataRequest();


//                Intent i = new Intent(SetorActivity.this, SetorBerhasilActivity.class);
//                startActivity(i);
//                finish();
            }
        });

    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            fotoBukti.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

//    private void uploadImage(){
//        if (imageUri != null){
//            StorageReference fileReference = storageReference.child("imageBukti/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//         fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//             @Override
//             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                 RequestSetor requestSetor = new RequestSetor(taskSnapshot.getStorage().getDownloadUrl().toString());
//             }
//         }).addOnFailureListener(new OnFailureListener() {
//             @Override
//             public void onFailure(@NonNull Exception e) {
//                 Toast.makeText(SetorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//             }
//         }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//             @Override
//             public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//
//             }
//         });
//
//        }
//        else {
//            Toast.makeText(this, "Pilih Image Bukti...", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void storeDataRequest(){
        SpinnerPengepul spinnerPengepul = new SpinnerPengepul();

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        String nama_pengepul;
        String no_telp_pengepul;



        listPengepul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPengepul.setNama_pengepul(listPengepul.getSelectedItem().toString());
                spinnerPengepul.setNo_telp(listPengepul.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formatdate = sdf.format(c);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("id").equals(mUser.getUid())){
                        dbUser.child("users").child(mUser.getUid()).child("jml_minyak").setValue(0);
                    }
                        String id_storeData = dbRequestSetorFinal.getKey();
                        String id_user = mUser.getUid();
                       String alamat_user = dataSnapshot.child("alamat").getValue().toString();
                       String tanggal_setor = formatdate;
                       String foto_bukti = "ini";
                       String jenis_pembayaran  = "bayar";

                       dbRequestSetorFinal = FirebaseDatabase.getInstance().getReference("requestSetorUser").child(id_user).child(id_storeData);
                       RequestSetorUser requestSetorUser1 = new RequestSetorUser(id_storeData,spinnerPengepul.getNama_pengepul(),spinnerPengepul.getId_pengepul(), mUser.getDisplayName(),spinnerPengepul.getNo_telp(),alamat_user,
                               tanggal_setor,foto_bukti,jenis_pembayaran,"",false,false,true,false);
                       System.out.println(requestSetorUser1);
                       dbRequestSetorFinal.setValue(requestSetorUser1);
                       
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    System.out.println("KELUARRRRR");

    }




    private void findView(){
        listPengepul = findViewById(R.id.list_pengepul);
        listMetodeBayar = findViewById(R.id.list_metode_bayar);
        fotoBukti = findViewById(R.id.view_foto_bukti);
        btnUpload = findViewById(R.id.upload_foto);
        btnBack = findViewById(R.id.btn_back);
        btnSetorNow = findViewById(R.id.btn_setor_now);
    }

    private void fetchData(){
        dbPengepul.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               final List<SpinnerPengepul> pengepul = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id_pengepul = dataSnapshot.child("id").getValue().toString();
                    String nama_pengepul = dataSnapshot.child("nama").getValue().toString();
                    String no_telp = dataSnapshot.child("no_telp").getValue().toString();
                    String alamat = dataSnapshot.child("alamat").getValue().toString();

                    pengepul.add(new SpinnerPengepul(id_pengepul,nama_pengepul,no_telp,alamat));
//                    pengepul.add(no_telp);
//                    pengepul.add(alamat);

                }
                ArrayAdapter<SpinnerPengepul> pengepulAdapter = new ArrayAdapter<SpinnerPengepul>(SetorActivity.this,
                        android.R.layout.simple_spinner_item,pengepul);
                pengepulAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listPengepul.setAdapter(pengepulAdapter);
                //SpinnerPengepulAdapter c = new SpinnerPengepulAdapter(SetorActivity.this,.getNama_pengepul(),pengepul.get(1).getNo_telp(),pengepul.get(2).getAlamat());
//                ArrayAdapter<String> adapterPengepul = new ArrayAdapter<String>(SetorActivity)
//                listPengepul.setAdapter(c);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}