package pl.patryklubik.model;

import javafx.scene.control.TreeItem;

/**
 * Create by Patryk Łubik on 08.01.2021.
 */


public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
    }
}
