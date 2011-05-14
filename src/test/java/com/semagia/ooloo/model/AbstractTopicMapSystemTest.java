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
import java.net.URISyntaxException;
import java.net.URL;

import com.semagia.ooloo.model.ITopicMapSystem.ITopicMapSource;
import com.semagia.ooloo.query.QueryLanguage;

import junit.framework.TestCase;

/**
 * Tests against {@link ITopicMapSystem}
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public abstract class AbstractTopicMapSystemTest extends TestCase {

    private ITopicMapSystem _sys;

    protected abstract ITopicMapSystem createTopicMapSystem();

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _sys = createTopicMapSystem();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        _sys.close();
    }

    public void testQueryLanguages() {
        final QueryLanguage[] langs = _sys.getQueryLanguages();
        assertNotNull(langs);
        assertTrue(langs.length > 0);
    }

    public void testTMSources() {
        final ITopicMapSource[] sources = _sys.getTopicMapSources();
        assertNotNull(sources);
        assertTrue(sources.length == 0);
    }

    public void testImportIllegal() throws IOException {
        try {
            _sys.loadSource(null);
            fail("Expected an IAE");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testImport() throws IOException, URISyntaxException {
        ITopicMapSource[] sources = _sys.getTopicMapSources();
        assertTrue(sources.length == 0);
        final URL url = AbstractTopicMapSystemTest.class.getResource("/test-tm.ctm");
        assertNotNull(url);
        final URI uri = url.toURI();
        final ITopicMapSource src = _sys.loadSource(uri);
        assertNotNull(src);
        assertEquals(uri, src.getURI());
        assertNull(src.getName());
        sources = _sys.getTopicMapSources();
        assertTrue(sources.length == 1);
        assertEquals(sources[0].getURI(), src.getURI());
        assertEquals(sources[0].getName(), src.getName());
    }

    public void testImport2() throws IOException, URISyntaxException {
        ITopicMapSource[] sources = _sys.getTopicMapSources();
        assertTrue(sources.length == 0);
        final URL url = AbstractTopicMapSystemTest.class.getResource("/test-tm-with-name.ctm");
        assertNotNull(url);
        final URI uri = url.toURI();
        final ITopicMapSource src = _sys.loadSource(uri);
        assertNotNull(src);
        assertEquals(uri, src.getURI());
        assertEquals("Name", src.getName());
        sources = _sys.getTopicMapSources();
        assertTrue(sources.length == 1);
        assertEquals(sources[0].getURI(), src.getURI());
        assertEquals(sources[0].getName(), src.getName());
        assertSame(src, _sys.loadSource(uri));
    }

    public void testImportClose() throws IOException, URISyntaxException {
        ITopicMapSource[] sources = _sys.getTopicMapSources();
        assertTrue(sources.length == 0);
        final URL url = AbstractTopicMapSystemTest.class.getResource("/test-tm-with-name.ctm");
        assertNotNull(url);
        final URI uri = url.toURI();
        ITopicMapSource src = _sys.loadSource(uri);
        _sys.closeSource(src);
        sources = _sys.getTopicMapSources();
        assertEquals(0, sources.length);

        src = _sys.loadSource(uri);
        sources = _sys.getTopicMapSources();
        assertEquals(1, sources.length);
        assertSame(src, _sys.loadSource(uri));
        _sys.closeSource(src);
        sources = _sys.getTopicMapSources();
        assertEquals(1, sources.length);
        _sys.closeSource(src);
        sources = _sys.getTopicMapSources();
        assertEquals(0, sources.length);
    }

}
