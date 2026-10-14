module com.gamewolf.examtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
	requires org.apache.poi.ooxml;
    opens com.gamewolf.examtool.controller to javafx.fxml;
    exports com.gamewolf.examtool;
}
