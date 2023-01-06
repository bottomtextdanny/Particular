package bottomtextdanny.particular;

import bottomtextdanny.particular.braincellapi.StretchLoopOptions;
import bottomtextdanny.particular.braincellapi.StretchOptions;
import bottomtextdanny.particular.braincellapi.LoopedStretchableSpriteParticle;
import bottomtextdanny.particular.braincellapi.StretchableSpriteParticle;
import bottomtextdanny.particular.braincellapi.ParticleAction;
import bottomtextdanny.particular.particle.ModularParticleClient;
import bottomtextdanny.particular.particle.ModularParticleClientData;
import bottomtextdanny.particular.particle.ModularParticleCreator;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatLists;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

public class ParticularParticles {

	public static final ModularParticleClient<StretchOptions> DUST = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(20)),
		serial("dust", 1),
		stretchableCreator(1)
	);

	public static final ModularParticleClient<StretchOptions> CLOUD = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(8)),
		serialInv("minecraft", "generic", 8),
		stretchableCreator(8)
	);

	public static final ModularParticleClient<StretchOptions> HASTY_SMOKE = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(10)),
		serial("hasty_smoke", 10),
		stretchableCreator(10)
	);

	public static final ModularParticleClient<StretchOptions> SHINE = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(5)),
		serial("shine", 5),
		stretchableCreator(5)
	);

	public static final ModularParticleClient<StretchOptions> FLASHY_EXPLOSION = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(6)),
		serial("flashy_explosion_glint", 6),
		stretchableCreator(6)
	);

	public static final ModularParticleClient<StretchOptions> LIT_EXPLOSION = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(8)),
		serial("lit_explosion_glint", 8),
		stretchableCreator(8)
	);

	public static final ModularParticleClient<StretchOptions> SHOCKY_EXPLOSION = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(4)),
		serial("shocky_explosion_glint", 4),
		stretchableCreator(4)
	);

	public static final ModularParticleClient<StretchLoopOptions> SOUL = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, StretchLoopOptions.progressive(25, 2, 10)),
		serial("soul", 15),
		stretchableLoopCreator(15)
	);

	public static final ModularParticleClient<StretchLoopOptions> LITTLE_SOUL = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, StretchLoopOptions.progressive(25, 2, 11)),
		serial("little_soul", 16),
		stretchableLoopCreator(16)
	);

	public static final ModularParticleClient<StretchOptions> INK_SPLASH = new ModularParticleClient<>(
		ModularParticleClientData.defaulted(ParticleAction.NO, ParticleAction.NO, new StretchOptions(20)),
		serial("ink_splash", 6),
		stretchableCreator(6)
	);

	private static List<ResourceLocation> serial(String name, int size) {
		List<ResourceLocation> textures = new LinkedList<>();

		for (int i = 0; i < size; i++) {
			textures.add(new ResourceLocation(Particular.ID, "particle/" + name + '_' + i));
		}

		return textures;
	}

	private static List<ResourceLocation> serial(String group, String name, int size) {
		List<ResourceLocation> textures = new LinkedList<>();

		for (int i = 0; i < size; i++) {
			textures.add(new ResourceLocation(group, "particle/" + name + '_' + i));
		}

		return textures;
	}

	private static List<ResourceLocation> serialInv(String group, String name, int size) {
		List<ResourceLocation> textures = new LinkedList<>();

		for (int i = size - 1; i >= 0; i--) {
			textures.add(new ResourceLocation(group, "particle/" + name + '_' + i));
		}

		return textures;
	}

	private static ModularParticleCreator<StretchOptions> stretchableCreator(int size) {
		FloatList defaultFrameSpace = getLinearFrameSpace(size);
		return (data, level, x, y, z, xDelta, yDelta, zDelta) -> {
			if (data.extra.frameSpace == null) data.extra.frameSpace = defaultFrameSpace;
			return new StretchableSpriteParticle(data.getManager().sprites(),
				level, x, y, z, xDelta, yDelta, zDelta, data.start, data.tick, data.extra, data.getManager().spriteSet());
		};
	}

	private static ModularParticleCreator<StretchLoopOptions> stretchableLoopCreator(int size) {
		FloatList defaultFrameSpace = getLinearFrameSpace(size);
		return (data, level, x, y, z, xDelta, yDelta, zDelta) -> {
			if (data.extra.frameSpace == null) data.extra.frameSpace = defaultFrameSpace;
			return new LoopedStretchableSpriteParticle(data.getManager().sprites(),
				level, x, y, z, xDelta, yDelta, zDelta, data.start, data.tick, data.extra, data.getManager().spriteSet());
		};
	}

	private static FloatList getLinearFrameSpace(int size) {
		FloatList list = new FloatArrayList();

		float inv = 1.0F / size;
		float stack = 0.0F;

		for (int i = 0; i < size; i++) {
			list.add(stack += inv);
		}

		return FloatLists.unmodifiable(list);
	}
}
