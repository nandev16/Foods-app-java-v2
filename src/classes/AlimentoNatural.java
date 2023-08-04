
package classes;

import java.time.LocalDate;

/**
 * Clase AlimentoNatural.
 * 
 * Descripción:
 * Esta clase hereda de la clase "Alimento" y representa un objeto "AlimentoNatural".
 * Cada objeto "AlimentoNatural" contiene información sobre un alimento natural específico,
 * incluyendo su código, nombre, proteínas, carbohidratos, grasas, fecha de creación y tipo de alimento.
 */
public class AlimentoNatural extends Alimento {
    private String foodType;
    
    // Constructor que recibe los atributos del alimento natural y los inicializa mediante el constructor de la clase "Alimento"
    public AlimentoNatural(String code, String name, double proteins, double carbs, double fats, LocalDate creationDate, String foodType) {
        super(code, name, proteins, carbs, fats, creationDate);
        this.foodType = foodType;
    }
    
    // Getter para obtener el tipo de alimento
    public String getFoodType() {
        return foodType;
    }
    
    public void setFoodType(String type) {
        this.foodType = type;
    }
}
