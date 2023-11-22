package Xanadu.Utils;

import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Component
public class HibernateProcessor {

    public <T> T unProxy(T t, Map<String, T> objectIsUnProxy, String path) throws IllegalAccessException, InvocationTargetException {
        if (t == null) return null;

        objectIsUnProxy.put(t.toString(), t);
        Field[] fields = t.getClass().getDeclaredFields();
        Method[] methods = t.getClass().getDeclaredMethods();
        HashMap<String, Method> methodsMap = new HashMap<>();
        for (Method method : methods) {
            methodsMap.put(method.getName(), method);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().getDeclaredAnnotation(Entity.class) != null && field.get(t) != null) {

                String className = field.getType().getName();
                String key = field.get(t).toString();

                if (path.contains(className + "/") && objectIsUnProxy.get(key) != null) {
                    try {
                        field.set(t, clone(field.get(t)));
                    } catch (Exception e) {
                        field.set(t, null);
                        e.printStackTrace();
                    }
                } else {
                    String newPath = path + className + "/";
                    field.set(t, unProxy((T) field.get(t), objectIsUnProxy, newPath));
                }
            }

            if (Collection.class.isAssignableFrom(field.getType())) {

                Collection<T> collection = (Collection<T>) field.get(t);

                if (collection != null) {
                    try {
                        Collection<T> newCollection = new ArrayList<>();
                        for (T _t : collection) {
                            String className = _t.getClass().getName();
                            String key = _t.toString();

                            if (path.contains(className + "/") && objectIsUnProxy.get(key) != null) {
                                newCollection.add(clone(_t));
                            } else {
                                String newPath = path + className + "/";
                                newCollection.add(unProxy(_t, objectIsUnProxy, newPath));
                            }

                        }
                        field.set(t, newCollection);

                    } catch (Exception e) {
                        String methodName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                        Method method = methodsMap.get(methodName);
                        method.invoke(t, (Object) null);
                    }
                }
            }
        }
        return t;
    }

    private <T> T clone(T t) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = t.getClass();
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T tClone = (T) constructor.newInstance();
        Field[] fields = Stream.concat(Arrays.stream(clazz.getSuperclass().getDeclaredFields()), Arrays.stream(clazz.getDeclaredFields())).toArray(Field[]::new);
        Method[] methods = t.getClass().getDeclaredMethods();
        Map<String, Method> methodsMap = new HashMap<>();
        for (Method method : methods) {
            methodsMap.put(method.getName(), method);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<?> collection = (Collection<?>) field.get(t);
                try {
                    boolean check = true;
                    for (Object o : collection) {
                        if (o.getClass().getDeclaredAnnotation(Entity.class) == null) {
                            field.set(tClone, collection);
                            check = false;
                        }
                        break;
                    }
                    if (check) {
                        field.set(tClone, null);
                    }
                } catch (Exception e) {
                    String methodName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                    Method method = methodsMap.get(methodName);
                    method.invoke(tClone, (Object) null);
                }

            } else {
                if (field.getType().getDeclaredAnnotation(Entity.class) == null) {
                    field.set(tClone, field.get(t));
                } else {
                    field.set(tClone, null);
                }
            }

        }

        return tClone;
    }


}
