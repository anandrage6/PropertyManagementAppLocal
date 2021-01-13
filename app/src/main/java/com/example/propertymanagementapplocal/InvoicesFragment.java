package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class InvoicesFragment extends Fragment implements InvoiceCreateListener {
    FloatingActionButton invoicebtnadd;

    private RecyclerView invoiceRecyclerView;
    private InvoiceListAdapter invoiceListAdapter;

    private DatabaseQueryClass databaseQueryClass;

    private List<InvoiceModelClass> invoiceList;
    private long refFlatId;
    private long refTenantId;
    private long tenantId;
    private LinearLayout emptyListLinearLayout;

    private TenantModelClass mtenantModelClass;
    //ads
    private AdView mAdView;


    public InvoicesFragment() {
        //empty Constructor
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_invoices, container, false);

        // banner ads
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        emptyListLinearLayout = v.findViewById(R.id.invoiceListHide);


        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_invoices: ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));

        tenantId = getArguments().getLong("2");
        Log.e("tenantId in Invoices ====> ", String.valueOf(tenantId));


        //getting values by flatid
        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in invoices ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }



        //retrive full details part
        invoiceRecyclerView = v.findViewById(R.id.invoiceRecyclerId);


        invoiceList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(tenantId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        invoiceListAdapter = new InvoiceListAdapter(getContext(), invoiceList, InvoicesFragment.this, tenantId);
        invoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        invoiceRecyclerView.setAdapter(invoiceListAdapter);

        //listEmptyView Visibility
        viewVisibility();


        //floating button
        invoicebtnadd = v.findViewById(R.id.invoiceAddbtn);
        invoicebtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddInvoice();
                //viewVisibility();

            }
        });


        return v;

    }

    private void openActivityAddInvoice() {
        /*
        AddInvoice addTenant = AddInvoice.newInstance(refFlatId, this);
        addTenant.show(getParentFragmentManager(), Config.CREATE_INVOICE);

         */

        /*
        Intent i = new Intent(getActivity(), AddInvoices.class );
        i.putExtra("flatId", refFlatId);
        getContext().startActivity(i);

         */

        AddInvoices addInvoices = AddInvoices.newInstance(tenantId, this);
        Intent intent = new Intent(getActivity(), addInvoices.getClass());
        getContext().startActivity(intent);

    }

    @Override
    public void onInvoiceCreated(InvoiceModelClass invoice) {
        invoiceList.add(invoice);
        invoiceListAdapter.notifyDataSetChanged();
        viewVisibility();

    }

    public void viewVisibility() {
        try {
            if (invoiceList.size() > 0) {
                emptyListLinearLayout.setVisibility(emptyListLinearLayout.GONE);
            } else {
                emptyListLinearLayout.setVisibility(emptyListLinearLayout.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}