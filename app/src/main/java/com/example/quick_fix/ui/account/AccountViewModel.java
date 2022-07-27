package com.example.quick_fix.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void settingText(String a){
        this.mText.setValue(a);
    }
}