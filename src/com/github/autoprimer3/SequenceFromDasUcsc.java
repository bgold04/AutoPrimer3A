/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.autoprimer3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk>
 */
public class SequenceFromDasUcsc {
    HashMap<String, String> buildToMapMaster = new HashMap<>();//maps build name to url e.g. hg19 => http://genome.cse.ucsc.edu:80/cgi-bin/das/hg19
    SequenceFromDasUcsc(){//get build names and DAS urls
        try{
            SAXReader reader = new SAXReader();
            //URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/dsn");//usa    
            URL url = new URL("http://genome-euro.ucsc.edu/cgi-bin/das/dsn");    
            Document dasXml;
            dasXml  = reader.read(url);
            Element root = dasXml.getRootElement();
            for ( Iterator i = root.elementIterator( "DSN" ); i.hasNext(); ) {
                Element dsn = (Element) i.next();
                Element source = dsn.element("SOURCE");
                Attribute build = source.attribute("id");
                Element mapmaster = dsn.element("MAPMASTER");
                buildToMapMaster.put(build.getValue(), mapmaster.getText());
            }
        }catch (DocumentException|MalformedURLException ex){
            //TO DO - handle (throw) this error properly
            ex.printStackTrace();
        }
    }
    //uses 0-based coordinates for compatibility with gene tables, bed etc., although DAS uses 1-based
    public String retrieveSequence(String build, String chrom, Integer start, Integer end){
        if (! buildToMapMaster.containsKey(build)){
            return null;
        }else{
            try{
                StringBuilder dna = new StringBuilder();
                URL genomeUrl = new URL(buildToMapMaster.get(build) + 
                        "/dna?segment="+chrom + ":" + (start + 1) + "," + end);
                SAXReader reader = new SAXReader();
                Document dasXml;
                dasXml  = reader.read(genomeUrl);
                Element root = dasXml.getRootElement();
                for ( Iterator i = root.elementIterator( "SEQUENCE" ); i.hasNext(); ) {
                    Element dsn = (Element) i.next();
                    Element seq = dsn.element("DNA");
                    String text = seq.getText().replaceAll("\n", "");
                    dna.append(text);
                    //dna.append(seq.getText());
                }
                return dna.toString();
            }catch(DocumentException|MalformedURLException ex){
                //TO DO - handle (throw) this error properly
                ex.printStackTrace();
                return null;
            }
            
        }
        
    }
}
