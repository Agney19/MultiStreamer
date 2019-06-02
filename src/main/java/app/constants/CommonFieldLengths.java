package app.constants;

/** Список длин строковых полей в сущностях */
public class CommonFieldLengths {
    public static final int EMAIL = 64;
    public static final int NAME = 64;
    public static final int SURNAME = 64;

    private CommonFieldLengths() {
        throw new RuntimeException("not instantiable");
    }
}
