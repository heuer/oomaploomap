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

import javax.swing.JInternalFrame;

import org.jdesktop.application.Application;

import com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource;
import com.semagia.ooloo.query.IResult;
import com.semagia.ooloo.query.Query;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class QueryFrame extends JInternalFrame implements IQueryView {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final DefaultQueryView _queryView;

    /**
     * 
     *
     * @param tmSrc 
     */
    public QueryFrame(final Application app, final ITopicMapSource tmSrc) {
        super(tmSrc.getName() != null ? tmSrc.getName() + " -- " + tmSrc.getURI() : tmSrc.getURI().toString(), 
                true, //resizable
                true, //closable
                true, //maximizable
                true  //iconifiable
                );
        _queryView = new DefaultQueryView(app, tmSrc);
        super.getContentPane().setLayout(new BorderLayout());
        super.getContentPane().add(_queryView.getComponent());
        super.pack();
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.ui.IQueryView#getQuery()
     */
    @Override
    public Query getQuery() {
        return _queryView.getQuery();
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.ui.IQueryView#setQuery(com.semagia.mappish.model.IQuery)
     */
    @Override
    public void setQuery(final Query query) {
        _queryView.setQuery(query);
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.ui.IQueryView#getTopicMapSource()
     */
    @Override
    public ITopicMapSource getTopicMapSource() {
        return _queryView.getTopicMapSource();
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.ui.IQueryView#setBusy(boolean)
     */
    @Override
    public void setBusy(boolean busy) {
        _queryView.setBusy(busy);
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.ui.IQueryView#setResult(com.semagia.mappish.model.IResult)
     */
    @Override
    public void setResult(IResult result) {
        _queryView.setResult(result);
    }

}
