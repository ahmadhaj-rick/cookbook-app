package cookbook.objects;

public class tagObject {
    private String tag_id;
    private String tag_name;

    /**
     * tag constructor.
     */
public tagObject(String tag_id, String tag_name){
    setTag_id(tag_id);
    setTag_name(tag_name);
}

public String getTag_id() {
    return tag_id;
}


public void setTag_id(String tag_id) {
    this.tag_id = tag_id;
}

public String getTag_name() {
    return tag_name;
}

public void setTag_name(String tag_name) {
    this.tag_name = tag_name;
}
    
    
}

