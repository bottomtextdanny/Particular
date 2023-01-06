package bottomtextdanny.particular.particle;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;

public class ParticleHandlers {
	private final ImmutableList<ModularParticleClient<?>> particleManagers;

	public ParticleHandlers(IEventBus bus, List<ModularParticleClient<?>> particleManagers) {
		this.particleManagers = ImmutableList.copyOf(particleManagers);
		bus.addListener(this::onTextureAtlasPre);
		bus.addListener(this::onTextureAtlasPost);
	}

	private void onTextureAtlasPre(TextureStitchEvent.Pre event){
		System.out.println("Stitching particle textures");
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_PARTICLES)) {
			particleManagers.forEach(manager -> {
				manager.textures.forEach(event::addSprite);
			});
		}
	}

	private void onTextureAtlasPost(TextureStitchEvent.Post event){
		System.out.println("Fetching particle textures");
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_PARTICLES)) {
			particleManagers.forEach(manager -> {
				manager.spriteSet.rebind(manager.textures.stream().map(loc -> event.getAtlas().getSprite(loc)).toList());
			});
		}
	}
}
