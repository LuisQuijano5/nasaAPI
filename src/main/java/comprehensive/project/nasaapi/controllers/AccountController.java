package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.*;
import comprehensive.project.nasaapi.models.Favorites;
import comprehensive.project.nasaapi.models.Modification;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.models.jsonApi.Modi;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountController {
    @FXML
    HBox toggleBtnsContainer;
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    @FXML
    Button myAccountBtn, usersBtn, modificationsBtn;
    @FXML
    VBox guestsSection, usersSection, modificationsSection, myAccountSection;
    @FXML
    VBox passwordContainer, visiblePasswordContainer;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField usernameField, visiblePasswordField;
    @FXML
    private ComboBox<String> comboBoxMode;
    @FXML
    private ComboBox<String> comboBoxMenu;
    @FXML
    private TableView<Favorites> favoritesTableView;
    @FXML
    private TableColumn<Favorites, String> userIdColumn;
    @FXML
    private TableColumn<Favorites, String> resourcesIdColumn;
    @FXML
    private TableColumn<Favorites, String> dateColumn;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, Integer> userIdColumnU;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private ComboBox<Integer> apodAccessComboBox;
    @FXML
    private ComboBox<Integer> apodPrivilegeComboBox;
    @FXML
    private ComboBox<Integer> galleryAccessComboBox;
    @FXML
    private ComboBox<Integer> galleryPrivilegeComboBox;
    @FXML
    private ComboBox<Integer> epicAccessComboBox;
    @FXML
    private ComboBox<Integer> epicPrivilegeComboBox;
    @FXML
    private ComboBox<Integer> accountAccessComboBox;
    @FXML
    private ComboBox<Integer> accountPrivilegeComboBox;
    @FXML
    private TableView<Modi> modificationsTableView;
    @FXML
    private TableColumn<Modi, Integer> userIdColumnM;
    @FXML
    private TableColumn<Modi, String> timeOfModColumn;
    @FXML
    private TableColumn<Modi, String> viewOfModColumn;
    @FXML
    private TableColumn<Modi, String> actionColumn;
    @FXML
    private Button deleteModificationsButton;

    private FavoritesDao favoritesDao = new FavoritesDao();
    private boolean menuVisibility = true;
    private boolean passwordIsVisible = false;
    private UserDao userDao = new UserDao();
    private PreferenceDao prefDao = new PreferenceDao();
    private ModificationDao modDao = new ModificationDao();
    private PrivilegeDao privilegeDao = new PrivilegeDao();
    private ViewDao viewDao = new ViewDao();

    public void initialize() throws IOException {
        if(!App.darkTheme){
            App.themeHandler.applyLightTheme(container);
        }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }

        //Access visibility handling
        if(App.currentUser.getAccountAccess() > 0 || App.currentUser.isAdmin()){
            setMyAccountSection();
            myAccountBtn.setVisible(true);
            showFavorites();
        }

        if(App.currentUser.getAccountAccess() == 2){
            modificationsBtn.setVisible(true);
        } else if(App.currentUser.getAccountAccess() == 3 || App.currentUser.isAdmin()){
            modificationsBtn.setVisible(true);
            usersBtn.setVisible(true);
        }

        if (App.currentUser.getAccountPrivilege() == 2 || App.currentUser.isAdmin()) {
            deleteModificationsButton.setVisible(true);
        } else {
            deleteModificationsButton.setVisible(false);
        }

        comboBoxMode.getItems().addAll("Light", "Dark");
        comboBoxMenu.getItems().addAll("Minimized", "Normal");

        int currentMode = App.currentUser.getColorModePref();
        int currentMenu = App.currentUser.getMenuVisibilityPref();
        comboBoxMode.getSelectionModel().select(currentMode == 0 ? "Light" : "Dark");
        comboBoxMenu.getSelectionModel().select(currentMenu == 0 ? "Minimized" : "Normal");

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        resourcesIdColumn.setCellValueFactory(new PropertyValueFactory<>("resourcesId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        dateColumn.setText("Date");

        userIdColumnM.setCellValueFactory(new PropertyValueFactory<>("userId"));
        timeOfModColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfMod"));
        viewOfModColumn.setCellValueFactory(new PropertyValueFactory<>("viewOfMod"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        userIdColumnU.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        apodAccessComboBox.getItems().addAll(0, 1, 2);
        apodPrivilegeComboBox.getItems().addAll(0, 1, 2);

        galleryAccessComboBox.getItems().addAll(0, 1);
        galleryPrivilegeComboBox.getItems().addAll(1);

        epicAccessComboBox.getItems().addAll(0, 1);
        epicPrivilegeComboBox.getItems().addAll(0, 1);

        accountAccessComboBox.getItems().addAll(1, 2, 3);
        accountPrivilegeComboBox.getItems().addAll(1, 2);

        // delete this when testing is done
        //myAccountBtn.setVisible(true); // for testing
        //modificationsBtn.setVisible(true); // for testing
        //usersBtn.setVisible(true); // for testing

        StringProperty sharedText = new SimpleStringProperty();
        passwordField.textProperty().bindBidirectional(sharedText);
        visiblePasswordField.textProperty().bindBidirectional(sharedText);
    }

    // Método para cambiar la visibilidad del menú
    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    // Método para establecer la sección "My Account"
    @FXML
    private void setMyAccountSection(){
        loadToggleButtons(1);

        User currentUser = App.currentUser;
        String currentUsername = currentUser.getName();
        String currentPassword = currentUser.getPassword();

        //Sin Platform.runLater no se obtiene la contraseña
        Platform.runLater(() -> {
            usernameField.setText(currentUsername);
            passwordField.setText(currentPassword);
            visiblePasswordField.setText(currentPassword);
        });
        System.out.println(currentPassword);

    }

    // Método para cambiar la visibilidad de la contraseña
    @FXML
    private void changePasswordVisibility() {
        passwordIsVisible = App.passwordSwitch.toogleVisibility(passwordField, visiblePasswordField, passwordContainer,
                visiblePasswordContainer, passwordIsVisible);
    }

    // Método para actualizar la información del usuario (username, password)
    @FXML
    private void updateUserInfo() throws IOException {
        String pastName = App.currentUser.getName();
        String pastPass = App.currentUser.getPassword();
        if (!App.inputVerifierPassword.checkInput(passwordField)) {
            return;
        }
        String newName = usernameField.getText();
        String newPass = passwordField.getText();

        boolean nameChanged = !newName.equals(pastName);
        boolean passChanged = !newPass.equals(pastPass);

        if (nameChanged || passChanged) {
            App.currentUser.setName(newName);
            App.currentUser.setPassword(newPass);
            System.out.println("Contraseña nueva " + newPass);

            AuxDao auxDao = userDao.updateUser(App.currentUser);
            if (auxDao.isSuccess()) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (nameChanged) {
                    Modification nameMod = new Modification(App.currentUser.getId(), formattedDateTime, "Account", "Name changed from " + pastName + " to " + newName);
                    modDao.registerModification(App.currentUser, nameMod);
                }
                if (passChanged) {
                    Modification passMod = new Modification(App.currentUser.getId(), formattedDateTime, "Account", "Password changed");
                    modDao.registerModification(App.currentUser, passMod);
                }
            } else {
                App.currentUser.setName(pastName);
                App.currentUser.setPassword(pastPass);
            }
        }
    }

    // Método para actualizar las preferencias
    @FXML
    private void updatePref() throws IOException {
        String selectedMode = comboBoxMode.getSelectionModel().getSelectedItem();
        String selectedMenu = comboBoxMenu.getSelectionModel().getSelectedItem();

        int modeValue = selectedMode.equals("Light") ? 0 : 1; // Convertir selección de modo a valor numérico
        int menuValue = selectedMenu.equals("Minimized") ? 0 : 1; // Convertir selección de menú a valor numérico

        // Actualizar preferencias en la base de datos
        AuxDao modeResult = prefDao.updatePreference(App.currentUser, App.currentUser.getId(), modeValue, "darkMode");
        AuxDao menuResult = prefDao.updatePreference(App.currentUser, App.currentUser.getId(), menuValue, "menuVisibility");

        //Verificar si las actualizaciones fueron exitosas
        if (modeResult.isSuccess() && menuResult.isSuccess()) {
            App.currentUser.setColorModePref(modeValue);
            App.currentUser.setMenuVisibilityPref(menuValue);
            System.out.println("Preferences updated successfully");
        } else {
            System.out.println("Error updating preferences");
        }
    }


    // Método para mostrar los favoritos
    @FXML
    private void showFavorites() throws IOException {
        AuxDao auxDao = favoritesDao.getFavs(App.currentUser);
        if (auxDao.isSuccess()) {
            List<Favorites> favoritesList = auxDao.getFavs();
            favoritesTableView.setItems(FXCollections.observableList(favoritesList));
        } else {
            System.out.println("Error retrieving favorites");
        }
    }

    // Método para establecer la sección "Modifications"
    @FXML
    private void setModificationsSection() throws IOException {
        loadToggleButtons(2);
        showModifications();
    }

    // Método para mostrar las modificaciones
    @FXML
    private void showModifications() {
        try {
            List<Modi> modificationsList = modDao.getModifications(App.currentUser);
            if (modificationsList != null) {
                modificationsTableView.setItems(FXCollections.observableList(modificationsList));
            } else {
                System.out.println("Error retrieving modifications");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar las modificaciones
    @FXML
    private void deleteModifications() {
        try {
            AuxDao auxDao = modDao.deleteModifications(App.currentUser);
            if (auxDao.isSuccess()) {
                showModifications(); // Actualizar la tabla de modificaciones después de borrarlas
            } else {
                System.out.println("Error deleting modifications");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para establecer la sección "Users"
    @FXML
    private void setUsersSection() throws IOException {
        loadToggleButtons(3);
        showUsers();

        usersTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
                    System.out.println("Usuario seleccionado: " + selectedUser.getName());

                    try {
                        AuxDao privilegeAuxDao = privilegeDao.getPrivilegeByUserId(selectedUser.getId());
                        AuxDao viewAuxDao = viewDao.getViewsByUserId(selectedUser.getId());

                        if (privilegeAuxDao.isSuccess() && viewAuxDao.isSuccess()) {
                            int[] privilegeValues = privilegeAuxDao.getValues();
                            int[] viewValues = viewAuxDao.getValues();

                            apodAccessComboBox.getSelectionModel().select(viewValues[0]);
                            apodPrivilegeComboBox.getSelectionModel().select(privilegeValues[0]);
                            galleryAccessComboBox.getSelectionModel().select(viewValues[1]);
                            galleryPrivilegeComboBox.getSelectionModel().select(privilegeValues[1]-1);
                            epicAccessComboBox.getSelectionModel().select(viewValues[2]);
                            epicPrivilegeComboBox.getSelectionModel().select(privilegeValues[2]);
                            accountAccessComboBox.getSelectionModel().select(viewValues[3] - 1);
                            accountPrivilegeComboBox.getSelectionModel().select(privilegeValues[3] - 1);
                        } else {
                            System.out.println("Error al obtener los valores de acceso y privilegio");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    // Método para mostrar los usuarios
    @FXML
    private void showUsers() throws IOException {
        AuxDao auxDao = userDao.getUsers(App.currentUser);
        if (auxDao.isSuccess()) {
            List<User> usersList = auxDao.getUsers();
            usersTableView.setItems(FXCollections.observableList(usersList));
        }
    }

    // Método para eliminar un usuario
    @FXML
    private void deleteUser() throws IOException {
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            AuxDao auxDao = userDao.deleteUser(App.currentUser, selectedUser.getId());
            if (auxDao.isSuccess()) {
                showUsers(); // Actualizar la tabla de usuarios después de la eliminación
            }
        }
    }

    // Método para actualizar los niveles de acceso y privilegios
    @FXML
    private void updateAccess() throws IOException {
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int apodAccess = apodAccessComboBox.getSelectionModel().getSelectedItem();
            int apodPrivilege = apodPrivilegeComboBox.getSelectionModel().getSelectedItem();
            int galleryAccess = galleryAccessComboBox.getSelectionModel().getSelectedItem();
            int galleryPrivilege = galleryPrivilegeComboBox.getSelectionModel().getSelectedItem();
            int epicAccess = epicAccessComboBox.getSelectionModel().getSelectedItem();
            int epicPrivilege = epicPrivilegeComboBox.getSelectionModel().getSelectedItem();
            int accountAccess = accountAccessComboBox.getSelectionModel().getSelectedItem();
            int accountPrivilege = accountPrivilegeComboBox.getSelectionModel().getSelectedItem();

            // Actualizar los niveles de acceso y privilegios en la base de datos
            AuxDao apodAccessResult = viewDao.updatePreference(App.currentUser, selectedUser.getId(), apodAccess, "apod");
            AuxDao apodPrivilegeResult = privilegeDao.updatePrivilege(App.currentUser, selectedUser.getId(), apodPrivilege, "apod");
            AuxDao galleryAccessResult = viewDao.updatePreference(App.currentUser, selectedUser.getId(), galleryAccess, "gallery");
            AuxDao galleryPrivilegeResult = privilegeDao.updatePrivilege(App.currentUser, selectedUser.getId(), galleryPrivilege, "gallery");
            AuxDao epicAccessResult = viewDao.updatePreference(App.currentUser, selectedUser.getId(), epicAccess, "epic");
            AuxDao epicPrivilegeResult = privilegeDao.updatePrivilege(App.currentUser, selectedUser.getId(), epicPrivilege, "epic");
            AuxDao accountAccessResult = viewDao.updatePreference(App.currentUser, selectedUser.getId(), accountAccess, "account");
            AuxDao accountPrivilegeResult = privilegeDao.updatePrivilege(App.currentUser, selectedUser.getId(), accountPrivilege, "account");

            if (apodAccessResult.isSuccess() && apodPrivilegeResult.isSuccess() &&
                    galleryAccessResult.isSuccess() && galleryPrivilegeResult.isSuccess() &&
                    epicAccessResult.isSuccess() && epicPrivilegeResult.isSuccess() &&
                    accountAccessResult.isSuccess() && accountPrivilegeResult.isSuccess()) {

                // Actualizar los niveles de acceso y privilegios en la tabla de usuarios
                try {
                    AuxDao privilegeAuxDao = privilegeDao.getPrivilegeByUserId(selectedUser.getId());
                    AuxDao viewAuxDao = viewDao.getViewsByUserId(selectedUser.getId());

                    if (privilegeAuxDao.isSuccess() && viewAuxDao.isSuccess()) {
                        int[] privilegeValues = privilegeAuxDao.getValues();
                        int[] viewValues = viewAuxDao.getValues();

                        apodAccessComboBox.getSelectionModel().select(viewValues[0]);
                        apodPrivilegeComboBox.getSelectionModel().select(privilegeValues[0]);
                        galleryAccessComboBox.getSelectionModel().select(viewValues[1]);
                        galleryPrivilegeComboBox.getSelectionModel().select(privilegeValues[1]);
                        epicAccessComboBox.getSelectionModel().select(viewValues[2]);
                        epicPrivilegeComboBox.getSelectionModel().select(privilegeValues[2]);
                        accountAccessComboBox.getSelectionModel().select(viewValues[3] - 1);
                        accountPrivilegeComboBox.getSelectionModel().select(privilegeValues[3] - 1);
                    } else {
                        System.out.println("Error al obtener los valores de acceso y privilegio actualizados");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Access and privilege levels updated successfully");
            } else {
                System.out.println("Error updating access and privilege levels");
            }
        }
    }

    // Método auxiliar para cargar los botones de alternancia
    private void loadToggleButtons(int section){
        guestsSection.setVisible(section == 0);
        myAccountSection.setVisible(section == 1);
        modificationsSection.setVisible(section == 2);
        usersSection.setVisible(section == 3);
    }
}
