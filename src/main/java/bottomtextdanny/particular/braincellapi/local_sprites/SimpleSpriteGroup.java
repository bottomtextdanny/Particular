/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi.local_sprites;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SimpleSpriteGroup implements SpriteGroup {
    private final SpriteSet set;
    private final int size;

    private SimpleSpriteGroup(SpriteSet spriteSet, int size) {
        this.set = spriteSet;
        this.size = size;
    }

    public static SimpleSpriteGroup of(SpriteSet spriteSet, int size) {
        return new SimpleSpriteGroup(spriteSet, Math.max(size - 1, 1));
    }

    @Override
    public TextureAtlasSprite fetch(int index) {
        return this.set.get(index, this.size);
    }

    @Override
    public int size() {
        return size;
    }
}
