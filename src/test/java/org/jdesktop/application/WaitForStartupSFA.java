/*
* Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
* subject to license terms.
*/

package org.jdesktop.application;

import javax.swing.*;

/**
 * Identical to WaitForStartupApplication.
 * <p/>
 * Support for launching a SingleFrameApplication from a non-EDT thread and
 * waiting until its startup method has finished running on the EDT.
 */
public class WaitForStartupSFA extends SingleFrameApplication {
    private boolean started = false;

    /**
     * Unblock the launchAndWait() method.
     */
    protected void startup() {
        started = true;
    }

    boolean isStarted() {
        return started;
    }

    /**
     * Launch the specified subclsas of WaitForStartupApplication and block
     * (wait) until it's startup() method has run.
     */
    public static void launchAndWait(Class<? extends WaitForStartupSFA> applicationClass) {
        Launcher.getInstance().launch(applicationClass, new String[]{});
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
