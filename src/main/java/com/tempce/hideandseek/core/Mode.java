package com.tempce.hideandseek.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum Mode implements Serializable {
    Normal("normal"),
    Hard("hard"),
    Insane("insane");

    private String serializedName;

    Mode(String serializedName) {
        this.serializedName = serializedName;
    }

    public String getSerializedName() {
        return serializedName;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(this.serializedName);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.serializedName = (String) ois.readObject();
    }
}
