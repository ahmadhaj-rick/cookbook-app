package cookbook.objects;

public class ingredientObject {
    private String ingredient_id;
    private String name_ingredient;


public ingredientObject(String ingredient_id, String name_ingredient){
    setIngredient_id(ingredient_id);
    setName_ingredient(name_ingredient);
}

public String getIngredient_id() {
    return ingredient_id;
}


public void setIngredient_id(String ingredient_id) {
    this.ingredient_id = ingredient_id;
}

public String getName_ingredient() {
    return name_ingredient;
}

public void setName_ingredient(String name_ingredient) {
    this.name_ingredient = name_ingredient;
}


    
}
