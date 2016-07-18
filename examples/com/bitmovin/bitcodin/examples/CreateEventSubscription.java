package com.bitmovin.bitcodin.examples;

import com.bitmovin.bitcodin.api.BitcodinApi;
import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.notification.Event;
import com.bitmovin.bitcodin.api.notification.Subscription;
import com.bitmovin.bitcodin.api.notification.SubscriptionConfig;

/**
 * Created by
 * David Moser [david.moser@bitmovin.com]
 * on 7/18/16
 */
public class CreateEventSubscription
{
    public static void main(String[] args) throws InterruptedException, BitcodinApiException
    {
        /* Create BitcodinApi */
        String apiKey = "YOUR API KEY";
        BitcodinApi bitApi = new BitcodinApi(apiKey);

        /* Get available Events */
        Event[] events = bitApi.getAvailableEvents();

        /* (debug) list events and choose appropriate event id*/
        for(Event event: events)
        {
            System.out.println("eventId: " + event.id + "\ndescription: " + event.description + "\n--------------------------------------------------\n");
        }

        /* (debug) List already defined subscriptions */
        Subscription[] alreadyDefinedSubscriptions = bitApi.listSubscriptions();
        for (Subscription subscription: alreadyDefinedSubscriptions)
        {
            System.out.println("subscriptionId: " + subscription.id + "\ncallback url: " + subscription.url + "\n--------------------------------------------------\n");
        }


        /* Create subscription config */
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig(events[0].id, "http://url.to/your/callback");
        Subscription subscription = bitApi.createSubscription(subscriptionConfig);

        System.out.println("Newly generated subscription id: " + subscription.id);

        /* Delete subscription */
        bitApi.deleteSubscription(subscription.id);
        System.out.println("Successfully deleted subscription with id " + subscription.id);

    }
}
