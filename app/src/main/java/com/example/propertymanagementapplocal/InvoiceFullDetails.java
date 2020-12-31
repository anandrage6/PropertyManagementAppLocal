package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InvoiceFullDetails extends AppCompatActivity {

    TextView title, details, amount, rent, invoiceIssued, paymentdue, note, waterTv, electricityTv, maintananceTv ;
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

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_full_details);

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
            }
        });


    }
}