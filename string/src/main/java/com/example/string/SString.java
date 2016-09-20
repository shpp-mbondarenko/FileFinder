package com.example.string;

import java.util.Arrays;

/**
 * Created by Maxim on 14.09.2016.
 */
public class SString {
    private int offset;
    private int count;
    private char[] value;




    public SString() {
        this.offset = 0;
        this.count = 0;
        this.value = new char[0];
    }

    public SString(char value[]) {
        int size = value.length;
        this.offset = 0;
        this.count = size;
        this.value = Arrays.copyOf(value, size);
    }

    // Package private constructor which shares value array for speed.
    SString(int offset, int count, char value[]) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public int length() {
        return count;
    }

    public SString substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > count) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        if (beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
        }
        return ((beginIndex == 0) && (endIndex == count)) ? this :
                new SString(offset + beginIndex, endIndex - beginIndex, value);
    }
}
