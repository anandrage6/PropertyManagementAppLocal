package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AddInvoice extends DialogFragment {
    private static long FlatId;
    private static InvoiceCreateListener invoiceCreateListener;

    EditText rent, note;
    TextView amount, details, title, paymentdue, invoiceIssued;
    Button save, cancel;

    //empty Constructor
    public AddInvoice() {

    }


    public static AddInvoice newInstance(long refFlatId, InvoiceCreateListener invoicelistener) {
        //Log.d("RefPropertyId : == >", String.valueOf(refPropertyId));
        //propertyId = refPropertyId;
        FlatId = refFlatId;
        invoiceCreateListener = invoicelistener;
        AddInvoice addInvoice = new AddInvoice();
        addInvoice.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);
        return addInvoice;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_invoice, container, false);
        title = view.findViewById(R.id.invoicetitleTextView);
        details = view.findViewById(R.id.invoiceDetailsTextView);
        amount = view.findViewById(R.id.invoiceAmountTextView);
        rent = view.findViewById(R.id.invoiceRentEditText);
        invoiceIssued = view.findViewById(R.id.invoiceIssuedSelectDate);
        paymentdue = view.findViewById(R.id.invoicePaymentdueSelectDate);
        note = view.findViewById(R.id.invoiceNoteEditText);
        save = view.findViewById(R.id.invoiceSaveButton);
        cancel = view.findViewById(R.id.invoiceCancelButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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




        return view;
    }

    @SuppressLint("LongLogTag")
    private void getData() {

        String strTitle = title.getText().toString();
        String strDetails = details.getText().toString();
        String strAmount = amount.getText().toString();
        String strRent = rent.getText().toString();
        String strInvoiceIssued = invoiceIssued.getText().toString();
        String strpaymentdue = paymentdue.getText().toString();
        String strNote = note.getText().toString();

        InvoiceModelClass invoice = new InvoiceModelClass(-1, strTitle, strDetails, strAmount, strRent, strInvoiceIssued, strpaymentdue, strNote );

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));

        long id = databaseQueryClass.insertInvoice(invoice, FlatId);
        Log.e("Result Invoice id : ==> ", String.valueOf(id));


        if (id > 0) {
            invoice.setInvoiceId(id);
            invoiceCreateListener.onInvoiceCreated(invoice);
            getDialog().dismiss();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}
