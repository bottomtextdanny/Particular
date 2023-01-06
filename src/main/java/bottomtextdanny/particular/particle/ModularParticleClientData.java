package bottomtextdanny.particular.particle;

import bottomtextdanny.particular.braincellapi.ExtraOptions;
import bottomtextdanny.particular.braincellapi.ParticleAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModularParticleClientData<E extends ExtraOptions> {
	ModularParticleClient<E> manager;
	public final ParticleAction<?> start;
	public final ParticleAction<?> tick;
	public final E extra;

	@OnlyIn(Dist.CLIENT)
	public ModularParticleClientData(ModularParticleClient<E> type, ParticleAction<?> start, ParticleAction<?> tick) {
		this(type, start, tick, type.defaultOptions.extra);
	}

	@OnlyIn(Dist.CLIENT)
	public ModularParticleClientData(ModularParticleClient<E> type, ParticleAction<?> start, ParticleAction<?> tick, E extra) {
		manager = type;
		this.start = start;
		this.tick = tick;
		this.extra = extra;
	}

	private ModularParticleClientData(ParticleAction<?> start, ParticleAction<?> tick, E extra) {
		this.start = start;
		this.tick = tick;
		this.extra = extra;
	}

	public static <E extends ExtraOptions> ModularParticleClientData<E> defaulted(ParticleAction<?> start, ParticleAction<?> tick, E extra) {
		return new ModularParticleClientData<>(start, tick, extra);
	}

	public void addToEngine(ClientLevel level, double x, double y, double z, double xDelta, double yDelta, double zDelta) {
		Minecraft.getInstance().particleEngine.add(manager.create(this, level, x, y, z, xDelta, yDelta, zDelta));
	}

	public ModularParticleClient<E> getManager() {
		return manager;
	}

	public ParticleAction<?> getStart() {
		return start;
	}

	public ParticleAction<?> getTick() {
		return tick;
	}

	public E getExtra() {
		return extra;
	}
}
