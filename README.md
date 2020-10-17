# Twitch4J - Minecraft Server Plugin Template

Support:

[![Discord Server](https://discordapp.com/api/guilds/143001431388061696/embed.png?style=banner2)](https://discord.gg/FQ5vgW3)
[![Twitch API Server](https://discordapp.com/api/guilds/325552783787032576/embed.png?style=banner2)](https://discord.gg/8NXaEyV)

--------

## A quick note:
This Bukkit plugin is part of the [Twitch4J API](https://github.com/twitch4j/twitch4j) project.

This code can easily be adapted for a different minecraft version and will also work on other platforms like (Paper)Spigot.

## Twitch Developer Applications
To obtain a `client_id` and `client_secret` to populate in `config.yml`, you can create an app [here](https://dev.twitch.tv/console/apps/create).

Alternatively, you can provide `oauth_token`, which can be generated [here](https://www.twitchtokengenerator.com/) if you do not wish to host the authentication process yourself.

At least one of these must be provided to interact with the Helix API, which is used to track follows and stream state.

To read chat, no token is necessary, but in order to send messages to chat, an `oauth_token` with the `chat:edit` scope *is*.
