package com.luisdbb.tarea3AD2024base.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

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

    @FXML
    private Label nombreLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Label responsableLabel;

    @FXML
    private TextField idPeregrinoField;

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
            if (idPeregrinoField.getText().isEmpty() || kmRecorridosField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Por favor, completa todos los campos.");
                return;
            }

            Long idCarnet = Long.parseLong(idPeregrinoField.getText());
            Double kmRecorridos = Double.parseDouble(kmRecorridosField.getText());
            boolean vip = vipCheckBox.isSelected();
            boolean estanciado = estanciaCheckBox.isSelected();

            Carnet carnet = carnetService.find(idCarnet);
            if (carnet == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el carnet con el ID ingresado.");
                return;
            }

            carnet.setDistancia(carnet.getDistancia() + kmRecorridos);
            if (vip) {
                carnet.setNumVips(carnet.getNumVips() + 1);
            }

            if (estanciado) {
                Long idParada = (Sesion.getSesion() != null) ? Sesion.getSesion().getId() : null;
                if (idParada == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró la parada actual.");
                    return;
                }

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

                Estancia estancia = new Estancia();
                estancia.setFecha(java.time.LocalDate.now()); 
                estancia.setVip(vip);
                estancia.setParada(parada);
                estancia.setPeregrino(peregrino);

                estanciaService.saveEstancia(estancia);
            }

            carnetService.save(carnet);

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Carnet y estancia actualizados correctamente.");
            showConfirmationAlert();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, ingresa valores numéricos válidos.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un problema al actualizar el carnet.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationAlert() {
        Alert confirmAlert = new Alert(AlertType.INFORMATION);
        confirmAlert.setTitle("Confirmación");
        confirmAlert.setHeaderText("¡Datos guardados!");
        confirmAlert.setContentText("El carnet ha sido actualizado correctamente.");
        
        confirmAlert.showAndWait();
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
}