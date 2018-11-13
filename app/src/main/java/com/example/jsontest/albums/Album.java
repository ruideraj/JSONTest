package com.example.jsontest.albums;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private int id;
    private int userId;
    private String title;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.title);
    }

    public Album() {
    }

    protected Album(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
