module com.gamewolf.examtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
    opens com.gamewolf.examtool.controller to javafx.fxml;
    exports com.gamewolf.examtool;
}
