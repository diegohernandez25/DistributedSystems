package Locals;

import Communication.Com;
import Interfaces.LoungeSS;

public class SupplierSite implements LoungeSS {
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
    public SupplierSite(String server, int port)
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
