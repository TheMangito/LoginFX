package com.example.login;

public class Alert {
    public String text;
    public String buttonText;

    public Alert(String text, String buttonText) {
        this.text = text;
        this.buttonText = buttonText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
