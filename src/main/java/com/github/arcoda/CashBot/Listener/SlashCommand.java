package com.github.arcoda.CashBot.Listener;

import com.github.arcoda.CashBot.CashBot;
import com.github.arcoda.CashBot.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;

public class SlashCommand extends ListenerAdapter {
    private OkHttpClient client = new OkHttpClient();
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        //Detect if user is too stinky to use the bot
        if(new Random().nextInt(100) == 99) {
            event.reply("too stinky to execute");
            return;
        }
        switch(event.getName()) {
            case "start":
            case "stop":
            case "restart":
            case "kill":
                power(event.getName(), event);
                break;
            case "execute":
                execute(event);
                break;
            case "info":
                info(event);
                break;
            default:
                event.reply("Unrecognized command?!");
                break;
        }
    }
    private void power(String action, SlashCommandEvent event) {
        event.deferReply().queue();
        InteractionHook hook = event.getHook();
        //Create request object
        Request request = new Request.Builder()
                .addHeader("Authorization", CashBot.mineToken)
                .url("https://rest.minestrator.com/api/v1/server/action")
                .post(new FormBody.Builder().add("hashsupport", CashBot.mineHash).add("action", action).build())
                .build();
        //Send request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hook.sendMessage("Communication error, couldn't reach Minestrator servers :(").queue();
                CashBot.getLogger.error("Got onFailure in power() in SlashCommand");
            }
            @Override
            public void onResponse(Call call, final Response response) {
                if (!response.isSuccessful()) {
                    hook.sendMessage("Got an error reply from Minestrator :(").queue();
                    CashBot.getLogger.error("Got http code " + response + " from Minestrator");
                } else {
                    hook.sendMessage("Done.").queue();
                }
                response.close();
            }
        });
    }
    private void execute(SlashCommandEvent event) {
        event.deferReply().queue();
        InteractionHook hook = event.getHook();
        try {
            String response = new Rcon("54.37.112.163", 25571, CashBot.rconPass.getBytes())
                    .command(event.getOption("command").getAsString());
            if (!response.isEmpty()) {
                hook.sendMessage("Answer from server :\n```"+response+"```").queue();
            } else {
                hook.sendMessage("Command was sent successfully but no reply from server.").queue();
            }
        } catch (IOException e) {
            hook.sendMessage("Couldn't connect to the server, is it completely started?").queue();
        } catch (AuthenticationException e) {
            hook.sendMessage("Got an anthentication error! Ask Arcoda for more infos.").queue();
        }
    }

    private void info(SlashCommandEvent event) {
        CashBot.getApi().getTextChannelById("903231457152098305").editMessageEmbedsById("984828207419256862", new MessageEmbed[]{Embed.getInfo()}).queue();
        event.reply("Done.").queue();
    }

}
