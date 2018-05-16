package gkfire.util;

import java.nio.ByteBuffer;
import java.util.Date;

public class FoxProConverter {

    private static final long BASE_FOXPRO_MILLIS = 210866803200000L;
    private static final long DAY_TO_MILLIS_FACTOR = 86400000L;

    public static Date toDate(byte[] foxProDate) {
        boolean isNull = true;
        for (byte b : foxProDate) {
            if (b != 0) {
                isNull = false;
            }
        }
        if (isNull) {
            return null;
        }
        if (foxProDate.length != 8) {
            throw new IllegalArgumentException("FoxPro date must be 8 bytes long");
        }

        byte[] reversedBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            reversedBytes[i] = foxProDate[(8 - i - 1)];
        }

        ByteBuffer buf = ByteBuffer.wrap(reversedBytes);

        long timeFieldMillis = buf.getInt();
        long dateFieldDays = buf.getInt();

        return new Date(dateFieldDays * 86400000L - 210866803200000L + timeFieldMillis);
    }
}
