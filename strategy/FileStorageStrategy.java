package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000L;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    private long bucketSizeLimit=DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;
    




    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }



    int hash(Long k){
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    int indexFor(int hash, int length) {
        return hash & (length-1);
    }


    //complite
    Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash((long)key.hashCode());
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }


    //complite
    void resize(int newCapacity){

        FileBucket[] newTable = new FileBucket[newCapacity];
        for(FileBucket bucket:newTable){
            bucket=new FileBucket();
        }

        transfer(newTable);
        table = newTable;
        //threshold = (int)(newCapacity * loadFactor);
    }

    //complite
    void transfer(FileBucket[] newTable){

        int newCapacity = newTable.length;
        for (int j = 0; j < table.length; j++) {
            if(table[j]==null)continue;
            Entry e = table[j].getEntry();

            while (e != null) {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i].getEntry();
                    newTable[i].putEntry(e);
                    e = next;
            }
            table[j].remove();
            table[j]=null;

        }
    }



    //complite
    void addEntry(int hash, Long key, String value, int bucketIndex){
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        if (table[bucketIndex].getFileSize() >= bucketSizeLimit)
            resize(2 * table.length);
    }

    //complite
    void createEntry(int hash, Long key, String value, int bucketIndex){
        table[bucketIndex]=new FileBucket();
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key)!=null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null)
            return false;


        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
            return false;
    }


    @Override
    public void put(Long key, String value) {

        int hash = hash((long)key.hashCode());
        int i = indexFor(hash, table.length);
        if (table[i] == null) {
            createEntry(hash, key, value, i);
        }
        else {
            for (Entry e = table[i].getEntry(); e != null; e = e.next) {
                Object k;
                if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                    e.value = value;
                }
            }
            addEntry(hash, key, value, i);
        }

    }

    @Override
    public Long getKey(String value) {
        /*
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return e.key;
        }
        return null;
*/

        if (value == null)
         return -1L;


        for (int i = 0; i < table.length ; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return e.key;
        }
        return -1L;
    }

    @Override
    public String getValue(Long key) {
        return getEntry(key).getValue();
    }
}
