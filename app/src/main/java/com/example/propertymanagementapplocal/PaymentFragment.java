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


public class PaymentFragment extends Fragment implements PaymentsCreateListener {
    FloatingActionButton btnadd;
    private long refFlatId;
    private long refTenantId;

    private RecyclerView paymentRecyclerView;
    private PaymentListRecyclerView paymentListRecyclerViewAdapter;

    private DatabaseQueryClass databaseQueryClass;

    private List<PaymentsModelClass> paymentList;
    private LinearLayout paymentEmptyListLinearLayout;

    private TenantModelClass mtenantModelClass;
    private long tenantId;
    //ads
    private AdView mAdView;

    public PaymentFragment() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        // banner ads
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_payments : ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));

        tenantId = getArguments().getLong("2");
        Log.e("tenantId in Payments ===== > ", String.valueOf(tenantId));


        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in Payments ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //emptyListvisibility
        paymentEmptyListLinearLayout = v.findViewById(R.id.paymentEmptyList);


        //floating button
        btnadd = v.findViewById(R.id.paymentaddbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();


            }
        });

        //


        //retrive full details part
        paymentRecyclerView = v.findViewById(R.id.paymentRecyclerId);


        paymentList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        paymentList.addAll(databaseQueryClass.getAllPaymentsbyId(tenantId));
        Log.e("PaymentList : ==> ", String.valueOf(paymentList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        paymentListRecyclerViewAdapter = new PaymentListRecyclerView(getContext(), paymentList, PaymentFragment.this);
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        paymentRecyclerView.setAdapter(paymentListRecyclerViewAdapter);

        //emptylistview
        listvisibility();


        return v;
    }

    private void openActivityAddTenant() {
        /*
        Intent i = new Intent(getActivity(), AddPayments.class );
        i.putExtra("flatId", refFlatId);
        getContext().startActivity(i);

         */

        AddPayments addPayments = AddPayments.newInstance(tenantId, this);
        Intent intent = new Intent(getActivity(), addPayments.getClass());
        getContext().startActivity(intent);

    }

    @Override
    public void onPaymentCreated(PaymentsModelClass payments) {
        paymentList.add(payments);
        paymentListRecyclerViewAdapter.notifyDataSetChanged();

        //visibility emptylist
        listvisibility();
    }

    //visibility emptylist
    public void listvisibility() {
        try {
            if (paymentList.size() > 0) {
                paymentEmptyListLinearLayout.setVisibility(paymentEmptyListLinearLayout.GONE);
            } else {
                paymentEmptyListLinearLayout.setVisibility(paymentEmptyListLinearLayout.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

