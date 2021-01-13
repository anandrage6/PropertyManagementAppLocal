package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PaymentDetails extends AppCompatActivity {
    private Toolbar toolbar;

    TextView amountTv, paidwithTv, datereceivedTv, receivedfromTv, taxstatusTv, notesTv, currencyId;
    String strAmount, strPaidwith, strDatereceived, strReceivedfrom, strTaxstatus, strNotes;
    //ads
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // banner ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Details");

        //id's

        currencyId = findViewById(R.id.currencyId);

        amountTv = findViewById(R.id.paymentDetailsAmount);
        paidwithTv = findViewById(R.id.paymentDetailsPaidwith);
        datereceivedTv = findViewById(R.id.paymentDetailsDatereceived);
        receivedfromTv = findViewById(R.id.paymentDetailsReceivedfrom);
        taxstatusTv = findViewById(R.id.paymentDetailsTaxstatus);
        notesTv = findViewById(R.id.paymentDetailsNotes);

        //getting values through intent

        strAmount = getIntent().getStringExtra(Config.COLUMN_PAYMENT_AMOUNT);
        strPaidwith = getIntent().getStringExtra(Config.COLUMN_PAYMENT_PAIDWITH);
        strDatereceived = getIntent().getStringExtra(Config.COLUMN_PAYMENT_DATERECEIVED);
        strReceivedfrom = getIntent().getStringExtra(Config.COLUMN_PAYMENT_RECEIVEDFROM);
        strTaxstatus = getIntent().getStringExtra(Config.COLUMN_PAYMENT_TAXSTATUS);
        strNotes = getIntent().getStringExtra(Config.COLUMN_PAYMENT_NOTES);

        //setting values

        amountTv.setText(strAmount);
        paidwithTv.setText(strPaidwith);
        datereceivedTv.setText(strDatereceived);
        receivedfromTv.setText(strReceivedfrom);
        taxstatusTv.setText(strTaxstatus);
        notesTv.setText(strNotes);


        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String upiv = pref.getString("ownerUpiId","");

        String rendv = pref.getString("RentalDueday","One Month");
        String curv = pref.getString("currencyType","₹");

        switch (curv) {
            case "ARS-$":
            case "AUD-$":
            case "BSD-$":
            case "BBD-$":
            case "BMD-$":
            case "BND-$":
            case "CAD-$":
            case "KYD-$":
            case "CLP-$":
            case "COP-$":
            case "SVC-$":
            case "FJD-$":
            case "GYD-$":
            case "HKD-$":
            case "LRD-$":
            case "MXN-$":
            case "NAD-$":
            case "NZD-$":
            case "SGD-$":
            case "SBD-$":
            case "SRD-$":
            case "TVD-$":
            case "USD-$":
                currencyId.setText("$");

                break;
            case "FKP-£":
            case "EGP-£":
            case "GIP-£":
            case "GGP-£":
            case "IMP-£":
            case "JEP-£":
            case "LBP-£":
            case "SHP-£":
            case "SYP-£":
            case "GBP-£":
                currencyId.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");



                break;
            case "AZN-₼":
                currencyId.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");


                break;
            case "BOB-$b":
                currencyId.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");


                break;
            case "BWP-P":
                currencyId.setText("P");


                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");


                break;
            case "KHR-៛":
                currencyId.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");


                break;
            case "CRC-₡":
                currencyId.setText("₡");


                break;
            case "HRK-kn":
                currencyId.setText("kn");


                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");


                break;
            case "CZK-Kč":
                currencyId.setText("Kč");


                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");


                break;
            case "GEL-₾":
                currencyId.setText("₾");


                break;
            case "GHC-¢":
                currencyId.setText("¢");


                break;
            case "GTQ-Q":
                currencyId.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");


                break;
            case "INR-₹":
                currencyId.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");


                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");


                break;
            case "JMD-J$":
                currencyId.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");

                break;
            case "LTL-Lt":
                currencyId.setText("Lt");


                break;
            case "MKD-ден":
                currencyId.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");


                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");


                break;
            case "MNT-₮":
                currencyId.setText("₮");


                break;
            case "MZN-MT":
                currencyId.setText("MT");


                break;
            case "NIO-C$":
                currencyId.setText("C$");


                break;
            case "NGN-₦":
                currencyId.setText("₦");

                break;
            case "PAB-B/.":
                currencyId.setText("B/.");


                break;
            case "PYG-Gs":
                currencyId.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");


                break;
            case "CHF-CHF":
                currencyId.setText("CHF");


                break;
            case "TWD-NT$":
                currencyId.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");


                break;
            case "TTD-TT$":
                currencyId.setText("TT$");


                break;
            case "TRL-₺":
                currencyId.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");


                break;
            case "UYU-$U":
                currencyId.setText("$U");


                break;
            case "VEF-Bs":
                currencyId.setText("Bs");



                break;
            case "VND-₫":
                currencyId.setText("₫");


                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");

                break;

        }
    }
}