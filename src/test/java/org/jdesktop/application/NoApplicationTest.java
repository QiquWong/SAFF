/*
* Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
* subject to license terms.
*/

package org.jdesktop.application;

import junit.framework.TestCase;

import javax.swing.*;

/**
 * Check that Application.getInstance() cannot be used even in
 * situtations where an Application hasn't actually been launched.
 * <p/>
 * This test depends on resources/Basic.properties
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class NoApplicationTest extends TestCase {
    public void testGetInstance() {
        Application app = Application.getInstance();
        assertNull("Application.getInstance()", app);
    }

}
