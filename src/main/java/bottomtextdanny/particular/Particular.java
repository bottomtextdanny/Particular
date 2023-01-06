package bottomtextdanny.particular;

import bottomtextdanny.particular.config.ParticularConfig;
import bottomtextdanny.particular.particle.ParticleHandlers;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod(Particular.ID)
public class Particular {
    public static final String ID = "particular";
    private static Object particleHandlers;
    private static Object config;

    public Particular() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> Particular::setupClient);
    }

    @OnlyIn(Dist.CLIENT)
    private static void setupClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        particleHandlers = new ParticleHandlers(modEventBus, List.of(
            ParticularParticles.DUST,
            ParticularParticles.CLOUD,
            ParticularParticles.HASTY_SMOKE,
            ParticularParticles.SHINE,
            ParticularParticles.FLASHY_EXPLOSION,
            ParticularParticles.LIT_EXPLOSION,
            ParticularParticles.SHOCKY_EXPLOSION,
            ParticularParticles.SOUL,
            ParticularParticles.LITTLE_SOUL,
            ParticularParticles.INK_SPLASH
        ));

        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        config = new ParticularConfig(configBuilder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configBuilder.build());
    }

    @OnlyIn(Dist.CLIENT)
    public static ParticularConfig config() {
        return (ParticularConfig) config;
    }
}
