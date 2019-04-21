package Main;

public class Manager extends Thread
{ /**
     *  Life cycle of Manager
     *  */
    @Override
    public void run()
    {
    }

    private void readPaper()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }
}
