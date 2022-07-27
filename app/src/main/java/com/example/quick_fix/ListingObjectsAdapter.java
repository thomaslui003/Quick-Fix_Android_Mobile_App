package com.example.quick_fix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ListingObjectsAdapter extends RecyclerView.Adapter<ListingObjectsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView userNameTextView;
        public TextView titleTextView;
        public TextView descTextView;
        public TextView feeTextView;
        public TextView postalTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descTextView = (TextView) itemView.findViewById(R.id.description);
            feeTextView = (TextView) itemView.findViewById(R.id.fee);
            postalTextView = (TextView) itemView.findViewById(R.id.postal_code);
        }
    }

    // Store a member variable for the contacts
    private List<ListingObject> listingObjects;

    // Pass in the contact array into the constructor
    public ListingObjectsAdapter(List<ListingObject> contacts) {
        listingObjects = contacts;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ListingObjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.content_search_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ListingObjectsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        ListingObject listingObject = listingObjects.get(position);

        // Set item views based on your views and data model
        TextView userView = holder.userNameTextView;
        userView.setText(listingObject.getProviderUsername());
        TextView titleView = holder.titleTextView;
        titleView.setText(listingObject.getTitle());
        TextView descView = holder.descTextView;
        descView.setText(listingObject.getDescription());
        TextView feeView = holder.feeTextView;
        feeView.setText("$" + listingObject.getFee());
        TextView postalView = holder.postalTextView;
        postalView.setText("Postal Code: " + listingObject.getPostalCode());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listingObjects.size();
    }

    public void swap(ArrayList<ListingObject> datas)
    {
        listingObjects.clear();
        listingObjects.addAll(datas);
        notifyDataSetChanged();
    }

    public void clear()
    {
        listingObjects.clear();
    }

    public ListingObject getItem(int position) {
        return listingObjects.get(position);
    }
}
