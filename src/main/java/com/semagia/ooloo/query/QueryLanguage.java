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
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public enum QueryLanguage {

    TMQL("TMQL"),
    TOLOG("tolog"),
    TOMA("Toma");

    private final String _title;

    QueryLanguage(final String title) {
        _title = title;
    }

    public String getTitle() {
        return _title;
    }

    public static QueryLanguage fromFilename(final String fileName) {
        if (fileName == null) {
            throw new NullPointerException("The file name must not be null");
        }
        final int dotIdx = fileName.lastIndexOf('.');
        if (dotIdx == -1) {
            throw new IllegalArgumentException("Unknown query language '" + fileName + "'");
        }
        return fromExtension(fileName.substring(dotIdx+1));
    }

    public static QueryLanguage fromExtension(final String ext) {
        if (ext == null) {
            throw new NullPointerException("The extension must not be null");
        }
        if ("tq".equalsIgnoreCase(ext)) {
            return TMQL;
        }
        else if ("tl".equalsIgnoreCase(ext)) {
            return TOLOG;
        }
        else if ("ta".equalsIgnoreCase(ext)) {
            return TOMA;
        }
        throw new IllegalArgumentException("Unknown extension '" + ext + "'");
    }

}
