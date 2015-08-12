package com.hunantv.fw.utils;

import java.util.Random;

public final class SeqID implements java.io.Serializable, Comparable<SeqID> {

	/*
	 * The most significant 64 bits of this UUID.
	 * 
	 * @serial
	 */
	private final long mostSigBits;
	/*
	 * The least significant 64 bits of this UUID.
	 * 
	 * @serial
	 */
	private final long leastSigBits;

	/*
	 * The random number generator used by this class to create random based
	 * UUIDs. In a holder class to defer initialization until needed.
	 */
	private static class Holder {
		static final Random numberGenerator = new Random();
	}

	// Constructors and Factories

	/*
	 * Private constructor which uses a byte array to construct the new UUID.
	 */
	private SeqID(byte[] data) {
		long msb = 0;
		long lsb = 0;
		assert data.length == 16 : "data must be 16 bytes in length";
		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (data[i] & 0xff);
		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (data[i] & 0xff);
		this.mostSigBits = msb;
		this.leastSigBits = lsb;
	}

	public static SeqID rnd() {
		Random ng = Holder.numberGenerator;
		byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		randomBytes[6] &= 0x0f; /* clear version */
		randomBytes[6] |= 0x40; /* set to version 4 */
		randomBytes[8] &= 0x3f; /* clear variant */
		randomBytes[8] |= 0x80; /* set to IETF variant */
		return new SeqID(randomBytes);
	}

	public String toString() {
		return (digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4) + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(leastSigBits, 12));
	}

	/** Returns val represented by the specified number of hex digits. */
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}

	public int hashCode() {
		long hilo = mostSigBits ^ leastSigBits;
		return ((int) (hilo >> 32)) ^ (int) hilo;
	}

	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != SeqID.class))
			return false;
		SeqID id = (SeqID) obj;
		return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
	}

	public int compareTo(SeqID val) {
		// The ordering is intentionally set up so that the UUIDs
		// can simply be numerically compared as two numbers
		return (this.mostSigBits < val.mostSigBits ? -1 : (this.mostSigBits > val.mostSigBits ? 1 : (this.leastSigBits < val.leastSigBits ? -1
				: (this.leastSigBits > val.leastSigBits ? 1 : 0))));
	}
}
