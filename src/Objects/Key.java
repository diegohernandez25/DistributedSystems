package Objects;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Key {
    private String keyValue;
    private Integer id;

    /**
     *      Used variables to create an unique ID for the key car.
     * */
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String alphanum = upper + lower + digits;

    /**
     *
     *  Instantiation of the Key.
     *
     *      @param keyValue - Unique id of the key
     *
     * */
    private Key(String keyValue) { this.keyValue = keyValue; }

    /**
     *  Instantiation of the Key.
     * */
    Key() { this(generateRandom(20, ThreadLocalRandom.current())); }

    /**
     *  Get the unique value of the Key that represent it.
     * */
    public String getKeyValue() { return keyValue; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(keyValue, key.keyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyValue);
    }

    /**
     *  Generates a random String.
     *
     *      @param length - length of the String.
     *      @param random - object from the Random Class.
     *
     *      @return random String.
     * */
    private static String generateRandom(int length, Random random)
    {
        char[] buf = new char[length];
        for( int i = 0; i < length; i++)
        {
            buf[i] = alphanum.charAt(random.nextInt(alphanum.length()));
        }
        return new String(buf);
    }

    public void setId(Integer id) { this.id = id;}
    public Integer getId(){ return this.id;}
}
