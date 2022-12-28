package com.github.arcoda.CashBot;

import com.github.arcoda.CashBot.Listener.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class CashBot {
    public static Logger getLogger;
    public static String mineToken;
    public static String mineHash;
    public static String rconPass;
    private static JDA api;
    public static void main(String[] arguments) throws LoginException, InterruptedException
    {
        getLogger = LoggerFactory.getLogger("CashBot");
        //Arguments are excepted to be: discord_token minestrator_token minestrator_hashsupport rcon_password
        if(arguments.length != 4) {
            getLogger.error("Invalid number of arguments!");
            return;
        }
        mineToken = arguments[1];
        mineHash = arguments[2];
        rconPass = arguments[3];
        api = JDABuilder.createDefault(arguments[0]).build();
        api.awaitReady();
        for (Guild server : api.getGuildCache()) {
            server.updateCommands().addCommands(
                    new CommandData("start", "Start the minecraft server"),
                    new CommandData("stop", "Stop the minecraft server"),
                    new CommandData("restart", "Restart the minecraft server"),
                    new CommandData("kill", "Kill the minecraft server (POTENTIAL DATA LOSS!)"),
                    new CommandData("execute", "Execute a command")
                            .addOption(OptionType.STRING, "command", "Command to be sent to server (WITHOUT \"/\")", true),
                    new CommandData("info", "Refresh the info embed").setDefaultEnabled(false)
            ).queue();
        }
        api.addEventListener(new SlashCommand());
    }
    public static JDA getApi() {
        return api;
    }
}
