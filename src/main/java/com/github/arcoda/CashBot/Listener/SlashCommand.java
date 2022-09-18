package com.github.arcoda.CashBot.Listener;

import com.github.arcoda.CashBot.CashBot;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

public class SlashCommand extends ListenerAdapter {
    private OkHttpClient client = new OkHttpClient();
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
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
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    hook.sendMessage("Got an error reply from Minestrator :(").queue();
                    CashBot.getLogger.error("Got http code " + response + " from Minestrator");
                } else {
                    hook.sendMessage("Done.").queue();
                }
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
            throw new RuntimeException(e);
        }
    }

}
