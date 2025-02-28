package com.luisdbb.tarea3AD2024base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.Db4oServicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;
import com.luisdbb.tarea3AD2024base.config.StageManager;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class EditarServicioController {
	

    @Autowired
    private Db4oServicio db4oServicio;
    @Autowired
    private ParadaService paradaService;
    @Autowired
    private StageManager stageManager;
    
    @FXML
    private TextField NombreServicioField;
    
    @FXML
    private TextField PrecioServicioField;
    
    @FXML
    private Button GuardarButton;
    
    @FXML
    private Button CancelarButton;
    
    @FXML
    private TableView<Servicio> tablaServicios;

    @FXML
    private TableColumn<Servicio, Long> idServicioColumn;
    
    @FXML
    private TableColumn<Servicio, String> nombreServicioColumn;
    
    @FXML
    private TableView<Parada> paradasTableView;

    @FXML
    private TableColumn<Parada, Long> idParadaColumn;

    @FXML
    private TableColumn<Parada, String> nombreParadaColumn;

    private Servicio servicioSeleccionado;
    
    @FXML
    private void initialize() {
        cargarServicios();
        cargarParadas();
        
        idServicioColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nombreServicioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        idParadaColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nombreParadaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        tablaServicios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> seleccionarServicio(newValue));

        
    }
    
    private void cargarServicios() {
        List<Servicio> servicios = db4oServicio.obtenerTodosLosServicios();
        tablaServicios.getItems().setAll(servicios);
    }

    private void cargarParadas() {
        List<Parada> paradas = paradaService.findAll(); 
        paradasTableView.getItems().setAll(paradas);
        paradasTableView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        if (servicioSeleccionado != null) {
            List<Long> idParadasServicio = servicioSeleccionado.getIdParadas(); 

            paradasTableView.getSelectionModel().clearSelection();
            for (int i = 0; i < paradas.size(); i++) {
                if (idParadasServicio.contains(paradas.get(i).getId())) {
                    paradasTableView.getSelectionModel().select(i);
                }
            }
        }
    }
    
    private void seleccionarServicio(Servicio servicio) {
        if (servicio != null) {
            this.servicioSeleccionado = servicio;
            NombreServicioField.setText(servicio.getNombre());
            PrecioServicioField.setText(String.valueOf(servicio.getPrecio()));
            cargarParadas();
        }
    }
    @FXML
    private void actualizarServicio() {
        if (servicioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un servicio para editar.");
            return;
        }

        String nombre = NombreServicioField.getText().trim();
        String precioTexto = PrecioServicioField.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }
        if (!nombre.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            mostrarAlerta("Error", "El nombre del servicio solo puede contener letras y espacios.");
            return;
        }
        
        if (!precioTexto.matches("\\d{1,5}(\\.\\d{1,2})?")) {
            mostrarAlerta("Error", "El precio debe tener como máximo 5 cifras enteras y hasta 2 decimales.");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
            if (precio < 0) {
                mostrarAlerta("Error", "El precio no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un precio válido.");
            return;
        }

        List<Parada> paradasSeleccionadas = new ArrayList<>(paradasTableView.getSelectionModel().getSelectedItems());

        if (paradasSeleccionadas.isEmpty()) {
            mostrarAlerta("Error", "Debes seleccionar al menos una parada.");
            return;
        }

        List<Long> nuevasIdParadas = new ArrayList<>();
        for (Parada parada : paradasSeleccionadas) {
            nuevasIdParadas.add(parada.getId());
        }

        List<Long> paradasAntiguas = new ArrayList<>(servicioSeleccionado.getIdParadas());

        List<Long> paradasEliminadas = new ArrayList<>();
        for (Long idParada : paradasAntiguas) {
            if (!nuevasIdParadas.contains(idParada)) {
                paradasEliminadas.add(idParada);
            }
        }

        servicioSeleccionado.setIdParadas(nuevasIdParadas);

        servicioSeleccionado.setNombre(nombre);
        servicioSeleccionado.setPrecio(precio);

        db4oServicio.guardarServicio(servicioSeleccionado);
        mostrarAlerta("Éxito", "Servicio actualizado correctamente.\n"
                + "Paradas añadidas: " + nuevasIdParadas.size() + "\n"
                + "Paradas eliminadas: " + paradasEliminadas.size());
        mostrarAlerta("Éxito", "Servicio actualizado correctamente y asignado a las paradas seleccionadas.");

        NombreServicioField.clear();
        PrecioServicioField.clear();

        cargarServicios();
        stageManager.switchScene(FxmlView.MENU_ADMIN);

    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void cerrarVentana() {
        stageManager.switchScene(FxmlView.MENU_ADMIN);
    }
}
