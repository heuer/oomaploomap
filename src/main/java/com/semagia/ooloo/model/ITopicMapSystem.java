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

import com.semagia.ooloo.query.IResult;
import com.semagia.ooloo.query.Query;
import com.semagia.ooloo.query.QueryException;
import com.semagia.ooloo.query.QueryLanguage;

/**
 * The topic map system keeps track about the loaded topic maps and can issue
 * queries against topic maps.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public interface ITopicMapSystem {

    /**
     * Returns the supported query languages.
     *
     * @return An array of query languages. The array must have at least one entry.
     */
    public QueryLanguage[] getQueryLanguages();

    /**
     * Returns an array of loaded topic maps.
     *
     * @return A (maybe empty) array of topic map sources.
     */
    public ITopicMapSource[] getSources();

    /**
     * Returns a {@link ITopicMapSource} iff it was previously loaded.
     *
     * @param uri The IRI of the source.
     * @return An {@link ITopicMapSource} instance if the it was loaded or 
     *          {@code null} if the topic map does not exist.
     */
    public ITopicMapSource getSource(final URI uri);

    /**
     * Loads the provided IRI into the system.
     * 
     * @param uri The IRI to load the topic map from.
     * @return A {@link ITopicMapSource} representing the loaded topic map.
     * @throws IOException In case of an error.
     */
    public ITopicMapSource loadSource(final URI uri) throws IOException;

    /**
     * Issues a query against the provided {@link ITopicMapSource}.
     * 
     * @param src The topic map source.
     * @param query The query.
     * @return A query result.
     * @throws QueryException In case of an error.
     */
    public IResult executeQuery(final ITopicMapSource src, final Query query) throws QueryException;

    /**
     * Closes the provided {@link ITopicMapSource}.
     * 
     * Closing a topic map source does not imply that the topic map is removed from
     * the system. The system may keep track about the usage of the topic map source
     * and keep it alive unless all references to the source are closed.
     * 
     * @param src The topic map source to close.
     */
    public void closeSource(final ITopicMapSource src);

    /**
     * Closes the instance.
     */
    public void close();


    /**
     * Represents a topic map.
     */
    public interface ITopicMapSource {

        /**
         * Returns the IRI of the topic map.
         * 
         * @return The IRI.
         */
        public URI getURI();

        /**
         * Returns the optional name of the topic map.
         * 
         * @return A string or {@code null} if the topic map has no name.
         */
        public String getName();

    }

}
