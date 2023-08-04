/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marcoa.p1v3;

import classes.AlimentoNatural;
import classes.AlimentoProcesado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;


public class Controller {
    
     // ***** CONSTANTES *****
    private static final String TIPO_PROCESADO = "Procesado";
    private static final String TIPO_NATURAL = "Natural";
    
    // ***** ATRIBUTOS *****
    private ArrayList<String> ingredientsList = new ArrayList<>(); // Lista que almacena los ingredientes ingresados para un alimento procesado.
    private String selectedIngredient = ""; // Variable que almacena el ingrediente seleccionado en la lista de ingredientes.

    // ***** COMPONENTES VISUALES *****
    @FXML
    private TextField input_code;
    @FXML
    private TextField input_name;
    @FXML
    private TextField input_proteins;
    @FXML
    private TextField input_carbs;
    @FXML
    private TextField input_fats;
    @FXML
    private DatePicker input_date;
    @FXML
    private TextField inputIngredient;
    @FXML
    private TextField inputFoodType;
    
    @FXML
    private ListView<String> listViewIngredients;
    
    @FXML
    private StackPane paneNatural;
    @FXML
    private StackPane paneProcessed;
    
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
    private void showReport() throws IOException {
        MarcoAP1v3.setRoot("report1");
    }
    @FXML
    private void showSecReport() throws IOException {
        MarcoAP1v3.setRoot("report2");
    }
    
    // ***** MÉTODOS PRINCIPALES *****
    
    // Inicializa la configuración del ListView para permitir selección única de ingredientes.
    @FXML
    void initialize() {
        listViewIngredients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    // Maneja la selección de ingredientes en la lista y almacena el ingrediente seleccionado.
    @FXML
    void handleSelection() {
        // Obtener el elemento seleccionado del ListView
        String selectedValue = listViewIngredients.getSelectionModel().getSelectedItem();
        this.selectedIngredient = selectedValue;
        System.out.println("Elemento: "+selectedValue);
    }
    
    // Maneja el evento de guardar un nuevo ingrediente en la lista.
    @FXML
    void saveIngredient(ActionEvent event) {
        String ingredient = inputIngredient.getText();
        try {
            if (!ingredient.isEmpty()) {
                // Agregar el ingrediente a la lista
                ingredientsList.add(ingredient);

                // Limpiar el TextField
                inputIngredient.clear();

                // Actualizar el ListView
                listViewIngredients.getItems().clear();
                listViewIngredients.getItems().addAll(ingredientsList);
            } else {
                newErrorAlert("No ha agregado ningun ingrediente...", null);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Cambia entre la vista para alimentos procesados y naturales.
    @FXML
    void changeNP(ActionEvent event) {
        if (paneProcessed.isVisible()) {
            paneNatural.setVisible(true);
            paneProcessed.setVisible(false);
        } else {
            paneNatural.setVisible(false);
            paneProcessed.setVisible(true);
        }
    }
    
    // Guarda un alimento procesado en el archivo "Alimentos.txt".
    public void saveProcessedFood(AlimentoProcesado alimento) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
              BufferedWriter writer = new BufferedWriter(new FileWriter("Alimentos.txt", true))) {
            
            String code = alimento.getCode();
            String line;
            boolean codeExist = false;
            
            // Verificar si el docigo del alimento existe
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length > 1 && data[1].equals(code)) {
                    codeExist = true;
                    break;
                }
            }
            
            if (!codeExist) {
                // Convertir la lista de ingredientes en una cadena separada por comas
                ArrayList<String> list = alimento.getIngredientsList();
                if (list.size() > 1) {
                    String ingredients = String.join(", ", alimento.getIngredientsList());

                    // Crear una cadena con los datos del alimento procesado en el formato deseado
                    String dataProcessedFood = TIPO_PROCESADO + ";" + alimento.getCode() + ";" + alimento.getName() + ";" + alimento.getProteins() + ";" + alimento.getCarbs() + ";" + alimento.getFats() + ";" + alimento.getCreationDate() + ";" + ingredients;

                    // Escribir los datos del alimento procesado en el archivo
                    writer.write(dataProcessedFood);
                    writer.newLine();
                    newAlert("Alimento guardado", null);
                } else {
                    newErrorAlert("Se necesitan almenos 2 ingredientes", null);
                }
            } else {
                newErrorAlert("El codigo de producto ya existe", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Guarda un alimento natural en el archivo "Alimentos.txt".
    public void saveNaturalFood(AlimentoNatural alimento) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Alimentos.txt"));
              BufferedWriter writer = new BufferedWriter(new FileWriter("Alimentos.txt", true))) {
            
            String code = alimento.getCode();
            String line;
            boolean codeExist = false;
            
            // Verificar si el docigo del alimento existe
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length > 1 && data[1].equals(code)) {
                    codeExist = true;
                    break;
                }
            }
            
            if (!codeExist) {
                // Crear una cadena con los datos del alimento procesado en el formato deseado
                String dataNaturalFood = TIPO_NATURAL + ";" + alimento.getCode() + ";" + alimento.getName() + ";" + alimento.getProteins() + ";" + alimento.getCarbs() + ";" + alimento.getFats() + ";" + alimento.getCreationDate() + ";" + alimento.getFoodType();

                // Escribir los datos del alimento procesado en el archivo
                writer.write(dataNaturalFood);
                writer.newLine();
                newAlert("Alimento guardado", null);
            } else {
                newErrorAlert("El codigo de producto ya existe", null);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //  Maneja el evento de eliminar un ingrediente de la lista.
    @FXML
    void handleDeleteIngredient(ActionEvent event) {
        ArrayList<String> myList = this.ingredientsList;
        String selection = this.selectedIngredient;
        
        if (selection != "") {
            myList.remove(selection);
            
            listViewIngredients.getItems().clear();
            listViewIngredients.getItems().addAll(ingredientsList);
        } else {
            newErrorAlert("No ha seleccionado ningun item.", null);
        }
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
    
    // Verifica si una cadena puede ser convertida a un valor flotante.
    float isInputFloat(String var) {
        try {
            float value = Float.parseFloat(var);
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Maneja el evento de guardar un nuevo alimento en la aplicación.
     @FXML
    void saveFood(ActionEvent event) {
        try {
            if (
                    input_code.getText().isEmpty() || 
                    input_name.getText().isEmpty() ||
                    input_proteins.getText().isEmpty() ||
                    input_carbs.getText().isEmpty() ||
                    input_fats.getText().isEmpty() ||
                    input_date.getValue() == null
            ) {
                newErrorAlert("LLene los todos los campos para poder guardar el alimento.", null);
            } else {
                String code = input_code.getText();
                String name = input_name.getText();
                String proteins = input_proteins.getText();
                String carbs = input_carbs.getText();
                String fats = input_fats.getText();
                LocalDate date = input_date.getValue();
                
                float numProteins = isInputFloat(proteins);
                float numCarbs = isInputFloat(carbs);
                float numfats = isInputFloat(fats);
                
                if (numProteins == -1 || numCarbs == -1 || numfats == -1) {
                    newErrorAlert("Las proteínas, carbohidratos y grasas deben ser datos numéricos.", null);
                    return;
                } 
                
                if(numProteins != -1 && numCarbs != -1 && numfats != -1) {
                    if (paneProcessed.isVisible()) {
                        ArrayList<String> list = ingredientsList;
                        AlimentoProcesado alimento = new AlimentoProcesado(code, name, numProteins, numCarbs, numfats, date, list);
                        saveProcessedFood(alimento);
                    } else {
                        if (!inputFoodType.getText().isEmpty()) {
                            String type = inputFoodType.getText();
                            AlimentoNatural alimento = new AlimentoNatural(code, name, numProteins, numCarbs, numfats, date, type);
                            saveNaturalFood(alimento);
                        } else {
                            newErrorAlert("Agregue un tipo de producto para el producto natural", null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}