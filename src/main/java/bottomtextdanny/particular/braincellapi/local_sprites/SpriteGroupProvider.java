/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi.local_sprites;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Random;

public class SpriteGroupProvider {
    private final Random RANDOM = new Random();
    private final CustomSpriteGroup[] cachedSpriteGroups;
    private final int[][] groups;
    private final int globalSize;

    private SpriteGroupProvider(int[][] groups, int globalSize) {
        super();
        this.groups = groups;
        this.globalSize = globalSize;
        this.cachedSpriteGroups = new CustomSpriteGroup[groups.length];
    }

    public CustomSpriteGroup get(SpriteSet set, int index) {
        if (index >= this.groups.length || index < 0) {
            throw new IllegalArgumentException("Tried to fetch or generate sprite array with layer out of range, requested layer:" + index + ", for bounds (inclusive): 0-" + (this.groups.length - 1));
        }
        return getRaw(set, index);
    }

    public CustomSpriteGroup fetchRandom(SpriteSet set) {
        return getRaw(set, RANDOM.nextInt(this.groups.length));
    }

    private CustomSpriteGroup getRaw(SpriteSet set, int index) {
        CustomSpriteGroup local = this.cachedSpriteGroups[index];
        return local == null ? createSubSpriteArray(index, set) : local;
    }

    private CustomSpriteGroup createSubSpriteArray(int groupIndex, SpriteSet set) {
        int length = this.groups[groupIndex].length;
        TextureAtlasSprite[] spriteArray = new TextureAtlasSprite[length];
        for (int i = 0; i < length; i++) {
            spriteArray[i] = set.get(this.groups[groupIndex][i], this.globalSize);
        }
        CustomSpriteGroup spriteGroup = new CustomSpriteGroup(spriteArray);
        this.cachedSpriteGroups[groupIndex] = spriteGroup;
        return spriteGroup;
    }

    public static class Builder {
        private final int[][] indicesArray;
        private final int globalSpriteSetSize;

        private Builder(int globalSpriteSetSize, int spriteGroups) {
            this.globalSpriteSetSize = globalSpriteSetSize;
            this.indicesArray = new int[spriteGroups][];
        }

        public static Builder create(int globalSpriteSetSize, int spriteGroups) {
            return new Builder(globalSpriteSetSize, spriteGroups);
        }

        public Builder entry(int groupIndex, int... spriteIndices) {
            this.indicesArray[groupIndex] = spriteIndices;
            return this;
        }

        public SpriteGroupProvider build() {
            return new SpriteGroupProvider(this.indicesArray, this.globalSpriteSetSize - 1);
        }
    }

    public static class SingularBuilder {
        private final int[][] indicesArray;
        private final int globalSpriteSetSize;

        private SingularBuilder(int globalSpriteSetSize) {
            this.globalSpriteSetSize = globalSpriteSetSize;
            this.indicesArray = new int[globalSpriteSetSize][];
        }

        public static SingularBuilder create(int globalSpriteSetSize) {
            return new SingularBuilder(globalSpriteSetSize);
        }

        public SingularBuilder entry(int spriteIndex) {
            this.indicesArray[spriteIndex] = new int[]{spriteIndex};
            return this;
        }

        public SpriteGroupProvider build() {
            return new SpriteGroupProvider(this.indicesArray, this.globalSpriteSetSize - 1);
        }
    }
}
