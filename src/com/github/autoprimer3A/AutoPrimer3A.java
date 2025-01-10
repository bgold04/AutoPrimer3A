/*things to add:
    server choice
    
*/

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.lingala.zip4j.exception.ZipException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author David Parray, with Bert Gold, PhD editor
 */
public class AutoPrimer3A extends Application implements Initializable{
    
    String VERSION = "3A";

public String build;    
public Integer cdsEnd;
public Integer cdsStart;
public String chrom;
public Integer ChromEnd;
public String chromosome;
public String Chromosome;
public String chromSet;
public Integer ChromStart;
public String db;
public String e;
public Integer end;
public Integer EndPos;
public String exon;
public String exonCount;
public String exonEnds;     
public String exonStarts;
public String ex;
public Object fieldsToRetrieve;
public String f;
public String gene;
public Object geneDetails;
public String genes;
public String genome;
public Object GetGeneCoordinates;
public String id;
public String name;
public Object ps;
public String query;
public Object rs;
public String snpDb;
public Object sql;
public Integer start;
public Integer StartPos;  
public Object statement;
public String Statement;
public String strand;
public Object st;    
public String symbol;        
public Integer TotalExons;    
public Integer txStart;
public Integer txEnd;
public Object getTranscriptsFromResultSet;   
public String t;
public String regions;
public Object document;
public Object node;

/*
* @param args the command line arguments
*/
    
    @FXML
    AnchorPane mainPane;
    //menus
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem refreshMenuItem;
    @FXML
    MenuItem quitMenuItem;
    @FXML
    MenuItem helpMenuItem;
    @FXML
    MenuItem aboutMenuItem;
    //tabs
    @FXML
    TabPane mainTabPane;
    @FXML
    Tab genesTab;
    @FXML
    Tab primerTab;
    @FXML
    Tab coordTab;
    //Genes tab components
    @FXML 
    Button runButton;
    @FXML
    Button cancelButton;
    @FXML
    Button refreshButton;
    @FXML
    ChoiceBox genomeChoiceBox;
    @FXML
    ChoiceBox databaseChoiceBox;
    @FXML
    ChoiceBox snpsChoiceBox;
    @FXML
    ChoiceBox designToChoiceBox;
    @FXML
    TextField minDistanceTextField;
    @FXML
    TextField flankingRegionsTextField;
    @FXML
    TextField genesTextField;
    @FXML
    ProgressIndicator progressIndicator = new ProgressIndicator();
    @FXML
    Label progressLabel;
    
    //Coordinates tab components
    @FXML
    TextArea regionsTextArea;
    @FXML
    ChoiceBox genomeChoiceBox2;
    @FXML
    ChoiceBox snpsChoiceBox2;
    @FXML
    TextField minDistanceTextField2;
    @FXML
    TextField flankingRegionsTextField2;
    @FXML
    ProgressIndicator progressIndicator2 = new ProgressIndicator();
    @FXML
    Label progressLabel2;
    @FXML 
    Button runButton2;
    @FXML
    Button cancelButton2;
    @FXML
    Button loadFileButton;
    @FXML
    Button clearButton;
    @FXML
    Label regionsLabel;
    @FXML
    CheckBox useRegionNamesCheckBox;
    
    
    //Primer3 Settings tab components
    @FXML
    TextField minSizeTextField;
    @FXML
    TextField optSizeTextField;
    @FXML
    TextField maxSizeTextField;
    @FXML
    TextField maxDiffTextField;
    @FXML
    TextField sizeRangeTextField;
    @FXML
    TextField maxMisprimeTextField;
    @FXML
    TextField minTmTextField;
    @FXML
    TextField optTmTextField;
    @FXML
    TextField maxTmTextField;
    @FXML
    TextField splitRegionsTextField;
    @FXML
    ChoiceBox misprimingLibraryChoiceBox;
    @FXML
    Button resetValuesButton;
    @FXML
    CheckBox autoSelectMisprimingLibraryCheckBox;
    
   
    public class GetUcscBuildsAndTables {

    // Other methods and fields...
        
private String buildToMapMaster; // Cache the master map value

public String getBuildToMapMaster() {
    return buildToMapMaster;
}    
public void fetchBuildToMapMaster(Document document) {
    try {
        // Assume the master map is stored in a specific XML node
        NodeList nodes = document.getElementsByTagName("buildMaster");
        if (nodes.getLength() > 0) {
            Node node = nodes.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                buildToMapMaster = node.getTextContent().trim();
            }
        }
    } 
    catch (Exception e) {
        e.printStackTrace();
    }
}        
   public LinkedHashMap<String, String> getBuildToDescription() {
    LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();

    try {
        // Assuming you already have an XML Document from UCSC
    
        Document document = getUcscDocument(); // Method to fetch/return the UCSC document
        
        NodeList builds = document.getElementsByTagName("build");

        for (int i = 0; i < builds.getLength(); i++) {
            Node buildNode = builds.item(i);
            if (buildNode.getNodeType() == Node.ELEMENT_NODE) {
                Element buildElement = (Element) buildNode;
                String buildName = buildElement.getAttribute("name");
                String description = buildElement.getTextContent().trim();

                if (!buildName.isEmpty() && !description.isEmpty()) {
                    buildToDescription.put(buildName, description);
                }
            }
        }
    } 
    catch (Exception e) {
        
        e.printStackTrace();
    }

    return buildToDescription;
}
     
        

public LinkedHashSet<String> extractTablesFromDocument(Document document) {
        LinkedHashSet<String> tables = new LinkedHashSet<>();
        try {
            // Assuming the Document represents an XML structure
    
            NodeList tableNodes = document.getElementsByTagName("table");
            for (int i = 0; i < tableNodes.getLength(); i++) {
                Node tableNode = tableNodes.item(i);
                if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tableElement = (Element) tableNode;
                    String tableName = tableElement.getTextContent().trim();
                    if (!tableName.isEmpty()) {
                        tables.add(tableName);
                    }
                }
            }
        } 
        catch (Exception e) {
            
            e.printStackTrace();
        }
        return tables;
    }
}
    Boolean CANRUN = false;
    final BuildToMisprimingLibrary buildToMisprime = new BuildToMisprimingLibrary();
    Boolean autoSelectMisprime = true;
    final GetUcscBuildsAndTables buildsAndTables = new GetUcscBuildsAndTables();

    File primer3ex; 
    File thermoConfig;
    String defaultSizeRange = "150-250 100-300 301-400 401-500 501-600 "
                + "601-700 701-850 851-1000 1000-2000";
    HashMap<TextField, String> defaultPrimer3Values = new HashMap<>();
    String serverUrl = "http://genome.ucsc.edu"; 
    //String serverUrl = "http://genome-euro.ucsc.edu"; 
    
    File configDirectory;
    AutoPrimer3AConfig ap3AConfig;
    File misprimeDir;
    HashSet<String> checkedAlready = new HashSet<>();
//we use this hashmap to ensure we only check tables for genomes once per session
    
    int MAX_GENES_PER_DESIGN = 10;
    int MAX_LINES_PER_DESIGN = 100;//max lines for coordinates design
    int MAX_REGION_SIZE = 100000;//longest permissible region size = 100 kb
    
  private void connectToUcsc() {
        progressIndicator.setProgress(-1);
        final Task<LinkedHashMap<String, String>> getBuildsTask = 
                new Task<LinkedHashMap<String, String>>() {
            @Override
            protected LinkedHashMap<String, String> call() 
                    throws DocumentException, MalformedURLException{

                buildsAndTables.connectToUcsc();

                return buildsAndTables.getBuildToDescription();
                
            }
            
        };
  }
    private void checkUcscTables(final String genome) {
    Task<Document> task = createCheckUcscTablesTask(genome);

    // Run the task in a background thread and handle its completion
    task.setOnSucceeded(event -> {
        Document tableDocument = task.getValue();
        // Process the tableDocument (e.g., update UI or store it somewhere)
        LinkedHashSet<String> tables = buildsAndTables.extractTablesFromDocument(tableDocument);
        setTables(tables);
    });

    task.setOnFailed(event -> {
        System.err.println("Failed to fetch UCSC tables for genome: " + genome);
        task.getException().printStackTrace();
    });

    new Thread(task).start(); // Run the task on a background thread
}

private Task<Document> createCheckUcscTablesTask(final String genome) {

    return new Task<Document>() {
        @Override
        protected Document call() throws DocumentException, MalformedURLException {
            System.out.println("Checking tables for " + genome);

            return buildsAndTables.getTableXmlDocument(genome);
        }
    };
}
private void setTables(LinkedHashSet<String> tables) {
    if (tables == null || tables.isEmpty()) {
        databaseChoiceBox.getItems().clear();
        databaseChoiceBox.getItems().add("No tables found - please choose another genome.");
        setCanRun(false);
        databaseChoiceBox.getSelectionModel().selectFirst();
        return;
    }

    LinkedHashSet<String> genes = getGenesFromTables(tables);

    LinkedHashSet<String> snps = getSnpsFromTables(tables);

    databaseChoiceBox.getItems().clear();
    if (genes.isEmpty()) {
        databaseChoiceBox.getItems().add("No gene databases found - please choose another genome.");
        setCanRun(false);
        databaseChoiceBox.getSelectionModel().selectFirst();
    } else {
        databaseChoiceBox.getItems().addAll(genes);
        if (databaseChoiceBox.getItems().contains("refGene")) {
            databaseChoiceBox.getSelectionModel().select("refGene");
        } else {
            databaseChoiceBox.getSelectionModel().selectFirst();
        }
        setCanRun(true);
    }

    snpsChoiceBox.getItems().clear();
    snpsChoiceBox.getItems().add("No");
    snpsChoiceBox.getItems().addAll(snps);
    snpsChoiceBox.getSelectionModel().selectFirst();
}
     private void getBuildTables(final String id, final boolean forceRefresh){
        databaseChoiceBox.getItems().clear();
        snpsChoiceBox.getItems().clear();
        if (ap3AConfig.getBuildToTables().containsKey(id) && !forceRefresh){
            setTables(ap3AConfig.getBuildToTables().get(id));
            if (! checkedAlready.contains(id)){
                checkUcscTables(id);
                checkedAlready.add(id);
            }
            return;
        }
        if (! ap3AConfig.getBuildXmlFile(id).exists()){
            checkedAlready.add(id);
        }
        setLoading(true);
        progressLabel.setText("Getting database information for " + id);

        final Task<Document> getTablesTask = new Task<Document>(){

            @Override
            protected Document call() 
                    throws DocumentException, MalformedURLException, IOException{
                //System.out.println("Called getTablesTask...");
                return ap3AConfig.getBuildXmlDocument(id, forceRefresh);// buildsAndTables.getAvailableTables(id);
            }
        };
  }
  public void refreshDatabase(){
        if (genomeChoiceBox.getSelectionModel().isEmpty()){//implies no connection to UCSC
            connectToUcsc();
        }else{//we've got a connection to UCSC but want to refresh the database info for current build
            final String id = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                getBuildTables(id, true);
        }
  }
 private static void openFile(File f) throws IOException {
    if (f == null) {
        throw new IllegalArgumentException("File cannot be null");
    }

    String command;
    String os = System.getProperty("os.name");

    if (os.contains("Linux")) {
        command = "xdg-open " + f.getAbsolutePath();
    } else if (os.contains("Mac OS X")) {
        command = "open " + f.getAbsolutePath();
    } else if (os.contains("Windows")) {
        command = "cmd /C start \"\" \"" + f.getAbsolutePath() + "\"";
    } else {
        // Unsupported operating system
        throw new UnsupportedOperationException("Unsupported operating system: " + os);
    }

    // Execute the command
    try {
        Runtime.getRuntime().exec(command);
    } catch (IOException e) {
        throw new IOException("Failed to open file: " + f.getAbsolutePath(), e);
    }
}
public void showAbout(Event ev) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        Pane page = loader.load();
        Scene scene = new Scene(page);
        Stage stage = new Stage();
        stage.setScene(scene);

        // Optional: Add stylesheet for consistent styling
        // scene.getStylesheets().add(AutoPrimer3A.class.getResource("autoprimer3A.css").toExternalForm());

        AboutController controller = loader.getController();
        controller.setVersion(VERSION);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        stage.setTitle("About AutoPrimer3A");
        stage.show();
    } catch (IOException ex) {
        // Show error dialog if the "About" window fails to load
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not display about dialog");
        alert.setContentText("Please see exception for details.");

        // Optionally log the exception to the console for debugging
        ex.printStackTrace();

        alert.showAndWait();
    }
}
  private void setCanRun(boolean designable){
        CANRUN = designable;
        runButton.setDisable(!CANRUN);
        cancelButton.setDisable(CANRUN);
        runButton2.setDisable(!CANRUN);
        cancelButton2.setDisable(CANRUN);
    }    
    private void setRunning(boolean running){
        setCanRun(!running);
        refreshButton.setDisable(running);
        refreshMenuItem.setDisable(running);
        genomeChoiceBox.setDisable(running);
        genomeChoiceBox2.setDisable(running);
        databaseChoiceBox.setDisable(running);
        snpsChoiceBox.setDisable(running);
        snpsChoiceBox2.setDisable(running);
        designToChoiceBox.setDisable(running);
        minDistanceTextField.setDisable(running);
        minDistanceTextField2.setDisable(running);
        flankingRegionsTextField.setDisable(running);
        flankingRegionsTextField2.setDisable(running);
        genesTextField.setDisable(running);
        refreshButton.setDisable(running);
        loadFileButton.setDisable(running);
    }
private void setLoading(boolean loading){
        setCanRun(!loading);
        setRunning(loading);
        if (loading){
            progressIndicator.setProgress(-1);
        }else{
            progressIndicator.setProgress(0);
        }
    }  
public void showHelp(){
        try{
            File instructionsPdf = File.createTempFile("autoprimer3_instructions", ".pdf" );
            instructionsPdf.deleteOnExit();
            InputStream inputStream = this.getClass().
                    getResourceAsStream("instructions.pdf");
            OutputStream outputStream = new FileOutputStream(instructionsPdf);
            int read = 0;
            byte[] bytes = new byte[1024];    
            while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
            openFile(instructionsPdf);
        }catch(IOException ex){

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle("Open failed");
    errorAlert.setHeaderText("Could not open instructions PDF");
    errorAlert.setContentText(
        "Exception encountered when attempting to open "
                            + "AutoPrimer3A's help pdf. See below:" +
        ex.getMessage()
    );
    errorAlert.showAndWait();
}
    }
private void displaySizeRangeError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Size Range");
        alert.setHeaderText("Invalid Primer Product Size Range values");
        alert.setContentText("Primer Product Size Range field must be in the format '"
                        + "100-200 200-400' etc.");
        alert.showAndWait();
    }      
  EventHandler<KeyEvent> checkNumeric(){
        return new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("\\d")){
                    ke.consume();
                }
            }
        };
    }
    
    EventHandler<KeyEvent> checkDecimal(){
        return new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d.]")){
                    ke.consume();
                }
            }
        };
    }
    
    EventHandler<KeyEvent> checkRange(){
        return new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d-\\s]")){
                    ke.consume();
                }
            }
        };
    }
    
    private boolean checkSizeRange(TextField field){
        List<String> split = Arrays.asList(field.getText().split("\\s+"));
        for (String s: split){
            if (!s.matches("\\d+-\\d+")){
                return false;
            }
        }
        return true;
    }  
    @Override
    public void start(final Stage primaryStage) {
        try {
            AnchorPane page;
            if (System.getProperty("os.name").equals("Mac OS X")){
                page = (AnchorPane) FXMLLoader.load(
                        com.github.autoprimer3A.AutoPrimer3A.class.
                                getResource("AutoPrimer3Mac.fxml"));  
            }else{
                page = (AnchorPane) FXMLLoader.load(
                        com.github.autoprimer3A.AutoPrimer3A.class.
                                getResource("AutoPrimer3A.fxml"));
            }
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("AutoPrimer3A");
            //scene.getStylesheets().add(com.github.autoprimer3A.AutoPrimer3A.class.
            //        getResource("autoprimer3A.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.getIcons().add(new Image(this.getClass().
                    getResourceAsStream("icon.png")));
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
               @Override
               public void handle(WindowEvent e) {
                  Platform.exit();
                  System.exit(0);
               }
            });
            final KeyCombination macCloseKeyComb = 
                new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
            if (System.getProperty("os.name").equals("Mac OS X")){
               scene.addEventHandler(
                    KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ev){
                        if (macCloseKeyComb.match(ev)){
                            primaryStage.close();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        menuBar.setUseSystemMenuBar(true);
        genesTextField.requestFocus();
        progressLabel2.textProperty().bind(progressLabel.textProperty());
        progressIndicator2.progressProperty().bind(progressIndicator.progressProperty());
        genomeChoiceBox2.itemsProperty().bind(genomeChoiceBox.itemsProperty());
        genomeChoiceBox2.selectionModelProperty().bind(genomeChoiceBox.selectionModelProperty());
        snpsChoiceBox2.itemsProperty().bind(snpsChoiceBox.itemsProperty());
        snpsChoiceBox2.selectionModelProperty().bind(snpsChoiceBox.selectionModelProperty());
        cancelButton2.cancelButtonProperty().bind(cancelButton.cancelButtonProperty());
        cancelButton2.onActionProperty().bind(cancelButton.onActionProperty());
        cancelButton.setCancelButton(true);
        setLoading(true);
       try {
    ap3AConfig = new AutoPrimer3AConfig();
} catch (IOException ex) {
    // Create an error alert
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Config Error");
    alert.setHeaderText("Error Preparing AutoPrimer3A Files");
    alert.setContentText(
        "AutoPrimer3A encountered an error when trying to prepare " +
        "required temporary files. See exception details below:\n\n" + ex.getMessage()
    );
            // Optionally log the exception details somewhere (e.g., console or log file)


    // Show the alert
    alert.showAndWait();
}
        try{
            ap3AConfig.readGenomeXmlFile();
            //ap3AConfig.readTablesXmlFiles();
        }catch (IOException|DocumentException ex){
    // Create an error alert
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Config Error");
    alert.setHeaderText("Error Reading AutoPrimer3A Genome Database");
    alert.setContentText(
        "AutoPrimer3A encountered an error reading local stored "
                    + "genome details - see exception below.:\n\n" + ex.getMessage()
    );
            // Optionally log the exception details somewhere (e.g., console or log file)

    // Show the alert
    alert.showAndWait();
}
        try{
            primer3ex = ap3AConfig.extractP3Executable();
            misprimeDir = ap3AConfig.extractMisprimingLibs();
            thermoConfig = ap3AConfig.extractThermoConfig();
            ap3AConfig.extractTableXml();
        }catch(IOException|ZipException ex){
// Create an error alert
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Config Error");
    alert.setHeaderText("Error Extracting Primer3 Files");
    alert.setContentText(
        "AutoPrimer3A encountered an error while trying to extract"
                    + " the primer3 executable and primer3 config files. "
                    + "AutoPrimer3A will have to exit. If this reoccurs you may"
                    + " need to reinstall AutoPrimer3A."
                    + "See exception below.:\n\n" + ex.getMessage()
    );
            // Optionally log the exception details somewhere (e.g., console or log file)

    // Show the alert
    alert.showAndWait();
}          
        designToChoiceBox.getSelectionModel().selectFirst();
        refreshButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                refreshDatabase();
            }
        });
        
        refreshMenuItem.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                refreshDatabase();
            }
        });
        
        quitMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Platform.exit();
            }
        });
        
        helpMenuItem.setOnAction(new EventHandler(){
            @Override
            public void handle (Event ev){
                showHelp();
            }
        });
        
        aboutMenuItem.setOnAction(new EventHandler(){
            @Override
            public void handle (Event ev){
                showAbout(ev);
            }
        });
        

        genomeChoiceBox.getSelectionModel().selectedIndexProperty().addListener
            (new ChangeListener<Number>(){
            @Override
            public void changed (ObservableValue ov, Number value, final Number new_value){ 
                if (new_value.intValue() >= 0){
                    final String id = (String) genomeChoiceBox.getItems().get(new_value.intValue());
                    genomeChoiceBox.setTooltip(new Tooltip (ap3AConfig.getBuildToDescription().get(id)));
                    if (autoSelectMisprime){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                selectMisprimingLibrary(id);
                            }
                        });
                        
                    }
                    getBuildTables(id, false);
                }
            }
        });
        
        genomeChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    event.consume();
                }else if (event.getCode().isLetterKey()){
                    String c = event.getText().toLowerCase();
                    List items = genomeChoiceBox.getItems();
                    int s = genomeChoiceBox.getSelectionModel().selectedIndexProperty().get();
                    for (int i = s + 1; i < items.size(); i++){
                        if (items.get(i).toString().toLowerCase().startsWith(c)){
                            genomeChoiceBox.getSelectionModel().select(i);
                            return;
                        }
                    }
                    for (int i = 0; i < s; i++){//wrap around
                        if (items.get(i).toString().toLowerCase().startsWith(c)){
                            genomeChoiceBox.getSelectionModel().select(i);
                            return;
                        }
                    }
                }
            }
        });
        genomeChoiceBox2.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    event.consume();
                }else if (event.getCode().isLetterKey()){
                    String c = event.getText().toLowerCase();
                    List items = genomeChoiceBox2.getItems();
                    int s = genomeChoiceBox2.getSelectionModel().selectedIndexProperty().get();
                    for (int i = s + 1; i < items.size(); i++){
                        if (items.get(i).toString().toLowerCase().startsWith(c)){
                            genomeChoiceBox2.getSelectionModel().select(i);
                            return;
                        }
                    }
                    for (int i = 0; i < s; i++){//wrap around
                        if (items.get(i).toString().toLowerCase().startsWith(c)){
                            genomeChoiceBox2.getSelectionModel().select(i);
                            return;
                        }
                    }
                }
            }
        });
    }
         private void resetPrimerSettings(){
        for (TextField f: defaultPrimer3Values.keySet()){
            f.setText(defaultPrimer3Values.get(f));
        }
    }
    private void resetEmptyPrimerSettings(){
        for (TextField f: defaultPrimer3Values.keySet()){
            if (f.getText().isEmpty()){
                f.setText(defaultPrimer3Values.get(f));
            }else if (f.getText().trim().length() < 1){
                f.setText(defaultPrimer3Values.get(f));
            }
        }
        autoSelectMisprimingLibraryCheckBox.selectedProperty().addListener(
                new ChangeListener<Boolean>(){
            @Override
            public void changed (ObservableValue ov, Boolean value, final Boolean newValue){
                autoSelectMisprime = newValue;
                final String id = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (newValue){
                            selectMisprimingLibrary(id);
                        }else{
                            misprimingLibraryChoiceBox.getSelectionModel().select("none");
                        }
                    }
                });
   
            }
        });        
 
        genomeChoiceBox.getItems().clear();
        genomeChoiceBox.getItems().addAll(new ArrayList<>(ap3AConfig.getBuildToDescription().keySet()));
        genomeChoiceBox.getSelectionModel().selectFirst();
        
        misprimingLibraryChoiceBox.getItems().add("none");
        for (File f: misprimeDir.listFiles()){
            misprimingLibraryChoiceBox.getItems().add(f.getName());
        }
        misprimingLibraryChoiceBox.getSelectionModel().selectFirst();
        minDistanceTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        minDistanceTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        flankingRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        flankingRegionsTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        minSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        optSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxDiffTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxMisprimeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        sizeRangeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkRange());
        minTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        optTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        maxTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        splitRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        defaultPrimer3Values.put(minSizeTextField, "18");
        defaultPrimer3Values.put(optSizeTextField, "20");
        defaultPrimer3Values.put(maxSizeTextField, "27");
        defaultPrimer3Values.put(maxDiffTextField, "10");
        defaultPrimer3Values.put(minTmTextField, "57.0");
        defaultPrimer3Values.put(optTmTextField, "59.0");
        defaultPrimer3Values.put(maxTmTextField, "62.0");
        defaultPrimer3Values.put(splitRegionsTextField, "300");
        defaultPrimer3Values.put(maxMisprimeTextField, "12");
        defaultPrimer3Values.put(sizeRangeTextField, defaultSizeRange);
                
        minDistanceTextField2.textProperty().bindBidirectional
                        (minDistanceTextField.textProperty());
        flankingRegionsTextField2.textProperty().bindBidirectional
                    (flankingRegionsTextField.textProperty());       
        
        resetValuesButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
        
               resetPrimerSettings();
            }
        });
        
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>(){
                    @Override
                    public void changed (ObservableValue<? extends Tab> ov, 
                            Tab ot, Tab nt) {
                        if (ot.equals(primerTab)){
                            resetEmptyPrimerSettings();
                        }
                        if (nt.equals(genesTab)){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    genesTextField.requestFocus();
                                    if (!progressLabel.textProperty().isBound()){
                                        progressLabel.setText("");
                                        checkGeneTextField();
                                    }
                                }
                            });
                        }else if (nt.equals(coordTab)){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (!progressLabel.textProperty().isBound()){
                                        progressLabel.setText("");
                                        displayValidRegions();
                                    }
                                }
                            });
                        }
                    }
                }
        );
        
        sizeRangeTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue ){
                if (!newValue){
                    if (!checkSizeRange(sizeRangeTextField)){
                        displaySizeRangeError();
                    }
                }
            }
        });
        
        regionsTextArea.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue ){
                //newValue = newValue.trim();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (progressLabel.textProperty().isBound()){
                            return;
                        }
                        String reg = newValue.replaceAll("(?m)^\\s", "");
                        int regions = reg.split("\\n").length;
                        progressLabel.setText(regions + " lines"); 
                    }
                });
            }
        });
        
        regionsTextArea.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue ){
                if (!newValue){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayValidRegions();
                        }
                    }
                    );
                }
            }
        });

        genesTextField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue ){
                //newValue = newValue.trim();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        checkGeneTextField();
                    }
                });
                
            }
        });
        
        if (ap3AConfig.getBuildToDescription().isEmpty()){
            connectToUcsc();   
        }else{
            setLoading(false);
            checkUcscGenomes();
        }
        Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    genesTextField.requestFocus();
                }
            }
        );
    }
    
    private void checkGeneTextField(){
        progressLabel.setText("");
        if (!genesTextField.getText().matches(".*\\w.*")){
            return;
        }
        String[] genes = genesTextField.getText().split("\\s+");
        if (genes.length > MAX_GENES_PER_DESIGN){
            progressLabel.setText("Warning: " + genes.length + " genes entered,"
                    + " max per design is " + MAX_GENES_PER_DESIGN);
        }else if (genes.length > 0){
            progressLabel.setText(genes.length + " genes entered.");
        }
    }
    
    private void displayValidRegions(){
        if (progressLabel.textProperty().isBound()){
            return;
        }
        int validRegions = 0;
        int invalidRegions = 0;
        String[] lines = regionsTextArea.getText().replaceAll("(?m)^\\s", "").split("\\n");
        if (lines.length < 1){
            return;
        }
        if (lines.length == 1 && lines[0].isEmpty()){
            return;
        }
        
        for (String r: lines){
            if (RegionParser.readRegion(r) == null){
                invalidRegions++;
            }else{
                validRegions++;
            }
        }
        StringBuilder lbl = new StringBuilder(validRegions + " valid regions");
        if (invalidRegions > 0){
            lbl.append(" (").append(invalidRegions).append(" invalid)");
        }
        progressLabel.setText(lbl.toString());
    }
    
    private void selectMisprimingLibrary(String id){
        String stub = id.replaceAll("\\d*$", "");
        String lib = buildToMisprime.getMisprimingLibrary(stub);
        misprimingLibraryChoiceBox.getSelectionModel().select(lib);
    }
    
    
    private void checkUcscGenomes() {
    final Task<Void> task = getGenomesTask();
    new Thread(task).start(); // Example: Execute the task in a new thread
}
    private Task<Void> getGenomesTask() {
    return new Task<Void>() {
        @Override
        protected Void call() throws DocumentException, MalformedURLException {
            System.out.println("Checking genome list.");

            buildsAndTables.connectToUcsc();
            return null;
        }
    };
        getGenomesTask.setOnSucceeded

                (new EventHandler<WorkerStateEvent>(){
            @Override
            
            public void handle (WorkerStateEvent e){
                boolean rewriteConfig = false;
                if (! ap3AConfig.getBuildToDescription().keySet().equals(
                        buildsAndTables.getBuildToDescription().keySet()) &&
                        buildsAndTables.getBuildToDescription() != null){
                    //warn and repopulate genome choicebox
                    // Create an error alert
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Repopulating Genomes");
                    alert.setContentText(
                    "The available genomes have changed. AutoPrimer3A "
                        + "will now repopulate the genome menu."
                        );
System.out.println("Genome list has changed - repopulating genome choice box");
rewriteConfig = true;
ap3AConfig.setBuildToDescription(buildsAndTables.getBuildToDescription());
String currentSel = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
genomeChoiceBox.getItems().clear();
genomeChoiceBox.getItems().addAll(ap3AConfig.getBuildToDescription().keySet());

if (genomeChoiceBox.getItems().contains(currentSel)) {
    genomeChoiceBox.getSelectionModel().select(currentSel);
} else {
    genomeChoiceBox.getSelectionModel().selectFirst();
}

System.out.println("Genome list is the same.");
                }
// Check if the build-to-map master has changed
if (buildsAndTables.getBuildToMapMaster() != null && 
    !ap3AConfig.getBuildToMapMaster().equals(buildsAndTables.getBuildToMapMaster())) {
    //ap3AConfig.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());
    System.out.println("Build-to-map master has changed - will rewrite.");
    rewriteConfig = true;
}
// If configuration needs rewriting
if (rewriteConfig) {
    try {
        System.out.println("Rewriting output...");
        
        ap3AConfig.writeGenomeXmlFile(getDasGenomeXmlDocument());
    } 
    catch { (IOException ex) {
    
        // Handle the error using a modern JavaFX Alert
        Alert alert2 = new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("Error");
        alert2.setHeaderText("Error Updating Genomes!");
        alert2.setContentText(
            "AutoPrimer3A encountered an error writing updated genomes to its database file. " +
            "See exception details below:\n\n" + ex.getMessage()
        );
        {
        // Optionally log the exception details
        
            ex.printStackTrace();
        }
        {
        // Display the alert dialog
        
            alert.showAndWait();
    }
    }
    
            getGenomesTask.setOnFailed
        (new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
        progressIndicator.setProgress(0);
        System.out.println(e.getSource().getException());
        // Create an error alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Gene Search Failed!");
        alert.setContentText(
            "AutoPrimer3A encountered an error when performing "
            + "a background check of available genomes."
            + "See exception below."
        );
alert.showAndWait();
        // Optionally log the exception details somewhere (e.g., console or log file)
        e.getSource().getException().printStackTrace();
    }                                            
checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
           System.out.println("Finished getting tables for " + genome);
           Document doc = (Document) e.getSource().getValue();

    }
});
            try {
                if (!ap3AConfig.getBuildXmlDocument(genome).asXML().equals(doc.asXML()) && doc != null) {
                    // Tables differ, check whether it affects relevant tables
                    System.out.println("Tables differ!");
                    LinkedHashSet tables = ap3AConfig.readTableFile(doc);

                    if (configTablesDiffer(tables, ap3AConfig.getBuildToTables().get(genome))) {
                        System.out.println("SNP/Gene tables differ!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tables Updated");
                        alert.setHeaderText("Gene/SNP Tables Updated");
                        alert.setContentText(
                            "The Gene/SNP tables for your currently selected genome have been updated."
                        );

                        // Show the alert dialog
                        alert.showAndWait();

                        String g = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                        if (g.equals(genome)) {
                            String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
                            snpsChoiceBox.getItems().clear();
                            snpsChoiceBox.getItems().add("No");
                            snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));

                            if (snpsChoiceBox.getItems().contains(curSnp)) {
                                snpsChoiceBox.getSelectionModel().select(curSnp);
                            } else {
                                snpsChoiceBox.getSelectionModel().selectFirst();
                            }

                            String curGene = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();
                            databaseChoiceBox.getItems().clear();
                            databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));

                            if (databaseChoiceBox.getItems().contains(curGene)) {
                                databaseChoiceBox.getSelectionModel().select(curGene);
                            } else {
                                databaseChoiceBox.getSelectionModel().selectFirst();
                            }
                        }
                    }
                 // Update and rewrite config file silently
                    ap3AConfig.getBuildToTables().put(genome, tables);

                    try {
                        System.out.println("Writing new xml database file");
                        ap3AConfig.writeTableXmlFile(doc, genome);
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Updating Genome Information");
                        alert.setContentText(
                            "AutoPrimer3A encountered an error writing "
                            + "updated gene/SNP tables to its database "
                            + "file for genome " + genome + ". "
                            + "See exception below."
                        );

                        alert.showAndWait();
                    }
                } else {
                    System.out.println("Tables are the same");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }    
                    // Create an error alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Gene Search Failed!");
                    alert.setContentText(
        "AutoPrimer3A encountered an error when performing "
                            + "a background check of available genomes."
                            + "See exception below.");        
    // Optionally log the exception details somewhere (e.g., console or log file)
    //ex.printStackTrace();
    // Show the alert
    alert.showAndWait();
    }
        }
    
private void checkUcscTables(final String genome){
        //Background check that tables for given genome are up to date
        final Task<Document> checkUcscTablesTask = 
            new Task<Document>(){
            @Override
            protected Document call() 
                    throws DocumentException, MalformedURLException{
                System.out.println("Checking tables for " + genome);

                return buildsAndTables.getTableXmlDocument(genome);
            }
        };
        checkUcscTablesTask.setOnSucceeded(
                new EventHandler<WorkerStateEvent>()        
                @Override
                public void handle(WorkerStateEvent e) {    
                    System.out.println("Finished getting tables for " + genome);
    Document doc = (Document) e.getSource().getValue();
    try {
        if (doc != null && !ap3AConfig.getBuildXmlDocument(genome).asXML().equals(doc.asXML())) {
            // Tables differ, check whether it affects relevant tables
            System.out.println("Tables differ!");
            LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);
            if (configTablesDiffer(tables, ap3AConfig.getBuildToTables().get(genome))) {
                // Handle SNP/Gene table differences
                System.out.println("SNP/Gene tables differ!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tables Updated");
                alert.setHeaderText("Gene/SNP Tables Updated");
                alert.setContentText(
                    "The Gene/SNP tables for your currently selected genome have been updated."
                );
                alert.showAndWait();

                String g = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                if (g.equals(genome)) {
                    // Update SNPs
                    String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
                    snpsChoiceBox.getItems().clear();
                    snpsChoiceBox.getItems().add("No");
                    snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));
                    if (snpsChoiceBox.getItems().contains(curSnp)) {
                        snpsChoiceBox.getSelectionModel().select(curSnp);
                    } else {
                        snpsChoiceBox.getSelectionModel().selectFirst();
                    }

                    // Update Genes
                    String curGene = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();
                    databaseChoiceBox.getItems().clear();
                    databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));
                    if (databaseChoiceBox.getItems().contains(curGene)) {
                        databaseChoiceBox.getSelectionModel().select(curGene);
                    } else {
                        databaseChoiceBox.getSelectionModel().selectFirst();
                    }
                }
            }
            // Update configuration
            ap3AConfig.getBuildToTables().put(genome, tables);
            try {
                System.out.println("Writing new XML database file");
                ap3AConfig.writeTableXmlFile(doc, genome);
            } catch (IOException ex) {
                showErrorAlert("Error Updating Genome Information", "AutoPrimer3A encountered an error writing updated gene/SNP tables to its database file for genome " + genome + ".", ex);
            }
        } else {
            System.out.println("Tables are the same");
        }
    } catch (DocumentException | IOException ex) {
        showErrorAlert("Error Updating Genome Information!", "AutoPrimer3A encountered an error updating gene/SNP tables from UCSC for genome " + genome + ".", ex);
    }
}

// Helper method to display error alerts
private void showErrorAlert(String title, String content, Exception ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(content + "\n\nSee exception below:\n" + ex.getMessage());
    alert.showAndWait();
}
                }        
           checkUcscTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                System.out.println(e.getSource().getException());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText("AutoPrimer3A encountered an error when performing "
                            + "a background check of available gene/SNP tables "
                            + "for genome " + genome + ". See exception below.");
            alert.showAndWait();
        }                  
       new Thread(checkUcscTablesTask).start();        
    //only checks snp and gene tables in lists to see if they are the same
    private boolean configTablesDiffer(LinkedHashSet<String> tables, 
            LinkedHashSet<String> configTables){
                ArrayList<String> tableComp = new ArrayList<>();
                ArrayList<String> configComp = new ArrayList<>();
                    for (String t: tables){
                    if (matchesGeneTable(t) || matchesSnpTable(t)){
                tableComp.add(t);
            }
        }
        for (String t: configTables){
            if (matchesGeneTable(t) || matchesSnpTable(t)){
                configComp.add(t);
            }
        }
        return (! (tableComp.containsAll(configComp) && configComp.containsAll(tableComp)));
    }
        
    private LinkedHashSet<String> getGenesFromTables(LinkedHashSet<String> tables){
        LinkedHashSet<String> genes = new LinkedHashSet<>();
        for (String t: tables){
            if (matchesGeneTable(t)){
                genes.add(t);
            
            }
            
        return genes;
            }
    }
        private boolean matchesSnpTable(String t)
{

            return t.matches("^snp\\d+(\\w+)*");
    }        
        private LinkedHashSet<String> 
                getSnpsFromTables 
        (LinkedHashSet<String> tables){
        LinkedHashSet<String> snps = new LinkedHashSet<>();
        for (String t: tables){
            if (matchesSnpTable(t)){
                snps.add(t);
            }
        
            return snps;
            
        }
        }   
    private boolean matchesGeneTable(String t){
        
        return (t.equals("refGene") || t.equals("knownGene") || 
                t.equals("ensGene") || t.equals("xenoRefGene"));        
    }   
                
}
getBuildsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){

LinkedHashMap<String, String> buildIds = 
                        (LinkedHashMap<String, String>) e.getSource().getValue();
                genomeChoiceBox.getItems().clear();
                genomeChoiceBox.getItems().addAll(buildIds.keySet());
                genomeChoiceBox.getSelectionModel().selectFirst();
                ap3AConfig.setBuildToDescription(buildIds);
                ap3AConfig.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());
} try {
                    System.out.println("Writing new xml database file");
                    ap3AConfig.writeGenomeXmlFile();
                }catch (DocumentException|IOException ex){
                    //ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Writing Genome Data!");
                    alert.setContentText("AutoPrimer3A encountered an error writing "
                                    + "updated genomes to its database file."
                                    + "See exception below.");
                    alert.showAndWait();
                }                                           
                setLoading(false);
                {                                
        getBuildsTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
 
@Override

public void handle (WorkerStateEvent e){
                progressIndicator.setProgress(0);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Retrieving Genome Information from UCSC");
                alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                            + "UCSC server to retrieve available genomes. "
                            + "See exception below. Use the Refresh/Reconnect "
                            + "button to try again.");
                alert.showAndWait();
                        
                setLoading(false);
                setCanRun(false);
            }
{
        getBuildsTask.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                progressIndicator.setProgress(0);                
                progressLabel.setText("UCSC connection cancelled.");
                setLoading(false);
                setCanRun(false);
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                getBuildsTask.cancel();
  
           }
       });
        progressLabel.setText("Connecting to UCSC...");
        new Thread(getBuildsTask).start();
            }   
        ;
{
        getTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            
            @Override
            
            public void handle (WorkerStateEvent e){
                //System.out.println("getTablesTask succeeded.");
            }    Document doc = (Document) e.getSource().getValue();
             try {
                    LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);
                    ap3AConfig.getBuildToTables().put(id, tables);    
                    if (! doc.asXML().equals(ap3AConfig.getBuildXmlDocument(id).asXML())){
                        try{
                            //System.out.println("Writing output");
                            ap3AConfig.writeTableXmlFile(doc, id);
                        }catch (IOException ex){
                            //ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Updating Genome Information!");
                alert.setContentText("AutoPrimer3A encountered an error writing "
                                        + "updated gene/SNP tables to the database "
                                        + "file for genome " + id + ". "
                                        + "See exception below.");
                alert.showAndWait();
    }                       
                    }
                    setTables(tables);
                    progressIndicator.setProgress(0);
                    progressLabel.setText("");
                    setLoading(false);
                } catch (IOException|DocumentException ex){
                    //ex.printStackTrace();
    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Reading Updating Genome Information!");
        alert.setContentText("AutoPrimer3A encountered an error reading "
                                + "updated gene/SNP tables from UCSC for genome "
                                + id + ". See exception below.");
        alert.showAndWait();                                        
                getTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                progressLabel.setText("Get Tables Task Failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Retrieving Gene/SNP Tables from UCSC");
        alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                            + "UCSC server to retrieve available gene/SNP tables. "
                            + "See exception below. Use the Refresh/Reconnect "
                            + "button to try again or select a different genome.");
        alert.showAndWait();
            }
            {
                setLoading(false);
                setCanRun(false);           
        }
                }
        getTablesTask.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                progressLabel.setText("Get Tables Task Cancelled");
                //System.out.println("getTablesTask cancelled.");
                setLoading(false);
                setCanRun(false);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                getTablesTask.cancel();
           }                          
        progressIndicator.setProgress(-1);
        new Thread(getTablesTask).start();
                                                 
public void loadRegionsFile(){
    /*  we need to know how many regions we already have to make sure we don't go over
        MAX_LINES_PER_DESIGN
    */
        final int regions = regionsTextArea.getText().split("\\n").length;
        if (regions > MAX_LINES_PER_DESIGN){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Max Regions Already Reached");
        alert.setHeaderText("Maximum of " + MAX_LINES_PER_DESIGN 
                    + " lines allowed per design");
        alert.setContentText("Cannot load file - you have already reached the maximum"
                        + " number of lines allowed per design.  Delete some or "
                        + "all regions if you want to load regions from a file.");
        alert.showAndWait();
    
}
        
            progressLabel.setText("Max regions reached.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select input file");
        {
        HashMap<String, ArrayList<String>> extFilters = new HashMap<>(); 
        extFilters.put("Any Region file", new ArrayList<>(
                Arrays.asList("*.bed", "*.bed.gz", "*.vcf", "*vcf.gz", "*.txt", "*txt.gz",
                "*.BED", "*.BED.GZ", "*.VCF", "*VCF.GZ", "*.TXT", "*TXT.GZ") ) );
        extFilters.put("BED file",  new ArrayList<>(
                Arrays.asList("*.bed", "*.bed.gz", "*.BED", "*.BED.GZ") ) );
        extFilters.put("VCF file", new ArrayList<>(
                Arrays.asList("*.vcf", "*vcf.gz", "*.VCF", "*VCF.GZ") ) ); 
        extFilters.put("Text file", new ArrayList<>(
                Arrays.asList("*.txt", "*txt.gz", "*.TXT", "*TXT.GZ") ) );
        extFilters.put("Any", new ArrayList<>(Arrays.asList("*.*") ) );
        ArrayList<String> keys = new ArrayList<>(extFilters.keySet()); 
        Collections.sort(keys);
        for (String ext: keys){
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(ext, extFilters.get(ext)));
        }
        fileChooser.setInitialDirectory(new File (System.getProperty("user.home")));
        setCanRun(false);
        final File inFile = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
        if (inFile != null){
            final Task<ArrayList<String>> loadFileTask = 
                        new Task<ArrayList<String>>(){
                @Override
                protected ArrayList<String> call() {
                    ArrayList<String> regionStrings = new ArrayList<>();
                    int totalRegions = regions;
                    int valid = 0;
                    int invalid = 0;
                    try{
                        updateMessage("Opening " + inFile.getName());
                        BufferedReader br;
                        if (inFile.getName().endsWith(".gz")){
                            InputStream gzipStream = new GZIPInputStream(new FileInputStream(inFile));
                            Reader decoder = new InputStreamReader(gzipStream);
                            br = new BufferedReader(decoder);
                        }else{
                            br = new BufferedReader(new FileReader(inFile));
                        }
                        String line;
                        int n = 0;
                        
                        while ((line = br.readLine()) != null) {
                           n++;
                           if (line.startsWith("#")){//skip header lines
                               continue;
                           }
                           updateMessage("Parsing line " + n + "...");
                           GenomicRegionSummary region = RegionParser.readRegion(line);
                           if (region != null){
                               String r = region.getChromosome() + ":" + 
                                       region.getStartPos() + "-" + region.getEndPos();
                               if (region.getName() != null && !region.getName().isEmpty()){
                                   r = r + " " + region.getName();
                               }
                               if (totalRegions > MAX_LINES_PER_DESIGN){
                                   final int lastLine = n - 1;
                                   final int validRegions = valid;
                                   Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Maximum of " + MAX_LINES_PER_DESIGN 
                                                    + " lines allowed per design");
                    alert.setContentText("You have reached the maximum "
                                                    + "number of lines allowed per "
                                                    + "design while processing line " 
                                                    + lastLine + " of file " + inFile.getName()
                                                    + ". " + validRegions  
                                                    +  " valid regions added. "
                                                    + "Remaining lines will not be "
                                                    + "read.");
                        alert.showAndWait();
                        }
                                        }
                                    );
                                   break;                                   
                               }
                               regionStrings.add(r);
                               valid++;
                               totalRegions++;
                           }else{
                                invalid++;
                                //System.out.println("Invalid region:\t" + line);
                           }
                        }
                        br.close();
                    }catch(IOException ex){
                        //ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Loading Region File");
                alert.setContentText("Could not read region file. "
                                    + "See exception below.");

        // Optionally log the exception to the console for debugging
                ex.printStackTrace();

                alert.showAndWait();
            }                        
                     if (invalid > 0){
                        StringBuilder msg = new StringBuilder(invalid)
                                .append(" invalid region");
                        if (invalid > 1){
                            msg.append("s");
                        }
                        msg.append(" identified in file ").append(inFile.getName())
                                .append(" (").append(valid).append(" valid region");
                        if (valid != 1){
                            msg.append("s");
                        }
                        msg.append(" identified).");
                        final String messageString = msg.toString();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Regions");
                        alert.setHeaderText("Invalid Regions Identified");
                        alert.setContentText(messageString);
                        alert.showAndWait();
                }
                        });
                    }
                    return regionStrings;
                }
            };
            loadFileTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
                @Override
                public void handle (WorkerStateEvent e){
                    setRunning(false);
                    progressIndicator.progressProperty().unbind();
                    progressIndicator.progressProperty().set(0);
                    progressLabel.textProperty().unbind();
                    ArrayList<String> loadedRegions = (ArrayList<String>) 
                            e.getSource().getValue();
                    int n = 0;
                    for (String r: loadedRegions){
                        n++;
                        regionsTextArea.appendText(r + "\n");
                    }
                    progressLabel.setText("Added " + n + " regions from " 
                            + inFile.getName() + ".");
                }
            });
            loadFileTask.setOnCancelled(new EventHandler<WorkerStateEvent>(){
                @Override
                public void handle (WorkerStateEvent e){
                    setRunning(false);
                    progressLabel.textProperty().unbind();
                    progressLabel.setText("Loading cancelled");
                    progressIndicator.progressProperty().unbind();
                    progressIndicator.progressProperty().set(0);
                }
            });
            loadFileTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
                @Override
                public void handle (WorkerStateEvent e){
                    setRunning(false);
                    progressLabel.textProperty().unbind();
                    progressLabel.setText("Loading failed!");
                    progressIndicator.progressProperty().unbind();
                    progressIndicator.progressProperty().set(0);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Loading Region File");
                alert.setContentText("Could not read region file. "
                            + "See exception below.");
                alert.showAndWait();
    }       
                
            });
            cancelButton.setOnAction(new EventHandler<ActionEvent>(){
               @Override
               public void handle(ActionEvent actionEvent){
                    loadFileTask.cancel();
                }
            });
            progressIndicator.progressProperty().unbind();
            progressLabel.textProperty().unbind();
            progressIndicator.progressProperty().bind(loadFileTask.progressProperty());
            progressLabel.textProperty().bind(loadFileTask.messageProperty());
            setRunning(true);
            new Thread(loadFileTask).start();
        }
    }
        
    public void clearRegions(){
        regionsTextArea.clear();
        if (! progressLabel.textProperty().isBound()){
            progressLabel.setText("");
        }
    }
    
    private ArrayList<GenomicRegionSummary> getRegionsForDesign(String regionsInput){
        ArrayList<GenomicRegionSummary> regions = new ArrayList<>();
        List<String> tempRegions = Arrays.asList(
                regionsInput.replaceAll("(?m)^\\s", "").split("\\n"));
        ArrayList<String> invalidRegions = new ArrayList<>();
        int n = 1;
        for (String r: tempRegions){
            if (! r.matches(".*\\w.*")){
                continue;
            }
            //region parser methods
            GenomicRegionSummary region = RegionParser.readRegion(r);
            if (region != null){
                if (region.getName() == null || region.getName().isEmpty() || 
                        !useRegionNamesCheckBox.isSelected()){
                    region.setName("Region_" + n++);
                }
                region.setId(region.getChromosome() + ":" + region.getStartPos()
                        + "-" + region.getEndPos());
                regions.add(region);
            }else{
                invalidRegions.add(r);
                System.out.println("Invalid region:\t" + r);
            }
        }
        if (regions.isEmpty()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("No Regions");
                            alert.setHeaderText("No valid regions found.");
                            alert.setContentText(
                            "No valid regions were found in your " + "input.");
                            // Show the alert dialog
                            alert.showAndWait();    
            return null;
        }
        
        if (invalidRegions.size() > 0){
            StringBuilder mh = new StringBuilder("Found " + 
                    invalidRegions.size() + " Invalid Region");
            if (invalidRegions.size() > 1){
                mh.append("s");
            }
            mh.append(" in User Input");
            StringBuilder msg = new StringBuilder("Invalid regions found "
                    + "- continue designing without these regions?\n");
            if (invalidRegions.size() <= 10){
                msg.append(String.join("\n", invalidRegions));
            }else{
                msg.append(String.join("\n", invalidRegions.subList(0, 10)));
                msg.append("\n...and ").
                    append((invalidRegions.size() - 9)).
                    append("more.");
            }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invalid Regions");
                alert.setHeaderText(mh.toString());
                alert.setContentText(msg.toString());

                // Add Yes and No buttons
                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesButton, noButton);

                // Show the dialog and capture the response
                Optional <ButtonType> response = alert.showAndWait();

                if (response.isPresent() && response.get() == yesButton) {
                // Handle Yes action
                System.out.println("User chose YES");
                } else {             
                setRunning(false);
                progressIndicator.progressProperty().unbind();
                progressLabel.textProperty().unbind();        
                progressLabel.setText("Design cancelled");
                progressIndicator.progressProperty().set(0);
                return null;
            }
        }
        
        ArrayList<GenomicRegionSummary> tooLong = new ArrayList<>();
        ArrayList<GenomicRegionSummary> passed = new ArrayList<>();
        for (GenomicRegionSummary r : regions){
            if (r.getLength() > MAX_REGION_SIZE){
                tooLong.add(r);
            }else{
                passed.add(r);
            }
        }
        if (tooLong.size() > 0){
            StringBuilder mh = new StringBuilder("Found " + 
                    tooLong.size() + " Region");
            if (tooLong.size() > 1){
                mh.append("s");
            }
            mh.append(" Exceeding Maximum Length in User Input");
            StringBuilder msg = new StringBuilder("Maximum region size "
                    + "is ").append(MAX_REGION_SIZE).append(" bp. "
                    + "Continue designing without these regions?\n");
            if (tooLong.size() <= 10){
                for (GenomicRegionSummary l: tooLong){
                    msg.append(l.getCoordinateString()).append("\n");
                }
            }else{
                for (GenomicRegionSummary l:  tooLong.subList(0, 10)){
                    msg.append(l.getCoordinateString()).append("\n");
                }
                msg.append("\n...and ").
                    append((tooLong.size() - 9)).
                    append("more.");
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Regions");
            alert.setHeaderText(mh.toString());
            alert.setContentText(msg.toString());
            // Add Yes and No buttons
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);

            // Show the dialog and capture the response
            Optional<ButtonType> response = alert.showAndWait();

            if (response.isPresent() && response.get() == yesButton) {
            // Handle Yes action
            System.out.println("User chose YES");
            } else {
            // Handle No action
            setRunning(false);
                progressIndicator.progressProperty().unbind();
                progressLabel.textProperty().unbind();        
                progressLabel.setText("Design cancelled");
                progressIndicator.progressProperty().set(0);
                return null;
            
        }
            
        return passed;
    }
        
    public void designPrimersToCoordinates(){
        final String regionsInput = regionsTextArea.getText();
        final int optSize = Integer.valueOf(splitRegionsTextField.getText());
        final int flanks = Integer.valueOf(flankingRegionsTextField.getText());
        final int designBuffer = Integer.valueOf(minDistanceTextField.getText()); 
        final String genome = (String) genomeChoiceBox.getSelectionModel().getSelectedItem(); 
        
        if (regionsInput.isEmpty()){
            return;
        }
        if (! checkDesignParameters()){
            return;
        }
        final ArrayList<GenomicRegionSummary> regs = getRegionsForDesign(regionsInput);
        if (regs == null){
            return;
        }
        
        final Task<HashMap<String, ArrayList>> designTask = 
                new Task<HashMap<String, ArrayList>>() {
            @Override
            protected HashMap<String, ArrayList> call() 
                    throws SQLException, IOException {
            ArrayList<GenomicRegionSummary> regions = regs;
            GetGeneCoordinates geneSearcher = null;
            ArrayList<Primer3Result> primers = new ArrayList<>();
            ArrayList<String> designs = new ArrayList<>();
            
            SequenceFromDasUcsc seqFromDas = new SequenceFromDasUcsc();
            GenomicRegionSummary merger = new GenomicRegionSummary();
            merger.mergeRegionsByPosition(regions);
            regions = splitLargeRegionsMergeSmallRegions(regions, optSize, 
                    designBuffer, false);
            int pair = 0;
            int n = 0;
            int p = 0;
            updateProgress(0, regions.size() * 3);
            for (GenomicRegionSummary r: regions){
                n++;
                int start = r.getStartPos() - flanks > 0 ? 
                            r.getStartPos() - flanks : 0;
                int end = r.getEndPos() + flanks;
                System.out.println("Region " + r.getChromosome() + ":" + 
                        r.getStartPos() + "-" + r.getEndPos());
                System.out.println("Using start = " + start + " and end = "
                        + end);
                updateMessage("Retrieving DNA for region " + n + 
                        " of " + regions.size() + "...");
                String dna;
                try {
                    dna= seqFromDas.retrieveSequence(
                        genome, r.getChromosome(), start, end);
                }catch(DocumentException | MalformedURLException seqex){
                    final String rString = r.getChromosome() + ":" + start + "-" + end;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error retrieving DNA");
                    alert.setContentText("Exception encountered while retrieving DNA for"
                                            + " region " + rString + ". See below:");
                    alert.showAndWait();
    }
                        }
                    );
                    return null;
                }
                updateProgress(++p, regions.size() * 3);
                //System.out.println(dna);//debug only
                String snpDb = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
                ArrayList<GenomicRegionSummary> snps = new ArrayList<>();
                if (! snpDb.equals("No")){
                    updateMessage("Retrieving SNPs for region " + n + 
                        " of " + regions.size() + "...");
                
                    if (geneSearcher == null){
                        geneSearcher = new GetGeneCoordinates();
                    }
                    snps = geneSearcher.getSnpCoordinates
                            (r.getChromosome(), start, end, genome, snpDb);
                }
                updateProgress(++p, regions.size() * 3);
                ArrayList<String> excludeRegions = new ArrayList<>();
                for (GenomicRegionSummary s: snps){
                    if (s.getStartPos() < start){
                        continue;
                    }else if(s.getEndPos() > end){
                        break;
                    }
                    Integer excludeStart = s.getStartPos() - start - 1;
                    Integer excludeEnd = s.getEndPos() - start - 1;
                    Integer excludeLength = 1 +  excludeEnd - excludeStart;
                    if (excludeStart + excludeLength < dna.length()){
                        excludeRegions.add(excludeStart + "," + excludeLength);
                    }else if (excludeStart < dna.length() - 1 ){
                        int diff = dna.length() -1 - excludeStart;
                        excludeRegions.add(excludeStart + "," + diff);
                    
                }
                //get info from text fields for primer3 options
                String target = Integer.toString(flanks - designBuffer - 1) + 
                        "," + Integer.toString(r.getLength() + 2*designBuffer);
                StringBuilder dnaTarget = new StringBuilder(
                        dna.substring(0, flanks -1).toLowerCase());
                dnaTarget.append(dna.substring(flanks -1, flanks 
                        + r.getLength() - 1).toUpperCase());
                dnaTarget.append(dna.substring(flanks + r.getLength() -1)
                        .toLowerCase());
                String seqid = (r.getName() + ": " + r.getId());
                //design primers
                updateMessage("Designing primers for region " + n + 
                        " of " + regions.size() + "...");
                ArrayList<String> result = designPrimers(seqid, 
                        dnaTarget.toString(), target, String.join(" ", excludeRegions));
                updateProgress(++p, regions.size() * 3);
                //updateProgress(prog, 100);
                designs.add(String.join("\n", result));

                //parse primer3 output and write our output
                primers.add(parsePrimer3Output(++pair,  r.getName(), r.getId(), 
                        r.getChromosome(), 1 + r.getStartPos() - flanks, result));

                }

                HashMap<String, ArrayList> primerResult = new HashMap<>();
                primerResult.put("primers", primers);
                primerResult.put("design", designs);
                return primerResult;
            }
        
        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(designTask.progressProperty());
        progressLabel.textProperty().bind(designTask.messageProperty());
        designTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().set(100);
                progressLabel.textProperty().unbind();
                setRunning(false);
                HashMap<String, ArrayList> result = 
                        (HashMap<String, ArrayList>) e.getSource().getValue();
                if (result == null){
                    return;
                }
                if (result.get("primers").isEmpty()){
                    progressLabel.setText("No primers designed");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No primers found.");
                        alert.setHeaderText("No primers found for your targets.");
                        alert.setContentText("No primer designs were attempted for your targets");
                        alert.showAndWait();  
                        }                    
                        progressIndicator.progressProperty().set(0);
                    return;
                }
                 if (result.get("design").isEmpty()){
                    progressLabel.setText("No primers designed.");
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No primers found.");
                        alert.setHeaderText("No primers found for your targets.");
                        alert.setContentText("No primer designs were attempted for your targets");
                        alert.showAndWait();  
                            
                    progressIndicator.progressProperty().set(0);
                    return;
                 }
        }
                progressLabel.setText(result.get("primers").size() +
                        " primer pairs designed.");
                FXMLLoader tableLoader;
                if (System.getProperty("os.name").equals("Mac OS X")){
                    tableLoader = new FXMLLoader(getClass().
                                       getResource("Primer3ResultViewMac.fxml"));
                }else{
                     tableLoader = new FXMLLoader(getClass().
                                       getResource("Primer3ResultView.fxml"));
                }
                try{
                    Pane tablePane = (Pane) tableLoader.load();
                    Primer3ResultViewController resultView = 
                            (Primer3ResultViewController) tableLoader.getController();
                    Scene tableScene = new Scene(tablePane);
                    Stage tableStage = new Stage();
                    tableStage.setScene(tableScene);
                    //tableScene.getStylesheets().add(AutoPrimer3A.class
                    //        .getResource("autoprimer3A.css").toExternalForm());
                    resultView.displayData(result.get("primers"), 
                            result.get("design"), null);
                    tableStage.setTitle("AutoPrimer3A Results");
                    tableStage.getIcons().add(new Image(this.getClass()
                            .getResourceAsStream("icon.png")));
                    tableStage.initModality(Modality.NONE);
                    tableStage.show();
               }catch (Exception ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Displaying Design Results");
                        alert.setContentText("Internal error displaying primer design"
                                + " results in a new window."
                                + "See exception below.");
                        alert.showAndWait();  
                        }    
                    ex.printStackTrace();
               }
            }
        }
        }
        designTask.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                setRunning(false);
                progressLabel.textProperty().unbind();
                progressLabel.setText("Design cancelled");
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().set(0);
            }

        });
        designTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                setRunning(false);
                e.getSource().getException().printStackTrace();
                progressLabel.textProperty().unbind();
                progressLabel.setText("Design failed!");
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().set(0);
                if (e.getSource().getException() instanceof SQLException){
                
                }
                
                try {
                // Your logic that might throw an exception
                } catch (Exception e) {
                // SQL Error Dialog
                Alert sqlError = new Alert(AlertType.ERROR);
                sqlError.setTitle("SQL Error");
                sqlError.setHeaderText("Error Retrieving SNPs");
                sqlError.setContentText("Failed to retrieve SNPs from UCSC. See exception below.");
    
                // Optional: Add expandable content for exception details
                String exceptionText = e.getCause() != null ? e.getCause().toString() : e.toString();
                TextArea exceptionDetails = new TextArea(exceptionText);
                exceptionDetails.setEditable(false);
                exceptionDetails.setWrapText(true);

                sqlError.getDialogPane().setExpandableContent(exceptionDetails);
                sqlError.showAndWait();
    
                // Design Error Dialog
                Alert designErr = new Alert(AlertType.ERROR);
                designErr.setTitle("Error");
                designErr.setHeaderText("Design Failed!");
                designErr.setContentText("AutoPrimer3A encountered an error designing primers to your targets. See exception below.");

                exceptionText = e.getCause() != null ? e.getCause().toString() : e.toString();
                exceptionDetails = new TextArea(exceptionText);
                exceptionDetails.setEditable(false);
                exceptionDetails.setWrapText(true);

                designErr.getDialogPane().setExpandableContent(exceptionDetails);
                designErr.showAndWait();
                }
                    }        
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                designTask.cancel();
            }        
        setRunning(true);
        new Thread(designTask).start();
    
    
    public boolean checkDesignParameters(){
        if (Integer.valueOf(flankingRegionsTextField.getText())
                <= (Integer.valueOf(minDistanceTextField.getText()) 
                + Integer.valueOf(maxSizeTextField.getText()))){
        		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Flanks Error");
			alert.setHeaderText("Invalid values for 'Min distance'/"
                            + "'Flanking region' fields.");
			alert.setContentText("Flanking region value must be greater than Min "
                            + "distance value  plus maximum primer size.");
			alert.showAndWait();
				}    
            return false;
        }
        
        if (Integer.valueOf(optSizeTextField.getText()) < 
                Integer.valueOf(minSizeTextField.getText())){
            
                    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Primer Size Error");
        alert.setHeaderText("Invalid values for primer size fields.");
        alert.setContentText("Min Primer Size can not be greater than Opt "
                            + "Primer Size.");
        alert.showAndWait();
    }       
            return false;
        }
        
        if (Integer.valueOf(maxSizeTextField.getText()) < 
                Integer.valueOf(optSizeTextField.getText())){            
                    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Primer Size Error");
        alert.setHeaderText("Invalid values for primer size fields.");
        alert.setContentText("Max Primer Size can not be less than Opt "
                            + "Primer Size.");
        alert.showAndWait();
        }
            return false;
        }
                
        if (Double.valueOf(optTmTextField.getText()) < 
                Double.valueOf(minTmTextField.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Primer TM Error");
        alert.setHeaderText("Invalid values for primer TM fields.");
        alert.setContentText("Min Primer TM can not be greater than Opt "
                            + "Primer TM.");
        alert.showAndWait();
        }
             return false;
        }
        
        if (Double.valueOf(maxTmTextField.getText()) < 
                Double.valueOf(optTmTextField.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Primer TM Error");
        alert.setHeaderText("Invalid values for primer TM fields.");
        alert.setContentText("Max Primer TM can not be less than Opt "
                            + "Primer TM.");
        alert.showAndWait();
    }            
            return false;
        }
        return true;
    
    
    public void designPrimersToGene(){
        //check that we've got at least one gene in our input box
        if (!genesTextField.getText().matches(".*\\w.*")){
            return;//TO DO show ERROR dialog maybe        
        resetEmptyPrimerSettings();
        if (!checkSizeRange(sizeRangeTextField)){
            displaySizeRangeError();
            return;        
        final int optSize = Integer.valueOf(splitRegionsTextField.getText());
        final int flanks = Integer.valueOf(flankingRegionsTextField.getText());
        final int designBuffer = Integer.valueOf(minDistanceTextField.getText());
        if (! checkDesignParameters()){
            return;
                                    
        setRunning(true);
        final Task<GeneSearchResult> geneSearchTask = 
                new Task<GeneSearchResult>(){
            @Override
            protected GeneSearchResult call() 
                    throws SQLException, GetGeneCoordinates.GetGeneExonException{
                updateMessage("Connecting to UCSC database...");
                GetGeneCoordinates geneSearcher = getGeneSearcher();
                if (geneSearcher == null){
                    updateMessage("Connection failed.");
                    return null;
                
                updateMessage("Connection succeeded.");

                LinkedHashSet<String> searchStrings = new LinkedHashSet<>();
                Collections.addAll(searchStrings, genesTextField.getText().split("\\s+"));
                LinkedHashSet<String> notFound = new LinkedHashSet<>();
                LinkedHashSet<String> nonCodingTargets = new LinkedHashSet<>();
                LinkedHashSet<GeneDetails> targets = new LinkedHashSet<>();

                searchStrings.removeAll(Arrays.asList(null, ""));
                for (String s : searchStrings){
                    if (! s.matches(".*\\w.*")){
                        continue;
                    }
                    updateMessage("searching for " + s + "...");
                    ArrayList<GeneDetails> found = new ArrayList<>();
                    //ArrayList<GeneDetails> found;
                    try {
                        found = getGeneDetails(s, geneSearcher);
                    } catch (GetGeneCoordinates.GetGeneFromIDException (ex)) {
                        Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GetGeneCoordinates.GetGeneFromSymbolException (ex)) {
                        Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
                    
                    if (found.isEmpty()){
                        notFound.add(s);
                    }else{
                        if (designToChoiceBox.getSelectionModel().
                                getSelectedItem().equals("Coding regions")){
                            boolean matched = false;
                            for (GeneDetails f : found){
                                if (f.isCoding()){
                                    matched = true;
                                    targets.add(f);
                                }else{
                                    nonCodingTargets.add(f.getId());
                                
                            }
                            if (! matched){
                                notFound.add(s);
                            
                        }else{
                            targets.addAll(found);
                        }
                    }
                }
                return new GeneSearchResult(targets, notFound, nonCodingTargets,
                        geneSearcher);
            }
         }
        
        geneSearchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                progressIndicator.progressProperty().unbind();
                progressLabel.textProperty().unbind();        
                GeneSearchResult result = 
                        (GeneSearchResult) e.getSource().getValue();
                final LinkedHashSet<GeneDetails> targets = result.getFound();
                LinkedHashSet<String> notFound = result.getNotFound();
                LinkedHashSet<String> nonCodingTargets = result.getNonCodingTargets();
                final GetGeneCoordinates geneSearcher = result.getGeneSearcher();
                if (targets.isEmpty()){
                    String message = "No targets found for your search terms.";
                    if (!nonCodingTargets.isEmpty()){
                        message = message + " " + nonCodingTargets.size() + 
                                " non-coding transcripts were found.\n";
                    }

}
}
}
}
}
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Genes Found");
                alert.setHeaderText("No targets found.");
                alert.setContentText(message);
                alert.showAndWait();
    }                    
                    progressIndicator.progressProperty().unbind();
                    progressLabel.textProperty().unbind();
                    progressIndicator.progressProperty().set(0);
                    progressLabel.setText("No targets found");
                    setRunning(false);
                    return;
                }else if (! notFound.isEmpty()){
                    StringBuilder message = new StringBuilder("Matching transcripts could"
                            + "not be found for the following genes using your search "
                            + "parameters:\n" + String.join(", ", notFound) + ".\n\n");
                    if (!nonCodingTargets.isEmpty()){
                        message.append("The following transcripts were found but are "
                                + "non-coding:\n"+ String.join(", ", nonCodingTargets) 
                                + ".\n\n");
                    }
                    message.append("Continue anyway?")
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("warning");
                alert.setHeaderText("Could Not Find Some Genes");
                alert.setContentText(message);
                alert.showAndWait();
    }
                            setRunning(false);
                        progressIndicator.progressProperty().unbind();
                        progressLabel.textProperty().unbind();        
                        progressLabel.setText("Design cancelled");
                        progressIndicator.progressProperty().set(0);
                        return;
                    }
                }                
                final Task<HashMap<String, ArrayList>> designTask = 
                        new Task<HashMap<String, ArrayList>>(){
                    @Override
                    protected HashMap<String, ArrayList> call() 
                            throws SQLException, IOException{

                    //get our transcript targets using databaseChoiceBox and genesTextField
                        double prog = 0;
                        Integer pair = 0;
                        //check we've found at least one target and warn if any search term could not be found

                        //find 5' and 3' ends of transcripts/CDS + flanks
                        ArrayList<GenomicRegionSummary> genomicRegions = new ArrayList<>();
                        for (GeneDetails t : targets){
                            int start = getGeneStart(t, flanks);
                            int end = getGeneEnd(t, flanks);
                            GenomicRegionSummary r = new GenomicRegionSummary(t.getChromosome(), 
                                    start, end, null, null, t.getId(), t.getSymbol());
                            genomicRegions.add(r);
                        }
                        final String genome = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                        GenomicRegionSummary merger = new GenomicRegionSummary();
                        merger.mergeRegionsByPosition(genomicRegions);

                        ArrayList<Primer3Result> primers = new ArrayList<>();
                        ArrayList<String> designs = new ArrayList<>();
                        HashMap<String, String> referenceSeqs = new HashMap<>();
                        //get DNA
                        int regionNumber = 0;
                        double incrementPerRegion = 100/genomicRegions.size();
                        HashSet<String> geneSymbolTracker = new HashSet<>();
                        HashSet<String> transcriptTracker = new HashSet<>();
                        SequenceFromDasUcsc seqFromDas = new SequenceFromDasUcsc();
                        for (GenomicRegionSummary r : genomicRegions){
                            regionNumber++;
                            updateMessage("Getting DNA for region " + regionNumber + 
                                    " of " + genomicRegions.size());
                            updateProgress(-1, -1);
                            HashMap<String, String> checkedName = new HashMap<>();
                            HashMap<String, String> checkedTranscript = new HashMap<>();
                                //only want to check names/ID has already been 
                                //used once per region i.e. for previous regions
                            String dna;
                            try{
                                dna = seqFromDas.retrieveSequence(
                                    genome, r.getChromosome(), r.getStartPos(), r.getEndPos());
                            }catch (DocumentException | MalformedURLException seqex){
                                final String rString = r.getChromosome() + ":" + 
                                                    r.getStartPos() + "-" + r.getEndPos();
                                final String rName = r.getName();
                                Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error retrieving DNA");
                        alert.setContentText("Exception encountered while retrieving DNA for"
                                                        + " region " + rString + " for gene " 
                                                        + rName + ". See below:");
                        alert.showAndWait();  
                        }     
                                 });
                                return null;
                            }
                            //System.out.println(dna);//debug only
                            String snpDb = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
                            ArrayList<GenomicRegionSummary> snps = new ArrayList<>();
                            if (! snpDb.equals("No")){
                                updateMessage("Getting SNPs for region " + regionNumber + 
                                " of " + genomicRegions.size());
                                updateProgress(-1, -1);
                                snps = geneSearcher.getSnpCoordinates
                                    (r.getChromosome(), r.getStartPos(), r.getEndPos(), 
                                    genome, snpDb);
                            }

                            ArrayList<GenomicRegionSummary> exonRegions = new ArrayList<>();
                            int minus_strand = 0;
                            int plus_strand = 0;
                            for (GeneDetails t : targets){
                                if (! t.getChromosome().equals(r.getChromosome())){
                                    continue;
                                }
                                int start = getGeneStart(t, flanks);
                                if (start > r.getEndPos()){
                                    continue;
                                }
                                int end = getGeneEnd(t, flanks);
                                if (end < r.getStartPos()){
                                    continue;
                                }
                                
                                ArrayList<Exon> exons = t.getExons();
                                if (t.getStrand().equals("-")){
                                    minus_strand++;
                                }else{
                                    plus_strand++;
                                }
                                for (Exon e : exons){
                                    if (designToChoiceBox.getSelectionModel().getSelectedItem()
                                        .equals("Coding regions")){
                                        if (! e.isCodingExon()){
                                            continue;
                                        }
                                    }
                                    String name = t.getSymbol();
                                    if (! checkedName.containsKey(name)){
                                        checkedName.put(name, checkDuplicate(name, geneSymbolTracker));
                                    }
                                    name = checkedName.get(name);
                                    String transcript = t.getId();
                                    if (!checkedTranscript.containsKey(transcript)){
                                        checkedTranscript.put(transcript, 
                                                checkDuplicate(transcript, transcriptTracker));
                                    }
                                    transcript = checkedTranscript.get(transcript);
                                    String id = transcript.concat("_ex").
                                            concat(Integer.toString(e.getOrder()));
                                    GenomicRegionSummary ex; 
                                    if (designToChoiceBox.getSelectionModel().getSelectedItem()
                                        .equals("Coding regions")){
                                        Exon ce = e.getExonCodingRegion();
                                        ex = new GenomicRegionSummary(
                                                r.getChromosome(), ce.getStart(), 
                                                ce.getEnd(), null, null, id, name);
                                    }else{
                                        ex = new GenomicRegionSummary(
                                                r.getChromosome(), e.getStart(), 
                                                e.getEnd(), null, null, id, name);
                                    }
                                    exonRegions.add(ex);
                                }
                            }
                            boolean onMinusStrand = false;
                            if (minus_strand > plus_strand){
                                onMinusStrand = true;
                            }
                            merger.mergeRegionsByPosition(exonRegions);
                            //need to allow for multiple regions for a given gene
                            referenceSeqs.put(r.getName() + "|" + r.getCoordinateString(), 
                                    createReferenceSequence(dna, r.getStartPos(),
                                            flanks, exonRegions, onMinusStrand));
                            //DEBUG
                            //System.out.println("Ref " + r.getName() + ":" + r.getCoordinateString());
                            //System.out.println(referenceSeqs.get(r.getName()));
                            
                            numberExons(exonRegions, onMinusStrand);
                            exonRegions = splitLargeRegionsMergeSmallRegions(exonRegions, 
                                    optSize, designBuffer, onMinusStrand);
                            if (onMinusStrand){
                                Collections.reverse(exonRegions);
                            }
                            //get substring for each exon and design primers using primer3
                            double incrementPerExRegion = 
                                    incrementPerRegion /exonRegions.size();
                            int exonNumber = 0;
                            for (GenomicRegionSummary er: exonRegions){
                                exonNumber++;
                                prog += incrementPerExRegion;
                                int tStart = er.getStartPos() - r.getStartPos();
                                int tEnd = 1 + er.getEndPos() - r.getStartPos();
                                int subsStart = tStart - flanks > 0 ? tStart - flanks : 0;
                                int subsEnd = tEnd + flanks - 1 < dna.length() ?  tEnd + flanks - 1 : dna.length();
                                StringBuilder dnaTarget = new StringBuilder(
                                        dna.substring(subsStart, tStart).toLowerCase());
                                dnaTarget.append(dna.substring(tStart, tEnd-1)
                                        .toUpperCase());
                                dnaTarget.append(dna.substring(tEnd -1, subsEnd)
                                        .toLowerCase());
                                if (minus_strand > plus_strand){
                                    dnaTarget = new StringBuilder(reverseComplement
                                            (dnaTarget.toString())
                                    );
                                }
                                //System.out.println(er.getName() + ": " + er.getId());//debug only
                                //System.out.println(dnaTarget.toString());//debug only

                                ArrayList<String> excludeRegions = new ArrayList<>();

                                for (GenomicRegionSummary s: snps){
                                    if (s.getStartPos() < r.getStartPos() + subsStart){
                                        continue;
                                    }else if(s.getEndPos() > r.getStartPos() + subsEnd){
                                        break;
                                    }
                                    Integer excludeStart = s.getStartPos() - r.getStartPos()
                                             - subsStart - 1;
                                    Integer excludeEnd = s.getEndPos() - r.getStartPos()
                                            - subsStart - 1;
                                    Integer excludeLength = 1 +  excludeEnd - excludeStart;
                                    if (onMinusStrand){
                                        excludeStart = dnaTarget.length() - excludeStart
                                                - excludeLength;
                                    }
                                    if (excludeStart + excludeLength < dnaTarget.length()){
                                        excludeRegions.add(excludeStart + "," + excludeLength);
                                    }else if (excludeStart < dnaTarget.length() - 1 ){
                                        int diff = dnaTarget.length() -1 - excludeStart;
                                        excludeRegions.add(excludeStart + "," + diff);
                                    }
                                }
                                //get info from text fields for primer3 options
                                String target = Integer.toString(flanks - designBuffer) + 
                                        "," + Integer.toString(tEnd - tStart + (designBuffer * 2));
                                String seqid = (er.getName() + ": " + er.getId());
                                //design primers
                                updateMessage("Designing primers for part " + exonNumber + 
                                        " of " + exonRegions.size() + "...");
                                ArrayList<String> result = designPrimers(seqid, 
                                        dnaTarget.toString(), target, String.join(" ", excludeRegions));

                                updateProgress(prog, 100);
                                designs.add(String.join("\n", result));

                                //parse primer3 output and write our output
                                primers.add(parsePrimer3Output(++pair,  er.getName(), er.getId(), 
                                        r.getChromosome(), 1 + er.getStartPos() - flanks, result));

                            }

                        }
                        HashMap<String, ArrayList> primerResult = new HashMap<>();
                        ArrayList<HashMap<String, String>> refList = new ArrayList<>();
                        refList.add(referenceSeqs);
                        primerResult.put("primers", primers);
                        primerResult.put("design", designs);
                        primerResult.put("ref", refList);
                        return primerResult;
                    }
                };
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().bind(designTask.progressProperty());
                progressLabel.textProperty().bind(designTask.messageProperty());

                designTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
                    @Override
                    public void handle (WorkerStateEvent e){
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().set(100);
                        progressLabel.textProperty().unbind();
                        setRunning(false);
                        HashMap<String, ArrayList> result = 

                                (HashMap<String, ArrayList>) e.getSource().getValue();
                        if (result == null){
                            return;
                        }
                        if (result.get("primers").isEmpty()){
                            progressLabel.setText("No primers designed.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No primers found!");
                        alert.setHeaderText("No primers found for your targets.");
                        alert.setContentText("No primer designs were attempted for your targets");
                        alert.showAndWait();  
                        } 
                                 progressIndicator.progressProperty().set(0);
                            return;
                        }
                        if (result.get("design").isEmpty()){
                            progressLabel.setText("No primers designed.");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No primers found!");
                        alert.setHeaderText("No primers found for your targets.");
                        alert.setContentText("No primer designs were attempted for your targets");
                        alert.showAndWait();  
                        } 
                           progressIndicator.progressProperty().set(0);
                            return;
                        }
                        progressLabel.setText(result.get("primers").size() +
                                " primer pairs designed.");
                        FXMLLoader tableLoader;
                        if (System.getProperty("os.name").equals("Mac OS X")){
                            tableLoader = new FXMLLoader(getClass().
                                               getResource("Primer3ResultViewMac.fxml"));
                        }else{
                             tableLoader = new FXMLLoader(getClass().
                                               getResource("Primer3ResultView.fxml"));
                        }
                        try{
                            Pane tablePane = (Pane) tableLoader.load();
                            Primer3ResultViewController resultView = 
                                    (Primer3ResultViewController) tableLoader.getController();
                            Scene tableScene = new Scene(tablePane);
                            Stage tableStage = new Stage();
                            tableStage.setScene(tableScene);
                            //tableScene.getStylesheets().add(AutoPrimer3A.class
                            //        .getResource("autoprimer3A.css").toExternalForm());
                            resultView.displayData(result.get("primers"), 
                                    result.get("design"), 
                                    (HashMap<String, String>) result.get("ref").get(0));
                            tableStage.setTitle("AutoPrimer3A Results");
                            tableStage.getIcons().add(new Image(this.getClass()
                                    .getResourceAsStream("icon.png")));
                            tableStage.initModality(Modality.NONE);
                            tableStage.show();
                       }catch (Exception ex)
                       {
                        
                           Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Design Failed!");
                        alert.setContentText("AutoPrimer3A encountered an error designing "
                                + "attempting to display your results. See exception below.");
                        alert.showAndWait();       
                   }
                
            });
                
                designTask.setOnCancelled(new EventHandler<WorkerStateEvent>(){
                    
                    @Override
                    
                    public void handle (WorkerStateEvent e){
                        
                        setRunning(false);
                        progressLabel.textProperty().unbind();
                        progressLabel.setText("Design cancelled");
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().set(0);
                    }
                
        }
                designTask.setOnFailed(new EventHandler<WorkerStateEvent>(){
                
                    @Override
                    
                    public void handle (WorkerStateEvent e){
                    
                    setRunning(false);
                        progressLabel.textProperty().unbind();
                        progressLabel.setText("Design failed!");
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().set(0);
                    }
                        if (e.getSource().getException() instanceof SQLException){
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Retrieving Genes/SNPs");
                        alert.setContentText("Failed to retrieve information from UCSC. "
                                        + "See exception below.");
                        alert.showAndWait();                           
                        
                        }else{
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Design Failed!");
                        alert.setContentText("AutoPrimer3A encountered an error designing "
                                + "primers to your targets. See exception below.");
                        alert.showAndWait();
                }                           
                cancelButton.setOnAction(new EventHandler<ActionEvent>()){                   
                    @Override                   
                    public void handle(ActionEvent actionEvent){                    
                    designTask.cancel();
                    }
                
                setRunning(true);

                }
                
new Thread(designTask).start();
            }    
                }
        geneSearchTask.setOnCancelled(new EventHandler<WorkerStateEvent>()){
                }
            @Override
            public void handle (WorkerStateEvent e){
                setRunning(false);
                progressLabel.textProperty().unbind();
                progressLabel.setText("Design cancelled");
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().set(0);
            }
        }
        geneSearchTask.setOnFailed(new EventHandler<WorkerStateEvent>()){
            
                    @Override
            public void handle (WorkerStateEvent e){
                setRunning(false);
                progressLabel.textProperty().unbind();
                progressLabel.setText("Search failed!");
                progressIndicator.progressProperty().unbind();
                progressIndicator.progressProperty().set(0);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Gene Search Failed!");
                        alert.setContentText("AutoPrimer3A encountered an error when searching "
                    + "for gene targets. See exception below.");
                        alert.showAndWait();
                }        
 
        )
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                geneSearchTask.cancel();
            }
        });
        setRunning(true);
        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(geneSearchTask.progressProperty());
        progressLabel.textProperty().unbind(); 
        progressLabel.textProperty().bind(geneSearchTask.messageProperty());
        new Thread(geneSearchTask).start();       
    }            
    private String createReferenceSequence(String dna, int offset, int flanks);            
            ArrayList<GenomicRegionSummary> exons, boolean revComp){        
        StringBuilder dnaTarget = new StringBuilder();
        int prevEnd = 0;
        for (int i = 0; i < exons.size(); i++){
            int tStart = exons.get(i).getStartPos() - offset;
            int tEnd = 1 + exons.get(i).getEndPos() - offset;
            int subsStart = tStart - flanks > 0 ? tStart - flanks : 0;
            int subsEnd = tEnd + flanks - 1 < dna.length() ?  tEnd + flanks - 1 : dna.length();
            //make sure we don't overlap end with next exon region
            if (i < exons.size() -1){
                subsEnd = subsEnd < exons.get(i + 1).getStartPos() - offset ? 
                        subsEnd : exons.get(i + 1).getStartPos() - offset;
            }
            //make sure we don't overlap current flanks with previous flanks
            if (prevEnd > 0){
                if (prevEnd > subsStart){
                    subsStart = prevEnd;
                }
            }
            prevEnd = subsEnd;
            dnaTarget.append(
                    dna.substring(subsStart, tStart).toLowerCase());
            dnaTarget.append(dna.substring(tStart, tEnd-1)
                    .toUpperCase());
            dnaTarget.append(dna.substring(tEnd -1, subsEnd)
                    .toLowerCase());
        }
        if (revComp){
            return reverseComplement(dnaTarget.toString());
        }else{
            return dnaTarget.toString();
        }
    }
    
    /*dup will always be an unedited gene name
    we need to check whether we already have made an '(alt)' version
    by checking in dupStorer
    */
    private String checkDuplicate(String dup, HashSet<String> dupStorer){
        String dedupped;
        if (dupStorer.contains(dup)){
           dedupped =  dup + "(alt)";
           if (dupStorer.contains(dedupped)){
               for (int i = 1; i < 999; i++){
                   dedupped = dup + "(alt" + i + ")";
                   if (!dupStorer.contains(dedupped)){
                       break;
                   }
               }
           }
           dupStorer.add(dedupped);
           return dedupped;
        }else{
            dupStorer.add(dup);
            return dup;
        }
    }
    
    private void numberExons(ArrayList<GenomicRegionSummary> exonRegions,
            boolean minusStrand){
        int n = 0;
        if (minusStrand){
            Collections.reverse(exonRegions);
        }
        
        for (GenomicRegionSummary e: exonRegions){
            e.setName(e.getName() + "_ex" + (n+1));
            n++;
        }
        if (minusStrand){//back to original order
            Collections.reverse(exonRegions);
        }
    }
    
    private ArrayList<GenomicRegionSummary> splitLargeRegionsMergeSmallRegions(
            ArrayList<GenomicRegionSummary> regions, Integer optSize, 
            Integer buffer, boolean minusStrand){
        ArrayList<GenomicRegionSummary> splitRegions = new ArrayList<>();
        for (GenomicRegionSummary r: regions){
            if (r.getLength() > optSize){
                //divide length by maxSize to determine no of products to make
                Double products = Math.ceil(r.getLength().doubleValue()/ 
                        optSize.doubleValue());
                if (products.intValue() < 2){
                    splitRegions.add(r);
                    continue;
                }
                //divide length by no. products and make each product
                Double productSize = r.getLength().doubleValue()/products;
                for (int i = 0; i < products.intValue(); i++){
                    int increment = i * productSize.intValue();
                    int startPos = r.getStartPos() + increment;
                    int endPos = startPos + productSize.intValue();
                    endPos = endPos < r.getEndPos() ? endPos : r.getEndPos();
                    String name;
                    if (minusStrand){
                        name = r.getName() + "_part" + (products.intValue() - i);
                    }else{
                        name = r.getName() + "_part" + (i+1);
                    }
                    ArrayList<String> ids = new ArrayList<>(); 
                    for (String id : r.getId().split("/")){
                        if (minusStrand){
                            ids.add(id + "_part" + (products.intValue() - i));
                        }else{
                            ids.add(id + "_part" + (i+1));
                        }
                    }
                    GenomicRegionSummary s = new GenomicRegionSummary(
                        r.getChromosome(), startPos, endPos,
                        r.getStartId(), r.getEndId(), String.join("/", ids), name);
                    if (i == products.intValue() - 1){
                        s.setEndPos(r.getEndPos());
                    }
                    splitRegions.add(s);
                }
            }else{
                splitRegions.add(r);
            }
        }
        ArrayList<GenomicRegionSummary> splitAndMergedRegions = new ArrayList<>();
        boolean smallRegion;
        do{
            smallRegion = false;
            splitAndMergedRegions.clear();
            for (int i = 0; i < splitRegions.size() - 1; i++){
                //merge any small and close regions
                if (! splitRegions.get(i).getChromosome().equals(
                        splitRegions.get(i+1).getChromosome())){
                    splitAndMergedRegions.add(splitRegions.get(i));
                    continue;
                }
                int gap = splitRegions.get(i+1).getEndPos() - splitRegions.get(i).getStartPos();
                if (gap + (2*buffer)  <= optSize){
                    smallRegion = true;
                    String chrom = splitRegions.get(i).getChromosome();
                    int start = splitRegions.get(i).getStartPos();
                    int end = splitRegions.get(i+1).getEndPos();
                    String name = mergeNames(splitRegions.get(i).getName(), 
                            splitRegions.get(i+1).getName());
                    String id = mergeIds(splitRegions.get(i).getId(), 
                            splitRegions.get(i + 1).getId());
                    splitAndMergedRegions.add(new GenomicRegionSummary(chrom, 
                            start, end, null, null, id, name));
                    for (int j = i +2; j < splitRegions.size(); j++){
                        splitAndMergedRegions.add(splitRegions.get(j));
                    }
                    splitRegions.clear();
                    splitRegions.addAll(splitAndMergedRegions);
                    break;
                }else{
                    splitAndMergedRegions.add(splitRegions.get(i));
                }
            }
            splitAndMergedRegions.add(splitRegions.get(splitRegions.size()-1));
        }while (smallRegion);
        return splitAndMergedRegions;
    }
    
    //create a new id from two genomic regions' ids
    private String mergeIds(String id1, String id2){
        ArrayList<String> merged = new ArrayList<>();
        List<String> ids1 = Arrays.asList(id1.split("/"));
        List<String> ids2 = Arrays.asList(id2.split("/"));
        LinkedHashMap<String, String> idToEx1 = new LinkedHashMap<>();
        LinkedHashMap<String, String> idToEx2 = new LinkedHashMap<>();
        for (String d: ids1){
            List<String> split = Arrays.asList(d.split("_ex"));
            if (split.size() > 1){
                idToEx1.put(split.get(0), split.get(1));
            }else{
                idToEx1.put(d, d);
            }
        }
        for (String d: ids2){
            List<String> split = Arrays.asList(d.split("_ex"));
            if (split.size() > 1){
                idToEx2.put(split.get(0), split.get(1));
            }else{
                idToEx2.put(d, d);
            }
        }
        for (String d: idToEx1.keySet()){
            if (idToEx2.containsKey(d)){
                merged.add(d + "_ex" + idToEx1.get(d) + "-" + idToEx2.get(d));
            }else{
                if (d.equals(idToEx1.get(d))){//if regions key and value will be identical
                    merged.add(d);
                }else{//if exons value will be exon number
                    merged.add(d + "_ex" + idToEx1.get(d));
                }
            }
        }
        for (String d: idToEx2.keySet()){
            if (! idToEx1.containsKey(d)){   
                if (d.equals(idToEx2.get(d))){//if regions key and value will be identical
                    merged.add(d);
                }else{//if exons value will be exon number
                    merged.add(d + "_ex" + idToEx2.get(d));
                }
            }
        }
        return String.join("/", merged);
    }


    //create a new name from two genomic regions' names
    private String mergeNames(String name1, String name2){
        String name;
        List<String> geneName1 = Arrays.asList(name1.split("_ex"));
        List<String> geneName2 = Arrays.asList(name2.split("_ex"));
        if (geneName1.size() >= 2 && geneName2.size() >= 2 && 
                geneName1.get(0).equalsIgnoreCase(geneName2.get(0))){
            ArrayList<Integer> sizes = new ArrayList<>();
            for (int i = 1; i < geneName1.size(); i++){
                sizes.add(Integer.valueOf(geneName1.get(i)));
            }
            for (int i = 1; i < geneName2.size(); i++){
                sizes.add(Integer.valueOf(geneName2.get(i)));
            }
            Collections.sort(sizes);
            name = geneName1.get(0) + "_ex" + sizes.get(0) + 
                    "-" + sizes.get(sizes.size()-1);
        }else{
            name = name1 + "/" +  name2;
        }
        return name;
    }
    
    //get left and right primer from Primer3 output
    private Primer3Result parsePrimer3Output(int index, String name, String id,
            String chrom, int baseCoordinate, ArrayList<String> output){
        String db = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
        final Hyperlink pcrLink = new Hyperlink();
        pcrLink.setText("in-silico PCR");
        pcrLink.setTextFill(Color.BLUE);
        pcrLink.setDisable(true);
        String left = "NOT FOUND";
        String right = "NOT FOUND";
        Integer lpos = 0;
        Integer rpos = 0;
        Integer leftStart = 0;
        Integer rightStart = 0;
        String productSize = "0";
        for (String res: output){
            if (res.startsWith("LEFT PRIMER")){
                List<String> split = Arrays.asList(res.split(" +"));
                left = split.get(split.size() -1);
                leftStart = Integer.valueOf(split.get(2));
            }else if (res.startsWith("RIGHT PRIMER")){
                List<String> split = Arrays.asList(res.split(" +"));
                right = split.get(split.size() -1);
                rightStart = Integer.valueOf(split.get(2));
            }else if (res.startsWith("PRODUCT SIZE:")){
                List<String> split = Arrays.asList(res.split(" +"));
                productSize = split.get(2).replaceAll("[^\\d/]", "");
                break;
            }
        }
        Primer3Result res = new Primer3Result();
        res.setLeftPrimer(left);
        res.setRightPrimer(right);
        res.setName(name);
        res.setTranscripts(id);
        res.setIndex(index);
        res.setProductSize(Integer.valueOf(productSize));
        
        if (Integer.valueOf(productSize) > 0){
            Integer wpSize = 4000 > Integer.valueOf(productSize) * 2 ? 
                    4000 : Integer.valueOf(productSize) * 2;
            lpos = baseCoordinate + leftStart;
            rpos = baseCoordinate + rightStart;
            final String pcrUrl = serverUrl + "/cgi-bin/hgPcr?db=" + db + 
                    "&wp_target=genome&wp_f=" + left + "&wp_r=" + right + 
                    "&wp_size=" + wpSize + 
                    "&wp_perfect=15&wp_good=15&boolshad.wp_flipReverse=0";
            pcrLink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    getHostServices().showDocument(pcrUrl);
                    pcrLink.setVisited(true);
                    pcrLink.setUnderline(false);
                }
            });
            pcrLink.setDisable(false);
            pcrLink.setUnderline(true);
            res.setIsPcrLink(pcrLink);
            res.setIsPcrUrl(pcrUrl);
            String region = chrom + ":" + lpos + "-" + rpos;
            final String regionUrl = serverUrl + "/cgi-bin/hgTracks?db=" + db + 
                    "&position=" + region;
            final Hyperlink regionLink = new Hyperlink();
            regionLink.setText(region);
            regionLink.setTextFill(Color.BLUE);
            regionLink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    getHostServices().showDocument(regionUrl);
                    pcrLink.setVisited(true);
                    pcrLink.setUnderline(false);
                }
            });
            res.setRegion(region);
            res.setRegionLink(regionLink);
        }else{
            res.setIsPcrLink(null);
            res.setIsPcrUrl(null);
            res.setRegion(null);
        }
        return res;
    }
    
    
    //for given parameters design primers and return result as an array of strings
    private ArrayList<String> designPrimers(String name, String dna, 
            String target, String exclude) throws IOException{
        ArrayList<String> result = new ArrayList<>();
        StringBuilder error = new StringBuilder(); 
        StringBuilder p3_job = new StringBuilder("SEQUENCE_TARGET=");
            p3_job.append(target).append("\n");
            p3_job.append("SEQUENCE_EXCLUDED_REGION=").append(exclude).append("\n");
            p3_job.append("SEQUENCE_ID=").append(name).append("\n");
            p3_job.append("SEQUENCE_TEMPLATE=").append(dna).append("\n");
            p3_job.append("PRIMER_TASK=pick_pcr_primers\n");
            p3_job.append("PRIMER_OPT_SIZE=").append(optSizeTextField.getText())
                    .append("\n");
            p3_job.append("PRIMER_MIN_SIZE=").append(minSizeTextField.getText())
                    .append("\n");
            p3_job.append("PRIMER_MAX_SIZE=").append(maxSizeTextField.getText())
                    .append("\n");
            p3_job.append("PRIMER_PRODUCT_SIZE_RANGE=")
                    .append(sizeRangeTextField.getText()).append("\n");
            p3_job.append("PRIMER_MIN_TM=")
                    .append(minTmTextField.getText()).append("\n");
            p3_job.append("PRIMER_OPT_TM=")
                    .append(optTmTextField.getText()).append("\n");
            p3_job.append("PRIMER_MAX_TM=")
                    .append(maxTmTextField.getText()).append("\n");
            p3_job.append("PRIMER_PAIR_MAX_DIFF_TM=")
                    .append(maxDiffTextField.getText()).append("\n");
            p3_job.append("PRIMER_THERMODYNAMIC_PARAMETERS_PATH=").
                    append(thermoConfig.toString())
                    .append(System.getProperty("file.separator")).append("\n");
            String misprimeLibrary = (String) 
                misprimingLibraryChoiceBox.getSelectionModel().getSelectedItem();
            if (!misprimeLibrary.isEmpty()){
                if (! misprimeLibrary.matches("none")){
                    p3_job.append("PRIMER_MISPRIMING_LIBRARY=")
                            .append(misprimeDir.toString())
                            .append(System.getProperty("file.separator"))
                            .append(misprimeLibrary).append("\n");
                    p3_job.append("PRIMER_MAX_LIBRARY_MISPRIMING=")
                            .append(maxMisprimeTextField.getText()).append("\n");
            
                }
            }            
            p3_job.append("=");
            System.out.println(p3_job.toString());//debug only
            ArrayList<String> command = new ArrayList<>();
            command.add(primer3ex.getAbsolutePath());
            command.add("-format_output");
            Process ps = new ProcessBuilder(command).start();
            try{
                BufferedReader errorbuf = new BufferedReader
                        (new InputStreamReader( ps.getErrorStream()));
                BufferedReader inbuf = new BufferedReader
                        (new InputStreamReader( ps.getInputStream()));
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(ps.getOutputStream()));
                out.write(p3_job.toString());
                out.flush();
                out.close();

                String line;
                while ((line = inbuf.readLine()) != null) {
                    //System.out.println(line);//debug only
                    result.add(line);
                }
                while ((line = errorbuf.readLine()) != null){
                    //System.out.println(line);//debug only
                    error.append(line);
                }
                int exit = ps.waitFor();
                //System.out.println(exit);//debug only
            }catch(InterruptedException ex){
                ex.printStackTrace();
                /*this should have been caused by user pressing cancel
                could do with a way of checking though and throwing if 
                not caused by user cancel
                */
            }
        return result;
    }
    
    /*this method gets the start coordinates of a gene based on 
    the values for the designToChoiceBox and the Flanking region choice box
    */
        
    private int getGeneStart(GeneDetails g, int flanks){
        int start;
        if (designToChoiceBox.getSelectionModel().getSelectedItem().equals("Coding regions")){
            start = g.getCdsStart();
        }else{
            start = g.getTxStart();
        }
        start -= flanks;
        if (start > 0){
            return start;
        }else{
            return 0;
        
    }
}
/*this method gets the end coordinates of a gene based on 
    the values for the designToChoiceBox and the Flanking region choice box
    */
        
    private int getGeneEnd(GeneDetails g, int flanks){
        int end;
        if (designToChoiceBox.getSelectionModel().getSelectedItem().equals("Coding regions")){
            end = g.getCdsEnd();
        }else{
            end = g.getTxEnd();
        }
        end += flanks;
        return end;
}    

    private GetGeneCoordinates getGeneSearcher(){
        if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("refGene") 
                || databaseChoiceBox.getSelectionModel().getSelectedItem().equals("xenoRefGene")){
            return new GetGeneCoordinates();
        }else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("knownGene")){
            return new GetUcscGeneCoordinates();
        }else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("ensGene")){
            return new GetEnsemblGeneCoordinates();
        }else{
            return null;
}               
    
    }    
    private ArrayList<GeneDetails> getGeneDetails(String searchString, 
            GetGeneCoordinates geneSearcher) 
            throws SQLException, GetGeneCoordinates.GetGeneExonException, GetGeneCoordinates.GetGeneFromIDException, GetGeneCoordinates.GetGeneFromSymbolException{
        ArrayList<GeneDetails> genes = new ArrayList<>();
        if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("refGene") 
                || databaseChoiceBox.getSelectionModel().getSelectedItem().equals("xenoRefGene")){
            if (searchString.matches("[NX][MR]_\\w+(.\\d)*")){
                //is accession, need to remove the version number if present
                searchString = searchString.replaceAll("\\.\\d$", "");
                genes.addAll(geneSearcher.getGeneFromId(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
                
            }else{
                //is gene symbol (?)
                genes.addAll(geneSearcher.getGeneFromSymbol(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
}           
            
        }else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("knownGene")){
            if (searchString.matches("uc\\d{3}[a-z]{3}\\.\\d")){
                //is accession
                genes.addAll(geneSearcher.getGeneFromId(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
            }else{
                //is gene symbol (?)
                genes.addAll(geneSearcher.getGeneFromSymbol(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
            
        }
            {
            else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("ensGene")){
            if (searchString.matches("ENS\\w*T\\d{11}.*\\d*")){
                //is accession
                genes.addAll(geneSearcher.getGeneFromId(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
            }else{
                //is gene symbol (?)
                genes.addAll(geneSearcher.getGeneFromSymbol(searchString, 
                        (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                        (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
/*if (t.equals("refGene") || t.equals("knownGene") || 
                            t.equals("ensGene") || t.equals("xenoRefGene"))*/
        for (int i = 0; i < genes.size(); i++){
            System.out.println(genes.get(i).getSymbol() + ":" + genes.get(i).getId()
                     + ":" + genes.get(i).getChromosome() + ":" + genes.get(i).getTxStart()
                     + "-" + genes.get(i).getTxEnd());
                    }
        return genes;
        }                           
    public Boolean 
        getCanRun(){
        return CANRUN;
    }  /**
 * The main() method
  */

        }
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.show();    }
    public static void main(String[] args) {
        launch(args);
    
}