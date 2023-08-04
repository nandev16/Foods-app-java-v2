
package classes;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase AlimentoProcesado.
 * 
 * Descripción:
 * Esta clase hereda de la clase "Alimento" y representa un objeto "AlimentoProcesado".
 * Cada objeto "AlimentoProcesado" contiene información sobre un alimento procesado específico,
 * incluyendo su código, nombre, contenido de proteínas, carbohidratos, grasas, fecha de creación y lista de ingredientes.
 */
public class AlimentoProcesado extends Alimento {
    // Lista de ingredientes del alimento procesado.
    private ArrayList<String> ingredientsList;

    public AlimentoProcesado(String code, String name, double proteins, double carbs, double fats, LocalDate creationDate, ArrayList<String> ingredientsList) {
        super(code, name, proteins, carbs, fats, creationDate);
        this.ingredientsList = ingredientsList;
    }

    // Retorna la lista de ingredientes del alimento procesado.
    public ArrayList<String> getIngredientsList() {
        return ingredientsList;
    }

    // Establece la lista de ingredientes del alimento procesado.
    public void setIngredientsList(ArrayList<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
