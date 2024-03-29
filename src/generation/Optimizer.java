package generation;

import semantic.FunctionTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Optimizer {
    private static final int MAX_SIZE_UNROLL = 7;
    private static final int[] UNROLL_FACTORS = new int[]{2, 3, 5};

    static String optimize(FunctionTable method) {
        return optimizeCode(cleanupCode(optimizeCode(method.methodCode())));
    }

    private static String optimizeCode(String generatedCode) {
        String optimizedCode = generatedCode;

        do {
            generatedCode = optimizedCode;
            optimizedCode = instructionCombining(loopUnrolling(generatedCode));
        } while (!generatedCode.equals(optimizedCode));

        return optimizedCode;
    }

    private static String loopUnrolling(String generatedCode) {
        return Pattern.compile(
                "\t(bipush|sipush|ldc) ([0-9]+)" + System.lineSeparator()
                + "\tistore ([0-9]+)" + System.lineSeparator()
                + "\tgoto (L[0-9]+)" + System.lineSeparator()
                + "\t(L[0-9]+):" + System.lineSeparator()
                + "((\t.+" + System.lineSeparator() + ")*)"
                + "\tiinc ([0-9]+) ([0-9]+)" + System.lineSeparator()
                + "\t(L[0-9]+):" + System.lineSeparator()
                + "\tiload ([0-9]+)" + System.lineSeparator()
                + "\t(bipush|sipush|ldc) ([0-9]+)" + System.lineSeparator()
                + "\tif_icmplt (L[0-9]+)" + System.lineSeparator()
        ).matcher(generatedCode).replaceAll(mr -> {
            if (mr.group(3).equals(mr.group(8)) && mr.group(3).equals(mr.group(11)) // def-inc-test var consistency
                    && mr.group(4).equals(mr.group(10)) && mr.group(5).equals(mr.group(14)) // Label consistency
                    && mr.group(7).split(System.lineSeparator()).length < MAX_SIZE_UNROLL) { // Loop body size
                final int deltaValue = Integer.parseInt(mr.group(13)) - Integer.parseInt(mr.group(2));
                final int incValue = Integer.parseInt(mr.group(9));

                for (int factor : UNROLL_FACTORS) {
                    if (deltaValue % factor == 0 && (deltaValue / factor) % incValue == 0) {
                        if (deltaValue / factor == incValue) {
                            final StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < factor; i++) {
                                sb.append(mr.group(6));
                            }

                            return sb.toString();
                        }
                        else {
                            final StringBuilder sb = new StringBuilder(
                                    "\t" + mr.group(1) + " " + mr.group(2) + System.lineSeparator()
                                            + "\tistore " + mr.group(3) + System.lineSeparator()
                                            + "\tgoto " + mr.group(4) + System.lineSeparator()
                                            + "\t" + mr.group(5) + ":" + System.lineSeparator()
                            );

                            for (int i = 0; i < factor; i++) {
                                sb.append(mr.group(6));
                            }

                            return sb.toString() + "\tiinc " + mr.group(8) + " " + mr.group(9) + System.lineSeparator()
                                    + "\t" + mr.group(10) + ":" + System.lineSeparator()
                                    + "\tiload " + mr.group(11) + System.lineSeparator()
                                    + "\t" + mr.group(12) + " " + deltaValue / factor + System.lineSeparator()
                                    + "\tif_icmplt " + mr.group(14) + System.lineSeparator();
                        }
                    }
                }
            }
            return mr.group();
        });
    }

    private static String instructionCombining(String generatedCode) {
        final String optimizedCode = Pattern.compile(
                "\tiinc ([0-9]+) ([0-9]+)" + System.lineSeparator()
                + "\tiinc ([0-9]+) ([0-9]+)" + System.lineSeparator()
        ).matcher(generatedCode).replaceAll(mr -> {
            if (mr.group(1).equals(mr.group(3))) {
                return "\tiinc " + mr.group(1) + " "
                        + (Integer.parseInt(mr.group(2)) + Integer.parseInt(mr.group(4))) + System.lineSeparator();
            }

            return mr.group();
        });

        return Pattern.compile(
                "\t(bipush|sipush|ldc) ([0-9]+)" + System.lineSeparator()
                + "\tistore ([0-9]+)" + System.lineSeparator()
                + "\tiinc ([0-9]+) ([0-9]+)" + System.lineSeparator()
        ).matcher(optimizedCode).replaceAll(mr -> {
            if (mr.group(3).equals(mr.group(4))) {
                final int value = Integer.parseInt(mr.group(2)) + Integer.parseInt(mr.group(5));

                if (value >= -128 && value <= 127)
                    return "\tbipush " + value + System.lineSeparator()
                            + "\tistore " + mr.group(3) + System.lineSeparator();
                else if (value >= -32768 && value <= 32767)
                    return "\tsipush " + value + System.lineSeparator()
                            + "\tistore " + mr.group(3) + System.lineSeparator();
                else
                    return "\tldc " + value + System.lineSeparator()
                            + "\tistore " + mr.group(3) + System.lineSeparator();
            }

            return mr.group();
        });
    }

    static String cleanupCode(String generatedCode) {
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
