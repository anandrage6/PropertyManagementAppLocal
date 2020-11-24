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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Balances extends Fragment {
    long refFlatId;
    long refTenantId;

    List<BalancesModel> balancesList;
    BalancesListRecyclerView balancesListRecyclerViewAdapter;
    TextView totalBalance, remainingBalance, balancePaid;


    //invoice part
    RecyclerView balancesRecyclerView;
    DatabaseQueryClass databaseQueryClass;
    List<InvoiceModelClass> invoiceList;
    long tenantId;
    String totalRent;
    TenantModelClass mtenantModelClass;

    //payments part
    List<PaymentsModelClass> paymentList;


    public Balances() {
        // Required empty public constructor
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balances, container, false);
        totalBalance = view.findViewById(R.id.totalBalanceTv);
        remainingBalance = view.findViewById(R.id.remainingBalanceTv);
        balancePaid = view.findViewById(R.id.balancePaidTv);

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
            // totalRent = mtenantModelClass.getRentAmount();
            Log.e("tenantId on resume in invoices ========> ", String.valueOf(tenantId));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Log.d("InvoiceListSize : ==> ", String.valueOf(invoiceList.size()));

        // total balances
        //totalBalance.setText(totalRent);


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

        double totalInvoiceAmount = 0;

        for (InvoiceModelClass invoiceModelClass : invoiceList) {
            invoiceModelClass.setType(0);
            totalInvoiceAmount = totalInvoiceAmount + Double.parseDouble(invoiceModelClass.getRent());


        }

        double totalPaidAmount = 0;
        for (PaymentsModelClass paymentsModelClass : paymentList) {
            paymentsModelClass.setType(1);
            totalPaidAmount = totalPaidAmount + Double.parseDouble(paymentsModelClass.getAmount());

        }

        totalBalance.setText(String.valueOf(totalInvoiceAmount));
        balancePaid.setText(String.valueOf(totalPaidAmount));
        remainingBalance.setText(String.valueOf(totalInvoiceAmount - totalPaidAmount));

        balancesList.addAll(invoiceList);
        balancesList.addAll(paymentList);

        Collections.sort(balancesList, new Comparator<BalancesModel>() {
            @Override
            public int compare(BalancesModel balancesModel, BalancesModel t1) {
                return t1.getEntryDate().compareTo(balancesModel.getEntryDate());

            }

        });


        Set<String> dates = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        for (BalancesModel balancesModel
                : balancesList) {
            dates.add(sdf.format(balancesModel.getEntryDate()));
        }
        Map<String, List<BalancesModel>> balanceListByDate = new HashMap<String, List<BalancesModel>>();
        for (String date : dates) {
            List<BalancesModel> bmList = new ArrayList<>();
            for (BalancesModel balancesModel
                    : balancesList) {
                if (date.equalsIgnoreCase(sdf.format(balancesModel.getEntryDate()))) {
                    bmList.add(balancesModel);
                }
            }
            Collections.sort(bmList, new Comparator<BalancesModel>() {
                @Override
                public int compare(BalancesModel balancesModel, BalancesModel t1) {
                    return t1.getEntryDate().compareTo(balancesModel.getEntryDate());

                }

            });
            balanceListByDate.put(date, bmList);
        }
        balancesList = new ArrayList<>();
        for (Map.Entry<String, List<BalancesModel>> entry : balanceListByDate.entrySet()) {
            BalancesModel bmModel = new BalancesModel();
            bmModel.setType(2);
            bmModel.setTitle(entry.getKey());
            balancesList.add(bmModel);
            double totalInvoiceamt = 0;
            double totalPaidAmt = 0;
            for(BalancesModel mm : entry.getValue()){
                if(mm.getType() == 0){
                    totalInvoiceamt += Double.valueOf(((InvoiceModelClass)mm).getRent());
                }else{
                    totalPaidAmt += Double.valueOf(((PaymentsModelClass)mm).getAmount());
                }
            }
            bmModel.setTitleAmount(String.valueOf(totalInvoiceamt-totalPaidAmt));
            balancesList.addAll(entry.getValue());
        }

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