module com.example.gpacalculatorapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gpacalculatorapp to javafx.fxml;
    exports com.example.gpacalculatorapp;
}