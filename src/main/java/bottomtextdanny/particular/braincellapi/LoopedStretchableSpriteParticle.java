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
public class LoopedStretchableSpriteParticle extends BaseSpriteParticle<StretchLoopOptions> {
    public int loops;
    public boolean shouldLoopOut;
    public int loopAge;

    public LoopedStretchableSpriteParticle(int sprites, ClientLevel world,
                                              double x, double y, double z,
                                              double xSpeed, double ySpeed, double zSpeed,
                                              ParticleAction<?> start,
                                              ParticleAction<?> ticker,
                                              StretchLoopOptions options, SpriteSet handleSpriteSet) {
        super(sprites, world, x, y, z, xSpeed, ySpeed, zSpeed, start, ticker, options);
        setSpriteGroup(SimpleSpriteGroup.of(handleSpriteSet, sprites));
        setLocalSprite(0);
        loopAge += options.offset;
    }

    @Override
    public void customInit() {}

    @Override
    protected void handleSprite(float lifetime) {
        StretchLoopOptions options = this.options;
        FloatList frameSpace = options.frameSpace;
        float loopTime = (loopAge % options.time);
        float space = loopTime / (float)options.time;
        short start = options.start;

        loopAge++;


        for (int i = 0, frameSpaceLength = frameSpace.size(); i < frameSpaceLength; i++) {
            float v = frameSpace.getFloat(i);

            if (space <= v) {
                if (i > options.end) {
                    if (shouldLoopOut) {
                        setLocalSprite(i);
                    } else {
                        loops++;
                        loopAge = (int) (frameSpace.getFloat(start) * options.time);
                        setLocalSprite(start);
                    }
                } else {
                    setLocalSprite(i);
                }

                if (shouldLoopOut && loopTime == 0) remove();

                break;
            }
        }

    }
}
