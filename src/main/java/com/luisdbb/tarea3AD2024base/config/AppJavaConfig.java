/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisdbb.tarea3AD2024base.config;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration
public class AppJavaConfig {
	
    @Autowired 
    SpringFXMLLoader springFXMLLoader;

//    /**
//     * Useful when dumping stack trace to a string for logging.
//     * @return ExceptionWriter contains logging utility methods
//     */
//    @Bean
//    @Scope("prototype")
//    public ExceptionWriter exceptionWriter() {
//        return new ExceptionWriter(new StringWriter());
//    }
    @Bean
    public Stage primaryStage() {
        final Stage[] stageHolder = new Stage[1];
        
        // Ejecuta en el hilo de JavaFX
        Platform.runLater(() -> {
            stageHolder[0] = new Stage();
        });

        // Espera hasta que se haya configurado el Stage
        while (stageHolder[0] == null) {
            try {
                Thread.sleep(10); // Pausa breve para evitar bloqueos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for JavaFX Stage to be created", e);
            }
        }

        return stageHolder[0];
    }

    @Bean
    public StageManager stageManager(SpringFXMLLoader springFXMLLoader, Stage primaryStage) {
        return new StageManager(springFXMLLoader, primaryStage);
    }
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }
 

    

}