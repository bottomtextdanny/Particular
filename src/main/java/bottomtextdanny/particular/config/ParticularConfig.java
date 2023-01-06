package bottomtextdanny.particular.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ParticularConfig {
	public final CeilingDebrisConfig ceilingDebris;
	public final BlockShineConfig blockShine;
	public final ExplosionConfig explosion;
	public final SquidInkSplashConfig inkSplash;
	public final FirefliesConfig fireflies;
	public final FlyingSoulsConfig flyingSouls;

	public ParticularConfig(ForgeConfigSpec.Builder builder) {
		ceilingDebris = new CeilingDebrisConfig(builder);
		blockShine = new BlockShineConfig(builder);
		explosion = new ExplosionConfig(builder);
		inkSplash = new SquidInkSplashConfig(builder);
		fireflies = new FirefliesConfig(builder);
		flyingSouls = new FlyingSoulsConfig(builder);
	}
}
