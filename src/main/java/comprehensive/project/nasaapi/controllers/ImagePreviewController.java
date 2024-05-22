package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.FavoritesDao;
import comprehensive.project.nasaapi.database.DAO.ResourceDao;
import comprehensive.project.nasaapi.models.Favorites;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDate;

public class ImagePreviewController {
    @FXML
    private ImageView imagePreview;
    @FXML
    private Button buttonPreview;

    private String photoId;
    private String earthDate;
    private String cameraName;
    private String roverName;

    public void setPrivilegeLevel(int privilegeLevel) {

        // Verificar el nivel de privilegio y mostrar/ocultar el botón de favoritos
        if (privilegeLevel == 0) {
            buttonPreview.setVisible(false);
        } else {
            buttonPreview.setVisible(true);
        }
    }

    public void setImage(Image image) {
        imagePreview.setImage(image);
    }

    public void setPhotoData(String photoId, String earthDate, String cameraName, String roverName) {
        this.photoId = photoId;
        this.earthDate = earthDate;
        this.cameraName = cameraName;
        this.roverName = roverName;
    }

    @FXML
    private void handleFavoriteButton() throws IOException {
        // Lógica para guardar la información en la tabla "Resources" y asociarla con el usuario en la tabla "Favorites"
        ResourceDao resourceDao = new ResourceDao();
        FavoritesDao favoritesDao = new FavoritesDao();
        LocalDate localDate = LocalDate.now();
        String favDate = localDate.toString();

        String title = photoId + " - " + earthDate;
        String type = "photo";
        String url = imagePreview.getImage().getUrl();
        String date = earthDate;
        String description = "ID: " + photoId + ", Camera: " + cameraName + ", Earth Date: " + earthDate + ", Rover: " + roverName;

        AuxDao resourceResult = resourceDao.addResource(App.currentUser, photoId, title, type, url, description);

        if (resourceResult.isSuccess()) {
            Favorites favorite = new Favorites(App.currentUser.getId(), photoId, favDate);
            AuxDao favoriteResult = favoritesDao.addToFavorite(App.currentUser, favorite);

            if (favoriteResult.isSuccess()) {
                System.out.println("Image added to favorites successfully");
            } else {
                System.out.println("Error adding image to favorites");
            }
        } else {
            System.out.println("Error saving image to resources");
        }
    }
}