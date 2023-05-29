package cookbook.objects;

import java.util.ArrayList;
import java.util.List;

public class recipeObject {
  private String id;
  private String name;
  private String description;
  private String instructions;
  private ArrayList<QuanitityIngredients> QuantityIngredientList = new ArrayList<>();
  private ArrayList<CommentObject> CommentList = new ArrayList<>();



  private ArrayList<tagObject> tagList = new ArrayList<>();
  private Boolean star;
  
  /**
   * recipe constructor
   */
  public recipeObject(String id, String name, String description, String instructions, Boolean Star) {
    setId(id);
    setName(name);
    setDescription(description);
    setInstructions(instructions);
    setStar(star);
    
  }

  /**
   * returns the id.
   * @return
   */
  public String getId() {
    return id;
  }
  
  /**
   * sets the id.
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * gets the name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * sets name.
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  
  public String getInstructions() {
    return instructions;
  }
  
  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public void setStar(Boolean star) {
    this.star = star;
  }

  public boolean getStar() {
    return star;
  }
  
  /**
   * returns list of comments.
   * @return
   */
  public ArrayList<CommentObject> getComments() {
    return new ArrayList<>(CommentList);
  }

  /**
   * adds coment to recipe.
   */
  public void addComment(CommentObject comment) {
    CommentList.add(comment);
  }

/**
 * adds ingredient to the list of ingredients.
 * @return
 */
  public ArrayList<QuanitityIngredients> getIngredientsList() {
    return new ArrayList<>(QuantityIngredientList);
  }

  public void addIngredient(QuanitityIngredients ingredient) {
    QuantityIngredientList.add(ingredient);
  }

  /**
   * gets a list of tags.
   */
  public ArrayList<tagObject> getTagList() {
    return new ArrayList<>(tagList);
  }

  /**
   * adds tag to the list of tags for the recipe.
   */
  public void addTag(tagObject tag) {
    tagList.add(tag);
  }

}
