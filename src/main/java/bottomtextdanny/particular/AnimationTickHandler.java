package bottomtextdanny.particular;

import bottomtextdanny.particular.braincellapi.*;
import bottomtextdanny.particular.braincellapi.base.BCVectors;
import bottomtextdanny.particular.config.CeilingDebrisConfig;
import bottomtextdanny.particular.config.ParticularConfig;
import bottomtextdanny.particular.particle.ModularParticleClientData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;


public class AnimationTickHandler {

	public static void animateTick(Block block, BlockState state, ClientLevel level,
								   BlockPos pos, RandomSource random) {
		Block blockBelow = level.getBlockState(pos.below()).getBlock();

		if (shouldSpawnDebris(block, blockBelow))
			trySpawnDebris(state, level, pos, random);

		if (canOreShine(block)) {
			Direction dir = oreShineDirection(level, pos, random);

			if (dir != null)
				trySpawnShine(block, level, pos, random, dir);
		}

		if (shouldSpawnFireflies(block)) {
			Vector3f fireflyColor = getFireflyColor(level, pos);

			if (fireflyColor != null) {
				trySpawnFirefly(state, level, pos, random, fireflyColor);
			}
		}

		if (shouldSpawnSouls(block)) {
			trySpawnSoul(state, level, pos, random);
		}
	}

	public static boolean shouldSpawnDebris(Block block, Block blockBelow) {
		return blockBelow == Blocks.AIR && Particular.config().ceilingDebris.appliedBlocks().contains(block);
	}

	public static boolean canOreShine(Block block) {
		return Particular.config().blockShine.appliedBlocks().contains(block);
	}

	public static Direction oreShineDirection(Level level, BlockPos pos, RandomSource random) {
		Direction dir = Direction.values()[random.nextInt(Direction.values().length)];

		if (!level.getBlockState(pos.relative(dir)).getMaterial().isSolid()) return dir;
		return null;
	}

	public static boolean shouldSpawnFireflies(Block block) {
		return Particular.config().fireflies.appliedBlocks().contains(block);
	}

	@Nullable
	private static Vector3f getFireflyColor(ClientLevel level, BlockPos pos) {
		Holder<Biome> biome = level.getBiome(pos);

		if (biome.is(BiomeTags.IS_OVERWORLD)) {
			return new Vector3f(1.0F, 0.9F, 0.1F);
		} else if (biome.is(Biomes.SOUL_SAND_VALLEY)) {
			return new Vector3f(0.2F, 0.9F, 0.9F);
		} else if (biome.is(Biomes.CRIMSON_FOREST)) {
			return new Vector3f(1.0F, 0.1F, 0.2F);
		} else {
			return null;
		}
	}

	public static boolean shouldSpawnSouls(Block block) {
		return Particular.config().flyingSouls.appliedBlocks().contains(block);
	}

	public static void trySpawnDebris(BlockState state, ClientLevel level,
									  BlockPos pos, RandomSource random) {
		CeilingDebrisConfig config = Particular.config().ceilingDebris;
		int min = config.min();
		int max = config.max();

		if (random.nextFloat() > config.chance() || (max < min)) return;

		int particleAmount = max == min ? min : random.nextInt(min, max);
		Vec3 centered = Vec3.atBottomCenterOf(pos);

		for (int i = 0; i < particleAmount; i++) {
			level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state),
				centered.x + random.nextGaussian() * 0.2,
				centered.y - 0.2 - random.nextFloat() * 0.3F,
				centered.z + random.nextGaussian() * 0.2,
				0.0,
				-random.nextFloat() * 0.09F,
				0.0);
		}
	}

	public static void trySpawnShine(Block block, ClientLevel level, BlockPos pos,
									 RandomSource random, Direction direction) {
		if (random.nextFloat() > Particular.config().blockShine.chance()) return;

		Vec3 shinePos = Vec3.atCenterOf(pos)
			.add(Vec3.atLowerCornerOf(direction.getNormal()).scale(0.75))
			.add(BCVectors.randomAdjacent(direction.getAxis(), random));

		ModularParticleClientData<StretchOptions> particleData = oreShineData(random.nextInt(10, 20), 1.0F, 0.95F, 0.8F, random);

		particleData.addToEngine(level,
			shinePos.x,
			shinePos.y,
			shinePos.z,
			random.nextGaussian() * 0.01,
			random.nextGaussian() * 0.01,
			random.nextGaussian() * 0.01);
	}

	public static void trySpawnFirefly(BlockState state, ClientLevel level,
									  BlockPos pos, RandomSource random, Vector3f color) {
		if (random.nextFloat() > Particular.config().fireflies.chance()) return;

		Vec3 fireflyPos = Vec3.atBottomCenterOf(pos)
			.add(BCVectors.randBorder3(random).scale(0.8));

		lanternFireflyData(random.nextInt(50, 80), color.x(), color.y(), color.z(), random).addToEngine(
			level,
			fireflyPos.x,
			fireflyPos.y,
			fireflyPos.z,
			0.0,
			0.0,
			0.0
		);
	}

	public static void trySpawnSoul(BlockState state, ClientLevel level,
									BlockPos pos, RandomSource random) {
		if (random.nextFloat() > Particular.config().flyingSouls.chance()) return;

		Vec3 fireflyPos = Vec3.atCenterOf(pos)
			.add(BCVectors.randomAdjacent(Direction.Axis.Y, random));

		ModularParticleClientData<StretchLoopOptions> particle;

		if (random.nextFloat() < 0.4F) {
			particle = soulOptions(random.nextInt(1, 10), random);
		} else {
			particle = littleSoulOptions(random.nextInt(1, 7), random);
		}

		particle.addToEngine(
			level,
			fireflyPos.x,
			fireflyPos.y + 0.5F,
			fireflyPos.z,
			0.0,
			0.05,
			0.0
		);
	}

	public static ModularParticleClientData<StretchOptions> oreShineData(int life, float r, float g, float b, RandomSource random) {
		int zMotionSignum = random.nextBoolean() ? 1 : -1;
		return new ModularParticleClientData<>(ParticularParticles.SHINE,
			particle -> {
				particle.setFlags((byte) (MParticle.GLOW_BIT | MParticle.TRANSLUCENT_BIT));
				particle.setColor(r, g, b);
				particle.setA(0.0F);
				particle.setLifeTime(life);
			},
			particle -> {
				int ticks = particle.getTicks();
				int lifeTime = particle.getLifeTime();
				float prog = (float)ticks / (float) lifeTime;
				float invProg = 1.0F - prog;

				if (prog > 0.4F && prog < 0.6F)
					particle.setColor(1.0F, 1.0F, 1.0F);
				else particle.setColor(r, g, b);

				if (prog > 0.5F) {
					particle.addZRot(40.0F * zMotionSignum * invProg);
					particle.setA(invProg * 2.0F);
				} else {
					particle.setA(prog * 2.0F);
					particle.addZRot(40.0F * zMotionSignum * prog);
				}
			});
	}

	public static ModularParticleClientData<StretchOptions> lanternFireflyData(int life, float r, float g, float b, RandomSource random) {
		float offX = random.nextFloat() * Mth.PI;
		float offY = random.nextFloat() * Mth.PI;
		float offZ = random.nextFloat() * Mth.PI;

		return new ModularParticleClientData<>(ParticularParticles.DUST,
			(StretchableSpriteParticle particle) -> {
				particle.flags = (byte) (MParticle.GLOW_BIT | MParticle.TRANSLUCENT_BIT);
				particle.setColor(r, g, b);
				particle.setA(0.0F);
				particle.setSize(random.nextFloat() * 0.01F + 0.02F);
				particle.setLifeTime(life);
				particle.setPhysics(false);
			},
			(StretchableSpriteParticle particle) -> {
				int ticks = particle.getTicks();
				int lifeTime = particle.getLifeTime();
				float prog = (float)ticks / (float) lifeTime;
				float lapse = ticks * 0.25F;

				if (prog < 0.2F) {
					particle.setA(prog * 5.0F);
				} else if (prog > 0.8F) {
					particle.setA((1.0F - prog) * 5.0F);
				} else {
					particle.setA(1.0F);
				}

				float cMag = Mth.sin(lapse) * 0.45F + 0.55F;

				particle.setColor(cMag * r, cMag * g, cMag * b);

				particle.addDelta(Mth.sin(offX + lapse) * 0.006, Mth.sin(offY + lapse) * 0.006, Mth.sin(offZ + lapse) * 0.006);
			});
	}

	public static ModularParticleClientData<StretchLoopOptions> soulOptions(int loops, RandomSource random) {

		return new ModularParticleClientData<>(ParticularParticles.SOUL,
			(LoopedStretchableSpriteParticle particle) -> {
				particle.setSize(random.nextFloat() * 0.22F + 0.17F);
				particle.setLifeTime(500);
				particle.setA(random.nextFloat() * 0.4F + 0.6F);
				particle.setPhysics(false);
				particle.setFriction(1.0F);
				particle.flags = (byte) (MParticle.GLOW_BIT | MParticle.TRANSLUCENT_BIT);
			},
			(LoopedStretchableSpriteParticle particle) -> {
				if (particle.loops > loops) particle.shouldLoopOut = true;
				particle.setZRot(Mth.sin(particle.getTicks() * 0.25F) * 20.0F);
			}, StretchLoopOptions.progressive(random.nextInt(28, 36), 2, 10));
	}

	public static ModularParticleClientData<StretchLoopOptions> littleSoulOptions(int loops, RandomSource random) {

		return new ModularParticleClientData<>(ParticularParticles.LITTLE_SOUL,
			(LoopedStretchableSpriteParticle particle) -> {
				particle.setSize(random.nextFloat() * 0.22F + 0.17F);
				particle.setLifeTime(500);
				particle.setA(random.nextFloat() * 0.4F + 0.6F);
				particle.setPhysics(false);
				particle.setFriction(1.0F);
				particle.flags = (byte) (MParticle.GLOW_BIT | MParticle.TRANSLUCENT_BIT);
			},
			(LoopedStretchableSpriteParticle particle) -> {
				if (particle.loops > loops) particle.shouldLoopOut = true;
				particle.setZRot(Mth.sin(particle.getTicks() * 0.25F) * 20.0F);
			}, StretchLoopOptions.progressive(random.nextInt(28, 36), 2, 11));
	}
}
