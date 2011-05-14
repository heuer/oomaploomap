/*
 * Copyright 2011 Lars Heuer (heuer[at]semagia.com). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.semagia.ooloo.ui;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import org.jdesktop.application.Application;

/**
 * Utility functions to create {@link JMenuBar}s and {@link JToolBar}s.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class UIUtils {

    private UIUtils() {
        // noop.
    }

    /**
     * Creates a menu from the provided action names {@code ---} indicates a separator.
     *
     * @param app The application.
     * @param name
     * @param actionNames Action names.
     * @return A menu.
     */
    public static JMenu menuFromActions(final Application app, final String name, 
                                            final String[] actionNames) {
        final ActionMap actionMap = app.getContext().getActionMap();
        final JMenu menu = new JMenu(name);
        for (String actionName: actionNames) {
            if (actionName.equals("---")) {
                menu.addSeparator();
                continue;
            }
            menu.add(actionMap.get(actionName));
        }
        return menu;
    }

    /**
     * Creates a toolbar from the provided action names {@code ---} indicates a separator.
     * 
     * @param app The application.
     * @param actionNames Action names.
     * @return A toolbar.
     */
    public static JToolBar toolbarFromActions(final Application app, final String[] actionNames) {
        final ActionMap actionMap = app.getContext().getActionMap();
        final JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        for (String actionName: actionNames) {
            if (actionName.equals("---")) {
                toolBar.addSeparator();
                continue;
            }
            JButton button = new JButton();
            button.setAction(actionMap.get(actionName));
            button.setFocusable(false);
            button.setText(null);
            toolBar.add(button);
        }
        return toolBar;
    }

}
