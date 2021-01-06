package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Balances extends Fragment {
    long refFlatId;
    long refTenantId;

    List<BalancesModel> balancesList;
    BalancesListRecyclerView balancesListRecyclerViewAdapter;
    TextView totalBalance, remainingBalance, balancePaid, currencyId, currencyId2, currencyId3;


    //invoice part
    RecyclerView balancesRecyclerView;
    DatabaseQueryClass databaseQueryClass;
    List<InvoiceModelClass> invoiceList;
    long tenantId;
    String totalRent;
    TenantModelClass mtenantModelClass;

    //payments part
    List<PaymentsModelClass> paymentList;


    public Balances() {
        // Required empty public constructor
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balances, container, false);
        totalBalance = view.findViewById(R.id.totalBalanceTv);
        remainingBalance = view.findViewById(R.id.remainingBalanceTv);
        balancePaid = view.findViewById(R.id.balancePaidTv);

        currencyId = view.findViewById(R.id.currencyId);
        currencyId2 = view.findViewById(R.id.currencyId2);
        currencyId3 = view.findViewById(R.id.currencyId3);

        databaseQueryClass = new DatabaseQueryClass(getContext());


        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        refTenantId = getArguments().getLong("2");
        Log.e("TenantId in Documents ===> ", String.valueOf(refTenantId));


        RecyclerView balancesRecyclerView = view.findViewById(R.id.recyclerViewBalances);
        //List<BalancesModel> balancesList = new ArrayList<>(Arrays.asList((new BalancesModel(0, invoiceList))));
        List<BalancesModel> balancesList = new ArrayList<>();

        //invoices part


        // to get tenant_Id by flat_id
        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            // totalRent = mtenantModelClass.getRentAmount();
            Log.e("tenantId on resume in invoices ========> ", String.valueOf(tenantId));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Log.d("InvoiceListSize : ==> ", String.valueOf(invoiceList.size()));

        // total balances
        //totalBalance.setText(totalRent);


// invoice part
        invoiceList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        invoiceList.addAll(databaseQueryClass.getAllInvoicebyId(tenantId));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();


        //payments part
        paymentList = new ArrayList<>();
        //Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllInvoicebyId(refFlatId)));
        paymentList.addAll(databaseQueryClass.getAllPaymentsbyId(tenantId));
        //Log.d("InvoiceList : ==> ", String.valueOf(invoiceList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();
        //balancesList.add(new BalancesModel(0, invoiceList));
        // balancesList.add(new BalancesModel(1, paymentList));

        double totalInvoiceAmount = 0;

        for (InvoiceModelClass invoiceModelClass : invoiceList) {
            invoiceModelClass.setType(0);
            totalInvoiceAmount = totalInvoiceAmount + Double.parseDouble(invoiceModelClass.getRent());


        }

        double totalPaidAmount = 0;
        for (PaymentsModelClass paymentsModelClass : paymentList) {
            paymentsModelClass.setType(1);
            totalPaidAmount = totalPaidAmount + Double.parseDouble(paymentsModelClass.getAmount());

        }

        totalBalance.setText(String.valueOf(totalInvoiceAmount));
        balancePaid.setText(String.valueOf(totalPaidAmount));
        remainingBalance.setText(String.valueOf(totalInvoiceAmount - totalPaidAmount));

        balancesList.addAll(invoiceList);
        balancesList.addAll(paymentList);

        Collections.sort(balancesList, new Comparator<BalancesModel>() {
            @Override
            public int compare(BalancesModel balancesModel, BalancesModel t1) {
                return t1.getEntryDate().compareTo(balancesModel.getEntryDate());

            }

        });


        Set<String> dates = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        for (BalancesModel balancesModel
                : balancesList) {
            dates.add(sdf.format(balancesModel.getEntryDate()));
        }
        Map<String, List<BalancesModel>> balanceListByDate = new HashMap<String, List<BalancesModel>>();
        for (String date : dates) {
            List<BalancesModel> bmList = new ArrayList<>();
            for (BalancesModel balancesModel
                    : balancesList) {
                if (date.equalsIgnoreCase(sdf.format(balancesModel.getEntryDate()))) {
                    bmList.add(balancesModel);
                }
            }
            Collections.sort(bmList, new Comparator<BalancesModel>() {
                @Override
                public int compare(BalancesModel balancesModel, BalancesModel t1) {
                    return t1.getEntryDate().compareTo(balancesModel.getEntryDate());

                }

            });
            balanceListByDate.put(date, bmList);
        }
        balancesList = new ArrayList<>();
        for (Map.Entry<String, List<BalancesModel>> entry : balanceListByDate.entrySet()) {
            BalancesModel bmModel = new BalancesModel();
            bmModel.setType(2);
            bmModel.setTitle(entry.getKey());
            balancesList.add(bmModel);
            double totalInvoiceamt = 0;
            double totalPaidAmt = 0;
            for(BalancesModel mm : entry.getValue()){
                if(mm.getType() == 0){
                    totalInvoiceamt += Double.valueOf(((InvoiceModelClass)mm).getRent());
                }else{
                    totalPaidAmt += Double.valueOf(((PaymentsModelClass)mm).getAmount());
                }
            }
            bmModel.setTitleAmount(String.valueOf(totalInvoiceamt-totalPaidAmt));
            balancesList.addAll(entry.getValue());
        }

        Log.d("InvoiceListSize=== > ", String.valueOf(invoiceList.size()));
        Log.d("PaymentListSize ===> ", String.valueOf(paymentList.size()));

        Log.d("BalanceListSize ===>", String.valueOf(balancesList.size()));
        // balancesList = Arrays.asList(new BalancesModel(0, invoiceList), new BalancesModel(1, paymentList));
        // balancesList = Arrays.asList(new BalancesModel(1, paymentList));

        // balancesRecyclerView.setAdapter(new BalancesListRecyclerView(balancesList));


        //balance part
        // balancesList.add(new List<InvoiceModelClass> invoiceList );
        // balancesList.add(Collections.singleton(paymentList.toString()));


        balancesListRecyclerViewAdapter = new BalancesListRecyclerView(getContext(), balancesList);
        balancesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        balancesRecyclerView.setAdapter(balancesListRecyclerViewAdapter);

        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        String upiv = pref.getString("ownerUpiId","");

        String rendv = pref.getString("RentalDueday","One Month");
        String curv = pref.getString("currencyType","₹");

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
                currencyId.setText("$");
                currencyId2.setText("$");
                currencyId3.setText("$");

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
                currencyId.setText("£");
                currencyId2.setText("£");
                currencyId3.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");
                currencyId2.setText("Lek");
                currencyId3.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");
                currencyId2.setText("؋");
                currencyId3.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");
                currencyId2.setText("ƒ");
                currencyId3.setText("ƒ");


                break;
            case "AZN-₼":
                currencyId.setText("₼");
                currencyId2.setText("₼");
                currencyId3.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");
                currencyId2.setText("p.");
                currencyId3.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");
                currencyId2.setText("BZ$");
                currencyId3.setText("BZ$");

                break;
            case "BOB-$b":
                currencyId.setText("$b");
                currencyId2.setText("$b");
                currencyId3.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");
                currencyId2.setText("KM");
                currencyId3.setText("KM");

                break;
            case "BWP-P":
                currencyId.setText("P");
                currencyId2.setText("P");
                currencyId3.setText("P");

                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");
                currencyId2.setText("лв");
                currencyId3.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");
                currencyId2.setText("R$");
                currencyId3.setText("R$");

                break;
            case "KHR-៛":
                currencyId.setText("៛");
                currencyId2.setText("៛");
                currencyId3.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");
                currencyId2.setText("¥");
                currencyId3.setText("¥");

                break;
            case "CRC-₡":
                currencyId.setText("₡");
                currencyId2.setText("₡");
                currencyId3.setText("₡");

                break;
            case "HRK-kn":
                currencyId.setText("kn");
                currencyId2.setText("kn");
                currencyId3.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");
                currencyId2.setText("₱");
                currencyId3.setText("₱");

                break;
            case "CZK-Kč":
                currencyId.setText("Kč");
                currencyId2.setText("Kč");
                currencyId3.setText("Kč");

                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");
                currencyId2.setText("kr");
                currencyId3.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");
                currencyId2.setText("RD$");
                currencyId3.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");
                currencyId2.setText("€");
                currencyId3.setText("€");

                break;
            case "GEL-₾":
                currencyId.setText("₾");
                currencyId2.setText("₾");
                currencyId3.setText("₾");

                break;
            case "GHC-¢":
                currencyId.setText("¢");
                currencyId2.setText("¢");
                currencyId3.setText("¢");

                break;
            case "GTQ-Q":
                currencyId.setText("Q");
                currencyId2.setText("Q");
                currencyId3.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");
                currencyId2.setText("L");
                currencyId3.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");
                currencyId2.setText("Ft");
                currencyId3.setText("Ft");

                break;
            case "INR-₹":
                currencyId.setText("₹");
                currencyId2.setText("₹");
                currencyId3.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");
                currencyId2.setText("Rp");
                currencyId3.setText("Rp");

                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");
                currencyId2.setText("﷼");
                currencyId3.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");
                currencyId2.setText("₪");
                currencyId3.setText("₪");

                break;
            case "JMD-J$":
                currencyId.setText("J$");
                currencyId2.setText("J$");
                currencyId3.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");
                currencyId2.setText("₩");
                currencyId3.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");
                currencyId2.setText("₭");
                currencyId3.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");
                currencyId2.setText("Ls");
                currencyId3.setText("Ls");
                break;
            case "LTL-Lt":
                currencyId.setText("Lt");
                currencyId2.setText("Lt");
                currencyId3.setText("Lt");

                break;
            case "MKD-ден":
                currencyId.setText("ден");
                currencyId2.setText("ден");
                currencyId3.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");
                currencyId2.setText("RM");
                currencyId3.setText("RM");

                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");
                currencyId2.setText("₨");
                currencyId3.setText("₨");

                break;
            case "MNT-₮":
                currencyId.setText("₮");
                currencyId2.setText("₮");
                currencyId3.setText("₮");

                break;
            case "MZN-MT":
                currencyId.setText("MT");
                currencyId2.setText("MT");
                currencyId3.setText("MT");

                break;
            case "NIO-C$":
                currencyId.setText("C$");
                currencyId2.setText("C$");
                currencyId3.setText("C$");

                break;
            case "NGN-₦":
                currencyId.setText("₦");
                currencyId2.setText("₦");
                currencyId3.setText("₦");

                break;
            case "PAB-B/.":
                currencyId.setText("B/.");
                currencyId2.setText("B/.");
                currencyId3.setText("B/.");

                break;
            case "PYG-Gs":
                currencyId.setText("Gs");
                currencyId2.setText("Gs");
                currencyId3.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");
                currencyId2.setText("S/.");
                currencyId3.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");
                currencyId2.setText("zł");
                currencyId3.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");
                currencyId2.setText("lei");
                currencyId3.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");
                currencyId2.setText("₽");
                currencyId3.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");
                currencyId2.setText("Дин.");
                currencyId3.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");
                currencyId2.setText("S");
                currencyId3.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");
                currencyId2.setText("R");
                currencyId3.setText("R");

                break;
            case "CHF-CHF":
                currencyId.setText("CHF");
                currencyId2.setText("CHF");
                currencyId3.setText("CHF");

                break;
            case "TWD-NT$":
                currencyId.setText("NT$");
                currencyId2.setText("NT$");
                currencyId3.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");
                currencyId2.setText("฿");
                currencyId3.setText("฿");

                break;
            case "TTD-TT$":
                currencyId.setText("TT$");
                currencyId2.setText("TT$");
                currencyId3.setText("TT$");

                break;
            case "TRL-₺":
                currencyId.setText("₺");
                currencyId2.setText("₺");
                currencyId3.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");
                currencyId2.setText("₴");
                currencyId3.setText("₴");

                break;
            case "UYU-$U":
                currencyId.setText("$U");
                currencyId2.setText("$U");
                currencyId3.setText("$U");

                break;
            case "VEF-Bs":
                currencyId.setText("Bs");
                currencyId2.setText("Bs");
                currencyId3.setText("Bs");

                break;
            case "VND-₫":
                currencyId.setText("₫");
                currencyId2.setText("₫");
                currencyId3.setText("₫");

                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");
                currencyId2.setText("Z$");
                currencyId3.setText("Z$");
                break;

        }

        return view;
    }
}