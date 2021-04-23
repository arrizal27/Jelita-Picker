package com.smkn4bdg.jelitapicker.request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.RequestSetorPengepul;
import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.R;

import java.util.ArrayList;

public class DiterimaFragment extends Fragment {
    private ArrayList<RequestSetorPengepul> dataRequest;
    private RecyclerView recyclerView;
    private FirebaseDatabase mfirebaseInstance;
    private DatabaseReference dbUser,dbReq;

    public DiterimaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diterima, container, false);
        recyclerView = view.findViewById(R.id.list_diterima);
        recyclerView.setHasFixedSize(true);
        dataRequest = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getdata();


        return view;
    }
    private void getdata(){
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String id = auth.getUid();
        mfirebaseInstance = FirebaseDatabase.getInstance();
        dbUser = mfirebaseInstance.getReference();
//        dbUser.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot mdatasnap : snapshot.getChildren()) {
//                    User user = mdatasnap.getValue(User.class);
//                    dbReq = mfirebaseInstance.getReference();
//                    dbReq.child("requestSetorUser").child(user.getId()).orderByChild("id_pengepul").equalTo(id)
//                            .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()){
//                                for (DataSnapshot datasnap : snapshot.getChildren()){
//                                    RequestSetorPengepul requestSetorPengepul = datasnap.getValue(RequestSetorPengepul.class);
//                                    dataRequest.add(requestSetorPengepul);
//                                }
//                                AllAdapter diterimaAdapter = new AllAdapter(dataRequest);
//                                recyclerView.setAdapter(diterimaAdapter);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        dbReq = mfirebaseInstance.getReference();
        dbReq.child("requestSetorPengepul").child(id).orderByChild("status").equalTo("Diterima").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()>0){
                    for (DataSnapshot datasnap : snapshot.getChildren()){
                        RequestSetorPengepul requestSetorPengepul = datasnap.getValue(RequestSetorPengepul.class);
                        dataRequest.add(requestSetorPengepul);
                    }
                    DiterimaAdapter diterimaAdapter = new DiterimaAdapter(DiterimaFragment.this.getContext(),dataRequest);
                    recyclerView.setAdapter(diterimaAdapter);
                    diterimaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}