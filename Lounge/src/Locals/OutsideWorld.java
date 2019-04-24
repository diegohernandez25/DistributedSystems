package Locals;

import Communication.Com;
import Interfaces.LoungeOutsideWorld;

public class OutsideWorld implements LoungeOutsideWorld {

    /**
     * Server Name
     * */
    private final String server;

    /**
     * Server port.
     * */
    private final int port;

    /**
     * Outside world constructor.
     * @param server    server.
     * @param port      port.
     * */
    public OutsideWorld(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     * Terminate Outside World server.
     * */
    public void finish()
    {   Com.finish(this.server, this.port);
    }
}
