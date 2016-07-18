package com.bitmovin.bitcodin.api.notification;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.com]
 * on 7/18/16
 */
public class SubscriptionConfig
{
    @Expose
    public String eventId;
    @Expose
    public String url;

    public SubscriptionConfig()
    {

    }

    public SubscriptionConfig(String eventId, String url)
    {
        this.eventId = eventId;
        this.url = url;
    }
}
