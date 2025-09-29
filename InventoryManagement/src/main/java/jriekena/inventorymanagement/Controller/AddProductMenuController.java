/**
 * The Add Product Menu Controller.
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
import javafx.scene.layout.GridPane;
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
 * Contains the logic for adding a new product.
 */
public class AddProductMenuController implements Initializable {

    @FXML
    private Button addProductButton;

    @FXML
    private TableView<Part> associatedPartsTableView;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partCostColumn;

    @FXML
    private TableView<Part> availablePartsTableView;
    public TableColumn assocPartIDColumn;
    public TableColumn assocPartNameColumn;
    public TableColumn assocPartInventoryLevelColumn;
    public TableColumn assocPartCostColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField currentInventoryTxt;

    @FXML
    private TextField inventoryMaxTxt;

    @FXML
    private TextField inventoryMinTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField productIDTxt;

    @FXML
    private GridPane productNameTxt;

    @FXML
    private TextField productPartSearchTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button saveButton;

    /**
     * Private variables for the class to work with.
     */
    private ObservableList<Part> availableParts = FXCollections.observableArrayList();
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();
    private Product productToAdd = new Product(0, "", 0.00, 0, 0, 0, allAssociatedParts);

    Stage stage;

    Parent scene;

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

                if (associatedPartsTableView.getSelectionModel().getSelectedItem() == null) {
                    return;
                } else {
                    allAssociatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
                    associatedPartsTableView.setItems(allAssociatedParts);
                    associatedPartsTableView.getSelectionModel().select(null);
                    return;
                }
            }
        }
    }

    /**
     * Saves the newly entered product.  Checks for errors when doing so.
     * @param event
     */
    @FXML
    void onClickSaveProduct(ActionEvent event) throws IOException {

            try {
                int id = Integer.parseInt(productIDTxt.getText());
                String name = nameTxt.getText();
                int currInventory = Integer.parseInt(currentInventoryTxt.getText());
                double unitPrice = Double.parseDouble(productPriceTxt.getText());
                int max = Integer.parseInt(inventoryMaxTxt.getText());
                int min = Integer.parseInt(inventoryMinTxt.getText());
                ObservableList<Part> associatedParts = associatedPartsTableView.getItems();

                Product newProduct = new Product(id, name, unitPrice, currInventory, min, max, associatedParts);

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
                    Inventory.addProduct(newProduct);
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
                    scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                }
            } catch (NumberFormatException e1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please enter a valid value for each field.");
                alert.showAndWait();
                return;
            }
        }

    /**
     * On clicking the search button, searches the available parts list.
     * @param actionEvent
     */
    @FXML
    public void OnClickSearchParts(ActionEvent actionEvent) {

        String searchPart = productPartSearchTxt.getText();
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

            productPartSearchTxt.clear();
            productPartSearchTxt.setPromptText("Search by Part ID or Name");

        }catch(NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchPart +"\" found.");
            alert.setTitle("Not Found");
            alert.showAndWait();
            productPartSearchTxt.clear();
            productPartSearchTxt.setPromptText("Search by Part ID or Name");

        }

    }

    /**
     * Clears fields and returns to the Main Menu.
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
     * Initializes the Add Product Form.  Populates the fields and TableViews.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productIDTxt.setText(String.valueOf(Inventory.getAllProducts().size() + 1));

        availableParts.setAll(Inventory.getAllParts());
        availablePartsTableView.setItems(availableParts);
            partIDColumn.setCellValueFactory(new PropertyValueFactory<PartOutsourced, Part>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryLevelColumn.setCellValueFactory((new PropertyValueFactory<>("stock")));
            partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(allAssociatedParts);
            assocPartIDColumn.setCellValueFactory(new PropertyValueFactory<PartOutsourced, Part>("id"));
            assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartInventoryLevelColumn.setCellValueFactory((new PropertyValueFactory<>("stock")));
            assocPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}