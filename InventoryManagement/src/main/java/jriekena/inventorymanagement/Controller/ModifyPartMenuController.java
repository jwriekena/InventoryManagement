/**
 * The Modify Parts Menu Controller.
 */
package jriekena.inventorymanagement.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jriekena.inventorymanagement.Model.Inventory;
import jriekena.inventorymanagement.Model.Part;
import jriekena.inventorymanagement.Model.PartInHouse;
import jriekena.inventorymanagement.Model.PartOutsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contains the logic for modifying existing parts.
 */
public class ModifyPartMenuController implements Initializable {


    @FXML
    public Label compNameOrMachineID;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField currentInventoryTxt;

    @FXML
    private ToggleGroup inhouseOroutsourcedTG;

    @FXML
    private RadioButton inhouseRadioButton;

    @FXML
    private TextField machineIDorCompanyNameTxt;

    @FXML
    private TextField maxInventoryTxt;

    @FXML
    private TextField minInventoryTxt;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField partIDTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField pricePerUnitTxt;

    @FXML
    private Button saveButton;

    Stage stage;

    Parent scene;

    /**
     * On clicking cancel button clears all fields and returns to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No changes will be saved. Continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){

            stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
            scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/MainMenu.fxml"));
            stage.setScene(new Scene(scene));

        }

    }

    /**
     * On clicking the Save button runs some error checks and saves the modified part.
     * @param event
     */
    @FXML
    void onClickSavePart(ActionEvent event) {

        try {
            int id = Integer.parseInt(partIDTxt.getText());
            String name = partNameTxt.getText();
            int currInventory = Integer.parseInt(currentInventoryTxt.getText());
            double unitPrice = Double.parseDouble(pricePerUnitTxt.getText());
            int max = Integer.parseInt(maxInventoryTxt.getText());
            int min = Integer.parseInt(minInventoryTxt.getText());

            if (inhouseRadioButton.isSelected()) {
                int machineID = Integer.parseInt(machineIDorCompanyNameTxt.getText());
                PartInHouse partToSave = new PartInHouse(id, name, unitPrice,currInventory, min, max, machineID);

                if(min >= max) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Maximum must be greater than minimum.");
                    alert.showAndWait();
                    return;
                }else {
                    if(currInventory < min || currInventory > max) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Current Inventory must be between the maximum and minimum.");
                        alert.showAndWait();
                        return;
                    }else {

                        Inventory.updatePart(id, partToSave);

                    }
                }
            } else if (outsourcedRadioButton.isSelected()) {
                String compName = machineIDorCompanyNameTxt.getText();
                PartOutsourced partToSave = new PartOutsourced(id, name, unitPrice,currInventory, min, max, compName);

                if(min >= max) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Maximum must be greater than minimum.");
                    alert.showAndWait();
                    return;
                }else {
                    if(currInventory < min || currInventory > max) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Current Inventory must be between the maximum and minimum.");
                        alert.showAndWait();
                        return;
                    }else {

                        Inventory.updatePart(id, partToSave);

                    }
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
            }


        } catch (NumberFormatException | IOException e1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Gathers the data from the selected part and puts it in the corresponding fields.
     * @param partToSend
     */
    public void sendPart(int index, Part partToSend) {

        partIDTxt.setText(String.valueOf(index));
        partNameTxt.setText(partToSend.getName());
        currentInventoryTxt.setText(String.valueOf(partToSend.getStock()));
        pricePerUnitTxt.setText(String.valueOf(partToSend.getPrice()));
        maxInventoryTxt.setText(String.valueOf(partToSend.getMax()));
        minInventoryTxt.setText(String.valueOf(partToSend.getMin()));

        if(partToSend instanceof PartOutsourced) {
            inhouseOroutsourcedTG.selectToggle(outsourcedRadioButton);
            compNameOrMachineID.setText("Company Name");
            machineIDorCompanyNameTxt.setText(((PartOutsourced) partToSend).getCompanyName());
        }

        if(partToSend instanceof PartInHouse) {
            inhouseOroutsourcedTG.selectToggle(inhouseRadioButton);
            compNameOrMachineID.setText("Machine ID");
            int thisMachineID = ((PartInHouse) partToSend).getMachineID();
            machineIDorCompanyNameTxt.setText(String.valueOf(thisMachineID));
        }

    }

    /**
     * Sets Add Part form when initialized.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inhouseOroutsourcedTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(inhouseRadioButton.isSelected()) {
                    compNameOrMachineID.setText("Machine ID");
                }
                else if(outsourcedRadioButton.isSelected()) {
                    compNameOrMachineID.setText("Company Name");
                }
            }
        });


}
}
