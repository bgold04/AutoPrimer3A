/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk> and edited By Bert Gold, PhD
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;


/**
 * FXML Controller class
 *
 * @author david
 */
public class Primer3ResultViewController implements Initializable {

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
public String os;
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
//public String genome;
public String t;
public String regions;
public Object document;
public Object node;
public String inf;
public Object wFile;
    
    
   @FXML
   AnchorPane resultPane;
   @FXML
   MenuBar menuBar;
   @FXML
   MenuItem writeFileMenuItem;
   @FXML
   MenuItem writeDesignMenuItem;
   @FXML
   MenuItem writeRefsMenuItem;
   @FXML
   MenuItem closeMenuItem;
   @FXML
   TableView<Primer3Result> primerTable;
   @FXML
   TableColumn indexCol;
   @FXML
   TableColumn nameCol;
   @FXML
   TableColumn idCol;
   @FXML
   TableColumn leftPrimerCol;
   @FXML
   TableColumn rightPrimerCol;
   @FXML
   TableColumn productSizeCol;
   @FXML
   TableColumn regionCol;
   @FXML
   TableColumn ispcrCol;
   @FXML
   TableColumn ispcrResCol;
   @FXML
   Button checkIsPcrButton;
   @FXML
   Button closeButton;
   @FXML 
   Label summaryLabel;
   @FXML
   TextArea designTextSummary;
   @FXML
   TextArea referenceTextArea;
   @FXML
   Tab refTab;
   @FXML
   ChoiceBox refChoiceBox;
   @FXML
   ProgressBar progressBar;
   

NumberFormat nf = NumberFormat.getNumberInstance();
   CoordComparator coordCompare = new CoordComparator();
   HashMap<String, String> refSeqs;
   String summary = null; 
   
public class FileHandler {

    public void showFileConfirmationDialog(File file) {
        // Create the confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("File Confirmation");
        confirmAlert.setHeaderText("Do you want to open the file?");
        confirmAlert.setContentText("The file was successfully created. Would you like to open it now?");

        // Add Yes and No buttons to the dialog
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmAlert.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and capture the user's response
        Optional<ButtonType> result = confirmAlert.showAndWait();
        
      if (result.isPresent() && result.get() == yesButton) {
    try {
        openFile(file);
    } catch (IOException ex) {
        // Show an error dialog if opening the file fails
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Open Failed");
        errorAlert.setHeaderText("Could not open output file");
        errorAlert.setContentText(
            "An exception was encountered when attempting to open the saved file. See details below:\n\n" +
            ex.getMessage()
        );
        errorAlert.showAndWait();
        ex.printStackTrace(); // Log the exception for debugging
    }
}
    }
    public void openFile(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + file.getAbsolutePath());
        }

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows: Use cmd to open the file
            new ProcessBuilder("cmd", "/c", "start", "\"\"", file.getAbsolutePath()).start();
        } else if (os.contains("mac")) {
            // macOS: Use the 'open' command
            new ProcessBuilder("open", file.getAbsolutePath()).start();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            // Linux: Use the 'xdg-open' command
            new ProcessBuilder("xdg-open", file.getAbsolutePath()).start();
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + os);
        }
    }
}
   
   private final ObservableList<Primer3Result> data = FXCollections.observableArrayList();
   
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.setUseSystemMenuBar(true);
        progressBar.setVisible(false);
        
        indexCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, Integer>("index"));
        leftPrimerCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, String>("leftPrimer"));
        nameCol.setCellValueFactory(new
                PropertyValueFactory<Primer3Result, String>("name"));
        idCol.setCellValueFactory(new
                PropertyValueFactory<Primer3Result, String>("transcripts"));
        rightPrimerCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, String>("rightPrimer"));
        productSizeCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, Integer>("productSize"));
        regionCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, String>("regionLink"));
        ispcrCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, Hyperlink>("isPcrLink"));
        ispcrResCol.setCellValueFactory(new 
                PropertyValueFactory<Primer3Result, Hyperlink>("isPcrResults"));
        ispcrResCol.setVisible(false);
        primerTable.getSortOrder().add(indexCol);
        primerTable.getSortOrder().add(regionCol);
        primerTable.getSortOrder().add(productSizeCol);
        regionCol.setComparator(coordCompare);
        primerTable.getSelectionModel().setCellSelectionEnabled(true);
        primerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refTab.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed (ObservableValue ov, Boolean value, final Boolean newValue){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        refChoiceBox.setVisible(newValue);
                    }
                });
            }
        });
    }
    private void writePrimersToFile() throws IOException {
    // Example logic to write primers to a file
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"),
        new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"),
        new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx")
    );
    fileChooser.setTitle("Save Primer File");
    File file = fileChooser.showSaveDialog(null); // Replace null with the parent window if available

    if (file != null) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Replace this with actual data
            String examplePrimerData = "Primer1\tSequence1\nPrimer2\tSequence2";

            writer.write(examplePrimerData);
        } catch (IOException ex) {
            throw new IOException("Error writing primers to file: " + ex.getMessage(), ex);
        }
    } else {
        throw new IOException("File selection was canceled or invalid.");
    }

writeFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        private Object result;
    @Override
    public void handle(ActionEvent event) {
        try {
            writePrimersToFile(); // Ensure this method is properly defined in your class
        } catch (final Exception ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Create an Alert to replace the deprecated Dialogs.create()
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Writing Failed");
                    alert.setHeaderText("Could not write primers to file");
                    alert.setContentText("Exception encountered when attempting to write primers to file. See below:");
                
                    // Display exception details in an expandable section
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String exceptionText = sw.toString();
            
                    TextArea textArea = new TextArea(exceptionText);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
            
                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
            
                    GridPane content = new GridPane();
                    content.setMaxWidth(Double.MAX_VALUE);
                    content.add(new Label("The exception stack trace was:"), 0, 0);
                    content.add(textArea, 0, 1);
            
                    alert.getDialogPane().setExpandableContent(content);
                    alert.showAndWait();
                }
            });
                    }
    }
        
private void openFile(File file) throws IOException {
    if (file == null) {
        throw new IllegalArgumentException("File cannot be null");
    }

    if (!file.exists()) {
        throw new FileNotFoundException("File does not exist: " + file.getAbsolutePath());
    }

    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
        // Windows: Use cmd to open the file
        new ProcessBuilder("cmd", "/c", "start", "\"\"", file.getAbsolutePath()).start();
    } else if (os.contains("mac")) {
        // macOS: Use the 'open' command
        new ProcessBuilder("open", file.getAbsolutePath()).start();
    } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
        // Linux: Use the 'xdg-open' command
        new ProcessBuilder("xdg-open", file.getAbsolutePath()).start();
    } else {
        throw new UnsupportedOperationException("Unsupported operating system: " + os);
    }
}

        private void writeDesignToFile() {
    // Check if the designTextSummary is empty
    if (designTextSummary.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Nothing to save", "No designs to save", 
                  "No primer designs were made, no file can be saved.");
        return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt"));
    fileChooser.setTitle("Write primer designs to file...");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    File wFile = fileChooser.showSaveDialog(resultPane.getScene().getWindow());
    if (wFile == null) {
        return;
    }

    // Ensure file has .txt extension
    if (!wFile.getName().endsWith(".txt")) {
        wFile = new File(wFile.getAbsolutePath() + ".txt");
    }
    // Write designs to the file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(wFile))) {
        String[] designs = designTextSummary.getText().split("\n");
        for (String design : designs) {
            bw.write(design);
            bw.newLine();
        }
    } catch (IOException ex) {
        showExceptionAlert("Writing Failed", "Could not write primer designs to file", ex);
        return;
    }

    // Display confirmation dialog
    Optional<ButtonType> result = showConfirmationDialog("Done", "Finished writing",
            "Primer designs successfully written to " + wFile.getAbsolutePath() +
            "\n\nDo you want to open this file now?");
    
    if (result.isPresent() && result.get() == ButtonType.YES) {
        openFile(wFile);
    }
}
private Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);

    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    return alert.showAndWait();
}

private void showAlert(Alert.AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}

private void showExceptionAlert(String title, String header, Exception ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText("An exception occurred: " + ex.getMessage());

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String exceptionText = sw.toString();

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(textArea, 0, 0);

    alert.getDialogPane().setExpandableContent(expContent);
    alert.showAndWait();
}
    private String splitStringOnLength(String s, Integer n, String sep){
        StringBuilder split = new StringBuilder();
        for (int i = 0; i < s.length(); i += n){
            split.append(s.substring(i, Math.min(i + n, s.length())));
            split.append(sep);
        }
        return split.toString();
    }
// Initialize Method
private void initialize() {
    writePrimersToFile.setOnAction(event -> {
        try {
            writePrimersToFile();
        } catch (Exception ex) {
            if (data.isEmpty()) {
                Platform.runLater(() -> {
                    // Show alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nothing to save");
                    alert.setHeaderText("No primers to save");
                    alert.setContentText("No primers were designed, no file can be saved.");
                    alert.showAndWait();

                    // File chooser logic
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"),
                        new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"),
                        new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt")
                    );
                    fileChooser.setTitle("Write primers to file...");
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    File wFile = fileChooser.showSaveDialog(resultPane.getScene().getWindow());

                    if (wFile == null) {
                        return;
                    }

                    // Handle missing extension
                    if (!wFile.getName().endsWith(".xlsx") &&
                        !wFile.getName().endsWith(".csv") &&
                        !wFile.getName().endsWith(".txt")) {
                        String ext = fileChooser
                            .selectedExtensionFilterProperty()
                            .get()
                            .getExtensions()
                            .get(0)
                            .substring(1);
                        wFile = new File(wFile.getAbsolutePath() + ext);
                    }

                    // Save file logic
                    if (wFile.getName().endsWith(".xlsx")) {
                        writePrimersToExcel(wFile);
                    } else if (wFile.getName().endsWith(".csv")) {
                        writePrimersToCsv(wFile);
                    } else {
                        writePrimersToTsv(wFile);
                    }
                });
            }
        }
    });
}
// write PrimersToExcel method 
private void writePrimersToExcel(final File f) throws IOException {
    final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws IOException {
                    try (BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(f))) {
                        Workbook wb = new XSSFWorkbook();
                        CellStyle hlinkStyle = wb.createCellStyle();
                        Font hlinkFont = wb.createFont();
                        hlinkFont.setUnderline(Font.U_SINGLE);
                        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
                        hlinkStyle.setFont(hlinkFont);
                        CreationHelper createHelper = wb.getCreationHelper();

                        // Create sheets
                        Sheet listSheet = wb.createSheet("List");
                        Sheet detailsSheet = wb.createSheet("Details");

                        // Write header for List sheet
                        Row row = listSheet.createRow(0);
                        String[] header = {"Primer", "Sequence", "Product Size (bp)"};
                        for (int col = 0; col < header.length; col++) {
                            row.createCell(col).setCellValue(header[col]);
                        }

                        // Write data to List sheet
                        int rowNo = 1;
                        for (Primer3Result r : data) {
                            updateMessage("Writing primer list...");
                            Row dataRow = listSheet.createRow(rowNo++);
                            dataRow.createCell(0).setCellValue(r.getName() + "F");
                            dataRow.createCell(1).setCellValue(r.getLeftPrimer());
                            dataRow.createCell(2).setCellValue(r.getProductSize());

                            dataRow = listSheet.createRow(rowNo++);
                            dataRow.createCell(0).setCellValue(r.getName() + "R");
                            dataRow.createCell(1).setCellValue(r.getRightPrimer());
                            dataRow.createCell(2).setCellValue(r.getProductSize());
                        }

                        // Write header for Details sheet
                        rowNo = 0;
                        Row detailsHeaderRow = detailsSheet.createRow(rowNo++);
                        String[] detailsHeader = {
                            "Name", "Other IDs", "Left Primer", "Right Primer",
                            "Product Size (bp)", "Region", "in-silico PCR"
                        };
                        for (int col = 0; col < detailsHeader.length; col++) {
                            detailsHeaderRow.createCell(col).setCellValue(detailsHeader[col]);
                        }

                        // Write details data
                        for (Primer3Result r : data) {
                            Row detailsRow = detailsSheet.createRow(rowNo++);
                            detailsRow.createCell(0).setCellValue(r.getName());
                            detailsRow.createCell(1).setCellValue(r.getTranscripts());
                            detailsRow.createCell(2).setCellValue(r.getLeftPrimer());
                            detailsRow.createCell(3).setCellValue(r.getRightPrimer());
                            detailsRow.createCell(4).setCellValue(r.getProductSize());
                            detailsRow.createCell(5).setCellValue(r.getRegion());
                            if (r.getIsPcrUrl() != null) {
                                Cell cell = detailsRow.createCell(6);
                                cell.setCellValue("isPCR");
                                org.apache.poi.ss.usermodel.Hyperlink hyperlink =
                                    createHelper.createHyperlink(Hyperlink.LINK_URL);
                                hyperlink.setAddress(r.getIsPcrUrl());
                                cell.setHyperlink(hyperlink);
                                cell.setCellStyle(hlinkStyle);
                            }
                        }

                        // Save workbook
                        wb.write(bo);
                    }
                    updateMessage("Finished writing to " + f.getAbsolutePath());
                    return null;
                }
            };
        }
    };
    // Start the service
    service.start();
   
    service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done");
        alert.setHeaderText("Finished writing");
        alert.setContentText(
            "Primers successfully written to " + f.getAbsolutePath() + 
            "\n\nDo you want to open this file now?"
        );

        // Add Yes and No buttons
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and get the response
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            try {
                openFile(f);
            } catch (Exception ex) {
                // Show an error dialog if opening the file fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Open Failed");
                errorAlert.setHeaderText("Could not open output file");
                errorAlert.setContentText(
                    "An exception was encountered when attempting to open the saved file. See below:\n\n" + 
                    ex.getMessage()
                );
                errorAlert.showAndWait();
            }
        }
    
                    progressBar.progressProperty().unbind();
                    progressBar.setVisible(false);
                    summaryLabel.textProperty().unbind();
                    summaryLabel.setText(summary);
                    closeButton.setDisable(false);
                    closeMenuItem.setDisable(false);
                    setCheckIsPcrButton();
        service.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Writing Cancelled");
                alert.setHeaderText("Cancelled writing to file");
                alert.setContentText(
                    "User cancelled writing primers to file."
                );

                // Show the alert dialog
                alert.showAndWait();
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        service.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Writing failed");
                alert.setHeaderText("Could not write primers to file");
                alert.setContentText(
                    "Exception encountered when attempting to write "
                            + "primers to file. See below:"
                );
                // Show the alert dialog
                alert.showAndWait();
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(service.progressProperty());
        summaryLabel.textProperty().bind(service.messageProperty());
        closeButton.setDisable(true);
        closeMenuItem.setDisable(true);
        checkIsPcrButton.setText("Cancel");
        checkIsPcrButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                service.cancel();

            }
       });
        service.start();
    }
    
    private void writePrimersToCsv(final File f) throws IOException{
        writePrimersToText(f, ",");
    }
    
    private void writePrimersToTsv(final File f) throws IOException{
        writePrimersToText(f, "\t");
    }
    
    //takes output file and delimiter string as arguments
    private void writePrimersToText(final File f, final String d) throws IOException{
        final Service<Void> service = new Service<Void>(){
            @Override
            protected Task<Void> createTask(){
                return new Task<Void>(){
                    @Override
                    protected Void call() throws IOException {
                        FileWriter fw = new FileWriter(f.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        updateMessage("Writing primers . . .");
                        updateProgress(0, data.size() * 2);
                        int n = 0;
                        for (Primer3Result r: data){
                            n++;
                            updateMessage("Writing primer " + n + " . . .");
                            updateProgress(n, data.size());
                            bw.write(r.getName() + "F" + d + r.getLeftPrimer());
                            bw.newLine();
                            n++;
                            updateMessage("Writing primer " + n + " . . .");
                            updateProgress(n, data.size());
                            bw.write(r.getName() + "R" + d + r.getRightPrimer());
                            bw.newLine();
                        }
                        updateMessage("Wrote " + n + " primers to file.");
                        bw.close();
                        return null;
                    }
                };
            }
            
        };
        
        
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
                @Override
                public void handle (WorkerStateEvent e){
                    
// Display a confirmation dialog
Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
confirmAlert.setTitle("Done");
confirmAlert.setHeaderText("Finished writing");
confirmAlert.setContentText(
    "Primers successfully written to " + f.getAbsolutePath() +
    "\n\nDo you want to open this file now?"
);

// Add Yes and No buttons to the dialog
ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
confirmAlert.getButtonTypes().setAll(yesButton, noButton);

// Show the dialog and capture the user's response
Optional<ButtonType> result = confirmAlert.showAndWait();

if (result.isPresent() && result.get() == yesButton) {
    try {
        // Attempt to open the file
        openFile(f);
    } catch (IOException ex) {
        // Display an error dialog if file opening fails
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Open Failed");
        errorAlert.setHeaderText("Could not open output file");
        errorAlert.setContentText(
            "An exception was encountered when attempting to open the saved file. See details below:\n\n" +
            ex.getMessage()
        );

        // Optionally include the full exception details
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        errorAlert.getDialogPane().setExpandableContent(expContent);

        errorAlert.showAndWait();
    }
}
                    progressBar.progressProperty().unbind();
                    progressBar.setVisible(false);
                    summaryLabel.textProperty().unbind();
                    summaryLabel.setText(summary);
                    closeButton.setDisable(false);
                    closeMenuItem.setDisable(false);
                    setCheckIsPcrButton();
                }
        });
        service.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Writing Cancelled");
                alert.setHeaderText("Cancelled writing to file");
                alert.setContentText(
                    "User cancelled writing primers to file."
                );

                // Show the alert dialog
                alert.showAndWait();                   
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        service.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Writing failed");
                alert.setHeaderText("Could not write primers to file");
                alert.setContentText(
                    "Exception encountered when attempting to write "
                            + "primers to file. See below:"
                );
                // Show the alert dialog
                alert.showAndWait(); 
                progressBar.progressProperty().unbind();
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(service.progressProperty());
        summaryLabel.textProperty().bind(service.messageProperty());
        closeButton.setDisable(true);
        closeMenuItem.setDisable(true);
        checkIsPcrButton.setText("Cancel");
        checkIsPcrButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                service.cancel();

            }
       });
        service.start();
    }
    
    
    private void writeRefSeqsToFile() throws IOException{        
        if (refSeqs.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to save");
            alert.setHeaderText("No reference sequences to save");
            alert.setContentText(
            "No reference sequences created, no file to be saved."
            );

            // Show the alert dialog
            alert.showAndWait();
            return;
        }
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().add(
               new FileChooser.ExtensionFilter("Text  (*.txt)", "*.txt"));
       fileChooser.setTitle("Write reference sequences to file...");
       fileChooser.setInitialDirectory(new File(getProperty("user.home")));
       File wFile = fileChooser.showSaveDialog(resultPane.getScene().getWindow());
       if (wFile == null){
           return;
       }else if (!wFile.getName().endsWith(".txt")){
            //annoying bug with filechooser means extension might not be appended
            wFile = new File(wFile.getAbsolutePath() + ".txt");
       }
        FileWriter fw = new FileWriter(wFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (String id: refSeqs.keySet()){
            bw.write(">" + id);
            bw.newLine();
            bw.write(splitStringOnLength(refSeqs.get(id), 60, System.getProperty("line.separator")));
            bw.newLine();
        }
        bw.close();
        
        // Display a confirmation dialog
Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
confirmAlert.setTitle("Done");
confirmAlert.setHeaderText("Finished writing");
confirmAlert.setContentText(
        "Primers successfully written to " + f.getAbsolutePath() +
        "\n\nDo you want to open this file now?"
);

// Show the dialog and wait for the user's response
Optional<ButtonType> response = confirmAlert.showAndWait();

// Check if the user clicked OK or YES
if (response.isPresent() && response.get() == ButtonType.OK) {
    try {
        // Replace this with your actual file-opening logic
        openFile(wFile);
    } catch (IOException ex) {
        // Show an error dialog if opening the file fails
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Open Failed");
        errorAlert.setHeaderText("Could not open output file");
        errorAlert.setContentText(
            "An exception was encountered when attempting to open the saved file. See details below:\n\n" +
            ex.getMessage()
        );
        errorAlert.showAndWait();
    }
}
    private void checkIsPcrResults() throws MalformedURLException, IOException {        
        final Service<ObservableList<Primer3Result>> service = new Service<ObservableList<Primer3Result>>(){
            @Override
            protected Task<ObservableList<Primer3Result>> createTask(){
                return new Task<ObservableList<Primer3Result>>(){
                    @Override
                    protected ObservableList<Primer3Result> call() throws IOException {
                        final ObservableList<Primer3Result> newData = FXCollections.observableArrayList();
                        updateProgress(0, data.size());
                        int p = 0;
                        for (Primer3Result r: data){
                            p++;
                            updateMessage("Checking primer pair " + p + " . . .");
                            r.setIsPcrResults(0);
                            if (r.getIsPcrUrl() != null){
                                URL url = new URL(r.getIsPcrUrl());
                                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(url.openStream()));
                                String inputLine;
                                while ((inputLine = in.readLine()) != null){
                                    if (inputLine.contains("PcrResult=pack")){
                                        Integer n = r.getIsPcrResults();
                                        r.setIsPcrResults(++n);
                                    }
                                }
                            }
                            updateProgress(p, data.size());
                            newData.add(r);
                        }
                        return newData;
                    }
                };
            }
            
        };
        
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
                @Override
                public void handle (WorkerStateEvent e){
                    ObservableList<Primer3Result> d = 
                            (ObservableList<Primer3Result>) e.getSource().getValue();
                    data.removeAll(data);
                    data.addAll(d);
                    final ArrayList<Primer3Result> nonSpecific = new ArrayList<>();
                    for (Primer3Result r: data){
                        if (r.getIsPcrResults() > 1){
                            nonSpecific.add(r);
                        }
                    }
                    StringBuilder msg = new StringBuilder();
                    if (nonSpecific.size() > 0){
                        msg.append("WARNING: ").append(nonSpecific.size()).append(" primer pair");
                        if (nonSpecific.size() == 1){
                            msg.append( " appears ");
                        }else{
                            msg.append("s appear ");
                        }
                        msg.append("to amplify more than one genomic region.\n\n");
                    }
                    msg.append(d.size()).append(" total primer pairs checked by in-silico PCR");
                    final String message = msg.toString();
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run(){
                            ispcrResCol.setVisible(true);
                            progressBar.progressProperty().unbind();
                            progressBar.setVisible(false);
                            summaryLabel.textProperty().unbind();
                            summaryLabel.setText(summary);
                            writeFileMenuItem.setDisable(false);
                            closeButton.setDisable(false);
                            closeMenuItem.setDisable(false);
                            setCheckIsPcrButton();
 Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Done");
alert.setHeaderText("Finished Checking isPCR Results");
alert.setContentText(
    "Finished Checking isPCR Results"
);                                                       
                            if (nonSpecific.isEmpty()){
                                inf.showInformation();
                            }else{
                                inf.showWarning();
                            }
                        }
                    });
                    
                }
        });
        service.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                
                Platform.runLater(() -> {
    // Create and configure an Alert dialog
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("isPCR Failed");
    alert.setHeaderText("Could not check primer pairs by in-silico PCR");
    alert.setContentText(
        "An exception was encountered when attempting to check primers by in-silico PCR. " +
        "See the details below:"
    );

    // Display the exception stack trace in an expandable section
    Throwable exception = e.getSource().getException(); // Extract the exception from the event source
    if (exception != null) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(new Label("The exception stack trace was:"), 0, 0);
        expContent.add(textArea, 0, 1);

        // Add expandable content to the alert dialog
        alert.getDialogPane().setExpandableContent(expContent);
    }

    // Show the alert dialog
    alert.showAndWait();
});                
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                writeFileMenuItem.setDisable(false);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        service.setOnCancelled(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle (WorkerStateEvent e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("isPCR Cancelled");
                    alert.setHeaderText("Primer pair checks cancelled");
                    alert.setContentText(
                "isPCR checks by in-silico PCR were cancelled by user."
            );

            // Show the alert dialog
            alert.showAndWait();
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                progressBar.setVisible(false);
                summaryLabel.textProperty().unbind();
                summaryLabel.setText(summary);
                writeFileMenuItem.setDisable(false);
                closeButton.setDisable(false);
                closeMenuItem.setDisable(false);
                setCheckIsPcrButton();
            }
        });
        checkIsPcrButton.setText("Cancel");
        checkIsPcrButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent actionEvent){
                service.cancel();

            }
       });
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(service.progressProperty());
        summaryLabel.textProperty().bind(service.messageProperty());
        writeFileMenuItem.setDisable(true);
        closeButton.setDisable(true);
        closeMenuItem.setDisable(true);
        service.start();
        
    }    
    private void setCheckIsPcrButton() {
    checkIsPcrButton.setText("Check isPCR Results");
    checkIsPcrButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                checkIsPcrResults(); // Call your method to check isPCR results
            } catch (final IOException ex) {
                Platform.runLater(() -> {
                    // Create and configure an Alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("isPCR Check Failed");
                    alert.setHeaderText("Error Performing In-Silico PCR Checks");
                    alert.setContentText(
                        "An error occurred while performing in-silico PCR checks on primers. " +
                        "Exception details:\n\n" + ex.getMessage()
                    );

                    // Optionally log the exception to the console for debugging
                    ex.printStackTrace();

                    // Show the alert dialog
                    alert.showAndWait();
                });
            }
        }
    });
}        
    private void openFile(File f) throws IOException{
        String command;
        //Desktop.getDesktop().open(f);
        if (System.getProperty("os.name").equals("Linux")) {
            command = "xdg-open " + f;
        }else if (System.getProperty("os.name").equals("Mac OS X")) {
            command = "open " + f;
        }else if (System.getProperty("os.name").contains("Windows")){
            command = "cmd /C start " + f;
        } else {
            return;
        }
                    Runtime.getRuntime().exec(command);
    }
    }
    
