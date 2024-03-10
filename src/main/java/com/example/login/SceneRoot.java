package com.example.login;

import javafx.scene.Parent;

public class SceneRoot {
    private static Parent root;

    public static void setRoot(Parent root) {
        SceneRoot.root = root;
    }

    public static Parent getRoot() {
        return root;
    }

}
