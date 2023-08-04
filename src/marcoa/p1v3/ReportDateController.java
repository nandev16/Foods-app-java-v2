
package marcoa.p1v3;

import classes.TablaAlimento;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador para la ventana de generación de informe por fecha.
 * 
 * Descripción:
 * Esta clase es el controlador para la ventana de generación de informe de alimentos por fecha.
 * Permite buscar alimentos en el archivo "Alimentos.txt" según una fecha de creación seleccionada,
 * y muestra los detalles de los alimentos encontrados en una tabla en la interfaz gráfica.
 * Los detalles incluyen el código, nombre, contenido de proteínas, carbohidratos y grasas,
 * el tipo de alimento (procesado o natural) y, en el caso de alimentos procesados, la lista de ingredientes.
 * 
 * Notas adicionales:
 */
public class ReportDateController {
    // ***** COMPONENTES VISUALES *****
    
    @FXML
    private DatePicker inputDate;
    @FXML
    private TableView<TablaAlimento> tableView;
    
    @FXML
    private TableColumn<TablaAlimento, String> codigoColumn;
    @FXML
    private TableColumn<TablaAlimento, String> nombreColumn;
    @FXML
    private TableColumn<TablaAlimento, String> proteinasColumn;
    @FXML
    private TableColumn<TablaAlimento, String> carbohidratosColumn;
    @FXML
    private TableColumn<TablaAlimento, String> grasasColumn;
    @FXML
    private TableColumn<TablaAlimento, String> tipoColumn;
    @FXML
    private TableColumn<TablaAlimento, String> ingredientesColumn;
    
    // Asigna los valores a las columnas de la tabla
    public void initialize() {
        codigoColumn.setCellValueFactory(data -> data.getValue().codigoProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        proteinasColumn.setCellValueFactory(data -> data.getValue().proteinasProperty());
        carbohidratosColumn.setCellValueFactory(data -> data.getValue().carbohidratosProperty());
        grasasColumn.setCellValueFactory(data -> data.getValue().grasasProperty());
        tipoColumn.setCellValueFactory(data -> data.getValue().tipoProperty());
        ingredientesColumn.setCellValueFactory(data -> data.getValue().ingredientesProperty());
    }
    
    // Busca alimentos en el archivo "Alimentos.txt" según la fecha de creación seleccionada y muestra sus detalles en la tabla.
    @FXML
    void searchPerDate() {
        LocalDate selectedDate = inputDate.getValue();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
            String line;
            ArrayList<TablaAlimento> foodsList = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String typeOfFood = data[0];
                LocalDate creationDate = LocalDate.parse(data[6]);
                
                if (creationDate.equals(selectedDate)) {
                    String code = data[1];
                    String name = data[2];
                    String proteins = data[3];
                    String carbs = data[4];
                    String fats = data[5];
                    String type = (typeOfFood.equals("Natural")) ? data[7] : "";
                    String ingredients = (typeOfFood.equals("Procesado")) ? data[7] : "";
                    
                    System.out.println("Datos: " + code + ", " + name + ", " + proteins + ", " + carbs + ", " + fats + ", " + type + ", " + ingredients);
                    
                    foodsList.add(new TablaAlimento(code, name, proteins, carbs, fats, type, ingredients));
                }
            }
            
            reader.close();
            
            tableView.getItems().clear();
            tableView.getItems().addAll(foodsList);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
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
