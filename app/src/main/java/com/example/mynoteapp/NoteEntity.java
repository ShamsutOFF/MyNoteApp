package com.example.mynoteapp;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {
    public String title;
    public String dateOfCreation;
    public String content;

    @Override
    public String toString() {
        return title + " от " + dateOfCreation;
    }

    public String getDate() {
        return dateOfCreation;
    }

    public String getTitle() {
        return title;
    }

    public NoteEntity(String title, String dateOfCreation, String content) {
        this.title = title;
        this.dateOfCreation = dateOfCreation;
        this.content = content;
    }

    protected NoteEntity(Parcel in) {
        title = in.readString();
        dateOfCreation = in.readString();
        content = in.readString();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(dateOfCreation);
        dest.writeString(content);
    }
}
