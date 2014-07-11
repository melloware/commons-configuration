/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration.plist;

import org.apache.commons.configuration.event.AbstractTestConfigurationEvents;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.junit.Test;

/**
 * A base test class for testing the events generated by the plist
 * configurations. This class especially checks events related to the special
 * handling of byte arrays.
 *
 * @version $Id$
 */
public abstract class AbstractTestPListEvents extends
        AbstractTestConfigurationEvents
{
    /** Constant for the name of the byte array property. */
    private static final String TEST_PROPBYTE = "byteData";

    /** Constant for the test byte array used for testing. */
    private static final byte[] TEST_DATA =
    { 1, 2, 3 };

    /**
     * Tests the events generated by an added byte array property.
     */
    @Test
    public void testAddByteArrayPropertyEvent()
    {
        config.addProperty(TEST_PROPBYTE, TEST_DATA);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPBYTE,
                TEST_DATA, true);
        listener.checkEvent(ConfigurationEvent.ADD_PROPERTY, TEST_PROPBYTE,
                TEST_DATA, false);
        listener.done();
    }

    /**
     * Tests the events generated by setting a byte array property.
     */
    @Test
    public void testSetByteArrayPropertyEvent()
    {
        config.setProperty(TEST_PROPBYTE, TEST_DATA);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, TEST_PROPBYTE,
                TEST_DATA, true);
        listener.checkEvent(ConfigurationEvent.SET_PROPERTY, TEST_PROPBYTE,
                TEST_DATA, false);
        listener.done();
    }
}
