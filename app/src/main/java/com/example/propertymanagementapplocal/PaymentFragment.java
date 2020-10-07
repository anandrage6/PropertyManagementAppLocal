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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class PaymentFragment extends Fragment implements PaymentsCreateListener {
    FloatingActionButton btnadd;
    private long refFlatId;
    private long refTenantId;

    private RecyclerView paymentRecyclerView;
    private PaymentListRecyclerView paymentListRecyclerViewAdapter;

    private DatabaseQueryClass databaseQueryClass ;

    private List<PaymentsModelClass> paymentList ;

    public PaymentFragment() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_payment, container, false);

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_payments : ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            refTenantId = bundle.getInt("2", -1);
            Log.d("TenantRefId_in_Payments : ==> ", String.valueOf(refTenantId));
        }

        //floating button
        btnadd = v.findViewById(R.id.paymentaddbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();
                // viewVisibility();

            }
        });

        //


        //retrive full details part
        paymentRecyclerView = v.findViewById(R.id.paymentRecyclerId);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        paymentList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        paymentList.addAll(databaseQueryClass.getAllPaymentsbyId(refFlatId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        paymentListRecyclerViewAdapter = new PaymentListRecyclerView(getContext(), paymentList);
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        paymentRecyclerView.setAdapter(paymentListRecyclerViewAdapter);



        return  v;
    }

    private void openActivityAddTenant() {
        Intent i = new Intent(getActivity(), AddPayments.class );
        i.putExtra("flatId", refFlatId);
        getContext().startActivity(i);

    }

    @Override
    public void onPaymentCreated(PaymentsModelClass payments) {
        paymentList.add(payments);
        paymentListRecyclerViewAdapter.notifyDataSetChanged();
    }
}
