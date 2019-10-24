package login;

import core.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import principal.PrincipalController;

import java.io.IOException;

public class LoginController {
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    ImageView imgUser;
    @FXML
    ImageView imgLock;

    private Main main;
    
    @FXML
    void login(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);

        String email = "admin";
        String password = "123456";

        if(txtUser.getText().equals(email) && txtPassword.getText().equals(password)) {
            changeScreen(event);
        }else {
            alert.setContentText("Email y/o contraseña incorrectos");
            alert.showAndWait();
        }
        
    }

    void changeScreen(ActionEvent event) throws IOException {
        //Obtenemos la escena que querremos cambiar
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("principal/Principal.fxml"));

        Parent parent = loader.load();
        Scene scenePrincipal = new Scene(parent);

        PrincipalController controller = loader.getController();
        controller.setMain(main);

        //Obtenemos el escenario desde donde se ha pulsado el botón
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());

        //Asignamos la escena deseada al escenario principal
        stage.setScene(scenePrincipal);
        stage.show();
    }

    public void setMain(Main main) {
        this.main = main;
        
        imgUser.setImage(new Image("/resources/user.png"));
        imgLock.setImage(new Image("/resources/lock.png"));

    }
}
