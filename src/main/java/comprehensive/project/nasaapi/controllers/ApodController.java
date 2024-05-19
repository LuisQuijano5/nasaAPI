package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectionAPOD;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.NasaKeyDao;
import comprehensive.project.nasaapi.models.APOD;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.models.ivl.Ivl;
import comprehensive.project.nasaapi.reports.ApodReportGenerator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import com.google.gson.JsonObject;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateStringConverter;

import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
    VBox apiContainer;
    @FXML
    ScrollPane scrollContainer;
    @FXML
    Button apiKeyBtn, apodBtn;

    @FXML
    private Label apiLabel;
    @FXML
    private TextField setApiKey;

    public static final String DEST_Q = "results/chapter01/APOD_last_week_report.pdf";

    User user = App.currentUser;

    public void initialize(){
        NasaKeyDao keyDao = new NasaKeyDao();
        AuxDao auxDao = new AuxDao();

        if(!App.darkTheme){
            App.themeHandler.applyLightTheme(container);
        }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }

        if(App.currentUser.isAdmin()){
            apiKeyBtn.setVisible(true);
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
        }
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    private void updateInfo(){
        // Limpiar
        dateText.setText("");
        titleText.setText("");
        imageView.setImage(null);
        imageDescription.setText("");
        errorText.setText("");

            try {

                APOD apodData = APIConnectionAPOD.getApod();
                //JsonObject apodData = APIConnectionAPOD.getApod();

                    Platform.runLater(() -> {

                        String date = apodData.getDate().toString();
                        dateText.setText("Date: " + date);

                        String title = apodData.getTitle();
                        titleText.setText(title);

                        String imageUrl = apodData.getUrl();
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);

                        String copyrightString = apodData.getCopyright();
                        copyright.setText("Image Credit & Copyright: " + copyrightString);

                        String description = apodData.getExplanation();
                        imageDescription.setWrappingWidth(600);
                        imageDescription.setText(description);
                    });


            }catch (Exception e){
                errorText.setText(e.toString());
                System.out.println(e);
            }

    }

    private void updateQuery(LocalDate newDate) {
        String formattedDate = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        APIConnectionAPOD.setCustomDate(formattedDate);

        updateInfo(); // Llamar a updateInfo() después de actualizar la fecha
    }

    @FXML
    private void onModifyKeyButtonClick(){
        scrollContainer.setVisible(false);
        apiContainer.setVisible(true);

        apiLabel.setText(APIConnectionAPOD.getApiKey());

    }

    @FXML
    private void onLoadApodViewButtonClick(){
        apiContainer.setVisible(false);
        scrollContainer.setVisible(true);
    }

    @FXML
    private void onSetNewKeyButtonClick(){
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

    private void showMessage(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private boolean showConfirmation(String title, String message){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setContentText(message);
        Optional< ButtonType> response = confirmation.showAndWait();
        return (response.get() == ButtonType.OK);
    }

    private void reloadApiKey(){
        apiLabel.setText("");
        apiLabel.setText(APIConnectionAPOD.getApiKey());
        setApiKey.clear();

    }

    @FXML
    private void ongenerateReportButtonClick(){
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


            File file = new File(DEST_Q);
            file.getParentFile().mkdirs();
            new ApodReportGenerator().createReport(apodList, DEST_Q);
            System.out.println("Se creo PDF");

            openFile(DEST_Q);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openFile(String filename)
    {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }
}
