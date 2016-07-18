package com.bitmovin.bitcodin.api.notification;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.com]
 * on 7/18/16
 */
public class Subscription
{
    @Expose
    public String id;
    @Expose
    public Event event;
    @Expose
    public String url;
}
