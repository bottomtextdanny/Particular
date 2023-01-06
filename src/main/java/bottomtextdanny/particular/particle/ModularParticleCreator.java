package bottomtextdanny.particular.particle;

import bottomtextdanny.particular.braincellapi.ExtraOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;

import javax.annotation.Nullable;

public interface ModularParticleCreator<E extends ExtraOptions> {
	@Nullable
	Particle createParticle(ModularParticleClientData<E> data, ClientLevel level, double x, double y, double z, double xDelta, double yDelta, double zDelta);
}
