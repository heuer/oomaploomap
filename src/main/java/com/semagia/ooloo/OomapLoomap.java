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
package com.semagia.ooloo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;

import com.semagia.ooloo.io.FileUtils;
import com.semagia.ooloo.model.ITopicMapSystem;
import com.semagia.ooloo.model.OntopiaTopicMapSystem;
import com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource;
import com.semagia.ooloo.query.IResult;
import com.semagia.ooloo.query.Query;
import com.semagia.ooloo.query.QueryLanguage;
import com.semagia.ooloo.ui.ErrorDialog;
import com.semagia.ooloo.ui.IQueryView;
import com.semagia.ooloo.ui.UIUtils;
import com.semagia.ooloo.ui.QueryFrame;

/**
 * Main application.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class OomapLoomap extends SingleFrameApplication {

    private static final FileFilter _TM_FILE_FILTER = new FileNameExtensionFilter(
            "Topic Maps (*.ctm, *.jtm, *.ltm, *.xtm)", "ctm", "jtm", "ltm", "xtm");

    private static final FileFilter _QL_FILE_FILTER = new FileNameExtensionFilter(
            "Topic Maps Query (*.tq, *.tl, *.ta)", "tq", "tl", "ta");

    static {
        // Replace the default token maker 
        System.setProperty(TokenMakerFactory.PROPERTY_DEFAULT_TOKEN_MAKER_FACTORY, 
                com.semagia.ooloo.mode.TokenMakerFactory.class.getName());
    }

    private JDesktopPane _desktop;
    private String _lastDirectory;

    private ITopicMapSystem _tmSys;

    private JProgressBar _progressBar;

    private InternalFrameListener _frameListener;

    private OomapLoomap() {
        _tmSys = new OntopiaTopicMapSystem();
        _frameListener = new FrameListener();
    }

    public static void main(final String[] args) throws Exception {
        Application.launch(OomapLoomap.class, args);
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Application#startup()
     */
    @Override
    protected void startup() {
        getMainFrame().setJMenuBar(_createMenuBar());
        getMainFrame().add(_createToolBar(), BorderLayout.NORTH);
        getMainFrame().setPreferredSize(new Dimension(500, 400));
        show(_createMainPanel());
        _setHasActiveFrame(false);
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.SingleFrameApplication#shutdown()
     */
    @Override
    protected void shutdown() {
        super.shutdown();
        _tmSys.close();
    }

    private void _setHasActiveFrame(boolean active) {
        getContext().getActionMap().get("runQuery").setEnabled(active);
        getContext().getActionMap().get("loadQuery").setEnabled(active);
        getContext().getActionMap().get("saveQuery").setEnabled(active);
        getContext().getActionMap().get("saveAsQuery").setEnabled(active);
    }

    /**
     * Creates the menubar.
     *
     * @return The menubar.
     */
    private JMenuBar _createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(UIUtils.menuFromActions(this, "File", new String[]{"open", "---", "quit"}));
        menuBar.add(UIUtils.menuFromActions(this, "Edit", new String[]{"cut", "copy", "paste"}));
        menuBar.add(UIUtils.menuFromActions(this, "Query", new String[]{"loadQuery", "---", "saveQuery", "saveAsQuery", "---", "runQuery"}));
        return menuBar;
    }

    /**
     * Creates the toolbar.
     *
     * @return The toolbar.
     */
    private JComponent _createToolBar() {
        _progressBar = new JProgressBar();
        _progressBar.setBorderPainted(false);
        final JToolBar toolBar = UIUtils.toolbarFromActions(this, new String[] {"open", "---", "cut", "copy", "paste", "---", "quit" });
        toolBar.add(_progressBar);
        return toolBar;
    }

    /**
     * Creates and returns the main panel.
     * 
     * This method must be invoked only once.
     *
     * @return The main panel.
     */
    private JComponent _createMainPanel() {
        _desktop = new JDesktopPane();
        return _desktop;
    }

    /**
     * Creates a file chooser with the provided file filter.
     * 
     * The file chooser starts at the last opened directory or in the
     * user's default directoy.
     *
     * @param filter The file filter.
     * @return A file chooser instance.
     */
    private final JFileChooser _fileChooser(final FileFilter filter) {
        final JFileChooser fc = new JFileChooser(_lastDirectory);
        fc.setFileFilter(filter);
        return fc;
    }

    /**
     * Creates and shows a file open dialog.
     *
     * @param filter A file filter or {@code null}.
     * @return A file or {@code null} if no file was chosen.
     */
    private File _openFile(final FileFilter filter) {
        final JFileChooser fc = _fileChooser(filter);
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(getMainFrame())) {
            final File file = fc.getSelectedFile();
            _lastDirectory = file.getAbsolutePath();
            return file;
        }
        return null;
    }

    /**
     * 
     *
     * @return
     */
    @Action
    public ImportTopicMapTask open() {
        final File file = _openFile(_TM_FILE_FILTER);
        if (file != null) {
            return new ImportTopicMapTask(this, file);
        }
        return null;
    }

    private IQueryView _queryView() {
        return (IQueryView) _desktop.getSelectedFrame();
    }

    private Query _selectedQuery() {
        final IQueryView view = _queryView();
        return view != null ? view.getQuery() : null;
    }

    @Action
    public void queryLanguageTolog() {
        _setQueryLanguage(QueryLanguage.TOLOG);
    }

    @Action
    public void queryLanguageTMQL() {
        _setQueryLanguage(QueryLanguage.TMQL);
    }

    @Action
    public void queryLanguageToma() {
        _setQueryLanguage(QueryLanguage.TOMA);
    }

    private void _setQueryLanguage(final QueryLanguage lang) {
        final Query currentQuery = _selectedQuery();
        if (currentQuery.getQueryLanguage() != lang) {
            _queryView().setQueryLanguage(lang);
        }
    }

    @Action
    public void loadQuery() {
        final File file = _openFile(_QL_FILE_FILTER);
        if (file != null) {
            try {
                final String query = FileUtils.read(file);
                _queryView().setQuery(Query.build(QueryLanguage.fromFilename(file.getName()), query, file.toURI()));
            }
            catch (IOException ex) {
                _showErrorDialog(ex);
            }
        }
    }

    @Action
    public void saveQuery() {
        final Query currentQuery = _selectedQuery();
        if (currentQuery.getURI() == null) {
            saveAsQuery();
        }
        else {
            _writeQuery(new File(currentQuery.getURI()), currentQuery.getQueryString());
        }
    }

    @Action
    public void saveAsQuery() {
        final JFileChooser fc = _fileChooser(_QL_FILE_FILTER);
        if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(getMainFrame())) {
            final File file = fc.getSelectedFile();
            _lastDirectory = file.getAbsolutePath();
            final Query currentQuery = _selectedQuery();
            _writeQuery(file, currentQuery.getQueryString());
            _queryView().setQuery(Query.build(currentQuery.getQueryLanguage(), currentQuery.getQueryString(), file.toURI()));
        }
    }

    private void _writeQuery(final File file, final String query) {
        try {
            FileUtils.write(file, query);
        }
        catch (IOException ex) {
            _showErrorDialog(ex);
        }
    }

    @Action
    public RunQueryTask runQuery() {
        return new RunQueryTask(this, _queryView());
    }

    private void _setBusy(boolean busy) {
        _progressBar.setIndeterminate(busy);
        _progressBar.setBorderPainted(busy);
        getContext().getActionMap().get("open").setEnabled(!busy);
    }

    private void _showErrorDialog(final Throwable ex) {
        _showErrorDialog(ex, ex.getMessage());
    }

    private void _showErrorDialog(final Throwable ex, final String msg) {
        final JDialog errorDialog = new ErrorDialog(ex);
        errorDialog.setVisible(true);
    }

    private void _createQueryPane(final ITopicMapSource source) {
        final QueryFrame frame = new QueryFrame(this, source);
        _desktop.add(frame);
        _desktop.setSelectedFrame(frame);
        frame.addInternalFrameListener(_frameListener);
        frame.setVisible(true);
    }


    private final class FrameListener extends InternalFrameAdapter {

        /* (non-Javadoc)
         * @see javax.swing.event.InternalFrameAdapter#internalFrameClosed(javax.swing.event.InternalFrameEvent)
         */
        @Override
        public void internalFrameClosed(InternalFrameEvent evt) {
            _setHasActiveFrame(false);
            _tmSys.closeSource(((IQueryView) evt.getInternalFrame()).getTopicMapSource());
        }

        /* (non-Javadoc)
         * @see javax.swing.event.InternalFrameAdapter#internalFrameActivated(javax.swing.event.InternalFrameEvent)
         */
        @Override
        public void internalFrameActivated(InternalFrameEvent evt) {
            _setHasActiveFrame(true);
        }
    }


    private final class RunQueryTask extends Task<IResult, Void> {

        private final IQueryView _queryView;

        public RunQueryTask(Application app, IQueryView queryView) {
            super(app);
            _queryView = queryView;
        }

        @Override
        protected IResult doInBackground() throws Exception {
            _queryView.setResult(null);
            _queryView.setBusy(true);
            return _tmSys.executeQuery(_queryView.getTopicMapSource(), _queryView.getQuery());
        }

        /* (non-Javadoc)
         * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
         */
        @Override
        protected void failed(Throwable cause) {
            _queryView.setBusy(false);
            _showErrorDialog(cause, "Query failed");
        }

        @Override
        protected void succeeded(IResult result) {
            _queryView.setBusy(false);
            _queryView.setResult(result);
        }
    }


    private final class ImportTopicMapTask extends Task<ITopicMapSource, Void> {

        private final File _file;

        public ImportTopicMapTask(Application app, final File file) {
            super(app);
            _file = file;
        }

        @Override
        protected ITopicMapSource doInBackground() throws Exception {
            _setBusy(true);
            return _tmSys.loadSource(_file.toURI());
        }

        /* (non-Javadoc)
         * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
         */
        @Override
        protected void failed(Throwable cause) {
            _setBusy(false);
            _showErrorDialog(cause, "Import failed");
        }

        @Override
        protected void succeeded(ITopicMapSource source) {
            _setBusy(false);
            _createQueryPane(source);
        }
    }

}
