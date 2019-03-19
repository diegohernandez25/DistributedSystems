package Locations;

import Loggers.Logger;
import Objects.CarPart;
import Resources.MemException;
import Resources.MemStack;
import java.util.ArrayList;
import java.util.HashMap;

public class RepairArea {

    private static HashMap<String, MemStack<CarPart>> stock;


    Logger logger = new Logger();

    public void startRepairProcedure()
    {

    }

    public RepairArea(HashMap<String,MemStack<CarPart>> stock)
    {
        RepairArea.stock = stock;
    }

    public ArrayList<CarPart> getCarParts(String name, Integer number) {
        ArrayList<CarPart> tmp = new ArrayList<>();
        MemStack<CarPart> partStock = stock.get(name);
        if (partStock == null || partStock.isEmpty()) return null;
        int count = number;
        while (count-- > 0)
        {
            try {
                tmp.add(partStock.read());
            } catch (MemException e) {
                e.printStackTrace();
                return null;
            }
        }
        stock.remove(name);
        stock.put(name, partStock); //FIXME: Is this really necessary??
        return tmp;
    }

    public boolean addCarParts(String name, MemStack<CarPart> parts)
    {
        MemStack<CarPart> partStock = stock.get(name);
        while(!parts.isEmpty())
        {
            if(partStock.isFull())
            {
                //FIXME Process id
                Logger.log("Manager","Repair Area",
                        "Aquire more car parts than the limit",
                        0, Logger.WARNING);
                return false;
            }
            try {
                partStock.write(parts.read());
            } catch (MemException e) {
                Logger.logException(e);
                return false;
            }
        }
        stock.remove(name); //FIXME: Is this really necessary??
        stock.put(name,partStock);
        return true;
    }





}
