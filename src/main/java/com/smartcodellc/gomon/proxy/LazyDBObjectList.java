package com.smartcodellc.gomon.proxy;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.smartcodellc.gomon.DBObjectLoader;
import com.smartcodellc.gomon.GomonEnhancer;

import java.util.*;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class LazyDBObjectList<T> implements List<T> {

    private BasicDBList basicDBList;

    private DBObjectLoader loader;

    private GomonEnhancer enhancer;

    private Class clazz;
    
    private List<T> initalized;

    public LazyDBObjectList(GomonEnhancer enhancer, BasicDBList basicDBList, DBObjectLoader loader, Class clazz) {
        this.basicDBList = basicDBList;
        this.loader = loader;
        initalized = new LinkedList<T>();
        this.clazz = clazz;
        this.enhancer = enhancer;
    }

    public int size() {
        return basicDBList.size();
    }

    public boolean isEmpty() {
        return basicDBList.isEmpty();
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<T> iterator() {
        return null;
    }

    public Object[] toArray() {
        return basicDBList.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return basicDBList.toArray(ts);
    }

    public boolean add(T t) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> objects) {
        return false;
    }

    public boolean addAll(Collection<? extends T> ts) {
        return false;
    }

    public boolean addAll(int i, Collection<? extends T> ts) {
        return false;
    }

    public boolean removeAll(Collection<?> objects) {
        return false;
    }

    public boolean retainAll(Collection<?> objects) {
        return false;
    }

    public void clear() {
        basicDBList.clear();
    }

    public T get(int i) {
        T element = null;
        if (initalized.size() > i) {
            element = initalized.get(i);
        }
        if (element == null) {
            BasicDBObject dbObject = (BasicDBObject) basicDBList.get(i);
            DBObjectProxy proxy = new DBObjectProxy(enhancer, loader);
            proxy.setDbObject(dbObject);
            element = (T) enhancer.enhance(proxy, clazz);
            initalized.add(i, element);
        }
        return element;
    }

    public T set(int i, T t) {
        return null;
    }

    public void add(int i, T t) {

    }

    public T remove(int i) {
        return (T) basicDBList.remove(i); // TODO: Handle
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator() {
        return null;
    }

    public ListIterator<T> listIterator(int i) {
        return null;
    }

    public List<T> subList(int i, int i1) {
        return null;
    }
}
