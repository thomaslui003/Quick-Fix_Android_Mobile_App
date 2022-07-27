package com.example.quick_fix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_fix.databinding.ActivityViewListingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.view.View.GONE;

public class ViewListingActivity extends AppCompatActivity {

    private ActivityViewListingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        ListingObject listingObject = intent.getParcelableExtra("Listing");

        TextView textViewTitle = binding.textViewTitle;
        TextView textViewProvider = binding.textViewProvider;
        TextView textViewPostalCode = binding.textViewPostalCode;
        TextView textViewDescription = binding.textViewDescription;

        textViewTitle.setText(listingObject.getTitle() + " - $" + listingObject.getFee());
        textViewProvider.setText("with " + listingObject.getProviderUsername());
        textViewPostalCode.setText("Location: " +listingObject.getPostalCode());
        textViewDescription.setText(listingObject.getDescription());

        FloatingActionButton fabEdit = binding.floatingActionButton;
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(binding.getRoot().getContext(), EditListingActivity.class);
                intent.putExtra("Listing", listingObject);
                startActivity(intent);
                finish();
            }

        });

        if (!listingObject.getProviderId().equals(Properties.getInstance().gettingDatabaseUserId())) {
            fabEdit.setVisibility(GONE);
        }

    }

    public void goBack(View view) {
        finish();
    }
}
