package com.example.administrator.a7stuff.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class Stuff implements Parcelable {
    private int id;
    private String name;
    private int price;
    private String imgPath;

    protected Stuff(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        imgPath = in.readString();
    }

    public static final Creator<Stuff> CREATOR = new Creator<Stuff>() {
        @Override
        public Stuff createFromParcel(Parcel in) {
            return new Stuff(in);
        }

        @Override
        public Stuff[] newArray(int size) {
            return new Stuff[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Stuff(int id, String name, int price, String imgPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgPath = imgPath;
    }

    public Stuff() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(imgPath);
    }
}
