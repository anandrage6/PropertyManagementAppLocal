package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InvoiceNote extends AppCompatActivity {
    Button saveBtn, cancelBtn;
    EditText note;
    Bundle b;
    String notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_note);


        note = findViewById(R.id.invoiceNoteEditTextView);
        saveBtn = findViewById(R.id.invoiceNoteSaveButton);
        cancelBtn = findViewById(R.id.invoiceNoteCancelButton);

        Bundle extra = getIntent().getExtras();
        notes = extra.getString("CurrentNote");
        note.setText(notes);

        //save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.putExtra("note", note.getText().toString());
                setResult(Activity.RESULT_OK, in);
                finish();
            }
        });

        //cancel Button

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}