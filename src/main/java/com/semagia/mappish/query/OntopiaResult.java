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

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.utils.TopicStringifiers;
import net.ontopia.utils.StringifierIF;


/**
 * {@link IResult} implementation that adapts a {@link QueryResultIF}.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class OntopiaResult implements IResult {

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
