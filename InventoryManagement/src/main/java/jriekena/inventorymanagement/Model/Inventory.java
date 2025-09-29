
package jriekena.inventorymanagement.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class.
 * Depends on the Part and Product classes for their respective observableArrayLists.
 */
public class Inventory {

    /**
     * The allParts ObservableList.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The allProducts ObservableList.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the allParts ObservableList.
     * @param newPart
     */
    public static void addPart(Part newPart) { allParts.add(newPart); }

    /**
     * Adds a new part to the allProducts ObservableList.
     * @param newProduct
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
     * Search for product by (partial)Name.
     * @param productName from the search Product Field.
     * @return foundProducts observable list.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product searchedProduct : allProducts) {

            if(searchedProduct.getName().contains(productName)){
                foundProducts.add(searchedProduct);
            }

        }

        return foundProducts;
    }

    /**
     * Search for part by (partial)name.
     * @param partName from the searchPartField.
     * @return foundParts observable list.
     * */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part searchedPart : allParts) {

            if(searchedPart.getName().contains(partName)){
                foundParts.add(searchedPart);
            }

        }

        return foundParts;
    }

    /**
     * Search for part by part ID.
     * @param partId from the searchPartField.
     * @return foundPart, the corresponding Part object.
     */
    public static Part lookupPart(int partId) {

        ObservableList<Part> allParts = getAllParts();

        for(int i = 0; i < allParts.size(); i++) {
            Part foundPart = allParts.get(i);

            if(foundPart.getId() == partId) {
                return foundPart;
            }
        }

        return null;

    }

    /**
     * Search for product by product ID.
     * @param productId from the searchProductField.
     * @return foundProduct, the corresponding Product object.
     */
    public static Product lookupProduct(int productId) {

        ObservableList<Product> allProducts = getAllProducts();

        for(int i = 0; i < allProducts.size(); i++) {
            Product foundProduct = allProducts.get(i);

            if(foundProduct.getId() == productId) {
                return foundProduct;
            }
        }

        return null;

    }

    /**
     * Updates the allParts list with the modifications.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates te allProducts list with the modified product.
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes selected Part from allParts Observable List.
     * @param selectedPart
     * @return boolean true if deletion successful, false if unsuccessful.
     */
    public static boolean deletePart(Part selectedPart) {

        allParts.remove(selectedPart);

        return !allParts.contains(selectedPart);

    }

    /**
     * Deletes the selected Product from the allProducts Observable List.
     * @param selectedProduct
     * @return boolean - true if deletion successful, false if unsuccessful.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);

        if(!allProducts.contains((selectedProduct))) {return true; }
        else { return false; }
    }

    /**
     * Returns the current allParts ObservableList.
     * @return ObservableList<Part> allParts.
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Returns the current allProducts ObservableList.
     * @return ObservableList<Product> allProducts.
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

}
