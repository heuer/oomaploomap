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
package com.semagia.ooloo.model;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapExistsException;
import org.tmapi.core.TopicMapSystem;

import com.semagia.mio.IMapHandler;
import com.semagia.ooloo.io.ImportUtils;
import com.semagia.ooloo.query.IResult;
import com.semagia.ooloo.query.Query;
import com.semagia.ooloo.query.QueryException;

/**
 * Abstract {@link ITopicMapSystem} implementation that uses a {@link TopicMapSystem}
 * implementation.
 * 
 * Derived classes are required to implement the {@link #createMapHandler(TopicMap)},
 * {@link #createTopicMapSystem()}, and {@link #executeQuery(TopicMap, Query)} methods;
 * all other methods of the {@link ITopicMapSystem} are implemented by this class.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public abstract class AbstractTMAPITopicMapSystem implements ITopicMapSystem {


    private final Map<URI, TopicMapSource> _sources;
    private final TopicMapSystem _tmSys;

    protected AbstractTMAPITopicMapSystem() {
        _sources = new HashMap<URI, TopicMapSource>();
        _tmSys = createTopicMapSystem();
    }

    /**
     * Returns the underlying {@link TopicMapSystem}.
     * 
     * @return A {@link TopicMapSystem} instance.
     */
    protected abstract TopicMapSystem createTopicMapSystem();

    /**
     * Returns a {@link IMapHandler} which operated upon the provided topic map.
     *
     * @param topicMap The topic map which should receive the MIO events.
     * @return A  {@link IMapHandler} instance.
     */
    protected abstract IMapHandler createMapHandler(final TopicMap topicMap);


    /**
     * Issues a query against the provided topic map.
     *
     * @param topicMap The topic map to issue the query against.
     * @param query The query.
     * @return The query result.
     * @throws QueryException In case of an error.
     */
    protected abstract IResult executeQuery(final TopicMap topicMap, final Query query) throws QueryException;

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#getSources()
     */
    @Override
    public final ITopicMapSource[] getSources() {
        return _sources.values().toArray(new ITopicMapSource[_sources.size()]);
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#getSource(java.net.URI)
     */
    @Override
    public ITopicMapSource getSource(final URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("The IRI must not be null");
        }
        return _sources.get(uri);
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#loadSource(java.net.URI)
     */
    @Override
    public final ITopicMapSource loadSource(final URI uri) throws IOException {
        if (uri == null) {
            throw new IllegalArgumentException("The IRI must not be null");
        }
        TopicMapSource src = _sources.get(uri);
        if (src == null) {
            TopicMap tm = null;
            try {
                tm = _tmSys.createTopicMap(uri.toString());
            }
            catch (TopicMapExistsException ex) {
                // Shouldn't happen
            }
            try {
                ImportUtils.importTopicMap(uri, createMapHandler(tm));
            }
            catch (IOException ex) {
                tm.remove();
                throw ex;
            }
            String name = null;
            final Topic reifier = tm.getReifier();
            if (reifier != null) {
                final Iterator<Name> iter = reifier.getNames().iterator();
                if (iter.hasNext()) {
                    name = iter.next().getValue();
                }
            }
            src = new TopicMapSource(uri, name);
            _sources.put(src.getURI(), src);
        }
        src.usage++;
        return src;
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#executeQuery(com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource, com.semagia.ooloo.query.Query)
     */
    @Override
    public final IResult executeQuery(final ITopicMapSource src, final Query query) throws QueryException {
        final String iri = src.getURI().toString();
        return executeQuery(_tmSys.getTopicMap(iri), query);
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#closeSource(com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource)
     */
    @Override
    public final void closeSource(final ITopicMapSource src) {
        final TopicMapSource source = _sources.get(src.getURI());
        if (source != null) {
            source.usage--;
            if (source.usage == 0) {
                _sources.remove(source.getURI());
                _tmSys.getTopicMap(source.getURI().toString()).remove();
            }
        }
    }

    /* (non-Javadoc)
     * @see com.semagia.ooloo.model.ITopicMapSystem#close()
     */
    @Override
    public final void close() {
        _tmSys.close();
        _sources.clear();
    }


    /**
     * Internal {@link ITopicMapSource} implementation.
     */
    private static final class TopicMapSource implements ITopicMapSource {

        private final URI _iri;
        private final String _name;
        private int usage = 0;

        public TopicMapSource(final URI iri, final String name) {
            _iri = iri;
            _name = name;
        }

        /* (non-Javadoc)
         * @see com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource#getURI()
         */
        @Override
        public URI getURI() {
            return _iri;
        }

        /* (non-Javadoc)
         * @see com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource#getName()
         */
        @Override
        public String getName() {
            return _name;
        }

    }
}
