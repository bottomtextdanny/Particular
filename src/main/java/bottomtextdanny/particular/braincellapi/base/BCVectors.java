package bottomtextdanny.particular.braincellapi.base;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public final class BCVectors {

    public static Vec2 randBorder2(RandomSource random) {
        if (random.nextBoolean()) {
            return new Vec2(random.nextFloat() - 0.5F, random.nextBoolean() ? 0.5F : -0.5F);
        }
        return new Vec2(random.nextBoolean() ? 0.5F : -0.5F, random.nextFloat() - 0.5F);
    }

    public static Vec3 randBorder3(RandomSource random) {
        switch (random.nextInt(3)) {
            case 0: return new Vec3(random.nextFloat() - 0.5, random.nextFloat() - 0.5, random.nextBoolean() ? 0.5 : -0.5);
            case 1: return new Vec3(random.nextFloat() - 0.5, random.nextBoolean() ? 0.5 : -0.5, random.nextFloat() - 0.5);
            case 2: return new Vec3(random.nextBoolean() ? 0.5 : -0.5, random.nextFloat() - 0.5, random.nextFloat() - 0.5);
        }

        throw new IllegalStateException("Random output is out of bounds!");
    }

    public static Vec3 rotate(Vec3 vec3, Direction direction) {
        switch (direction) {
            case NORTH: return vec3;
            case SOUTH: return new Vec3(-vec3.x, vec3.y, -vec3.z);
            case WEST: return new Vec3(-vec3.z, vec3.y, vec3.x);
            case EAST: return new Vec3(vec3.z, vec3.y, -vec3.x);
            case DOWN: return new Vec3(vec3.x, -vec3.z, vec3.y);
            case UP: return new Vec3(vec3.x, vec3.z, -vec3.y);
            default: throw new IllegalStateException("Unhandled Direction! | " + direction);
        }
    }

    public static Vec3 randomAdjacent(Direction.Axis axis, RandomSource random) {
        switch (axis) {
            case Z: return new Vec3(random.nextFloat() - 0.5, random.nextFloat() - 0.5, 0.0);
            case Y: return new Vec3(random.nextFloat() - 0.5, 0.0, random.nextFloat() - 0.5);
            case X: return new Vec3(0.0, random.nextFloat() - 0.5, random.nextFloat() - 0.5);
            default: throw new IllegalStateException("Unhandled Axis! | " + axis);
        }
    }

    private BCVectors() {}
}
