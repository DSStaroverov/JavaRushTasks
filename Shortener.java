package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;

public class Shortener {

    private Long lastId=0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy strategy){
        this.storageStrategy=strategy;
    }

    public synchronized Long getId(String string){
        if(storageStrategy.containsValue(string)){
            return storageStrategy.getKey(string);
        }else{
            lastId++;
            storageStrategy.put(lastId,string);
            return lastId;
        }
    }

    public synchronized String getString(Long id){
       // if(storageStrategy.containsKey(id)){
            return storageStrategy.getValue(id);
        //}
        //return null;
    }
}
