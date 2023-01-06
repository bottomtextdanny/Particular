/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi.local_sprites;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface SpriteGroup {

    TextureAtlasSprite fetch(int index);

    int size();
}
