package bottomtextdanny.particular;

import bottomtextdanny.particular.braincellapi.MParticle;
import bottomtextdanny.particular.braincellapi.StretchOptions;
import bottomtextdanny.particular.config.ExplosionConfig;
import bottomtextdanny.particular.particle.ModularParticleClientData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.RandomSource;

public class ExplosionHandler {

	public static void emitterTick(Particle particle, ClientLevel level, int tickCount, double x, double y, double z, RandomSource random) {
		ExplosionConfig explosionConfig = Particular.config().explosion;
		int min = explosionConfig.min();
		int max = explosionConfig.max();
		int amount;

		if (max < min) {
			return;
		} else if (max == min) {
			amount = min;
		} else {
			amount = level.random.nextInt(min, max);
		}

		for(int i = 0; i < amount; ++i) {
			double posX = x + (random.nextDouble() - random.nextDouble()) * 4.0D;
			double posY = y + (random.nextDouble() - random.nextDouble()) * 4.0D;
			double posZ = z + (random.nextDouble() - random.nextDouble()) * 4.0D;
			float rng = random.nextFloat();
			ModularParticleClientData<?> particleData;

			if (rng < 0.5F) {
				particleData = litExplosionData(random.nextInt(8, 12), random);
			} else if (rng < 0.75F) {
				particleData = flashyExplosionData(random.nextInt(7, 10), random);
			} else {
				particleData = shockyExplosionData(random.nextInt(5, 7), random);
			}

			particleData.addToEngine(level, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	public static ModularParticleClientData<StretchOptions> litExplosionData(int life, RandomSource random) {
		return new ModularParticleClientData<>(ParticularParticles.LIT_EXPLOSION,
			particle -> {
			    float decolor = 0.7F + random.nextFloat() * 0.3F;

				particle.setFlags((byte) (MParticle.GLOW_BIT));
				particle.setSize(random.nextFloat() * 0.3F + 0.7F);
				particle.setLifeTime(life);
				particle.setColor(1.0F, decolor, decolor);
			},
			particle -> {
			    if (particle.getTicks() > 3) {
					float decolor = 0.7F + random.nextFloat() * 0.3F;
					particle.setColor(1.0F, decolor, decolor);
				}
			});
	}

	public static ModularParticleClientData<?> flashyExplosionData(int life, RandomSource random) {
		return new ModularParticleClientData<>(ParticularParticles.FLASHY_EXPLOSION,
			particle -> {
				float decolor = 0.8F + random.nextFloat() * 0.3F;

				particle.setFlags((byte) (MParticle.GLOW_BIT));
				particle.setSize(random.nextFloat() * 0.3F + 0.7F);
				particle.setLifeTime(life);
				particle.setColor(decolor, decolor, decolor);
			},
			particle -> {
				float decolor = 0.8F + random.nextFloat() * 0.3F;
				particle.setColor(decolor, decolor, decolor);
			});
	}

	public static ModularParticleClientData<?> shockyExplosionData(int life, RandomSource random) {
		return new ModularParticleClientData<>(ParticularParticles.SHOCKY_EXPLOSION,
			particle -> {
				float decolor = 0.8F + random.nextFloat() * 0.3F;

				particle.setFlags((byte) (MParticle.GLOW_BIT));
				particle.setSize(random.nextFloat() * 0.3F + 0.7F);
				particle.setLifeTime(life);
				particle.setColor(decolor, decolor, decolor);
			},
			particle -> {
				float decolor = 0.8F + random.nextFloat() * 0.3F;
				particle.setColor(decolor, decolor, decolor);
			});
	}
}
