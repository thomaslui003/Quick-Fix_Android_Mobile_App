package com.example.quick_fix.ui.listings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.quick_fix.CreateListingActivity;
import com.example.quick_fix.ListingObject;
import com.example.quick_fix.ListingObjectsAdapter;
import com.example.quick_fix.Properties;
import com.example.quick_fix.R;
import com.example.quick_fix.RecyclerItemClickListener;
import com.example.quick_fix.ViewListingActivity;
import com.example.quick_fix.databinding.FragmentListingsBinding;
import com.example.quickfixapi.QuickFixApi;
import com.example.volleyservice.VolleyResponseHandler;
import com.example.volleyservice.VolleyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.android.volley.toolbox.Volley.newRequestQueue;

public class ListingsFragment extends Fragment {

    private ListingsViewModel listingsViewModel;
    private FragmentListingsBinding binding;
    private ListingObjectsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listingsViewModel =
                new ViewModelProvider(this).get(ListingsViewModel.class);

        binding = FragmentListingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textListings;
        listingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = Properties.getInstance().listingAdapter;
        FloatingActionButton fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(binding.getRoot().getContext(), CreateListingActivity.class);
                startActivity(intent);
            }

        });

        RecyclerView listview = (RecyclerView) getActivity().findViewById(R.id.listview);
        listview.setAdapter(adapter);
        loadListings(adapter);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));

        listview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), listview, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ViewListingActivity.class);
                intent.putExtra("Listing", (ListingObject) adapter.getItem(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void loadListings(ListingObjectsAdapter adapter) {
        ArrayList<ListingObject> newArrayList = new ArrayList<ListingObject>();
        QuickFixApi api = new QuickFixApi(getContext()) {
            @Override
            public void onSuccess(String requestType, JSONObject response) {
            }

            @Override
            public void onSuccess(String requestType, JSONArray response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON " + requestType + " " + response);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject responseObject = response.getJSONObject(i);
                        ListingObject item = new ListingObject("N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
                        if (responseObject.has("id")) {
                            item.setId(responseObject.getString("id"));
                        }
                        if (responseObject.has("providerId")) {
                            item.setProviderId(responseObject.getString("providerId"));
                        }
                        if (responseObject.has("providerUsername")) {
                            item.setProviderUsername(responseObject.getString("providerUsername"));
                        }
                        if (responseObject.has("title")) {
                            item.setTitle(responseObject.getString("title"));
                        }
                        if (responseObject.has("description")) {
                            item.setDescription(responseObject.getString("description"));
                        }
                        if (responseObject.has("fee")) {
                            item.setFee(responseObject.getString("fee"));
                        }
                        if (responseObject.has("postalCode")) {
                            item.setPostalCode(responseObject.getString("postalCode"));
                        }
                        newArrayList.add(item);
                    }
                    adapter.swap(newArrayList);
                    Log.i("newarraylist", newArrayList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String requestType, VolleyError error) {
            }
        };
        api.getListingsByUserId(Properties.getInstance().gettingDatabaseUserId());
    }
}