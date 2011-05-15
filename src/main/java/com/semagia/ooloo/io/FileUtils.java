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
package com.semagia.ooloo.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Utility functions to read/write strings from/to files.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class FileUtils {

    private FileUtils() {
        // noop.
    }

    /**
     * Reads the content of the file into a string, using UTF-8 encoding.
     *
     * @param file The file to read.
     * @return The file content.
     * @throws IOException In case of an error.
     */
    public static String read(final File file) throws IOException {
        return read(file, "utf-8");
    }

    /**
     * Reads the content of the file into a string, using the provided encoding.
     *
     * @param file The file to read.
     * @param encoding The encoding.
     * @return The file content.
     * @throws IOException In case of an error.
     */
    public static String read(final File file, final String encoding) throws IOException {
        final Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        final char[] cbuffer = new char[2048];
        final StringBuilder buffer = new StringBuilder();
        int i = 0;
        while((i=in.read(cbuffer)) != -1) {
            buffer.append(cbuffer, 0, i);
        }
        in.close();
        return buffer.toString();
    }

    /**
     * Writes the string to the provided file, using UTF-8 encoding.
     *
     * @param file The file.
     * @param content The content to write.
     * @throws IOException In case of an error.
     */
    public static void write(final File file, final String content) throws IOException {
        write(file, content, "utf-8");
    }

    /**
     * Writes the string to the provided file, using the provided encoding.
     *
     * @param file The file.
     * @param content The content to write.
     * @param encoding The encoding.
     * @throws IOException In case of an error.
     */
    public static void write(final File file, final String content, final String encoding) throws IOException {
        final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }

}
