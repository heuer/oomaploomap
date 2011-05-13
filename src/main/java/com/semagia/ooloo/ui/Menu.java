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
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.jdesktop.application.Application;

/**
 * Utility functions to create {@link JMenuBar}s.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public class Menu {

    private Menu() {
        // noop.
    }

    public static JMenu fromActions(final Application app, final String name, 
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

}
