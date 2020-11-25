package com.example.donationappv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllDonations extends AppCompatActivity {

    ListView myList;
    List<Donations> donationList=new ArrayList<>();
    DonationAdapter adapter;
    Activity appContext;
    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donations);
        appContext=this;
        myList = (ListView) findViewById(R.id.alldonationlist);
        androidx.appcompat.widget.SearchView searchView=findViewById(R.id.search_view);
        //force  user input number only
       searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
       //use getAll to display all result
         DatabaseClient.databaseWriteExecutor.execute(()->{
                    donationList=DatabaseClient.getDbClient().donationDao().getALl();
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new DonationAdapter(appContext,donationList);
                            myList.setAdapter(adapter);
                        }
                    });
                });


        //after input a number, search result by condition
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //case input from string to double
                final Double searchInput=Double.parseDouble(query);

                DatabaseClient.databaseWriteExecutor.execute(()->{
                    List<Donations> searchAmount=DatabaseClient.getDbClient().donationDao().getAllDonationWithAmountBiggerThan(searchInput);
                    if(searchAmount.size()>0)
                    {
                        donationList=searchAmount;
                    }
                    else
                    { Toast.makeText(AllDonations.this,"NO MATCH RESULT", Toast.LENGTH_LONG).show();};
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new DonationAdapter(appContext,donationList);
                            myList.setAdapter(adapter);
                        }
                    });
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        };


}
