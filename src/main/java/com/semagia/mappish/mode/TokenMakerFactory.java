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
package com.semagia.mappish.mode;

import java.util.HashMap;
import java.util.Map;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.semagia.mappish.model.QueryLanguage;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public class TokenMakerFactory extends AbstractTokenMakerFactory {

    /* (non-Javadoc)
     * @see org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory#createTokenMakerKeyToClassNameMap()
     */
    @Override
    protected Map<String, String> createTokenMakerKeyToClassNameMap() {
        final Map<String, String> map = new HashMap<String, String>();
        map.put(SyntaxConstants.SYNTAX_STYLE_NONE, "org.fife.ui.rsyntaxtextarea.modes.PlainTextTokenMaker");
        map.put(QueryLanguage.TMQL.name(), "com.semagia.mappish.mode.TMQLTokenMaker");
        map.put(QueryLanguage.TOLOG.name(), "com.semagia.mappish.mode.TologTokenMaker");
        return map;
    }

}
