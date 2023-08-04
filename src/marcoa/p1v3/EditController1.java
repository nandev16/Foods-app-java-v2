
package marcoa.p1v3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controlador de la ventana "Edit" para editar y eliminar alimentos existentes.
 * 
 * Descripción:
 * Esta clase es el controlador de la ventana "Edit" en la que los usuarios pueden editar y eliminar
 * alimentos existentes de la aplicación. Permite seleccionar un alimento de la lista y realizar acciones
 * como eliminarlo o editar sus detalles.
 * 
 * Notas adicionales:
 * - Los alimentos se muestran en un ListView con el formato "Codigo: [código] | Nombre: [nombre]" para facilitar su identificación.
 * - Para editar un alimento, se abre una ventana modal que muestra los detalles actuales del alimento y permite su modificación.
 * - La clase ModalEditController se utiliza para la ventana modal de edición.
 * - La aplicación guarda las líneas eliminadas del archivo original en "AlimentosEliminados.txt" como respaldo de los datos eliminados.
 */
public class EditController1 {
    ArrayList<String> listFoodsToView = new ArrayList<>(); // Lista que almacena los datos de los alimentos para mostrar en el ListView.
    String selectedCode = ""; // Variable que almacena el código del alimento seleccionado en la lista.
    
    // ***** COMPONENTES VISUALES *****
    @FXML
    private Button buttonEdit;
    @FXML
    private ListView<String> foodsList;
    
    // ***** MÉTODOS PRINCIPALES *****
    
    // Inicializa la ventana, carga los alimentos disponibles y los muestra en el ListView.
    @FXML
    void initialize() {
        foodsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 

        try {
            File archivo = new File("Alimentos.txt");
            Scanner scanner = new Scanner(archivo);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // Dividir la linea y agregarla a un array
                String[] fields = line.split(";");
                
                // Acceder a los campos segun el indice en que se guardaron
                String code = fields[1];
                String name = fields[2];
                
                String text = "Codigo: " + code + " | Nombre: " + name;
                listFoodsToView.add(text);
            }
            
            foodsList.getItems().addAll(listFoodsToView);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            newErrorAlert("No se han encontrado datos...", null);
        }
    }
    
    // Maneja la selección de un alimento en la lista y almacena su código para su posterior uso.
    @FXML
    void handleSelection() {
        // Obtener el elemento seleccionado del ListView
        String selectedValue = foodsList.getSelectionModel().getSelectedItem();
        
        if (selectedValue != null) {
            String codigo = obtenerCodigo(selectedValue);
            this.selectedCode = codigo;
        }
    }
    
    // Elimina el alimento seleccionado de la lista y lo guarda en "AlimentosEliminados.txt".
    @FXML
    void deleteFood(ActionEvent event) {
        if (this.selectedCode == null || this.selectedCode == "") {
            newErrorAlert("No hay ningun elemento seleccionado", null);
        } else {
            try {
                // Leer el contenido del archivo original
                BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
                StringBuilder contenido = new StringBuilder();
                String linea;
                
                while ((linea = reader.readLine()) != null) {
                    // Si la línea contiene el código del producto a eliminar, guárdala en una variable
                    if (linea.contains(this.selectedCode)) {
                        guardarLineaEliminada(linea, "AlimentosEliminados.txt");
                    } else {
                        contenido.append(linea).append(System.lineSeparator());
                    }
                }
                
                reader.close();
                
                // Escribir el contenido modificado en el archivo original
                BufferedWriter writer = new BufferedWriter(new FileWriter("Alimentos.txt"));
                writer.write(contenido.toString());
                writer.close();

                System.out.println("Línea eliminada y guardada en AlimentosEliminados.txt correctamente.");
                
                try {
                    showEdit();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Guarda una línea eliminada del archivo original en otro archivo.
    private static void guardarLineaEliminada(String linea, String archivoDestino) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino, true));
            writer.write(linea + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Abre una ventana modal para editar los detalles del alimento seleccionado.
    @FXML
    void editFood(ActionEvent event) {
        try {
            if (selectedCode.equals("")) {
                newErrorAlert("No ha seleccionado ningun alimento...", null);
            } else {
                // Cargar el archivo ".fxml" que contiene la informacion de la interfaz del modal.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modalEdit.fxml"));
                AnchorPane modalRoot = loader.load();

                ModalEditController modalController = loader.getController(); // Obtener el controlador para pasarle los datos del codigo y cargar los datos.

                Stage modalStage = new Stage();
                modalStage.initOwner(buttonEdit.getScene().getWindow());
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.initStyle(StageStyle.TRANSPARENT);
                modalStage.setScene(new Scene(modalRoot));
                modalStage.setTitle("Editar producto");

                modalController.setModalStage(modalStage);
                modalController.setFoodCode(selectedCode);
                modalController.loadData();

                modalStage.showAndWait(); 
                
                Platform.runLater(() -> {
                    try {
                        showEdit();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Extrae el código de un alimento a partir de los datos mostrados en el ListView.
    public static String obtenerCodigo(String datos) {
        // Definir la expresión regular para buscar el código
        String regex = "Codigo: (\\w+)";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher para buscar el patrón en la cadena de datos
        Matcher matcher = pattern.matcher(datos);

        // Verificar si se encuentra el patrón en la cadena
        if (matcher.find()) {
            // Obtener el grupo de captura del patrón (en este caso, el código)
            return matcher.group(1);
        }

        // Si no se encuentra el código, devuelve una cadena vacía o null, según convenga
        return "";
    }
    
    // Muestra una alerta de información con el texto proporcionado.
    void newAlert(String txt, String title) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Alerta!");
        alert.setHeaderText(title);
        alert.setContentText(txt);
        alert.showAndWait();
    }
    // Muestra una alerta de error con el texto proporcionado.
    void newErrorAlert(String txt, String title) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ha ocurrido un error.");
        alert.setHeaderText(title);
        alert.setContentText(txt);
        alert.showAndWait();
    }
    
    // OPCIONES DEL MENU LATERAL
     @FXML
    private void showAdd() throws IOException {
        MarcoAP1v3.setRoot("hellofx");
    }
    @FXML
    private void showEdit() throws IOException {
        MarcoAP1v3.setRoot("edit");
    }
    @FXML
    private void showDelete() throws IOException {
        MarcoAP1v3.setRoot("delete");
    }
    @FXML
    private void showReport() throws IOException {
        MarcoAP1v3.setRoot("report1");
    }
    @FXML
    private void showSecReport() throws IOException {
        MarcoAP1v3.setRoot("report2");
    }
}
