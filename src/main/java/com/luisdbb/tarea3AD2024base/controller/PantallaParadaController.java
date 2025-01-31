package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
@Controller
public class PantallaParadaController implements Initializable{
	@Autowired
    private EstanciaService estanciaService;
	@Autowired
    private ParadaService paradaService; 
	@Autowired
    private StageManager stageManager; 

    @FXML
    private Label label1; 
    @FXML
    private Label label2; 
    @FXML
    private Label label3; 
    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private Button buscarButton;

    @FXML
    private TableView<Estancia> estanciaTable;

    @FXML
    private TableColumn<Estancia, Long> idColumn;

    @FXML
    private TableColumn<Estancia, String> nombreColumn;

    @FXML
    private TableColumn<Estancia, LocalDate> region;  

    @FXML
    private TableColumn<Estancia, Boolean> responsable; 

    private ObservableList<Estancia> estanciaData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	cargarDatosParada();
    	buscarButton.setOnAction(event -> buscarEstancias());

        
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeregrino().getNombre()));
        region.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha())); 
        responsable.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isVip())); 
    }
    @FXML
    private void buscarEstancias() {
        Long idParada = (Sesion.getSesion() != null) ? Sesion.getSesion().getId() : null;

        if (idParada == null) {
            mostrarAlerta("Error", "No se encontró la parada en la sesión.");
            return;
        }

        LocalDate inicio = fechaInicio.getValue();
        LocalDate fin = fechaFin.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta("Error", "Debes seleccionar ambas fechas.");
            return;
        }

        List<Estancia> estancias = estanciaService.findByParadaIdAndFechaBetween(idParada, inicio, fin);

        if (estancias.isEmpty()) {
            mostrarAlerta("Información", "No hay estancias en ese período.");
        } else {
            estanciaData.setAll(estancias);
            estanciaTable.setItems(estanciaData);
        }
    }
    private void cargarDatosParada() {
        Long idParada = (Sesion.getSesion() != null) ? Sesion.getSesion().getId() : null;
        System.out.println("ID de la parada en sesión: " + idParada);

        if (idParada != null) {
            Parada parada = paradaService.findParadaById(idParada);
            if (parada != null) {
                label1.setText(parada.getNombre());
                label2.setText(String.valueOf(parada.getRegion()));
                label3.setText(parada.getResponsable());
            } else {
                setLabelText("Parada no encontrada");
            }
        } else {
            setLabelText("ID no válido");
        }
    }
    private void setLabelText(String text) {
        label1.setText(text);
        label2.setText(text);
        label3.setText(text);
    }
    @FXML
    private void salir(ActionEvent event) {
        stageManager.switchScene(FxmlView.MENU_PARADA);
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
