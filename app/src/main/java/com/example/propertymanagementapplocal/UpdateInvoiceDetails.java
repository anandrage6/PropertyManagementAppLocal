package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateInvoiceDetails extends AppCompatActivity {

    private static long invoiceId;

    private static int invoiceItemPosition;

    private static InvoiceUpdateListener invoiceUpdateListener;

    private InvoiceModelClass mInvoiceModelClass;

    private DatabaseQueryClass databaseQueryClass;


    EditText rent, waterBill, electricityBill, maintananceBill;
    TextView amount, details, title, paymentdue, invoiceIssued, note, addLine;
    Button save, cancel;
    LinearLayout linearLayout;

    String strtitle, strDetails, strInvoiceIssued, amountStr, strPaymentdue, strNotes;

    String Title, Amount, Details;

    double strAmount;
    double strRent, strWaterBill, strElectricityBill, strMaintananceBill;

    //constructor
    public UpdateInvoiceDetails() {

    }

    //new Instance

    public static UpdateInvoiceDetails newInstance(long id, int position, InvoiceUpdateListener listener) {

        invoiceId = id;
        invoiceItemPosition = position;
        invoiceUpdateListener = listener;

        UpdateInvoiceDetails updateInvoice = new UpdateInvoiceDetails();
        return updateInvoice;

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

                if (!Amount.isEmpty() && !Details.isEmpty() && !Title.isEmpty()) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                } else {
                    linearLayout.setVisibility(linearLayout.GONE);

                }

            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_invoice_details);

        title = findViewById(R.id.invoicetitleTextViewUpdate);
        details = findViewById(R.id.invoiceDetailsTextViewUpdate);
        amount = findViewById(R.id.invoiceAmountTextViewUpdate);
        rent = findViewById(R.id.invoiceRentEditTextUpdate);
        invoiceIssued = findViewById(R.id.invoiceIssuedSelectDateUpdate);
        paymentdue = findViewById(R.id.invoicePaymentdueSelectDateUpdate);
        note = findViewById(R.id.invoiceNoteTextViewUpdate);
        save = findViewById(R.id.invoiceSaveButtonUpdate);
        cancel = findViewById(R.id.invoiceCancelButtonUpdate);
        linearLayout = findViewById(R.id.hidelayout);
        addLine = findViewById(R.id.addlineTextviewUpdate);
        waterBill = findViewById(R.id.invoiceWaterEditTextUpdate);
        electricityBill = findViewById(R.id.invoiceElectricityEditTextUpdate);
        maintananceBill = findViewById(R.id.invoiceMaintananceEditTextUpdate);


        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        mInvoiceModelClass = databaseQueryClass.getInvoiceById(invoiceId);

        title.setText(mInvoiceModelClass.getTitle());
        details.setText(mInvoiceModelClass.getDetails());
        amount.setText(mInvoiceModelClass.getAmount());
        rent.setText(mInvoiceModelClass.getRent());
        invoiceIssued.setText(mInvoiceModelClass.getInvoiceIssued());
        paymentdue.setText(mInvoiceModelClass.getPaymentDue());
        note.setText(mInvoiceModelClass.getNotes());
        waterBill.setText(mInvoiceModelClass.getWaterBill());
        electricityBill.setText(mInvoiceModelClass.getElectricityBill());
        maintananceBill.setText(mInvoiceModelClass.getMaintenanceCharges());


        //notes
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateInvoiceDetails.this, InvoiceNote.class);
                i.putExtra("CurrentNote", note.getText().toString());
                startActivityForResult(i, 1);

            }
        });

        //add new line
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateInvoiceDetails.this, InvoiceAddNewLine.class);
                i.putExtra("CurrentTitle", title.getText().toString());
                i.putExtra("CurrentDetails", details.getText().toString());
                i.putExtra("CurrentAmount", amount.getText().toString());
                startActivityForResult(i, 2);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInvoiceDetails.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInvoiceDetails.this, new DatePickerDialog.OnDateSetListener() {
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

        //hidelayout addnewine
        if (!title.getText().toString().isEmpty() && !details.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
        } else {
            linearLayout.setVisibility(linearLayout.GONE);
        }

        //update invoice

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strtitle = title.getText().toString();
                strDetails = details.getText().toString();

                try {
                    strAmount = Double.parseDouble(amount.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                amountStr = amount.getText().toString();
                strRent = Double.parseDouble(rent.getText().toString());
                strInvoiceIssued = invoiceIssued.getText().toString();
                strPaymentdue = paymentdue.getText().toString();
                strNotes = note.getText().toString();
                try {
                    strWaterBill = Double.parseDouble(waterBill.getText().toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    strElectricityBill = Double.parseDouble(electricityBill.getText().toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    strMaintananceBill = Double.parseDouble(maintananceBill.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                mInvoiceModelClass.setTitle(strtitle);
                mInvoiceModelClass.setDetails(strDetails);
                mInvoiceModelClass.setAmount(amountStr);
                mInvoiceModelClass.setRent(String.valueOf(strRent - strAmount + strWaterBill + strElectricityBill + strMaintananceBill));
                mInvoiceModelClass.setInvoiceIssued(strInvoiceIssued);
                mInvoiceModelClass.setPaymentDue(strPaymentdue);
                mInvoiceModelClass.setNotes(strNotes);
                mInvoiceModelClass.setWaterBill(String.valueOf(strWaterBill));
                mInvoiceModelClass.setElectricityBill(String.valueOf(strElectricityBill));
                mInvoiceModelClass.setMaintenanceCharges(String.valueOf(strMaintananceBill));

                long id = databaseQueryClass.updateInvoiceInfo(mInvoiceModelClass);
                if (id > 0) {
                    invoiceUpdateListener.onInvoiceInfoUpdated(mInvoiceModelClass, invoiceItemPosition);
                    //getDialog().dismiss();
                    //Intent i = new Intent(UpdateFlat.this, PropertyDetails.class);
                    //startActivity(i);
                    finish();
                }
            }
        });


        //cancel button

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdateInvoiceDetails.this);
                alertDialogBuilder.setTitle("Confirm Exit...!!");
                alertDialogBuilder.setMessage("Are you sure you want to exit");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UpdateInvoiceDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    public void onBackPressed() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Exit...!!");
        alertDialogBuilder.setMessage("Are you sure you want to exit");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateInvoiceDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}