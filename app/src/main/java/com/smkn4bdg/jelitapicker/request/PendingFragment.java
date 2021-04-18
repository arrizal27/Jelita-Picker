package com.smkn4bdg.jelitapicker.request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.smkn4bdg.jelitapicker.R;

import java.util.ArrayList;

public class PendingFragment extends Fragment {
    private ArrayList<RequestSetorPengepul> dataRequest;
    private RecyclerView recyclerView;
    private DatabaseReference dbUser,dbReq;
    private FirebaseDatabase mfirebaseInstance;
    private Button acc;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        recyclerView = view.findViewById(R.id.list_pending);
        recyclerView.setHasFixedSize(true);
        dataRequest = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getdata();


        return view;
    }
    private void getdata(){
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("requestSetorPengepul");
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String id = auth.getUid();
        dbref.child(id).orderByChild("status").equalTo("Pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot datasnap : snapshot.getChildren()){
                        RequestSetorPengepul requestSetorPengepul = datasnap.getValue(RequestSetorPengepul.class);
                        dataRequest.add(requestSetorPengepul);
                    }
                    PendingAdapter pendingAdapter = new PendingAdapter(dataRequest);
                    recyclerView.setAdapter(pendingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}