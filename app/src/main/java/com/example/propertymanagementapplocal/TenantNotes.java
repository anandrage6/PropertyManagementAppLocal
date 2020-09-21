package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class TenantNotes extends AppCompatActivity {
    EditText tenantNotes;
    private Toolbar toolbar;
    ImageButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_notes);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        tenantNotes = findViewById(R.id.editNotes);
        /*
        String note = getIntent().getStringExtra("cNote");
        saveBtn = findViewById(R.id.noteSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = tenantNotes.getText().toString();
                Intent i = new Intent(TenantNotes.this, AddTenants.class);
                i.putExtra("Note", note);
                startActivity(i);
            }
        });

         */
        saveBtn = findViewById(R.id.noteSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = tenantNotes.getText().toString();
                FragmentManager fm = getSupportFragmentManager();
                AddTenant addTenant = new AddTenant();
               // fm.beginTransaction().replace(R.id.fragmentContainer,addTenant).commit();

                Bundle data = new Bundle();//create bundle instance
                data.putString("Note", note);//put string to pass with a key value
                addTenant.setArguments(data);//Set bundle data to fragment
            }
        });

    }
}