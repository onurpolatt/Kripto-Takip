package com.example.onurp.betc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by onurp on 6.03.2018.
 */

public class User implements Parcelable {
    public int def_coin;
    public String username;
    public String email;

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        def_coin = 100;
    }

    public User(Parcel parcel) {
        username = parcel.readString();
        email = parcel.readString();
        def_coin = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeInt(def_coin);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}