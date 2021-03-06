package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlatsListRecyclerAdapter extends RecyclerView.Adapter<FlatsListRecyclerAdapter.CustomViewHolder> {

    private Context context;
    private List<FlatsModelClass> flatsList;

    DatabaseQueryClass databaseQueryClass;
    TenantModelClass tenantModelClass;

    public FlatsListRecyclerAdapter(Context context, List<FlatsModelClass> flatsList) {
        this.context = context;
        this.flatsList = flatsList;
        databaseQueryClass = new DatabaseQueryClass(context);


    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flats_design_row_recycler_view, parent, false);
        return new CustomViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        final int itemPosition = position;
        //final PropertyModelClass property = new PropertyModelClass();
        final FlatsModelClass flats = flatsList.get(position);
        final long flatId = flats.getFlatId();
        holder.flatNo.setText(flats.getFlaNo());
        holder.floor.setText(flats.getFloor());
        holder.flatfacing.setText(flats.getFaltfacing());
        holder.noOfBedrooms.setText(flats.getNoofbedrooms());


        try {
            tenantModelClass = databaseQueryClass.getTenantIdByFlatId(flatId);
            String tenantName = tenantModelClass.getTenantName();
            holder.tenantInfoUpdate.setText(tenantName);
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(context, TenantDetails.class);
                //intent.putExtra(Config.COLUMN_PROPERTY_ID, property.getId());
                intent.putExtra(Config.COLUMN_FLATS_ID, flats.getFlatId());
                intent.putExtra(Config.COLUMN_FLATS_FLOOR, flats.getFloor());
                intent.putExtra(Config.COLUMN_FLATS_FLATNO, flats.getFlaNo());
                intent.putExtra(Config.COLUMN_FLATS_FLATFACING, flats.getFaltfacing());
                intent.putExtra(Config.COLUMN_FLATS_NOOFBEDROOMS, flats.getNoofbedrooms());

                context.startActivity(intent);

                 */
                //fragmentJump(flats);


                Intent intent = new Intent(context, TotalTenant.class);

                intent.putExtra(Config.COLUMN_FLATS_ID, flats.getFlatId());
                intent.putExtra(Config.COLUMN_FLATS_FLOOR, flats.getFloor());
                intent.putExtra(Config.COLUMN_FLATS_FLATNO, flats.getFlaNo());
                intent.putExtra(Config.COLUMN_FLATS_FLATFACING, flats.getFaltfacing());
                intent.putExtra(Config.COLUMN_FLATS_NOOFBEDROOMS, flats.getNoofbedrooms());

                context.startActivity(intent);


            }
        });


        //update/see details/ settings
        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display opion Menu
                final PopupMenu popupMenu = new PopupMenu(context, holder.optionMenu);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.menu_item_Edit:
                                //Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();

                                UpdateFlat updateFlat = UpdateFlat.newInstance(flats.getFlatId(), itemPosition, new FlatUpdateListener() {

                                    @Override
                                    public void onFlatInfoUpdated(FlatsModelClass flat, int position) {
                                        flatsList.set(position, flat);
                                        notifyDataSetChanged();
                                    }
                                });
                                Intent i = new Intent(context, updateFlat.getClass());
                                context.startActivity(i);
                                break;
                            case R.id.menu_item_delete:
                                //Toast.makeText(context, "settings", Toast.LENGTH_LONG).show();

                                FlatsModelClass mflat = flatsList.get(position);
                                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
                                boolean isDeleted = databaseQueryClass.deleteFlatById(mflat.getFlatId());

                                if (isDeleted) {
                                    flatsList.remove(mflat);
                                    notifyDataSetChanged();
                                    // ((SubjectListActivity) context).viewVisibility();
                                    ((PropertyDetails) context).viewVisibility();
                                    Toast.makeText(context, " deleted ", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(context, "Cannot settings!", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_item_seeDetails:
                                //Toast.makeText(context, "see details", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(context, TotalTenant.class);

                                intent.putExtra(Config.COLUMN_FLATS_ID, flats.getFlatId());
                                intent.putExtra(Config.COLUMN_FLATS_FLOOR, flats.getFloor());
                                intent.putExtra(Config.COLUMN_FLATS_FLATNO, flats.getFlaNo());
                                intent.putExtra(Config.COLUMN_FLATS_FLATFACING, flats.getFaltfacing());
                                intent.putExtra(Config.COLUMN_FLATS_NOOFBEDROOMS, flats.getNoofbedrooms());

                                context.startActivity(intent);

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        /*
        try {
            notifyItemChanged(position, holder.getAdapterPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }

         */

    }




    @Override
    public int getItemCount() {

        return flatsList.size();


    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        DatabaseQueryClass databaseQueryClass;

        TextView flatNo, floor, flatfacing, noOfBedrooms;
        TextView optionMenu, tenantInfoUpdate;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            flatNo = itemView.findViewById(R.id.flatNoTextView);
            floor = itemView.findViewById(R.id.floorTextView);
            flatfacing = itemView.findViewById(R.id.flatfacingTextView);
            noOfBedrooms = itemView.findViewById(R.id.noofbedroomsTextView);
            optionMenu = itemView.findViewById(R.id.textOption);
            tenantInfoUpdate = itemView.findViewById(R.id.AddTenantId);

        }

    }

}
