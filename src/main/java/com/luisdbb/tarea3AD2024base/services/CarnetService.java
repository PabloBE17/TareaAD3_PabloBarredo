package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Service
@Transactional
public class CarnetService {

    @Autowired
    private CarnetRepository carnetRepository;

    public Carnet save(Carnet entity) {
        return carnetRepository.save(entity);
    }

    public Carnet update(Carnet entity) {
        return carnetRepository.save(entity);
    }

    public void delete(Carnet entity) {
        carnetRepository.delete(entity);
    }

    public void delete(Long id) {
        carnetRepository.deleteById(id);
    }

    public Carnet find(Long id) {
        return carnetRepository.findById(id).orElse(null);
    }

    public List<Carnet> findAll() {
        return carnetRepository.findAll();
    }

    public boolean existsById(Long id) {
        return carnetRepository.existsById(id);
    }

    public void exportarCarnetAXML(Carnet carnet, List<Parada> paradasAsociadas, String outputPath) {
        if (paradasAsociadas == null || paradasAsociadas.isEmpty()) {
            throw new IllegalArgumentException("La lista de paradas asociadas está vacía o es nula.");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.newDocument();

            org.w3c.dom.Element rootElement = doc.createElement("Carnet");
            doc.appendChild(rootElement);

            org.w3c.dom.Element id = doc.createElement("ID");
            id.appendChild(doc.createTextNode(String.valueOf(carnet.getId())));
            rootElement.appendChild(id);

            org.w3c.dom.Element fecha = doc.createElement("FechaExpedicion");
            fecha.appendChild(doc.createTextNode(carnet.getFechaExpedicion().toString()));
            rootElement.appendChild(fecha);

            org.w3c.dom.Element lugarExp = doc.createElement("LugarExp");
            lugarExp.appendChild(doc.createTextNode(carnet.getParadaInicial().getNombre()));
            rootElement.appendChild(lugarExp);

            org.w3c.dom.Element paradasElement = doc.createElement("Paradas");
            rootElement.appendChild(paradasElement);

            for (Parada parada : paradasAsociadas) {
                org.w3c.dom.Element paradaElement = doc.createElement("Parada");

                org.w3c.dom.Element idParada = doc.createElement("ID");
                idParada.appendChild(doc.createTextNode(String.valueOf(parada.getId())));
                paradaElement.appendChild(idParada);

                org.w3c.dom.Element nombreParada = doc.createElement("Nombre");
                nombreParada.appendChild(doc.createTextNode(parada.getNombre()));
                paradaElement.appendChild(nombreParada);

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
        }
    }
}
