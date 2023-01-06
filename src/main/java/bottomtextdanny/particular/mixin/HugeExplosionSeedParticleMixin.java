package bottomtextdanny.particular.mixin;

import bottomtextdanny.particular.ExplosionHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionSeedParticle;
import net.minecraft.client.particle.NoRenderParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HugeExplosionSeedParticle.class)
public class HugeExplosionSeedParticleMixin extends NoRenderParticle {

	protected HugeExplosionSeedParticleMixin(ClientLevel p_107149_, double p_107150_, double p_107151_, double p_107152_) {
		super(p_107149_, p_107150_, p_107151_, p_107152_);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	protected void tick(CallbackInfo ci) {
		ExplosionHandler.emitterTick(this, level, age, x, y, z, random);
	}
}
