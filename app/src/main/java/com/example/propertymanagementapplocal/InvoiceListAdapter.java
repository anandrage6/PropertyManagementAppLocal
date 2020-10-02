package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.invoiceCustomViewHolder> {
    private Context context;
    private List<InvoiceModelClass> invoiceList;

    public InvoiceListAdapter(Context context, List<InvoiceModelClass> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
    }

    @NonNull
    @Override
    public invoiceCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_design_row_recyclerview, parent, false);
        return new invoiceCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull invoiceCustomViewHolder holder, int position) {
        final int invoiceListPosition = position;
        final InvoiceModelClass invoice = invoiceList.get(position);
        final long invoiceId = invoice.getInvoiceId();
        holder.title.setText(invoice.getTitle());
        holder.details.setText(invoice.getDetails());
        holder.amount.setText(invoice.getAmount());
        holder.rent.setText(invoice.getRent());
        holder.invoiceIssued.setText(invoice.getInvoiceIssued());
        holder.paymentdue.setText(invoice.getPaymentDue());
        holder.note.setText(invoice.getNotes());

        InvoicesFragment fragmentB=new InvoicesFragment();
        Bundle bundle=new Bundle();
        bundle.getLong("2",invoice.getInvoiceId());
        fragmentB.setArguments(bundle);


    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class invoiceCustomViewHolder extends RecyclerView.ViewHolder {
        TextView title, details, amount, rent, invoiceIssued, paymentdue, note;
        public invoiceCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTv);
            details = itemView.findViewById(R.id.detailsTv);
            amount = itemView.findViewById(R.id.amountTv);
            rent = itemView.findViewById(R.id.rentTv);
            invoiceIssued = itemView.findViewById(R.id.invoiceissuedDateTv);
            paymentdue = itemView.findViewById(R.id.paymentdueDateTv);
            note = itemView.findViewById(R.id.noteTv);

        }
    }
}
