package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class TenantFullDetails extends AppCompatActivity {

    TextView name, email, phone, leaseStart, leaseEnd, rentIsPaid, totalOccupants, notes, rentAmount, securityDeposit, currencyId, currencyId2;
    String strName, strPhone, strEmail, strleaseStart, strLeaseEnd, strRentIsPaid, strTotalOccupants, strNotes, strRentAmount, strSecurityDeposit;

    private Toolbar toolbar;

    //ads
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_full_details);



        // banner ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tenant Details");


        //id's
        name = findViewById(R.id.tenantNameTV);
        email = findViewById(R.id.tenantEmailTV);
        phone = findViewById(R.id.tenantPhoneTV);
        leaseStart = findViewById(R.id.leaseStartTextView);
        leaseEnd = findViewById(R.id.leaseEndTextView);
        rentIsPaid = findViewById(R.id.rentIsPaidTextView);
        totalOccupants = findViewById(R.id.totaloccupantsTextView);
        notes = findViewById(R.id.notesTextView);
        rentAmount = findViewById(R.id.rentAmountTextView);
        securityDeposit = findViewById(R.id.depositTextView);
        currencyId = findViewById(R.id.currencyId);
        currencyId2 = findViewById(R.id.currencyId2);

        //getting values
        strName = getIntent().getStringExtra(Config.COLUMN_TENANTS_NAME);
        strPhone = getIntent().getStringExtra(Config.COLUMN_TENANTS_PHONE);
        strEmail = getIntent().getStringExtra(Config.COLUMN_TENANTS_EMAIL);
        strleaseStart = getIntent().getStringExtra(Config.COLUMN_TENANTS_LEASESTART);
        strLeaseEnd = getIntent().getStringExtra(Config.COLUMN_TENANTS_LEASEEND);
        strRentIsPaid = getIntent().getStringExtra(Config.COLUMN_TENANTS_RENTISPAID);
        strTotalOccupants = getIntent().getStringExtra(Config.COLUMN_TENANTS_TOTALOCCUPANTS);
        strNotes = getIntent().getStringExtra(Config.COLUMN_TENANTS_NOTES);
        strRentAmount = getIntent().getStringExtra(Config.COLUMN_TENANTS_RENT);
        strSecurityDeposit = getIntent().getStringExtra(Config.COLUMN_TENANTS_SECURITYDEPOSIT);

        name.setText(strName);
        phone.setText(strPhone);
        email.setText(strEmail);
        leaseStart.setText(strleaseStart);
        leaseEnd.setText(strLeaseEnd);
        rentIsPaid.setText(strRentIsPaid);
        totalOccupants.setText(strTotalOccupants);
        notes.setText(strNotes);
        rentAmount.setText(strRentAmount);
        securityDeposit.setText(strSecurityDeposit);

        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");

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
                Log.e("dollar value ==> ", "$");

                currencyId2.setText("$");

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
                currencyId2.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");
                currencyId2.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");
                currencyId2.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");
                currencyId2.setText("ƒ");


                break;
            case "AZN-₼":
                currencyId.setText("₼");
                currencyId2.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");
                currencyId2.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");
                currencyId2.setText("BZ$");

                break;
            case "BOB-$b":
                currencyId.setText("$b");
                currencyId2.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");
                currencyId2.setText("KM");

                break;
            case "BWP-P":
                currencyId.setText("P");
                currencyId2.setText("P");

                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");
                currencyId2.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");
                currencyId2.setText("R$");

                break;
            case "KHR-៛":
                currencyId.setText("៛");
                currencyId2.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");
                currencyId2.setText("¥");

                break;
            case "CRC-₡":
                currencyId.setText("₡");
                currencyId2.setText("₡");

                break;
            case "HRK-kn":
                currencyId.setText("kn");
                currencyId2.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");
                currencyId2.setText("₱");

                break;
            case "CZK-Kč":
                currencyId.setText("Kč");
                currencyId2.setText("Kč");

                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");
                currencyId2.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");
                currencyId2.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");
                currencyId2.setText("€");

                break;
            case "GEL-₾":
                currencyId.setText("₾");
                currencyId2.setText("₾");

                break;
            case "GHC-¢":
                currencyId.setText("¢");
                currencyId2.setText("¢");

                break;
            case "GTQ-Q":
                currencyId.setText("Q");
                currencyId2.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");
                currencyId2.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");
                currencyId2.setText("Ft");

                break;
            case "INR-₹":
                currencyId.setText("₹");
                currencyId2.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");
                currencyId2.setText("Rp");

                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");
                currencyId2.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");
                currencyId2.setText("₪");

                break;
            case "JMD-J$":
                currencyId.setText("J$");
                currencyId2.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");
                currencyId2.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");
                currencyId2.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");
                currencyId2.setText("Ls");

                break;
            case "LTL-Lt":
                currencyId.setText("Lt");
                currencyId2.setText("Lt");

                break;
            case "MKD-ден":
                currencyId.setText("ден");
                currencyId2.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");
                currencyId2.setText("RM");

                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");
                currencyId2.setText("₨");

                break;
            case "MNT-₮":
                currencyId.setText("₮");
                currencyId2.setText("₮");

                break;
            case "MZN-MT":
                currencyId.setText("MT");
                currencyId2.setText("MT");

                break;
            case "NIO-C$":
                currencyId.setText("C$");
                currencyId2.setText("C$");

                break;
            case "NGN-₦":
                currencyId.setText("₦");
                currencyId2.setText("₦");

                break;
            case "PAB-B/.":
                currencyId.setText("B/.");
                currencyId2.setText("B/.");

                break;
            case "PYG-Gs":
                currencyId.setText("Gs");
                currencyId2.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");
                currencyId2.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");
                currencyId2.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");
                currencyId2.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");
                currencyId2.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");
                currencyId2.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");
                currencyId2.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");
                currencyId2.setText("R");

                break;
            case "CHF-CHF":
                currencyId.setText("CHF");
                currencyId2.setText("CHF");

                break;
            case "TWD-NT$":
                currencyId.setText("NT$");
                currencyId2.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");
                currencyId2.setText("฿");

                break;
            case "TTD-TT$":
                currencyId.setText("TT$");
                currencyId2.setText("TT$");

                break;
            case "TRL-₺":
                currencyId.setText("₺");
                currencyId2.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");
                currencyId2.setText("₴");

                break;
            case "UYU-$U":
                currencyId.setText("$U");
                currencyId2.setText("$U");

                break;
            case "VEF-Bs":
                currencyId.setText("Bs");
                currencyId2.setText("Bs");

                break;
            case "VND-₫":
                currencyId.setText("₫");
                currencyId2.setText("₫");

                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");
                currencyId2.setText("Z$");

                break;
            default:
                currencyId.setText(curv);
                currencyId2.setText(curv);

                break;
        }



    }
}