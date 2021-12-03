package Multiplayer;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

@Serializable
public class HelloMessage extends AbstractMessage {

    private String hello;       // custom message data

    public HelloMessage() {
    }    // empty constructor

    public HelloMessage(String s) {
        hello = s;
    } // custom constructor

    public static void initializeSerializables() {
        Serializer.registerClass(HelloMessage.class);
        //Serializer.registerClass(PosAndDirMessage.class);
        //Serializer.registerClass(PosMessage.class);
    }

    public String getSomething() {
        return "message of HelloMessage: ";
    }
}
