package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentListRecyclerView extends RecyclerView.Adapter<PaymentListRecyclerView.PaymentViewHolder> {
    private Context context;
    private List<PaymentsModelClass> paymentList;

    public PaymentListRecyclerView(Context context, List<PaymentsModelClass> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payments_design_row_recyclerview, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentListRecyclerView.PaymentViewHolder holder, int position) {

        final int paymentListPosition = position;
        final PaymentsModelClass payment = paymentList.get(position);
        final long paymentId = payment.getPaymentId();

        holder.amount.setText(payment.getAmount());
        holder.receivedfrom.setText(payment.getReceivedfrom());
        holder.dateReceived.setText(payment.getDatereceived());

        //itemview

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentDetails.class);
                intent.putExtra(Config.COLUMN_PAYMENT_ID, payment.getPaymentId());
                intent.putExtra(Config.COLUMN_PAYMENT_AMOUNT, payment.getAmount());
                intent.putExtra(Config.COLUMN_PAYMENT_PAIDWITH, payment.getPaidwith());
                intent.putExtra(Config.COLUMN_PAYMENT_DATERECEIVED, payment.getDatereceived());
                intent.putExtra(Config.COLUMN_PAYMENT_RECEIVEDFROM, payment.getReceivedfrom());
                intent.putExtra(Config.COLUMN_PAYMENT_TAXSTATUS, payment.getTaxstatus());
                intent.putExtra(Config.COLUMN_PAYMENT_NOTES, payment.getNotes());

                context.startActivity(intent);
            }
        });

        //longPress on item view

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
                //Toast.makeText(context, "You have long clicked", Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView receivedfrom, dateReceived, amount;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            receivedfrom = itemView.findViewById(R.id.paymentReceivedFromTextView);
            dateReceived = itemView.findViewById(R.id.paymentDateReceivedTextView);
            amount = itemView.findViewById(R.id.paymentAmountTextView);
        }
    }
}
