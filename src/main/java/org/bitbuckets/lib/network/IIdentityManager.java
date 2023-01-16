package org.bitbuckets.lib.network;

/**
 * true to the name it manages identities
 *
 * The identities are used to build smartdashboard paths such as
 * intake/control/intakeMotorSpeed
 *
 * TODO: Eventually, the IdentityManager will be used to manage subsystems with multiple components, and support enabling/disabling them
 * For example you could disable /drive or /drive/balance/* to disable all functionality in that path
 */
public interface IIdentityManager {


    //Some/Smart/Dashboard/Thing

    /**
     * Acquires an identity key for a device with no dependencies
     * @param name
     * @return integer identity "key" representing your factory's built object
     */
    int rootProcess(String name);

    /**
     * Acquires a new random identity key for a device, registering it as a child of another process
     * @param parentId
     * @param name
     * @return integer key representing your factory's object
     */
    int childProcess(int parentId, String name);

    /**
     * Acquires the full string path for an identity
     * @param id
     * @return something like /swerve/talon/encoder1
     */
    String fullPath(int id);


}
