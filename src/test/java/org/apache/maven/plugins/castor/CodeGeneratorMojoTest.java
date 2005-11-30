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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusTestCase;

public class CodeGeneratorMojoTest
    extends PlexusTestCase
{

    private static final String TIMESTAMP_DIR = getBasedir() + "/target/test/resources/timestamp";

    private static final String GENERATED_DIR = getBasedir() + "/target/test/generated";

    private static final String MAPPING_XSD = getBasedir() + "/src/test/resources/mapping.xml";

    CodeGeneratorMojo codeGeneratorMojo;

    private File aClassFile;

    private File aDescriptorClassFile;

    public void setUp()
        throws IOException
    {
        FileUtils.deleteDirectory( new File( GENERATED_DIR ) );
        FileUtils.deleteDirectory( new File( TIMESTAMP_DIR ) );

        aClassFile = new File( GENERATED_DIR, "org/apache/maven/plugins/castor/A.java" );
        aDescriptorClassFile = new File( GENERATED_DIR, "org/apache/maven/plugins/castor/ADescriptor.java" );

        codeGeneratorMojo = new CodeGeneratorMojo();
        codeGeneratorMojo.setProject( new MavenProject( new Model() ) );
        codeGeneratorMojo.setDest( GENERATED_DIR );
        codeGeneratorMojo.setTstamp( TIMESTAMP_DIR );
    }

    public void tearDown()
        throws IOException
    {
        codeGeneratorMojo = null;
        FileUtils.deleteDirectory( new File( GENERATED_DIR ) );
        FileUtils.deleteDirectory( new File( TIMESTAMP_DIR ) );
    }

    public void testExecute()
        throws MojoExecutionException
    {

        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( MAPPING_XSD );
        codeGeneratorMojo.execute();

        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );

    }

    public void testEmptyPackage()
        throws MojoExecutionException
    {

        codeGeneratorMojo.setSchema( getPathTo( "src/test/resources/vacuumd-configuration.xsd" ) );
        codeGeneratorMojo.setProperties( getPathTo( "src/test/resources/castorbuilder.properties" ) );
        codeGeneratorMojo.execute();

        assertFalse( new File( GENERATED_DIR, "Actions.java" ).exists() );
    }

    private File getTimeStampFile()
    {
        return new File( TIMESTAMP_DIR, "mapping.xml" );
    }

    public void testCreateTimeStamp()
        throws MojoExecutionException
    {
        File timeStampFile = getTimeStampFile();

        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( MAPPING_XSD );
        codeGeneratorMojo.execute();

        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

    }

    public void testCreateTimeStampFolder()
        throws MojoExecutionException
    {
        File timeStampFile = getTimeStampFile();

        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( MAPPING_XSD );
        codeGeneratorMojo.execute();

        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

    }

    // timestamp exist but not updated
    public void testCreateTimeStampOld()
        throws MojoExecutionException, IOException
    {
        File timeStampFile = createTimeStampWithTime( timestampOf( MAPPING_XSD ) - 1 );

        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( MAPPING_XSD );
        codeGeneratorMojo.execute();

        assertTrue( aClassFile.exists() );
        assertTrue( aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

    }

    private File createTimeStampWithTime( long time )
        throws IOException
    {
        File timeStampFolder = new File( TIMESTAMP_DIR );
        File timeStampFile = getTimeStampFile();
        if ( !timeStampFolder.exists() )
        {
            timeStampFolder.mkdirs();
        }
        if ( !timeStampFile.exists() )
        {
            FileUtils.touch( timeStampFile );
            timeStampFile.setLastModified( time );
        }
        return timeStampFile;
    }

    public void testCreateTimeStampLatest()
        throws MojoExecutionException, IOException
    {
        File timeStampFile = createTimeStampWithTime( timestampOf( MAPPING_XSD ) + 1 );

        codeGeneratorMojo.setPackaging( "org.apache.maven.plugins.castor" );
        codeGeneratorMojo.setSchema( MAPPING_XSD );
        codeGeneratorMojo.execute();

        assertTrue( !aClassFile.exists() );
        assertTrue( !aDescriptorClassFile.exists() );
        assertTrue( timeStampFile.exists() );

    }

    private long timestampOf( String file )
    {
        File sourcefile = new File( file );
        long time = sourcefile.lastModified();
        return time;
    }

    public void testDestProperty()
    {
        codeGeneratorMojo.setDest( "testString" );
        assertEquals( "testString", codeGeneratorMojo.getDest() );
    }

    public void testTStampProperty()
    {
        codeGeneratorMojo.setTstamp( "testString" );
        assertEquals( "testString", codeGeneratorMojo.getTstamp() );
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

    private String getPathTo( String relativePath )
    {
        return getBasedir() + '/' + relativePath;
    }

}
