package com.example.quick_fix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.quick_fix.databinding.ActivityEditListingBinding;
import com.example.quick_fix.databinding.ActivityViewListingBinding;
import com.example.quickfixapi.QuickFixApi;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditListingActivity extends AppCompatActivity {

    private ActivityEditListingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ListingObject listingObject = intent.getParcelableExtra("Listing");


        EditText editTextTitle = binding.contentScrolling.editTextTitle;
        EditText editTextDescription = binding.contentScrolling.editTextDescription;
        EditText editTextPostalCode = binding.contentScrolling.editTextPostalCode;
        EditText editTextFee = binding.contentScrolling.editTextFee;

        editTextTitle.setText(listingObject.getTitle());
        editTextDescription.setText(listingObject.getDescription());
        editTextPostalCode.setText((listingObject.getPostalCode()));
        editTextFee.setText((listingObject.getFee()));

        Button updateButton = binding.contentScrolling.buttonUpdateListing;
        updateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickFixApi api = new QuickFixApi(binding.getRoot().getContext()) {
                    @Override
                    public void onSuccess(String requestType, JSONObject response) {
                        finish();
                    }

                    @Override
                    public void onSuccess(String requestType, JSONArray response) {

                    }

                    @Override
                    public void onError(String requestType, VolleyError error) {

                    }
                };
                api.putListing(listingObject.getId(), editTextTitle.getText().toString(), editTextDescription.getText().toString(), editTextPostalCode.getText().toString(), editTextFee.getText().toString());
            }
        });
        Button deleteButton = binding.contentScrolling.buttonDeleteListing;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickFixApi api = new QuickFixApi(binding.getRoot().getContext()) {
                    @Override
                    public void onSuccess(String requestType, JSONObject response) {
                        finish();
                    }

                    @Override
                    public void onSuccess(String requestType, JSONArray response) {

                    }

                    @Override
                    public void onError(String requestType, VolleyError error) {

                    }
                };
                api.deleteListing(listingObject.getId());
            }
        });

    }
}