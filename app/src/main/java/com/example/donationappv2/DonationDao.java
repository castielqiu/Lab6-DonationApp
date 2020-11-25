package com.example.donationappv2;

import android.widget.ListView;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DonationDao {

    @Query("SELECT * FROM  DONATIONS")
    List<Donations> getALl();

    @Insert
    void insert(Donations donations);

    @Query("SELECT * FROM Donations WHERE amount > :amount_")
    List<Donations> getAllDonationWithAmountBiggerThan(double amount_);

    @Query("DELETE FROM Donations")
    void reset();
}