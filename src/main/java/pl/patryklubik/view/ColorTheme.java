package pl.patryklubik.view;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */
public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme){
        switch (colorTheme) {
            case LIGHT:
                return "css/themeLight.css";
            case DEFAULT:
                return "css/themeDefault.css";
            case DARK:
                return "css/themeDark.css";
            default:
                return null;
        }
    }
}
