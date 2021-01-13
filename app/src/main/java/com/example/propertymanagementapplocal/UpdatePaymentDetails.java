package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdatePaymentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static long paymentId;

    private static int paymentItemPosition;

    private static PaymentUpdateListener paymentUpdateListener;

    private PaymentsModelClass mPaymentModelClass;

    private DatabaseQueryClass databaseQueryClass;
    List<InvoiceModelClass> invoiceList;



    private Toolbar toolbar;
    //EditText
    EditText pAmount, pReceivedfrom;
    Spinner pPaidwith;
    TextView pNotes, pDatereceived, currencyId;
    //spin paid with
    ArrayList<String> paidWithAll;
    ArrayAdapter<String> paidWithAdapter;


    AppCompatRadioButton taxableRadioBtn, nontaxableRadioBtn;

    //Buttons
    Button pSave, pCancel;

    //spinner String values
    String strPaidWith;

    // taxable/non-taxable
    String strTaxStatus;

    String strDateReceived;

    String strReceivedFrom;

    String strNotes;

    double Amount;
    String strAmount;
    //default constructor
    public UpdatePaymentDetails() {

    }

    // new Instance

    public static UpdatePaymentDetails newInstance(long id, int position, PaymentUpdateListener listener){
        paymentId = id;
        paymentItemPosition = position;
        paymentUpdateListener = listener;

        UpdatePaymentDetails updatePaymentDetails = new UpdatePaymentDetails();
        return  updatePaymentDetails;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment_details);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Add Payments");

        currencyId = findViewById(R.id.currencyId);

        pAmount = findViewById(R.id.paymentAmountEdtUpdate);
        pReceivedfrom = findViewById(R.id.paymentReceivedFromEdtUpdate);
        pDatereceived = findViewById(R.id.paymentDateReceivedTvUpdate);
        pPaidwith = findViewById(R.id.paymentPaidwithSpinUpdate);
        //pTaxstatus = findViewById(R.id.paymentswitchButton);
        pNotes = findViewById(R.id.paymentNotesTvUpdate);

        pSave = findViewById(R.id.paymentSaveButtonUpdate);
        pCancel = findViewById(R.id.paymentCancelButtonUpdate);

        nontaxableRadioBtn = findViewById(R.id.nontaxableRadioButtonUpdate);
        taxableRadioBtn = findViewById(R.id.taxableRadioButtonUpdate);

        // querying tenantname through tenant id
        databaseQueryClass = new DatabaseQueryClass(UpdatePaymentDetails.this);

        mPaymentModelClass = databaseQueryClass.getPaymentsById(paymentId);


        // getting all values from database to update
        pAmount.setText(mPaymentModelClass.getAmount());

        //spinner value
        String paidWith = mPaymentModelClass.getPaidwith();
        paidWithAll = new ArrayList<>();
        paidWithAll.add(paidWith);
        List<String> allPaidWith = Arrays.asList(getResources().getStringArray(R.array.Paid_With));
        paidWithAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, paidWithAll);
        pPaidwith.setAdapter(paidWithAdapter);
        paidWithAdapter.addAll(allPaidWith);
        pPaidwith.setOnItemSelectedListener(this);

        pDatereceived.setText(mPaymentModelClass.getDatereceived());

        //select dates

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        pDatereceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePaymentDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date2 = day + "/" + month + "/" + year;
                       // pDatereceived.setText(date);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date parsedDate = new Date();
                        try {
                            parsedDate = sdf.parse(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        pDatereceived.setText(sdf.format(parsedDate));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        pReceivedfrom.setText(mPaymentModelClass.getReceivedfrom());

        /*
        if(mPaymentModelClass.getTaxstatus().contains("Taxable")){
            taxableRadioBtn.setText(mPaymentModelClass.getTaxstatus());
        }else if(mPaymentModelClass.getTaxstatus().contains("NonTaxable")){
            nontaxableRadioBtn.setText(mPaymentModelClass.getTaxstatus());
        }

         */

        strTaxStatus = mPaymentModelClass.getTaxstatus();

        pNotes.setText(mPaymentModelClass.getNotes());
        //notes
        pNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdatePaymentDetails.this, PaymentNote.class);
                i.putExtra("CurrentNote", pNotes.getText().toString());
                startActivityForResult(i, 1);

            }
        });

        pSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        pCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdatePaymentDetails.this);
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
                        Toast.makeText(UpdatePaymentDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

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

    private void getData() {

        Amount = Double.parseDouble(pAmount.getText().toString());
        strAmount = String.valueOf(Amount);
        strDateReceived = pDatereceived.getText().toString();
        strReceivedFrom = pReceivedfrom.getText().toString();
        strNotes = pNotes.getText().toString();
        mPaymentModelClass.setAmount(strAmount);
        mPaymentModelClass.setPaidwith(strPaidWith);
        mPaymentModelClass.setDatereceived(strDateReceived);
        mPaymentModelClass.setReceivedfrom(strReceivedFrom);
        mPaymentModelClass.setTaxstatus(strTaxStatus);
        mPaymentModelClass.setNotes(strNotes);

        long id = databaseQueryClass.updatePaymentInfo(mPaymentModelClass);
        if (id > 0) {
            paymentUpdateListener.onPaymentInfoUpdated(mPaymentModelClass, paymentItemPosition);
            //getDialog().dismiss();
            //Intent i = new Intent(UpdateFlat.this, PropertyDetails.class);
            //startActivity(i);
            finish();
        }

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

    // radio buttons taxable and non-taxable
    @SuppressLint("LongLogTag")
    public void onRadioButtonClicked(View v) {

        boolean isSelected = ((AppCompatRadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.nontaxableRadioButton:
                if (isSelected) {
                    nontaxableRadioBtn.setTextColor(Color.WHITE);
                    taxableRadioBtn.setTextColor(Color.BLACK);
                    strTaxStatus = nontaxableRadioBtn.getText().toString();
                    Log.e("StrTaxstatus_nontax : ==> ", strTaxStatus);
                    //Toast.makeText(AddPayments.this, "nonTax: ==> "+strTaxStatus, Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.taxableRadioButton:
                if (isSelected) {
                    nontaxableRadioBtn.setTextColor(Color.BLACK);
                    taxableRadioBtn.setTextColor(Color.WHITE);
                    strTaxStatus = taxableRadioBtn.getText().toString();
                    Log.e("StrTaxstatus tax : ==> ", strTaxStatus);
                    //Toast.makeText(AddPayments.this, "Tax: ==> "+strTaxStatus, Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        strPaidWith = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}