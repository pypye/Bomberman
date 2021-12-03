package Multiplayer;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

public class ClientListener implements MessageListener<Client> {
    public void messageReceived(Client source, Message message) {
        if (message instanceof HelloMessage) {
            // do something with the message
            HelloMessage helloMessage = (HelloMessage) message;
            System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");

        } else {
            System.out.println("In correct format");
        }
    }

    public static void main(String[] args) {

    }
}
