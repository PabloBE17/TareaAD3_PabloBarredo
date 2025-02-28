package com.luisdbb.tarea3AD2024base.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
public class ObjectDBManager {

    private static final Path BASE_DIR = Paths.get(System.getProperty("user.home"), "tarea3AD2024");
    private static final Path DB_FOLDER = BASE_DIR.resolve("data");
    private static final Path DB_FILE = DB_FOLDER.resolve("bdtarea3ad.odb");
    private static final Path LOG_FOLDER = BASE_DIR.resolve("logs");
    private static final Path LOG_FILE = LOG_FOLDER.resolve("objectdb.log");

    private EntityManagerFactory emf;
    private EntityManager em;

    public ObjectDBManager() {
        try {
            Files.createDirectories(DB_FOLDER);
            Files.createDirectories(LOG_FOLDER);

            System.setProperty("objectdb.home", BASE_DIR.toString());

            emf = Persistence.createEntityManagerFactory("objectdb:" + DB_FILE.toAbsolutePath().toString());
        } catch (Exception e) {
            System.err.println("Error al configurar ObjectDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public void closeODB() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
