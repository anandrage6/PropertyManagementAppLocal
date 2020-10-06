package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    public void onBindViewHolder(@NonNull tenantCustomViewHolder holder, int position) {
        final int tenantlistPosition = position;
        final TenantModelClass tenants = tenantList.get(position);
        final long tenantId = tenants.getTenantId();
        holder.name.setText(tenants.getTenantName());
        holder.email.setText(tenants.getTenantEmail());
        holder.phone.setText(tenants.getTenantphone());
        holder.leaseStart.setText(tenants.getLeaseStart());
        holder.leaseEnd.setText(tenants.getLeaseEnd());
        holder.rentIsPaid.setText(tenants.getRentIsPaid());
        holder.totalOccupants.setText(tenants.getTotalOccupants());
        holder.notes.setText(tenants.getNotes());
        holder.rentAmount.setText(tenants.getRentAmount());
        holder.securityDeposit.setText(tenants.getSecurityDeposit());




        OverView fragmentB=new OverView();
        Bundle bundle=new Bundle();
        bundle.putLong("2",tenants.getTenantId());
        Log.d("TenantId in bundle : ==> ", String.valueOf(bundle));
        fragmentB.setArguments(bundle);




    }

    @Override
    public int getItemCount() {

        return tenantList.size();
    }

    public class tenantCustomViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone,leaseStart,leaseEnd,rentIsPaid,totalOccupants,notes,rentAmount,securityDeposit;
        public tenantCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tenantNameTV);
            email = itemView.findViewById(R.id.tenantEmailTV);
            phone = itemView.findViewById(R.id.tenantPhoneTV);
            leaseStart = itemView.findViewById(R.id.leaseStartTextView);
            leaseEnd = itemView.findViewById(R.id.leaseEndTextView);
            rentIsPaid = itemView.findViewById(R.id.rentIsPaidTextView);
            totalOccupants = itemView.findViewById(R.id.totaloccupantsTextView);
            notes = itemView.findViewById(R.id.notesTextView);
            rentAmount = itemView.findViewById(R.id.rentAmountTextView);
            securityDeposit = itemView.findViewById(R.id.depositTextView);


        }
    }
}
