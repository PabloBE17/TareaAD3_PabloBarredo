package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ParadaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
@Controller
public class PantallaParadaController implements Initializable{
	@Autowired
    private ParadaService paradaService; // Servicio para obtener las paradas desde la BD.

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private Button buscarButton;

    @FXML
    private TableView<Parada> paradaTable;

    @FXML
    private TableColumn<Parada, Long> idColumn;

    @FXML
    private TableColumn<Parada, String> nombreColumn;

    @FXML
    private TableColumn<Parada, String> region;

    @FXML
    private TableColumn<Parada, String> responsable;

    private ObservableList<Parada> paradaData = FXCollections.observableArrayList();
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
