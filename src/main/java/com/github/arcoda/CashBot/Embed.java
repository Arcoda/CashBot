package com.github.arcoda.CashBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Embed {
    public static final MessageEmbed getInfo() {
        return new EmbedBuilder()
                .setTitle("Welcome to Cash's MC Server <a:CDST_RainbowSheep:886360678552707073>")
                .setColor(Color.BLUE)
                .setDescription(
                        "You can find here all essential information you might need on the server. Make sure to have a look at <#903231128171847690>.\nServer is running 1.19.3 with bedrock support. If you play on Java, we support clients from 1.7.2 to 1.19.3. 1.16+ is recommended."
                )
                .addField(
                        "Joining",
                        "Server is running 24/7, the server only restarts in the middle of the night (4am BST) and sadly occasionally when the server crashes.\nYou can join either through Java or Bedrock.\nFor Java -> `play.cashcraftserver.com` Most convenient one with our server-dedicated domain.\nFor bedrock -> IP: `51.210.137.117` PORT: `25565` Less convenient but you are sure it will work.",
                        false
                )
                .addField(
                        "Permissions",
                        "When you first join the server, you can get a virtual tour of the server trough a video. You will be guided by the Community Manager towards being set to Creative Mode and being able to claim a plot.\nDon't even think of abusing your permissions, we have daily backups and any attempt at griefing would be useless.",
                        false
                )
                .addField(
                        "Property",
                        "As you get to know the server you'll most likely want a house and maybe start a business. Please note that land on all villages is managed by Governors.\nCheck the <#1001819329622450236> channel for the village that interest you to check availability and ask for a plot.",
                        false
                )
                .addField(
                        "Railway",
                        "A very important part of Cashcraft is the railway. The main operator CNRN operate multiples projects such as the Metro, HS1 and HS2. All three projects use different technologies but all thrive to make useful and good looking railway. If interested, check the CNRN sub server in <#925109823165591617>.",
                        false
                )
                .addField(
                        "Wiki",
                        "The official wiki created by SquadFam9000 is a very valuable source of informations, don't hesitate to contribute if informations are missing or not up to date.\nhttps://cashcraft.fandom.com/wiki/CashCraft_Wiki",
                        false
                )
                .addField(
                        "Server Specs",
                        "Flexi\nCores : 2C up to 4Ghz +1 FlexCore ™\nRam: 16G (Can be increased depending on server activity)\nDisk: 80G\nDatabase: 8G MySQL 5.7\nHost: Minestrator.com (It's a french host)",
                        false
                )
                .setFooter("Last updated 28/12/2022")
                .build();
    }
}
