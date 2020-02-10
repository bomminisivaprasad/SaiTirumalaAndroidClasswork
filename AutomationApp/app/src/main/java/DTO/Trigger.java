package DTO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laiwf on 05/03/2017.
 */

public class Trigger implements Serializable {
    private String id;
    private String name;
    private String desc;
    private String icon;



    public Trigger() {
    }


    public Trigger(String id,String name ,String desc , String icon) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trigger)) return false;

        Trigger trigger = (Trigger) o;

        return getId().equals(trigger.getId());

    }

    @Override
    public int hashCode() {
        int result = getIcon() != null ? getIcon().hashCode() : 0;
        result = 31 * result + (getDesc() != null ? getDesc().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trigger{" +
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
