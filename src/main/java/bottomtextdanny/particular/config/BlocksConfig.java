package bottomtextdanny.particular.config;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.Optional;
import java.util.Set;

public class BlocksConfig {
	private static final Logger LOGGER = LogManager.getLogger("PARTICULAR BLOCK PARSING");
	private final ForgeConfigSpec.ConfigValue<String> blocks;
	private String lastBlocksSaved;
	private Set<Block> cachedBlocks;

	public BlocksConfig(ForgeConfigSpec.Builder builder, String path, String defaultValue) {
		blocks = builder.define(path, defaultValue);
	}

	public Set<Block> get() {
		String dyn = blocks.get();

		if (!dyn.equals(lastBlocksSaved)) {
			lastBlocksSaved = dyn;

			String[] codified = dyn.split("\\s+");
			ImmutableSet.Builder<Block> set = new ImmutableSet.Builder<>();

			for (String key : codified) {
				checkKey(set, key);
			}

			cachedBlocks = set.build();
		}

		if (cachedBlocks != null) {
			return cachedBlocks;
		} else {
			return Set.of();
		}
	}

	private void checkKey(ImmutableSet.Builder<Block> set, String key) {
		if (key.length() > 0 && key.charAt(0) == '#') {
			key = key.substring(1);

			testTagReference(set, key);
		} else {
			testBlockReference(set, key);
		}
	}

	private void testTagReference(ImmutableSet.Builder<Block> set, String key) {
		if (ResourceLocation.isValidResourceLocation(key)) {
			Optional<HolderSet.Named<Block>> tagBlocksIterator = Registry.BLOCK.getTag(TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(key)));

			if (tagBlocksIterator.isPresent()) {
				for (Holder<Block> holder : tagBlocksIterator.get()) {
					set.add(holder.get());
				}
			} else {
				LOGGER.error("Tag reference {} couldn't be found. It will be skipped!", key);
			}
		} else {
			LOGGER.error("Tag reference {} couldn't be recognized as a resource location. It will be skipped!", key);
		}
	}

	private void testBlockReference(ImmutableSet.Builder<Block> set, String key) {
		if (ResourceLocation.isValidResourceLocation(key)) {
			Block block = Registry.BLOCK.get(new ResourceLocation(key));

			if (block != null && block != Blocks.AIR) {
				set.add(block);
			} else {
				LOGGER.error("Block reference {} couldn't be found or it is minecraft:air. It will be skipped!", key);
			}
		} else {
			LOGGER.error("Block reference {} couldn't be recognized as a resource location. It will be skipped!", key);
		}
	}
}
