package DTO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laiwf on 05/03/2017.
 */

public class FunctionTrigger implements Serializable {
    private String id;
    private HashMap<String,String> parameter;

    public FunctionTrigger() {
        this.id = "";
        this.parameter = new HashMap<>();
    }

    public FunctionTrigger(String id, HashMap<String, String> parameter) {
        this.id = id;
        this.parameter = parameter;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("parameter", parameter);

        return result;
    }

    @Override
    public String toString() {
        return "FunctionTrigger{" +
                "id='" + id + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
