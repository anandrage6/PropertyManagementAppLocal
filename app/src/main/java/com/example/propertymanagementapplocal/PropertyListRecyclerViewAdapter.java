package com.example.propertymanagementapplocal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertyListRecyclerViewAdapter extends RecyclerView.Adapter<PropertyListRecyclerViewAdapter.CustomViewHolder> {

    private Context context;
    private List<PropertyModelClass> propertyList;
    private DatabaseQueryClass databaseQueryClass;

    //constructor

    public PropertyListRecyclerViewAdapter(Context context, List<PropertyModelClass> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.apartment_design_row_recycler_view, parent, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        final int itemPosition = position;
        final PropertyModelClass property = propertyList.get(position);

        final long id = property.getId();
        holder.propertyNameTv.setText(property.getPropertyName());
        holder.propertyTvInfo.setText(property.getPropertyName());
        holder.ownerNameTv.setText(property.getOwnerName());
        holder.addressTv.setText(property.getAddress()+", "+property.getCity()+" "+property.getState()+"-"+property.getZipCode());
       // holder.imageView.setImageURI(Uri.parse(property.getImage()));
        final String imageurl = property.getImage();
        holder.imageView.setVisibility(View.VISIBLE);
        Picasso.get().load(imageurl).into(holder.imageView);

        //item click function
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(context, PropertyDetails.class);
                intent.putExtra(Config.COLUMN_PROPERTY_ID, property.getId());
                intent.putExtra(Config.COLUMN_PROPERTY_PROPERTYTYPE, property.getPropertyType());
                intent.putExtra(Config.COLUMN_PROPERTY_PROPERTYNAME, property.getPropertyName());
                intent.putExtra(Config.COLUMN_PROPERTY_OWNERNAME, property.getOwnerName());
                intent.putExtra(Config.COLUMN_PROPERTY_IMAGE, property.getImage());
                intent.putExtra(Config.COLUMN_PROPERTY_ADDRESS, property.getAddress());
                intent.putExtra(Config.COLUMN_PROPERTY_CITY, property.getCity());
                intent.putExtra(Config.COLUMN_PROPERTY_STATE, property.getState());
                intent.putExtra(Config.COLUMN_PROPERTY_ZIPCODE, property.getZipCode());
                intent.putExtra(Config.COLUMN_PROPERTY_DESCRIPTION, property.getDescription());
                intent.putExtra(Config.COLUMN_PROPERTY_PROPERTYTYPE, property.getPropertyType());
                //intent.putExtra("itemPosition", itemPosition);

                //intent.putExtra("RECORD_ID", id );

                context.startActivity(intent);
            }
        });

        //update property / delete property / view property
        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display opion Menu
                PopupMenu popupMenu = new PopupMenu(context, holder.optionMenu);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem Item) {
                        switch (Item.getItemId()) {
                            case R.id.menu_item_Edit:
                                //Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();

                                UpdateProperty updateProperty = UpdateProperty.newInstance(property.getId(), itemPosition, new PropertyUpdateListener() {
                                    @Override
                                    public void onPropertyInfoUpdated(PropertyModelClass property, int position) {
                                        propertyList.set(position, property);
                                        notifyDataSetChanged();
                                    }
                                });
                                Intent i = new Intent(context, updateProperty.getClass());
                                context.startActivity(i);
                                //updateProperty.show(((MainActivity) context).getSupportFragmentManager(), Config.UPDATE_PROPERTY);
                                    break;
                            case R.id.menu_item_delete:
                                //Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();

                                PropertyModelClass mproperty = propertyList.get(position);
                                long count = databaseQueryClass.deletePropertyById(mproperty.getId());

                                if(count>0){
                                    propertyList.remove(position);
                                    notifyDataSetChanged();
                                    //((MainActivity) context).viewVisibility();
                                    Toast.makeText(context, "Property deleted successfully", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(context, "Property not deleted. Something wrong!", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.menu_item_seeDetails:
                                Intent intent = new Intent(context, PropertyDetails.class);
                                intent.putExtra(Config.COLUMN_PROPERTY_ID, property.getId());
                                intent.putExtra(Config.COLUMN_PROPERTY_PROPERTYNAME, property.getPropertyName());
                                intent.putExtra(Config.COLUMN_PROPERTY_OWNERNAME, property.getOwnerName());
                                intent.putExtra(Config.COLUMN_PROPERTY_IMAGE, property.getImage());
                                intent.putExtra(Config.COLUMN_PROPERTY_ADDRESS, property.getAddress());
                                intent.putExtra(Config.COLUMN_PROPERTY_CITY, property.getCity());
                                intent.putExtra(Config.COLUMN_PROPERTY_STATE, property.getState());
                                intent.putExtra(Config.COLUMN_PROPERTY_ZIPCODE, property.getZipCode());
                                intent.putExtra(Config.COLUMN_PROPERTY_DESCRIPTION, property.getDescription());
                                intent.putExtra(Config.COLUMN_PROPERTY_PROPERTYTYPE, property.getPropertyType());
                                //intent.putExtra("RECORD_ID", id );


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

    }




    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView propertyNameTv,ownerNameTv, addressTv;
        TextView propertyTvInfo;
        ImageView imageView;
       TextView optionMenu;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyNameTv = itemView.findViewById(R.id.propertyNameTextView);
            ownerNameTv = itemView.findViewById(R.id.ownerNameTextView);
            addressTv = itemView.findViewById(R.id.addressTextView);
            imageView = itemView.findViewById(R.id.imageView);
            optionMenu = itemView.findViewById(R.id.textOption);
            propertyTvInfo = itemView.findViewById(R.id.propertyTvInfo);

        }
    }
}
