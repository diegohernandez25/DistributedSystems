package Main;

public class Customer extends Thread{


    /**
     *  Life cycle of the customer thread
     * */
    @Override
    public void run()
    {
    }

    /**
     *  Living a normal life (internal operation)
     * */

    private void livingNormalLife()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }

}