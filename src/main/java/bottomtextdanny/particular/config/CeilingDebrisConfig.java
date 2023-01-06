package bottomtextdanny.particular.config;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Set;

public class CeilingDebrisConfig {
	private final ForgeConfigSpec.DoubleValue chance;
	private final ForgeConfigSpec.IntValue minParticles;
	private final ForgeConfigSpec.IntValue maxParticles;
	private final BlocksConfig appliedBlocks;

	public CeilingDebrisConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Ceiling Debris");
		builder.comment("Random animation tick chance that this effect happens, a zero input avoids processing altogether.");
		chance = builder.defineInRange("Chance", 0.003, 0.0, 1.0);
		minParticles = builder.defineInRange("MinParticles", 1, 0, 100000);
		maxParticles = builder.defineInRange("MaxParticles", 7, 0, 100000);
		appliedBlocks = new BlocksConfig(builder, "AppliedToBlocks", "#minecraft:base_stone_overworld #minecraft:base_stone_nether");
		builder.pop();
	}

	public float chance() {
		return chance.get().floatValue();
	}

	public int min() {
		return minParticles.get();
	}

	public int max() {
		return maxParticles.get();
	}

	public Set<Block> appliedBlocks() {
		return appliedBlocks.get();
	}
}
