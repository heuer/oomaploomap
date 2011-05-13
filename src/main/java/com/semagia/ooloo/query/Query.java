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
package com.semagia.ooloo.query;

import java.net.URI;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class Query {

    private final QueryLanguage _lang;
    private final String _query;
    private final URI _iri;

    private Query(final QueryLanguage lang, final String query) {
        this(lang, query, null);
    }

    private Query(final QueryLanguage lang, final String query, final URI iri) {
        _lang = lang;
        _query = query;
        _iri = iri;
    }

    public QueryLanguage getQueryLanguage() {
        return _lang;
    }

    public String getQueryString() {
        return _query;
    }

    public URI getURI() {
        return _iri;
    }

    public static Query build(final QueryLanguage lang, final String query) {
        return build(lang, query, null);
    }

    public static Query build(final QueryLanguage lang, final String query, final URI iri) {
        return new Query(lang, query, iri);
    }

}
