package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class OverView extends Fragment implements TenantCreateListener {

    FloatingActionButton btnadd;
    TextView name, phone, email;
    String strName, strPhone, strEmail;
    private long refFlatId;
    private long refTenantId;
    private long tenantId;
    // private String address;
    private RecyclerView tenantRecyclerView;
    private TenantListRecyclerAdapter tenantListRecyclerAdapter;

    private RecyclerView balanceRecyclerView;


    private DatabaseQueryClass databaseQueryClass;
    private TenantModelClass mtenantModelClass;

    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;

    private List<TenantModelClass> tenantList;
    private Toolbar toolbar;

    private long rFid;

    public OverView() {
        //empty
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_over_view, container, false);
        databaseQueryClass = new DatabaseQueryClass(getContext());

        balanceRecyclerView = view.findViewById(R.id.recyclerViewBalances);


        relativeLayout = view.findViewById(R.id.floatingButtonHide);
        linearLayout = view.findViewById(R.id.tenantListHide);



        //refFlatId = getArguments().getLong(Config.COLUMN_FLATS_ID, -1);

        // rFid= getArguments().getLong("rFId");
        //Log.d("rFid : ==> ", String.valueOf(rFid));


        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_overView : ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));

        refTenantId = getArguments().getLong("2");
        Log.e("TenantId in OverView ====> ", String.valueOf(refTenantId));

        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in OverView ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*

        if(getArguments()!=null)
        {
            refTenantId = getArguments().getLong("TenantId");
            //Log.d("Tenanid :", "No Id");
            Log.d("TenantRefId_in_OverView : ==> ", String.valueOf(refTenantId));
        }

         */


        //retrive full details part
        tenantRecyclerView = view.findViewById(R.id.tenantRecyclerId);


        tenantList = new ArrayList<>();
        // Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllTenantsByFId(refFlatId)));
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(refFlatId));
        //Log.d("TenantList : ==> ", String.valueOf(tenantList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        tenantListRecyclerAdapter = new TenantListRecyclerAdapter(getContext(), tenantList, OverView.this);
        tenantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tenantRecyclerView.setAdapter(tenantListRecyclerAdapter);


        //visibility of floating button
        //btnadd.setVisibility(btnadd.VISIBLE);
        viewVisibility();

        //visibility of list
        listVisibility();


        //floating button
        btnadd = view.findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();
                /*
                //visibility of floating button
                //btnadd.setVisibility(btnadd.VISIBLE);
                viewVisibility();

                 */

            }
        });

        return view;
    }


    private void openActivityAddTenant() {
        /*
       Intent i = new Intent(getActivity(), AddT.class );
       i.putExtra("flatId", refFlatId);
       getContext().startActivity(i);

         */

        AddTenant addT = AddTenant.newInstance(refFlatId, this);
        Intent i = new Intent(getActivity(), addT.getClass());
        getContext().startActivity(i);
    }


    public void viewVisibility() {
        try {
            if ((tenantList.size() > 0)) {
                relativeLayout.setVisibility(relativeLayout.GONE);
            } else {
                relativeLayout.setVisibility(relativeLayout.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //listvisibility

    public void listVisibility() {
        try {
            if (tenantList.size() > 0) {
                linearLayout.setVisibility(linearLayout.GONE);
            } else {
                linearLayout.setVisibility(linearLayout.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTenantCreated(TenantModelClass tenant) {
        tenantList.add(tenant);
        tenantListRecyclerAdapter.notifyDataSetChanged();
        //visibility of floating button
        //btnadd.setVisibility(btnadd.VISIBLE);
        viewVisibility();

        //visibility of list
        listVisibility();

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onResume() {
        super.onResume();


        //tenant part
        databaseQueryClass = new DatabaseQueryClass(getContext());
        tenantList = new ArrayList<>();
        // Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllTenantsByFId(refFlatId)));
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(refFlatId));
        //Log.d("TenantList : ==> ", String.valueOf(tenantList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        tenantListRecyclerAdapter = new TenantListRecyclerAdapter(getContext(), tenantList, OverView.this);
        tenantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tenantRecyclerView.setAdapter(tenantListRecyclerAdapter);


        /*
        //balance part

        databaseQueryClass = new DatabaseQueryClass(getContext());
        tenantList = new ArrayList<>();
        // Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllTenantsByFId(refFlatId)));
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(refFlatId));
        //Log.d("TenantList : ==> ", String.valueOf(tenantList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        balancesListRecyclerView = new BalancesListRecyclerView(getContext(), tenantList, OverView.this);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        balanceRecyclerView.setAdapter(balancesListRecyclerView);

         */
    }
}