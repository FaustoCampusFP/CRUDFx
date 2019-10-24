package product.detail;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Producto;
import util.DateUtil;

public class ProductDetailController {
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField unitsField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField dateField;

    private Stage dialogStage;
    private Producto producto;
    private boolean okClicked = false;

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            producto.setDescripcion(descriptionField.getText());
            producto.setUnidades(Integer.parseInt(unitsField.getText()));
            producto.setPrecio(Float.parseFloat(priceField.getText()));
            producto.setFechaFabricacion(DateUtil.parse(dateField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProduct(Producto producto) {
        this.producto = producto;

        descriptionField.setText(producto.getDescripcion());
        priceField.setText(Float.toString(producto.getPrecio()));
        unitsField.setText(Integer.toString(producto.getUnidades()));
        dateField.setText(DateUtil.format(producto.getFechaFabricacion()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "La descripción del producto no es válida!\n";
        }

        if (unitsField.getText() == null || unitsField.getText().length() == 0) {
            errorMessage += "Las unidades en stock no son validas!\n";
        } else {
            // Intentar parsear las unidades a int.
            try {
                Float.parseFloat(unitsField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Las unidades no son válidas (debe ser un entero, ej.: 23)!\n";
            }
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "El precio por unidad no es válido!\n";
        } else {
            // Intentar parsear el precio a float.
            try {
                Float.parseFloat(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "El precio no es válido (debe ser un decimal, ej.: 1.99)!\n";
            }
        }

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "La fecha de fabricación no es válida!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "Fecha de fabricación no válida. Usar el formato dd/mm/aaaa!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos Incorrectos");
            alert.setHeaderText("Por favor corregir los campos erróneos");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
