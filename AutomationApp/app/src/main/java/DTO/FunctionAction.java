package DTO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laiwf on 05/03/2017.
 */

public class FunctionAction implements Serializable {
    private String id;
    private HashMap<String,String> parameter;

    public FunctionAction() {
        this.id = "";
        this.parameter = new HashMap<>();
    }

    public FunctionAction( String id, HashMap<String, String> parameter) {
        this.id = id;
        this.parameter = parameter;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "FunctionAction{" +
                "id='" + id + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
