package sample;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main_controller {
    @FXML
    private Button add_part;

    // Parts table
    @FXML private VBox part_vbox;  // box for parts table
    @FXML private TableView<Part> parts_table;  //Makes the Parts table use Part objects.
    @FXML private TableColumn<Part, Integer> part_id_column;
    @FXML private TableColumn<Part, String> part_name_column;
    @FXML private TableColumn<Part, Integer> part_inv_column;
    @FXML private TableColumn<Part, Double> part_price_column;

    public void initialize() {
        showAllParts();
    }

    public void addPartScreen() throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("addPart_scene.fxml"));
        Parent root = (Parent) loadFXML.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> showAllParts());  // Refreshes the parts table when the window is closed.
    }

    public void showAllParts(){
        part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        parts_table.getItems().setAll(Main.database.getAllParts());  // Use the parts database to output to the table.
    }

}
