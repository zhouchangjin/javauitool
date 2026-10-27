module com.gamewolf.examtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
	requires org.apache.poi.ooxml;
	requires ik.analyzer;
	requires lucene.core;
    opens com.gamewolf.examtool.controller to javafx.fxml;
    exports com.gamewolf.examtool;
}
