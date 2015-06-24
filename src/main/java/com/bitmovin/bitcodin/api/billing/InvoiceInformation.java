package com.bitmovin.bitcodin.api.billing;

import com.google.gson.annotations.Expose;

public class InvoiceInformation {
    @Expose
    public String firstName;
    @Expose
    public String lastName;
    @Expose
    public String address;
    @Expose
    public int postalCode;
    @Expose
    public String city;
    @Expose
    public String country;
    @Expose
    public String vatNumber;
}
