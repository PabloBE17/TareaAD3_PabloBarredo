package com.luisdbb.tarea3AD2024base.controller;

import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

@Controller
public class AnadirPeregrinoController implements Initializable {
	@Autowired
    private StageManager stageManager;
	 @Autowired
	    private PeregrinoService peregrinoService;
	 @Autowired
	 private UsuarioServicio usuarioService;
	 @Autowired
	 private CarnetService carnetService;

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
	            if (nacionalidades.isEmpty()) {
	                showAlert(Alert.AlertType.WARNING, "Aviso", "No se encontraron nacionalidades en el archivo XML.");
	            } else {
	                nacionalidadComboBox.getItems().addAll(nacionalidades);
	            }
	        } catch (Exception e) {
	            showAlert(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las nacionalidades.");
	        }
	    }
	    @FXML
	    private void guardarPeregrino(ActionEvent event) {
	        String nombre = nombreField.getText();
	        String nacionalidad = nacionalidadComboBox.getValue();
	        String paradaInicial = paradaInicialComboBox.getValue();
	        String contraseña = contrasenaField.getText();
	        String correo = emailField.getText();

	        if (nombre.isEmpty() || nacionalidad == null || paradaInicial == null || correo.isEmpty() || contraseña.isEmpty()) {
	            showAlert(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa todos los campos.");
	            return;
	        }
	        
	        if (usuarioService.existePorNombre(nombre)) {
	            showAlert(Alert.AlertType.ERROR, "Error", "El nombre de usuario ya existe.");
	            return;
	        }
	        if (!esCorreoValido(correo)) {
	            showAlert(Alert.AlertType.ERROR, "Correo Inválido", "Por favor, ingresa un correo válido.");
	            return;
	        }

	        if (usuarioService.existePorCorreo(correo)) {
	            showAlert(Alert.AlertType.ERROR, "Error", "El correo ya está registrado.");
	            return;
	        }
	        
	        if (contieneEspacios(nombre) || contieneEspacios(contraseña)) {
	            showAlert(Alert.AlertType.ERROR, "Error de Entrada", "El nombre de usuario y la contraseña no pueden contener espacios.");
	            return;
	        }
	        Peregrino peregrino = new Peregrino();
	        peregrino.setNombre(nombre);
	        peregrino.setNacionalidad(nacionalidad);
	        peregrino.setEmail(correo);

	      
	        Parada parada = paradaService.findByNombre(paradaInicial);
	       
	        Usuario usuario = new Usuario();
	        usuario.setNombre(nombre);
	        usuario.setCorreo(correo);
	        usuario.setPassword(contraseña);
	        usuario.setRol(Rol.PEREGRINO); 
	        usuario.setPeregrino(peregrino); 

	        Carnet carnet = new Carnet();
	        carnet.setFechaExpedicion(LocalDate.now()); 
	        carnet.setDistancia(0); 
	        carnet.setNumVips(0); 
	        carnet.setParadaInicial(parada); 
	        peregrino.setCarnet(carnet);
	        
	        carnetService.save(carnet); 
	        peregrinoService.save(peregrino);
	        usuarioService.save(usuario);
	        
	        
	        

	        
	        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Peregrino y usuario registrados correctamente.");

	        
	        limpiarCampos();
	        stageManager.switchScene(FxmlView.LOGIN);
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
	        stageManager.switchScene(FxmlView.LOGIN);
	    }

	    private void showAlert(Alert.AlertType alertType, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    private List<String> obtenerNacionalidades() {
	        List<String> nacionalidades = new ArrayList<>();

	        try {
	            File file = new File("src/main/resources/paises.xml");
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document document = builder.parse(file);

	            document.getDocumentElement().normalize();

	            NodeList nodeList = document.getElementsByTagName("pais");

	            for (int i = 0; i < nodeList.getLength(); i++) {
	                Node node = nodeList.item(i);

	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;

	                    NodeList nombreList = element.getElementsByTagName("nombre");

	                    if (nombreList.getLength() > 0) {
	                        String nombrePais = nombreList.item(0).getTextContent().trim();
	                        nacionalidades.add(nombrePais);
	                    } else {
	                        System.err.println(" Advertencia: Nodo <nombre> no encontrado en país " + (i + 1));
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Error al leer el archivo XML: " + e.getMessage());
	            e.printStackTrace();
	        }

	        return nacionalidades;
	    }
	    private void cargarParadas() {
	        List<Parada> paradas = paradaService.findAll();
	        for (Parada parada : paradas) {
	            paradaInicialComboBox.getItems().add(parada.getNombre());
	        }
	    }
	    private boolean esCorreoValido(String correo) {
	        String patronCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	        return Pattern.matches(patronCorreo, correo);
	    }
	    private boolean contieneEspacios(String input) {
	        return input.contains(" ");
	    }
	}


    


