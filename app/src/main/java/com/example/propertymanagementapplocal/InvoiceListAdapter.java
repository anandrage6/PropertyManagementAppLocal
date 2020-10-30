package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.invoiceCustomViewHolder> {
    private Context context;
    private List<InvoiceModelClass> invoiceList;
    private DatabaseQueryClass databaseQueryClass;

    public InvoiceListAdapter(Context context, List<InvoiceModelClass> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @NonNull
    @Override
    public invoiceCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_design_row_recyclerview, parent, false);
        return new invoiceCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final invoiceCustomViewHolder holder, final int position) {
        final int invoiceListPosition = position;
        final InvoiceModelClass invoice = invoiceList.get(position);
        final long invoiceId = invoice.getInvoiceId();

        /*
        holder.title.setText(invoice.getTitle());
        holder.details.setText(invoice.getDetails());
        holder.amount.setText(invoice.getAmount());
        holder.rent.setText(invoice.getRent());
        holder.invoiceIssued.setText(invoice.getInvoiceIssued());
        holder.paymentdue.setText(invoice.getPaymentDue());
        holder.note.setText(invoice.getNotes());

         */

        String id = String.valueOf(invoiceId);

        //list details

        holder.invoiceIdTv.setText(" #"+id);
        holder.invoiceissuedDateTv.setText(invoice.getInvoiceIssued());
        holder.paymentduedateTv.setText(invoice.getPaymentDue());
        holder.rentTextView.setText(invoice.getRent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InvoiceFullDetails.class);
                intent.putExtra(Config.COLUMN_INVOICE_ID, invoice.getInvoiceId());
                intent.putExtra(Config.COLUMN_INVOICE_TITLE, invoice.getTitle());
                intent.putExtra(Config.COLUMN_INVOICE_DETAILS, invoice.getDetails());
                intent.putExtra(Config.COLUMN_INVOICE_AMOUNT, invoice.getAmount());
                intent.putExtra(Config.COLUMN_INVOICE_RENT, invoice.getRent());
                intent.putExtra(Config.COLUMN_INVOICE_INVOICE_ISSUED, invoice.getInvoiceIssued());
                intent.putExtra(Config.COLUMN_INVOICE_PaymentDue, invoice.getPaymentDue());
                intent.putExtra(Config.COLUMN_INVOICE_Notes, invoice.getNotes());

                context.startActivity(intent);

            }
        });

        //long click
        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
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
                                //Toast.makeText(context, "long delete", Toast.LENGTH_LONG).show();

                                InvoiceModelClass mInvoice = invoiceList.get(position);
                                long count = databaseQueryClass.deleteInvoiceById(mInvoice.getInvoiceId());

                                if(count>0){
                                    invoiceList.remove(position);
                                    notifyDataSetChanged();
                                    // ((TotalTenant) context);
                                    Toast.makeText(context, "Tenant deleted successfully", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(context, "Property not deleted. Something wrong!", Toast.LENGTH_LONG).show();

                                break;

                        }
                        return false;
                    }

                });
                popupMenu.show();
                //Toast.makeText(context, "You have long clicked", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        /*
        InvoicesFragment fragmentB=new InvoicesFragment();
        Bundle bundle=new Bundle();
        bundle.getLong("2",invoice.getInvoiceId());
        fragmentB.setArguments(bundle);
        
         */


    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class invoiceCustomViewHolder extends RecyclerView.ViewHolder {

        TextView invoiceissuedDateTv, paymentduedateTv, rentTextView, invoiceIdTv;

        public invoiceCustomViewHolder(@NonNull View itemView) {
            super(itemView);



            invoiceissuedDateTv = itemView.findViewById(R.id.invoiceissuedDateTextView);
            paymentduedateTv = itemView.findViewById(R.id.paymentdueTextView);
            rentTextView = itemView.findViewById(R.id.rentTextView);
            invoiceIdTv = itemView.findViewById(R.id.invoiceId);

        }
    }
}
