package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public  static void main(String[] args){
        testStrategy(new HashMapStorageStrategy(),10000);
        testStrategy(new OurHashMapStorageStrategy(),10000);
        testStrategy(new FileStorageStrategy(),1000);
        testStrategy(new OurHashBiMapStorageStrategy(),10000);
        testStrategy(new HashBiMapStorageStrategy(),10000);
        testStrategy(new DualHashBidiMapStorageStrategy(),10000);

    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> set = new HashSet<>();

        for(String s:strings){
            set.add(shortener.getId(s));
        }
        return set;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> set = new HashSet<>();

        for(Long s:keys){
            set.add(shortener.getString(s));
        }
        return set;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> setStrings = new HashSet<>();
        Shortener shortener = new Shortener(strategy);

        //generate strings
        for (long i=0;i<elementsNumber;i++){
            setStrings.add(Helper.generateRandomString());
        }

        //test getIds
        Long dateStart = new Date().getTime();


        Set setId = getIds(shortener,setStrings);

        Long dateEnd= new Date().getTime();

        Long delay = (dateEnd-dateStart);
        Helper.printMessage(delay.toString());

        //test getStrings

        dateStart = new Date().getTime();

        Set setTest = getStrings(shortener,setId);

        dateEnd = new Date().getTime();

        delay = (dateEnd-dateStart);
        Helper.printMessage(delay.toString());

        //test result

        if(setTest.equals(setStrings)){
            Helper.printMessage("Тест пройден.");
        }else {
            Helper.printMessage("Тест не пройден.");
        }


    }
}
