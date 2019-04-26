package Communication;

/**
 * Common communication functions used by the proxies.
 * */
public class Com {
    /**
     *  Open socket communication.
     * @param server    - server name.
     * @param port      - port
     *  @return client communication object.
     * */
    public static ClientCom openChannel(String server, int port)
    {   ClientCom clientCom = new ClientCom(server,port);
        while (!clientCom.open())
        {   try
        {   Thread.sleep((long) 10);
        } catch (InterruptedException ignored){}

        }
        return clientCom;
    }

    /**
     *  Closes socket communication.
     *  @param clientCom - client communication object.
     * */
    private static void closeChannel(ClientCom clientCom){ clientCom.close();}

    /**
     * Checks if message is according to the expected message. If it is, returns response message.
     * Closes connection.
     * @param clientCom - client socket communication object.
     * @param msgType   - expected message type.
     * @return response message.
     * */
    public static Message expectMessageType(ClientCom clientCom, MessageType msgType)
    {   Message response = (Message) clientCom.readObject();
        if(response.getType() != msgType)
        {   System.out.println("Expecting message of type "+ msgType.toString());
            System.exit(1);
        }
        closeChannel(clientCom);
        return response;
    }
}
