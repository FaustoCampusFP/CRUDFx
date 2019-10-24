package principal;

import core.Main;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import modelo.Producto;
import util.DateUtil;

import java.io.File;

public class PrincipalController {
    @FXML
    private TableView<Producto> productsTable;
    @FXML
    private TableColumn<Producto, String> productDescriptionColumn;
    @FXML
    private TableColumn<Producto, Float> productPriceColumn;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label unitsLabel;
    @FXML
    private Label dateLabel;

    private Main main;

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        productDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());

        // Clear person details.
        showProductsDetails(null);

        // Listen for selection changes and show the person details when changed.
        productsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductsDetails(newValue));
    }

    @FXML
    private void handleDeleteProduct() {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            productsTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Main.getPrimaryStage());
            alert.setTitle("No hay selección");
            alert.setHeaderText("No se ha seleccionado un producto");
            alert.setContentText("Por favor, elija un producto de la tabla.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewProduct() {
        Producto tempProduct = new Producto();
        boolean okClicked = main.showPersonEditDialog(tempProduct);
        if (okClicked) {
            main.getProductData().add(tempProduct);
        }
    }

    @FXML
    private void handleEditProduct() {
        Producto selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = main.showPersonEditDialog(selectedProduct);
            if (okClicked) {
                showProductsDetails(selectedProduct);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Main.getPrimaryStage());
            alert.setTitle("No hay selección");
            alert.setHeaderText("No se ha seleccionado un producto");
            alert.setContentText("Por favor, elija un producto de la tabla.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNew() {
        main.getProductData().clear();
        main.getProductFilePath();
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            main.loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File personFile = main.getProductFilePath();
        if (personFile != null) {
            main.saveProductDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.saveProductDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ingenium Studios");
        alert.setHeaderText("About");
        alert.setContentText("(c) Ingenium Studios 2019");

        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    public void setMain(Main main) {
        this.main = main;
        productsTable.setItems(main.getProductData());

        // Seleccionamos el primer elemento de la lista y mostramos sus detalles.
        productsTable.getSelectionModel().select(0);
        Producto defaultProduct = productsTable.getSelectionModel().getSelectedItem();
        if(defaultProduct != null) showProductsDetails(defaultProduct);

        File file = main.getProductFilePath();
        if (file != null) {
            main.loadPersonDataFromFile(file);
        }
    }

    private void showProductsDetails(Producto producto) {
        if (producto != null) {
            descriptionLabel.setText(producto.getDescripcion());
            priceLabel.setText(Float.toString(producto.getPrecio()) + " €");
            unitsLabel.setText(Integer.toString(producto.getUnidades()));
            dateLabel.setText(DateUtil.format(producto.getFechaFabricacion()));
        } else {
            descriptionLabel.setText("");
            priceLabel.setText("");
            unitsLabel.setText("");
            dateLabel.setText("");
        }
    }
}
