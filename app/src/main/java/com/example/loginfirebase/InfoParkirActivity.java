package com.example.loginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.loginfirebase.model.ParkirModel;

public class InfoParkirActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tx_nama, tx_lokasi, tx_status, tx_jarak;
    Button btn_ceklokasiLaundry, btn_back;
    ImageView photo;

    ParkirModel parkirModel;

    String id_laundry;

    Double latit, longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_parkir);

        parkirModel = getIntent().getParcelableExtra("list_parkir");

//        if (ParkirModel != null) {
//            id_laundry = parkirModel.get();
//        } else {
//            namaLaundry = new NamaLaundry();
//        }

        tx_nama = findViewById(R.id.nama_lokasi);
        tx_lokasi = findViewById(R.id.lokasi);
        tx_status = findViewById(R.id.status);
        photo   = findViewById(R.id.photo);

        btn_back = findViewById(R.id.backbutton);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoParkirActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        btn_ceklokasiLaundry = findViewById(R.id.btn_ceklokasilaundry);

        tx_nama.setText(parkirModel.getNama());
        tx_lokasi.setText(parkirModel.getNama_lokasi());
        tx_status.setText(parkirModel.getStatus());

        if (parkirModel.getStatus().equals("Penuh")){
            btn_ceklokasiLaundry.setVisibility(View.INVISIBLE);
        } else {
            btn_ceklokasiLaundry.setVisibility(View.VISIBLE);
        }

        btn_ceklokasiLaundry.setOnClickListener(this);

        Glide.with(this)
                .load(parkirModel.getPhoto())
                .into(photo);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ceklokasilaundry:

                String loc = parkirModel.getLokasi();
                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent l = new Intent(Intent.ACTION_VIEW, addressUri);
                if (l.resolveActivity(getPackageManager()) != null) {
                    startActivity(l);
                }else {
                    Log.d("ImplicitIntent","Cant Handle This Intent");
                }
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.f/Math.PI);

        float longitude1 = lat_a / pk;
        float latitude1 = lng_a / pk;
        float longitude2 = lat_b / pk;
        float latitude2 = lng_b / pk;

        double t1 = Math.cos(longitude1) * Math.cos(latitude1) * Math.cos(longitude2) * Math.cos(latitude2);
        double t2 = Math.cos(longitude1) * Math.sin(latitude1) * Math.cos(longitude2) * Math.sin(latitude2);
        double t3 = Math.sin(longitude1) * Math.sin(longitude2);
        double tt = Math.acos(t1 + t2 + t3);

//        Log.d("jarak" + tt.toString)
        return 6366000 * tt;
    }

    public static void getJarak(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radious of the earth
        double latDistance = toRad(lat2-lat1);
        double lonDistance = toRad(lon2-lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

    }

    private static Double toRad(double value) {
        return value * Math.PI / 180;
    }
}