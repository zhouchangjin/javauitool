module com.gamewolf.usbback {
    requires javafx.controls;
    requires javafx.fxml;
    
   
    
    //opens com.gamewolf.usbback to javafx.fxml;
    opens com.gamewolf.usbback.controller to javafx.fxml;
    
    exports com.gamewolf.usbback;
    exports com.gamewolf.usbback.controller;
}
