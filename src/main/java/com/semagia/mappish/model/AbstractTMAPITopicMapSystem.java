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

import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapExistsException;
import org.tmapi.core.TopicMapSystem;

import com.semagia.mappish.io.ImportException;
import com.semagia.mappish.io.ImportUtils;
import com.semagia.mappish.query.IResult;
import com.semagia.mappish.query.Query;
import com.semagia.mappish.query.QueryException;
import com.semagia.mio.IMapHandler;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public abstract class AbstractTMAPITopicMapSystem implements ITopicMapSystem {


    private final Map<String, TopicMapSource> _sources;
    private final TopicMapSystem _tmSys;

    protected AbstractTMAPITopicMapSystem() {
        _sources = new HashMap<String, TopicMapSource>();
        _tmSys = getTopicMapSystem();
    }

    protected abstract TopicMapSystem getTopicMapSystem();

    /**
     * 
     *
     * @param tm
     * @return
     */
    protected abstract IMapHandler createMapHandler(final TopicMap tm);


    /**
     * 
     *
     * @param topicMap
     * @param query
     * @return
     * @throws QueryException
     */
    protected abstract IResult executeQuery(final TopicMap topicMap, final Query query) throws QueryException;

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.ITopicMapSystem#getTopicMapSources()
     */
    @Override
    public ITopicMapSource[] getTopicMapSources() {
        return _sources.values().toArray(new ITopicMapSource[_sources.size()]);
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.ITopicMapSystem#loadSource(java.net.URI)
     */
    public ITopicMapSource loadSource(final URI uri) throws ImportException, IOException {
        final String iri = uri.toString();
        TopicMapSource src = _sources.get(iri);
        if (src == null) {
            TopicMap tm = null;
            try {
                tm = _tmSys.createTopicMap(iri);
            }
            catch (TopicMapExistsException ex) {
                // Shouldn't happen
            }
            ImportUtils.importTopicMap(new File(uri), createMapHandler(tm));
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
        return executeQuery(_tmSys.getTopicMap(iri), query);
    }

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.ITopicMapSystem#close()
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.semagia.mappish.model.ITopicMapSystem#close()
     */
    @Override
    public void close() {
        _tmSys.close();
        _sources.clear();
    }


    private static final class TopicMapSource implements ITopicMapSource {

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
