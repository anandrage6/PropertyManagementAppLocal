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


        if (getItemViewType(position) == 0) {
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
                    ((InvoiceHolder) holder).currencyId0.setText("$");


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
                    ((InvoiceHolder) holder).currencyId0.setText("£");


                    break;
                case "ALL-Lek":
                    ((InvoiceHolder) holder).currencyId0.setText("Lek");


                    break;
                case "؋-AFN":
                    ((InvoiceHolder) holder).currencyId0.setText("؋");


                    break;
                case "AWG-ƒ":
                case "ANG-ƒ":
                    ((InvoiceHolder) holder).currencyId0.setText("ƒ");


                    break;
                case "AZN-₼":
                    ((InvoiceHolder) holder).currencyId0.setText("₼");


                    break;
                case "BYR-p.":
                    ((InvoiceHolder) holder).currencyId0.setText("p.");


                    break;
                case "BZD-BZ$":
                    ((InvoiceHolder) holder).currencyId0.setText("BZ$");

                    break;
                case "BOB-$b":
                    ((InvoiceHolder) holder).currencyId0.setText("$b");

                    break;
                case "BAM-KM":
                    ((InvoiceHolder) holder).currencyId0.setText("KM");


                    break;
                case "BWP-P":
                    ((InvoiceHolder) holder).currencyId0.setText("P");


                    break;
                case "BGN-лв":
                case "KZT-лв":
                case "KGS-лв":
                case "UZS-лв":
                    ((InvoiceHolder) holder).currencyId0.setText("лв");


                    break;
                case "BRL-R$":
                    ((InvoiceHolder) holder).currencyId0.setText("R$");

                    break;
                case "KHR-៛":
                    ((InvoiceHolder) holder).currencyId0.setText("៛");

                    break;
                case "CNY-¥":
                case "JPY-¥":
                    ((InvoiceHolder) holder).currencyId0.setText("¥");


                    break;
                case "CRC-₡":
                    ((InvoiceHolder) holder).currencyId0.setText("₡");

                    break;
                case "HRK-kn":
                    ((InvoiceHolder) holder).currencyId0.setText("kn");

                    break;
                case "CUP-₱":
                case "PHP-₱":
                    ((InvoiceHolder) holder).currencyId0.setText("₱");


                    break;
                case "CZK-Kč":
                    ((InvoiceHolder) holder).currencyId0.setText("Kč");


                    break;
                case "DKK-kr":
                case "EEK-kr":
                case "ISK-kr":
                case "NOK-kr":
                case "SEK-kr":
                    ((InvoiceHolder) holder).currencyId0.setText("kr");


                    break;
                case "DOP-RD$":
                    ((InvoiceHolder) holder).currencyId0.setText("RD$");


                    break;
                case "EUR-€":
                    ((InvoiceHolder) holder).currencyId0.setText("€");


                    break;
                case "GEL-₾":
                    ((InvoiceHolder) holder).currencyId0.setText("₾");


                    break;
                case "GHC-¢":
                    ((InvoiceHolder) holder).currencyId0.setText("¢");


                    break;
                case "GTQ-Q":
                    ((InvoiceHolder) holder).currencyId0.setText("Q");


                    break;
                case "HNL-L":
                    ((InvoiceHolder) holder).currencyId0.setText("L");

                    break;
                case "HUF-Ft":
                    ((InvoiceHolder) holder).currencyId0.setText("Ft");


                    break;
                case "INR-₹":
                    ((InvoiceHolder) holder).currencyId0.setText("₹");


                    break;
                case "IDR-Rp":
                    ((InvoiceHolder) holder).currencyId0.setText("Rp");


                    break;
                case "﷼-IRR":
                case "﷼-OMR":
                case "QAR-﷼":
                case "﷼-SAR":
                case "﷼-YER":
                    ((InvoiceHolder) holder).currencyId0.setText("﷼");


                    break;
                case "ILS-₪":
                    ((InvoiceHolder) holder).currencyId0.setText("₪");

                    break;
                case "JMD-J$":
                    ((InvoiceHolder) holder).currencyId0.setText("J$");

                    break;
                case "KPW-₩":
                case "KRW-₩":
                    ((InvoiceHolder) holder).currencyId0.setText("₩");


                    break;
                case "LAK-₭":
                    ((InvoiceHolder) holder).currencyId0.setText("₭");


                    break;
                case "LVL-Ls":
                    ((InvoiceHolder) holder).currencyId0.setText("Ls");

                    break;
                case "LTL-Lt":
                    ((InvoiceHolder) holder).currencyId0.setText("Lt");


                    break;
                case "MKD-ден":
                    ((InvoiceHolder) holder).currencyId0.setText("ден");

                    break;
                case "MYR-RM":
                    ((InvoiceHolder) holder).currencyId0.setText("RM");


                    break;
                case "MUR-₨":
                case "NPR-₨":
                case "PKR-₨":
                case "SCR-₨":
                case "LKR-₨":
                    ((InvoiceHolder) holder).currencyId0.setText("₨");

                    break;
                case "MNT-₮":
                    ((InvoiceHolder) holder).currencyId0.setText("₮");

                    break;
                case "MZN-MT":
                    ((InvoiceHolder) holder).currencyId0.setText("MT");


                    break;
                case "NIO-C$":
                    ((InvoiceHolder) holder).currencyId0.setText("C$");

                    break;
                case "NGN-₦":
                    ((InvoiceHolder) holder).currencyId0.setText("₦");

                    break;
                case "PAB-B/.":
                    ((InvoiceHolder) holder).currencyId0.setText("B/.");

                    break;
                case "PYG-Gs":
                    ((InvoiceHolder) holder).currencyId0.setText("Gs");


                    break;
                case "PEN-S/.":
                    ((InvoiceHolder) holder).currencyId0.setText("S/.");


                    break;

                case "PLN-zł":
                    ((InvoiceHolder) holder).currencyId0.setText("zł");

                    break;
                case "RON-lei":
                    ((InvoiceHolder) holder).currencyId0.setText("lei");


                    break;
                case "RUB-₽":
                    ((InvoiceHolder) holder).currencyId0.setText("₽");


                    break;
                case "RSD-Дин.":
                    ((InvoiceHolder) holder).currencyId0.setText("Дин.");

                    break;
                case "SOS-S":
                    ((InvoiceHolder) holder).currencyId0.setText("S");


                    break;
                case "ZAR-R":
                    ((InvoiceHolder) holder).currencyId0.setText("R");


                    break;
                case "CHF-CHF":
                    ((InvoiceHolder) holder).currencyId0.setText("CHF");


                    break;
                case "TWD-NT$":
                    ((InvoiceHolder) holder).currencyId0.setText("NT$");


                    break;
                case "THB-฿":
                    ((InvoiceHolder) holder).currencyId0.setText("฿");


                    break;
                case "TTD-TT$":
                    ((InvoiceHolder) holder).currencyId0.setText("TT$");


                    break;
                case "TRL-₺":
                    ((InvoiceHolder) holder).currencyId0.setText("₺");


                    break;
                case "UAH-₴":
                    ((InvoiceHolder) holder).currencyId0.setText("₴");

                    break;
                case "UYU-$U":
                    ((InvoiceHolder) holder).currencyId0.setText("$U");

                    break;
                case "VEF-Bs":
                    ((InvoiceHolder) holder).currencyId0.setText("Bs");

                    break;
                case "VND-₫":
                    ((InvoiceHolder) holder).currencyId0.setText("₫");


                    break;
                case "ZWD-Z$":
                    ((InvoiceHolder) holder).currencyId0.setText("Z$");
                    break;

            }
        }

        if (getItemViewType(position) == 1) {
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
                    ((PaymentsHolder) holder).currencyId1.setText("$");


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
                    ((PaymentsHolder) holder).currencyId1.setText("£");


                    break;
                case "ALL-Lek":
                    ((PaymentsHolder) holder).currencyId1.setText("Lek");


                    break;
                case "؋-AFN":
                    ((PaymentsHolder) holder).currencyId1.setText("؋");


                    break;
                case "AWG-ƒ":
                case "ANG-ƒ":
                    ((PaymentsHolder) holder).currencyId1.setText("ƒ");


                    break;
                case "AZN-₼":
                    ((PaymentsHolder) holder).currencyId1.setText("₼");


                    break;
                case "BYR-p.":
                    ((PaymentsHolder) holder).currencyId1.setText("p.");


                    break;
                case "BZD-BZ$":
                    ((PaymentsHolder) holder).currencyId1.setText("BZ$");

                    break;
                case "BOB-$b":
                    ((PaymentsHolder) holder).currencyId1.setText("$b");

                    break;
                case "BAM-KM":
                    ((PaymentsHolder) holder).currencyId1.setText("KM");


                    break;
                case "BWP-P":
                    ((PaymentsHolder) holder).currencyId1.setText("P");


                    break;
                case "BGN-лв":
                case "KZT-лв":
                case "KGS-лв":
                case "UZS-лв":
                    ((PaymentsHolder) holder).currencyId1.setText("лв");


                    break;
                case "BRL-R$":
                    ((PaymentsHolder) holder).currencyId1.setText("R$");

                    break;
                case "KHR-៛":
                    ((PaymentsHolder) holder).currencyId1.setText("៛");

                    break;
                case "CNY-¥":
                case "JPY-¥":
                    ((PaymentsHolder) holder).currencyId1.setText("¥");


                    break;
                case "CRC-₡":
                    ((PaymentsHolder) holder).currencyId1.setText("₡");

                    break;
                case "HRK-kn":
                    ((PaymentsHolder) holder).currencyId1.setText("kn");

                    break;
                case "CUP-₱":
                case "PHP-₱":
                    ((PaymentsHolder) holder).currencyId1.setText("₱");


                    break;
                case "CZK-Kč":
                    ((PaymentsHolder) holder).currencyId1.setText("Kč");


                    break;
                case "DKK-kr":
                case "EEK-kr":
                case "ISK-kr":
                case "NOK-kr":
                case "SEK-kr":
                    ((PaymentsHolder) holder).currencyId1.setText("kr");


                    break;
                case "DOP-RD$":
                    ((PaymentsHolder) holder).currencyId1.setText("RD$");


                    break;
                case "EUR-€":
                    ((PaymentsHolder) holder).currencyId1.setText("€");


                    break;
                case "GEL-₾":
                    ((PaymentsHolder) holder).currencyId1.setText("₾");


                    break;
                case "GHC-¢":
                    ((PaymentsHolder) holder).currencyId1.setText("¢");


                    break;
                case "GTQ-Q":
                    ((PaymentsHolder) holder).currencyId1.setText("Q");


                    break;
                case "HNL-L":
                    ((PaymentsHolder) holder).currencyId1.setText("L");

                    break;
                case "HUF-Ft":
                    ((PaymentsHolder) holder).currencyId1.setText("Ft");


                    break;
                case "INR-₹":
                    ((PaymentsHolder) holder).currencyId1.setText("₹");


                    break;
                case "IDR-Rp":
                    ((PaymentsHolder) holder).currencyId1.setText("Rp");


                    break;
                case "﷼-IRR":
                case "﷼-OMR":
                case "QAR-﷼":
                case "﷼-SAR":
                case "﷼-YER":
                    ((PaymentsHolder) holder).currencyId1.setText("﷼");


                    break;
                case "ILS-₪":
                    ((PaymentsHolder) holder).currencyId1.setText("₪");

                    break;
                case "JMD-J$":
                    ((PaymentsHolder) holder).currencyId1.setText("J$");

                    break;
                case "KPW-₩":
                case "KRW-₩":
                    ((PaymentsHolder) holder).currencyId1.setText("₩");


                    break;
                case "LAK-₭":
                    ((PaymentsHolder) holder).currencyId1.setText("₭");


                    break;
                case "LVL-Ls":
                    ((PaymentsHolder) holder).currencyId1.setText("Ls");

                    break;
                case "LTL-Lt":
                    ((PaymentsHolder) holder).currencyId1.setText("Lt");


                    break;
                case "MKD-ден":
                    ((PaymentsHolder) holder).currencyId1.setText("ден");

                    break;
                case "MYR-RM":
                    ((PaymentsHolder) holder).currencyId1.setText("RM");


                    break;
                case "MUR-₨":
                case "NPR-₨":
                case "PKR-₨":
                case "SCR-₨":
                case "LKR-₨":
                    ((PaymentsHolder) holder).currencyId1.setText("₨");

                    break;
                case "MNT-₮":
                    ((PaymentsHolder) holder).currencyId1.setText("₮");

                    break;
                case "MZN-MT":
                    ((PaymentsHolder) holder).currencyId1.setText("MT");


                    break;
                case "NIO-C$":
                    ((PaymentsHolder) holder).currencyId1.setText("C$");

                    break;
                case "NGN-₦":
                    ((PaymentsHolder) holder).currencyId1.setText("₦");

                    break;
                case "PAB-B/.":
                    ((PaymentsHolder) holder).currencyId1.setText("B/.");

                    break;
                case "PYG-Gs":
                    ((PaymentsHolder) holder).currencyId1.setText("Gs");


                    break;
                case "PEN-S/.":
                    ((PaymentsHolder) holder).currencyId1.setText("S/.");


                    break;

                case "PLN-zł":
                    ((PaymentsHolder) holder).currencyId1.setText("zł");

                    break;
                case "RON-lei":
                    ((PaymentsHolder) holder).currencyId1.setText("lei");


                    break;
                case "RUB-₽":
                    ((PaymentsHolder) holder).currencyId1.setText("₽");


                    break;
                case "RSD-Дин.":
                    ((PaymentsHolder) holder).currencyId1.setText("Дин.");

                    break;
                case "SOS-S":
                    ((PaymentsHolder) holder).currencyId1.setText("S");


                    break;
                case "ZAR-R":
                    ((PaymentsHolder) holder).currencyId1.setText("R");


                    break;
                case "CHF-CHF":
                    ((PaymentsHolder) holder).currencyId1.setText("CHF");


                    break;
                case "TWD-NT$":
                    ((PaymentsHolder) holder).currencyId1.setText("NT$");


                    break;
                case "THB-฿":
                    ((PaymentsHolder) holder).currencyId1.setText("฿");


                    break;
                case "TTD-TT$":
                    ((PaymentsHolder) holder).currencyId1.setText("TT$");


                    break;
                case "TRL-₺":
                    ((PaymentsHolder) holder).currencyId1.setText("₺");


                    break;
                case "UAH-₴":
                    ((PaymentsHolder) holder).currencyId1.setText("₴");

                    break;
                case "UYU-$U":
                    ((PaymentsHolder) holder).currencyId1.setText("$U");

                    break;
                case "VEF-Bs":
                    ((PaymentsHolder) holder).currencyId1.setText("Bs");

                    break;
                case "VND-₫":
                    ((PaymentsHolder) holder).currencyId1.setText("₫");


                    break;
                case "ZWD-Z$":
                    ((PaymentsHolder) holder).currencyId1.setText("Z$");
                    break;

            }
        }




        if( getItemViewType(position) == 2){
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
                    ((titleHolder) holder).currencyId2.setText("$");


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
                    ((titleHolder) holder).currencyId2.setText("£");


                    break;
                case "ALL-Lek":
                    ((titleHolder) holder).currencyId2.setText("Lek");


                    break;
                case "؋-AFN":
                    ((titleHolder) holder).currencyId2.setText("؋");


                    break;
                case "AWG-ƒ":
                case "ANG-ƒ":
                    ((titleHolder) holder).currencyId2.setText("ƒ");



                    break;
                case "AZN-₼":
                    ((titleHolder) holder).currencyId2.setText("₼");


                    break;
                case "BYR-p.":
                    ((titleHolder) holder).currencyId2.setText("p.");


                    break;
                case "BZD-BZ$":
                    ((titleHolder) holder).currencyId2.setText("BZ$");

                    break;
                case "BOB-$b":
                    ((titleHolder) holder).currencyId2.setText("$b");

                    break;
                case "BAM-KM":
                    ((titleHolder) holder).currencyId2.setText("KM");


                    break;
                case "BWP-P":
                    ((titleHolder) holder).currencyId2.setText("P");


                    break;
                case "BGN-лв":
                case "KZT-лв":
                case "KGS-лв":
                case "UZS-лв":
                    ((titleHolder) holder).currencyId2.setText("лв");


                    break;
                case "BRL-R$":
                    ((titleHolder) holder).currencyId2.setText("R$");

                    break;
                case "KHR-៛":
                    ((titleHolder) holder).currencyId2.setText("៛");

                    break;
                case "CNY-¥":
                case "JPY-¥":
                    ((titleHolder) holder).currencyId2.setText("¥");


                    break;
                case "CRC-₡":
                    ((titleHolder) holder).currencyId2.setText("₡");

                    break;
                case "HRK-kn":
                    ((titleHolder) holder).currencyId2.setText("kn");

                    break;
                case "CUP-₱":
                case "PHP-₱":
                    ((titleHolder) holder).currencyId2.setText("₱");



                    break;
                case "CZK-Kč":
                    ((titleHolder) holder).currencyId2.setText("Kč");


                    break;
                case "DKK-kr":
                case "EEK-kr":
                case "ISK-kr":
                case "NOK-kr":
                case "SEK-kr":
                    ((titleHolder) holder).currencyId2.setText("kr");


                    break;
                case "DOP-RD$":
                    ((titleHolder) holder).currencyId2.setText("RD$");


                    break;
                case "EUR-€":
                    ((titleHolder) holder).currencyId2.setText("€");


                    break;
                case "GEL-₾":
                    ((titleHolder) holder).currencyId2.setText("₾");


                    break;
                case "GHC-¢":
                    ((titleHolder) holder).currencyId2.setText("¢");


                    break;
                case "GTQ-Q":
                    ((titleHolder) holder).currencyId2.setText("Q");


                    break;
                case "HNL-L":
                    ((titleHolder) holder).currencyId2.setText("L");

                    break;
                case "HUF-Ft":
                    ((titleHolder) holder).currencyId2.setText("Ft");


                    break;
                case "INR-₹":
                    ((titleHolder) holder).currencyId2.setText("₹");


                    break;
                case "IDR-Rp":
                    ((titleHolder) holder).currencyId2.setText("Rp");


                    break;
                case "﷼-IRR":
                case "﷼-OMR":
                case "QAR-﷼":
                case "﷼-SAR":
                case "﷼-YER":
                    ((titleHolder) holder).currencyId2.setText("﷼");


                    break;
                case "ILS-₪":
                    ((titleHolder) holder).currencyId2.setText("₪");

                    break;
                case "JMD-J$":
                    ((titleHolder) holder).currencyId2.setText("J$");

                    break;
                case "KPW-₩":
                case "KRW-₩":
                    ((titleHolder) holder).currencyId2.setText("₩");


                    break;
                case "LAK-₭":
                    ((titleHolder) holder).currencyId2.setText("₭");


                    break;
                case "LVL-Ls":
                    ((titleHolder) holder).currencyId2.setText("Ls");

                    break;
                case "LTL-Lt":
                    ((titleHolder) holder).currencyId2.setText("Lt");


                    break;
                case "MKD-ден":
                    ((titleHolder) holder).currencyId2.setText("ден");

                    break;
                case "MYR-RM":
                    ((titleHolder) holder).currencyId2.setText("RM");


                    break;
                case "MUR-₨":
                case "NPR-₨":
                case "PKR-₨":
                case "SCR-₨":
                case "LKR-₨":
                    ((titleHolder) holder).currencyId2.setText("₨");

                    break;
                case "MNT-₮":
                    ((titleHolder) holder).currencyId2.setText("₮");

                    break;
                case "MZN-MT":
                    ((titleHolder) holder).currencyId2.setText("MT");


                    break;
                case "NIO-C$":
                    ((titleHolder) holder).currencyId2.setText("C$");

                    break;
                case "NGN-₦":
                    ((titleHolder) holder).currencyId2.setText("₦");

                    break;
                case "PAB-B/.":
                    ((titleHolder) holder).currencyId2.setText("B/.");

                    break;
                case "PYG-Gs":
                    ((titleHolder) holder).currencyId2.setText("Gs");


                    break;
                case "PEN-S/.":
                    ((titleHolder) holder).currencyId2.setText("S/.");


                    break;

                case "PLN-zł":
                    ((titleHolder) holder).currencyId2.setText("zł");

                    break;
                case "RON-lei":
                    ((titleHolder) holder).currencyId2.setText("lei");


                    break;
                case "RUB-₽":
                    ((titleHolder) holder).currencyId2.setText("₽");


                    break;
                case "RSD-Дин.":
                    ((titleHolder) holder).currencyId2.setText("Дин.");

                    break;
                case "SOS-S":
                    ((titleHolder) holder).currencyId2.setText("S");


                    break;
                case "ZAR-R":
                    ((titleHolder) holder).currencyId2.setText("R");


                    break;
                case "CHF-CHF":
                    ((titleHolder) holder).currencyId2.setText("CHF");


                    break;
                case "TWD-NT$":
                    ((titleHolder) holder).currencyId2.setText("NT$");


                    break;
                case "THB-฿":
                    ((titleHolder) holder).currencyId2.setText("฿");


                    break;
                case "TTD-TT$":
                    ((titleHolder) holder).currencyId2.setText("TT$");


                    break;
                case "TRL-₺":
                    ((titleHolder) holder).currencyId2.setText("₺");


                    break;
                case "UAH-₴":
                    ((titleHolder) holder).currencyId2.setText("₴");

                    break;
                case "UYU-$U":
                    ((titleHolder) holder).currencyId2.setText("$U");

                    break;
                case "VEF-Bs":
                    ((titleHolder) holder).currencyId2.setText("Bs");

                    break;
                case "VND-₫":
                    ((titleHolder) holder).currencyId2.setText("₫");


                    break;
                case "ZWD-Z$":
                    ((titleHolder) holder).currencyId2.setText("Z$");
                    break;

            }
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
        TextView currencyId0;

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

         private TextView receivedfrom, dateReceived, amount;
                 TextView currencyId1;

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
