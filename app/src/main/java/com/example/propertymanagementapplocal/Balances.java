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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Balances extends Fragment {
    private long refFlatId;
    private long refTenantId;

    private List<String> balancesList;
    private BalancesListRecyclerView balancesListRecyclerView;


    //invoice part
    private RecyclerView balancesRecyclerView;
    private DatabaseQueryClass databaseQueryClass;
    private List<InvoiceModelClass> invoiceList;
    private long tenantId;
    private TenantModelClass mtenantModelClass;

    //payments part

    private List<PaymentsModelClass> paymentList;


    // payments part

    public Balances() {
        // Required empty public constructor
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balances, container, false);

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        refTenantId = getArguments().getLong("2");
        Log.e("TenantId in Documents ===> ", String.valueOf(refTenantId));

        // to get tenant_Id by flat_id
        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in invoices ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        balancesRecyclerView = view.findViewById(R.id.recyclerViewBalances);
        balancesList = new ArrayList<String>();

        //invoices part
        databaseQueryClass = new DatabaseQueryClass(getContext());
        invoiceList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(tenantId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();


        //payments part
        databaseQueryClass = new DatabaseQueryClass(getContext());
        paymentList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        paymentList.addAll(databaseQueryClass.getAllPaymentsbyId(tenantId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();


        //balance part
       // balancesList.add(new List<InvoiceModelClass> invoiceList );
       // balancesList.add(Collections.singleton(paymentList.toString()));

        balancesListRecyclerView = new BalancesListRecyclerView(getContext(), paymentList, invoiceList);
        balancesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        balancesRecyclerView.setAdapter(balancesListRecyclerView);

        return view;
    }
}