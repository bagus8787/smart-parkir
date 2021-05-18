package com.example.loginfirebase.Admin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.loginfirebase.HomeActivity;
import com.example.loginfirebase.InfoParkirActivity;
import com.example.loginfirebase.R;
import com.example.loginfirebase.model.ParkirModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditParkirAdminActivity extends AppCompatActivity implements View.OnClickListener{

    static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    TextView tx_nama, tx_lokasi, tx_status, tx_jarak;

    EditText edt_nama, edt_lokasi, edt_nama_lokasi;
    Button btn_ceklokasiLaundry, btn_pesanLaundry, btnUpdate, btn_back;
    ImageView photo, img_cek_lokasi;

    String Id_parkir;

    ParkirModel parkirModel;

    DatabaseReference mDatabase;

    Double latit, longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parkir_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        parkirModel = getIntent().getParcelableExtra("list_parkir");

        edt_nama = findViewById(R.id.edt_nama);
        edt_nama_lokasi = findViewById(R.id.edt_nama_lokasi);
        edt_lokasi = findViewById(R.id.edt_lokasi);

        photo   = findViewById(R.id.photo);

        img_cek_lokasi = findViewById(R.id.img_cek_lokasi);
        img_cek_lokasi.setOnClickListener(this);

        btn_ceklokasiLaundry = findViewById(R.id.btn_ceklokasilaundry);
        btn_ceklokasiLaundry.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        btn_back = findViewById(R.id.backbutton);
        btn_back.setOnClickListener(this);

        edt_nama.setText(parkirModel.getNama());
        edt_nama_lokasi.setText(parkirModel.getNama_lokasi());
        edt_lokasi.setText(parkirModel.getLokasi());

        if (parkirModel != null) {
            Id_parkir = parkirModel.getId();
        } else {
            parkirModel = new ParkirModel();
        }

//        Id_parkir = parkirModel.getId();

        Glide.with(this)
                .load(parkirModel.getPhoto())
                .into(photo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Info Parkir");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ceklokasilaundry:
                getCurrentLocation();
                break;

            case R.id.img_cek_lokasi:
                String loc = parkirModel.getLokasi();
                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent l = new Intent(Intent.ACTION_VIEW, addressUri);
                if (l.resolveActivity(getPackageManager()) != null) {
                    startActivity(l);
                }else {
                    Log.d("ImplicitIntent","Cant Handle This Intent");
                }
                break;

            case R.id.backbutton:
                Intent i = new Intent(EditParkirAdminActivity.this, ListParkirAdminActivity.class);
                startActivity(i);
                break;

            case R.id.btn_update:
                updateParkir();

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void updateParkir() {
        String nama = edt_nama.getText().toString().trim();
        String lokasi = edt_lokasi.getText().toString().trim();
        String nama_lok = edt_nama_lokasi.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edt_nama.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(lokasi)) {
            isEmptyFields = true;
            edt_lokasi.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(nama_lok)) {
            isEmptyFields = true;
            edt_nama_lokasi.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(EditParkirAdminActivity.this, "Informasi berhasil di perbarui...", Toast.LENGTH_SHORT).show();

            parkirModel.setNama(nama);
            parkirModel.setLokasi(lokasi);
            parkirModel.setNama_lokasi(nama_lok);

            DatabaseReference dbMahasiswa = mDatabase.child("Parkir");

            //update data
            dbMahasiswa.child(Id_parkir).setValue(parkirModel);

            finish();

        }
    }

    private String getCityAndProvince(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        String address = null;
        geocoder = new Geocoder(EditParkirAdminActivity.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());

            edt_lokasi.setText(latitude + "," + longitude);
            address = addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String currentLocation = address;
        return currentLocation;
    }

    private void getCurrentLocation() {

        final LocationManager locationManager = (LocationManager) EditParkirAdminActivity.this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    try {
                        Log.d("cek", "onLocationChanged: " + getCityAndProvince(location));
                        edt_nama_lokasi.setText(getCityAndProvince(location));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditParkirAdminActivity.this);

                // set title dialog
                alertDialogBuilder.setTitle("GPS mati");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Apakah ingin menghidupkan GPS?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol diklik, maka akan menutup activity ini
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol ini diklik, akan menutup dialog
                                // dan tidak terjadi apa2
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        };

        if (ActivityCompat.checkSelfPermission(EditParkirAdminActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(EditParkirAdminActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 50, locationListener);
    }
}