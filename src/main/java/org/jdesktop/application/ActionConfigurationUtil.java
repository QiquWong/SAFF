package org.jdesktop.application;

import javax.swing.*;
import javax.swing.Action;

/**
 * Gratiously donated by Escenic.
 * Original author: Yngvar So&ring;rensen
 *
 * @author <a href="mailto:erlend@hamnaberg.net">Erlend Hamnaberg</a>
 * @version $Revision: $
 */
class ActionConfigurationUtil {

    private ActionConfigurationUtil() {
    }

    /**
     * Configures an {@link Action} the same way as described by {@link org.jdesktop.application.ApplicationAction#ApplicationAction(org.jdesktop.application.ApplicationActionMap,org.jdesktop.application.ResourceMap,String,java.lang.reflect.Method,String,String,org.jdesktop.application.Task.BlockingScope) ApplicationAction}.
     * This method exists so that you are not forced to extend {@link org.jdesktop.application.ApplicationAction ApplicationAction} or use {@link ProxyAction @Action} annotations to get
     * automatic configuration of actions.
     * <p/>
     * Unlike {@link org.jdesktop.application.ApplicationAction}, the {@link Action#NAME} will be <code>null</code> unless <code><em>basename</em>.Action.text</code> is specified (It doesn't default to <em>basename</em>).
     * <p/>
     * Supported resource map values and how they are converted to Action properties:
     * <p/>
     * <table>
     * <tr><th>ResourceMap key</th><th>Action value</th></tr>
     * <tr><td><em>baseName</em>.Action.text</td><td>{@link Action#NAME}, {@link Action#MNEMONIC_KEY}, {@link Action#DISPLAYED_MNEMONIC_INDEX_KEY}</td></tr>
     * <tr><td><em>baseName</em>.Action.mnemonic</td><td>{@link Action#MNEMONIC_KEY}, {@link Action#DISPLAYED_MNEMONIC_INDEX_KEY}  </td></tr>
     * <tr><td><em>baseName</em>.Action.accelerator</td><td>{@link Action#ACCELERATOR_KEY}</td></tr>
     * <tr><td><em>baseName</em>.Action.icon</td><td>{@link Action#SMALL_ICON}, {@link Action#LARGE_ICON_KEY}</td></tr>
     * <tr><td><em>baseName</em>.Action.smallIcon</td><td>{@link Action#SMALL_ICON}</td></tr>
     * <tr><td><em>baseName</em>.Action.largeIcon</td><td>{@link Action#LARGE_ICON_KEY}</td></tr>
     * <tr><td><em>baseName</em>.Action.shortDescription</td><td>{@link Action#SHORT_DESCRIPTION}</td></tr>
     * <tr><td><em>baseName</em>.Action.longDescription</td><td>{@link Action#LONG_DESCRIPTION}</td></tr>
     * <tr><td><em>baseName</em>.Action.command</td><td>{@link Action#ACTION_COMMAND_KEY}</td></tr>
     * </table>
     *
     * @param action      target action. May not be {@code null}.
     * @param baseName    base name used for resource map lookups. May not be {@code null}.
     * @param resourceMap resource map used to look up values. May not be {@code null}.
     * @throws IllegalArgumentException if either parameter is {@code null}.
     * @see org.jdesktop.application.ApplicationAction#ApplicationAction(org.jdesktop.application.ApplicationActionMap,org.jdesktop.application.ResourceMap,String,java.lang.reflect.Method,String,String,org.jdesktop.application.Task.BlockingScope) ApplicationAction
     */
    public static void configureAction(final Action action, final String baseName, final ResourceMap resourceMap) {
        boolean iconOrNameSpecified = false;  // true if Action's icon/name properties set

        // Action.text => Action.NAME,MNEMONIC_KEY,DISPLAYED_MNEMONIC_INDEX_KEY
        String text = resourceMap.getString(baseName + ".Action.text");
        if (text != null) {
            MnemonicText.configure(action, text);
            iconOrNameSpecified = true;
        }
        // Action.mnemonic => Action.MNEMONIC_KEY
        Integer mnemonic = resourceMap.getKeyCode(baseName + ".Action.mnemonic");
        if (mnemonic != null) {
            action.putValue(Action.MNEMONIC_KEY, mnemonic);
        }
        // Action.mnemonic => Action.DISPLAYED_MNEMONIC_INDEX_KEY
        Integer index = resourceMap.getInteger(baseName + ".Action.displayedMnemonicIndex");
        if (index != null) {
            action.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, index);
        }
        // Action.accelerator => Action.ACCELERATOR_KEY
        KeyStroke key = resourceMap.getKeyStroke(baseName + ".Action.accelerator");
        if (key != null) {
            action.putValue(Action.ACCELERATOR_KEY, key);
        }
        // Action.icon => Action.SMALL_ICON,LARGE_ICON_KEY
        Icon icon = resourceMap.getIcon(baseName + ".Action.icon");
        if (icon != null) {
            action.putValue(Action.SMALL_ICON, icon);
            action.putValue(Action.LARGE_ICON_KEY, icon);
            iconOrNameSpecified = true;
        }
        // Action.smallIcon => Action.SMALL_ICON
        Icon smallIcon = resourceMap.getIcon(baseName + ".Action.smallIcon");
        if (smallIcon != null) {
            action.putValue(Action.SMALL_ICON, smallIcon);
            iconOrNameSpecified = true;
        }
        // Action.largeIcon => Action.LARGE_ICON
        Icon largeIcon = resourceMap.getIcon(baseName + ".Action.largeIcon");
        if (largeIcon != null) {
            action.putValue(Action.LARGE_ICON_KEY, largeIcon);
            iconOrNameSpecified = true;
        }
        // Action.shortDescription => Action.SHORT_DESCRIPTION
        action.putValue(Action.SHORT_DESCRIPTION,
                resourceMap.getString(baseName + ".Action.shortDescription"));
        // Action.longDescription => Action.LONG_DESCRIPTION
        action.putValue(Action.LONG_DESCRIPTION,
                resourceMap.getString(baseName + ".Action.longDescription"));
        // Action.command => Action.ACTION_COMMAND_KEY
        action.putValue(Action.ACTION_COMMAND_KEY,
                resourceMap.getString(baseName + ".Action.command"));
        // If no visual was defined for this Action, i.e. no text
        // and no icon, then we default Action.NAME
        if (!iconOrNameSpecified) {
            action.putValue(Action.NAME, baseName);
        }
    }
}
