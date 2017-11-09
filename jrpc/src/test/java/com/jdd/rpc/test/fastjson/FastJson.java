package com.jdd.rpc.test.fastjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 *
 */
public class FastJson {
    public static void main(String[] args) {
        MyObject object = new MyObject();
        object.setId("123");
        object.setName("dalan");
        MyObjectList list = new MyObjectList();
        HashMap<String, MyObject> map = new HashMap<String, MyObject>();
        map.put(String.valueOf(System.currentTimeMillis()), object);
        list.getList().add(map);
        System.out.println(JSON.toJSON(list));
    }

}

class MyObjectList {
    private List<Map<String, MyObject>> list = new ArrayList<Map<String, MyObject>>();

    /**
     * getter method
     * 
     * @see MyObjectList#list
     * @return the list
     */
    public List<Map<String, MyObject>> getList() {
        return list;
    }

    /**
     * setter method
     * 
     * @see MyObjectList#list
     * @param list
     *            the list to set
     */
    public void setList(List<Map<String, MyObject>> list) {
        this.list = list;
    }

}

class MyObject {
    @JSONField(serialize = false)
    private String id;

    private String name;

    /**
     * getter method
     * 
     * @see com.jdd.rpc.test.fastjson.MyObject#id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * setter method
     * 
     * @see com.jdd.rpc.test.fastjson.MyObject#id
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter method
     * 
     * @see com.jdd.rpc.test.fastjson.MyObject#name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method
     * 
     * @see com.jdd.rpc.test.fastjson.MyObject#name
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
