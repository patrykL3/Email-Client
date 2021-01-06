package pl.patryklubik.controller;

import pl.patryklubik.EmailManager;
import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */

public abstract class BaseController {

    protected EmailManager emailManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
