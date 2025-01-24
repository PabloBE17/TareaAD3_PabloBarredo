package com.luisdbb.tarea3AD2024base.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuPeregrinoController implements Initializable {
	 @Autowired
	    private StageManager stageManager;
	 @Autowired
	    private CarnetService carnetService;

	    @Autowired
	    private ParadaService paradaService;

	    @Autowired
	    private Stage primaryStage;

	    private Sesion sesionActual;
	
	
	    @FXML
	    private void exportarCarnet(ActionEvent event) {
	        try {
	            Long idPeregrino = sesionActual.getId();

	            Carnet carnet = carnetService.find(idPeregrino);

	            if (carnet != null) {
	                List<Parada> paradasAsociadas = paradaService.bsucarParadasPorParadaInicial(carnet.getParadaInicial().getId());

	                String path = "src/main/resources/carnet_" + carnet.getId() + ".xml";
	                carnetService.exportarCarnetAXML(carnet, paradasAsociadas, path);

	                showAlert(Alert.AlertType.INFORMATION, "Exportación Exitosa", "El carnet se exportó exitosamente a XML.");
	            } else {
	                showAlert(Alert.AlertType.WARNING, "Carnet no encontrado", "No se encontró un carnet");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al exportar el carnet.");
	        }
	    }

	 
	    @FXML
	    private void salir(ActionEvent event) {
	        
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
			// TODO Auto-generated method stub
			
		}
}
