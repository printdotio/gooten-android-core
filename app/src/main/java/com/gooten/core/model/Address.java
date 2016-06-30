package com.gooten.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.gooten.core.types.JSONSerializable;

public class Address implements Parcelable, JSONSerializable {

    private String firstName;
    private String lastName;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String countryCode;
    private String postalCode;
    private boolean isBusinessAddress;
    private String phone;
    private String email;

    public Address() {
        // NOP
    }

    public Address(String firstName, String lastName, String line1, String line2, String city, String state, String countryCode, String postalCode, boolean isBusinessAddress, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.isBusinessAddress = isBusinessAddress;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isBusinessAddress() {
        return isBusinessAddress;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setBusinessAddress(boolean isBusinessAddress) {
        this.isBusinessAddress = isBusinessAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (isBusinessAddress != address.isBusinessAddress) return false;
        if (firstName != null ? !firstName.equals(address.firstName) : address.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(address.lastName) : address.lastName != null)
            return false;
        if (line1 != null ? !line1.equals(address.line1) : address.line1 != null) return false;
        if (line2 != null ? !line2.equals(address.line2) : address.line2 != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        if (countryCode != null ? !countryCode.equals(address.countryCode) : address.countryCode != null)
            return false;
        if (postalCode != null ? !postalCode.equals(address.postalCode) : address.postalCode != null)
            return false;
        if (phone != null ? !phone.equals(address.phone) : address.phone != null) return false;
        if (email != null ? !email.equals(address.email) : address.email != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (line1 != null ? line1.hashCode() : 0);
        result = 31 * result + (line2 != null ? line2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (isBusinessAddress ? 1 : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    // ==================================================================================
    // Methods to make class JSONSerializable
    // ==================================================================================

    @Override
    public void fromJSON(JSONObject json) {
        firstName = json.optString("FirstName");
        lastName = json.optString("LastName");
        line1 = json.optString("Line1");
        line2 = json.optString("Line2");
        city = json.optString("City");
        state = json.optString("State");
        countryCode = json.optString("CountryCode");
        postalCode = json.optString("PostalCode");
        isBusinessAddress = json.optBoolean("IsBusinessAddress");
        phone = json.optString("Phone");
        email = json.optString("Email");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("FirstName", firstName);
            jsonObject.put("LastName", lastName);
            jsonObject.put("Line1", line1);
            jsonObject.put("Line2", line2);
            jsonObject.put("City", city);
            jsonObject.put("State", state);
            jsonObject.put("CountryCode", countryCode);
            jsonObject.put("PostalCode", postalCode);
            jsonObject.put("IsBusinessAddress", isBusinessAddress);
            jsonObject.put("Phone", phone);
            jsonObject.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // ==================================================================================
    // Methods to make class Parcelable
    // ==================================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(firstName);
        pc.writeString(lastName);
        pc.writeString(line1);
        pc.writeString(line2);
        pc.writeString(city);
        pc.writeString(state);
        pc.writeString(countryCode);
        pc.writeString(postalCode);
        pc.writeByte((byte) (isBusinessAddress ? 1 : 0));
        pc.writeString(phone);
        pc.writeString(email);
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {

        public Address createFromParcel(Parcel pc) {
            return new Address(pc);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }

    };

    public Address(Parcel pc) {
        firstName = pc.readString();
        lastName = pc.readString();
        line1 = pc.readString();
        line2 = pc.readString();
        city = pc.readString();
        state = pc.readString();
        countryCode = pc.readString();
        postalCode = pc.readString();
        isBusinessAddress = (pc.readByte() == 1);
        phone = pc.readString();
        email = pc.readString();
    }

}
