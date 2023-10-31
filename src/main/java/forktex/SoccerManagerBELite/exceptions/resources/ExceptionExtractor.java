package forktex.SoccerManagerBELite.exceptions.resources;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionExtractor {
    private static final int MAX_STACK_LENGTH = 2000;

    public static String getStackTrace(Exception exc) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exc.printStackTrace(pw);

        String traceResult = sw.toString();

        try {
            traceResult = traceResult.substring(0, MAX_STACK_LENGTH);
        } catch (IndexOutOfBoundsException ex) {
            return traceResult;
        }

        return traceResult;
    }
}
