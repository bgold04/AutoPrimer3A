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
package com.github.autoprimer3A;
import com.github.autoprimer3A.GetGeneCoordinates;
import static com.github.autoprimer3A.GetGeneCoordinates.conn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.sql.Connection;
import java.util.Collection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.lang.Object;
import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk>, with edits by Bert Gold <bgold04@gmail.com>
 */
public class GetUcscGeneCoordinates extends GetGeneCoordinates {
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", 
            "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", 
            "exonEnds", "strand"));   
    public String name;
    public String id;
    public String chrom;
    public Integer txStart;
    public Integer txEnd;
    public String ChromStart;
    public String ChromEnd;
    public String chromSet;
    public Object gene;
    public Object genes;
    public Integer cdsStart;
    public Integer cdsEnd;
    public String exonCount;
    public String exon;
    public String exonStarts;
    public String exonEnds;        
    public String ex;
    public String j;
    public Object geneDetails;
    public String strand;
    public String name2;        
    public String build;
    public String chromosome;
    public Integer TotalExons;
    public Integer end;
    public Integer start;
    public String symbol;
    public String db;
    public String sql;
    public String qu;
    public String qu1;
    public String query;
    public String query1;
    public String query2;
    public Object statement;
    public Object st;    
    public Object ResultSet;
    public String stmt10;
    public Object rs;
    public Object rs2;
    public String f;
    public Object fieldsToRetrieve;
    public String geneSymbol;
    public Object Cursor;
    public Object Transcripts;
    
    public class GetGeneFromSymbolException extends Exception{
        public GetGeneFromSymbolException() { super(); }
        public GetGeneFromSymbolException(String message) { super(message); }
        public GetGeneFromSymbolException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromSymbolException(Throwable cause) { super(cause); }
}    
    public class GetGeneExonException extends Exception{
        public GetGeneExonException() { super(); }
        public GetGeneExonException(String message) { super(message); }
        public GetGeneExonException(String message, Throwable cause) { super(message, cause); }
        public GetGeneExonException(Throwable cause) { super(cause); }
}
    public class AddAllTransExcep extends Exception{
        public AddAllTransExcep() { super(); }
        public AddAllTransExcep(String message) { super(message); }
        public AddAllTransExcep(String message, Throwable cause) { super(message, cause); }
        public AddAllTransExcep(Throwable cause) { super(cause); }
}
public class GetGeneFromIDException extends Exception{
        public GetGeneFromIDException() { super(); }
        public GetGeneFromIDException(String message) { super(message); }
        public GetGeneFromIDException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromIDException(Throwable cause) { super(cause); }
}    
    public class Id {
    public String id;   
    public String getid() {        
        return id;
    }
    }
    public class GeneSymbol {
    public String geneSymbol;   
    public String getgeneSymbol() {        
        return geneSymbol;
    }
    }
    public class Name {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String name, String id) {
        this.name = id;
    }
    }        
    public class Name2 {
    public String name2;   
    public String getName2() {        
        return name2;
    }
    public void setName2(String name2, String symbol) {
        this.name2 = symbol;
    }
    }        
    public class Symbol {
    public String symbol;   
    public String getSymbol() {        
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    }  
    public class Build {
    public String build;   
    public String getBuild() {        
        return build;
    }
    public void setBuild(String build) {
        this.build = symbol;
    }
    } 
public class Db {
    public String db;   
    public String getdb() {        
        return db;
    }
    public void setBuild(String build) {
        this.db = db;
    }
    }    
    public void setgeneSymbol(String geneSymbol, String symbol) {
        this.geneSymbol = symbol;
    }
    public boolean next() {
            throw new IllegalStateException
        ("next() exception problem");
        }
    public String getString(String rs) {
            throw new 
        ArrayIndexOutOfBoundsException
        ("getString exception problem");
        }      
    public void readDataBase()
        throws 
        com.mysql.cj.jdbc.exceptions.CommunicationsException{
        Connection conn = null;
        try  {//code that may throw an exception    
conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");           
        }
catch(SQLException ex){
{
{
        if (ex instanceof SQLException) {         
                ex.printStackTrace(System.err);
                System.err.println(((SQLException)ex).getSQLState() +
                        "SQLState: ");
                System.err.println("Error Code: " +
                    ((SQLException)ex).getErrorCode());
                System.err.println("Message: " + ex.getMessage());               
                }
            }
        }
    }
} 
public class getTranscriptsFromResultSet {
    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(java.sql.ResultSet rs, String symbol) throws SQLException, GetGeneExonException {
        ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()){
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : fields){
                geneCoords.put(f, rs.getString(f));
            }
            geneCoords.put("name2", symbol);
            genes.add(geneCoords);
        }
        ArrayList<GeneDetails> TranscriptsToReturn = new ArrayList<>();
        for (HashMap<String, String> gene : genes){
            GeneDetails GeneDetails = GeneHashToGeneDetails(gene);
            TranscriptsToReturn.add(GeneDetails);
        }
        return TranscriptsToReturn;
    }
            private GeneDetails GeneHashToGeneDetails(HashMap<String, String> gene) {
                throw new UnsupportedOperationException("Method Creation to support GeneHashToGeneDetails is inadquate"); //To change body of generated methods, choose Tools | Templates.
            }
public class getGeneFromSymbol {     
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneFromSymbolException {
        PreparedStatement ps = conn.prepareStatement(query);
            query = ("SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE " + "geneSymbol='" + symbol +"'");         
            ResultSet rs2  = (ResultSet) ps.executeQuery(query);     
        String fieldsToRetrieve = String.join(", ", fields );          
                  while (rs2.next()){                                
            query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name='" + rs2.getString("kgID") + "'");
     PreparedStatement stmt =  conn.prepareStatement(query);
     ResultSet rs = (ResultSet) stmt.executeQuery(query);
     class transcripts {         
        public ArrayList<GeneDetails> transcripts = new ArrayList<>(); 
         private void addAll(Object transcriptsFromResultSet) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }       
        private transcripts() throws SQLException {                       
    System.out.println("transcrips is having difficulty obtaining addAll(Object transcriptsFromResultSet");        
     transcripts.addAll(transcripts);                
    }         
    }
                  }
        return getGeneFromSymbol(symbol, build, db);
    }
}
//@Override    
public class getGeneFromID {    
//public void getGeneFromID() {    
    public ArrayList<GeneDetails> getGeneFromID(Symbol id, Symbol build, Symbol db) throws SQLException, GetGeneFromIDException {
        String fieldsToRetrieve = String.join(", ", fields);
        query = "SELECT " + fieldsToRetrieve + 
                " FROM " + build + "." + db + " WHERE name='"+ id + "'";
        PreparedStatement ps =  conn.prepareStatement(query);
        rs  = ps.executeQuery(query); 
        query = "SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE kgID='" + id +"'";
        ps =  conn.prepareStatement(query);
        ResultSet rs  = (ResultSet) ps.executeQuery(query); 
        String symbol = new String();
        while (rs.next()){
            query = "geneSymbol";
        rs = (ResultSet) ps.executeQuery(query);
        }     
        return getTranscriptsFromResultSet(rs, symbol);
    }
        private ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, String symbol) {
                throw new UnsupportedOperationException("Fault on returning in GetGeneFromID"); //To change body of generated methods, choose Tools | Templates.
            }
    }
    }    
    }
    
