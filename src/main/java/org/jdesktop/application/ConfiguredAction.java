package org.jdesktop.application;

import javax.swing.*;

/**
 * @author <a href="mailto:erlend@hamnaberg.net">Erlend Hamnaberg</a>
 * @version $Revision: $
 */
public abstract class ConfiguredAction extends AbstractAction {
    protected ConfiguredAction(String basename, ResourceMap resourceMap) {
        ActionConfigurationUtil.configureAction(this, basename, resourceMap);
    }
}
