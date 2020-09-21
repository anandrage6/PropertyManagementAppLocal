package com.example.propertymanagementapplocal;

import android.content.Context;
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

    @Override
    public void onBindViewHolder(@NonNull tenantCustomViewHolder holder, int position) {
        final int tenantlistPosition = position;
        final TenantModelClass tenants = tenantList.get(position);
        final long tenantId = tenants.getTenantId();
        holder.name.setText(tenants.getTenantName());
        holder.phone.setText(tenants.getTenantphone());
        holder.email.setText(tenants.getTenantEmail());

    }

    @Override
    public int getItemCount() {

        return tenantList.size();
    }

    public class tenantCustomViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone;
        public tenantCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tenantNameTV);
            phone = itemView.findViewById(R.id.tenantPhoneTV);
            email = itemView.findViewById(R.id.tenantEmailTV);
        }
    }
}
