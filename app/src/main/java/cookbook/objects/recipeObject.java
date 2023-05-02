package cookbook.objects;

import java.util.List;

public class recipeObject {
  private String id;
  private String name;
  private String description;
  private int category;
  private String instructions;
  private String ingredient_id;
  //private List<String> tag;
  private Boolean star;
  
  
  public recipeObject(String id, String name, String shortDesc, int category, String instructions, String ingredient_id, Boolean Star) {
    setId(id);
    setName(name);
    setDescription(description);
    setCategory(category);
    setInstructions(instructions);
    setIngredient_id(ingredient_id);
    setStar(star);
    
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String shortDesc) {
    this.description = description;
  }
  
  public int getCategory() {
    return category;
  }
  
  public void setCategory(int category) {
    this.category = category;
  }
  
  public String getInstructions() {
    return instructions;
  }
  
  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }
  
  public String getIngredient_id() {
    return ingredient_id;
  }
  
  public void setIngredient_id(String ingredient_id) {
    this.ingredient_id = ingredient_id;
  }

  public void setStar(Boolean star) {
    if (star == false) {
      this.star = false;
    } else {
     this.star = true; 
    }
  }

}
