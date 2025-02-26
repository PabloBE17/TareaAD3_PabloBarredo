package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.Db4oServicio;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class SellarAlojarController implements Initializable {
	@Autowired
	private StageManager stageManager;
    @Autowired
    private ParadaService paradaService;

    @Autowired
    private CarnetService carnetService;
    @Autowired
    private PeregrinoService peregrinoService;
    @Autowired
    private EstanciaService estanciaService;
    @Autowired
    private Db4oServicio db4oService;
    @FXML
    private TableView<Servicio> serviciosTableView;

    @FXML
    private TableColumn<Servicio, String> nombreColumn;

    @FXML
    private TableColumn<Servicio, Double> precioColumn;
    @FXML
    private TextField modoPagoField;
    @FXML
    private TextField extrasField;
    @FXML
    private Label nombreLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Label responsableLabel;

    @FXML
    private ComboBox<String> peregrinoComboBox;

    @FXML
    private TextField kmRecorridosField;

    @FXML
    private CheckBox estanciaCheckBox;

    @FXML
    private CheckBox vipCheckBox;

    @FXML
    private Button sellarButton;
    @FXML
    private Button salir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosParada();
        cargarPeregrinos();
        estanciaCheckBox.setOnAction(event -> manejarCheckboxVip());
        vipCheckBox.setOnAction(event -> manejarCheckboxVip());
        kmRecorridosField.textProperty().addListener((observable, viejoValor, nuevoValue) -> {
            if (!nuevoValue.matches("\\d{0,4}")) { 
                kmRecorridosField.setText(viejoValor); 
            }
        });
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        precioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        cargarServicios();
        serviciosTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void cargarDatosParada() {
        Long idParada = (Sesion.getSesion() != null) ? Sesion.getSesion().getId() : null;
        System.out.println("ID de la parada en sesión: " + idParada);

        if (idParada != null) {
            Parada parada = paradaService.findParadaById(idParada);
            if (parada != null) {
                nombreLabel.setText(parada.getNombre());
                regionLabel.setText(String.valueOf(parada.getRegion()));
                responsableLabel.setText(parada.getResponsable());
            } else {
                setLabelText("Parada no encontrada");
            }
        } else {
            setLabelText("ID no válido");
        }
    }

    @FXML
    private void sellarAlojar(ActionEvent event) {
        try {
            if (peregrinoComboBox.getValue() == null || kmRecorridosField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Por favor, selecciona un peregrino y completa los kilómetros recorridos.");
                return;
            }

            String selectedPeregrino = peregrinoComboBox.getValue();
            Long idCarnet = Long.parseLong(selectedPeregrino.split(" - ")[0]);
            Double kmRecorridos = Double.parseDouble(kmRecorridosField.getText());
            boolean vip = vipCheckBox.isSelected();
            boolean estanciado = estanciaCheckBox.isSelected();
            Carnet carnet = carnetService.find(idCarnet);
            if (carnet == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el carnet con el ID ingresado.");
                return;
            }

            Long idParada = Sesion.getSesion().getId();
            Parada parada = paradaService.findParadaById(idParada);
            if (parada == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró la parada en la base de datos.");
                return;
            }

            Peregrino peregrino = peregrinoService.find(idCarnet);
            if (peregrino == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el peregrino en la base de datos.");
                return;
            }

            if (parada.getPeregrinos().contains(peregrino)) {
                showAlert(Alert.AlertType.WARNING, "Aviso", "El peregrino ya ha estado en esta parada y no puede volver a sellar.");
                return;
            }

            carnet.setDistancia(carnet.getDistancia() + kmRecorridos);
            if (vip) {
                carnet.setNumVips(carnet.getNumVips() + 1);
            }

            if (!peregrino.getParada().contains(parada)) {
                peregrino.getParada().add(parada);
            }
            if (!parada.getPeregrinos().contains(peregrino)) {
                parada.getPeregrinos().add(peregrino);
            }

            
            peregrinoService.save(peregrino);
            paradaService.save(parada);
            carnetService.save(carnet);
            
            

           
            if (estanciado) {
                Estancia estancia = new Estancia();
                estancia.setFecha(java.time.LocalDate.now());
                estancia.setVip(vip);
                estancia.setParada(parada);
                estancia.setPeregrino(peregrino);
                estanciaService.saveEstancia(estancia);
            }
            crearConjuntoContratado();
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Carnet actualizado correctamente.");
            
            stageManager.switchScene(FxmlView.MENU_PARADA);
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, ingresa valores numéricos válidos.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un problema al actualizar el carnet.");
        }
    }
    private void manejarCheckboxVip() {
        if (!estanciaCheckBox.isSelected()) {
            if (vipCheckBox.isSelected()) {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Debes marcar 'Estancia' antes de seleccionar 'Es VIP'.");
                vipCheckBox.setSelected(false);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    private void setLabelText(String text) {
        nombreLabel.setText(text);
        regionLabel.setText(text);
        responsableLabel.setText(text);
    }
    @FXML
    private void salir(ActionEvent event) {
        stageManager.switchScene(FxmlView.MENU_PARADA);
    }
    private void cargarPeregrinos() {
        List<Peregrino> peregrinos = peregrinoService.findAll();
        for (Peregrino peregrino : peregrinos) {
            peregrinoComboBox.getItems().add(peregrino.getId() + " - " + peregrino.getNombre());
        }
    }
    private void cargarServicios() {
        Long idParada = Sesion.getSesion().getId();
        
        

        List<Servicio> servicios = db4oService.obtenerServiciosPorParada(idParada);
        
        if (servicios.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Información", "No hay servicios disponibles para esta parada.");
        }

        serviciosTableView.getItems().setAll(servicios);
    }
   private void crearConjuntoContratado() {
      Long idParada = Sesion.getSesion().getId();


        List<Servicio> serviciosSeleccionados = new ArrayList<>(serviciosTableView.getSelectionModel().getSelectedItems());

        if (serviciosSeleccionados.isEmpty()) {
           showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar al menos un servicio.");
            return;
      }

       String modoPagoTexto = modoPagoField.getText().trim();
        String extrasTexto = extrasField.getText().trim();

        if (modoPagoTexto.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar un modo de pago.");
            return;
       }

       char modoPago = modoPagoTexto.charAt(0); 

       double precioTotal = serviciosSeleccionados.stream().mapToDouble(Servicio::getPrecio).sum();

        List<Long> idServicios = new ArrayList<>();
        for (Servicio servicio : serviciosSeleccionados) {
            idServicios.add(servicio.getId());
        }

        ConjuntoContratado conjunto = new ConjuntoContratado();
        ; 
       conjunto.setIdEstancia(idParada);
        conjunto.setModoPago(modoPago);
        conjunto.setExtra(extrasTexto);
       conjunto.setPrecioTotal(precioTotal);
       conjunto.setIdServivcio(idServicios);

       db4oService.guardarConjuntoContratado(conjunto);

      showAlert(Alert.AlertType.INFORMATION, "Éxito", "Conjunto de servicios contratado correctamente.");
    }
}