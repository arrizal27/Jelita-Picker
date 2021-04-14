//package com.smkn4bdg.jelitapicker.ui.setor;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.button.MaterialButton;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.smkn4bdg.jelitapicker.Models.RequestSetorPengepul;
//import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
//import com.smkn4bdg.jelitapicker.Models.SpinnerPengepul;
//import com.smkn4bdg.jelitapicker.R;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class SetorActivity extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//    ImageView fotoBukti;
//    Spinner listPengepul, listMetodeBayar;
//    MaterialButton btnUpload;
//    MaterialButton btnBack;
//    MaterialButton btnSetorNow;
//
//
//    DatabaseReference dbPengepul;
//    DatabaseReference dbRequestSetorUser;
//    DatabaseReference dbRequestSetorUserFinal;
//    DatabaseReference dbRequestSetorPengepul;
//    DatabaseReference dbRequestSetorPengepulFinal;
//    DatabaseReference dbUser;
//    StorageReference storageReference;
//    private Uri imageUri;
//    FirebaseUser mUser;
//    TextView id_picker, no_picker;
//    ArrayList<SpinnerPengepul> pengepul;
//
//    private String namaPengepul;
//    private String noPengepul;
//    private String idPengepul;
//
//
//    String id_pul, no_pul, alamat_pul;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setor);
//
//        findView();
//
//        dbPengepul = FirebaseDatabase.getInstance().getReference("pengepul");
//        dbRequestSetorUser = FirebaseDatabase.getInstance().getReference("requestSetorUser");
//        dbRequestSetorUserFinal = dbRequestSetorUser.push();
//        dbRequestSetorPengepul = FirebaseDatabase.getInstance().getReference("requestSetorPengepul");
//        dbRequestSetorPengepulFinal = dbRequestSetorPengepul.push();
//
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//        fetchData();
//
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openFileChooser();
//
//            }
//        });
//
//        btnSetorNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //button setor sekarang diklik
//                //uploadImage();
//                storeDataUserRequest();
//                storeDataPengepulRequest();
//
//                Intent i = new Intent(SetorActivity.this, SetorBerhasilActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//    }
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, 3);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 3 && resultCode == RESULT_OK) {
//            imageUri = data.getData();
//            fotoBukti.setImageURI(imageUri);
//
//        }
//    }
//
//
//    private void storeDataUserRequest() {
//        FirebaseApp.initializeApp(this);
//        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        dbUser = FirebaseDatabase.getInstance().getReference("users");
//
//        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String image = uri.toString();
//                        fotoBukti.setImageResource(R.drawable.ic_baseline_image_24);
//                        dbUser.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Date c = Calendar.getInstance().getTime();
//                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                                String formatdate = sdf.format(c);
//
//                                String metode_bayar = listMetodeBayar.getSelectedItem().toString();
//                                double total_uang = 0;
//
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    dbUser.child(mUser.getUid()).child("jml_minyak").setValue(0);
//                                    String id_storeData = dbRequestSetorUserFinal.getKey();
//                                    String id_user = mUser.getUid();
//                                    String nama_user = dataSnapshot.child("nama").getValue().toString();
//                                    String alamat_user = dataSnapshot.child("alamat").getValue().toString();
//                                    String tanggal_setor = formatdate;
//
//
//                                    String nama_pengepuls = SetorActivity.this.namaPengepul;
//
//                                    String nomor_pengepul = SetorActivity.this.noPengepul;
//                                    System.out.println("DATAAAA" + nama_pengepuls);
//                                    String foto_bukti = image;
//                                    String jenis_pembayaran = metode_bayar;
//                                    String alasan_tolak = "Gk papa";
//                                    String status = "Pending";
//                                    if (dataSnapshot.child("id").getValue().equals(mUser.getUid())) {
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Rumah Tangga")) {
//                                            total_uang = 15000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Pedagang")) {
//                                            total_uang = 30000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Cafe dan Rumah Makan")) {
//                                            total_uang = 45000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Hotel dan Penginapan")) {
//                                            total_uang = 60000;
//                                        }
//                                    }
//                                    dbRequestSetorUserFinal = FirebaseDatabase.getInstance().getReference("requestSetorUser").child(id_user).child(id_storeData);
//                                    RequestSetorUser requestSetorUser1 = new RequestSetorUser(id_storeData, nama_pengepuls, nomor_pengepul, alamat_user,
//                                            tanggal_setor, foto_bukti, jenis_pembayaran, alasan_tolak, total_uang, status);
//                                    System.out.println(requestSetorUser1);
//                                    dbRequestSetorUserFinal.setValue(requestSetorUser1);
//
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//                    }
//                });
//            }
//        });
//
//        System.out.println(namaPengepul);
//
//        System.out.println("KELUARRRRR");
//
//    }
//
//    private void storeDataPengepulRequest() {
//        FirebaseApp.initializeApp(this);
//        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        dbUser = FirebaseDatabase.getInstance().getReference("users");
//
//        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String image = uri.toString();
//
//                        dbUser.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Date c = Calendar.getInstance().getTime();
//                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                                String formatdate = sdf.format(c);
//                                String namaUser = null, alamatUser = null, noTelpUser = null, tanggalSetor = null, Foto= null;
//                                String metode_bayar = listMetodeBayar.getSelectedItem().toString();
//                                double total_uang = 0;
//
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                    String id_storeData = dbRequestSetorPengepulFinal.getKey();
//                                    tanggalSetor = formatdate;
//                                    Foto = image;
//                                    if (dataSnapshot.child("id").getValue().equals(mUser.getUid())) {
//                                        namaUser = dataSnapshot.child("nama").getValue().toString();
//                                        alamatUser = dataSnapshot.child("alamat").getValue().toString();
//                                        noTelpUser = dataSnapshot.child("no_tlp").getValue().toString();
//
//
//
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Rumah Tangga")) {
//                                            total_uang = 15000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Pedagang")) {
//                                            total_uang = 30000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Cafe dan Rumah Makan")) {
//                                            total_uang = 45000;
//                                        }
//                                        if (dataSnapshot.child("role").getValue().toString().equals("Hotel dan Penginapan")) {
//                                            total_uang = 60000;
//                                        }
//
//                                    }
//                                    String id_pengepul = SetorActivity.this.idPengepul;
//                                    String status = "Pending";
//                                    dbRequestSetorPengepulFinal = FirebaseDatabase.getInstance().getReference("requestSetorPengepul").child(id_pengepul).child(id_storeData);
//                                    RequestSetorPengepul requestSetorPengepul = new RequestSetorPengepul(id_storeData,namaUser,alamatUser,noTelpUser,tanggalSetor,Foto,total_uang,status);
//                                    dbRequestSetorPengepulFinal.setValue(requestSetorPengepul);
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//
//                    }
//                });
//            }
//        });
//
//
//    }
//
//
//    private void findView() {
//        listPengepul = findViewById(R.id.list_pengepul);
//        listMetodeBayar = findViewById(R.id.list_metode_bayar);
//        fotoBukti = findViewById(R.id.view_foto_bukti);
//        btnUpload = findViewById(R.id.upload_foto);
//        btnBack = findViewById(R.id.btn_back);
//        btnSetorNow = findViewById(R.id.btn_setor_now);
//        id_picker = findViewById(R.id.id_picker);
//        no_picker = findViewById(R.id.no_telp_pengepul);
//    }
//
//    private void getPengepul(String nama_pengepul) {
//        dbPengepul.orderByChild("nama").equalTo(nama_pengepul).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    id_pul = dataSnapshot.child("nama").getValue().toString();
//                    no_pul = dataSnapshot.child("no_telp").getValue().toString();
//                    alamat_pul = dataSnapshot.child("alamat").getValue().toString();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void fetchData() {
//        pengepul = new ArrayList<SpinnerPengepul>();
//        ArrayList<String> listPengepuls = new ArrayList<String>();
//        dbPengepul.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(final DataSnapshot snapshot) {
//                final List<String> pengepulItems = new ArrayList<String>();
//                for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {
////                    final String pengepulName = dataSnapshot.child("nama").getValue(String.class);
////                    pengepulItems.add(pengepulName);
//
//                    SpinnerPengepul Sp = dataSnapshot.getValue(SpinnerPengepul.class);
//                    listPengepuls.add(Sp.getNama());
//                    pengepul.add(Sp);
//
//
//                }
//                ArrayAdapter<String> pengepulAdapter = new ArrayAdapter<String>(SetorActivity.this,
//                        android.R.layout.simple_spinner_item, listPengepuls);
//                pengepulAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                listPengepul.setAdapter(pengepulAdapter);
//                //SpinnerPengepulAdapter c = new SpinnerPengepulAdapter(SetorActivity.this,.getNama_pengepul(),pengepul.get(1).getNo_telp(),pengepul.get(2).getAlamat());
////                ArrayAdapter<String> adapterPengepul = new ArrayAdapter<String>(SetorActivity)
////                listPengepul.setAdapter(c);
//                listPengepul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        idPengepul = pengepul.get(i).getId();
//                        noPengepul = pengepul.get(i).getNo_telp();
//                        namaPengepul = pengepul.get(i).getNama();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        String[] metode_bayar = {"Bayar Langsung", "Credit"};
//
//        ArrayAdapter<String> metodeAdapter = new ArrayAdapter<String>(SetorActivity.this, android.R.layout.simple_spinner_item, metode_bayar);
//        metodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        listMetodeBayar.setAdapter(metodeAdapter);
//    }
//
//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//}