/**
 * <p>
 * John Riekena - C482 Performance Assessment - Inventory Management.
 * FUTURE ENHANCEMENTS:
 *  - Convert the generated IDs to an array for contiguous IDs when objects are deleted or added.
 *  - Create an external database to store inventory in a persistent(between running of the application) way.
 *  - Create a custom exceptions class to streamline error handling instead of using if/else statements.
 *  - Making the search boxes searchable on pressing the enter key as well as by clicking the search button.
 *  - Make the price of a product correlate to the sum of the prices of associated parts.
 *  - Improve the UX of the dialog boxes and their messages.
 *
 *  RUNTIME ERRORS ENCOUNTERED
 *  - On the add parts menu the entered minimum and maximum values were being saved to the opposite variables due to a typing error.
 *          Changed the names to their correct
 *  - While writing the updatePart method using the id as the index created an out of range exception when appending the allParts list.
 *          Using the id-1 as the index solved this problem as the indexes start at 0 and the ids start at 1. This also caused issues.
 *          Finally obtained the desired result by using the object index in its respective ObservableList as the base for generating the id.
 *  - When adding or modifying products the list of associated parts was not persisting between screens.
 *          Moved the product constructor call from the save function to a class level private instance to work with.</p>
 */
package jriekena.inventorymanagement;
