package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Name");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        Method meth = r.getClass().getMethod("toString");
        System.out.println(meth.invoke(r));
        System.out.println();

        Class r2 = Class.forName("com.urise.webapp.model.Resume");
        Field[] fields = r2.getDeclaredFields();
        for (Field field1 : fields) {
            System.out.println(field1.getName() + " ," + field1.getType());
        }
        System.out.println();

        Method method = r2.getMethod("toString");
        Method method1 = r2.getMethod("compareTo", Resume.class);
        System.out.println(Arrays.toString(method.getParameterTypes()));
        System.out.println(Arrays.toString(method1.getParameterTypes()));
    }
}