package com.example.donationappv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Button donate;
    RadioButton paypal_payment;
    RadioButton credit_payment;
    CheckBox whatsup_check;
    CheckBox messenger_check;
    CheckBox messages_check;
    EditText amount;
    Donations currentDonation;
    AlertDialog.Builder builder;
    int id=0;
    int pType = 0;
    int[] sharingArray = {0,0,0};//what'sup,massenger,text
    int sharingIndex = 0;
    ArrayList<Donations> donationsArrayList = new ArrayList<>(0);

    AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        donate = (Button) findViewById(R.id.donate_id);
        paypal_payment = (RadioButton)findViewById(R.id.paypal_btn);
        credit_payment = (RadioButton)findViewById(R.id.credit_btn);
        whatsup_check = (CheckBox)findViewById(R.id.wup_check);
        messenger_check = (CheckBox)findViewById(R.id.messenger_check);
        messages_check = (CheckBox)findViewById(R.id.messages_check);
        amount = (EditText) findViewById(R.id.amount);

        DatabaseClient.buildDbClient(this);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.messege,Toast.LENGTH_SHORT).show();
                String payment, sharing=" and sharing the link via ";
                if (paypal_payment.isChecked()) { //1 for credit 2 for Paypal
                    payment = "PayPal";
                    pType = 2;
                }
                else{
                    payment = "Credit card";
                    pType = 1;
                }
                sharingArray = new int[]{0,0,0};
                Boolean shared = false;//what'sup,text,massenger
                if (whatsup_check.isChecked()) {
                    sharing += "What's up";
                    sharingArray[0] = 1;
                    shared = true;
                }
                if (messenger_check.isChecked()) {
                    sharing += " massenger ";
                    sharingArray[2] = 1;
                    shared = true;
                }
                if (messages_check.isChecked()) {
                    sharing += " messages";
                    sharingArray[1] = 1;
                    shared = true;
                }
                if (!shared)
                    sharing="";
                int a = 0;
                a=Integer.parseInt(amount.getText().toString());
                currentDonation = new Donations(id,pType,a,sharingArray);
               // donationsArrayList.add(currentDonation);
                //database
                DatabaseClient.insert(currentDonation);
                Log.d("insert", String.valueOf(currentDonation.amount));
                builder.setMessage("Thanks for your " +payment + " payment with amount " + a +sharing )
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    setUI();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.donationapp_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.reportMenuItem: {
                Intent myIntent = new Intent(this, DonationReport.class);
                myIntent.putExtra("currentDonation", currentDonation);
                startActivity(myIntent);
                setUI();
                break;
            }
            case R.id.resetUI:{
               setUI();
                DatabaseClient.reset();
                break;
            }
            case R.id.donations_list_id:{
                Intent allDonations =new  Intent(this, AllDonations.class);
               // allDonations.putParcelableArrayListExtra("DonationsExtra",donationsArrayList);
                startActivity(allDonations);
                break;
            /*    Intent allDonationsIntent = new Intent(this,AllDonations.class);
                allDonationsIntent.putParcelableArrayListExtra("donationList",donationsArrayList);
                startActivity(allDonationsIntent);*/

            }
        }

/*DatabaseClient.databaseWriteExecutor.execute(()->{
                DatabaseClient.getDbClient().donationDao().reset();
               });*/
         return true;

    }

    public void setUI(){
        paypal_payment.setChecked(false);
        credit_payment.setChecked(false);
        whatsup_check.setChecked(false);
        messages_check.setChecked(false);
        messenger_check.setChecked(false);
        amount.setText("");
    }

}
