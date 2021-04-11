package com.smkn4bdg.jelitapicker.request;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smkn4bdg.jelitapicker.Models.RequestSetorUser;
import com.smkn4bdg.jelitapicker.R;

import java.util.ArrayList;

public class DitolakAdapter extends RecyclerView.Adapter<DitolakAdapter.DitolakViewHolder> {
    private ArrayList<RequestSetorUser> dataSetor;

    public DitolakAdapter(ArrayList<RequestSetorUser> dataSetor){
        this.dataSetor = dataSetor;
    }

    @NonNull
    @Override
    public DitolakViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setoran, parent,false);
        return new DitolakViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DitolakViewHolder holder, int position) {
        RequestSetorUser requestSetorUser = dataSetor.get(position);
        holder.tvpengepul.setText(requestSetorUser.getNama_pengepul());
        holder.tvtelepon.setText(requestSetorUser.getNo_telp_pengepul());
        holder.tvalamat.setText(requestSetorUser.getAlamat_user());
        holder.tvstatus.setText("Ditolak");
        holder.tvtanggalsetor.setText(requestSetorUser.getTanggal_setor());
        holder.tvjenispembayaran.setText(requestSetorUser.getJenis_pembayaran());
        holder.tvalasan.setText(requestSetorUser.getAlasantolak());

    }

    @Override
    public int getItemCount() {
        return dataSetor.size();
    }

    public class DitolakViewHolder extends RecyclerView.ViewHolder {
        TextView tvpengepul,tvtelepon,tvalamat,tvtanggalsetor,tvjenispembayaran,tvstatus,tvalasan;
        ImageView ivbukti;
        public DitolakViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpengepul = itemView.findViewById(R.id.txt_pengepul);
            tvtelepon = itemView.findViewById(R.id.txt_notelp);
            tvalamat = itemView.findViewById(R.id.txt_alamat);
            tvtanggalsetor = itemView.findViewById(R.id.txt_tgl_setor);
            tvjenispembayaran = itemView.findViewById(R.id.txt_jenis_bayar);
            tvstatus = itemView.findViewById(R.id.txt_status);
            tvalasan = itemView.findViewById(R.id.txt_alasan);


        }
    }
}
