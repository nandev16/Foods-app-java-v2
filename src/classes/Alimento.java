
package classes;

import java.time.LocalDate;

/**
 * Esta clase representa un objeto "Alimento" con sus atributos y métodos asociados.
 * Cada objeto "Alimento" contiene información sobre un alimento específico, incluyendo su código,
 * nombre, contenido de proteínas, carbohidratos, grasas y fecha de creación.
 */
public class Alimento {
    private String code; // Código del alimento.
    private String name; // Nombre del alimento.
    private double proteins; // Contenido de proteínas del alimento.
    private double carbs; // Contenido de carbohidratos del alimento.
    private double fats; // Contenido de grasas del alimento.
    private LocalDate creationDate; // Fecha de creación del alimento.
    
    
    // Constructor que recibe los atributos del alimento y los inicializa.
    public Alimento(String code, String name, double proteins, double carbs, double fats, LocalDate creationDate) {
        this.code = code;
        this.name = name;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.creationDate = creationDate;
    }
    
    // ***** Metodos de la clase *****
    public String getCode() {
        return code;
    }
    
    public void setCodigo(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
         this.name = name;
    }
    
    public double getProteins() {
        return proteins;
    }
    
    public void setProteins(double proteins) {
         this.proteins = proteins;
    }
   
    public double getCarbs() {
        return carbs;
    }
    
    public void setCarbs(double carbs) {
         this.carbs = carbs;
    }
   
    public double getFats() {
        return fats;
    }
    
    public void setFats(double fats) {
         this.fats = fats;
    }
   
    public LocalDate getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(LocalDate creationDate) {
         this.creationDate = creationDate;
    }
}
