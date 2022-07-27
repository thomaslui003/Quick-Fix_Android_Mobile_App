package com.example.quick_fix.ui.listing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quick_fix.R;
import com.example.quick_fix.ViewListingActivity;
import com.example.quick_fix.databinding.FragmentListingBinding;

import java.util.ArrayList;

public class ListingFragment extends Fragment {

    private ListingViewModel dashboardViewModel;
    private FragmentListingBinding binding;
    ListView listview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(ListingViewModel.class);

        binding = FragmentListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listview = (ListView) root.findViewById(R.id.listview);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Listing 1");
        arrayList.add("Listing 2");
        arrayList.add("Listing 3");
        arrayList.add("Listing 4");
        arrayList.add("Listing 5");
        arrayList.add("Listing 6");
        arrayList.add("Listing 7");
        arrayList.add("Listing 8");
        arrayList.add("Listing 9");
        arrayList.add("Listing 10");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ViewListingActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}