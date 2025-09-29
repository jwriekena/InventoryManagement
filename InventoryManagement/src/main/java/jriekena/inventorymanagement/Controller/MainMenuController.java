/**
 * The Main Menu Controller.
 */
package jriekena.inventorymanagement.Controller;

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
 * Contains the methods and logic for user interaction.
 * @author John Riekena
 * */
public class MainMenuController implements Initializable {

    @FXML
    private Button addPartButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private TableView<Part> partTableView;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partCostColumn;

    @FXML
    private TableView<Product> productTableView;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryLevelColumn;
    public TableColumn productCostColumn;

    @FXML
    private TextField searchPartField;

    @FXML
    private TextField searchProductField;

    Stage stage;
    Parent scene;

    /**
     * On clicking the Exit Button closes the Application.
     */
    @FXML
    void onClickExit(ActionEvent event) {

        System.exit(0);

    }

    /**
     * Opens the AddPartForm.
     * */
    @FXML
    void onClickOpenAddPartForm(ActionEvent event) throws IOException {

        stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));

    }

    /**
     * Opens the AddProductForm.
     * */
    @FXML
    void onClickOpenAddProductForm(ActionEvent event) throws IOException {

        stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/jriekena/inventorymanagement/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));

    }

    /**
     * Delete selected part.
     * @param event
     */
    @FXML
    void onClickOpenDeletePartDialog(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
              Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an Item to delete.");
            Optional<ButtonType> result = noSelection.showAndWait();
        }

        else {

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Are you sure?");
            Optional<ButtonType> result = confirmDelete.showAndWait();


            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {

                    Inventory.deletePart(selectedPart);

                    if(Inventory.deletePart(selectedPart) == false) {
                        Alert couldNotDelete = new Alert(Alert.AlertType.INFORMATION);
                        couldNotDelete.setTitle("Could not delete item");
                        couldNotDelete.setContentText("Part not deleted.");
                        Optional<ButtonType> notDeleted = couldNotDelete.showAndWait();
                    }
                    else {
                        Alert deletedPart = new Alert(Alert.AlertType.INFORMATION, "Part deleted.");
                        deletedPart.setTitle("");
                        deletedPart.setHeaderText("Success!");
                        Optional<ButtonType> deleteSuccess = deletedPart.showAndWait();
                    }

                }
                else if(result.get() == ButtonType.CANCEL) {

                    Alert choseNotToDelete = new Alert(Alert.AlertType.INFORMATION, "User chose not to delete part.");
                    Optional<ButtonType> noDelete = choseNotToDelete.showAndWait();

                }

            }

        }

        }

    /**
     * Opens the deleteProductDialog and handles error checking.
     * Calls Inventory.deleteProduct.
     * @param event
     */
    @FXML
    void onClickOpenDeleteProductDialog(ActionEvent event) {
        //FIXME - Inventory.deleteProduct and confirmation dialog
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert couldNotDelete = new Alert(Alert.AlertType.INFORMATION);
            couldNotDelete.setTitle("Could not delete item");
            couldNotDelete.setContentText("Product has associated parts. Please remove parts using modify menu.");
            Optional<ButtonType> notDeleted = couldNotDelete.showAndWait();
            return;
        }
        else {

            if (selectedProduct == null) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an Item to delete.");
                Optional<ButtonType> result = noSelection.showAndWait();
            } else {

                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected product. Are you sure?");
                Optional<ButtonType> result = confirmDelete.showAndWait();


                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {

                        Inventory.deleteProduct(selectedProduct);

                        if (Inventory.deleteProduct(selectedProduct) == false) {
                            Alert couldNotDelete = new Alert(Alert.AlertType.INFORMATION);
                            couldNotDelete.setTitle("Could not delete item");
                            couldNotDelete.setContentText("Product not deleted.");
                            Optional<ButtonType> notDeleted = couldNotDelete.showAndWait();
                        } else {
                            Alert deletedPart = new Alert(Alert.AlertType.INFORMATION, "Product deleted.");
                            deletedPart.setTitle("");
                            deletedPart.setHeaderText("Success!");
                            Optional<ButtonType> deleteSuccess = deletedPart.showAndWait();
                        }

                    } else if (result.get() == ButtonType.CANCEL) {

                        Alert choseNotToDelete = new Alert(Alert.AlertType.INFORMATION, "User chose not to delete product.");
                        Optional<ButtonType> noDelete = choseNotToDelete.showAndWait();

                    }

                }

            }
        }
    }

    /**
     * Opens and transfers data to the ModifyPartForm.
     * */
    @FXML
    void onClickOpenModifyPartForm(ActionEvent event) throws IOException {

        if(partTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
            return;
        }
            FXMLLoader loadPart = new FXMLLoader();
            loadPart.setLocation(getClass().getResource("/jriekena/inventorymanagement/ModifyPartMenu.fxml"));
            loadPart.load();

            ModifyPartMenuController modPartController = loadPart.getController();
            modPartController.sendPart(partTableView.getSelectionModel().getSelectedIndex(), partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
            Parent scene = loadPart.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

    }

    /**
     * Opens the ModifyProductForm.
     * */
    @FXML
    void onClickOpenModifyProductForm(ActionEvent event) throws IOException {

        if(productTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loadProd = new FXMLLoader();
        loadProd.setLocation(getClass().getResource("/jriekena/inventorymanagement/ModifyProductMenu.fxml"));
        loadProd.load();

        ModifyProductMenuController modProdController = loadProd.getController();
        modProdController.sendProduct(productTableView.getSelectionModel().getSelectedIndex(),  productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) (((Button) event.getSource()).getScene().getWindow());
        Parent scene = loadProd.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On clicking the search button captures the entered text and
     * calls the Inventory.lookupProduct method.
     * @param actionEvent
     */
    @FXML
    public void onClickLookupPart(ActionEvent actionEvent) {
        String searchPart = searchPartField.getText();
        ObservableList<Part> foundPartsList = Inventory.lookupPart(searchPart);
        partTableView.getSelectionModel().clearSelection();

        try {

            if (foundPartsList.size() != 0) {
                partTableView.setItems(foundPartsList);
            }

            else {
                int partId = Integer.parseInt(searchPart);
                Part foundPart = Inventory.lookupPart(partId);
                foundPartsList.add(foundPart);
                if (foundPart != null) {
                    partTableView.setItems(foundPartsList); //Now shows only the found part.
                    //partTableView.getSelectionModel().select(partId - 1); <-- Used to only select the found part.
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchPart +"\" found.");
                    alert.setTitle("Not Found");
                    alert.showAndWait();
                }
            }

            searchPartField.clear();
            searchPartField.setPromptText("Search by Part ID or Name");

        }catch(NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchPart +"\" found.");
            alert.setTitle("Not Found");
            alert.showAndWait();
            searchPartField.clear();
            searchPartField.setPromptText("Search by Part ID or Name");

        }

    }

    /**
     * On clicking the search button captures the entered text and
     * calls the Inventory.lookupProduct method.
     * @param actionEvent
     * */
    @FXML
    public void onClickLookupProduct(ActionEvent actionEvent) {

        String searchProduct = searchProductField.getText();
        ObservableList<Product> foundProductsList = Inventory.lookupProduct(searchProduct);
        productTableView.getSelectionModel().clearSelection();

        try {

            if (!foundProductsList.isEmpty()) {
                productTableView.setItems(foundProductsList);
            }

            else {
                int productId = Integer.parseInt(searchProduct);
                Product foundProduct = Inventory.lookupProduct(productId);
                foundProductsList.add(foundProduct);
                if (foundProduct != null) {
                    productTableView.setItems(foundProductsList); //Now shows only the found product.
                    //productTableView.getSelectionModel().select(productId - 1); <--Used to simply highlight found product.
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchProduct +"\" found.");
                    alert.setTitle("Not Found");
                    alert.showAndWait();
                }
            }

            searchProductField.clear();
            searchProductField.setPromptText("Search by Product ID or Name");

        }catch(NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "No match for \"" + searchProduct +"\" found.");
            alert.setTitle("Not Found");
            alert.showAndWait();
            searchProductField.clear();
            searchProductField.setPromptText("Search by Product ID or Name");

        }

    }

    /**
     * On initialization loads the test data into the TableViews.
     * Calls on the Inventory getAll methods.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<PartOutsourced, Part>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory((new PropertyValueFactory<>("stock")));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTableView.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}