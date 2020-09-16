package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/*
public class AddTenants extends AppCompatActivity {
    private Toolbar toolbar;

    TextView tvNotes;
    String note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenants);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tvNotes = findViewById(R.id.tenantNotesTextView);
        note = getIntent().getStringExtra("Note");
        tvNotes.setText(note);

        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTenants.this, TenantNotes.class);
                i.putExtra("cNote", note);
                startActivity(i);
            }
        });

    }
}

 */