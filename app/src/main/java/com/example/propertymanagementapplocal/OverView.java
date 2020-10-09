package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class OverView extends Fragment implements TenantCreateListener  {

    FloatingActionButton btnadd;
    TextView name, phone, email;
    String strName, strPhone, strEmail;
    private long refFlatId;
    private long refTenantId;
    private long refPropertyId;
    // private String address;
    private RecyclerView tenantRecyclerView;
    private TenantListRecyclerAdapter tenantListRecyclerAdapter;

    private DatabaseQueryClass databaseQueryClass ;

    private List<TenantModelClass> tenantList ;
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





        //refFlatId = getArguments().getLong(Config.COLUMN_FLATS_ID, -1);

         // rFid= getArguments().getLong("rFId");
        //Log.d("rFid : ==> ", String.valueOf(rFid));



        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_overView : ==> ", String.valueOf(refFlatId));
        //Log.d("flatRefFId_in_overView : ==> ", String.valueOf(getArguments().getLong("1")));

        //
        /*
        if(getArguments()!=null)
        {
            refTenantId = getArguments().getInt("2", 0);
            Log.d("TenantRefId_in_OverView : ==> ", String.valueOf(refTenantId));
        }

         */

        if(getArguments()!=null)
        {
            refTenantId = getArguments().getLong("TenantId");
            //Log.d("Tenanid :", "No Id");
            Log.d("TenantRefId_in_OverView : ==> ", String.valueOf(refTenantId));
        }


        //retrive full details part
        tenantRecyclerView = view.findViewById(R.id.tenantRecyclerId);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        tenantList = new ArrayList<>();
       // Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllTenantsByFId(refFlatId)));
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(refFlatId));
        //Log.d("TenantList : ==> ", String.valueOf(tenantList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();

        tenantListRecyclerAdapter = new TenantListRecyclerAdapter(getContext(), tenantList);
        tenantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tenantRecyclerView.setAdapter(tenantListRecyclerAdapter);





        //floating button
        btnadd = view.findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();
                // viewVisibility();

            }
        });

        return view;
    }

    private void openActivityAddTenant() {
       Intent i = new Intent(getActivity(), AddT.class );
       i.putExtra("flatId", refFlatId);
       getContext().startActivity(i);
    }



    public void viewVisibility() {
        if(tenantList.isEmpty())
            btnadd.setVisibility(View.VISIBLE);
        else
            btnadd.setVisibility(View.GONE);
    }


    /*
    private void openActivityAddTenant() {
        //AddTenantsFragment addTenant = AddTenantsFragment.newInstance(refFlatId, this);
        FragmentTransaction fr = getChildFragmentManager().beginTransaction();
       fr.replace(R.id.frag1, new AddTenantsFragment());
        fr.commit();
    }

     */


/*
    private void openActivityAddTenant() {


        AddTenant addTenant = AddTenant.newInstance(refFlatId, this);
        addTenant.show(getChildFragmentManager(), Config.CREATE_TENANT);
        }


 */


    @Override
    public void onTenantCreated(TenantModelClass tenant) {
        tenantList.add(tenant);
        tenantListRecyclerAdapter.notifyDataSetChanged();
        //viewVisibility();

    }

}