package org.bitbuckets.lib;

import org.bitbuckets.lib.motor.IMotorFactory;

/**
 * BucketLib extended with motor factories so you can actually make motors lmao
 */
public interface UserBucketLib extends BucketLib {


    @Override
    UserBucketLib child(String name);

    /**
     * talon factory that you don't want to fx with (theoretically also possible to generate SRX from this)
     * @return
     */
    IMotorFactory talonFXFactory();



}
