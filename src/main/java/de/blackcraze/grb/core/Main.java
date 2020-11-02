package de.blackcraze.grb.core;

import java.util.Objects;

import de.blackcraze.grb.listener.MessageListener;
import de.blackcraze.grb.listener.ReadyListener;
import de.blackcraze.grb.util.StandingDataInitializer;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class Main {

    public static void main(String[] args) {

        for (String env_var : BotConfig.REQUIRED_ENV_VARS) {
            if (Objects.isNull(System.getenv(env_var))) {
                System.err.printf("Missing environment variable: \"%s\"%n", env_var);
                System.exit(1);
            }
        }

        initData();
        initDiscord();
    }

    private static void initData() {
        new StandingDataInitializer().initStockTypes();
    }

    private static void initDiscord() {
        JDABuilder builder = JDABuilder.createLight(BotConfig.DISCORD_TOKEN);
        try {
            builder.setAutoReconnect(true);
            builder.setStatus(OnlineStatus.ONLINE);
            builder.addEventListeners(new ReadyListener(), new MessageListener());
            builder.build();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            builder.setStatus(OnlineStatus.OFFLINE);
        }
    }

}
