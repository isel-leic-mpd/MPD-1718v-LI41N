package util;

import java.text.MessageFormat;

public final class Logging {
    private Logging() {
    }

    public static void log(String str) {
        System.out.println(MessageFormat.format("{0} on thread {1}", str, Thread.currentThread().getId()));
    }
}
