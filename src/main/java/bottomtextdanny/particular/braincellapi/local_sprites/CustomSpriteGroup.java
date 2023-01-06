/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi.local_sprites;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class CustomSpriteGroup implements SpriteGroup {
    private final TextureAtlasSprite[] spriteArray;

    public CustomSpriteGroup(TextureAtlasSprite[] spriteArray) {
        super();
        this.spriteArray = spriteArray;
    }

    public TextureAtlasSprite fetch(int index) {
        if (index >= this.spriteArray.length || index < 0) {
            throw new IllegalArgumentException("Tried to fetch sprite with layer out of range, requested layer:" + index + ", for bounds (inclusive): 0-" + (this.spriteArray.length - 1));
        }
        return this.spriteArray[index];
    }

    @Override
    public int size() {
        return spriteArray.length;
    }
}
