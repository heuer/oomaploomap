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
package com.semagia.mappish.model;

import org.tmapi.core.TMAPIException;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.tmapi2.TopicMapImpl;
import net.ontopia.topicmaps.io.OntopiaMapHandler;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryProcessorFactoryIF;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;
import net.ontopia.topicmaps.utils.TopicStringifiers;
import net.ontopia.utils.StringifierIF;

import com.semagia.mappish.query.IResult;
import com.semagia.mappish.query.Query;
import com.semagia.mappish.query.QueryException;
import com.semagia.mio.IMapHandler;

/**
 * {@link ITopicMapSystem} implementation that uses Ontopia.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class OntopiaTopicMapSystem extends AbstractTMAPITopicMapSystem {

    public OntopiaTopicMapSystem() {
        super();
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.AbstractTMAPITopicMapSystem#getTopicMapSystem()
     */
    @Override
    protected TopicMapSystem getTopicMapSystem() {
        try {
            return new net.ontopia.topicmaps.impl.tmapi2.TopicMapSystemFactory().newTopicMapSystem();
        }
        catch (TMAPIException ex) {
            throw new RuntimeException("Initialization of the topic map system failed", ex);
        }
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.AbstractTMAPITopicMapSystem#createMapHandler(org.tmapi.core.TopicMap)
     */
    @Override
    protected IMapHandler createMapHandler(TopicMap tm) {
        return new OntopiaMapHandler(((TopicMapImpl) tm).getWrapped());
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.AbstractTMAPITopicMapSystem#executeQuery(org.tmapi.core.TopicMap, com.semagia.mappish.query.Query)
     */
    @Override
    public IResult executeQuery(final TopicMap topicMap, final Query query) throws QueryException {
        final TopicMapIF tm = ((TopicMapImpl) topicMap).getWrapped();
        final QueryProcessorFactoryIF procFactory = QueryUtils.getQueryProcessorFactory(query.getQueryLanguage().name());
        if (procFactory == null) {
            throw new QueryException("Unknown query language " + query.getQueryLanguage());
        }
        final QueryProcessorIF proc = procFactory.createQueryProcessor(tm, null, null);
        try {
            return new OntopiaResult(proc.execute(query.getQueryString()));
        } 
        catch (InvalidQueryException ex) {
            throw new QueryException(ex);
        }
    }


    /**
     * {@link IResult} implementation that adapts a {@link QueryResultIF}.
     */
    private static class OntopiaResult implements IResult {

        private QueryResultIF _result;
        private final int _width;
        private static final StringifierIF _TOPIC2STR = TopicStringifiers.getDefaultStringifier();

        public OntopiaResult(final QueryResultIF result) {
            _result = result;
            _width = result.getWidth();
        }

        /* (non-Javadoc)
         * @see com.semagia.mappish.query.IResult#getColumnNames()
         */
        @Override
        public String[] getColumnNames() {
            return _result.getColumnNames();
        }

        /* (non-Javadoc)
         * @see com.semagia.mappish.query.IResult#getValues()
         */
        @Override
        public String[] getValues() {
            final String[] row = new String[_width];
            final Object[] objects = _result.getValues();
            for (int i=0; i<_width; i++) {
                if (objects[i] instanceof TopicIF) {
                    row[i] = _TOPIC2STR.toString(objects[i]);
                }
                else if (objects[i] instanceof TopicNameIF) {
                    row[i] = ((TopicNameIF) objects[i]).getValue();
                }
                else {
                    row[i] = objects[i].toString();
                }
            }
            return row;
        }

        /* (non-Javadoc)
         * @see com.semagia.mappish.query.IResult#next()
         */
        @Override
        public boolean next() {
            return _result.next();
        }

        /* (non-Javadoc)
         * @see com.semagia.mappish.query.IResult#close()
         */
        @Override
        public void close() {
            _result.close();
            _result = null;
        }

    }

}
