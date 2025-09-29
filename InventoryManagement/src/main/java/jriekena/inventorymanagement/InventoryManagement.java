/**
 * This is the main InventoryManagement class.
 * Inherits from the java Application class.
 * Included is commented out test date used during development. It can be ignored.
 */

/**FUTURE ENHANCEMENTS:
 *  - Convert the generated IDs to an array for contiguous IDs when objects are deleted or added.
 *  - Create an external database to store inventory in a persistent(between running of the application) way.
 *  - Create a custom exceptions class to streamline error handling instead of using if/else statements.
 *  - Making the search boxes searchable on pressing the enter key as well as by clicking the search button.
 *  - Make the price of a product correlate to the sum of the prices of associated parts.
 *  - Improve the UX of the dialog boxes and their messages.
 */

/**RUNTIME ERRORS ENCOUNTERED
 *  - On the add parts menu the entered minimum and maximum values were being saved to the opposite variables due to a typing error.
 *          Changed the names to their correct
 *  - While writing the updatePart method using the id as the index created an out of range exception when appending the allParts list.
 *          Using the id-1 as the index solved this problem as the indexes start at 0 and the ids start at 1. This also caused issues.
 *          Finally obtained the desired result by using the object index in its respective ObservableList as the base for generating the id.
 *  - When adding or modifying products the list of associated parts was not persisting between screens.
 *          Moved the product constructor call from the save function to a class level private instance to work with.
 */

package jriekena.inventorymanagement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jriekena.inventorymanagement.Model.*;

import java.io.IOException;

/**
 * The Inventory Management application.
 */
public class InventoryManagement extends Application {


    //Counter variables for auto-generated ids for test purposes, created on opening the application. Not used in the actual program.

/*    static public int partGeneratedID = 1;
    static public int productGeneratedID = 1;
    static public int testCounter = 0;*/


    /**
     * Loads the Inventory Management GUI Main Menu.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagement.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 880, 620);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the Inventory Management Program.
     * @param args
     */
    public static void main(String[] args) {

        //Test Data...
/*
        if(testCounter == 0) {

            Inventory.addPart(new PartOutsourced(partGeneratedID, "Pepperoni", .22, 20, 5, 30, "FSA"));
            partGeneratedID++;
            Inventory.addPart(new PartOutsourced(partGeneratedID, "Sausage", .33, 11, 3, 7, "FSA"));
            partGeneratedID++;
            Inventory.addPart(new PartOutsourced(partGeneratedID, "Ham", .20, 5, 2, 10, "Sysco"));
            partGeneratedID++;
            Inventory.addPart(new PartOutsourced(partGeneratedID, "Pineapple", .15, 2, 1, 4, "Sysco"));
            partGeneratedID++;
            Inventory.addPart(new PartInHouse(partGeneratedID, "Dough", .05, 20, 7, 50, 1));
            partGeneratedID++;
            Inventory.addPart(new PartInHouse(partGeneratedID, "Cheese", .25, 25, 15, 75, 2));
            partGeneratedID++;
            Inventory.addPart(new PartInHouse(partGeneratedID, "Sauce", .05, 15, 5, 50, 3));

            ObservableList<Part> supreme = FXCollections.observableArrayList();;
            Inventory.addProduct(new Product(productGeneratedID, "Supreme", 19.99, 2, 0, 5, supreme));
            productGeneratedID++;
            ObservableList<Part> hawaiian = FXCollections.observableArrayList();;
            Inventory.addProduct(new Product(productGeneratedID, "Hawaiian", 15.99, 0, 0, 3, hawaiian));
            productGeneratedID++;
            ObservableList<Part> cheese = FXCollections.observableArrayList();;
            Inventory.addProduct(new Product(productGeneratedID, "Cheese", 10.99, 4, 0, 5, cheese));

            testCounter++;
        }
*/
        // End of Test Data...

        launch();
    }

}