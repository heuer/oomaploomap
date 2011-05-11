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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.ontopia.topicmaps.impl.tmapi2.TopicMapImpl;
import net.ontopia.topicmaps.io.OntopiaMapHandler;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.tmql.impl.basic.TMQL4JQueryProcessor;
import net.ontopia.topicmaps.query.utils.QueryUtils;

import org.tmapi.core.Name;
import org.tmapi.core.TMAPIException;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import com.semagia.mappish.io.ImportException;
import com.semagia.mappish.io.ImportUtils;
import com.semagia.mappish.query.IResult;
import com.semagia.mappish.query.OntopiaResult;
import com.semagia.mappish.query.Query;
import com.semagia.mappish.query.QueryException;
import com.semagia.mappish.query.QueryLanguage;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class TopicMapSystem {

    private final org.tmapi.core.TopicMapSystem _tmSys;
    private final Map<String, TopicMapSource> _sources;

    public TopicMapSystem() {
        try {
            _tmSys = new net.ontopia.topicmaps.impl.tmapi2.TopicMapSystemFactory().newTopicMapSystem();
        } 
        catch (TMAPIException ex) {
            throw new RuntimeException("Unexpected error while creating TopicMapSystem", ex);
        }
        _sources = new HashMap<String, TopicMapSource>();
    }

    /**
     * 
     *
     * @return
     */
    public ITopicMapSource[] getTopicMapSources() {
        return _sources.values().toArray(new ITopicMapSource[_sources.size()]);
    }

    /**
     * 
     *
     * @param iri
     * @return
     * @throws IOException 
     * @throws ImportException 
     */
    public ITopicMapSource loadSource(final URI uri) throws ImportException, IOException {
        final String iri = uri.toString();
        TopicMapSource src = _sources.get(iri);
        if (src == null) {
            TopicMap tm = null;
            try {
                tm = _tmSys.createTopicMap(iri);
            } 
            catch (TMAPIException ex) {
                // Unlikely
            }
            ImportUtils.importTopicMap(new File(uri), new OntopiaMapHandler(((TopicMapImpl) tm).getWrapped()));
            String name = null;
            final Topic reifier = tm.getReifier();
            if (reifier != null) {
                final Iterator<Name> iter = reifier.getNames().iterator();
                if (iter.hasNext()) {
                    name = iter.next().getValue();
                }
            }
            src = new TopicMapSource(uri, name);
            _sources.put(iri, src);
        }
        src.usage++;
        return src;
    }

    public IResult executeQuery(final ITopicMapSource src, final Query query) throws QueryException {
        final String iri = src.getURI().toString();
        final TopicMap tm = _tmSys.getTopicMap(iri);
        if (query.getQueryLanguage() == QueryLanguage.TOLOG) {
            return _executeTologQuery(tm, query.getQueryString());
        }
        return _executeTMQLQuery(tm, query.getQueryString());
    }

    private IResult _executeTMQLQuery(final TopicMap tm, final String query) throws QueryException {
        return _runQuery(new TMQL4JQueryProcessor(((TopicMapImpl) tm).getWrapped()), query);
    }

    private IResult _executeTologQuery(final TopicMap tm, String query) throws QueryException {
        return _runQuery(QueryUtils.createQueryProcessor(((TopicMapImpl) tm).getWrapped()), query);

    }

    private IResult _runQuery(final QueryProcessorIF proc, final String query) throws QueryException {
        try {
            return new OntopiaResult(proc.execute(query));
        } 
        catch (InvalidQueryException ex) {
            throw new QueryException(ex);
        }
    }

    

    /**
     * 
     *
     * @param iri
     */
    public void closeSource(final ITopicMapSource src) {
        final TopicMapSource source = _sources.get(src.getURI());
        if (source != null) {
            final String iri = src.getURI().toString();
            source.usage--;
            if (source.usage == 0) {
                _sources.remove(src.getURI());
                _tmSys.getTopicMap(iri).remove();
            }
        }
    }

    /**
     * 
     *
     */
    public void close() {
        _tmSys.close();
        _sources.clear();
    }

    
    private static class TopicMapSource implements ITopicMapSource {

        private final URI _iri;
        private final String _name;
        private int usage;

        public TopicMapSource(final URI iri, final String name) {
            _iri = iri;
            _name = name;
        }

        @Override
        public URI getURI() {
            return _iri;
        }

        @Override
        public String getName() {
            return _name;
        }

    }

}
