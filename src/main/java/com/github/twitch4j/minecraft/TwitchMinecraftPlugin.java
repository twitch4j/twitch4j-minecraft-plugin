package com.github.twitch4j.minecraft;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.ITwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class TwitchMinecraftPlugin extends JavaPlugin {

    private ITwitchClient client;

    @Override
    public void onEnable() {
        // Get the latest config after saving the default if missing
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();

        // Build credential when possible
        String token = config.getString("oauth_token");
        OAuth2Credential credential = StringUtils.isNotBlank(token) ? new OAuth2Credential("twitch", token) : null;

        // Build TwitchClient
        client = TwitchClientBuilder.builder()
            .withClientId(config.getString("client_id"))
            .withClientSecret(config.getString("client_secret"))
            .withEnableChat(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .withDefaultAuthToken(credential)
            .build();

        // Join the twitch chats of these channels and enable stream/follow events
        List<String> channels = config.getStringList("channels");
        if (!channels.isEmpty()) {
            channels.forEach(name -> client.getChat().joinChannel(name));
            client.getClientHelper().enableStreamEventListener(channels);
            client.getClientHelper().enableFollowEventListener(channels);
        }

        // Register event listeners
        client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEventHandler(this));
    }

    @Override
    public void onDisable() {
        if (client != null) {
            client.getEventManager().close();
            client.close();
            client = null;
        }
    }

    public ITwitchClient getTwitchClient() {
        return this.client;
    }

}
