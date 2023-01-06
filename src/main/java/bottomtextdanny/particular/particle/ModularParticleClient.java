package bottomtextdanny.particular.particle;

import bottomtextdanny.particular.braincellapi.ExtraOptions;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ModularParticleClient<E extends ExtraOptions> {
	public final ModularParticleClientData<E> defaultOptions;
	final ImmutableSet<ResourceLocation> textures;
	final MutableSpriteSet spriteSet = new MutableSpriteSet();
	public final ModularParticleCreator<E> creator;

	public ModularParticleClient(ModularParticleClientData<E> defaultOptions, List<ResourceLocation> textures, ModularParticleCreator<E> creator) {
		super();
		this.defaultOptions = defaultOptions;
		this.textures = ImmutableSet.copyOf(textures);
		defaultOptions.manager = this;
		this.creator = creator;
	}

	public SpriteSet spriteSet() {
		return spriteSet;
	}

	public Particle create(ModularParticleClientData<E> data, ClientLevel level, double x, double y, double z, double xDelta, double yDelta, double zDelta) {
		return creator.createParticle(data, level, x, y, z, xDelta, yDelta, zDelta);
	}

	public int sprites() {
		return textures.size();
	}
}
