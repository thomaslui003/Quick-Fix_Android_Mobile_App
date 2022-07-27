package com.example.quick_fix.ui.listing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListingViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("Available Listings");
    }

    public LiveData<String> getText() {
        return mText;
    }
}