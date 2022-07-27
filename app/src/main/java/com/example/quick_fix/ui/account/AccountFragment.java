package com.example.quick_fix.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.example.quick_fix.CreateListingActivity;
import com.example.quick_fix.Properties;
import com.example.quick_fix.R;
import com.example.quick_fix.databinding.FragmentAccountBinding;
import com.example.volleyservice.VolleyResponseHandler;
import com.example.volleyservice.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;


public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private FragmentAccountBinding binding;
    private Properties properties;
    private EditText mText;
    private TextView sfuIdTextView;
    private TextView sfuEmailTextView;
    private Button editingNameButton;

    Activity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAccountName;
        accountViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        properties = Properties.getInstance();
        String name = properties.gettingSfuUserId();
        String databaseUserId = properties.gettingDatabaseUserId();
        String email = properties.gettingSfuUserId();


        sfuIdTextView = root.findViewById(R.id.textView2);
        sfuEmailTextView = root.findViewById(R.id.sfuEmail);
        sfuIdTextView.setText("User Database ID: "+ databaseUserId);
        sfuEmailTextView.setText(email+"@sfu.ca");

        //Log.i(TAG, "the dataID is: "+ databaseUserId);
//        mText = root.findViewById(R.id.text_account_name);
//        mText.setText(name);
        accountViewModel.settingText("SFU User ID: "+ name);

//        editingNameButton = root.findViewById(R.id.editName);
//        editingNameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(binding.getRoot().getContext(), AccountUpdate.class);
//                startActivity(intent);
//
//            }
//        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}