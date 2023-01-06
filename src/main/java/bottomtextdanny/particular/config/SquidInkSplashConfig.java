package bottomtextdanny.particular.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SquidInkSplashConfig {
	private final ForgeConfigSpec.IntValue minParticles;
	private final ForgeConfigSpec.IntValue maxParticles;
	private final ForgeConfigSpec.DoubleValue ejectionStrength;

	public SquidInkSplashConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Squid Ink Splash");
		ejectionStrength = builder.defineInRange("EjectionStrength", 0.8, 0.0, 16.0);
		builder.comment("Particle amount range for each Squid hurt.");
		minParticles = builder.defineInRange("MinParticles", 5, 0, 100000);
		maxParticles = builder.defineInRange("MaxParticles", 9, 0, 100000);
		builder.pop();
	}

	public double ejectionStrength() {
		return ejectionStrength.get().doubleValue();
	}

	public int min() {
		return minParticles.get();
	}

	public int max() {
		return maxParticles.get();
	}
}
