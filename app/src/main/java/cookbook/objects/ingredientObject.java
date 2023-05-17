package cookbook.objects;

public class ingredientObject {
    private String id;
    private String name;
    private String unit;
    private int amount;
    
    
    public ingredientObject(String id, String name, int amount, String unit){
        setId(id);
        setName(name);
        setAmount(amount);
        setUnit(unit);
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
    
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString() {
        return Integer.toString(amount) + unit + " " + name;
    }
}
