package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class FusionRotationSetup implements ISetup<IRotationEncoder> {

    final ISetup<IRotationEncoder> talonEncoder;
    final ISetup<IRotationEncoder> canEncoder;

    public FusionRotationSetup(ISetup<IRotationEncoder> talonEncoder, ISetup<IRotationEncoder> canEncoder) {
        this.talonEncoder = talonEncoder;
        this.canEncoder = canEncoder;
    }

    @Override
    public FusionEncoder build(IHandle handle) {
        IRotationEncoder tal = talonEncoder.build(handle.child("talon"));
        IRotationEncoder absolute = canEncoder.build(handle.child("can"));

        FusionEncoder encoder = new FusionEncoder(tal, absolute);
        //encoder.realign();
        //handle.loopFactory().registerLoop(encoder::realign);

        return encoder;
    }
}
