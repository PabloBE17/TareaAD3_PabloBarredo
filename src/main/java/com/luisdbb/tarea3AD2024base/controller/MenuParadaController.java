package com.luisdbb.tarea3AD2024base.controller;

import javafx.scene.control.Button;
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
@Controller
public class MenuParadaController implements Initializable {
	@Autowired
    private StageManager stageManager;
	 @FXML
	    private Button sellarButton;
	    
	    @FXML
	    private Button exportarButton;
	    
	    @FXML
	    private Button salirButton;
	@FXML
    private void ExportarParada(ActionEvent event) {
        stageManager.switchScene(FxmlView.PANTALLA_PARADA);
    }
	@FXML
    private void Sellar(ActionEvent event) {
        stageManager.switchScene(FxmlView.SELLAR_ALOJAR);
    }
	@FXML
    private void Salir(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }
	
	 private void showAlert(Alert.AlertType alertType, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
