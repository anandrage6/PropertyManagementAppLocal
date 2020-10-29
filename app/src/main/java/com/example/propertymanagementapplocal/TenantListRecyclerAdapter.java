package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TenantListRecyclerAdapter extends RecyclerView.Adapter<TenantListRecyclerAdapter.tenantCustomViewHolder> {

    private Context context;
    private List<TenantModelClass> tenantList;

    public TenantListRecyclerAdapter(Context context, List<TenantModelClass> tenantList) {
        this.context = context;
        this.tenantList = tenantList;
    }

    @NonNull
    @Override
    public tenantCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_design_row_recyclerview, parent, false);
        return new tenantCustomViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull final tenantCustomViewHolder holder, int position) {
        final int tenantlistPosition = position;
        final TenantModelClass tenants = tenantList.get(position);
        final long tenantId = tenants.getTenantId();

        holder.tenantExtraname.setText(tenants.getTenantName());
        holder.name.setText(tenants.getTenantName());
        holder.phone.setText(tenants.getTenantphone());
        holder.email.setText(tenants.getTenantEmail());

        //item view

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TenantFullDetails.class);
                intent.putExtra(Config.COLUMN_TENANTS_NAME, tenants.getTenantName());
                intent.putExtra(Config.COLUMN_TENANTS_PHONE, tenants.getTenantphone());
                intent.putExtra(Config.COLUMN_TENANTS_EMAIL, tenants.getTenantEmail());
                intent.putExtra(Config.COLUMN_TENANTS_LEASESTART, tenants.getLeaseStart());
                intent.putExtra(Config.COLUMN_TENANTS_LEASEEND, tenants.getLeaseEnd());
                intent.putExtra(Config.COLUMN_TENANTS_RENTISPAID, tenants.getRentIsPaid());
                intent.putExtra(Config.COLUMN_TENANTS_TOTALOCCUPANTS, tenants.getTotalOccupants());
                intent.putExtra(Config.COLUMN_TENANTS_NOTES, tenants.getNotes());
                intent.putExtra(Config.COLUMN_TENANTS_RENT, tenants.getRentAmount());
                intent.putExtra(Config.COLUMN_TENANTS_SECURITYDEPOSIT, tenants.getSecurityDeposit());
               // intent.putExtra(Config.COLUMN_TENANTS_NAME, tenants.getTenantName());
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.long_click_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.long_update:
                                Toast.makeText(context, "long update", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.long_delete:
                                Toast.makeText(context, "long delete", Toast.LENGTH_LONG).show();

                        }
                        return false;
                    }

                });
                popupMenu.show();
                return true;
            }

        });



        /*
        //passing  tenantid in to invoice fragment
        Bundle bundle = new Bundle();
        bundle.putLong("TenantId",tenants.getTenantId());
        Log.d("TenantId in bundle : ==> ", String.valueOf(bundle));
        OverView fragmentB = new OverView();
        fragmentB.setArguments(bundle);
        //Log.d("TenantId in bundle : ==> ", fragmentB);
        FragmentManager  fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.overViewList, fragmentB);
        ft.commit();

         */





        /*
        Intent i = new Intent();
        i.putExtra("TId: ==>", tenantId);
        context.startActivity(i);

         */


    }

    @Override
    public int getItemCount() {

        return tenantList.size();
    }

    public class tenantCustomViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone,leaseStart,leaseEnd,rentIsPaid,totalOccupants,notes,rentAmount,securityDeposit;
        TextView tenantExtraname;
        public tenantCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            tenantExtraname = itemView.findViewById(R.id.tenantExtraName);
            name = itemView.findViewById(R.id.tenantName);
            email = itemView.findViewById(R.id.tenantEmail);
            phone = itemView.findViewById(R.id.tenantPhone);




        }
    }
}
