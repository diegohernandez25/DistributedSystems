package Resources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *    Parametric FIFO derived from a parametric memory.
 *    Errors are reported.
 *
 *    @param <R> data type of stored objects
 */

public class MemFIFO<R> extends MemObject<R>
{
  /**
   *   Pointer to the first empty location.
   */

   private volatile int inPnt;

  /**
   *   Pointer to the first occupied location.
   */

   private volatile int outPnt;

  /**
   *   Signaling FIFO empty state.
   */

   private volatile boolean empty;

   private int numElem;

  /**
   *   FIFO instantiation.
   *   The instantiation only takes place if the memory exists.
   *   Otherwise, an error is reported.
   *
   *     @param storage memory to be used
   *     @throws MemException when the memory does not exist
   */

   public MemFIFO (R [] storage) throws MemException
   {
     super (storage);
     inPnt = outPnt = 0;
     empty = true;
     this.numElem= 0;
   }

  /**
   *   FIFO insertion.
   *   A parametric object is written into it.
   *   If the FIFO is full, an error is reported.
   *
   *    @param val parametric object to be written
   *    @throws MemException when the FIFO is full
   */

   @Override
   public void write (R val) throws MemException
   {
     if ((inPnt != outPnt) || empty)
        { mem[inPnt] = val;
          inPnt = (inPnt + 1) % mem.length;
          empty = false;
          this.numElem++;
        }
        else throw new MemException ("Fifo full!");
   }

  /**
   *   FIFO retrieval.
   *   A parametric object is read from it.
   *   If the FIFO is empty, an error is reported.
   *
   *    @return first parametric object that was written
   *    @throws MemException when the FIFO is empty
   */

   @Override
   public R read () throws MemException
   {
     R val;

     if (!empty)
        { val = mem[outPnt];
            mem[outPnt]=null;
          outPnt = (outPnt + 1) % mem.length;
          if(mem.length==1) empty = true;
          else empty = (inPnt == outPnt);
          this.numElem--;
        }
        else throw new MemException ("Fifo empty!");
     return val;
   }

   public boolean isEmpty()
   {
       return empty;
   }


   public R peek () throws MemException
   {
       R val = null;
       if(!empty) { val = mem[outPnt]; }
       else throw new MemException("Fifo empty!");
       return val;
   }

   /**
    *       Checks if storage contains input value.
    *
    *       @param val    - value to check
    *
    *       @return true - storage contains value. false- otherwise.
    * */
   public boolean containsValue(R val)
   {    if(empty) return false;
        for (R r : mem) {
            if(r == null) continue;
            if (r.equals(val)) return true; }
        return false;
   }

    public int numElements(){
       return numElem;
    }


    @Override
    public String toString() {
        return "MemFIFO{" +
                "mem=" + Arrays.toString(mem) +
                '}';
    }
    public int getInPtn()
    {
        return inPnt;
    }

    public int getOutPtn()
    {
        return outPnt;
    }
}
