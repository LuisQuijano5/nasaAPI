package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectioMarsRovers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class MarsRoversController {
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    @FXML
    private DatePicker datepickerContainer;
    @FXML
    private ComboBox<String> roverComboBox;
    @FXML
    private ComboBox<String> cameraComboBox;
    @FXML
    private Label resultLabel;
    @FXML
    private TilePane imageTilePane;
    @FXML
    private Button searchButton;
    @FXML
    private StackPane contentPane;

    private boolean menuVisibility = true;
    private int privilegeLevel;

    public void initialize(){
        if(!App.darkTheme){ App.themeHandler.applyLightTheme(container); }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }

        if (App.currentUser.getEpicAccess() == 0 && !App.currentUser.isAdmin()) {
            try {
                // Cargar el archivo FXML de "no access"
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/comprehensive/project/nasaapi/views/no_access.fxml"));
                Pane noAccessPane = loader.load();

                // Reemplazar el contenido del contenedor con el contenido de "no access"
                contentPane.getChildren().clear();
                contentPane.getChildren().add(noAccessPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        privilegeLevel = App.currentUser.getEpicPrivilege();

        try {
            LocalDate currentDate = LocalDate.now();
            datepickerContainer.setPromptText(currentDate.toString());

            roverComboBox.getItems().addAll("curiosity", "opportunity", "spirit");
            roverComboBox.getSelectionModel().clearSelection();

            cameraComboBox.getItems().add("All Cameras");

            roverComboBox.setOnAction(event -> updateCameras());

        } catch (Exception e) {
            resultLabel.setText(e.toString());
        }
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    @FXML
    private void searchImages() {
        LocalDate earthDate = datepickerContainer.getValue();
        String rover = roverComboBox.getValue();
        String camera = cameraComboBox.getValue();

        if (earthDate != null && rover != null) {
            String formattedDate = earthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Thread searchThread = new Thread(() -> {
                try {
                    JsonArray photoArray = APIConnectioMarsRovers.getPhotosByEarthDate(rover.toLowerCase(), formattedDate);
                    displayImages(photoArray, camera);
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> resultLabel.setText("Error al obtener las imágenes."));
                }
            });
            searchThread.start();
        }
    }

    private void displayImages(JsonArray photoArray, String camera) {
        Platform.runLater(() -> imageTilePane.getChildren().clear());

        int totalPhotos = photoArray.size();
        int displayedPhotos = 0;

        for (int i = 0; i < totalPhotos; i++) {
            JsonObject photoObject = photoArray.get(i).getAsJsonObject();
            String cameraName = photoObject.getAsJsonObject("camera").get("name").getAsString();

            if (camera.equals("All Cameras") || camera.equals(cameraName)) {
                String imageUrl = photoObject.get("img_src").getAsString();
                String photoId = photoObject.get("id").getAsString();
                String earthDate = photoObject.get("earth_date").getAsString();
                String roverName = photoObject.getAsJsonObject("rover").get("name").getAsString();

                ImageView imageView = new ImageView();
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);

                // Verifica si la URL utiliza el protocolo HTTP
                if (imageUrl.startsWith("http://")) {
                    // Agregar la "s" después de "http"
                    imageUrl = imageUrl.replace("http://", "https://");
                }

                // Carga la imagen de forma asíncrona
                loadImageAsync(imageUrl, imageView, photoId, earthDate, cameraName, roverName);

                // Agrega el ImageView al TilePane en el hilo de JavaFX
                Platform.runLater(() -> {
                    imageTilePane.getChildren().add(imageView);
                });

                displayedPhotos++;
            }
        }

        // Actualiza el contador de resultados en el hilo de JavaFX
        int finalDisplayedPhotos = displayedPhotos;
        Platform.runLater(() -> resultLabel.setText("Showing " + finalDisplayedPhotos + " results in this search."));
    }

    private void loadImageAsync(String imageUrl, ImageView imageView, String photoId, String earthDate, String cameraName, String roverName) {
        Thread loadImageThread = new Thread(() -> {
            try {
                Image image = new Image(imageUrl, true);
                Platform.runLater(() -> {
                    imageView.setImage(image);
                    imageView.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/comprehensive/project/nasaapi/views/image_preview.fxml"));
                            Stage previewStage = new Stage();
                            previewStage.setTitle("Rover Preview");
                            previewStage.initModality(Modality.APPLICATION_MODAL);
                            Scene previewScene = new Scene(loader.load());
                            ImagePreviewController previewController = loader.getController();
                            previewController.setImage(image);
                            previewController.setPhotoData(photoId, earthDate, cameraName, roverName);
                            previewController.setPrivilegeLevel(privilegeLevel);

                            previewStage.setTitle("Image Preview");
                            previewStage.setScene(previewScene);
                            previewStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loadImageThread.setDaemon(true);
        loadImageThread.start();
    }

    private void updateCameras() {
        LocalDate earthDate = datepickerContainer.getValue();
        String rover = roverComboBox.getValue();

        if (earthDate != null && rover != null) {
            String formattedDate = earthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Thread updateCamerasThread = new Thread(() -> {
                try {
                    JsonArray photoArray = APIConnectioMarsRovers.getPhotosByEarthDate(rover.toLowerCase(), formattedDate);
                    updateCameraComboBox(photoArray);
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> resultLabel.setText("Error al obtener las cámaras disponibles."));
                }
            });
            updateCamerasThread.start();
        }
    }

    private void updateCameraComboBox(JsonArray photoArray) {
        Set<String> availableCameras = new HashSet<>();

        for (int i = 0; i < photoArray.size(); i++) {
            JsonObject photoObject = photoArray.get(i).getAsJsonObject();
            String cameraName = photoObject.getAsJsonObject("camera").get("name").getAsString();
            availableCameras.add(cameraName);
        }

        Platform.runLater(() -> {
            cameraComboBox.getItems().clear();
            cameraComboBox.getItems().add("All Cameras");
            cameraComboBox.getItems().addAll(availableCameras);
            cameraComboBox.getSelectionModel().clearSelection();
        });
    }
}
