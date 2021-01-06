package pl.patryklubik.view;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */
public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath(FontSize fontSize){
        switch (fontSize) {
            case MEDIUM:
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            case SMALL:
                return "css/fontSmall.css";
            default:
                return null;
        }
    }
}
