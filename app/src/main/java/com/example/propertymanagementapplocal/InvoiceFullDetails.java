package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class InvoiceFullDetails extends AppCompatActivity {

    TextView title, details, amount, rent, invoiceIssued, paymentdue, note, waterTv, electricityTv, maintananceTv, currencyId, currencyId2, currencyId3, currencyId4;
    String strTitle, strDetails, strAmount, strRent, strInvoiceIssued, strPaymentDue, strNote, strWaterBill, strElectricityBill, strMaintananceCharges;
    private Toolbar toolbar;
    Button smsButton;
    private long invoiceId;
    //private long refTenantId;
    long tenantId;
    String phoneNumber, textMessage;

    private DatabaseQueryClass databaseQueryClass;
    private TenantModelClass mtenantModelClass;
    LinearLayout linearLayout;

    //ads
    private AdView mAdView;



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_full_details);

        // banner ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Invoice Details");

        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        title = findViewById(R.id.titleTv);
        details = findViewById(R.id.detailsTv);
        amount = findViewById(R.id.amountTv);
        rent = findViewById(R.id.rentTv);
        invoiceIssued = findViewById(R.id.invoiceissuedDateTv);
        paymentdue = findViewById(R.id.paymentdueDateTv);
        note = findViewById(R.id.noteTv);
        waterTv = findViewById(R.id.waterTv);
        electricityTv = findViewById(R.id.electricityTv);
        maintananceTv = findViewById(R.id.maintananceTv);
        smsButton = findViewById(R.id.invoiceResendSms);

        currencyId = findViewById(R.id.currencyId);
        currencyId2 = findViewById(R.id.currencyId2);
        currencyId3 = findViewById(R.id.currencyId3);
        currencyId4 = findViewById(R.id.currencyId4);

        linearLayout = findViewById(R.id.invoiceFullDetailsHide);

        invoiceId = getIntent().getLongExtra(Config.COLUMN_INVOICE_ID, -1);
        Log.e("invoicesId  in full details ========> ", String.valueOf(invoiceId));
        strTitle = getIntent().getStringExtra(Config.COLUMN_INVOICE_TITLE);
        strDetails = getIntent().getStringExtra(Config.COLUMN_INVOICE_DETAILS);
        strAmount = getIntent().getStringExtra(Config.COLUMN_INVOICE_AMOUNT);
        strRent = getIntent().getStringExtra(Config.COLUMN_INVOICE_RENT);
        strInvoiceIssued = getIntent().getStringExtra(Config.COLUMN_INVOICE_INVOICE_ISSUED);
        strPaymentDue = getIntent().getStringExtra(Config.COLUMN_INVOICE_PaymentDue);
        strNote = getIntent().getStringExtra(Config.COLUMN_INVOICE_Notes);
        strWaterBill = getIntent().getStringExtra(Config.COLUMN_INVOICE_WATER);
        strElectricityBill = getIntent().getStringExtra(Config.COLUMN_INVOICE_ELECTRICITY);
        strMaintananceCharges = getIntent().getStringExtra(Config.COLUMN_INVOICE_MAINTENANCE_CHARGES);

        // getting tenantId from invoice Adapter
        tenantId = getIntent().getLongExtra("refTenantId", -1);

        Log.e("tenantId on resume in invoices full details ========> ", String.valueOf(tenantId));

       mtenantModelClass = databaseQueryClass.getTenantById(tenantId);
       phoneNumber = mtenantModelClass.getTenantphone();

        title.setText(strTitle);
        details.setText(strDetails);
        amount.setText(strAmount);
        rent.setText(strRent);
        invoiceIssued.setText(strInvoiceIssued);
        paymentdue.setText(strPaymentDue);
        note.setText(strNote);
        waterTv.setText(strWaterBill);
        electricityTv.setText(strElectricityBill);
        maintananceTv.setText(strMaintananceCharges);

        //show and hide layout
        //linearLayout.setVisibility(linearLayout.GONE);

        if (!title.getText().toString().isEmpty() && !details.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
        } else {
            linearLayout.setVisibility(linearLayout.GONE);
        }

        textMessage = "hello resend sms";
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sending
                SmsManager mySmsManager = SmsManager.getDefault();
                mySmsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);
                Toast.makeText(getApplicationContext(), "Sms sent", Toast.LENGTH_LONG).show();
            }
        });

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
                currencyId2.setText("$");
                currencyId3.setText("$");
                currencyId4.setText("$");

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
                currencyId3.setText("£");
                currencyId4.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");
                currencyId2.setText("Lek");
                currencyId3.setText("Lek");
                currencyId4.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");
                currencyId2.setText("؋");
                currencyId3.setText("؋");
                currencyId4.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");
                currencyId2.setText("ƒ");
                currencyId3.setText("ƒ");
                currencyId4.setText("ƒ");


                break;
            case "AZN-₼":
                currencyId.setText("₼");
                currencyId2.setText("₼");
                currencyId3.setText("₼");
                currencyId4.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");
                currencyId2.setText("p.");
                currencyId3.setText("p.");
                currencyId4.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");
                currencyId2.setText("BZ$");
                currencyId3.setText("BZ$");
                currencyId4.setText("BZ$");

                break;
            case "BOB-$b":
                currencyId.setText("$b");
                currencyId2.setText("$b");
                currencyId3.setText("$b");
                currencyId4.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");
                currencyId2.setText("KM");
                currencyId3.setText("KM");
                currencyId4.setText("KM");

                break;
            case "BWP-P":
                currencyId.setText("P");
                currencyId2.setText("P");
                currencyId3.setText("P");
                currencyId4.setText("P");

                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");
                currencyId2.setText("лв");
                currencyId3.setText("лв");
                currencyId4.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");
                currencyId2.setText("R$");
                currencyId3.setText("R$");
                currencyId4.setText("R$");

                break;
            case "KHR-៛":
                currencyId.setText("៛");
                currencyId2.setText("៛");
                currencyId3.setText("៛");
                currencyId4.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");
                currencyId2.setText("¥");
                currencyId3.setText("¥");
                currencyId4.setText("¥");

                break;
            case "CRC-₡":
                currencyId.setText("₡");
                currencyId2.setText("₡");
                currencyId3.setText("₡");
                currencyId4.setText("₡");

                break;
            case "HRK-kn":
                currencyId.setText("kn");
                currencyId2.setText("kn");
                currencyId3.setText("kn");
                currencyId4.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");
                currencyId2.setText("₱");
                currencyId3.setText("₱");
                currencyId4.setText("₱");


                break;
            case "CZK-Kč":
                currencyId.setText("Kč");
                currencyId2.setText("Kč");
                currencyId3.setText("Kč");
                currencyId4.setText("Kč");

                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");
                currencyId2.setText("kr");
                currencyId3.setText("kr");
                currencyId4.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");
                currencyId2.setText("RD$");
                currencyId3.setText("RD$");
                currencyId4.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");
                currencyId2.setText("€");
                currencyId3.setText("€");
                currencyId4.setText("€");

                break;
            case "GEL-₾":
                currencyId.setText("₾");
                currencyId2.setText("₾");
                currencyId3.setText("₾");
                currencyId4.setText("₾");

                break;
            case "GHC-¢":
                currencyId.setText("¢");
                currencyId2.setText("¢");
                currencyId3.setText("¢");
                currencyId4.setText("¢");

                break;
            case "GTQ-Q":
                currencyId.setText("Q");
                currencyId2.setText("Q");
                currencyId3.setText("Q");
                currencyId4.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");
                currencyId2.setText("L");
                currencyId3.setText("L");
                currencyId4.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");
                currencyId2.setText("Ft");
                currencyId3.setText("Ft");
                currencyId4.setText("Ft");

                break;
            case "INR-₹":
                currencyId.setText("₹");
                currencyId2.setText("₹");
                currencyId3.setText("₹");
                currencyId4.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");
                currencyId2.setText("Rp");
                currencyId3.setText("Rp");
                currencyId4.setText("Rp");

                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");
                currencyId2.setText("﷼");
                currencyId3.setText("﷼");
                currencyId4.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");
                currencyId2.setText("₪");
                currencyId3.setText("₪");
                currencyId4.setText("₪");

                break;
            case "JMD-J$":
                currencyId.setText("J$");
                currencyId2.setText("J$");
                currencyId3.setText("J$");
                currencyId4.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");
                currencyId2.setText("₩");
                currencyId3.setText("₩");
                currencyId4.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");
                currencyId2.setText("₭");
                currencyId3.setText("₭");
                currencyId4.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");
                currencyId2.setText("Ls");
                currencyId3.setText("Ls");
                currencyId4.setText("Ls");
                break;
            case "LTL-Lt":
                currencyId.setText("Lt");
                currencyId2.setText("Lt");
                currencyId3.setText("Lt");
                currencyId4.setText("Lt");

                break;
            case "MKD-ден":
                currencyId.setText("ден");
                currencyId2.setText("ден");
                currencyId3.setText("ден");
                currencyId4.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");
                currencyId2.setText("RM");
                currencyId3.setText("RM");
                currencyId4.setText("RM");

                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");
                currencyId2.setText("₨");
                currencyId3.setText("₨");
                currencyId4.setText("₨");

                break;
            case "MNT-₮":
                currencyId.setText("₮");
                currencyId2.setText("₮");
                currencyId3.setText("₮");
                currencyId4.setText("₮");

                break;
            case "MZN-MT":
                currencyId.setText("MT");
                currencyId2.setText("MT");
                currencyId3.setText("MT");
                currencyId4.setText("MT");

                break;
            case "NIO-C$":
                currencyId.setText("C$");
                currencyId2.setText("C$");
                currencyId3.setText("C$");
                currencyId4.setText("C$");

                break;
            case "NGN-₦":
                currencyId.setText("₦");
                currencyId2.setText("₦");
                currencyId3.setText("₦");
                currencyId4.setText("₦");
                break;
            case "PAB-B/.":
                currencyId.setText("B/.");
                currencyId2.setText("B/.");
                currencyId3.setText("B/.");
                currencyId4.setText("B/.");

                break;
            case "PYG-Gs":
                currencyId.setText("Gs");
                currencyId2.setText("Gs");
                currencyId3.setText("Gs");
                currencyId4.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");
                currencyId2.setText("S/.");
                currencyId3.setText("S/.");
                currencyId4.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");
                currencyId2.setText("zł");
                currencyId3.setText("zł");
                currencyId4.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");
                currencyId2.setText("lei");
                currencyId3.setText("lei");
                currencyId4.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");
                currencyId2.setText("₽");
                currencyId3.setText("₽");
                currencyId4.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");
                currencyId2.setText("Дин.");
                currencyId3.setText("Дин.");
                currencyId4.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");
                currencyId2.setText("S");
                currencyId3.setText("S");
                currencyId4.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");
                currencyId2.setText("R");
                currencyId3.setText("R");
                currencyId4.setText("R");

                break;
            case "CHF-CHF":
                currencyId.setText("CHF");
                currencyId2.setText("CHF");
                currencyId3.setText("CHF");
                currencyId4.setText("CHF");

                break;
            case "TWD-NT$":
                currencyId.setText("NT$");
                currencyId2.setText("NT$");
                currencyId3.setText("NT$");
                currencyId4.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");
                currencyId2.setText("฿");
                currencyId3.setText("฿");
                currencyId4.setText("฿");

                break;
            case "TTD-TT$":
                currencyId.setText("TT$");
                currencyId2.setText("TT$");
                currencyId3.setText("TT$");
                currencyId4.setText("TT$");

                break;
            case "TRL-₺":
                currencyId.setText("₺");
                currencyId2.setText("₺");
                currencyId3.setText("₺");
                currencyId4.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");
                currencyId2.setText("₴");
                currencyId3.setText("₴");
                currencyId4.setText("₴");

                break;
            case "UYU-$U":
                currencyId.setText("$U");
                currencyId2.setText("$U");
                currencyId3.setText("$U");
                currencyId4.setText("$U");

                break;
            case "VEF-Bs":
                currencyId.setText("Bs");
                currencyId2.setText("Bs");
                currencyId3.setText("Bs");
                currencyId4.setText("Bs");

                break;
            case "VND-₫":
                currencyId.setText("₫");
                currencyId2.setText("₫");
                currencyId3.setText("₫");
                currencyId4.setText("₫");

                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");
                currencyId2.setText("Z$");
                currencyId3.setText("Z$");
                currencyId4.setText("Z$");

                break;

        }

    }
}