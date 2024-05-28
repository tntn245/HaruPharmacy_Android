package com.example.pharmacyandroidapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemPay implements Parcelable {

    private String id_product,unit,name,img;
    private int quantity,price;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public  ItemPay()
    {

    };

    public ItemPay(String id_product, String unit, String name, int quantity, int price,String img) {
        this.id_product = id_product;
        this.unit = unit;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img =img;
    }

    protected ItemPay(Parcel in) {
        id_product = in.readString();
        unit = in.readString();
        name = in.readString();
        quantity = in.readInt();
        price = in.readInt();
        img = in.readString();
    }

    public static final Creator<ItemPay> CREATOR = new Creator<ItemPay>() {
        @Override
        public ItemPay createFromParcel(Parcel in) {
            return new ItemPay(in);
        }

        @Override
        public ItemPay[] newArray(int size) {
            return new ItemPay[size];
        }
    };

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id_product);
        dest.writeString(unit);
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeInt(price);
        dest.writeString(img);
    }
}
