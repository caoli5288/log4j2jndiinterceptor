package log4j2jndiinterceptor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Log4j2JndiInterceptor extends JavaPlugin {

    @Override
    public void onLoad() {
        // load libraries
        try {
            Class.forName("net.bytebuddy.ByteBuddy");
        } catch (ClassNotFoundException e) {
            MavenLibs.of("net.bytebuddy", "byte-buddy", "1.12.3").load();
            MavenLibs.of("net.bytebuddy", "byte-buddy-agent", "1.12.3").load();
        }
        try {
            Injector.inject(Bukkit.class.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
