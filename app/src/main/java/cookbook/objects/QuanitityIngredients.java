package cookbook.objects;

/**
 * The QuanitityIngredients class represents the quantity of an ingredient, including its unit, amount, and name.
 */

public class QuanitityIngredients {
  private String unit;
  private float amount;
  private ingredientObject ingredient;
  private String name;

  /**
   * Constructs a new QuanitityIngredients object with the specified unit, amount, and ingredient.
   *
   * @param unit       the unit of measurement for the ingredient (e.g., "g", "ml")
   * @param amount     the amount of the ingredient
   * @param ingredient the ingredient object
   */
  
  public QuanitityIngredients(String unit, float amount, ingredientObject ingredient) {
    this.unit= unit;
    if(unit == null) {
      this.unit = "";
    }
    if(unit == "unit") {
      this.unit = "";
    }
    this.amount = amount;
    this.ingredient = ingredient;
    this.name = ingredient.getName();
  }

  public QuanitityIngredients(String value, Float amount, String value1) {
  }

  public void QuanitityIngredients(String unit, float amount, String name) {
    this.unit= unit;
    if(unit == null) {
      this.unit = "";
    }
    this.amount = amount;
    this.name = name;
  }
  
  public ingredientObject getIngredient() {
    return ingredient;
  }
  
  public String ingredientID() {
    return ingredient.getId();
  }

  public String getUnit() {
    return unit;
  }
  
  public String getName() {
    return name;
  }
  
  public float getAmount() {
    return amount;
  }
  public void addAmount(float additional) {
    amount += additional;
  }
  
  public void setAmount(float amount) {
    this.amount = amount;
  }
  
  
  @Override
  public String toString() {
    return amount + " " + unit.toString() + " " + name;
  }
  
  public String toData() {
    return "INGREDIENT:" + this.amount + ":" + String.valueOf(this.unit) + ":" + this.name;
  }
  
  
  
  
  
  
  
  
  
  
  
  
}
