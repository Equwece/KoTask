package com.equwece.kotask.data;

import java.awt.Color;
import java.util.Optional;

final public class Tag {
    final private String title;
    final private Optional<Color> color;

    public Tag(Optional<Color> color, String title) {
        this.color = color;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Optional<Color> getColor() {
        return color;
    }
}
