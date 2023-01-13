package org.bitbuckets.lib.index;

/**
 * Index for pid constants to be used on an array storing pid constants
 */
public interface PIDIndex {

    byte P = 0;
    byte I = 1;
    byte D = 2;
    byte FF = 3;
    byte IZONE = 4;

    static double[] CONSTANTS(double p, double i, double d, double ff, double izone) {
        return new double[] {
                p,
                i,
                d,
                ff,
                izone
        };
    }

}
