
/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI 
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

package com.vividsolutions.jump.io;

import java.io.*;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import com.vividsolutions.jump.feature.*;
import com.vividsolutions.jump.io.datasource.DataSource;


/**
 * WKTReader is a {@link JUMPReader} specialized to read WTK (Well Known Text) files.
 * 
 * <p>
 * DataProperties for the JUMPReader load(DataProperties)
 * interface:<br>
 * </p>
 *
 * <table style="border-collapse: collapse;" summary="">
 *   <tr>
 *     <th style="border: 1px solid #999; padding: 4px;">Parameter</th>
 *     <th style="border: 1px solid #999; padding: 4px;">Meaning</th>
 *   </tr>
 *   <tr>
 *     <td style="border: 1px solid #999; padding: 4px;">File or DefaultValue</td>
 *     <td style="border: 1px solid #999; padding: 4px;">File name for the input WKT file</td>
 *   </tr>
 *   <tr>
 *     <td style="border: 1px solid #999; padding: 4px;">CompressedFile</td>
 *     <td style="border: 1px solid #999; padding: 4px;">File name (a .zip or .gz) with a .jml/.xml/.gml inside
 *         (specified by File)</td>
 *   </tr>
 * </table> <br>
 *
 */
public class WKTReader extends AbstractJUMPReader {

    private GeometryFactory geometryFactory = new GeometryFactory();
    private org.locationtech.jts.io.WKTReader wktReader = new org.locationtech.jts.io.WKTReader(geometryFactory);

    /**constructor**/
    public WKTReader() {
    }

    /**
     * Main function -read in a file containing a list of WKT geometries
     * @param dp 'InputFile' or 'DefaultValue' to specify where the WKT file is.
     */
    public FeatureCollection read(DriverProperties dp) throws Exception {
        FeatureCollection fc;

        String inputFname;
        boolean isCompressed;
        Reader fileReader = null;

        isCompressed = (dp.getProperty(DataSource.COMPRESSED_KEY) != null);

        inputFname = dp.getProperty(DataSource.FILE_KEY);

        if (inputFname == null) {
            inputFname = dp.getProperty(DriverProperties.DEFAULT_VALUE_KEY);
        }

        if (inputFname == null) {
            throw new IllegalParametersException(
                "call to WKTReader.read() has DataProperties w/o a InputFile specified");
        }

        try {
            if (isCompressed) {
                fileReader = new InputStreamReader(CompressedFile.openFile(
                            inputFname, dp.getProperty(DataSource.COMPRESSED_KEY)));
            } else {
                fileReader = new FileReader(inputFname);
            }

            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                fc = read(bufferedReader);
            }
        } finally {
            if (fileReader != null) fileReader.close();
        }

        return fc;
    }

    /**
     * Reads in the actual WKT geometries
     * @param reader where to read the geometries from
     */
    public FeatureCollection read(Reader reader) throws Exception {

        FeatureSchema featureSchema = new FeatureSchema();
        featureSchema.addAttribute("Geometry", AttributeType.GEOMETRY);

        FeatureCollection featureCollection = new FeatureDataset(featureSchema);

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            while (!isAtEndOfFile(bufferedReader)) {
                featureCollection.add(nextFeature(bufferedReader, featureSchema));
            }
        }

        return featureCollection;
    }

    /**
     * returns true if at the end of the file.
     */
    private boolean isAtEndOfFile(BufferedReader bufferedReader)
                throws IOException, ParseException {
        bufferedReader.mark(1000);

        try {
            StreamTokenizer tokenizer = new StreamTokenizer(bufferedReader);
            int type = tokenizer.nextToken();

            if (type == StreamTokenizer.TT_EOF) {
                return true;
            }

            if (type == StreamTokenizer.TT_WORD) {
                return false;
            }

            throw new ParseException(
                "Expected word or end-of-file but encountered StreamTokenizer type " +
                type);
        } finally {
            bufferedReader.reset();
        }
    }

    /**
     * Reads 1 feature
     */
    private Feature nextFeature(Reader reader, FeatureSchema featureSchema)
                throws ParseException {

        Feature feature = new BasicFeature(featureSchema);
        feature.setGeometry(wktReader.read(reader));

        return feature;
    }
}
