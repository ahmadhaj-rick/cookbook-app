package cookbook.objects;

/**
 * The ingredientObject class represents an ingredient with its ID and name.
 */

public class ingredientObject {
    private String id;
    private String name;

/**
  * Constructs a new ingredientObject with the specified ID and name.
  *
  * @param id   the ID of the ingredient
  * @param name the name of the ingredient
  */
    
    
    public ingredientObject(String id, String name){
        setId(id);
        setName(name);
    }

    /**
     * Gets the ID of the ingredient.
     *
     * @return the ID of the ingredient
     */
    
    public String getId() {
        return id;
    }
    
    /**
     * Sets the ID of the ingredient.
     *
     * @param id the ID of the ingredient
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the ingredient.
     *
     * @return the name of the ingredient
     */
    
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ingredient.
     *
     * @param name the name of the ingredient
     */

    
    public void setName(String name) {
        this.name = name;
    }
}
