/*
* Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
* subject to license terms.
*/

package examples;

import org.jdesktop.application.ProxyAction;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A demo that shows the use of SingleFrameApplication secondary windows.
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class SingleFrameExample6 extends SingleFrameApplication {
    private final List<Window> windows =
            new ArrayList<Window>(Collections.nCopies(3, (Window) null));

    // Lazily create an element of the windows List and return it
    private Window getWindow(int n, Class<? extends Window> windowClass) {
        Window window = windows.get(n);
        if (window == null) {
            try {
                window = windowClass.newInstance();
            }
            catch (Exception e) {
                throw new Error("HCTB", e);
            }
            JLabel label = new JLabel();
            JButton button = new JButton();
            button.setAction(getAction("hideWindow"));
            window.setName("window" + n);
            label.setName("label" + n);
            button.setName("button" + n);
            window.add(label, BorderLayout.CENTER);
            window.add(button, BorderLayout.SOUTH);
            windows.set(n, window);
        }
        return window;
    }

    @ProxyAction
    public void hideWindow(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component source = (Component) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(source);
            if (window != null) {
                window.setVisible(false);
            }
        }
    }

    @ProxyAction
    public void showWindow0() {
        show((JFrame) getWindow(0, JFrame.class));
    }

    @ProxyAction
    public void showWindow1() {
        show((JDialog) getWindow(1, JDialog.class));
    }

    @ProxyAction
    public void showWindow2() {
        show((JDialog) getWindow(2, JDialog.class));
    }

    @ProxyAction
    public void disposeSecondaryWindows() {
        for (int i = 0; i < windows.size(); i++) {
            Window window = windows.get(i);
            if (window != null) {
                windows.set(i, null);
                window.dispose();
            }
        }
    }

    private javax.swing.Action getAction(String actionName) {
        return getContext().getActionMap().get(actionName);
    }

    private JMenu createMenu(String menuName, String[] actionNames) {
        JMenu menu = new JMenu();
        menu.setName(menuName);
        for (String actionName : actionNames) {
            if (actionName.equals("---")) {
                menu.add(new JSeparator());
            } else {
                JMenuItem menuItem = new JMenuItem();
                menuItem.setAction(getAction(actionName));
                menuItem.setIcon(null);
                menu.add(menuItem);
            }
        }
        return menu;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] viewMenuActionNames = {
                "showWindow0",
                "showWindow1",
                "showWindow2",
                "disposeSecondaryWindows",
                "---",
                "quit"
        };
        menuBar.add(createMenu("viewMenu", viewMenuActionNames));
        return menuBar;
    }

    @Override
    protected void startup() {
        getMainFrame().setJMenuBar(createMenuBar());
        JLabel label = new JLabel();
        label.setName("mainLabel");
        show(label);
    }

    public static void main(String[] args) {
        Launcher.getInstance().launch(SingleFrameExample6.class, args);
    }
}
