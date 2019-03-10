package Objects;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Key {
    private String keyValue;

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private Key(String keyValue) { this.keyValue = keyValue; }

    Key() { this(generateRandom(20, ThreadLocalRandom.current())); }

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
     * @brief Generate a random string.
     * @param length - Length of the string.
     * @return Random string.
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
}
