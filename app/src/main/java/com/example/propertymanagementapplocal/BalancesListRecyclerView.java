package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
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
        } else if(viewType == 1) {
            return new PaymentsHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.balances_payments_design_row_recyclerview,
                            parent,
                            false
                    )
            );
        }else  {
            return new titleHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.balances_title_row_recyclerview,
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

            } else if (getItemViewType(position) == 2) {
                BalancesModel balancesModel = (BalancesModel) balancesList.get(position);
                ((titleHolder) holder).setTitleData(balancesModel);
            }

        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");


           if( getItemViewType(position) == 2){
               ((titleHolder) holder).currencyId2.setText(curv);
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

       private TextView invoiceissuedDateTv, paymentduedateTv, rentTextView, invoiceIdTv, currencyId0;

        InvoiceHolder(@NonNull View itemView) {
            super(itemView);

            invoiceissuedDateTv = itemView.findViewById(R.id.invoiceissuedDateTextViewBalances);
            paymentduedateTv = itemView.findViewById(R.id.paymentdueTextViewBalances);
            rentTextView = itemView.findViewById(R.id.rentTextViewBalances);
            invoiceIdTv = itemView.findViewById(R.id.invoiceIdBalances);
            currencyId0 = itemView.findViewById(R.id.currencyId0);
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

         private TextView receivedfrom, dateReceived, amount, currencyId1;

            PaymentsHolder(@NonNull View itemView) {
            super(itemView);

            receivedfrom = itemView.findViewById(R.id.paymentReceivedFromTextViewBalance);
            dateReceived = itemView.findViewById(R.id.paymentDateReceivedTextViewBalance);
            amount = itemView.findViewById(R.id.paymentAmountTextViewBalance);
            currencyId1 = itemView.findViewById(R.id.currencyId1);
        }

        void setPaymentsData(PaymentsModelClass paymentsModelClass){
            receivedfrom.setText(paymentsModelClass.getReceivedfrom());
            dateReceived.setText(paymentsModelClass.getDatereceived());
            amount.setText(paymentsModelClass.getAmount());
        }
    }

    static  class titleHolder extends RecyclerView.ViewHolder{
        TextView titleMonth, totalAmount, currencyId2;

        public titleHolder(@NonNull View itemView) {
            super(itemView);

            titleMonth = itemView.findViewById(R.id.titleMonthTv);
            totalAmount = itemView.findViewById(R.id.totalAmountTv);
            currencyId2 = itemView.findViewById(R.id.currencyId2);

        }

        void setTitleData(BalancesModel balancesModel){
            titleMonth.setText(balancesModel.getTitle());
            totalAmount.setText(balancesModel.getTitleAmount());
        }
    }
}
