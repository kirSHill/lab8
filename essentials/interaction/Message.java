package essentials.interaction;

import server.commands.PrintDescending;
import server.commands.interfaces.Command;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private final String text;
    private final LocalDateTime creationDate;
    private final boolean result;
    private Object object;
    private String command;
    private String type;
    private boolean forUpdate = false;

    public Message(boolean result, String text) {
        this.text = text;
        this.creationDate = LocalDateTime.now();
        this.result = result;
    }

    public boolean isForUpdate() {return forUpdate;}

    public void setForUpdate() {this.forUpdate = true;}

    public void setCommand(String command) {this.command = command;}

    public String getCommand() {return command;}

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {return type;}

    public String getText() {return text;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public boolean isSuccessful() {return this.result;}

    public Object getObject() {return object;}

    public void setObject(Object object) {this.object = object;}
}
