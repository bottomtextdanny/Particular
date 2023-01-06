package bottomtextdanny.particular.particle;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class MutableSpriteSet implements SpriteSet {
	private List<TextureAtlasSprite> sprites;

	public TextureAtlasSprite get(int p_107413_, int p_107414_) {
		return this.sprites.get(p_107413_ * (this.sprites.size() - 1) / p_107414_);
	}

	public TextureAtlasSprite get(RandomSource p_233889_) {
		return this.sprites.get(p_233889_.nextInt(this.sprites.size()));
	}

	public void rebind(List<TextureAtlasSprite> p_107416_) {
		this.sprites = ImmutableList.copyOf(p_107416_);
	}
}
