package com.example.loginfirebase;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuIOT extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    final DatabaseReference DISTANCE = myRef.child("jarak");
    final DatabaseReference DISTANCE2 = myRef.child("jarak");

    final  DatabaseReference LOKASI1 = myRef.child("LOKASI1");

    Boolean HIGH=true;
    Boolean LOW=false;

    Button b1, b2;
    TextView led1, led2,led11, nt, switchStatus, kelembabann, suhuu, coba;
    ImageView image_1,image_2,img_mobil2;
    Button btn;

    String cobatext, lokasi1;

    int angka = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_iot);

        coba = findViewById(R.id.coba);

        image_1 = (ImageView) findViewById(R.id.img_mobil1);
        image_2 = (ImageView) findViewById(R.id.img_mobil2);

        cobatext = "Lokasi";

        lokasi1 = String.valueOf(LOKASI1);

        ///status lampu dan sanyo
        DISTANCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
                Log.d("file", "Value is: " + value);

                ///mySwitchSanyo.setChecked(Boolean.parseBoolean(value));
                if (value == 1){
                    coba.setText(Integer.parseInt(String.valueOf(DISTANCE))); // type data int

                    coba.setVisibility(View.INVISIBLE); //menghilangka text

                   // coba.setVisibility(View.VISIBLE); // menampilkan kembali

                    coba.setText(cobatext); //type data firebase string

                    image_1.setImageResource(R.drawable.trans_on);
                    ((TransitionDrawable)image_1.getDrawable()).startTransition(3000);

                    ((TransitionDrawable)image_1.getDrawable()).startTransition(3000);
                } else if (value == 0){

                    coba.setText(Integer.parseInt(String.valueOf(DISTANCE)));

                    image_1.setImageResource(R.drawable.trans_off);

                    coba.setVisibility(View.VISIBLE);

                    ((TransitionDrawable)image_1.getDrawable()).startTransition(3000);

                    ((TransitionDrawable)image_1.getDrawable()).startTransition(3000);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("File", "Failed to read value.", error.toException());

            }
        });

        // start
        DISTANCE2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
                Log.d("file", "Value is: " + value);

                ///mySwitchSanyo.setChecked(Boolean.parseBoolean(value));
                if (value == 1){

                    image_2.setImageResource(R.drawable.trans_on);
                    ((TransitionDrawable)image_2.getDrawable()).startTransition(3000);

                    ((TransitionDrawable)image_2.getDrawable()).startTransition(3000);
                } else if (value == 0){
                    image_2.setImageResource(R.drawable.trans_off);
                    ((TransitionDrawable)image_2.getDrawable()).startTransition(3000);

                    ((TransitionDrawable)image_2.getDrawable()).startTransition(3000);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("File", "Failed to read value.", error.toException());

            }
        });
        /// end





    }




}
