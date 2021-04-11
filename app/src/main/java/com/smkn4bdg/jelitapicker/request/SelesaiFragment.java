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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
import com.smkn4bdg.jelitapicker.R;

import java.util.ArrayList;

public class SelesaiFragment extends Fragment {
    private ArrayList<RequestSetorUser> dataRequest;
    private RecyclerView recyclerView;

    public SelesaiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        recyclerView = view.findViewById(R.id.list_selesai);
        recyclerView.setHasFixedSize(true);
        dataRequest = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getdata();


        return view;
    }
    private void getdata(){
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String id = auth.getUid();
        Query q = FirebaseDatabase.getInstance().getReference("requestSetorUser").child(id).orderByChild("status_selesai").equalTo(true);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot datasnap : snapshot.getChildren()){
                        RequestSetorUser requestSetorUser = datasnap.getValue(RequestSetorUser.class);
                        dataRequest.add(requestSetorUser);
                    }
                    SelesaiAdapter selesaiAdapter = new SelesaiAdapter(dataRequest);
                    recyclerView.setAdapter(selesaiAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}