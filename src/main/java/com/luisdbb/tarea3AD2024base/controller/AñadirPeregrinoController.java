package com.luisdbb.tarea3AD2024base.controller;

import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

@Controller
public class AñadirPeregrinoController implements Initializable {
	 @Autowired
	    private PeregrinoService peregrinoService;

	    @Autowired
	    private ParadaService paradaService;

	    @FXML
	    private TextField nombreField;

	    @FXML
	    private TextField contrasenaField;

	    @FXML
	    private TextField emailField;

	    @FXML
	    private ComboBox<String> nacionalidadComboBox;

	    @FXML
	    private ComboBox<String> paradaInicialComboBox;

	    @FXML
	    private Button guardarButton;

	    @FXML
	    private Button cancelarButton;

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	        cargarNacionalidades();

	        cargarParadas();
	    }

	    private void cargarNacionalidades() {
	        try {
	            List<String> nacionalidades = obtenerNacionalidades();
	            nacionalidadComboBox.getItems().addAll(nacionalidades);
	        } catch (Exception e) {
	            showAlert(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las nacionalidades.");
	        }
	    }

	    private void cargarParadas() {
	        List<Parada> paradas = paradaService.findAll();
	        for (Parada parada : paradas) {
	            paradaInicialComboBox.getItems().add(parada.getNombre());
	        }
	    }
	    @FXML
	    private void guardarPeregrino(ActionEvent event) {
	        String nombre = nombreField.getText();
	        String nacionalidad = nacionalidadComboBox.getValue();
	        String paradaInicial = paradaInicialComboBox.getValue();
	        String contraseña=contrasenaField.getText();
	        String correo=emailField.getText();

	        if (nombre.isEmpty() || nacionalidad == null || paradaInicial == null || correo == null || contraseña == null) {
	            showAlert(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa todos los campos.");
	            return;
	        }

	        Peregrino peregrino = new Peregrino();
	        peregrino.setNombre(nombre);
	        peregrino.setNacionalidad(nacionalidad);
	        peregrino.setEmail(correo);
	        

	        Parada parada = paradaService.findByNombre(paradaInicial);
	        if (parada != null) {
	            peregrino.getParada().add(parada);
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Error", "La parada seleccionada no existe.");
	            return;
	        }

	        peregrinoService.save(peregrino);
	        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Peregrino registrado correctamente.");

	        limpiarCampos();
	    }
	    private void limpiarCampos() {
	        nombreField.clear();
	        contrasenaField.clear();
	        emailField.clear();
	        nacionalidadComboBox.getSelectionModel().clearSelection();
	        paradaInicialComboBox.getSelectionModel().clearSelection();
	    }

	    @FXML
	    private void cancelar(ActionEvent event) {
	        limpiarCampos();
	    }

	    private void showAlert(Alert.AlertType alertType, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    public static List<String> obtenerNacionalidades() {
	        List<String> nacionalidades = new ArrayList<>();
	        try {
	            
	            File file = new File("src/main/resources/paises.xml");

	           
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(file);
	            doc.getDocumentElement().normalize();

	            
	            NodeList nodeList = doc.getElementsByTagName("pais");

	            
	            for (int i = 0; i < nodeList.getLength(); i++) {
	                Node node = nodeList.item(i);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    Element element = (Element) node;
	                    String nombre = ((Document) element).getElementsByTagName("nombre").item(0).getTextContent();
	                    nacionalidades.add(nombre);
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error al cargar las nacionalidades desde el archivo XML.");
	        }
	        return nacionalidades;
	    }
	}


    


