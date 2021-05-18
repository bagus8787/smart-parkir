package com.example.loginfirebase.Admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginfirebase.Login;
import com.example.loginfirebase.R;
import com.example.loginfirebase.model.ParkirModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreateParkirAdminActivity extends AppCompatActivity implements View.OnClickListener{
    // Creating URI.
    Uri FilePathUri;

    int Image_Request_Code = 7;

    // Folder path for Firebase Storage.
    String Storage_Path = "";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private ParkirModel parkirModel;

    private EditText nama, lokasi, nama_image, nama_lokasi_parkir;
    private Button btn_add, btn_pilih;
    private ImageView SelectImage;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parkir_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Parkir");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        nama_lokasi_parkir = findViewById(R.id.nama_lokasi_adm);
        nama = findViewById(R.id.nama_adm);
        lokasi = findViewById(R.id.lokasi_adm);
        nama_image = findViewById(R.id.img_adm);

        SelectImage = findViewById(R.id.image);

        btn_add = findViewById(R.id.btn_add);
        btn_pilih = findViewById(R.id.btn_pilih);

        btn_add.setOnClickListener(this);

        // Adding click listener to Choose image button.
        btn_pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        parkirModel = new ParkirModel();

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(CreateParkirAdminActivity.this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
//            saveParkir();
            UploadImageFileToFirebaseStorage();

            Intent intent = new Intent(CreateParkirAdminActivity.this, ListParkirAdminActivity.class);
//            intent.putExtra("id_kategori", kategori_id);
            startActivity(intent);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                btn_pilih.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            final StorageReference storageReference2nd = mStorageRef.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // get the image Url of the file uploaded
                            storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // getting image uri and converting into string
                                    Uri downloadUrl = uri;

                                }
                            });
//                            Uri downloadUrl = FilePathUri.getDownloadUrl();  // here is Url for photo
//                            Task<Uri> urlTask = taskSnapshot.getUploadSessionUri().getDownloadUrl();

                            // Getting image name from EditText and store into string variable.
                            String TempImageName = nama_image.getText().toString().trim();
                            String namaa = nama.getText().toString().trim();
                            String lokasii = lokasi.getText().toString().trim();
                            String status = "Kosong";
                            String id = "LOKASI 5";

                            String nama_lokasi_parkir2 = nama_lokasi_parkir.getText().toString().trim();

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ParkirModel imageUploadInfo = new ParkirModel(namaa, nama_lokasi_parkir2, lokasii, status, storageReference2nd.getDownloadUrl().toString(), TempImageName, id.toString());

                            // Getting image upload ID.
                            String ImageUploadId = mDatabase.push().getKey();

                            Log.d("urutan", String.valueOf(imageUploadInfo));

                            // Adding image upload id s child element into databaseReference.
//                            mDatabase.child(ImageUploadId).setValue(imageUploadInfo);
                            mDatabase.child(id).setValue(imageUploadInfo);

                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(CreateParkirAdminActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(CreateParkirAdminActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    private void saveParkir() {

        String namaa = nama.getText().toString().trim();
        String lokasii = lokasi.getText().toString().trim();

        String nama_lokasi_parkir2 = nama_lokasi_parkir.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(namaa)) {
            isEmptyFields = true;
            nama.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(lokasii)) {
            isEmptyFields = true;
            lokasi.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(CreateParkirAdminActivity.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMahasiswa = mDatabase;

            String id = dbMahasiswa.push().getKey();
            parkirModel.setNama(namaa);
            parkirModel.setLokasi(lokasii);

            //insert data
            dbMahasiswa.child(nama_lokasi_parkir2).setValue(parkirModel);

            finish();

        }
    }
}