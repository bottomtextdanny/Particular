package bottomtextdanny.particular.config;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Set;

public class ExplosionConfig {
	private final ForgeConfigSpec.IntValue minParticles;
	private final ForgeConfigSpec.IntValue maxParticles;

	public ExplosionConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Explosion");
		builder.comment("Particle amount range for each explosion.");
		minParticles = builder.defineInRange("MinParticles", 1, 0, 100000);
		maxParticles = builder.defineInRange("MaxParticles", 7, 0, 100000);
		builder.pop();
	}

	public int min() {
		return minParticles.get();
	}

	public int max() {
		return maxParticles.get();
	}
}
