package com.example.loginfirebase.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginfirebase.HomeActivity;
import com.example.loginfirebase.InfoParkirActivity;
import com.example.loginfirebase.R;
import com.example.loginfirebase.adapter.AdapterParkir;
import com.example.loginfirebase.model.ParkirModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListParkirAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private Button btnAdd;

    Toolbar toolbar;

    //tambahkan kode ini
    private AdapterParkir adapter;
    private ArrayList<ParkirModel> soalList;
//    DatabaseReference dbMahasiswa;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbMahasiswa = database.getReference("Parkir");

    //Mendapatkan referensi dari Database
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public String id_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parkir_admin);

//        dbMahasiswa = FirebaseDatabase.getInstance().getReference("Parkir");


//        dbMahasiswa = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.lv_list);
//        btnAdd = findViewById(R.id.btn_add);
//        btnAdd.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();

        //list mahasiswa
        soalList = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Admin List Parkir");

        }


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                soalList.clear();

                for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
                    ParkirModel questionsModel = mahasiswaSnapshot.getValue(ParkirModel.class);
                    Log.d(TAG, "Value is: " + questionsModel);
                    soalList.add(questionsModel);

//                    ParkirModel picture = singleSnapshot.getValue(Pictures.class);
//                    soalList.add(picture);
                }

                AdapterParkir adapter = new AdapterParkir(ListParkirAdminActivity.this);
                adapter.setSoalList(soalList);

                Log.d(TAG, "Gambar e :" + adapter);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListParkirAdminActivity.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });

        //kode yang ditambahkan
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListParkirAdminActivity.this, EditParkirAdminActivity.class);
                intent.putExtra("list_parkir", soalList.get(i));

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
//        if (item.getItemId() == R.id.miLogout) {
//            Intent intent = new Intent(ListParkirAdminActivity.this, LoginAdmin.class);
//            startActivity(intent);
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.miLogout:
                Intent intent = new Intent(ListParkirAdminActivity.this, LoginAdmin.class);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "StartActiviy 1", Toast.LENGTH_SHORT).show();
                // start activity 1
                return true;
            case R.id.add_parkir:
                Intent i = new Intent(ListParkirAdminActivity.this,CreateParkirAdminActivity.class);
//                Toast.makeText(getApplicationContext(), "StartActiviy 2", Toast.LENGTH_SHORT).show();
                startActivity(i);
//                 start activity 2
                return true;
            default:
                //default intent
                return true;
        }
    }



}