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
package com.semagia.mappish.query;

import junit.framework.TestCase;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public class TestQueryLanguage extends TestCase {

    public void testByExtension() {
        assertEquals(QueryLanguage.TMQL, QueryLanguage.fromExtension("tq"));
        assertEquals(QueryLanguage.TOLOG, QueryLanguage.fromExtension("tl"));
        assertEquals(QueryLanguage.TMQL, QueryLanguage.fromExtension("tQ"));
        assertEquals(QueryLanguage.TOLOG, QueryLanguage.fromExtension("tL"));
    }

    public void testByExtensionTologIllegal() {
        try {
            QueryLanguage.fromExtension("tlo");
            fail("Expected an IAE");
        }
        catch (IllegalArgumentException ex) {
            
        }
    }

}
