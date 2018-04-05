package com.csy.booklibrary.data;

public class Attribute {
    private String key;
    private Object value;


    public Attribute(String key, Object value){
        this.key = key;
        this.value = value;
    }

    @Override public boolean equals(Object o) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Attribute attribute = (Attribute) o;

        if ( key != null ? !key.equals(attribute.key) : attribute.key != null )
            return false;
        return value != null ? value.equals(attribute.value) : attribute.value == null;
    }

    @Override public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
