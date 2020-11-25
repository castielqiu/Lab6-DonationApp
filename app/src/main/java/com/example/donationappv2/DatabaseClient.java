package com.example.donationappv2;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseClient {

    private static AppDatabase dbClient;

    public static AppDatabase buildDbClient(Context context)
    {
        if(dbClient==null)
        {
            dbClient= Room.databaseBuilder(context,AppDatabase.class,"Donations_db").build();
        }
        return  dbClient;
    }

    public static AppDatabase getDbClient(){
        return dbClient;
    }
    //set threads maximum number to 4
    private static final int NUMBER_OF_THREADS=4;

    public static  final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static void  insert (Donations donations)
    {
        databaseWriteExecutor.execute(()->{getDbClient().donationDao().insert(donations);});
    }

    public static void getAll(){
        databaseWriteExecutor.execute(()->{getDbClient().donationDao().getALl();});
    }
    public static void getAllDonationWithAmountBiggerThan(int amount_){
        databaseWriteExecutor.execute(()->{getDbClient().donationDao().getAllDonationWithAmountBiggerThan(amount_);
        });
    }
    public static void  reset()
    {
        databaseWriteExecutor.execute(()->{getDbClient().donationDao().reset();});
    }

}
