package Entities;

import Locations.Lounge;
import Locations.RepairArea;
import Locations.SupplierSite;
import Loggers.Logger;
import jdk.nashorn.internal.runtime.ECMAException;

public class Manager extends Thread{

    /**
     *  Type of Manager Tasks
     * */
    private static final int    ATTEND_CUSTOMER = 0,
                                CALL_CUSTOMER   = 1,
                                FILL_STOCK      = 2,
                                READ_PAPER      = 3,
                                DAY_OFF         = 4;



    private static final String CLASS   = "Manager",
                                RUN     = "run";

    private Lounge lounge;

    private RepairArea repairArea;

    private SupplierSite supplierSite;

    public Manager(Lounge lounge, RepairArea repairArea, SupplierSite supplierSite)
    {   this.lounge = lounge;
        this.repairArea = repairArea;
        this.supplierSite = supplierSite;
    }

    @Override
    public void run() {
        Integer task;
        while (!(task = lounge.getNextTask()).equals(DAY_OFF))
        {
            System.out.println(task);
            switch (task){
                case ATTEND_CUSTOMER:
                    lounge.attendCustomer();
                    break;
                case CALL_CUSTOMER:
                    break;
                case FILL_STOCK:
                    break;
                case READ_PAPER:
                    break;
                case DAY_OFF:
                    break;
                default:
                    Logger.log(CLASS,CLASS,"Unknown type of task",0,Logger.ERROR);
                    break;
            }

        }
    }

    private void readPaper()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }
}
