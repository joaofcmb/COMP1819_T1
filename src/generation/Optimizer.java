package generation;

import semantic.FunctionTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Optimizer {
    static String optimize(FunctionTable method) {
        String generatedCode = method.methodCode();

        /*
        boolean furtherOptimization = true;
        while (furtherOptimization) {
        }
        */

        generatedCode = cleanupCode(generatedCode);

        return generatedCode;
    }

    private static String cleanupCode(String generatedCode) {
        // Usage of iinc for increments
        String cleanupCode = Pattern.compile(
                "\tiload ([0-9]+)" + System.lineSeparator()
                + "\t(bipush|sipush|ldc) ([0-9]+)" + System.lineSeparator()
                + "\tiadd " + System.lineSeparator()
                + "\tistore ([0-9]+)" + System.lineSeparator()
        ).matcher(generatedCode).replaceAll(mr -> {
            if (mr.group(1).equals(mr.group(4)))
                return "\tiinc " + mr.group(1) + " " + mr.group(3) + System.lineSeparator();
            else
                return mr.group();
        });

        // Branch elimination
        final Matcher matcher = Pattern.compile(
                "\tL([0-9]+):" + System.lineSeparator()
                + "\t(ireturn |return|goto L[0-9]+)" + System.lineSeparator()
        ).matcher(cleanupCode);

        while (matcher.find()) {
            cleanupCode = cleanupCode.replaceAll(
                    "\tgoto L" + matcher.group(1) + System.lineSeparator(),
                    "\t" + matcher.group(2) + System.lineSeparator()
            );
        }

        return cleanupCode;
    }
}
