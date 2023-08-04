
package marcoa.p1v3;

import classes.AlimentoNatural;
import classes.AlimentoProcesado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controlador para la ventana modal de edición de alimentos existentes.
 *
 * Descripción:
 * Esta clase es el controlador para la ventana modal de edición de alimentos existentes. Permite modificar
 * los detalles de un alimento seleccionado, tanto para alimentos procesados como naturales. La ventana
 * modal muestra campos para ingresar o editar el nombre, contenido de proteínas, carbohidratos y grasas,
 * así como la fecha de creación y los ingredientes (solo para alimentos procesados). Al guardar los cambios,
 * actualiza los datos del alimento en el archivo "Alimentos.txt". 
 * 
 * Notas adicionales:
 * - La ventana modal de edición permite modificar los detalles de un alimento existente y guardar los cambios.
 * - Los campos específicos para cada tipo de alimento se muestran u ocultan según la selección del usuario.
 * - Los alimentos procesados muestran una lista de ingredientes que se pueden agregar o eliminar según sea necesario.
 */
public class ModalEditController implements Initializable {
    // **** ATRIBUTOS *****
    private String foodCode = "";
    private String lineToEdit; // Línea del archivo "Alimentos.txt" que corresponde al alimento seleccionado.
    private ArrayList<String> ingredientsList = new ArrayList<>(); // Lista que almacena los ingredientes del alimento procesado.
    private String selectedIngredient = ""; 
    private Stage modalStage; // Escenario de la ventana modal para permitir su cierre.
    private String foodType;
    private AlimentoProcesado alimentoProcesado; // Objeto que almacena los datos del alimento procesado en edición.
    private AlimentoNatural alimentoNatural; // Objeto que almacena los datos del alimento natural en edición.
    
    // ***** COMPONENTES VISUALES *****
    @FXML
    private TextField inputCode;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputProteins;
    @FXML
    private TextField inputCarbs;
    @FXML
    private TextField inputFats;
    
    @FXML
    private DatePicker inputDate;
    
    @FXML
    private TextField inputFoodType;
    @FXML
    private TextField inputIngredientsFood;
    
    @FXML
    private ListView<String> listIngredientsFood;
    
    @FXML
    private StackPane paneNatural;
    @FXML
    private StackPane paneProcessed;
    
    // ***** METODOS PRINCIPALES *****
    
    // Inicializa la ventana modal y configura la selección de la lista de ingredientes.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listIngredientsFood.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 
    }    
    
    // Carga los datos del alimento seleccionado en los campos de la ventana modal para su edición.
    public void loadData() {
        if (foodCode != "") {
            inputCode.setText(foodCode);
            
            try {
                File archivo = new File("Alimentos.txt");
                Scanner scanner = new Scanner(archivo);
            
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    // Dividir la linea y agregarla a un array
                    String[] fields = line.split(";");

                    // Acceder a los campos segun el indice en que se guardaron
                    String type = fields[0];
                    String code = fields[1];
                    
                    if (code.equals(foodCode)) {
                        this.lineToEdit = line;
                        if ("Procesado".equals(type)) {
                            this.foodType = type;
                            
                            paneNatural.setVisible(false);
                            paneProcessed.setVisible(true);
                            
                            LocalDate formatDate = LocalDate.parse(fields[6]);
                            DecimalFormat formatNum = new DecimalFormat("#.##");
                            String proteins = formatNum.format(Double.parseDouble(fields[3]));
                            String Carbs = formatNum.format(Double.parseDouble(fields[4]));
                            String fats = formatNum.format(Double.parseDouble(fields[5]));
                            
                            String[] elementsList = fields[7].split(",");
                            
                            for (String elemento : elementsList) {
                                String txt = elemento.trim();
                                ingredientsList.add(txt);
                            }
                            
                            alimentoProcesado = new AlimentoProcesado(code, fields[2], Double.parseDouble(proteins), Double.parseDouble(Carbs), Double.parseDouble(fats), formatDate, ingredientsList);
                            
                            inputName.setText(alimentoProcesado.getName());
                            inputProteins.setText(Double.toString(alimentoProcesado.getProteins()));
                            inputCarbs.setText(Double.toString(alimentoProcesado.getCarbs()));
                            inputFats.setText(Double.toString(alimentoProcesado.getFats()));
                            inputDate.setValue(alimentoProcesado.getCreationDate());
                            
                            listIngredientsFood.getItems().addAll(ingredientsList);
                        } else {
                            this.foodType = type;
                            
                            paneNatural.setVisible(true);
                            paneProcessed.setVisible(false);
                            
                            LocalDate formatDate = LocalDate.parse(fields[6]);
                            DecimalFormat formatNum = new DecimalFormat("#.##");
                            String proteins = formatNum.format(Double.parseDouble(fields[3]));
                            String Carbs = formatNum.format(Double.parseDouble(fields[4]));
                            String fats = formatNum.format(Double.parseDouble(fields[5]));
                            
                            alimentoNatural = new AlimentoNatural(code, fields[2], Double.parseDouble(proteins), Double.parseDouble(Carbs), Double.parseDouble(fats), formatDate, fields[7]);
                            
                            inputName.setText(alimentoNatural.getName());
                            inputProteins.setText(Double.toString(alimentoNatural.getProteins()));
                            inputCarbs.setText(Double.toString(alimentoNatural.getCarbs()));
                            inputFats.setText(Double.toString(alimentoNatural.getFats()));
                            
                            inputDate.setValue(alimentoNatural.getCreationDate());
                            
                            inputFoodType.setText(alimentoNatural.getFoodType());
                        }
                    }
                }
            
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            inputCode.setText("null");
        }
    }
    
    // Maneja la selección de un ingrediente en la lista y almacena el ingrediente seleccionado.
    @FXML
    void handleSelection() {
        // Obtener el elemento seleccionado del ListView
        String selectedValue = listIngredientsFood.getSelectionModel().getSelectedItem();
        this.selectedIngredient = selectedValue;
        System.out.println("Elemento: "+selectedValue);
    }
    
    // Agrega un ingrediente a la lista del alimento procesado en edición.
    @FXML
    void handleAddIngredient(ActionEvent event) {
        String ingredient = inputIngredientsFood.getText();
        try {
            if (!ingredient.isEmpty()) {
                // Agregar el ingrediente a la lista
                ingredientsList.add(ingredient);

                // Limpiar el TextField
                inputIngredientsFood.clear();

                // Actualizar el ListView
                listIngredientsFood.getItems().clear();
                listIngredientsFood.getItems().addAll(ingredientsList);
            } else {
                newErrorAlert("No se ha podido agregar el elemento", null);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Cierra la ventana modal sin guardar los cambios realizados.
    @FXML
    void handleCancel(ActionEvent event) {
        this.modalStage.close();
    }
    
    //  Elimina un ingrediente de la lista del alimento procesado en edición.
    @FXML
    void handleDeleteIngredient(ActionEvent event) {
        ArrayList<String> myList = this.ingredientsList;
        String selection = this.selectedIngredient;
        
        if (selection != "") {
            myList.remove(selection);
            
            listIngredientsFood.getItems().clear();
            listIngredientsFood.getItems().addAll(ingredientsList);
        } else {
            newErrorAlert("No ha seleccionado ningun item.", null);
        }
    }
    
    // Guarda los cambios realizados en los detalles del alimento en el archivo "Alimentos.txt".
    @FXML
    void handleSaveFood(ActionEvent event) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
            StringBuilder content = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            
            reader.close();
            
            String contentString = content.toString();
            int indexOfLineToEdit = contentString.indexOf(this.lineToEdit);
            int startOfLine = contentString.lastIndexOf(System.lineSeparator(), indexOfLineToEdit) + 1;
            int endLine = contentString.indexOf(System.lineSeparator(), indexOfLineToEdit);
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("Alimentos.txt"));
            if (this.foodType.equals("Procesado")) {
                alimentoProcesado.setName(this.inputName.getText());
                alimentoProcesado.setProteins(Double.parseDouble(this.inputProteins.getText()));
                alimentoProcesado.setCarbs(Double.parseDouble(this.inputCarbs.getText()));
                alimentoProcesado.setFats(Double.parseDouble(this.inputFats.getText()));
                alimentoProcesado.setCreationDate(this.inputDate.getValue());
                
                alimentoProcesado.setIngredientsList(ingredientsList);
                
                String ingredients = String.join(", ", alimentoProcesado.getIngredientsList());
                
                String dataProcessedFood = this.foodType + ";" + alimentoProcesado.getCode() + ";" + alimentoProcesado.getName() + ";" + alimentoProcesado.getProteins() + ";" + alimentoProcesado.getCarbs() + ";" + alimentoProcesado.getFats() + ";" + alimentoProcesado.getCreationDate() + ";" + ingredients;
                
                content.replace(startOfLine, endLine, dataProcessedFood);
                
                writer.write(content.toString());
                writer.close();
                
                newAlert("Se ha actualizado el alimento", null);
                this.modalStage.close();
            } else {
                alimentoNatural.setName(this.inputName.getText());
                alimentoNatural.setProteins(Double.parseDouble(this.inputProteins.getText()));
                alimentoNatural.setCarbs(Double.parseDouble(this.inputCarbs.getText()));
                alimentoNatural.setFats(Double.parseDouble(this.inputFats.getText()));
                alimentoNatural.setCreationDate(this.inputDate.getValue());
                
                alimentoNatural.setFoodType(this.inputFoodType.getText());
                
                String dataProcessedFood = this.foodType + ";" + alimentoNatural.getCode() + ";" + alimentoNatural.getName() + ";" + alimentoNatural.getProteins() + ";" + alimentoNatural.getCarbs() + ";" + alimentoNatural.getFats() + ";" + alimentoNatural.getCreationDate() + ";" + alimentoNatural.getFoodType();
                
                content.replace(startOfLine, endLine, dataProcessedFood);
                
                writer.write(content.toString());
                writer.close();
                
                newAlert("Se ha actualizado el alimento", null);
                this.modalStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Establece el código del alimento seleccionado para edición.
    public void setFoodCode(String code) {
        this.foodCode = code;
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
    
    // Establece el escenario de la ventana modal para permitir su cierre desde este controlador.
    public void setModalStage(Stage stage) {
        this.modalStage = stage;
    }
}
