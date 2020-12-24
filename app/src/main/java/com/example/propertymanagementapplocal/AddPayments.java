package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddPayments extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;

    EditText pAmount, pReceivedfrom;
    Spinner pPaidwith;
    //Switch pTaxstatus;
    TextView pNotes, pDatereceived;
    Button pSave, pCancel;

    String strPaidwith, strTaxStatus;
    String tenantName;

    double dAmount;

    private static long reftenantId;
    private static PaymentsCreateListener paymentsCreateListener;
    private DatabaseQueryClass databaseQueryClass;
    private TenantModelClass mtenantModelClass;
    List<InvoiceModelClass> invoiceList;

    AppCompatRadioButton taxableRadioBtn, nontaxableRadioBtn;

    //empty Constructor
    public AddPayments() {

    }

    //new Instance
    @SuppressLint("LongLogTag")
    public static AddPayments newInstance(long tenantId, PaymentsCreateListener paymentsListener) {
        //Log.d("RefPropertyId : == >", String.valueOf(refPropertyId));
        //propertyId = refPropertyId;
        reftenantId = tenantId;
        Log.d("refTenantId in Payments : == >", String.valueOf(reftenantId));
        paymentsCreateListener = paymentsListener;
        AddPayments addPayments = new AddPayments();
        return addPayments;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payments);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Add Payments");


        pAmount = findViewById(R.id.paymentAmountEdt);
        pReceivedfrom = findViewById(R.id.paymentReceivedFromEdt);
        pDatereceived = findViewById(R.id.paymentDateReceivedTv);
        //pTaxstatus = findViewById(R.id.paymentswitchButton);
        pNotes = findViewById(R.id.paymentNotesTv);

        pSave = findViewById(R.id.paymentSaveButton);
        pCancel = findViewById(R.id.paymentCancelButton);

        nontaxableRadioBtn = findViewById(R.id.nontaxableRadioButton);
        taxableRadioBtn = findViewById(R.id.taxableRadioButton);

        // querying tenantname through tenant id
        databaseQueryClass = new DatabaseQueryClass(AddPayments.this);

        mtenantModelClass = databaseQueryClass.getTenantById(reftenantId);
        tenantName = mtenantModelClass.getTenantName();
        pReceivedfrom.setText(tenantName);

        // invoice part to get total balance
        invoiceList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(reftenantId));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        double totalInvoiceAmount = 0;

        for (InvoiceModelClass invoiceModelClass : invoiceList) {
            invoiceModelClass.setType(0);
            totalInvoiceAmount = totalInvoiceAmount + Double.parseDouble(invoiceModelClass.getRent());

        }

        pAmount.setText(String.valueOf(totalInvoiceAmount));
        //

        //spinner for paid with
        pPaidwith = findViewById(R.id.paymentPaidwithSpin);
        ArrayAdapter<CharSequence> pPaidwithAdapter = ArrayAdapter.createFromResource(this, R.array.Paid_With, android.R.layout.simple_spinner_item);
        pPaidwithAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pPaidwith.setAdapter(pPaidwithAdapter);
        pPaidwith.setOnItemSelectedListener(this);

        //spinner for date received from
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //startDate Picker
        pDatereceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPayments.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        pDatereceived.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        /*
        //switch
        pTaxstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pTaxstatus.isChecked()){
                    strTaxStatus = pTaxstatus.getTextOn().toString();
                    Toast.makeText(AddPayments.this, "Clicked "+strTaxStatus, Toast.LENGTH_SHORT).show();
                }else{
                    strTaxStatus = pTaxstatus.getTextOff().toString();
                    Toast.makeText(AddPayments.this, "Clicked "+strTaxStatus, Toast.LENGTH_SHORT).show();
                }
            }
        });

         */


        /*
        //get flatid
        Intent in = getIntent();
        FlatId = in.getLongExtra("flatId", -1);
        Log.d("RefFlatId in AddT : ==>", String.valueOf(FlatId));

         */

        //save data

        pSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        pCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //notes

        //notes
        pNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddPayments.this, PaymentNote.class);
                i.putExtra("CurrentNote", pNotes.getText().toString());
                startActivityForResult(i, 1);

            }
        });


    }

    //radio button taxable and nontaxable

    @SuppressLint("LongLogTag")
    public void onRadioButtonClicked(View v) {

        boolean isSelected = ((AppCompatRadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.nontaxableRadioButton:
                if (isSelected) {
                    nontaxableRadioBtn.setTextColor(Color.WHITE);
                    taxableRadioBtn.setTextColor(Color.BLACK);
                    strTaxStatus = nontaxableRadioBtn.getText().toString();
                    //Log.e("StrTaxstatus_nontax : ==> ", strTaxStatus);
                    //Toast.makeText(AddPayments.this, "nonTax: ==> "+strTaxStatus, Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.taxableRadioButton:
                if (isSelected) {
                    nontaxableRadioBtn.setTextColor(Color.BLACK);
                    taxableRadioBtn.setTextColor(Color.WHITE);
                    strTaxStatus = taxableRadioBtn.getText().toString();
                    //Log.e("StrTaxstatus tax : ==> ", strTaxStatus);
                    //Toast.makeText(AddPayments.this, "Tax: ==> "+strTaxStatus, Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @SuppressLint("LongLogTag")
    private void getData() {
        dAmount = Integer.parseInt(pAmount.getText().toString());
        String amount = String.valueOf(dAmount);
        String paidwith = strPaidwith;
        String datereceived = pDatereceived.getText().toString();
        String receivedfrom = pReceivedfrom.getText().toString();
        String taxstatus = strTaxStatus;
        String notes = pNotes.getText().toString();

        PaymentsModelClass payment = new PaymentsModelClass(-1, amount, paidwith, datereceived, receivedfrom, taxstatus, notes);

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));


        long id = databaseQueryClass.insertPayments(payment, reftenantId);
        Log.e("Result Payment id : ==> ", String.valueOf(id));


        if (id > 0) {
            payment.setPaymentId(id);
            paymentsCreateListener.onPaymentCreated(payment);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        strPaidwith = "" + adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //note avtivity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Bundle b = getIntent().getExtras();
                String strnote = data.getStringExtra("note");

                //Log.d("StrNote Result : ", strnote);
                pNotes.setText(strnote);

            }
        }
    }
}