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
     * Sets the text and mnemonic of an action as specified by the provided {@link TextWithMnemonic}.
     *
     * @param pAction           the action to be configured. May not be {@code null}.
     * @param pTextWithMnemonic the text with mnemonic. May not be {@code null}.
     */
    public static void configureAction(Action pAction, TextWithMnemonic pTextWithMnemonic) {
        pAction.putValue(Action.NAME, pTextWithMnemonic.getTextWithoutMnemonic());
        if (pTextWithMnemonic.getMnemonic() != null) {
            pAction.putValue(Action.MNEMONIC_KEY, pTextWithMnemonic.getMnemonic());
            pAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, pTextWithMnemonic.getIndex());
        }
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
     * @param pAction      target action. May not be {@code null}.
     * @param pBaseName    base name used for resource map lookups. May not be {@code null}.
     * @param pResourceMap resource map used to look up values. May not be {@code null}.
     * @throws IllegalArgumentException if either parameter is {@code null}.
     * @see org.jdesktop.application.ApplicationAction#ApplicationAction(org.jdesktop.application.ApplicationActionMap,org.jdesktop.application.ResourceMap,String,java.lang.reflect.Method,String,String,org.jdesktop.application.Task.BlockingScope) ApplicationAction
     */
    public static void configureAction(final Action pAction, final String pBaseName, final ResourceMap pResourceMap) {

        // this code was copied then modified. See ApplicationAction.initActionProperties(ResourceMap, String)
        // Action.text => Action.NAME,MNEMONIC_KEY,DISPLAYED_MNEMONIC_INDEX_KEY
        String text = pResourceMap.getString(pBaseName + ".Action.text");
        if (text != null) {
            TextWithMnemonic textWithMnemonic = new TextWithMnemonic(text);
            pAction.putValue(Action.NAME, textWithMnemonic.getTextWithoutMnemonic());
            if (textWithMnemonic.getMnemonic() != null) {
                pAction.putValue(Action.MNEMONIC_KEY, textWithMnemonic.getMnemonic());
                pAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, textWithMnemonic.getIndex());
            }
        }
        // Action.mnemonic => Action.MNEMONIC_KEY
        Integer mnemonic = pResourceMap.getKeyCode(pBaseName + ".Action.mnemonic");
        if (mnemonic != null) {
            pAction.putValue(Action.MNEMONIC_KEY, mnemonic);
        }
        // Action.mnemonic => Action.DISPLAYED_MNEMONIC_INDEX_KEY
        Integer index = pResourceMap.getInteger(pBaseName + ".Action.displayedMnemonicIndex");
        if (index != null) {
            pAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, index);
        }
        // Action.accelerator => Action.ACCELERATOR_KEY
        KeyStroke key = pResourceMap.getKeyStroke(pBaseName + ".Action.accelerator");
        if (key != null) {
            pAction.putValue(Action.ACCELERATOR_KEY, key);
        }
        // Action.icon => Action.SMALL_ICON,LARGE_ICON_KEY
        Icon icon = pResourceMap.getIcon(pBaseName + ".Action.icon");
        if (icon != null) {
            pAction.putValue(Action.SMALL_ICON, icon);
            pAction.putValue(Action.LARGE_ICON_KEY, icon);
        }
        // Action.smallIcon => Action.SMALL_ICON
        Icon smallIcon = pResourceMap.getIcon(pBaseName + ".Action.smallIcon");
        if (smallIcon != null) {
            pAction.putValue(Action.SMALL_ICON, smallIcon);
        }
        // Action.largeIcon => Action.LARGE_ICON_KEY
        Icon largeIcon = pResourceMap.getIcon(pBaseName + ".Action.largeIcon");
        if (largeIcon != null) {
            pAction.putValue(Action.LARGE_ICON_KEY, largeIcon);
        }
        // Action.shortDescription => Action.SHORT_DESCRIPTION
        pAction.putValue(Action.SHORT_DESCRIPTION, pResourceMap.getString(pBaseName + ".Action.shortDescription"));
        // Action.longDescription => Action.LONG_DESCRIPTION
        pAction.putValue(Action.LONG_DESCRIPTION, pResourceMap.getString(pBaseName + ".Action.longDescription"));
        // Action.command => Action.ACTION_COMMAND_KEY
        pAction.putValue(Action.ACTION_COMMAND_KEY, pResourceMap.getString(pBaseName + ".Action.command"));
    }
}
