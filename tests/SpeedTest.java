package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Long startTime = new Date().getTime();

        for(String s:strings){
            ids.add(shortener.getId(s));
        }

        Long endTime = new Date().getTime();
        Long res = endTime-startTime;
        return res;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Long startTime = new Date().getTime();

        for(Long id:ids){
            strings.add(shortener.getString(id));
        }

        Long endTime = new Date().getTime();
        Long res = endTime-startTime;
        return res;
    }

    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();

        for(int i = 0;i<10000;i++){
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> id1 = new HashSet<Long>();
        Set<Long> id2 = new HashSet<Long>();
        Long time1 = getTimeForGettingIds(shortener1,origStrings,id1);
        Long time2 = getTimeForGettingIds(shortener2,origStrings,id2);

        Assert.assertTrue(time1>time2);

        time1=getTimeForGettingStrings(shortener1,id1,new HashSet<>());
        time2=getTimeForGettingStrings(shortener2,id2,new HashSet<>());

        Assert.assertEquals(time1,time2,30);


    }


}
