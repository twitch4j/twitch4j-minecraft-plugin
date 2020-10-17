package com.github.twitch4j.minecraft;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.CheerEvent;
import com.github.twitch4j.chat.events.channel.FollowEvent;
import com.github.twitch4j.chat.events.channel.GiftSubscriptionsEvent;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.github.twitch4j.helix.domain.Stream;

public class TwitchEventHandler {

    private final TwitchMinecraftPlugin plugin;

    public TwitchEventHandler(TwitchMinecraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventSubscriber
    public void onStreamUp(ChannelGoLiveEvent event) {
        Stream stream = event.getStream();
        broadcast(String.format("[Twitch] %s has gone live for %d viewers: %s", stream.getUserName(), stream.getViewerCount(), stream.getTitle()));
    }

    @EventSubscriber
    public void onStreamDown(ChannelGoOfflineEvent event) {
        broadcast(String.format("[Twitch] %s has stopped streaming.", event.getChannel().getName()));
    }

    @EventSubscriber
    public void onFollow(FollowEvent event) {
        broadcast(String.format("[Twitch] %s just followed %s!", event.getUser().getName(), event.getChannel().getName()));
    }

    @EventSubscriber
    public void onCheer(CheerEvent event) {
        if (event.getBits() >= 500)
            broadcast(String.format("[Twitch] %s just cheered %d bits for %s!", event.getUser().getName(), event.getBits(), event.getChannel().getName()));
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent event) {
        if (!event.getGifted())
            broadcast(String.format("[Twitch] %s just subscribed to %s for %d months", event.getUser().getName(), event.getChannel().getName(), event.getMonths()));
    }

    @EventSubscriber
    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
        broadcast(String.format("[Twitch] Thank you %s for gifting %d subs to %s", event.getUser().getName(), event.getCount(), event.getChannel().getName()));
    }

    private void broadcast(String message) {
        this.plugin.getServer().broadcastMessage(message);
    }

}
