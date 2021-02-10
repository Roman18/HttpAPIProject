package com.company.properiessetting;

import com.company.Exceptions.PropertiesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ConfigLoader {

private Object createObject(Class clazz)throws PropertiesException{
    try {
       Constructor constructor = clazz.getConstructor();
        return constructor.newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
        throw new PropertiesException("Need default constructor");
    }


}
    private void extractedProps(Object object,Properties prop)throws PropertiesException{
        Class clazz=object.getClass();
        for (Field f:clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(SystemProp.class)){
                SystemProp annotation = f.getAnnotation(SystemProp.class);
                String propName=annotation.value();
                String value=prop.getProperty(propName);
                f.setAccessible(true);
                try {
                    f.set(object,value);
                } catch (IllegalAccessException e) {
                    throw new PropertiesException("Fail load properties");
                }
            }

        }
    }

    public<T> T getSystemProp(Class <T> clazz) throws PropertiesException{
    Object object=createObject(clazz);
    extractedProps(object,System.getProperties());
    return (T)object;
    }

    public <T>  T getFileProp(Class <T> clazz,String file) throws PropertiesException{
try (InputStream is=new FileInputStream(file)){
    Properties properties=new Properties();
    properties.load(is);
    Object object=createObject(clazz);
    extractedProps(object,properties);
    return (T) object;
}catch (IOException e){
    throw new PropertiesException("Fail load properties from "+file);
}
    }
}
