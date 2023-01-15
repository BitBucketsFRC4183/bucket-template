package org.bitbuckets.lib.encoder;

public class Angle {

    /**
     * constrains a parameter to the range [0, 2pi]
     * @param unbound_radians any radian value
     * @return that radian value represented from [0 to 2pi]
     */
    public static double wrap(double unbound_radians) {
        double angle = unbound_radians;

        angle %= (2.0 * Math.PI);
        if (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }

        return angle;
    }

}
