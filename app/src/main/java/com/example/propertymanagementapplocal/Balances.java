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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Balances extends Fragment {
    long refFlatId;
    long refTenantId;

    List<BalancesModel> balancesList;
    BalancesListRecyclerView balancesListRecyclerViewAdapter;


    //invoice part
    RecyclerView balancesRecyclerView;
    DatabaseQueryClass databaseQueryClass;
    List<InvoiceModelClass> invoiceList;
    long tenantId;
    TenantModelClass mtenantModelClass;

    //payments part

    List<PaymentsModelClass> paymentList;


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
        databaseQueryClass = new DatabaseQueryClass(getContext());

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        refTenantId = getArguments().getLong("2");
        Log.e("TenantId in Documents ===> ", String.valueOf(refTenantId));



       RecyclerView balancesRecyclerView = view.findViewById(R.id.recyclerViewBalances);
        //List<BalancesModel> balancesList = new ArrayList<>(Arrays.asList((new BalancesModel(0, invoiceList))));
       List<BalancesModel> balancesList = new ArrayList<>();

        //invoices part



        // to get tenant_Id by flat_id
        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in invoices ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }
       // Log.d("InvoiceListSize : ==> ", String.valueOf(invoiceList.size()));



// invoice part
        invoiceList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(tenantId));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();


        //payments part
        paymentList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        paymentList.addAll(databaseQueryClass.getAllPaymentsbyId(tenantId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();
        //balancesList.add(new BalancesModel(0, invoiceList));
       // balancesList.add(new BalancesModel(1, paymentList));

        for (InvoiceModelClass invoiceModelClass : invoiceList){
            invoiceModelClass.setType(0);
        }

        for (PaymentsModelClass paymentsModelClass : paymentList){
            paymentsModelClass.setType(1);

        }

        balancesList.addAll(invoiceList);
        balancesList.addAll(paymentList);
        Collections.sort(balancesList, new Comparator<BalancesModel>() {
            @Override
            public int compare(BalancesModel balancesModel, BalancesModel t1) {
              return t1.getEntryDate().compareTo(balancesModel.getEntryDate());

            }

        });


        Log.d("InvoiceListSize=== > ", String.valueOf(invoiceList.size()));
        Log.d("PaymentListSize ===> ", String.valueOf(paymentList.size()));

        Log.d("BalanceListSize ===>", String.valueOf(balancesList.size()));
        // balancesList = Arrays.asList(new BalancesModel(0, invoiceList), new BalancesModel(1, paymentList));
       // balancesList = Arrays.asList(new BalancesModel(1, paymentList));

      // balancesRecyclerView.setAdapter(new BalancesListRecyclerView(balancesList));


        //balance part
       // balancesList.add(new List<InvoiceModelClass> invoiceList );
       // balancesList.add(Collections.singleton(paymentList.toString()));


        balancesListRecyclerViewAdapter = new BalancesListRecyclerView(getContext(), balancesList);
        balancesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        balancesRecyclerView.setAdapter(balancesListRecyclerViewAdapter);

        return view;
    }
}