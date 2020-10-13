package com.vegazsdev.bobobot.commands;

import com.vegazsdev.bobobot.TelegramBot;
import com.vegazsdev.bobobot.core.Command;
import com.vegazsdev.bobobot.db.PrefObj;
import com.vegazsdev.bobobot.utils.FileTools;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class sfsetup extends Command {

    public sfsetup() {
        super("sfs", "Setup the sourceforge auth");
    }

    public static String getSfConf(String prop) {
        try {
            Properties getProps = new Properties();
            getProps.load(new FileInputStream("configs/sf-creds.config"));
            return getProps.getProperty(prop);
        } catch (Exception e) {
            // e
        }
        return null;
    }

    @Override
    public void botReply(Update update, TelegramBot bot, PrefObj prefs) {
        String msg = update.getMessage().getText();
        if (FileTools.checkIfFolderExists("configs/sf-creds.config")) {
            mkSfConf();
            bot.sendMessage("Check your config folder", update);
        } else if (msg.contains(" -d")) {
            FileTools.deleteFile("configs/sf-creds.config");
            mkSfConf();
            bot.sendMessage("Sourceforge settings have been deleted, check the folder again", update);
        } else {
            bot.sendMessage("Unable to create a new configuration file, as it already exists. If intentional, use \"-d\" in the arguments", update);
        }
    }

    public boolean mkSfConf() {
        try {
            FileTools.createFolder("configs");
            Properties saveProps = new Properties();
            saveProps.setProperty("bot-sf-user", "put your sf username");
            saveProps.setProperty("bot-sf-host", "put your sf host");
            saveProps.setProperty("bot-sf-pass", "put your sf pass");
            saveProps.setProperty("bot-sf-proj", "put your sf project name");
            saveProps.setProperty("bot-send-announcement", "false");
            saveProps.setProperty("bot-announcement-id", "none");
            saveProps.store(new FileOutputStream("configs/sf-creds.config"), "Config file");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
