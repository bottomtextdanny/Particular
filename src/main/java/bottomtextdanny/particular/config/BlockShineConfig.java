package bottomtextdanny.particular.config;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Set;

public class BlockShineConfig {
	private final ForgeConfigSpec.DoubleValue chance;
	private final BlocksConfig appliedBlocks;

	public BlockShineConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Block Shine");
		builder.comment("Random animation tick chance that this effect happens, a zero input avoids processing altogether.");
		chance = builder.defineInRange("Chance", 0.5, 0.0, 1.0);
		appliedBlocks = new BlocksConfig(builder, "AppliedToBlocks", "#minecraft:copper_ores #minecraft:iron_ores #minecraft:redstone_ores " +
																	 "#minecraft:lapis_ores #minecraft:gold_ores #minecraft:diamond_ores " +
																	 "#minecraft:emerald_ores " +
																	 "minecraft:ancient_debris " +
																	 "minecraft:raw_copper_block minecraft:raw_iron_block minecraft:raw_gold_block " +
																	 "minecraft:small_amethyst_bud minecraft:medium_amethyst_bud minecraft:large_amethyst_bud minecraft:amethyst_cluster ");
		builder.pop();
	}

	public float chance() {
		return chance.get().floatValue();
	}

	public Set<Block> appliedBlocks() {
		return appliedBlocks.get();
	}
}
