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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.semagia.mio.DeserializerRegistry;
import com.semagia.mio.IDeserializer;
import com.semagia.mio.IMapHandler;
import com.semagia.mio.MIOException;
import com.semagia.mio.Property;
import com.semagia.mio.Source;
import com.semagia.mio.Syntax;

/**
 * Utility functions to import topic maps regardless of the underlying Topic Maps engine.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
public final class ImportUtils {

    private ImportUtils() {
        // noop.
    }

    /**
     * Import the provided IRI. Events are sent to the provided {@link IMapHandler}.
     * 
     * @param uri The IRI to import the topic map from.
     * @param handler The handler which should receive the MIO events.
     * @throws IOException In case of an error.
     */
    public static void importTopicMap(final URI uri, final IMapHandler handler) throws IOException {
        final String base = uri.toString();
        final IDeserializer deser = _createDeserializer(base);
        if (deser == null) {
            throw new IOException("Cannot import " + base + ". No deserializer found.");
        }
        deser.setMapHandler(handler);
        // Enable more lenient topic map parsing
        deser.setProperty(Property.VALIDATE, Boolean.FALSE);
        deser.setProperty(Property.LTM_LEGACY, Boolean.FALSE);
        final InputStream stream = new BufferedInputStream(uri.toURL().openStream());
        try {
            deser.parse(new Source(stream, base));
        }
        catch (MIOException ex) {
            if (ex.getCause() instanceof IOException) {
                throw ((IOException) ex.getCause());
            }
            else {
                throw new IOException(ex);
            }
        }
        finally {
            stream.close();
        }
    }

    /**
     * Returns a {@link IDeserializer} instance to import the provided IRI.
     * 
     * This method relies on the file name extension.
     * 
     * @param iri The IRI.
     * @return A deserializer instance or {@code null} if no deserializer can be found.
     */
    private static IDeserializer _createDeserializer(final String iri) {
        final int dotIdx = iri.lastIndexOf('.');
        if (dotIdx == -1) {
            return null;
        }
        final Syntax syntax = Syntax.forFileExtension(iri.substring(dotIdx+1));
        return syntax == null ? null : DeserializerRegistry.getInstance().createDeserializer(syntax);
    }

}
