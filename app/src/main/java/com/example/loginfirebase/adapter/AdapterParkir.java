package com.example.loginfirebase.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.loginfirebase.R;
import com.example.loginfirebase.model.ParkirModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

public class AdapterParkir extends BaseAdapter {
    private static Context context;
    private ArrayList<ParkirModel> soalList = new ArrayList<>();

    public void setSoalList(ArrayList<ParkirModel> soalList) {
        this.soalList = soalList;
    }

    public AdapterParkir(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return soalList.size();
    }

    @Override
    public Object getItem(int i) {
        return soalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_card_nama_parkir, viewGroup, false);
        }

        AdapterParkir.ViewHolder viewHolder = new AdapterParkir.ViewHolder(itemView);

        ParkirModel soal = (ParkirModel) getItem(i);
        viewHolder.bind(soal);
        return itemView;
    }

    public static class ViewHolder {
        private TextView txtNama, txtLokasi, txtStatus;
        private TextView txtIdkategori;
        private ImageView imgArah, photo;

        String status;

        public ArrayList<String> a = new ArrayList();

//        private StorageReference mStorageRef;
//        mStorageRef = FirebaseStorage.getInstance().getReference();

        ViewHolder(View view) {
//            txtNama = view.findViewById(R.id.txt_nama);
            txtLokasi   = view.findViewById(R.id.txt_lokasi);
            txtStatus   = view.findViewById(R.id.txt_status);
            photo       = view.findViewById(R.id.photo);

        }

        void bind(ParkirModel soal) {

            txtLokasi.setMaxLines(1);
            txtLokasi.setText(soal.getNama_lokasi());

            txtStatus.setText(soal.getStatus());

            status = soal.getStatus();

//            status = Boolean.valueOf(soal.getStatus());

            if (status.equals("Kosong")){
                photo.setImageResource(R.drawable.available_slot);
            } else {
                photo.setImageResource(R.drawable.full_slot);
            }

        }

    }
}
