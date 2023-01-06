/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi;

import bottomtextdanny.particular.braincellapi.local_sprites.SimpleSpriteGroup;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StretchableSpriteParticle extends BaseSpriteParticle<StretchOptions> {

    public StretchableSpriteParticle(int sprites, ClientLevel world,
                                     double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed,
                                     ParticleAction<?> start, ParticleAction<?> ticker,
                                     StretchOptions options, SpriteSet handleSpriteSet) {
        super(sprites, world, x, y, z, xSpeed, ySpeed, zSpeed, start, ticker, options);
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, sprites));
        setLocalSprite(0);
    }

    @Override
    public void customInit() {}

    @Override
    protected void handleSprite(float lifetime) {
        float space = age / lifetime;
        FloatList frameSpace = options.frameSpace();

        for (int i = 0, frameSpaceLength = frameSpace.size(); i < frameSpaceLength; i++) {
            float v = frameSpace.getFloat(i);
            if (space <= v) {
                setLocalSprite(i);
                break;
            }
        }
    }

    @Override
    public void removeParticle() {
        super.remove();
    }
}
