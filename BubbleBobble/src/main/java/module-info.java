module com.example.BubbleBobble {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;


    opens com.example.BubbleBobble to javafx.fxml;
    opens com.example.BubbleBobble.Controller to javafx.fxml;
    opens com.example.BubbleBobble.Model.User to javafx.base;
    exports com.example.BubbleBobble;
}