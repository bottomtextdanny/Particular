package bottomtextdanny.particular;

import bottomtextdanny.particular.braincellapi.MParticle;
import bottomtextdanny.particular.braincellapi.StretchOptions;
import bottomtextdanny.particular.config.SquidInkSplashConfig;
import bottomtextdanny.particular.particle.ModularParticleClientData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.phys.Vec3;

public class SquidSploinkyHandler {
	public static final InkData BLACK = new InkData(0.1F, 0.0F, 0.1F, false);
	public static final InkData GLOW_SQUID = new InkData(0.19999999F, 0.88f, 0.58823526F, true);

	public static void hurt(Squid squid) {

		if (!(squid.level instanceof ClientLevel clientLevel)) {
			return;
		}

		SquidInkSplashConfig config = Particular.config().inkSplash;
		int min = config.min();
		int max = config.max();
		int amount;

		if (max < min) {
			return;
		} else if (max == min) {
			amount = min;
		} else {
			amount = squid.getRandom().nextInt(5, 9);
		}

		InkData type = squid instanceof GlowSquid ? GLOW_SQUID : BLACK;

		for (int i = 0; i < amount; i++) {
			makeDrop(clientLevel, type, squid, squid.getForward().scale(-0.2F));
		}
	}

	private static void makeDrop(ClientLevel level, InkData type, Squid squid, Vec3 direction) {
		int lifeTicks = squid.getRandom().nextInt(20, 30);
		Vec3 motion = new Vec3(squid.getRandom().nextFloat() * 0.6 - 0.3, -1.0, squid.getRandom().nextFloat() * 0.6 - 0.3).xRot(squid.xBodyRotO * ((float)Math.PI / 180.0F));
		motion = motion.yRot(-squid.yBodyRotO * ((float)Math.PI / 180.0F));
		motion = motion.scale((0.3 + (double) (squid.getRandom().nextFloat() * 2.0)) * Particular.config().inkSplash.ejectionStrength());

		inkDropData(type, lifeTicks, squid.getRandom()).addToEngine(level,
			squid.getX(), squid.getY() + direction.y + 0.3, squid.getZ() + direction.z,
			motion.x, motion.y, motion.z);
	}

	public static ModularParticleClientData<StretchOptions> inkDropData(InkData type, int splashLife, RandomSource random) {
		float defSize = random.nextFloat() * 0.03F + 0.2F;

		return new ModularParticleClientData<>(ParticularParticles.DUST,
			particle -> {
				if (type.bright()) {
					particle.setFlags((byte) (MParticle.GLOW_BIT));
				}

				particle.setSize(defSize);
				particle.setLifeTime(40);
				particle.setColor(type.r, type.g, type.b);
				particle.setGravity(2.6F);
				particle.setFriction(0.92F);
			},
			particle -> {
				float size = ((float) particle.getTicks() / (float) particle.getLifeTime()) * 0.75F + 0.25F;

				particle.setSize(defSize * size);

				Direction direction = particle.collidedDirection();

				if (direction != null) {
					Vec3 pos = particle.getPos();

					inkSplashData(type, direction.getStepY() * -90.0F, direction.toYRot(), splashLife, particle.random()).addToEngine(particle.level(),
						pos.x + direction.getStepX() * -0.072, pos.y + direction.getStepY() * -0.072, pos.z + direction.getStepZ() * -0.072,
						0.0, 0.0, 0.0);
					particle.removeParticle();
				}
			});
	}

	public static ModularParticleClientData<StretchOptions> inkSplashData(InkData type, float xRot, float yRot, int life, RandomSource random) {
		float defSize = random.nextFloat() * 0.45F + 0.5F;

		return new ModularParticleClientData<>(ParticularParticles.INK_SPLASH,
			particle -> {
				if (type.bright()) {
					particle.setFlags((byte) (MParticle.CAMERA_DISJUNCTION_BIT | MParticle.IGNORE_CULLING_BIT | MParticle.GLOW_BIT));
				} else {
					particle.setFlags((byte) (MParticle.CAMERA_DISJUNCTION_BIT | MParticle.IGNORE_CULLING_BIT));
				}

				particle.setSize(defSize * 0.2F);
				particle.setXRot(xRot);
				particle.setYRot(yRot);
				particle.setLifeTime(life);
				particle.setColor(type.r, type.g, type.b);
				particle.setFriction(0.0F);
			},
			particle -> {
				if (particle.getTicks() < 5) {
					particle.setSize(((((float) particle.getTicks() / 5.0F) * 0.8F) + 0.2F) * defSize);
				} else {
					particle.setSize(defSize);
				}
			});
	}

	public record InkData(float r, float g, float b, boolean bright) {}
}
