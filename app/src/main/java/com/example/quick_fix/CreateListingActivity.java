package com.example.quick_fix;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.example.quickfixapi.QuickFixApi;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quick_fix.databinding.ActivityCreateListingBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CreateListingActivity extends AppCompatActivity {

    private ActivityCreateListingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = binding.contentScrolling.buttonCreateListing;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickFixApi api = new QuickFixApi(binding.getRoot().getContext()) {
                    @Override
                    public void onSuccess(String requestType, JSONObject response) {
                        finish();
                    }

                    @Override
                    public void onSuccess(String requestType, JSONArray response) {
                        finish();
                    }

                    @Override
                    public void onError(String requestType, VolleyError error) {
                        error.printStackTrace();
                    }
                };

                String editTextTitle =((TextView) findViewById(R.id.editTextTitle)).getText().toString();
                String editTextDescription = ((TextView) findViewById(R.id.editTextDescription)).getText().toString();
                String editTextPostalCode = ((TextView) findViewById(R.id.editTextPostalCode)).getText().toString();
                String editTextFee = ((TextView) findViewById(R.id.editTextFee)).getText().toString();


                if (!(editTextTitle.isEmpty() || editTextDescription.isEmpty() || editTextPostalCode.isEmpty() || editTextFee.isEmpty())) {
                    api.postListing(Properties.getInstance().gettingDatabaseUserId() , editTextTitle , editTextDescription , editTextPostalCode , editTextFee );
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Please fill all required fields.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}