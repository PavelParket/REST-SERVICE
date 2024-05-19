package com.springboot.flats.Entity;

import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;

public class ObjectListDTO {
    private Object object;
    private List<?> list;

    public ObjectListDTO() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public <T> List<T> getObjectList(List<T> list, Class<T> tClass) {
        if (list != null) {
            List<T> realList = new ArrayList<>();
            for (T object : list) {
                if (object instanceof HibernateProxy hibernateProxy) {
                    Object realObject = hibernateProxy.getHibernateLazyInitializer().getImplementation();
                    realList.add(tClass.cast(realObject));
                } else realList.add(object);
            }
            return realList;
        }
        return list;
    }
}
