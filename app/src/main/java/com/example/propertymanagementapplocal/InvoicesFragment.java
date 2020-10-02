package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class InvoicesFragment extends Fragment implements InvoiceCreateListener {
    FloatingActionButton invoicebtnadd;

    private RecyclerView invoiceRecyclerView;
    private InvoiceListAdapter invoiceListAdapter;

    private DatabaseQueryClass databaseQueryClass ;

    private List<InvoiceModelClass> invoiceList ;
    private long refFlatId;
    private long refTenantId;


    public InvoicesFragment() {
        //empty Constructor
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_invoices, container, false);

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_overView : ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));
        refTenantId = getArguments().getLong("2");
        Log.d("TenantRefId : ==> ", String.valueOf(refTenantId));



        //retrive full details part
        invoiceRecyclerView = v.findViewById(R.id.invoiceRecyclerId);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        invoiceList = new ArrayList<>();
        Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(refFlatId));
        Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

         invoiceListAdapter = new InvoiceListAdapter(getContext(), invoiceList);
        invoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        invoiceRecyclerView.setAdapter(invoiceListAdapter);



        //floating button
        invoicebtnadd = v.findViewById(R.id.invoiceAddbtn);
        invoicebtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddInvoice();
                // viewVisibility();

            }
        });


        return v;

    }

    private void openActivityAddInvoice() {
        AddInvoice addTenant = AddInvoice.newInstance(refFlatId, this);
        addTenant.show(getParentFragmentManager(), Config.CREATE_INVOICE);

    }

    @Override
    public void onInvoiceCreated(InvoiceModelClass invoice) {
        invoiceList.add(invoice);
        invoiceListAdapter.notifyDataSetChanged();
        //viewVisibility();

    }
}