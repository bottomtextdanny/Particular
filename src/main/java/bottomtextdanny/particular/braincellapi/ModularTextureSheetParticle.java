/*
 * Copyright Saturday October 01 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ModularTextureSheetParticle<E extends ExtraOptions> extends TextureSheetParticle implements MParticle {
	private static final double MAXIMUM_COLLISION_VELOCITY_SQUARED = Mth.square(100.0D);

	protected final int sprites;
	protected final ParticleAction<?> ticker;
	protected final ParticleAction<?> start;
	public final E options;
	@Nullable
	protected Direction collisionDirection;
	protected Vec3 collisionPosition;
	protected int collisionDirectionUpdateTime;
	private boolean stoppedByCollision;
	public byte flags;
	public float xRotO;
	public float yRotO;
	public float xRot;
	public float yRot;
	public float sizeO;

	protected ModularTextureSheetParticle(int sprites, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ParticleAction start, ParticleAction ticker, E options) {
		super(world, x, y, z, xSpeed, ySpeed, zSpeed);
		this.sprites = sprites;
		this.ticker = ticker;
		this.start = start;
		this.options = options;
		init(xSpeed, ySpeed, zSpeed);
	}

	protected void init(double xDelta, double yDelta, double zDelta) {
		xRotO = 0.0F;
		yRotO = 0.0F;
		oRoll = 0.0F;
		xRot = 0.0F;
		yRot = 0.0F;
		roll = 0.0F;
		sizeO = 0.0F;
		rCol = 1.0F;
		gCol = 1.0F;
		bCol = 1.0F;
		alpha = 1.0F;
		xd = xDelta;
		yd = yDelta;
		zd = zDelta;
		start._execute(this);
		customInit();
	}

	public abstract void customInit();

	@Override
	public void tick() {
		xRotO = xRot;
		yRotO = yRot;
		oRoll = roll;
		sizeO = quadSize;

		super.tick();

		ticker._execute(this);
	}

	@Override
	public void move(double xDelta, double yDelta, double zDelta) {
		if (getFlag(IGNORE_COLLISION)) setPos(getPos().add(xDelta, yDelta, zDelta));
		else reflectedMove(xDelta, yDelta, zDelta);
	}

	public void reflectedMove(double p_107246_, double p_107247_, double p_107248_) {
		if (!this.stoppedByCollision) {
			double d0 = p_107246_;
			double d1 = p_107247_;
			double d2 = p_107248_;
			if (this.hasPhysics && (p_107246_ != 0.0D || p_107247_ != 0.0D || p_107248_ != 0.0D) && p_107246_ * p_107246_ + p_107247_ * p_107247_ + p_107248_ * p_107248_ < MAXIMUM_COLLISION_VELOCITY_SQUARED) {
				Vec3 vec3 = collideBoundingBox(new Vec3(p_107246_, p_107247_, p_107248_), this.getBoundingBox(), this.level, List.of());
				p_107246_ = vec3.x;
				p_107247_ = vec3.y;
				p_107248_ = vec3.z;
			}

			if (p_107246_ != 0.0D || p_107247_ != 0.0D || p_107248_ != 0.0D) {
				this.setBoundingBox(this.getBoundingBox().move(p_107246_, p_107247_, p_107248_));
				this.setLocationFromBoundingbox();
			}

			if (Math.abs(d1) >= (double)1.0E-5F && Math.abs(p_107247_) < (double)1.0E-5F) {
				this.stoppedByCollision = true;
			}

			this.onGround = d1 != p_107247_ && d1 < 0.0D;
			if (d0 != p_107246_) {
				this.xd = 0.0D;
			}

			if (d2 != p_107248_) {
				this.zd = 0.0D;
			}

		}
	}

	public Vec3 collideBoundingBox(Vec3 delta, AABB boundingBox, Level level, List<VoxelShape> collidables) {
		ImmutableList.Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(collidables.size() + 1);
		if (!collidables.isEmpty()) {
			builder.addAll(collidables);
		}

		builder.addAll(level.getBlockCollisions(null, boundingBox.expandTowards(delta)));

		CollisionData collisionData = collideWithShapes(delta, boundingBox, builder.build());

		if (collisionDirection == null || collisionDirectionUpdateTime != age) {
			collisionDirectionUpdateTime = age;
			collisionDirection = collisionData.direction;
		}

		return collisionData.position;
	}

	private static CollisionData collideWithShapes(Vec3 delta, AABB collisionBox, List<VoxelShape> collidables) {
		if (collidables.isEmpty()) {
			return new CollisionData(delta, null);
		} else {
			double deltaX = delta.x;
			double deltaY = delta.y;
			double deltaZ = delta.z;
			double collisionInput;
			Direction direction = null;
			Direction yDirection = null;

			if (deltaY != 0.0D) {
				collisionInput = Shapes.collide(Direction.Axis.Y, collisionBox, collidables, deltaY);
				if (collisionInput != 0.0D) {
					if (collisionInput != deltaY) {
						if (deltaY >= 0.0) {
							yDirection = Direction.UP;
						} else {
							yDirection = Direction.DOWN;
						}
					}

					deltaY = collisionInput;
					collisionBox = collisionBox.move(0.0D, deltaY, 0.0D);
				} else {
					deltaY = 0.0F;
				}
			}

			boolean zLean = Math.abs(deltaX) < Math.abs(deltaZ);

			if (zLean && deltaZ != 0.0D) {
				collisionInput = Shapes.collide(Direction.Axis.Z, collisionBox, collidables, deltaZ);

				if (collisionInput != 0.0D) {
					if (collisionInput != deltaZ) {
						if (deltaZ >= 0.0) {
							direction = Direction.NORTH;
						} else {
							direction = Direction.SOUTH;
						}
					}

					deltaZ = collisionInput;
					collisionBox = collisionBox.move(0.0D, 0.0D, deltaZ);
				} else {
					deltaZ = 0.0F;
				}
			}

			if (deltaX != 0.0D) {
				collisionInput = Shapes.collide(Direction.Axis.X, collisionBox, collidables, deltaX);

				if (!zLean && collisionInput != 0.0D) {
					if (collisionInput != deltaX) {
						if (deltaX >= 0.0) {
							direction = Direction.EAST;
						} else {
							direction = Direction.WEST;
						}
					}

					deltaX = collisionInput;
					collisionBox = collisionBox.move(deltaX, 0.0D, 0.0D);
				} else {
					deltaX = 0.0F;
				}
			}

			if (!zLean && deltaZ != 0.0D) {
				collisionInput = Shapes.collide(Direction.Axis.Z, collisionBox, collidables, deltaZ);

				if (collisionInput != 0.0D) {
					if (direction == null && collisionInput != deltaZ) {
						if (deltaZ >= 0.0) {
							direction = Direction.NORTH;
						} else {
							direction = Direction.SOUTH;
						}
					}
				}

				deltaZ = collisionInput;
			}

			return new CollisionData(new Vec3(deltaX, deltaY, deltaZ), direction == null ? yDirection : direction);
		}
	}

	private record CollisionData(Vec3 position, @Nullable Direction direction) {}

	@Override
	protected int getLightColor(float tickOffset) {
		return getFlag(GLOW) ? 15728880 : super.getLightColor(tickOffset);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return getFlag(TRANSLUCENT) ? ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT : ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public boolean shouldCull() {
		return !getFlag(IGNORE_CULLING);
	}

	@Override
	public Vec3 getPos() {
		return new Vec3(x, y, z);
	}

	@Override
	public Vec3 getPosO() {
		return new Vec3(xo, yo, zo);
	}

	@Override
	public void setDelta(double x, double y, double z) {
		xd = x;
		yd = y;
		zd = z;
	}

	@Override
	public Vec3 getDelta() {
		return new Vec3(xd, yd, zd);
	}

	@Override
	public void setXRot(float value) {
		xRot = value;
	}

	@Override
	public float getXRot() {
		return xRot;
	}

	@Override
	public void setYRot(float value) {
		yRot = value;
	}

	@Override
	public float getYRot() {
		return yRot;
	}

	@Override
	public void setZRot(float value) {
		roll = value;
	}

	@Override
	public float getZRot() {
		return roll;
	}

	@Override
	public void setR(float value) {
		rCol = value;
	}

	@Override
	public float getR() {
		return rCol;
	}

	@Override
	public void setG(float value) {
		gCol = value;
	}

	@Override
	public float getG() {
		return gCol;
	}

	@Override
	public void setB(float value) {
		bCol = value;
	}

	@Override
	public float getB() {
		return bCol;
	}

	@Override
	public void setA(float value) {
		alpha = value;
	}

	@Override
	public float getA() {
		return alpha;
	}

	@Override
	public ParticleAction start() {
		return start;
	}

	@Override
	public ParticleAction ticker() {
		return ticker;
	}

	@Override
	public ClientLevel level() {
		return level;
	}

	@Override
	public RandomSource random() {
		return random;
	}

	@Override
	public void setLifeTime(int value) {
		lifetime = value;
	}

	@Override
	public int getLifeTime() {
		return lifetime;
	}

	@Override
	public void setTicks(int value) {
		age = value;
	}

	@Override
	public int getTicks() {
		return age;
	}

	@Override
	public boolean onGround() {
		return onGround;
	}

	@Override
	public void setPhysics(boolean value) {
		hasPhysics = value;
	}

	@Override
	public boolean hasPhysics() {
		return hasPhysics;
	}

	@Override
	public void setFriction(float value) {
		friction = value;
	}

	@Override
	public float getFriction() {
		return friction;
	}

	@Override
	public void setSize(float value) {
		quadSize = value;
	}

	@Override
	public float getSize() {
		return quadSize;
	}

	@Override
	public void setFlags(byte value) {
		flags = value;
	}

	@Override
	public byte getFlags() {
		return flags;
	}

	@Override
	public void setGravity(float value) {
		gravity = value;
	}

	@Override
	public float getGravity() {
		return gravity;
	}

	@Override
	public Direction collidedDirection() {
		return collisionDirection;
	}

	@Override
	public int sprites() {
		return sprites;
	}

	@Override
	public void removeParticle() {
		super.remove();
	}
}
