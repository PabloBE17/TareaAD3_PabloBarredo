package com.luisdbb.tarea3AD2024base.controller;

import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Controller
public class MenuPeregrinoController implements Initializable {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private CarnetService carnetService;
    @SuppressWarnings("unused")
	@Autowired
    private PeregrinoService peregrinoService;
    @Autowired
    private EstanciaService estanciaService;

    @Autowired
    private ParadaService paradaService;

    @Autowired
    private Stage primaryStage;
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
      
    }

    @FXML
    private void exportarCarnet(ActionEvent event) {
        try {
            if (Sesion.getSesion() == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró la sesión del usuario.");
                return;
            }

            Long idPeregrino = Sesion.getSesion().getId();
            Carnet carnet = carnetService.find(idPeregrino);

            if (carnet == null) {
                showAlert(Alert.AlertType.WARNING, "Aviso", "No se encontró un carnet para este peregrino.");
                return;
            }

            List<Estancia> estancias = estanciaService.findByPeregrinoId(idPeregrino);
            
            List<Parada> paradasVisitadas = paradaService.findByPeregrinoId(idPeregrino);

            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Carnet XML");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos XML", "*.xml"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
            	exportarCarnetAXML(carnet, estancias, paradasVisitadas, file.getAbsolutePath());
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Carnet exportado correctamente.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Exportación cancelada por el usuario.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al exportar el carnet.");
        }
    }

    private void exportarCarnetAXML(Carnet carnet, List<Estancia> estancias, List<Parada> paradasVisitadas, String outputPath) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Carnet");
            doc.appendChild(rootElement);

            Element idElement = doc.createElement("ID");
            idElement.appendChild(doc.createTextNode(String.valueOf(carnet.getId())));
            rootElement.appendChild(idElement);

            Element fechaElement = doc.createElement("FechaExpedicion");
            fechaElement.appendChild(doc.createTextNode(carnet.getFechaExpedicion().toString()));
            rootElement.appendChild(fechaElement);

            Element distanciaElement = doc.createElement("DistanciaTotal");
            distanciaElement.appendChild(doc.createTextNode(String.valueOf(carnet.getDistancia())));
            rootElement.appendChild(distanciaElement);

            Element vipsElement = doc.createElement("NumVips");
            vipsElement.appendChild(doc.createTextNode(String.valueOf(carnet.getNumVips())));
            rootElement.appendChild(vipsElement);

            Element lugarExpElement = doc.createElement("LugarExp");
            lugarExpElement.appendChild(doc.createTextNode(carnet.getParadaInicial().getNombre()));
            rootElement.appendChild(lugarExpElement);

            Element fechaActualElement = doc.createElement("Fecha");
            fechaActualElement.appendChild(doc.createTextNode(LocalDate.now().toString()));
            rootElement.appendChild(fechaActualElement);
            
            
            Element estanciasElement = doc.createElement("Estancias");
            rootElement.appendChild(estanciasElement);

            for (Estancia estancia : estancias) {
                Element estanciaElement = doc.createElement("Estancia");

                Element idEstanciaElement = doc.createElement("ID");
                idEstanciaElement.appendChild(doc.createTextNode(String.valueOf(estancia.getId())));
                estanciaElement.appendChild(idEstanciaElement);

                Element fechaEstanciaElement = doc.createElement("Fecha");
                fechaEstanciaElement.appendChild(doc.createTextNode(estancia.getFecha().toString()));
                estanciaElement.appendChild(fechaEstanciaElement);

                Element vipElement = doc.createElement("VIP");
                vipElement.appendChild(doc.createTextNode(String.valueOf(estancia.isVip())));
                estanciaElement.appendChild(vipElement);

                Element idParadaElement = doc.createElement("ID_Parada");
                idParadaElement.appendChild(doc.createTextNode(String.valueOf(estancia.getParada().getId())));
                estanciaElement.appendChild(idParadaElement);

                Element idPeregrinoElement = doc.createElement("ID_Peregrino");
                idPeregrinoElement.appendChild(doc.createTextNode(String.valueOf(estancia.getPeregrino().getId())));
                estanciaElement.appendChild(idPeregrinoElement);

                estanciasElement.appendChild(estanciaElement);
            }
            Element paradasElement = doc.createElement("ParadasVisitadas");
            rootElement.appendChild(paradasElement);

            for (Parada parada : paradasVisitadas) {
                Element paradaElement = doc.createElement("Parada");

                Element idParadaElement = doc.createElement("ID");
                idParadaElement.appendChild(doc.createTextNode(String.valueOf(parada.getId())));
                paradaElement.appendChild(idParadaElement);

                Element nombreParadaElement = doc.createElement("Nombre");
                nombreParadaElement.appendChild(doc.createTextNode(parada.getNombre()));
                paradaElement.appendChild(nombreParadaElement);

                Element regionParadaElement = doc.createElement("Region");
                regionParadaElement.appendChild(doc.createTextNode(String.valueOf(parada.getRegion())));
                paradaElement.appendChild(regionParadaElement);

                paradasElement.appendChild(paradaElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);

            System.out.println("Archivo XML generado: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error al generar el archivo XML.");
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
}