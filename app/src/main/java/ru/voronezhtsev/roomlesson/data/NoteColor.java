package ru.voronezhtsev.roomlesson.data;

public enum NoteColor {

    BLACK("Черный"),
    RED("Красный"),
    GREEN("Зеленый");
    public static final String NO_SUCH_COLOR = "Нет такого цвета";
    private String mName;

    NoteColor(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public static NoteColor parseColor(String colorDef) {
        switch (colorDef) {
            case "#ff000000":
                return BLACK;
            case "#ffff0000":
                return RED;
            case "#9983CC39":
                return GREEN;
            default:
                throw new IllegalStateException(NO_SUCH_COLOR + " " + colorDef);
        }
    }
    public String getCode() {
        switch(this) {
            case BLACK:
                return "#ff000000";
            case RED:
                return "#ffff0000";
            case GREEN:
                return "#9983CC39";
        }
        throw new IllegalStateException(NO_SUCH_COLOR);
    }
}
