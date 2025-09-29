/**
 * The Modify Product Menu Controller.
 */
package jriekena.inventorymanagement.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jriekena.inventorymanagement.Model.Inventory;
import jriekena.inventorymanagement.Model.Part;
import jriekena.inventorymanagement.Model.PartOutsourced;
import jriekena.inventorymanagement.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contains the logic for modifying exsting parts.
 */
public class ModifyProductMenuController implements Initializable {

    @FXML
    public TextField inventoryMinTxt;

    @FXML
    private Button addProductButton;

    @FXML
    private TableView<Part> associatedPartsTableView;
    public TableColumn assocPartIDColumn;
    public TableColumn assocPartNameColumn;
    public TableColumn assocPartInventoryLevelColumn;
    public TableColumn assocPartCostColumn;

    @FXML
    private TableView<Part> availablePartsTableView;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partCostColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField currentInventoryTxt;

    @FXML
    private TextField inventoryMaxTxt;

    @FXML
    private TextField modifyProductIDTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button saveButton;

    Stage stage;

    Parent scene;

    /**
     * Private variables for the class to work with.
     */
    private ObservableList<Part> availableParts = FXCollections.observableArrayList();
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();
    private Product productToModify = new Product(0, "", 0.00, 0, 0, 0, allAssociatedParts);


    /**
     * On clicking the search button, searches the available parts list.
     * @param actionEvent
     */
    @FXML
    public void OnClickSearchParts(ActionEvent actionEvent) {

        String searchPart = partSearchTxt.getText();
        ObservableList<Part> foundPartsList = Inventory.lookupPart(searchPart);
        availablePartsTableView.getSelectionModel().clearSelection();

        try {

            if (foundPartsList.size() != 0) {
                availablePartsTableView.setItems(foundPartsList);
            }

            else {
                int partId = Integer.parseInt(searchPart);
                Part foundPart = Inventory.lookupPart(partId);
                foundPartsList.add(foundPart);
                if (foundPart != null) {
                    availablePartsTableView.setItems(foundPartsList); //Now shows only the found part.
                    //partTableView.getSelectionModel().select(partId - 1); <-- Used to only select the found part.
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchPart +"\" found.");
                    alert.setTitle("Not Found");
                    alert.showAndWait();
                }
            }

            partSearchTxt.clear();
            partSearchTxt.setPromptText("Search by Part ID or Name");

        }catch(NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchPart +"\" found.");
            alert.setTitle("Not Found");
            alert.showAndWait();
            partSearchTxt.clear();
            partSearchTxt.setPromptText("Search by Part ID or Name");

        }

    }

    /**
     * Adds the selected part from the available parts table/list to the associated parts table/list.
     * @param event
     */
    @FXML
    void onClickAddSelectedPartToProduct(ActionEvent event) {
        if(availablePartsTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        else {
            allAssociatedParts.add(availablePartsTableView.getSelectionModel().getSelectedItem());
            associatedPartsTableView.setItems(allAssociatedParts);
        }
    }

    /**
     * Removes the selected part from the associated parts table/list.
     * @param event
     */
    @FXML
    void onClickRemovePartFromProduct(ActionEvent event) {
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        else {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Selected part will be removed from product. Continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

                allAssociatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
                associatedPartsTableView.setItems(allAssociatedParts);
                associatedPartsTableView.getSelectionModel().select(null);
                return;
            }
        }
    }

    /**
     * Saves the modified Product to the allProducts list.
     * @param event
     */
    @FXML
    void onClickSaveProduct(ActionEvent event) {

        try {

            int id = Integer.parseInt(modifyProductIDTxt.getText());
            productToModify.setId(Integer.parseInt(modifyProductIDTxt.getText()));
            productToModify.setName(nameTxt.getText());
            int currInventory = Integer.parseInt(currentInventoryTxt.getText());
            productToModify.setStock(Integer.parseInt(currentInventoryTxt.getText()));
            productToModify.setPrice(Double.parseDouble(productPriceTxt.getText()));
            int max = Integer.parseInt(inventoryMaxTxt.getText());
            productToModify.setMax(Integer.parseInt(inventoryMaxTxt.getText()));
            int min = Integer.parseInt(inventoryMinTxt.getText());
            productToModify.setMin(Integer.parseInt(inventoryMinTxt.getText()));

            if(currInventory < min || currInventory > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Current Inventory must be between the maximum and minimum.");
                alert.showAndWait();
                return;
            }
            else if(min >= max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            else {
                Inventory.updateProduct(id, productToModify);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
            }

        } catch (NumberFormatException | IOException e1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
            return;
        }

    }

    /**
     * Returns to the Main Menu after a confirmation dialog.
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
     * Facilitates the transfer of the product selected on the Main Menu to the Modify Product Menu.
     * @param productToSend
     */
    @FXML
    public void sendProduct(int index, Product productToSend) {

        productToModify = productToSend;

        modifyProductIDTxt.setText(String.valueOf(index));
        nameTxt.setText(productToSend.getName());
        currentInventoryTxt.setText(String.valueOf(productToSend.getStock()));
        productPriceTxt.setText(String.valueOf(productToSend.getPrice()));
        inventoryMaxTxt.setText(String.valueOf(productToSend.getMax()));
        inventoryMinTxt.setText(String.valueOf(productToSend.getMin()));
        associatedPartsTableView.setItems(productToSend.getAllAssociatedParts());
        allAssociatedParts = productToSend.getAllAssociatedParts();
    }

    /**
     * Initializes the Modify Parts Menu. Populates the AvailableParts TableView.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        availableParts.setAll(Inventory.getAllParts());
        availablePartsTableView.setItems(availableParts);
            partIDColumn.setCellValueFactory(new PropertyValueFactory<PartOutsourced, Part>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryLevelColumn.setCellValueFactory((new PropertyValueFactory<>("stock")));
            partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            assocPartIDColumn.setCellValueFactory(new PropertyValueFactory<PartOutsourced, Part>("id"));
            assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartInventoryLevelColumn.setCellValueFactory((new PropertyValueFactory<>("stock")));
            assocPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}



