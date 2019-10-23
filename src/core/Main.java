package core;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginController;
import modelo.Producto;
import product.detail.ProductDetailController;

public class Main extends Application {

    private static Stage primaryStage;
    private AnchorPane rootLayout;

    private ObservableList<Producto> productData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initLogin();
    }

    public Main()
    {
        productData.addAll(new Producto("Martillo", 3.4f, 8),
                new Producto("Pintura Roja 5L", 8.99f, 23),
                new Producto("Clavo", 0.05f, 265),
                new Producto("Tornillo", 0.15f, 673));
    }

    public ObservableList<Producto> getProductData() {
        return productData;
    }

    /**
     * Initializes the root layout.
     */
    public void initLogin() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("login/Login.fxml"));
            rootLayout = (AnchorPane) loader.load();

            LoginController controller = loader.getController();
            controller.setMain(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Producto producto) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("product/detail/ProductDetail.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Producto");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ProductDetailController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(producto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}