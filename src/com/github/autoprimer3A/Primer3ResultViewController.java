package com.github.autoprimer3A;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
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
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Primer3ResultViewController implements Initializable {

    public File wFile;

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
    TableColumn<Primer3Result, Integer> indexCol;
    @FXML
    TableColumn<Primer3Result, String> nameCol;
    @FXML
    TableColumn<Primer3Result, String> idCol;
    @FXML
    TableColumn<Primer3Result, String> leftPrimerCol;
    @FXML
    TableColumn<Primer3Result, String> rightPrimerCol;
    @FXML
    TableColumn<Primer3Result, Integer> productSizeCol;
    @FXML
    TableColumn<Primer3Result, String> regionCol;
    @FXML
    TableColumn<Primer3Result, Hyperlink> ispcrCol;
    @FXML
    TableColumn<Primer3Result, Hyperlink> ispcrResCol;
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
    ChoiceBox<String> refChoiceBox;
    @FXML
    ProgressBar progressBar;

    private final ObservableList<Primer3Result> data = FXCollections.observableArrayList();

    NumberFormat nf = NumberFormat.getNumberInstance();
    CoordComparator coordCompare = new CoordComparator();
    HashMap<String, String> refSeqs = new HashMap<>();
    String summary = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.setUseSystemMenuBar(true);
        progressBar.setVisible(false);

        indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        leftPrimerCol.setCellValueFactory(new PropertyValueFactory<>("leftPrimer"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("transcripts"));
        rightPrimerCol.setCellValueFactory(new PropertyValueFactory<>("rightPrimer"));
        productSizeCol.setCellValueFactory(new PropertyValueFactory<>("productSize"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionLink"));
        ispcrCol.setCellValueFactory(new PropertyValueFactory<>("isPcrLink"));
        ispcrResCol.setCellValueFactory(new PropertyValueFactory<>("isPcrResults"));
        ispcrResCol.setVisible(false);

        primerTable.getSortOrder().add(indexCol);
        primerTable.getSortOrder().add(regionCol);
        primerTable.getSortOrder().add(productSizeCol);
        regionCol.setComparator(coordCompare);
        primerTable.getSelectionModel().setCellSelectionEnabled(true);
        primerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        refTab.selectedProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> refChoiceBox.setVisible(newValue)));
    }

    private void writePrimersToFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx")
        );
        fileChooser.setTitle("Save Primer File");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Example Primer Data\nPrimer1\tSequence1\nPrimer2\tSequence2");
            } catch (IOException ex) {
                throw new IOException("Error writing primers to file: " + ex.getMessage(), ex);
            }
        } else {
            throw new IOException("File selection was canceled or invalid.");
        }
    }

    private void writePrimersToCsv(final File f) throws IOException {
        writePrimersToText(f, ",");
    }

    private void writePrimersToTsv(final File f) throws IOException {
        writePrimersToText(f, "\t");
    }

    private void writePrimersToText(final File f, final String delimiter) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Primer3Result r : data) {
                bw.write(r.getName() + "F" + delimiter + r.getLeftPrimer());
                bw.newLine();
                bw.write(r.getName() + "R" + delimiter + r.getRightPrimer());
                bw.newLine();
            }
        }
    }

    private void writePrimersToExcel(final File f) throws IOException {
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("Primers");
        Row headerRow = sheet.createRow(0);

        String[] columns = {"Primer Name", "Sequence"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (Primer3Result r : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getName());
            row.createCell(1).setCellValue(r.getLeftPrimer());
        }

        try (FileOutputStream fileOut = new FileOutputStream(f)) {
            wb.write(fileOut);
        }
    }

    private void checkIsPcrResults() throws IOException {
        final Service<ObservableList<Primer3Result>> service = new Service<>() {
            @Override
            protected Task<ObservableList<Primer3Result>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Primer3Result> call() throws IOException {
                        ObservableList<Primer3Result> newData = FXCollections.observableArrayList();
                        for (Primer3Result r : data) {
                            if (r.getIsPcrUrl() != null) {
                                URL url = new URL(r.getIsPcrUrl());
                                try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                                    String inputLine;
                                    while ((inputLine = in.readLine()) != null) {
                                        if (inputLine.contains("PcrResult=pack")) {
                                            r.setIsPcrResults(1);
                                        }
                                    }
                                }
                            }
                            newData.add(r);
                        }
                        return newData;
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            data.clear();
            data.addAll(service.getValue());
        });
        service.start();
    }
}
