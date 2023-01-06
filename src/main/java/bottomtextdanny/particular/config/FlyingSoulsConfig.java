package bottomtextdanny.particular.config;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Set;

public class FlyingSoulsConfig {
	private final ForgeConfigSpec.DoubleValue chance;
	private final BlocksConfig appliedBlocks;

	public FlyingSoulsConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Flying Souls");
		builder.comment("Random animation tick chance that this effect happens, a zero input avoids processing altogether.");
		chance = builder.defineInRange("Chance", 0.05, 0.0, 1.0);
		appliedBlocks = new BlocksConfig(builder, "AppliedToBlocks", "minecraft:soul_fire");
		builder.pop();
	}

	public float chance() {
		return chance.get().floatValue();
	}

	public Set<Block> appliedBlocks() {
		return appliedBlocks.get();
	}
}
