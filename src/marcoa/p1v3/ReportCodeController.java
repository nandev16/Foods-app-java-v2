
package marcoa.p1v3;

import classes.Alimento;
import classes.AlimentoNatural;
import classes.AlimentoProcesado;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controlador para la ventana de generación de informe de un alimento por código.
 * 
 * Descripción:
 * Esta clase es el controlador para la ventana de generación de informe de un alimento a partir de su código.
 * Permite buscar un alimento por su código en el archivo "Alimentos.txt" y muestra los detalles correspondientes
 * del alimento en la interfaz gráfica. Los detalles incluyen el nombre, contenido de proteínas, carbohidratos y grasas,
 * la fecha de creación y el tipo de alimento (procesado o natural). Si el alimento es procesado, también muestra la lista
 * de ingredientes. La ventana de informe se actualiza dinámicamente según el alimento encontrado.
 * 
 * Notas adicionales:
 * - La ventana de informe muestra los detalles del alimento en etiquetas, ajustando la visualización según el tipo de alimento encontrado.
 * - Si el alimento es procesado, muestra una lista de ingredientes en un ListView.
 */
public class ReportCodeController {
    // ***** COMPONENTES VISUALES *****
    @FXML
    private TextField inputCode;
    @FXML
    private Label lblName;
    @FXML
    private Label lblProteins;
    @FXML
    private Label lblCarbs;
    @FXML
    private Label lblFats;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblType;
    @FXML
    private Label titleType;
    @FXML
    private Label lblRight;
    @FXML
    private ListView<String> listViewIngredients;
    
    // Busca un alimento en el archivo "Alimentos.txt" a partir del código ingresado y muestra sus detalles.
    @FXML
    void searchFood(ActionEvent event) {
        String productCode = inputCode.getText();
        String[] foodData = null;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
            String line;
            //Alimento foodFound = null;
            
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(";");
                if (datos[1].equals(productCode)) {
                    foodData = datos;
                    //foodFound = parseLineToAlimento(datos);
                    break;
                }
            }
            
            reader.close();
            
            if (foodData != null) {
                String foodType = foodData[0];
                String name = foodData[2];
                double proteins = Double.parseDouble(foodData[3]);
                double carbs = Double.parseDouble(foodData[4]);
                double fats = Double.parseDouble(foodData[5]);
                String date = foodData[6];
                
                DecimalFormat formatNum = new DecimalFormat("#.##");
                
                if (foodType.equals("Procesado")) {
                    String[] ingredients = foodData[7].split(", ");
                    ArrayList<String> ingList = new ArrayList<>();
                    for (String ingredient : ingredients) {
                        ingList.add(ingredient);
                    }
                    this.lblName.setText(name);
                    this.lblProteins.setText(formatNum.format(proteins));
                    this.lblCarbs.setText(formatNum.format(carbs));
                    this.lblFats.setText(formatNum.format(fats));
                    this.lblDate.setText(date);
                    this.titleType.setText("");
                    this.lblType.setText("");
                    this.lblRight.setText("Ingredientes:");
                    this.listViewIngredients.setVisible(true);
                    this.listViewIngredients.getItems().clear();
                    this.listViewIngredients.getItems().addAll(ingList);
                } else {
                    String type = foodData[7];
                    this.lblName.setText(name);
                    this.lblProteins.setText(formatNum.format(proteins));
                    this.lblCarbs.setText(formatNum.format(carbs));
                    this.lblFats.setText(formatNum.format(fats));
                    this.lblDate.setText(date);
                    this.titleType.setText("Tipo de alimento:");
                    this.lblType.setText(type);
                    this.lblRight.setText("");
                    this.listViewIngredients.setVisible(false);
                }
                
                System.out.println(foodData[1]);
            } else {
                newErrorAlert("No se ha encontrado el producto.", "Producto no encontrado");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Convierte los datos de un alimento (en formato de array de String) en un objeto Alimento (procesado o natural).
    private Alimento parseLineToAlimento(String[] data) {
        String foodType = data[0];
        String code = data[1];
        String name = data[2];
        double proteins = Double.parseDouble(data[3]);
        double carbs = Double.parseDouble(data[4]);
        double fats = Double.parseDouble(data[5]);
        LocalDate creationDate = LocalDate.parse(data[6]);

        if (foodType.equals("Procesado")) {
            String[] ingredients = data[7].split(", ");
            ArrayList<String> ingList = new ArrayList<>();
            for (String ingredient : ingredients) {
                ingList.add(ingredient);
            }
            return new AlimentoProcesado(code, name, proteins, carbs, fats, creationDate, ingList);
        } else {
            String tipo = data[7];
            return new AlimentoNatural(code, name, proteins, carbs, fats, creationDate, tipo);
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
    
    // Muestra una alerta de error con el texto proporcionado.
    void newErrorAlert(String txt, String title) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ha ocurrido un error.");
        alert.setHeaderText(title);
        alert.setContentText(txt);
        alert.showAndWait();
    }
}
