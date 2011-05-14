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

}
