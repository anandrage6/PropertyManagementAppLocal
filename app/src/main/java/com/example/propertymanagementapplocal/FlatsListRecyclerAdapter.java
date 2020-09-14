package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlatsListRecyclerAdapter extends RecyclerView.Adapter<FlatsListRecyclerAdapter.CustomViewHolder> {
    private Context context;

    private List<FlatsModelClass> flatsList;

    public FlatsListRecyclerAdapter(Context context, List<FlatsModelClass> flatsList) {
        this.context = context;
        this.flatsList = flatsList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flats_design_row_recycler_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int listPosition = position;
        final FlatsModelClass flats = flatsList.get(position);
        final long flatId = flats.getFlatId();
        holder.flatNo.setText(flats.getFlaNo());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TenantDetails.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return flatsList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView flatNo;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            flatNo = itemView.findViewById(R.id.flatNoTextView);
        }
    }
}
