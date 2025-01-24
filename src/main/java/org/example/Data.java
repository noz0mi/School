package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Data implements Cloneable{

    private List<String> rawArray = new ArrayList<String>();

    public Data() {
    }

    public Data(List<String> rawArray) {
        this.rawArray = rawArray;
    }

    public List<String> getRawArray() {
        return rawArray;
    }

    public void setRawArray(List<String> rawArray) {
        this.rawArray = rawArray;
    }

    @Override
    public Data clone() throws CloneNotSupportedException {
        Data clone = (Data) super.clone();
        clone.rawArray = new ArrayList<>(this.rawArray);
        return clone;
    }



}
