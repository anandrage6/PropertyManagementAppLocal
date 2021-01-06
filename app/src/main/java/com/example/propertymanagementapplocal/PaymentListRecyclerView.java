package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentListRecyclerView extends RecyclerView.Adapter<PaymentListRecyclerView.PaymentViewHolder> {
    private Context context;
    private List<PaymentsModelClass> paymentList;
    private DatabaseQueryClass databaseQueryClass;
    PaymentFragment paymentFragment;

    public PaymentListRecyclerView(Context context, List<PaymentsModelClass> paymentList, PaymentFragment paymentFragment) {
        this.context = context;
        this.paymentList = paymentList;
        this.paymentFragment = paymentFragment;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payments_design_row_recyclerview, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentListRecyclerView.PaymentViewHolder holder, final int position) {

        final int paymentListPosition = position;
        final PaymentsModelClass payment = paymentList.get(position);
        final long paymentId = payment.getPaymentId();

        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");


        switch (curv) {
            case "ARS-$":
            case "AUD-$":
            case "BSD-$":
            case "BBD-$":
            case "BMD-$":
            case "BND-$":
            case "CAD-$":
            case "KYD-$":
            case "CLP-$":
            case "COP-$":
            case "SVC-$":
            case "FJD-$":
            case "GYD-$":
            case "HKD-$":
            case "LRD-$":
            case "MXN-$":
            case "NAD-$":
            case "NZD-$":
            case "SGD-$":
            case "SBD-$":
            case "SRD-$":
            case "TVD-$":
            case "USD-$":
                holder.currencyId.setText("$");


                break;
            case "FKP-£":
            case "EGP-£":
            case "GIP-£":
            case "GGP-£":
            case "IMP-£":
            case "JEP-£":
            case "LBP-£":
            case "SHP-£":
            case "SYP-£":
            case "GBP-£":
                holder.currencyId.setText("£");


                break;
            case "ALL-Lek":
                holder.currencyId.setText("Lek");


                break;
            case "؋-AFN":
                holder.currencyId.setText("؋");


                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                holder.currencyId.setText("ƒ");



                break;
            case "AZN-₼":
                holder.currencyId.setText("₼");


                break;
            case "BYR-p.":
                holder.currencyId.setText("p.");


                break;
            case "BZD-BZ$":
                holder.currencyId.setText("BZ$");

                break;
            case "BOB-$b":
                holder.currencyId.setText("$b");

                break;
            case "BAM-KM":
                holder.currencyId.setText("KM");


                break;
            case "BWP-P":
                holder.currencyId.setText("P");


                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                holder.currencyId.setText("лв");


                break;
            case "BRL-R$":
                holder.currencyId.setText("R$");

                break;
            case "KHR-៛":
                holder.currencyId.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                holder.currencyId.setText("¥");


                break;
            case "CRC-₡":
                holder.currencyId.setText("₡");

                break;
            case "HRK-kn":
                holder.currencyId.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                holder.currencyId.setText("₱");



                break;
            case "CZK-Kč":
                holder.currencyId.setText("Kč");


                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                holder.currencyId.setText("kr");


                break;
            case "DOP-RD$":
                holder.currencyId.setText("RD$");


                break;
            case "EUR-€":
                holder.currencyId.setText("€");


                break;
            case "GEL-₾":
                holder.currencyId.setText("₾");


                break;
            case "GHC-¢":
                holder.currencyId.setText("¢");


                break;
            case "GTQ-Q":
                holder.currencyId.setText("Q");


                break;
            case "HNL-L":
                holder.currencyId.setText("L");

                break;
            case "HUF-Ft":
                holder.currencyId.setText("Ft");


                break;
            case "INR-₹":
                holder.currencyId.setText("₹");


                break;
            case "IDR-Rp":
                holder.currencyId.setText("Rp");


                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                holder.currencyId.setText("﷼");


                break;
            case "ILS-₪":
                holder.currencyId.setText("₪");

                break;
            case "JMD-J$":
                holder.currencyId.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                holder.currencyId.setText("₩");


                break;
            case "LAK-₭":
                holder.currencyId.setText("₭");


                break;
            case "LVL-Ls":
                holder.currencyId.setText("Ls");

                break;
            case "LTL-Lt":
                holder.currencyId.setText("Lt");


                break;
            case "MKD-ден":
                holder.currencyId.setText("ден");

                break;
            case "MYR-RM":
                holder.currencyId.setText("RM");


                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                holder.currencyId.setText("₨");

                break;
            case "MNT-₮":
                holder.currencyId.setText("₮");

                break;
            case "MZN-MT":
                holder.currencyId.setText("MT");


                break;
            case "NIO-C$":
                holder.currencyId.setText("C$");

                break;
            case "NGN-₦":
                holder.currencyId.setText("₦");

                break;
            case "PAB-B/.":
                holder.currencyId.setText("B/.");

                break;
            case "PYG-Gs":
                holder.currencyId.setText("Gs");


                break;
            case "PEN-S/.":
                holder.currencyId.setText("S/.");


                break;

            case "PLN-zł":
                holder.currencyId.setText("zł");

                break;
            case "RON-lei":
                holder.currencyId.setText("lei");


                break;
            case "RUB-₽":
                holder.currencyId.setText("₽");


                break;
            case "RSD-Дин.":
                holder.currencyId.setText("Дин.");

                break;
            case "SOS-S":
                holder.currencyId.setText("S");


                break;
            case "ZAR-R":
                holder.currencyId.setText("R");


                break;
            case "CHF-CHF":
                holder.currencyId.setText("CHF");


                break;
            case "TWD-NT$":
                holder.currencyId.setText("NT$");


                break;
            case "THB-฿":
                holder.currencyId.setText("฿");


                break;
            case "TTD-TT$":
                holder.currencyId.setText("TT$");


                break;
            case "TRL-₺":
                holder.currencyId.setText("₺");


                break;
            case "UAH-₴":
                holder.currencyId.setText("₴");

                break;
            case "UYU-$U":
                holder.currencyId.setText("$U");

                break;
            case "VEF-Bs":
                holder.currencyId.setText("Bs");

                break;
            case "VND-₫":
                holder.currencyId.setText("₫");


                break;
            case "ZWD-Z$":
                holder.currencyId.setText("Z$");
                break;

        }


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

        /*
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
                                //Toast.makeText(context, "long settings", Toast.LENGTH_LONG).show();
                                PaymentsModelClass mPayment = paymentList.get(position);
                                long count = databaseQueryClass.deletePaymentsById(mPayment.getPaymentId());

                                if(count>0){
                                    paymentList.remove(position);
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

         */

        //option menu on card

        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.optionMenu);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            //edit case
                            case R.id.menu_item_Edit:
                                //Toast.makeText(context, "long update", Toast.LENGTH_LONG).show();
                                UpdatePaymentDetails updatePaymentDetails = UpdatePaymentDetails.newInstance(payment.getPaymentId(), paymentListPosition, new PaymentUpdateListener() {
                                    @Override
                                    public void onPaymentInfoUpdated(PaymentsModelClass payment, int position) {
                                        paymentList.set(position, payment);
                                        notifyDataSetChanged();

                                    }

                                });
                                Intent i = new Intent(context, updatePaymentDetails.getClass());
                                context.startActivity(i);

                                break;

                            //settings case
                            case R.id.menu_item_delete:

                                //Toast.makeText(context, "long settings", Toast.LENGTH_LONG).show();

                                PaymentsModelClass mPayment = paymentList.get(position);
                                long count = databaseQueryClass.deletePaymentsById(mPayment.getPaymentId());

                                if (count > 0) {
                                    paymentList.remove(position);
                                    notifyDataSetChanged();
                                    // ((TotalTenant) context);

                                    ((PaymentFragment) paymentFragment).listvisibility();
                                    Toast.makeText(context, "Tenant deleted successfully", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(context, "Property not deleted. Something wrong!", Toast.LENGTH_LONG).show();

                                break;


                            //see details
                            case R.id.menu_item_seeDetails:

                                Intent intent = new Intent(context, PaymentDetails.class);
                                intent.putExtra(Config.COLUMN_PAYMENT_ID, payment.getPaymentId());
                                intent.putExtra(Config.COLUMN_PAYMENT_AMOUNT, payment.getAmount());
                                intent.putExtra(Config.COLUMN_PAYMENT_PAIDWITH, payment.getPaidwith());
                                intent.putExtra(Config.COLUMN_PAYMENT_DATERECEIVED, payment.getDatereceived());
                                intent.putExtra(Config.COLUMN_PAYMENT_RECEIVEDFROM, payment.getReceivedfrom());
                                intent.putExtra(Config.COLUMN_PAYMENT_TAXSTATUS, payment.getTaxstatus());
                                intent.putExtra(Config.COLUMN_PAYMENT_NOTES, payment.getNotes());

                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView receivedfrom, dateReceived, amount;
        TextView optionMenu, currencyId;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            receivedfrom = itemView.findViewById(R.id.paymentReceivedFromTextView);
            dateReceived = itemView.findViewById(R.id.paymentDateReceivedTextView);
            amount = itemView.findViewById(R.id.paymentAmountTextView);
            optionMenu = itemView.findViewById(R.id.textOption);
            currencyId = itemView.findViewById(R.id.currencyId);

        }
    }
}
