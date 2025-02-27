package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EnvioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class MostrarEnvioContoller implements Initializable {

    @Autowired
    private EnvioService envioService;
    @Autowired
    private StageManager stageManager;
    @FXML
    private TableView<EnvioACasa> enviosTable;
    @FXML
    private TableColumn<EnvioACasa, Long> idColumn;
    @FXML
    private TableColumn<EnvioACasa, Double> pesoColumn;
    @FXML
    private TableColumn<EnvioACasa, Integer> volumenColumn;
    @FXML
    private TableColumn<EnvioACasa, Boolean> urgenteColumn;
    @FXML
    private TableColumn<EnvioACasa, String> direccionColumn;
    @FXML
    private TableColumn<EnvioACasa, String> localidadColumn;
    @FXML
    private TableColumn<EnvioACasa, Long> idParadaColumn;
    @FXML
    private Button salirBoton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarEnvios();
    }

    private void cargarEnvios() {
    	  Long idParada = (Sesion.getSesion() != null) ? Sesion.getSesion().getId() : null;
    	    
    	    if (idParada == null) {
    	        showAlert(Alert.AlertType.ERROR, "Error", "No se pudo obtener la parada del usuario en sesión.");
    	        return;
    	    }

    	    List<EnvioACasa> envios = envioService.obtenerTodosLosEnvios();
    	    
    	    List<EnvioACasa> enviosFiltrados = envios.stream()
                    .filter(envio -> envio.getIdParada() == idParada) 
                    .toList();

    	enviosTable.getItems().setAll(enviosFiltrados);
        
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        pesoColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());
        volumenColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVolumen()).asObject());
        urgenteColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isUrgente()).asObject());
        direccionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion()));
        localidadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getLocalidad()));
        idParadaColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIdParada()).asObject());
    }

    @FXML
    private void salir() {
    	stageManager.switchScene(FxmlView.MENU_PARADA);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}