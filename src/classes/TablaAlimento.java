
package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase TablaAlimento.
 * 
 * Descripción:
 * Esta clase representa un objeto "TablaAlimento" que se utiliza para mostrar información de alimentos en una tabla, como en una interfaz gráfica (GUI).
 * Cada objeto "TablaAlimento" contiene propiedades de tipo StringProperty que se utilizan para enlazar con una tabla o componente gráfico que muestra los datos.
 * Los atributos incluyen el código, nombre, contenido de proteínas, carbohidratos, grasas, tipo y lista de ingredientes del alimento.
 */
public class TablaAlimento {
    private final StringProperty codigo;
    private final StringProperty nombre;
    private final StringProperty proteinas;
    private final StringProperty carbohidratos;
    private final StringProperty grasas;
    private final StringProperty tipo;
    private final StringProperty ingredientes;

    // Constructor que recibe los datos del alimento y los inicializa mediante SimpleStringProperty.
    public TablaAlimento(String codigo, String nombre, String proteinas, String carbohidratos, String grasas, String tipo, String ingredientes) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre = new SimpleStringProperty(nombre);
        this.proteinas = new SimpleStringProperty(proteinas);
        this.carbohidratos = new SimpleStringProperty(carbohidratos);
        this.grasas = new SimpleStringProperty(grasas);
        this.tipo = new SimpleStringProperty(tipo);
        this.ingredientes = new SimpleStringProperty(ingredientes);
    }
    
    // Métodos getter para acceder a las propiedades
    public StringProperty codigoProperty() {
        return codigo;
    }
    public StringProperty nombreProperty() {
        return nombre;
    }
    public StringProperty proteinasProperty() {
        return proteinas;
    }
    public StringProperty carbohidratosProperty() {
        return carbohidratos;
    }
    public StringProperty grasasProperty() {
        return grasas;
    }
    public StringProperty tipoProperty() {
        return tipo;
    }
    public StringProperty ingredientesProperty() {
        return ingredientes;
    }
    
    // Métodos getter para los atributos
    public String getCodigo() {
        return codigo.get();
    }
    public String getNombre() {
        return nombre.get();
    }
    public String getProteinas() {
        return proteinas.get();
    }
    public String getCarbohidratos() {
        return carbohidratos.get();
    }
    public String getGrasas() {
        return grasas.get();
    }
    public String getTipo() {
        return tipo.get();
    }
    public String getIngredientes() {
        return ingredientes.get();
    }
}
