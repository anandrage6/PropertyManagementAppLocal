package com.example.propertymanagementapplocal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BalancesListRecyclerView extends RecyclerView.Adapter {



    private Context context;
    private List<PaymentsModelClass> paymentList;
    private List<InvoiceModelClass> invoiceList;

    public BalancesListRecyclerView(Context context, List<PaymentsModelClass> paymentList, List<InvoiceModelClass> invoiceList) {
        this.context = context;
        this.paymentList = paymentList;
        this.invoiceList = invoiceList;
    }

    @Override
    public int getItemViewType(int position) {
        if (!invoiceList.isEmpty()) {
            return 0;
        }
        if(!paymentList.isEmpty()){
            return 1;
        }
        return Integer.parseInt(null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.invoice_design_row_recyclerview, parent, false);
            return new InvoiceViewHolderOne(view);
        }
        if(viewType == 1){
            view = layoutInflater.inflate(R.layout.payments_design_row_recyclerview, parent, false);
            return new PayementsViewHolderTwo(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {


                final int invoiceListPosition = position;
                final InvoiceModelClass invoice = invoiceList.get(position);
                final long invoiceId = invoice.getInvoiceId();

                InvoiceViewHolderOne invoiceViewHolderOne = (InvoiceViewHolderOne) holder;
                invoiceViewHolderOne.invoiceIdTv.setText(String.valueOf(invoiceId));
                invoiceViewHolderOne.invoiceissuedDateTv.setText(invoice.getInvoiceIssued());
                invoiceViewHolderOne.paymentduedateTv.setText(invoice.getPaymentDue());
                invoiceViewHolderOne.rentTextView.setText(invoice.getRent());


                PayementsViewHolderTwo payementsViewHolderTwo = (PayementsViewHolderTwo) holder;
                payementsViewHolderTwo.amount.setText(paymentList.get(position).getAmount());
                payementsViewHolderTwo.receivedfrom.setText(paymentList.get(position).getReceivedfrom());
                payementsViewHolderTwo.dateReceived.setText(paymentList.get(position).getDatereceived());


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return invoiceList.size() + paymentList.size();
    }

    //invoice view Holder
    class InvoiceViewHolderOne extends RecyclerView.ViewHolder{

        TextView invoiceissuedDateTv, paymentduedateTv, rentTextView, invoiceIdTv;

        public InvoiceViewHolderOne(@NonNull View itemView) {
            super(itemView);

            invoiceissuedDateTv = itemView.findViewById(R.id.invoiceissuedDateTextView);
            paymentduedateTv = itemView.findViewById(R.id.paymentdueTextView);
            rentTextView = itemView.findViewById(R.id.rentTextView);
            invoiceIdTv = itemView.findViewById(R.id.invoiceId);
        }
    }

    //Payements View Holder
    class PayementsViewHolderTwo extends RecyclerView.ViewHolder{

        TextView receivedfrom, dateReceived, amount;
        TextView optionMenu;

        public PayementsViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            receivedfrom = itemView.findViewById(R.id.paymentReceivedFromTextView);
            dateReceived = itemView.findViewById(R.id.paymentDateReceivedTextView);
            amount = itemView.findViewById(R.id.paymentAmountTextView);
        }
    }
}
