package com.example.quick_fix;

import android.os.Parcel;
import android.os.Parcelable;

public class ListingObject implements Parcelable {

    private String id;
    private String providerId;
    private String providerUsername;

    private String title;
    private String description;
    private String fee;
    private String postalCode;

    public ListingObject(String id, String providerId, String providerUsername, String title, String description, String fee, String postalCode) {
        this.id = id;
        this.providerId = providerId;
        this.providerUsername = providerUsername;
        this.title = title;
        this.description = description;
        this.fee = fee;
        this.postalCode = postalCode;
    }

    public ListingObject(Parcel source) {
        this.id = source.readString();
        this.providerId = source.readString();
        this.providerUsername = source.readString();
        this.title = source.readString();
        this.description = source.readString();
        this.fee = source.readString();
        this.postalCode = source.readString();
    }

    public static final Creator<ListingObject> CREATOR = new Creator<ListingObject>() {
        @Override
        public ListingObject createFromParcel(Parcel in) {
            return new ListingObject(in);
        }

        @Override
        public ListingObject[] newArray(int size) {
            return new ListingObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    public String getFee() {
        return fee;
    }

    public String getDescription() {
        return description;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(providerId);
        dest.writeString(providerUsername);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(fee);
        dest.writeString(postalCode);
    }
}
