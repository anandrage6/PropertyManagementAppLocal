package com.example.propertymanagementapplocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.CustomViewHolder> {
    private Context context;
    private List<DocumentModelClass> DocList;
    private DatabaseQueryClass databaseQueryClass;

    //constructor

    public DocumentAdapter(Context context, List<DocumentModelClass> docmentlist) {
        this.context = context;
        this.DocList = docmentlist;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.document_designrow_recyclerview, parent, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final DocumentModelClass doc = DocList.get(itemPosition);

        final long id = doc.getId();
        final String imageUrl = doc.getDoc();
        holder.txtdoc.setVisibility(View.VISIBLE);
        Picasso.get().load(imageUrl).into(holder.txtdoc);

    }

    @Override
    public int getItemCount() {
        return DocList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView txtdoc;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdoc = itemView.findViewById(R.id.Documentimg);


        }
    }
}
