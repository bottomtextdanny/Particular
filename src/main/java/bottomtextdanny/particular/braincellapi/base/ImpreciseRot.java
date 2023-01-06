/*
 * Copyright Friday September 09 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi.base;

import com.mojang.math.Quaternion;
import net.minecraft.util.Mth;

public final class ImpreciseRot {

	public static Quaternion xRot(float rad) {
		rad /= 2.0F;
		return new Quaternion(Mth.sin(rad), 0.0F, 0.0F, Mth.cos(rad));
	}

	public static Quaternion xRotDeg(float degrees) {
		return xRot(degrees * Mth.DEG_TO_RAD);
	}

	public static Quaternion yRot(float rad) {
		rad /= 2.0F;
		return new Quaternion(0.0F, Mth.sin(rad), 0.0F, Mth.cos(rad));
	}

	public static Quaternion yRotDeg(float degrees) {
		return yRot(degrees * Mth.DEG_TO_RAD);
	}

	public static Quaternion zRot(float rad) {
		rad /= 2.0F;
		return new Quaternion(0.0F, 0.0F, Mth.sin(rad), Mth.cos(rad));
	}

	public static Quaternion zRotDeg(float degrees) {
		return zRot(degrees * Mth.DEG_TO_RAD);
	}

	public static Quaternion rot(float xRad, float yRad, float zRad) {
		xRad /= 2.0F;
		yRad /= 2.0F;
		zRad /= 2.0F;
		return new Quaternion(Mth.sin(xRad), Mth.sin(yRad), Mth.sin(zRad), Mth.cos(xRad) * Mth.cos(yRad) * Mth.cos(zRad));
	}

	public static Quaternion rotDeg(float xDeg, float yDeg, float zDeg) {
		return rot(xDeg * Mth.DEG_TO_RAD, yDeg * Mth.DEG_TO_RAD, zDeg * Mth.DEG_TO_RAD);
	}
}
