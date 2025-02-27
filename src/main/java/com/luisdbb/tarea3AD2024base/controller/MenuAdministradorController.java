package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
@Controller
public class MenuAdministradorController implements Initializable{
	@Autowired
    private StageManager stageManager;
	@FXML
    private Button AñadirPeregrinoButton;
	@FXML
    private Button AnadirServicioButton;
    @FXML
    private Button salirButton;
    
	@FXML
	private void añadirPeregrino(ActionEvent event) {
		stageManager.switchScene(FxmlView.AÑADIR_PARADA);
	}
	@FXML
	private void añadirServicio(ActionEvent event) {
		stageManager.switchScene(FxmlView.AÑADIR_SERVICIO);
	}
	@FXML
	private void editarServicio(ActionEvent event) {
		stageManager.switchScene(FxmlView.EDITAR_SERVICIO);
	}
	@FXML
    private void salir(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }
	
	@SuppressWarnings("unused")
	private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}