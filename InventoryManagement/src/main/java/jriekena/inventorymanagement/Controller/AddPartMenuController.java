/**
 * The Add Part Menu Controller.
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
import jriekena.inventorymanagement.InventoryManagement;
import jriekena.inventorymanagement.Model.Inventory;
import jriekena.inventorymanagement.Model.Part;
import jriekena.inventorymanagement.Model.PartInHouse;
import jriekena.inventorymanagement.Model.PartOutsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contains the logic for adding a new part.
 */
public class AddPartMenuController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField currentInventoryTxt;

    @FXML
    private ToggleGroup inhouseOroutsourcedTG;

    @FXML
    private RadioButton inhouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private Label machineIDorCompanyNameLbl;

    @FXML
    private TextField machineIDorCompanyNameTxt;

    @FXML
    private TextField maxInventoryTxt;

    @FXML
    private TextField minInventoryTxt;

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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all fields. Continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){

            stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
            scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/MainMenu.fxml"));
            stage.setScene(new Scene(scene));

        }

    }

    /**
     * Saves the newly entered part.
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSave(ActionEvent event) throws IOException {

        try {
                int id = Integer.parseInt(partIDTxt.getText());
                String name = partNameTxt.getText();
                int currInventory = Integer.parseInt(currentInventoryTxt.getText());
                double unitPrice = Double.parseDouble(pricePerUnitTxt.getText());
                int max = Integer.parseInt(maxInventoryTxt.getText());
                int min = Integer.parseInt(minInventoryTxt.getText());


                    if (inhouseRadioButton.isSelected()) {
                        int machineID = Integer.parseInt(machineIDorCompanyNameTxt.getText());
                        PartInHouse newPart = new PartInHouse(id, name, unitPrice, currInventory, min, max, machineID);
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
                                Inventory.addPart(newPart);
                            }
                        }
                    } else if (outsourcedRadioButton.isSelected()) {
                        String compName = machineIDorCompanyNameTxt.getText();
                        PartOutsourced newPart = new PartOutsourced(id, name, unitPrice, currInventory, min, max, compName);
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
                                Inventory.addPart(newPart);
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


        } catch (NumberFormatException e1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
            return;
            }
    }

    /**
     * Sets Add Part form when initialized with a default selection of in house.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inhouseOroutsourcedTG.selectToggle(inhouseRadioButton);
        machineIDorCompanyNameLbl.setText("Machine ID");
        partIDTxt.setText(String.valueOf(Inventory.getAllParts().size() + 1));
        machineIDorCompanyNameTxt.setText("00");

        inhouseOroutsourcedTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(inhouseRadioButton.isSelected()) {
                    machineIDorCompanyNameLbl.setText("Machine ID");
                    machineIDorCompanyNameTxt.setText("00");
                }
                else if(outsourcedRadioButton.isSelected()) {
                    machineIDorCompanyNameLbl.setText("Company Name");
                    machineIDorCompanyNameTxt.setText("");
                }
            }
        });


    }
}