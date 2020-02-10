package DTO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laiwf on 05/03/2017.
 */

public class Action implements Serializable {

    private String id;
    private String icon;
    private String desc;
    private String name;

    public Action(){
        this.id = "";
        this.icon = "";
        this.desc = "";
        this.name = "";
    }

    public Action(String id, String icon, String desc, String name) {
        this.id = id;
        this.icon = icon;
        this.desc = desc;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Action{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("icon", icon);
        result.put("desc", desc);
        result.put("name", name);

        return result;
    }
}
