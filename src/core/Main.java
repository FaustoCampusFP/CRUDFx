package core;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginController;
import modelo.ProductListWrapper;
import modelo.Producto;
import product.detail.ProductDetailController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class Main extends Application {

    private static Stage primaryStage;
    private AnchorPane rootLayout;

    private ObservableList<Producto> productData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Ingenium Studios");

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

    public File getProductFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setProductFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("Ingenium Studio - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("Ingenium Studio");
        }
    }

    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ProductListWrapper wrapper = um.unmarshal(new StreamSource(file), ProductListWrapper.class).getValue();

            productData.clear();
            productData.addAll(wrapper.getProductos());

            // Save the file path to the registry.
            saveProductDataToFile(file);

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha podido cargar los datos");
            alert.setContentText("No se pueden cargar los datos desde el archivo:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveProductDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ProductListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            ProductListWrapper wrapper = new ProductListWrapper();
            wrapper.setPersons(productData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setProductFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha podido grabar los datos");
            alert.setContentText("No se pueden grabar los datos al archivo:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}