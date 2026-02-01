module com.safa.chatudp_cliente {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.safa.chatudp_cliente to javafx.fxml;
    exports com.safa.chatudp_cliente;
}