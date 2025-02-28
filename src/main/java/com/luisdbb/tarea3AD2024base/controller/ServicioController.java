package com.luisdbb.tarea3AD2024base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.Db4oServicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class ServicioController {
	@Autowired
    private StageManager stageManager;
    @FXML
    private TextField NombreServicioField;

    @FXML
    private TextField PrecioServcioField;

    @FXML
    private Button AñadirButton;

    @FXML
    private Button salir;
    @FXML
    private TableView<Parada> paradasTableView;

    @FXML
    private TableColumn<Parada, Long> idColumn;

    @FXML
    private TableColumn<Parada, String> nombreColumn;

    @Autowired
    private Db4oServicio db4oServicio;
    @Autowired
    private ParadaService paradaService;
    @FXML
    private void initialize() {
        cargarParadas();
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        paradasTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    @FXML
    private void crearServicio() {
        String nombre = NombreServicioField.getText().trim();
        String precioTexto = PrecioServcioField.getText().trim();

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
        
        List<Servicio> serviciosExistentes = db4oServicio.obtenerTodosLosServicios();
        for (Servicio servicio : serviciosExistentes) {
            if (servicio.getNombre().equalsIgnoreCase(nombre)) {
                mostrarAlerta("Error", "El servicio '" + nombre + "' ya existe.");
                return;
            }
        }

        List<Parada> paradasSeleccionadas = new ArrayList<>(paradasTableView.getSelectionModel().getSelectedItems());
        if (paradasSeleccionadas.isEmpty()) {
            mostrarAlerta("Error", "Debes seleccionar al menos una parada.");
            return;
        }

        List<Long> idParadas = new ArrayList<>();
        for (Parada parada : paradasSeleccionadas) {
            idParadas.add(parada.getId());
        }

        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombre(nombre);
        nuevoServicio.setPrecio(precio);
        nuevoServicio.setIdParadas(idParadas);

        db4oServicio.guardarServicio(nuevoServicio);
        mostrarAlerta("Éxito", "Servicio creado correctamente.");

        NombreServicioField.clear();
        PrecioServcioField.clear();
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
    private void cargarParadas() {
        List<Parada> paradas = paradaService.findAll();
        paradasTableView.getItems().setAll(paradas);
        paradasTableView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE); 
    }
}
