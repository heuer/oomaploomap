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
import javax.swing.JToolBar;

import org.jdesktop.application.Application;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class ToolBar {

    private ToolBar() {
        super();
    }

    public static JToolBar fromActions(final Application app, final String[] actionNames) {
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
