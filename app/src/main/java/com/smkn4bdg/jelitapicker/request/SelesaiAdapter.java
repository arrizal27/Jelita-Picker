package com.smkn4bdg.jelitapicker.request;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.RequestSetorPengepul;
import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.main.MainActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SelesaiAdapter extends RecyclerView.Adapter<SelesaiAdapter.DiterimaViewHolder> {
    private ArrayList<RequestSetorPengepul> dataSetor;
    FirebaseDatabase dbUser = FirebaseDatabase.getInstance();
    FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
    FirebaseDatabase dbReq = FirebaseDatabase.getInstance();


    public SelesaiAdapter(ArrayList<RequestSetorPengepul> dataSetor){
        this.dataSetor = dataSetor;
    }

    @NonNull
    @Override
    public DiterimaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setoran, parent,false);
        return new DiterimaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiterimaViewHolder holder, int position) {
        RequestSetorPengepul requestSetorPengepul = dataSetor.get(position);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        NumberFormat nm = NumberFormat.getInstance();
        holder.tvpengepul.setText(requestSetorPengepul.getNama_user());
        holder.tvtelepon.setText(requestSetorPengepul.getNo_telp_user());
        holder.tvalamat.setText(requestSetorPengepul.getAlamat_user());
        holder.tvstatus.setText(requestSetorPengepul.getStatus());
        holder.tvtanggalsetor.setText(requestSetorPengepul.getTanggal_setor());
        holder.tvjenispembayaran.setText(requestSetorPengepul.getJenis_pembayaran());
        holder.tvalasan.setText(requestSetorPengepul.getAlasantolak());
        holder.tvalasan.setVisibility(View.GONE);
        holder.txt_alasan.setVisibility(View.GONE);
        holder.tvtotal.setText(nm.format(requestSetorPengepul.getTotal_uang()));
        Picasso.get().load(requestSetorPengepul.getFoto()).into(holder.ivbukti);
        holder.btn_acc.setVisibility(View.GONE);
        holder.btn_tolak.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return dataSetor.size();
    }

    public class DiterimaViewHolder extends RecyclerView.ViewHolder {
        TextView tvpengepul,tvtelepon,tvalamat,tvtanggalsetor,tvjenispembayaran,tvstatus,tvalasan,tvtotal,txt_alasan;
        ImageView ivbukti;
        Button btn_tolak,btn_acc;
        public DiterimaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpengepul = itemView.findViewById(R.id.txt_pengepul);
            tvtelepon = itemView.findViewById(R.id.txt_notelp);
            tvalamat = itemView.findViewById(R.id.txt_alamat);
            tvtanggalsetor = itemView.findViewById(R.id.txt_tgl_setor);
            tvjenispembayaran = itemView.findViewById(R.id.txt_jenis_bayar);
            tvstatus = itemView.findViewById(R.id.txt_status);
            tvalasan = itemView.findViewById(R.id.txt_alasan);
            txt_alasan = itemView.findViewById(R.id.tit_alasan);
            tvtotal = itemView.findViewById(R.id.txt_total);
            ivbukti = itemView.findViewById(R.id.foto_bukti);
            btn_tolak = itemView.findViewById(R.id.btn_tolak);
            btn_acc = itemView.findViewById(R.id.btn_acc);

        }
    }
}

