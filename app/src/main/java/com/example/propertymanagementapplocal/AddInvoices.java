package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class AddInvoices extends AppCompatActivity {
   // private static long FlatId;
    private static long refTenantId;
    private static InvoiceCreateListener invoiceCreateListener;

    EditText rent;
    TextView amount, details, title, paymentdue, invoiceIssued, note, addLine;
    Button save, cancel;
    LinearLayout linearLayout;

    String Title, Amount, Details;

    double strAmount;
    double strRent;

    //empty Constructor
    public AddInvoices() {

    }


    public static AddInvoices newInstance(long tenantId, InvoiceCreateListener invoicelistener) {
        //Log.d("RefPropertyId : == >", String.valueOf(refPropertyId));
        //propertyId = refPropertyId;
        refTenantId = tenantId;
        Log.d("ReTenantId : == >", String.valueOf(refTenantId));
        invoiceCreateListener = invoicelistener;
        AddInvoices addInvoices = new AddInvoices();
        return addInvoices;
    }



//notes activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Bundle b = getIntent().getExtras();
                String strnote = data.getStringExtra("note");

                //Log.d("StrNote Result : ", strnote);
                note.setText(strnote);

            }
        }


        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                 Title = data.getStringExtra("title");
                 Details = data.getStringExtra("details");
                 Amount = data.getStringExtra("amount");

                amount.setText(Amount);
                details.setText(Details);
                title.setText(Title);

                if(!Amount.isEmpty() && !Details.isEmpty() && !Title.isEmpty()){
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                }else{
                    linearLayout.setVisibility(linearLayout.GONE);

                }

            }

        }
    }



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoices);

        title = findViewById(R.id.invoicetitleTextView);
        details = findViewById(R.id.invoiceDetailsTextView);
        amount = findViewById(R.id.invoiceAmountTextView);
        rent = findViewById(R.id.invoiceRentEditText);
        invoiceIssued = findViewById(R.id.invoiceIssuedSelectDate);
        paymentdue = findViewById(R.id.invoicePaymentdueSelectDate);
        note = findViewById(R.id.invoiceNoteTextView);
        save = findViewById(R.id.invoiceSaveButton);
        cancel = findViewById(R.id.invoiceCancelButton);
        linearLayout = findViewById(R.id.hidelayout);
        addLine = findViewById(R.id.addlineTextview);


        //navigation part

        //String intentValue = getIntent().getStringExtra("intentPassed");



        linearLayout.setVisibility(linearLayout.GONE);



        //notes
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddInvoices.this, InvoiceNote.class);
                i.putExtra("CurrentNote", note.getText().toString());
                startActivityForResult(i, 1);

            }
        });

        //add new line
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddInvoices.this, InvoiceAddNewLine.class);
                i.putExtra("CurrentTitle", title.getText().toString());
                i.putExtra("CurrentDetails", details.getText().toString());
                i.putExtra("CurrentAmount", amount.getText().toString());
                startActivityForResult(i, 2);

            }
        });

        /*
        //get flatid
        Intent in = getIntent();
        FlatId = in.getLongExtra("flatId", -1);
        Log.d("RefFlatId in AddInvoices : ==>", String.valueOf(FlatId));

         */


        //save data
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //select dates

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        //startDate Picker
        invoiceIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddInvoices.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        invoiceIssued.setText(date);


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //EndDate Picker
        paymentdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddInvoices.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        paymentdue.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


    }

    @SuppressLint("LongLogTag")
    private void getData() {

        String strTitle = title.getText().toString();
        String strDetails = details.getText().toString();
        try {

                strAmount = Integer.parseInt(amount.getText().toString());

        }catch(Exception e){
            e.printStackTrace();
        }
        strRent = Integer.parseInt(rent.getText().toString());
        String strInvoiceIssued = invoiceIssued.getText().toString();
        String strpaymentdue = paymentdue.getText().toString();
        String strNote = note.getText().toString();
        String Amount = amount.getText().toString();
            String totalrent = String.valueOf(strRent - strAmount);
            Log.d("totalrent : ==> ", totalrent);


        InvoiceModelClass invoice = new InvoiceModelClass(-1, strTitle, strDetails, Amount, totalrent, strInvoiceIssued, strpaymentdue, strNote );

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));

        long id = databaseQueryClass.insertInvoice(invoice, refTenantId);
        Log.e("Result Invoice id : ==> ", String.valueOf(id));


        if (id > 0) {
            invoice.setInvoiceId(id);
            invoiceCreateListener.onInvoiceCreated(invoice);
            finish();
        }
    }
}