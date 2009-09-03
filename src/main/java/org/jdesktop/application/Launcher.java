package org.jdesktop.application;

import javax.swing.*;
import java.util.ServiceLoader;
import java.lang.reflect.Constructor;

public class Launcher {
    private static Launcher INSTANCE;
    static {
        ServiceLoader<Launcher> loader = 
                ServiceLoader.load(Launcher.class);
        for (Launcher launcher : loader) {
            INSTANCE = launcher;
            break;
        }
        if (INSTANCE == null) {
            INSTANCE = new Launcher();
        }
    }
    
    public void launch(Class<? extends Application> appClass) {
        launch(appClass, new String[0]);
    }

    public void launch(final Class<? extends Application> appClass,
                       final String[] args) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                Constructor<? extends Application> ctor = appClass.getDeclaredConstructor();
                if (!ctor.isAccessible()) {
                    try {
                        ctor.setAccessible(true);
                    } catch (SecurityException ignore) {
                    }
                }
                Application app = ctor.newInstance();
                app.launch(args);
            } else {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        launch(appClass, args);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Launcher getInstance() {
        return INSTANCE;
    }
}
