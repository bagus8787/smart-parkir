package com.example.loginfirebase.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkirModel implements Parcelable {

    public String nama, lokasi, status, photo, photo_url, id, nama_lokasi;

    public ParkirModel() {
    }

    public ParkirModel(String nama, String lokasi, String status, String photo, String photo_url, String id, String nama_lokasi) {
        this.nama = nama;
        this.nama_lokasi = nama_lokasi;
        this.lokasi = lokasi;
        this.status = status;
        this.photo = photo;
        this.photo_url = photo_url;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public void setNama_lokasi(String nama_lokasi) {
        this.nama_lokasi = nama_lokasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Creator<ParkirModel> getCREATOR() {
        return CREATOR;
    }

    protected ParkirModel(Parcel in) {
        nama = in.readString();
        nama_lokasi = in.readString();
        lokasi = in.readString();

        status = in.readString();
        photo = in.readString();
        photo_url = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(nama_lokasi);
        dest.writeString(lokasi);
        dest.writeString(status);
        dest.writeString(photo);
        dest.writeString(photo_url);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParkirModel> CREATOR = new Creator<ParkirModel>() {
        @Override
        public ParkirModel createFromParcel(Parcel in) {
            return new ParkirModel(in);
        }

        @Override
        public ParkirModel[] newArray(int size) {
            return new ParkirModel[size];
        }
    };
}
