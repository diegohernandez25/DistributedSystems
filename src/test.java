import Loggers.Logger;
import Resources.*;

public class test {
    public static void main(String[] args)
    {
        Logger log = new Logger();

        try {
            MemFIFO<Integer> t = new MemFIFO<>(new Integer[3]);
            System.out.println("Current size: " + t.numElements());
            System.out.println("Is empty? " + t.isEmpty());
            t.write(1);
            System.out.println("Written value: " + 1);
            t.write(2);
            System.out.println("Written value: " + 2);

            System.out.println("Current size: " + t.numElements());
            System.out.println("Is empty? " + t.isEmpty());

            t.write(3);
            System.out.println("Written value: " + 3);
            System.out.println("Current size: " + t.numElements());

            System.out.println("Read value : " + t.read());
            System.out.println("Current size: " + t.numElements());
            System.out.println("Is empty? " + t.isEmpty());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
