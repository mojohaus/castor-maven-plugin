package org.apache.maven.plugins.castor;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class CodeGeneratorMojoTest
    extends TestCase
{

    CodeGeneratorMojo codeGeneratorMojo;

    public void setUp()
    {
        codeGeneratorMojo = new CodeGeneratorMojo();
        codeGeneratorMojo.setProject(new MavenProject(new Model()));
    }

    public void tearDown()
    {
        codeGeneratorMojo = null;
    }

    public void testExecute()
        throws MojoExecutionException
    {
        File aClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/A.java" );
        File aDescriptorClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/ADescriptor.java" );
        File timestamp = getTimeStampFile();
        if ( timestamp.exists() )
        {
            timestamp.delete();
        }
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        codeGeneratorMojo.setDest( "src/test/java" );
        codeGeneratorMojo.setTstamp( "src/test/resources/timestamp" );
        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( "src/test/resources/mapping.xml" );
        codeGeneratorMojo.execute();
        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        if ( timestamp.exists() )
        {
            timestamp.delete();
        }
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
    }

    private File getTimeStampFile()
    {
        return new File( "src/test/resources/timestamp/mapping.xml" );
    }

    public void testCreateTimeStamp()
        throws MojoExecutionException
    {
        File timeStampFolder = new File( "src/test/resources/timestamp" );
        File timeStampFile = getTimeStampFile();
        if ( !timeStampFolder.exists() )
        {
            timeStampFolder.mkdirs();
        }
        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }

        File aClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/A.java" );
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }

        File aDescriptorClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/ADescriptor.java" );
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }

        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        codeGeneratorMojo.setDest( "src/test/java" );
        codeGeneratorMojo.setTstamp( "src/test/resources/timestamp" );
        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( "src/test/resources/mapping.xml" );
        codeGeneratorMojo.execute();
        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }

        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
    }

    public void testCreateTimeStampFolder()
        throws MojoExecutionException
    {
        File timeStampFolder = new File( "src/test/resources/timestamp" );
        File timeStampFile = getTimeStampFile();
        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }
        if ( timeStampFolder.exists() )
        {
            timeStampFolder.delete();
        }

        File aClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/A.java" );
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }

        File aDescriptorClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/ADescriptor.java" );
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }

        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        codeGeneratorMojo.setDest( "src/test/java" );
        codeGeneratorMojo.setTstamp( "src/test/resources/timestamp" );
        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( "src/test/resources/mapping.xml" );
        codeGeneratorMojo.execute();
        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }

        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
    }

    // timestamp exist but not updated
    public void testCreateTimeStampOld()
        throws MojoExecutionException, IOException
    {
        File timeStampFolder = new File( "src/test/resources/timestamp" );
        File timeStampFile = getTimeStampFile();
        if ( !timeStampFolder.exists() )
        {
            timeStampFolder.mkdirs();
        }
        if ( !timeStampFile.exists() )
        {
            File sourcefile = new File( "src/test/resources/mapping.xml" );
            timeStampFile.setLastModified( sourcefile.lastModified() - 1 );
        }

        File aClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/A.java" );
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }

        File aDescriptorClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/ADescriptor.java" );
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }

        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        codeGeneratorMojo.setDest( "src/test/java" );
        codeGeneratorMojo.setTstamp( "src/test/resources/timestamp" );
        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( "src/test/resources/mapping.xml" );
        codeGeneratorMojo.execute();
        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }

        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
    }

    public void testCreateTimeStampLatest()
        throws MojoExecutionException, IOException
    {
        File timeStampFolder = new File( "src/test/resources/timestamp" );
        File timeStampFile = getTimeStampFile();
        if ( !timeStampFolder.exists() )
        {
            timeStampFolder.mkdirs();
        }
        if ( !timeStampFile.exists() )
        {
            File timestamp = getTimeStampFile();
            FileUtils.touch( timestamp );
            File sourcefile = new File( "src/test/resources/mapping.xml" );
            timeStampFile.setLastModified( sourcefile.lastModified() + 1 );
        }

        File aClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/A.java" );
        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }

        File aDescriptorClassFile = new File( "src/test/java/org/apache/maven/plugins/castor/ADescriptor.java" );
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }

        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        codeGeneratorMojo.setDest( "src/test/java" );
        codeGeneratorMojo.setTstamp( "src/test/resources/timestamp" );
        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( "src/test/resources/mapping.xml" );
        codeGeneratorMojo.execute();
        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

        if ( timeStampFile.exists() )
        {
            timeStampFile.delete();
        }

        if ( aClassFile.exists() )
        {
            aClassFile.delete();
        }
        if ( aDescriptorClassFile.exists() )
        {
            aDescriptorClassFile.delete();
        }
    }

    public void testDestProperty()
    {
        codeGeneratorMojo.setDest( "testString" );
        assertEquals( "testString", codeGeneratorMojo.getDest());
    }

    public void testTStampProperty()
    {
        codeGeneratorMojo.setTstamp( "testString" );
        assertEquals( "testString", codeGeneratorMojo.getTstamp());
    }

    public void testSchemaProperty()
    {
        codeGeneratorMojo.setSchema( "teststring" );
        assertEquals( "teststring", codeGeneratorMojo.getSchema() );
    }

    public void testPackagingProperty()
    {
        codeGeneratorMojo.setPackaging( "teststring" );
        assertEquals( "teststring", codeGeneratorMojo.getPackaging() );
    }

    public void testTypesProperty()
    {
        codeGeneratorMojo.setTypes( "teststring" );
        assertEquals( "teststring", codeGeneratorMojo.getTypes() );
    }

    public void testMarshalProperty()
    {
        codeGeneratorMojo.setMarshal( true );
        assertTrue( codeGeneratorMojo.getMarshal() );
    }

}
