package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectionAPOD;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;

import com.google.gson.JsonObject;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private boolean menuVisibility = true;

    public void initialize(){
        if(!App.darkTheme){ App.themeHandler.applyLightTheme(container); }

        try {
            LocalDate currentDate = LocalDate.now();
            //LocalDate selectedDate =  datePicker.getValue();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            APIConnectionAPOD.setCustomDate(formattedDate);

            updateInfo();

            datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate != null) {
                    updateQuery(selectedDate);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    private void updateInfo(){
        try {
            // Limpiar
            dateText.setText("");
            titleText.setText("");
            imageView.setImage(null);
            imageDescription.setText("");

            JsonObject apodData = APIConnectionAPOD.getApod();

            String date = apodData.get("date").getAsString();
            dateText.setText("Date: " + date);

            String title = apodData.get("title").getAsString();
            titleText.setText(title);

            URL imageUrl = new URL(apodData.get("hdurl").getAsString());
            Image image = new Image(imageUrl.openStream());
            imageView.setImage(image);

            String description = apodData.get("explanation").getAsString();
            imageDescription.setWrappingWidth(600);
            imageDescription.setText(description);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateQuery(LocalDate newDate) {
        String formattedDate = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        APIConnectionAPOD.setCustomDate(formattedDate);

        updateInfo(); // Llamar a updateInfo() después de actualizar la fecha
    }
}
