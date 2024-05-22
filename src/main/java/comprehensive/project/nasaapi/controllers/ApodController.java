package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectionAPOD;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.ModificationDao;
import comprehensive.project.nasaapi.database.DAO.PicODayDao;
import comprehensive.project.nasaapi.models.APOD;
import comprehensive.project.nasaapi.models.Modification;
import comprehensive.project.nasaapi.models.PicODay;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.models.jsonApi.DatumApi;
import comprehensive.project.nasaapi.reports.ApodReportGenerator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

public class ApodController {
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon apodBtnIcon;
    @FXML
    FontIcon returnIcon;
    @FXML
    FontIcon modifyIcon;
    @FXML
    FontIcon openEye;
    @FXML
    private Label dateText;
    @FXML
    private Label titleText;
    @FXML
    private ImageView imageView;
    @FXML
    private Text imageDescription;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button showBtn;
    @FXML
    private Label errorText, copyright;
    @FXML
    private boolean menuVisibility = true;
    @FXML
    VBox apiContainer, adminContainer;
    @FXML
    ScrollPane scrollContainer, scrollAdmin;
    @FXML
    Button apiKeyBtn, apodBtn, downloadtBtn, modify;

    @FXML
    private Label apiLabel;
    @FXML
    private TextField setApiKey;

    User user = App.currentUser;

    @FXML
    private Stage primaryStage;  // Reference to the primary stage
    @FXML
    TableView<DatumApi> tblRecords;
    private PicODayDao picODayDao = new PicODayDao();

    public void initialize(){

        if(!App.darkTheme){
            App.themeHandler.applyLightTheme(container);
            returnIcon.setIconColor(Paint.valueOf("black"));
            modifyIcon.setIconColor(Paint.valueOf("black"));
            apodBtnIcon.setIconColor(Paint.valueOf("black"));
        }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }

        //access control
        if(App.currentUser.isAdmin()){
            apiKeyBtn.setVisible(true);
        }
        if (App.currentUser.getApodPrivilege() == 2 || App.currentUser.isAdmin()){
            modify.setVisible(true);
            apodBtn.setVisible(true);
        }

        try {
            LocalDate currentDate = LocalDate.now();
            datePicker.setPromptText(currentDate.toString());

            showBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LocalDate selectedDate = datePicker.getValue();

                    if (selectedDate != null) {
                        datePicker.setPromptText(selectedDate.toString());
                        updateQuery(selectedDate);
                    }else {
                        updateQuery(currentDate);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            errorText.setText(e.toString());
            System.out.println("error: " + e);
        }
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    private void updateInfo(){ //Load the all the information
        // Clean
        dateText.setText("");
        titleText.setText("");
        imageView.setImage(null);
        imageDescription.setText("");
        errorText.setText("");

        downloadtBtn.setVisible(true);

            try {

                APOD apodData = APIConnectionAPOD.getApod();

                    Platform.runLater(() -> {
                        String copyrightString = "";
                        if (apodData.getCopyright() == null){
                            copyrightString = "None";
                        }else {
                            copyrightString = apodData.getCopyright().replace("\n"," ");
                        }

                        String date = apodData.getDate().toString();
                        dateText.setText("Date: " + date);

                        String title = apodData.getTitle();
                        titleText.setText(title);

                        String imageUrl = apodData.getUrl();
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);

                        copyright.setText("Image Credit & Copyright: " + copyrightString);

                        String description = apodData.getExplanation();
                        imageDescription.setWrappingWidth(600);
                        imageDescription.setText(description);
                    });


            }catch (Exception e){
                errorText.setText(e.toString());
                System.out.println("Error: " + e);
                System.out.println(e);
            }

    }

    private void updateQuery(LocalDate newDate) {
        String formattedDate = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        APIConnectionAPOD.setCustomDate(formattedDate);

        updateInfo(); // Llamar a updateInfo() después de actualizar la fecha
    }

    @FXML
    private void onModifyKeyButtonClick(){//Button to change the Api key
        scrollContainer.setVisible(false);
        apiContainer.setVisible(true);
        adminContainer.setVisible(false);
        scrollAdmin.setVisible(false);

        apiLabel.setText(APIConnectionAPOD.getApiKey());

    }

    @FXML
    private void onLoadApodViewButtonClick(){//Button to return to the principal APOD view
        apiContainer.setVisible(false);
        scrollContainer.setVisible(true);
        adminContainer.setVisible(false);
        scrollAdmin.setVisible(false);
    }

    @FXML
    private void onSetNewKeyButtonClick(){//Button that change definitively changes the api key in DB
        if (!setApiKey.getText().isEmpty()){
            if (showConfirmation("Confirm", "are you sure to set this new Api key?")){
                System.out.println("User from controller: " + App.currentUser.getToken());

                APIConnectionAPOD.setConApiKey(App.currentUser,setApiKey.getText());
                reloadApiKey();
            }
        }else {
            showMessage("Warning", "Enter an Api key", Alert.AlertType.WARNING);
        }
    }

    private void showMessage(String title, String message, Alert.AlertType alertType) {//Method to show an alert
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private boolean showConfirmation(String title, String message){//Method to show a confirmation message
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setContentText(message);
        Optional< ButtonType> response = confirmation.showAndWait();
        return (response.get() == ButtonType.OK);
    }

    private void reloadApiKey(){//Show the current api key and when it changes
        apiLabel.setText("");
        apiLabel.setText(APIConnectionAPOD.getApiKey());
        setApiKey.clear();

    }

    @FXML
    private void ongenerateReportButtonClick(){//
        try {
            LocalDate today = LocalDate.now();

            // Ir al domingo de la semana actual
            LocalDate actualSunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

            // Calcular el lunes de la semana anterior (7 días antes del domingo actual)
            LocalDate lastMonday = actualSunday.minusDays(6);
            LocalDate lastSunday = actualSunday.minusDays(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDate = lastMonday.format(formatter);
            String endDate = lastSunday.format(formatter);

            List<APOD> apodList = APIConnectionAPOD.getApodsInRange(startDate, endDate);


            // Selecciona la ruta de destino
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("APOD_Report_"+startDate +"_to_"+endDate+"_.pdf");

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String destinationPath = file.getAbsolutePath();
                //Create the PDF report by calling the class ApodReportGenerator
                new ApodReportGenerator().createReport(apodList, destinationPath);
                showMessage("Success", "Report was successfully saved...", Alert.AlertType.INFORMATION);

            } else {
                showMessage("Warning", "The save operation was cancelled...", Alert.AlertType.WARNING);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Button to download the image and it makes a register
    and a modification in DB*/
    @FXML
    private void onDownLoadImageButtonClick(){
        try {
            APOD apodData = APIConnectionAPOD.getApod();
            String date ="";

            String imageUrl = apodData.getUrl();
            if (imageUrl == null || imageUrl.isEmpty()) {
                errorText.setText("URL cannot be empty.");
                return;
            }

            //here the user chooses the download path
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            if (datePicker.getValue() == null){
                LocalDate today = LocalDate.now();
                date =today.toString();
            }else {
                date = datePicker.getValue().toString();
            }

            String defaultFileName = apodData.getTitle() + "_APOD_" + date;
            fileChooser.setInitialFileName(defaultFileName);

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    //call back the method that download image
                    downloadImage(imageUrl, file);
                    String rastreo = "";

                    //formats the copyright string to be entered into the database
                    if (apodData.getCopyright() == null){
                        rastreo = "None";
                    }else {
                        rastreo = apodData.getCopyright().replace("\n"," ");
                    }

                    //Make a record
                    PicODay picODay = new PicODay(date,apodData.getTitle(),apodData.getUrl(),rastreo);

                    AuxDao auxdao = picODayDao.addPic(App.currentUser, picODay);

                    //Make a Modification
                    Modification mod = new Modification(App.currentUser.getId(), date, "APOD",
                            "download image");

                    ModificationDao modDao = new ModificationDao();
                    modDao.registerModification(App.currentUser, mod);

                    if(auxdao.isSuccess()){
                        showMessage("Success", "Image downloaded successfully to " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showMessage("Warning", "Save operation was cancelled...", Alert.AlertType.WARNING);

            }
        }catch (Exception e){
            showMessage("Success", "Image downloaded successfully...", Alert.AlertType.INFORMATION);

            showMessage("Warning", "Warning, this record is already in the database" + e.getMessage()+ "...", Alert.AlertType.WARNING);
        }
    }

    //Here is the method to download the image
    private void downloadImage(String imageUrl, File destinationFile) throws IOException {

        URL url = new URL(imageUrl);
       Thread download = new Thread(() ->{
           try (InputStream in = url.openStream()) {
               Files.copy(in, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       });
       download.start();
    }

    // Setter for primaryStage, if needed for filechoosers
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void onAdminUserModButtonClick(){
        scrollAdmin.setVisible(true);
        adminContainer.setVisible(true);
        apiContainer.setVisible(false);
        scrollContainer.setVisible(false);

    }

    private void reloadRecordsTbl(){//here the items are assigned to the table of records
        try {
            tblRecords.setItems(FXCollections.observableArrayList(new PicODayDao().getWeeksPics(App.currentUser)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onreloadRecordTblButtonClick(){//Button for reload the records table
        reloadRecordsTbl();
    }
}