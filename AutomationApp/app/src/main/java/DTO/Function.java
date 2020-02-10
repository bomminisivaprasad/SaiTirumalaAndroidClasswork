package DTO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laiwf on 05/03/2017.
 */

public class Function implements Serializable {

    private String id;
    private String name;
    private String desc;
    private String fail;
    private String success;
    private String status;
    private String timestamp_created;
    private FunctionAction action;
    private FunctionTrigger trigger;

    public Function() {
        this.id="";
        this.name = "";
        this.desc="";
        this.fail = "";
        this.success = "";
        this.status = "";
        this.timestamp_created = "";
        this.action =new FunctionAction() ;
        this.trigger = new FunctionTrigger();
    }

    public Function(String id,String name,String desc,String fail, String success, String status, String timestamp_created, FunctionAction action, FunctionTrigger trigger) {
        this.id=id;
        this.name = name;
        this.desc=desc;
        this.fail = fail;
        this.success = success;
        this.status = status;
        this.timestamp_created = timestamp_created;
        this.action = action;
        this.trigger = trigger;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp_created() {
        return timestamp_created;
    }

    public void setTimestamp_created(String timestamp_created) {
        this.timestamp_created = timestamp_created;
    }

    public FunctionAction getAction() {
        return action;
    }

    public void setAction(FunctionAction action) {
        this.action = action;
    }

    public FunctionTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(FunctionTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id='" + id + '\'' +
                ",name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", fail='" + fail + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", timestamp_created='" + timestamp_created + '\'' +
                ", action=" + action +
                ", trigger=" + trigger +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("desc", desc);
        result.put("fail", fail);
        result.put("success", success);
        result.put("status", status);
        result.put("timestamp_created", timestamp_created);
        result.put("action", action);
        result.put("trigger", trigger);

        return result;
    }

}
