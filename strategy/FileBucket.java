package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.Helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile("tmp", null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        }catch (IOException e){
            Helper.printMessage(e.getMessage());
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize(){
        try {
            return Files.size(path);
        }catch (IOException e){
            Helper.printMessage(e.getMessage());
            return -1;
        }
    }

    public void putEntry(Entry entry){
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))

        ){

           outputStream.writeObject(entry);

        } catch (FileNotFoundException e) {
            Helper.printMessage(e.getMessage());
        } catch (IOException e) {
            Helper.printMessage(e.getMessage());
        }
    }

    public Entry getEntry(){
        if(getFileSize()>0) {
            try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))
                ) {

                Object object = inputStream.readObject();
                if (object instanceof Entry) {
                    return (Entry) object;
                }
            } catch (FileNotFoundException e) {
                Helper.printMessage(e.getMessage());
            } catch (IOException e) {
                Helper.printMessage(e.getMessage());
            } catch (ClassNotFoundException e) {
                Helper.printMessage(e.getMessage());
            }
        }
        return null;
    }

    public void remove(){
        try {
            Files.delete(path);
        } catch (IOException e) {
            Helper.printMessage(e.getMessage());
        }
    }
}
