package com.people;

/**
 * Enum of colors
 */
public enum Color {
    RED,
    BLACK,
    BLUE,
    ORANGE,
    WHITE,
    BROWN;

    /**
     * Returns comma separated list with the colors.
     * @return RED, BLACK, BLUE, ORANGE, WHITE, BROWN
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Color color : values()) {
            nameList.append(color.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }

}