package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;

public class AddPayments extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;

    EditText pAmount, pReceivedfrom;
    Spinner pPaidwith;
    Switch pTaxstatus;
    TextView pNotes, pDatereceived;
    Button pSave, pCancel;

    String strPaidwith, strTaxStatus;

    private static long FlatId;

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
        pTaxstatus = findViewById(R.id.paymentswitchButton);
        pNotes = findViewById(R.id.paymentNotesTv);

        pSave = findViewById(R.id.paymentSaveButton);
        pCancel = findViewById(R.id.paymentCancelButton);




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


        //get flatid
        Intent in = getIntent();
        FlatId = in.getLongExtra("flatId", -1);
        Log.d("RefFlatId in AddT : ==>", String.valueOf(FlatId));

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




    }

    @SuppressLint("LongLogTag")
    private void getData() {
        String amount = pAmount.getText().toString();
        String paidwith = strPaidwith;
        String datereceived = pDatereceived.getText().toString();
        String receivedfrom = pReceivedfrom.getText().toString();
        String taxstatus = strTaxStatus;
        String notes = pNotes.getText().toString();

        PaymentsModelClass payment = new PaymentsModelClass(-1, amount, paidwith, datereceived, receivedfrom, taxstatus, notes);

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));


        long id = databaseQueryClass.insertPayments(payment, FlatId);
        Log.e("Result Payment id : ==> ", String.valueOf(id));


        if (id > 0) {
            payment.setPaymentId(id);

        }
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            strPaidwith = ""+adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}