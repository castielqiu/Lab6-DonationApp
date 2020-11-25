package com.example.donationappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DonationReport extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_report_layout);
        Donations obj = getIntent().getParcelableExtra("currentDonation");
        TextView reportText = (TextView) findViewById(R.id.report_id);
        reportText.setText(buildStringReport(obj));

    }


    public String buildStringReport (Donations obj){
        String sharingArray = " and sharing the campaign via ";
        String payment = "";
        boolean isShard = false;
       //what'sup,text,massenger
        if (obj.sharingApps[0] == 1){
                sharingArray += " What's up";
                isShard = true;
        }
        if (obj.sharingApps[1] == 1){
            sharingArray += " text masseges";
            isShard = true;

        }
        if (obj.sharingApps[2] == 1){
            sharingArray += " massenger";
            isShard = true;

        }


        if (obj.paymentMethod == 1)//1 for credit 2 for Paypal
            payment = "Credit Card";
        else
            payment = "PayPal";

        if (isShard)
            return "Thanks for your " + payment + " payment with amount " + obj.amount +sharingArray;
        else
            return  "Thanks for your " + payment + " payment with amount " + obj.amount ;

    }

}
