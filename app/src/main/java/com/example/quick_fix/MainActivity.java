package com.example.quick_fix;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quick_fix.databinding.ActivityMainBinding;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_listings, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        checkForDynamicLinks();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForDynamicLinks();
    }

    private void checkForDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        String loginAPIResponseStatus = "";
                        String loginAPIResponse = "";
                        if (deepLink != null) {
                            Log.i(TAG, "Here's the deeplink URL " + deepLink);
                            loginAPIResponseStatus = deepLink.getQueryParameter("loginAPIResponse") != null ? "success" : "failure";
                            loginAPIResponse = deepLink.getQueryParameter("loginAPIResponse");
                            Log.i(TAG, "loginAPIResponseStatus " + loginAPIResponseStatus);
                            Log.i(TAG, "loginAPIResponse " + loginAPIResponse);

                            properties = Properties.getInstance();
                            try {
                                JSONObject responseObject = new JSONObject(loginAPIResponse);
                                properties.settingDatabaseUserId(responseObject.getString("id"));
                                properties.settingSfuUserName(responseObject.getString("username"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }

}