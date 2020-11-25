package com.example.donationappv2;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Donations implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name= "paymentMethod")
    int paymentMethod; // 1 for credit and 2 for paypal
    @ColumnInfo(name = "amount")
    double amount;
@Ignore
    int[] sharingApps;

    public  Donations(){};
    public Donations(int id,int paymentMethod, double amount, int[] sharingApps) {
        this.id=id;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.sharingApps = sharingApps;
    }

    protected Donations(Parcel in) {
        id=in.readInt();
        paymentMethod = in.readInt();
        amount = in.readDouble();
        sharingApps = in.createIntArray();
    }

    public static final Creator<Donations> CREATOR = new Creator<Donations>() {
        @Override
        public Donations createFromParcel(Parcel in) {
            return new Donations(in);
        }

        @Override
        public Donations[] newArray(int size) {
            return new Donations[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(paymentMethod);
        dest.writeDouble(amount);
        dest.writeIntArray(sharingApps);
    }
}
