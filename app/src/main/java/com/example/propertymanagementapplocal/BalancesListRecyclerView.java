package com.example.propertymanagementapplocal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalancesListRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BalancesModel> balancesList;

    public BalancesListRecyclerView(Context context, List<BalancesModel> balancesList) {
        this.context = context;
        this.balancesList = balancesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0){
            return new InvoiceHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.balances_invoice_design_row_recyclerview,
                            parent,
                            false
                    )
            );
        } else {
            return new PaymentsHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.balances_payments_design_row_recyclerview,
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



            if (getItemViewType(position) == 0) {
                Log.e("BalancesList ===> ", String.valueOf(balancesList.size()));

                InvoiceModelClass invoiceModelClass = (InvoiceModelClass) balancesList.get(position);

                ((InvoiceHolder) holder).setInvoiceData(invoiceModelClass);

            } else if (getItemViewType(position) == 1) {
                PaymentsModelClass paymentsModelClass = (PaymentsModelClass) balancesList.get(position);
                ((PaymentsHolder) holder).setPaymentsData(paymentsModelClass);
            }


    }

    @Override
    public int getItemCount() {
        return balancesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return balancesList.get(position).getType();
    }

    // invoice part adapter
    static class InvoiceHolder extends RecyclerView.ViewHolder{

       private TextView invoiceissuedDateTv, paymentduedateTv, rentTextView, invoiceIdTv;

        InvoiceHolder(@NonNull View itemView) {
            super(itemView);

            invoiceissuedDateTv = itemView.findViewById(R.id.invoiceissuedDateTextViewBalances);
            paymentduedateTv = itemView.findViewById(R.id.paymentdueTextViewBalances);
            rentTextView = itemView.findViewById(R.id.rentTextViewBalances);
            invoiceIdTv = itemView.findViewById(R.id.invoiceIdBalances);
        }

        void setInvoiceData(InvoiceModelClass invoiceModelClass){
            invoiceissuedDateTv.setText(invoiceModelClass.getInvoiceIssued());
            paymentduedateTv.setText(invoiceModelClass.getPaymentDue());
            rentTextView.setText(invoiceModelClass.getRent());
            invoiceIdTv.setText(String.valueOf( invoiceModelClass.getInvoiceId()));


        }
    }

    //payments part Adapter

    static class PaymentsHolder extends RecyclerView.ViewHolder{

         private TextView receivedfrom, dateReceived, amount;

            PaymentsHolder(@NonNull View itemView) {
            super(itemView);

            receivedfrom = itemView.findViewById(R.id.paymentReceivedFromTextViewBalance);
            dateReceived = itemView.findViewById(R.id.paymentDateReceivedTextViewBalance);
            amount = itemView.findViewById(R.id.paymentAmountTextViewBalance);
        }

        void setPaymentsData(PaymentsModelClass paymentsModelClass){
            receivedfrom.setText(paymentsModelClass.getReceivedfrom());
            dateReceived.setText(paymentsModelClass.getDatereceived());
            amount.setText(paymentsModelClass.getAmount());
        }
    }
}
