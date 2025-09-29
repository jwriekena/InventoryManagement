module jriekena.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens jriekena.inventorymanagement to javafx.fxml;
    exports jriekena.inventorymanagement;
    exports jriekena.inventorymanagement.Controller;
    opens jriekena.inventorymanagement.Controller to javafx.fxml;
    opens jriekena.inventorymanagement.Model to javafx.base;
}