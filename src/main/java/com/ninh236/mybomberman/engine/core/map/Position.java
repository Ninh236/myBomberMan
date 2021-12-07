package com.ninh236.mybomberman.engine.core.map;

public class Position implements Cloneable {

    public final int row;
    public final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    protected Object clone() {
        return new Position(row, column);
    }

    @Override
    public int hashCode() {
        var hash = 3;
        hash = 67 * hash + this.row;
        hash = 67 * hash + this.column;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        final var positionObject = (Position) object;
        return this.row == positionObject.row && this.column == positionObject.column;
    }

}
