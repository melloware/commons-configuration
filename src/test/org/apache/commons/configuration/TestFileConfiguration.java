/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package org.apache.commons.configuration;

import java.net.URL;
import java.io.File;

import junit.framework.TestCase;

/**
 * @author Emmanuel Bourg
 * @version $Revision: 1.4 $, $Date: 2005/01/15 15:29:30 $
 */
public class TestFileConfiguration extends TestCase
{
    public void testSetURL() throws Exception
    {
        // http URL
        FileConfiguration config = new PropertiesConfiguration();
        config.setURL(new URL("http://jakarta.apache.org/commons/configuration/index.html"));

        assertEquals("base path", "http://jakarta.apache.org/commons/configuration/", config
                .getBasePath());
        assertEquals("file name", "index.html", config.getFileName());

        // file URL
        config.setURL(new URL("file:/temp/test.properties"));
        assertEquals("base path", "file:/temp/", config.getBasePath());
        assertEquals("file name", "test.properties", config.getFileName());
    }

    public void testLocations() throws Exception
    {
        PropertiesConfiguration config = new PropertiesConfiguration();

        File directory = new File("conf");
        File file = new File(directory, "test.properties");
        config.setFile(file);
        assertEquals(directory.getAbsolutePath(), config.getBasePath());
        assertEquals("test.properties", config.getFileName());
        assertEquals(file.getAbsolutePath(), config.getPath());

        config.setPath("conf" + File.separator + "test.properties");
        assertEquals("test.properties", config.getFileName());
        assertEquals(directory.getAbsolutePath(), config.getBasePath());
        assertEquals(file.getAbsolutePath(), config.getPath());
        assertEquals(file.toURL(), config.getURL());

        config.setBasePath(null);
        config.setFileName("test.properties");
        assertNull(config.getBasePath());
        assertEquals("test.properties", config.getFileName());
    }

    public void testCreateFile1() throws Exception
    {
        File file = new File("target/test-resources/foo/bar/test.properties");
        if (file.exists())
        {
            file.delete();
            file.getParentFile().delete();
        }

        assertFalse("The file should not exist", file.exists());

        FileConfiguration config = new PropertiesConfiguration(file);
        config.save();

        assertTrue("The file doesn't exist", file.exists());
    }

    public void testCreateFile2() throws Exception
    {
        File file = new File("target/test-resources/foo/bar/test.properties");
        if (file.exists())
        {
            file.delete();
            file.getParentFile().delete();
        }

        assertFalse("The file should not exist", file.exists());

        FileConfiguration config = new PropertiesConfiguration();
        config.setFile(file);
        config.save();

        assertTrue("The file doesn't exist", file.exists());
    }

    public void testCreateFile3() throws Exception
    {
        File file = new File("target/test-resources/foo/bar/test.properties");
        if (file.exists())
        {
            file.delete();
            file.getParentFile().delete();
        }

        assertFalse("The file should not exist", file.exists());

        FileConfiguration config = new PropertiesConfiguration();
        config.save(file);

        assertTrue("The file doesn't exist", file.exists());
    }

    /**
     * Tests collaboration with ConfigurationFactory: Is the base path set on
     * loading is valid in file based configurations?
     * 
     * @throws Exception if an error occurs
     */
    public void testWithConfigurationFactory() throws Exception
    {
        File dir = new File("conf");
        File file = new File(dir, "testFileConfiguration.properties");

        if (file.exists())
        {
            assertTrue("File cannot be deleted", file.delete());
        }

        try
        {
            ConfigurationFactory factory = new ConfigurationFactory();
            factory.setConfigurationURL(new File(dir, "testDigesterConfiguration2.xml").toURL());
            CompositeConfiguration cc = (CompositeConfiguration) factory.getConfiguration();
            PropertiesConfiguration config = null;
            for (int i = 0; config == null; i++)
            {
                if (cc.getConfiguration(i) instanceof PropertiesConfiguration)
                {
                    config = (PropertiesConfiguration) cc.getConfiguration(i);
                }
            }

            config.setProperty("test", "yes");
            config.save(file.getName());
            assertTrue(file.exists());
            config = new PropertiesConfiguration();
            config.setFile(file);
            config.load();

            assertEquals("yes", config.getProperty("test"));
            assertEquals("masterOfPost", config.getProperty("mail.account.user"));
        }
        finally
        {
            if (file.exists())
            {
                assertTrue("File could not be deleted", file.delete());
            }
        }
    }
    
    /**
     * Tests if invalid URLs cause an exception.
     */
    public void testSaveInvalidURL() throws Exception
    {
        FileConfiguration config = new PropertiesConfiguration();
        try
        {
            config.save(new URL("http://jakarta.apache.org"));
            fail("Should throw a ConfigurationException!");
        }
        catch (ConfigurationException cex)
        {
            //fine
        }

        try
        {
            config.save("http://www.apache.org");
            fail("Should throw a ConfigurationException!");
        }
        catch (ConfigurationException cex)
        {
            //fine
        }
    }
}