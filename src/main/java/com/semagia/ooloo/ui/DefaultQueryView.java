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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.View;

import com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource;
import com.semagia.ooloo.query.IResult;
import com.semagia.ooloo.query.Query;
import com.semagia.ooloo.query.QueryLanguage;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
final class DefaultQueryView extends View implements IQueryView {

    private static final TableModel _NOOP_TABLE_MODEL = new DefaultTableModel();

    private final ITopicMapSource _tmSrc;
    private Query _query;
    private TextEditorPane _queryEditor;
    private JTable _results;
    private final JProgressBar _progressBar;
    private static boolean _NICE_FONT_AVAILABLE = false;
    private static final Font _NICE_FONT =  new Font("DejaVu Sans Mono", Font.PLAIN, 15);

    static {
        for (String name: GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if ("DejaVu Sans Mono".equals(name)) {
                _NICE_FONT_AVAILABLE = true;
                break;
            }
        }
    }

    /**
     * 
     *
     */
    public DefaultQueryView(final Application app, final ITopicMapSource tmSrc) {
        super(app);
        _progressBar = new JProgressBar();
        _progressBar.setBorderPainted(false);
        _tmSrc = tmSrc;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.View#getComponent()
     */
    @Override
    public JComponent getComponent() {
        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        _queryEditor = new TextEditorPane(TextEditorPane.INSERT_MODE);
        final RTextScrollPane textPane = new RTextScrollPane(_queryEditor);
        textPane.setPreferredSize(new Dimension(600, 350));
        _results = new JTable();
        splitter.setLeftComponent(textPane);
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(_createToolBar(), BorderLayout.NORTH);
        final JScrollPane resultPane = new JScrollPane(_results);
        resultPane.setPreferredSize(new Dimension(600, 250));
        panel.add(resultPane);
        splitter.setRightComponent(panel);
        _initQueryEditor();
        return splitter;
    }

    private void _initQueryEditor() {
        _queryEditor.getPopupMenu().addSeparator();
        javax.swing.Action a = new RSyntaxTextAreaEditorKit.IncreaseFontSizeAction("Increse font size",
                null, null,
                null,
                null);
        _queryEditor.getPopupMenu().add(a);
        a = new RSyntaxTextAreaEditorKit.DecreaseFontSizeAction("Decrese font size",
                null, null,
                null,
                null);
        if (_NICE_FONT_AVAILABLE) {
            _queryEditor.setFont(_NICE_FONT);
        }
        _queryEditor.getPopupMenu().add(a);
        _queryEditor.getPopupMenu().addSeparator();
        final JMenu queryLanguageMenu = new JMenu("Query Language");
        queryLanguageMenu.add(getApplication().getContext().getActionMap().get("queryLanguageTMQL"));
        queryLanguageMenu.add(getApplication().getContext().getActionMap().get("queryLanguageTolog"));
        queryLanguageMenu.add(getApplication().getContext().getActionMap().get("queryLanguageToma"));
        _queryEditor.getPopupMenu().add(queryLanguageMenu);
        _queryEditor.setTabsEmulated(true);
        _queryEditor.setWhitespaceVisible(true);
        _queryEditor.setTabSize(4);
        setQuery(Query.build(QueryLanguage.TMQL, ""));

        //TODO: Make this configurable and find better colors
        final SyntaxScheme scheme = _queryEditor.getSyntaxScheme();
        scheme.styles[Token.FUNCTION].foreground = Color.BLACK; 
    }

    private JComponent _createToolBar() {
        final JToolBar toolBar = UIUtils.toolbarFromActions(super.getApplication(), new String[] {"loadQuery", "saveQuery", "saveAsQuery", "---", "runQuery"});
        toolBar.add(_progressBar);
        return toolBar;
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#getQuery()
     */
    @Override
    public Query getQuery() {
        return Query.build(QueryLanguage.valueOf(_queryEditor.getSyntaxEditingStyle()), _queryEditor.getText(), _query.getURI());
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#setQuery(com.semagia.ooloo.query.Query)
     */
    @Override
    public void setQuery(final Query query) {
        _query = query;
        setQueryLanguage(query.getQueryLanguage());
        _queryEditor.setText(query.getQueryString());
        _queryEditor.setCaretPosition(0);
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#setQueryLanguage(com.semagia.ooloo.query.QueryLanguage)
     */
    @Override
    public void setQueryLanguage(QueryLanguage lang) {
        _queryEditor.setSyntaxEditingStyle(lang.name());
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#getTopicMapSource()
     */
    @Override
    public ITopicMapSource getTopicMapSource() {
        return _tmSrc;
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#setBusy(boolean)
     */
    @Override
    public void setBusy(boolean busy) {
        _progressBar.setIndeterminate(busy);
        _progressBar.setBorderPainted(busy);
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.ui.IQueryView#setResult(com.semagia.ooloo.query.IResult)
     */
    @Override
    public void setResult(final IResult result) {
        if (result == null) {
            _results.setModel(_NOOP_TABLE_MODEL);
        }
        else {
            _results.setModel(new DefaultTableModel(_getData(result), new Vector<String>(Arrays.asList(result.getColumnNames()))));
            result.close();
            _results.repaint();
        }
    }

    private Vector<Vector<String>> _getData(IResult result) {
        final Vector<Vector<String>> data = new Vector<Vector<String>>();
        while (result.next()) {
            data.add(new Vector<String>(Arrays.asList(result.getValues())));
        }
        return data;
    }

}
