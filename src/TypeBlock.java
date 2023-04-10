import java.util.ArrayList;

public class TypeBlock {

    private String name, type, description;
    private ArrayList<String> values;

    public TypeBlock(){
        values = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addValue(String value){
        values.add(value);
    }

    public ArrayList<String> getValues(){
        return values;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
