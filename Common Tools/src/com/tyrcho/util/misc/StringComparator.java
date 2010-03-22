package com.tyrcho.util.misc;

import java.util.Comparator;

/**
 * Compare 2 chaines en fonction d'un parametre qui définit l'ordre
 * alphabétique.
 */
public class StringComparator implements Comparator<String> {
	private String languageOrder;

	public StringComparator(String languageOrder) {
		this.languageOrder = languageOrder;
	}

	public int compare(String o1, String o2) {
		int len1 = 0;
		int len2 = 0;
		char v1[] = new char[o1.length()];
		char v2[] = new char[o2.length()];
		for (char c1 : o1.toCharArray()) {
			if (languageOrder.indexOf(c1) >= 0) {
				v1[len1++] = c1;
			}
		}
		for (char c2 : o2.toCharArray()) {
			if (languageOrder.indexOf(c2) >= 0) {
				v2[len2++] = c2;
			}
		}
		int k = 0;
		int n = Math.min(len1, len2);
		while (k < n) {
			char c1 = v1[k];
			char c2 = v2[k];
			if (c1 != c2) {
				int i1 = languageOrder.indexOf(c1);
				int i2 = languageOrder.indexOf(c2);
				if (i1 >= 0 && i2 >= 0) {
					return i1 - i2;
				}
				return c1 - c2;
			}
			k++;
		}
		return len1 - len2;
	}

}
