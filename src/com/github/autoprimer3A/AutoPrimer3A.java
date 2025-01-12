
/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * edited by Bert Gold, PhD, 2025
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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.dom4j.Document;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoPrimer3A extends Application {

    public class TryCatchException extends Exception {
        public TryCatchException() { super(); }
        public TryCatchException(String message) { super(message); }
        public TryCatchException(String message, Throwable cause) { super(message, cause); }
        public TryCatchException(Throwable cause) { super(cause); }
    }

    @FXML
    private ChoiceBox<String> genomeChoiceBox;
    @FXML
    private ChoiceBox<String> databaseChoiceBox;
    @FXML
    private Button refreshButton;

    @Override
    public void start(final Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AutoPrimer3A.fxml"));
            AnchorPane page = loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("AutoPrimer3A");
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshButton.setOnAction(event -> connectToUcsc());
    }

    private void connectToUcsc() {
        Task<LinkedHashMap<String, String>> connectTask = new Task<LinkedHashMap<String, String>>() { 
            @Override
            protected LinkedHashMap<String, String> call() throws TryCatchException {
                try {
                    // Simulated UCSC connection logic
                    return new LinkedHashMap<>(); // Replace with actual UCSC data fetch
                } catch (Exception ex) {
                    throw new TryCatchException("Error connecting to UCSC", ex);
                }
            }
        };

        connectTask.setOnFailed(event -> {
            Throwable ex = connectTask.getException();
            if (ex instanceof TryCatchException) {
                showErrorAlert("Error", "Failed to connect to UCSC", (TryCatchException) ex);
            } else {
                showErrorAlert("Error", "An unexpected error occurred", new Exception(ex));
            }
        });

        connectTask.setOnSucceeded(event -> {
            LinkedHashMap<String, String> genomeData = connectTask.getValue();
            if (genomeData != null) {
                genomeChoiceBox.getItems().clear();
                genomeChoiceBox.getItems().addAll(genomeData.keySet());
                genomeChoiceBox.getSelectionModel().selectFirst();
            }
        });

        new Thread(connectTask).start();
    }

    private void getBuildTables(String genome, boolean forceRefresh) {
        databaseChoiceBox.getItems().clear();

        Task<Document> getTablesTask = new Task<Document>() {
            @Override
            protected Document call() throws TryCatchException {
                try {
                    // Simulated table fetching logic
                    return null; // Replace with actual data fetching logic
                } catch (Exception ex) {
                    throw new TryCatchException("Error fetching genome tables", ex);
                }
            }
        };

        getTablesTask.setOnFailed(event -> {
            Throwable ex = getTablesTask.getException();
            if (ex instanceof TryCatchException) {
                showErrorAlert("Error", "Failed to retrieve genome data", (TryCatchException) ex);
            } else {
                showErrorAlert("Error", "An unexpected error occurred", new Exception(ex));
            }
        });

        getTablesTask.setOnSucceeded(event -> {
            Document result = getTablesTask.getValue();
            // Logic to populate databaseChoiceBox with result
        });

        new Thread(getTablesTask).start();
    }

    private void showErrorAlert(String title, String header, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
