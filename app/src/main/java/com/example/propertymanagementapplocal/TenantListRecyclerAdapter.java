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

import static android.os.Build.VERSION_CODES.O;

public class TenantListRecyclerAdapter extends RecyclerView.Adapter<TenantListRecyclerAdapter.tenantCustomViewHolder> {

    private Context context;
    private List<TenantModelClass> tenantList;
    private DatabaseQueryClass databaseQueryClass;

    public TenantListRecyclerAdapter(Context context, List<TenantModelClass> tenantList) {
        this.context = context;
        this.tenantList = tenantList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @NonNull
    @Override
    public tenantCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_design_row_recyclerview, parent, false);
        return new tenantCustomViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull final tenantCustomViewHolder holder, final int position) {
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

       holder.optionMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PopupMenu popupMenu = new PopupMenu(context, holder.optionMenu);
               popupMenu.inflate(R.menu.option_menu);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem menuItem) {
                       switch (menuItem.getItemId()) {
                           case R.id.menu_item_Edit:
                               //Toast.makeText(context, "long update", Toast.LENGTH_LONG).show();
                               UpdateTenantDetails updateTenant = UpdateTenantDetails.newInstance(tenants.getTenantId(), tenantlistPosition, new TenantUpdateListener() {
                                   @Override
                                   public void onTenantInfoUpdated(TenantModelClass tenant, int position) {
                                       tenantList.set(position, tenant);
                                       notifyDataSetChanged();
                                   }
                               });

                               Intent i = new Intent(context, updateTenant.getClass());
                               context.startActivity(i);
                               break;

                               //delete case
                           case R.id.menu_item_delete:

                               //Toast.makeText(context, "long delete", Toast.LENGTH_LONG).show();

                               TenantModelClass mTenant = tenantList.get(position);
                               long count = databaseQueryClass.deleteTenantById(mTenant.getTenantId());

                               if(count>0){
                                   tenantList.remove(position);
                                   notifyDataSetChanged();
                                   // ((TotalTenant) context);
                                   Toast.makeText(context, "Tenant deleted successfully", Toast.LENGTH_LONG).show();
                               } else
                                   Toast.makeText(context, "Property not deleted. Something wrong!", Toast.LENGTH_LONG).show();

                               break;

                               //see details
                           case R.id.menu_item_seeDetails:
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
                               break;
                       }
                       return false;
                   }
               });
               popupMenu.show();
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
        TextView optionMenu;
        public tenantCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            tenantExtraname = itemView.findViewById(R.id.tenantExtraName);
            name = itemView.findViewById(R.id.tenantName);
            email = itemView.findViewById(R.id.tenantEmail);
            phone = itemView.findViewById(R.id.tenantPhone);
            optionMenu = itemView.findViewById(R.id.textOption);




        }
    }
}
