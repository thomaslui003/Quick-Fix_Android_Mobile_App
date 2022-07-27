package com.example.quick_fix.ui.listings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ListingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListingsViewModel() {

                mText = new MutableLiveData<>();
                mText.setValue("Available Listings");
            }

            public LiveData<String> getText() {
                return mText;
            }
        }