package com.example.propertymanagementapplocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BalancesListRecyclerView extends RecyclerView.Adapter<BalancesListRecyclerView.CustomViewHolder> {

    private Context context;
    private List<TenantModelClass> tenantList;
    private DatabaseQueryClass databaseQueryClass;
    InvoiceModelClass invoiceModelClass;
    PaymentsModelClass paymentsModelClass;
    OverView overView;

    public BalancesListRecyclerView(Context context, List<TenantModelClass> tenantList,  OverView overView) {
        this.context = context;
        this.tenantList = tenantList;
        this.overView = overView;
        databaseQueryClass = new DatabaseQueryClass(context);

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.balance_design_row_recycler_view, parent, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int tenantlistPosition = position;
        final TenantModelClass tenants = tenantList.get(position);
        final long tenantId = tenants.getTenantId();

        //holder.balanceamount.setText(tenants.getTenantName());

        try{
            invoiceModelClass = databaseQueryClass.getIvoiceIdByTenantId(tenantId);
            String balance = invoiceModelClass.getRent();
            holder.balanceamount.setText(balance);
        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        try {
            paymentsModelClass = databaseQueryClass
        }catch (Exception e){
            e.printStackTrace();
        }

         */

    }

    @Override
    public int getItemCount()
    {
        return tenantList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView balanceamount;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            balanceamount = itemView.findViewById(R.id.balanceAmountTv);
        }
    }
}
