/*
* Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
* subject to license terms.
*/

package examples;

import org.jdesktop.application.ProxyAction;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Launcher;

import javax.swing.*;
import java.awt.*;

/**
 * A simple demonstration of the {@code @Action(selectedProperty)}
 * annotation parameter.  The {@code selectedProperty} parameter names
 * a bound boolean property whose value is kept in sync with the
 * value of the corresponding ApplicationAction's {@code selectedProperty},
 * which in turn mirrors the value of JToggleButtons that have
 * been configured with that ApplicationAction.
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class SelectedPropertyExample extends SingleFrameApplication {
    private boolean selected = false;
    JCheckBox checkBox = null;
    JButton button = null;
    JRadioButton radioButton = null;
    JTextArea textArea = null;

    @Override
    protected void startup() {
        ActionMap actionMap = getContext().getActionMap();
        radioButton = new JRadioButton(actionMap.get("toggleAction"));
        checkBox = new JCheckBox(actionMap.get("toggleAction"));
        button = new JButton(actionMap.get("buttonAction"));
        textArea = new JTextArea();
        textArea.setName("textArea");
        radioButton.setName("radioButton");
        JPanel controlPanel = new JPanel();
        controlPanel.add(radioButton);
        controlPanel.add(checkBox);
        controlPanel.add(button);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        show(mainPanel);
    }

    @ProxyAction
    public void buttonAction() {
        setSelected(!isSelected());
    }

    @ProxyAction(selectedProperty = "selected")
    public void toggleAction() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        firePropertyChange("selected", oldValue, this.selected);
        ApplicationAction cba = (ApplicationAction) checkBox.getAction();
        String msg =
                String.format("%s.setSelected(%s)\n", getClass().getName(), this.selected) +
                        String.format("checkBox.getAction().isSelected() %s\n", cba.isSelected()) +
                        String.format("checkBox.isSelected() %s\n", checkBox.isSelected()) +
                        String.format("radioButton.isSelected() %s\n", radioButton.isSelected());
        textArea.append(msg + "\n");
    }

    public static void main(String[] args) {
        Launcher.getInstance().launch(SelectedPropertyExample.class, args);
    }
}
