package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PaymentNote extends AppCompatActivity {

    Button saveBtn, cancelBtn;
    EditText note;
    Bundle b;
    String notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_note);

        note = findViewById(R.id.paymentNoteEditTextView);
        saveBtn = findViewById(R.id.paymentNoteSaveButton);
        cancelBtn = findViewById(R.id.paymentNoteCancelButton);

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

        //cancel button

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}