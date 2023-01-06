/*
 * Copyright Sunday October 02 of 2022. BottomTextDanny.
 * All rights reserved.
 */

package bottomtextdanny.particular.braincellapi;

import it.unimi.dsi.fastutil.floats.FloatList;

public final class StretchLoopOptions implements ExtraOptions {
	public final short time;
	public FloatList frameSpace;
	public final short start;
	public final short end;
	public final short offset;

	public StretchLoopOptions(int loopTime, int loopStart, int loopEnd, int offset) {
		this.time = (short)loopTime;
		this.start = (short)loopStart;
		this.end = (short)loopEnd;
		this.offset = (short)offset;
	}

	public StretchLoopOptions frameSpace(FloatList frameSpace) {
		this.frameSpace = frameSpace;
		return this;
	}

	public static StretchLoopOptions real(int loopTime) {
		return new StretchLoopOptions(loopTime, 0, Short.MAX_VALUE, 0);
	}

	public static StretchLoopOptions progressive(int loopTime, int loopStart, int loopEnd) {
		return new StretchLoopOptions(loopTime, loopStart, loopEnd, 0);
	}

	public static StretchLoopOptions offset(int loopTime, int offset) {
		return new StretchLoopOptions(loopTime, 0, Short.MAX_VALUE, offset);
	}
}
