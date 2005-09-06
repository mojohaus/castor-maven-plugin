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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.commons.io.FileUtils;

import org.exolab.castor.builder.SourceGenerator;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/**
 * @goal generate
 * @phase generate-sources
 */
public class CodeGeneratorMojo
    extends AbstractMojo
{

    private static final String TIMESTAMP = "/tstamp.log";


    /**
     * @parameter
     * @required
     */
    private String dest;

    /**
     * @parameter
     * @required
     */
    private String tstamp;

    /**
     * @parameter
     * @required
     */
    private String schema;

    /**
     * @parameter
     */
    private String packaging;

    /**
     * @parameter
     */
    private String types;

    /**
     * @parameter default-value=true
     */
    private boolean marshal;

    public String getDest()
    {
        return ( destHasNoValue() ? "" : dest.trim() );
    }

    public void setDest( String dest )
    {
        this.dest = dest;
    }

    public boolean destHasNoValue()
    {
        return ( null == dest ) || ( "".equals( dest ) );
    }

    public String getTstamp()
    {
        return ( tstampHasNoValue() ? "" : tstamp.trim() + TIMESTAMP );
    }

    public String getTstampDirectory()
    {
        return ( tstampHasNoValue() ? "" : tstamp.trim() );
    }

    public void setTstamp( String tstamp )
    {
        this.tstamp = tstamp;
    }

    public boolean tstampHasNoValue()
    {
        return ( null == tstamp ) || ( "".equals( tstamp ) );
    }

    public String getSchema()
    {
        return ( schemaHasNoValue() ? "" : schema.trim() );
    }

    public void setSchema( String schema )
    {
        this.schema = schema;
    }

    public boolean schemaHasNoValue()
    {
        return ( null == schema ) || ( "".equals( schema ) );
    }

    public String getPackaging()
    {
        return ( packagingHasNoValue() ? "" : packaging.trim() );
    }

    public void setPackaging( String packaging )
    {
        this.packaging = packaging;
    }

    public boolean packagingHasNoValue()
    {
        return ( null == packaging ) || ( "".equals( packaging ) );
    }

    public String getTypes()
    {
        return ( typesHasNoValue() ? "" : types.trim() );
    }

    public void setTypes( String types )
    {
        this.types = types;
    }

    public boolean typesHasNoValue()
    {
        return ( null == types ) || ( "".equals( types ) );
    }

    public boolean getMarshal()
    {
        return marshal;
    }

    public void setMarshal( boolean marshal )
    {
        this.marshal = marshal;
    }

    public void execute()
        throws MojoExecutionException
    {
        File timestampFile = new File( getTstamp() );
        File schemaFile = new File( getSchema() );
        if ( ( !timestampFile.exists() ) ||
             ( timestampFile.exists() && !FileUtils.isFileNewer( timestampFile, schemaFile ) ) )
        {
            try
            {
                File tstampDir = new File( getTstampDirectory() );
                FileUtils.forceMkdir( tstampDir );
                FileUtils.touch( timestampFile );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( e.getMessage() );
            }
            ArrayList a = new ArrayList();
            if ( !schemaHasNoValue() )
            {
                a.add( "-i" + getSchema() );
            }
            a.add( "-f" );
            if ( !packagingHasNoValue() )
            {
                a.add( "-package" + getPackaging() );
            }
            if ( !typesHasNoValue() )
            {
                a.add( "-types" + getTypes() );
            }
            if ( marshal )
            {
                a.add( "-nomarshall" );
            }
            if ( !destHasNoValue() )
            {
                a.add( "-dest" + getDest() );
            }
            SourceGenerator sourceGenerator = new SourceGenerator();
            String args[] = new String[a.size()];
            for ( int i = 0; i < a.size(); i++ )
            {
                args[i] = ( String ) a.get( i );
            }
            sourceGenerator.main( args );
        }
        else
        {
            System.out.println( "Schema is up to date. Did not generate source files. Delete "
                                + getTstamp() +
                                " if you want to force source generation." );
        }
    }
}
