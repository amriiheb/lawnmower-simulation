package com.mowitnow.mower.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.function.UnaryOperator;

@Getter
@Setter
public class Position {
     int x, y;
    char orientation ;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position moveHorizontal(int deltaX) {
        return new Position(this.x + deltaX, this.y);
    }

    public Position moveVertical(int deltaY) {
        return new Position(this.x, this.y + deltaY);
    }

     public Position constrain(int maxWidth, int maxHeight) {
        this.x = Math.min(Math.max(this.x, 0), maxWidth - 1);
        this.y = Math.min(Math.max(this.y, 0), maxHeight - 1);
        return this;
    }
}
