package com.jagex.runescape;

import com.jagex.runescape.collection.Linkable;
import com.jagex.runescape.sign.signlink;

final class Array {

	private final int length;
	private final Linkable[] array;

	public Array() {
		int size = 1024;// was parameter
		length = size;
		array = new Linkable[size];
		for (int n = 0; n < size; n++) {
			Linkable l = array[n] = new Linkable();
			l.next = l;
			l.previous = l;
		}
	}

	public Linkable get(long id) {
		Linkable l = array[(int) (id & length - 1)];
		for (Linkable l1 = l.next; l1 != l; l1 = l1.next)
			if (l1.id == id)
				return l1;
		return null;
	}

	public void put(Linkable l, long id) {
		try {
			if (l.previous != null)
				l.unlink();
			Linkable l1 = array[(int) (id & length - 1)];
			l.previous = l1.previous;
			l.next = l1;
			l.previous.next = l;
			l.next.previous = l;
			l.id = id;
			return;
		} catch (RuntimeException e) {
			signlink.reporterror("91499, " + l + ", " + id + ", " + (byte) 7 + ", " + e.toString());
		}
		throw new RuntimeException();
	}
}
