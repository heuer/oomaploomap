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

/**
 * Represents a query result.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public interface IResult {

    /**
     * Returns the column names.
     * 
     * @return The column names.
     */
    public String[] getColumnNames();

    /**
     * Returns the values of the current row as string values.
     * 
     * @return The values of the current row.
     */
    public String[] getValues();

    /**
     * Moves to the next row.
     * 
     * After creation the cursor is an invalid state. After calling {@code next} the
     * cursor moves to the first row.
     * 
     * @return {@code true} if a "next" row exists, otherwise {@code false}.
     */
    public boolean next();

    /**
     * Closes the query result.
     */
    public void close();

}
