package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddInvoices extends AppCompatActivity {
    // private static long FlatId;
    private static long refTenantId;
    private static InvoiceCreateListener invoiceCreateListener;

    EditText rent, waterBillEdT, electricityBillEdT, maintenanceChargesEdT;
    TextView amount, details, title, paymentdue, invoiceIssued, note, addLine, currencyId, currencyId2, currencyId3, currencyId4;
    Button save, cancel;
    LinearLayout linearLayout;

    String Title, Amount, Details;
    String tenantRent;
    String phoneNumber;
    String tenantName;

    String textMessage;

    double strAmount ;
    double strRent;
    double strwaterBill;
    double strElectricityBill;
    double strMaintenanceCharges;

    private DatabaseQueryClass databaseQueryClass;
    private TenantModelClass mtenantModelClass;



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

                if (!Amount.isEmpty() && !Details.isEmpty() && !Title.isEmpty()) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                } else {
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
        //int rentLength = rent.getText().length();
        //Selection.setSelection(rent.getText(), rent.getText().toString().length());
        //rent.setSelection(0);
        invoiceIssued = findViewById(R.id.invoiceIssuedSelectDate);
        paymentdue = findViewById(R.id.invoicePaymentdueSelectDate);
        note = findViewById(R.id.invoiceNoteTextView);
        save = findViewById(R.id.invoiceSaveButton);
        cancel = findViewById(R.id.invoiceCancelButton);
        linearLayout = findViewById(R.id.hidelayout);
        addLine = findViewById(R.id.addlineTextview);
        currencyId = findViewById(R.id.currencyId);
        currencyId2 = findViewById(R.id.currencyId2);
        currencyId3 = findViewById(R.id.currencyId3);
        currencyId4 = findViewById(R.id.currencyId4);

        waterBillEdT = findViewById(R.id.invoiceWaterBillEditText);
        electricityBillEdT = findViewById(R.id.invoiceElecricityBillEditText);

        maintenanceChargesEdT = findViewById(R.id.invoiceMaintananceChargesEditText);

        // to send sms through permission grant access
        ActivityCompat.requestPermissions(AddInvoices.this, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        //navigation part

        //String intentValue = getIntent().getStringExtra("intentPassed");


        linearLayout.setVisibility(linearLayout.GONE);
        // querying tenantname through tenant id
        databaseQueryClass = new DatabaseQueryClass(AddInvoices.this);


        mtenantModelClass = databaseQueryClass.getTenantById(refTenantId);
        tenantRent = mtenantModelClass.getRentAmount();
        rent.setText(tenantRent);

        tenantName = mtenantModelClass.getTenantName();
        // getting phone number to send sms
        phoneNumber = mtenantModelClass.getTenantphone();


        //notes
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddInvoices.this, InvoiceNote.class);
                i.putExtra("CurrentNote", note.getText().toString());
                startActivityForResult(i, 1);

            }
        });

        //add new line
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddInvoices.this, InvoiceAddNewLine.class);
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




        //InvoiceDate
        //Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        invoiceIssued.setText(currentDate);

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

    // getting phone number from tenant to send sms




    @SuppressLint("LongLogTag")
    private void getData() {

        String strTitle = title.getText().toString();
        String strDetails = details.getText().toString();


        strRent = Double.parseDouble(rent.getText().toString());
        String strInvoiceIssued = invoiceIssued.getText().toString();
        String strpaymentdue = paymentdue.getText().toString();
        String strNote = note.getText().toString();

        try {

            strAmount = Double.parseDouble(amount.getText().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }



       try {
           strwaterBill = Double.parseDouble(waterBillEdT.getText().toString());
       }
       catch (Exception e) {
           e.printStackTrace();
       }

        try {
            strElectricityBill = Double.parseDouble(electricityBillEdT.getText().toString());
        }catch (Exception e) {
            e.printStackTrace();
        }



        try {
            strMaintenanceCharges = Double.parseDouble(maintenanceChargesEdT.getText().toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("waterbill : ==> ", String.valueOf(strwaterBill));
        Log.d("electricity bill : ==> ", String.valueOf(strElectricityBill));
        Log.d("maintanance bill : ==> ", String.valueOf(strMaintenanceCharges));


       // strwaterBill = Double.parseDouble(waterBillEdT.getText().toString());
       // strElectricityBill = Double.parseDouble(electricityBillEdT.getText().toString());
       // strMaintenanceCharges = Double.parseDouble(maintenanceChargesEdT.getText().toString());

        String Amount = String.valueOf(strAmount);
        String waterBill = String.valueOf(strwaterBill);
        String electricityBill = String.valueOf(strElectricityBill);
        String maintenanceCharges = String.valueOf(strMaintenanceCharges);
        String totalrent = String.valueOf(strRent + strAmount + strwaterBill + strElectricityBill + strMaintenanceCharges);
        Log.d("totalrent : ==> ", totalrent);



        InvoiceModelClass invoice = new InvoiceModelClass(-1, strTitle, strDetails, Amount, totalrent, waterBill, electricityBill, maintenanceCharges, strInvoiceIssued, strpaymentdue, strNote);

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));


        long id = databaseQueryClass.insertInvoice(invoice, refTenantId);
        Log.e("Result Invoice id : ==> ", String.valueOf(id));


        if (id > 0) {
            invoice.setInvoiceId(id);
            invoiceCreateListener.onInvoiceCreated(invoice);

            //text message
            textMessage = "Hi,"+ "\n"+
                    tenantName+"\n"+
                    "Your New Invoice Id " + id +"\n"+
                     strTitle+" = "+Amount+"\n"+ strDetails+"\n"+
                    "Water Bill = " +waterBill+"\n"+
                    "Electricity Bill = "+electricityBill+"\n"+
                    "Maintanance Charges = "+maintenanceCharges+"\n"+
                    "Invoice Issued = "+strInvoiceIssued+"\n"+
                    "Payment Due = "+strpaymentdue+"\n"+
                    "Notes = "+strNote;



            // sending
            SmsManager mySmsManager = SmsManager.getDefault();
            ArrayList<String> parts = mySmsManager.divideMessage(textMessage);
            mySmsManager.sendTextMessage(phoneNumber, null, parts.get(1), null, null);
            finish();
        }
    }
}