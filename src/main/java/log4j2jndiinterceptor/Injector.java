package log4j2jndiinterceptor;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.lookup.JndiLookup;

@Log4j2
public class Injector {

    public static void inject(ClassLoader appCl) throws Exception {
        ByteBuddyAgent.install();
        ByteBuddy buddy = new ByteBuddy();
        DynamicType.Unloaded<JndiLookup> type = buddy.redefine(JndiLookup.class)
                .method(ElementMatchers.named("lookup").and(ElementMatchers.takesArguments(LogEvent.class, String.class)))
                .intercept(FixedValue.nullValue())
                .make();
        log.info("[Log4j2JndiInterceptor] make " + type.getTypeDescription());
        type.load(JndiLookup.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        log.info("[Log4j2JndiInterceptor] test result => ${jndi:ldap://xxx.xxx.com}");
    }
}
