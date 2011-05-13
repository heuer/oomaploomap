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
package com.semagia.mappish.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.semagia.mio.DeserializerRegistry;
import com.semagia.mio.IDeserializer;
import com.semagia.mio.IMapHandler;
import com.semagia.mio.MIOException;
import com.semagia.mio.Property;
import com.semagia.mio.Source;
import com.semagia.mio.Syntax;

/**
 * 
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class ImportUtils {

    private ImportUtils() {
        // noop.
    }

    public static void importTopicMap(final File file, final IMapHandler handler) throws ImportException, IOException {
        final IDeserializer deser = _createDeserializer(file.getName());
        if (deser == null) {
            throw new ImportException("Cannot import " + file.getName() + ". No deserializer found.");
        }
        deser.setMapHandler(handler);
        // Enable more lenient topic map parsing
        deser.setProperty(Property.VALIDATE, Boolean.FALSE);
        deser.setProperty(Property.LTM_LEGACY, Boolean.FALSE);
        final InputStream stream = new BufferedInputStream(new FileInputStream(file));
        try {
            deser.parse(new Source(stream, file.toURI().toString()));
        }
        catch (MIOException ex) {
            if (ex.getCause() instanceof IOException) {
                throw ((IOException) ex.getCause());
            }
            else {
                throw new ImportException(ex);
            }
        }
        finally {
            stream.close();
        }
    }

    private static IDeserializer _createDeserializer(final String fileName) {
        final int dotIdx = fileName.lastIndexOf('.');
        if (dotIdx == -1) {
            return null;
        }
        final Syntax syntax = Syntax.forFileExtension(fileName.substring(dotIdx+1));
        if (syntax == null) {
            return null;
        }
        return DeserializerRegistry.getInstance().createDeserializer(syntax);
    }

}
