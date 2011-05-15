/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.semagia.ooloo.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * This class was taken from org.apache.openjpa.swing
 * 
 * C.f. <http://svn.apache.org/repos/asf/openjpa/tools/trunk/openjpa-tools/src/main/java/org/apache/openjpa/swing/ErrorDialog.java>
 */
/**
 * A dialog to display runtime error.
 * 
 * @author Pinaki Poddar
 */
@SuppressWarnings("serial")
public final class ErrorDialog extends JDialog {
    private static List<String> filters = Arrays.asList(
            "java.awt.", 
            "javax.swing.", 
            "sun.reflect.",
            "java.util.concurrent.");
    private static String NEWLINE = "<br>";
    private static String INDENT  = "&nbsp;&nbsp;";
    private final  JCheckBox filter;
    private final  JLabel _header;
    private JComponent   _main;
    private JEditorPane  _stacktrace;
    private JScrollPane  _stacktraceBar;
    private final Throwable _error;
    
    /**
     * Creates a modal dialog to display the given exception message.
     * 
     * @param t the exception to display
     */
    public ErrorDialog(Throwable t) {
        this(null, null, t);
    }

    public ErrorDialog(JComponent owner, Throwable t) {
        this(owner, null, t);
    }
    
    /**
     * Creates a modal dialog to display the given exception message.
     * 
     * @param owner if non-null, then the dialog is positioned (centered) w.r.t. this component
     * @param t the exception to display
     */
    public ErrorDialog(JComponent owner, Icon icon, Throwable t) {
        super();
        t.printStackTrace();
        setTitle(t.getClass().getName());
        setModal(true);
        if (icon != null && icon instanceof ImageIcon) 
            setIconImage(((ImageIcon)icon).getImage());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _error = t;
        _main  = new JPanel(true);
        _main.setLayout(new BorderLayout());
        
        _header = new JLabel();
        _header.setText(formatMessage(_error, false, true));

        final JButton showDetails = new JButton("Show StackTrace >>");
        final JButton copyToClipBoard = new JButton("Copy Stack Trace");
        copyToClipBoard.setEnabled(false);
        filter = new JCheckBox("Filter stack traces");
        filter.setSelected(false);
        String stacktrace = generateStackTrace(_error);
        _stacktrace  = new JEditorPane();
        _stacktrace.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        _stacktrace.setContentType("text/html");
        _stacktrace.setText(stacktrace);
        _stacktraceBar = new JScrollPane(_stacktrace, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        _stacktraceBar.setVisible(false);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(showDetails);
        buttonPanel.add(filter);
        buttonPanel.add(copyToClipBoard);
        buttonPanel.add(Box.createHorizontalGlue());
        
        JPanel messagePanel = new JPanel(true);
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(_header, BorderLayout.CENTER);
        messagePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        _main.add(messagePanel, BorderLayout.NORTH);
        _main.add(_stacktraceBar, BorderLayout.CENTER);
        _main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        _header.setBackground(_main.getBackground());
        getContentPane().add(_main);

        pack();
        setLocationRelativeTo(owner);
        
        
        showDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_stacktraceBar.isVisible()) {
                    _stacktraceBar.setVisible(false);
                    copyToClipBoard.setEnabled(false);
                } else {
                    _stacktraceBar.setVisible(true);
                    copyToClipBoard.setEnabled(true);
                }
                showDetails.setText(_stacktraceBar.isVisible() ? "<< Hide StackTrace" : "Show StackTrace >>");
                _main.revalidate();
                _main.repaint();
                pack();
            }
        });
      
        filter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String buffer = generateStackTrace(_error);
                _stacktrace.setText(buffer);
            }
        });
        
        copyToClipBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection data = new StringSelection(_stacktrace.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
            }
        });
        
    }
    
    /**
     * Creates a non-editable widget to display the detailed stack trace.
     */
    String generateStackTrace(Throwable t) {
        StringBuilder buffer = generateStackTrace(t, null);
        return buffer.toString();
    }
    
    /**
     * Recursively print the stack trace on the given buffer.
     */    
    private StringBuilder generateStackTrace(Throwable t, StringBuilder buffer) {
        if (buffer == null) {
            buffer = new StringBuilder();
            buffer.append(t.getClass().getName() + NEWLINE);
        } else {
            buffer.append(formatMessage(t, true, false));
        }
        buffer.append(formatStackTraces(t.getStackTrace()));
        Throwable cause = t.getCause();
        if (cause != null && cause != t) {
            generateStackTrace(cause, buffer);
        }
        return buffer;
    }
    
    StringBuilder formatStackTraces(StackTraceElement[] traces) {
        StringBuilder error = new StringBuilder();
        for (StackTraceElement e : traces) {
            if (!filter.isSelected() || !isSuppressed(e.getClassName())) {
                String str = e.toString();
                error.append(INDENT).append(str).append(NEWLINE);
            }
        }
        return error;
    }

        
    /**
     * Create a HTML formatted string with the given exception type and the message broken
     * at word boundaries at line of given width.
     */ 
    String formatMessage(Throwable t, boolean appendClass, boolean addHTML) {
        List<String> lines = wrapText((appendClass ? t.getClass().getName()+": " : "") + t.getMessage(), 80);
        StringBuilder buf = new StringBuilder(addHTML ? "<HTML>" : "");
        for (String s : lines) {
            buf.append(s + NEWLINE);
        }
        return buf.append(addHTML ? "</HTML>" : "").toString();
    }
    
    public static List<String> wrapText(String s, int width) {
        List<String> lines = new ArrayList<String>();
        char[] chars = s.toCharArray();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (line.length() < width) {
                line.append(chars[i]);
            } else if (Character.isSpaceChar(chars[i])) {
                lines.add(line.toString());
                line = new StringBuilder();
            } else {
                line.append(chars[i]);
            }
        }
        if (line.length() > 0)
            lines.add(line.toString());
        return lines;
    }
    
    /**
     * Affirms if the error messages from the given class name is to be suppressed.
     */
    private boolean isSuppressed(String className) {
        for (String s : filters) {
            if (className.startsWith(s))
                return true;
        }
        return false;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String m1 = "This is test error with very very very very very long line of error message that " 
            + " should not be in a single line. Another message string that should be split across word." +
            "The quick brown fox jumpled over the lazy dog";
        String m2 = "This is another test error with very long line of error message that " 
            + " should not be in a single line";
        Throwable nested = new NumberFormatException(m2);
        Throwable top = new IllegalArgumentException(m1, nested);
        new ErrorDialog(top).setVisible(true);
    }

}
