/*
 * Copyright 2017 Crown Copyright
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

package uk.gov.gchq.gaffer.store.serialiser;

import org.junit.Before;
import org.junit.Test;

import uk.gov.gchq.gaffer.data.element.id.EdgeId;
import uk.gov.gchq.gaffer.data.element.id.ElementId;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.serialisation.implementation.StringSerialiser;
import uk.gov.gchq.gaffer.store.schema.Schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ElementIdSerialiserTest {

    private Schema schema;
    private ElementIdSerialiser serialiser;
    private static final String TEST_VERTEX = "testVertex";

    @Before
    public void setUp() {
        schema = new Schema.Builder()
                .vertexSerialiser(new StringSerialiser())
                .build();
        serialiser = new ElementIdSerialiser(schema);
    }

    @Test
    public void testNullSerialiser() {
        // Given
        schema = new Schema.Builder().build();

        // When / Then
        try {
            serialiser = new ElementIdSerialiser(schema);
            fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Vertex serialiser is required"));
        }
    }

    @Test
    public void testCanSerialiseEntityId() throws SerialisationException {
        // Given
        final EntityId entityId = new EntitySeed("vertex");

        // When
        final byte[] serialisedEntityId = serialiser.serialise(entityId);
        final ElementId deserialisedEntityId = serialiser.deserialise(serialisedEntityId);

        // Then
        assertEquals(entityId, deserialisedEntityId);
    }

    @Test
    public void testCanSerialiseEdgeId() throws SerialisationException {
        // Given
        final EdgeId edgeId = new EdgeSeed("source", "destination", true);

        // When
        final byte[] serialisedEdgeId = serialiser.serialise(edgeId);
        final ElementId deserialisedEdgeId = serialiser.deserialise(serialisedEdgeId);

        // Then
        assertEquals(edgeId, deserialisedEdgeId);
    }

    @Test
    public void testCanSerialiseVertex() throws SerialisationException {
        // When
        final byte[] serialisedVertex = serialiser.serialiseVertex(TEST_VERTEX);
        final EntityId deserialisedEntityId = (EntityId) serialiser.deserialise(serialisedVertex);

        // Then
        assertEquals(TEST_VERTEX, deserialisedEntityId.getVertex());
    }

    @Test
    public void testCantSerialiseIntegerClass() throws SerialisationException {
        assertFalse(serialiser.canHandle(Integer.class));
    }

    @Test
    public void testCanSerialiseElementIdClass() throws SerialisationException {
        assertTrue(serialiser.canHandle(ElementId.class));
    }

    @Test
    public void testDeserialiseEmpty() throws SerialisationException {
        assertEquals(null, serialiser.deserialiseEmpty());
    }

    @Test
    public void testPreserveObjectOrdering() throws SerialisationException {
        assertEquals(true, serialiser.preservesObjectOrdering());
    }

    @Test
    public void testIsConsistent() {
        assertEquals(true, serialiser.isConsistent());
    }
}
