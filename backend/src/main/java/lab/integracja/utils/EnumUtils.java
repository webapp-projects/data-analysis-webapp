package lab.integracja.utils;

public class EnumUtils {
    public static <E extends Enum<E>> boolean isStringInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }
}
