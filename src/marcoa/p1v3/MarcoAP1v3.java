/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package marcoa.p1v3;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Proyecto1: Aplicación JavaFX para gestionar alimentos y sus propiedades nutricionales.
 * 
 * Esta aplicación permite a los usuarios agregar, editar y eliminar alimentos, y generar reportes
 * basados en las propiedades nutricionales de los alimentos almacenados. Los datos de los alimentos
 * se almacenan en un archivo de texto llamado "Alimentos.txt".
 * 
 * Características:
 * - La aplicación presenta una interfaz gráfica de usuario (GUI) para una fácil interacción.
 * - Los alimentos pueden ser de tipo "Procesado" o "Natural", y tienen atributos como código, nombre,
 *   proteínas, carbohidratos, grasas y fecha de creación.
 * - Los alimentos procesados también pueden tener una lista de ingredientes.
 * - Se incluyen opciones para buscar alimentos por código o fecha de creación.
 * - La aplicación controla las excepciones para evitar errores y comportamientos inesperados.
 * 
 * Notas adicionales:
 * - El código utiliza JavaFX para la creación de la interfaz gráfica y permite la interacción con el usuario.
 *   Documentacion consultada para JavaFX: https://jenkov.com/tutorials/javafx/overview.html
 * 
 * Autor: Marco Aguero Barboza
 * Universidad: UNED
 * Curso: Programación intermedia
 * Fecha: 28/07/2023
 */
public class MarcoAP1v3 extends Application {
    
    private static Scene scene;
    
     @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            // Cargar el archivo .fxml
            Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
            scene = new Scene(root, 800, 500);
            primaryStage.setTitle("Ejemplo JavaFX con FXML");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MarcoAP1v3.class.getResource(fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
