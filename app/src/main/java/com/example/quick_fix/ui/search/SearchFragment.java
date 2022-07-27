package com.example.quick_fix.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.quick_fix.Properties;
import com.example.quick_fix.R;
import com.example.quick_fix.ListingObject;
import com.example.quick_fix.ListingObjectsAdapter;
import com.example.quick_fix.RecyclerItemClickListener;
import com.example.quick_fix.ViewListingActivity;
import com.example.quick_fix.databinding.FragmentSearchBinding;
import com.example.quickfixapi.QuickFixApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.android.volley.toolbox.Volley.newRequestQueue;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    SearchView mySearchView;
    RecyclerView myList;
    private ListingObjectsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adapter = Properties.getInstance().searchAdapter;
        adapter.clear();
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mySearchView = (SearchView) binding.searchView.findViewById(R.id.search_View);
        myList = (RecyclerView) binding.searchListing.findViewById(R.id.search_listing);


        myList.setAdapter(adapter);

        myList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), myList , new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ViewListingActivity.class);
                intent.putExtra("Listing", (ListingObject) adapter.getItem(position));
                adapter.clear();
                adapter.notifyDataSetChanged();
                mySearchView.clearFocus();
                mySearchView.setQuery("", false);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(view.getContext(), ViewListingActivity.class);
////                intent.putExtra("Listing", (ListingObject) parent.getItemAtPosition(position));
//                startActivity(intent);
//            }


        myList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(root, adapter, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.clear();
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void search(View root, ListingObjectsAdapter adapter, String query) {
        ArrayList newArrayList = new ArrayList();

        QuickFixApi api = new QuickFixApi(root.getContext()) {
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
                        ListingObject item = new ListingObject("N/A", "N/A","N/A", "N/A", "N/A", "N/A" ,"N/A");
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String requestType, VolleyError error) {
                error.printStackTrace();
            }
        };
        api.searchListings(query);
    }
}