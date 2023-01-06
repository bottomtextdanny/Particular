/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi;

@FunctionalInterface
public interface ParticleAction<T extends MParticle> {
	ParticleAction NO = particle -> {};

	default void _execute(MParticle particle) {
		execute((T)particle);
	}

	void execute(T particle);
}
