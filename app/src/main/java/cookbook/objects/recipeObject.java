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
  
  
  public recipeObject(String id, String name, String description, String instructions, Boolean Star) {
    setId(id);
    setName(name);
    setDescription(description);
    setInstructions(instructions);
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
  
  public ArrayList<CommentObject> getComments() {
    return new ArrayList<>(CommentList);
  }

  public void addComment(CommentObject comment) {
    CommentList.add(comment);
  }


  public ArrayList<QuanitityIngredients> getIngredientsList() {
    return new ArrayList<>(QuantityIngredientList);
  }

  public void addIngredient(QuanitityIngredients ingredient) {
    QuantityIngredientList.add(ingredient);
  }

  public ArrayList<tagObject> getTagList() {
    return new ArrayList<>(tagList);
  }

  public void addTag(tagObject tag) {
    tagList.add(tag);
  }

}
