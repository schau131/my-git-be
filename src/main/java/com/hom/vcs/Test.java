package com.hom.vcs;

import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		HashMap<Key, String> a = new HashMap<>();
		a.put(new Key(), "1");
		a.put(new Key(), "2");
		a.put(new Key(), "3");
		
		Key key1 = new Key();
		Key key2 = new Key();
		Key key3 = new Key();
		
		System.out.println(key1 == key2);
		System.out.println(key1.equals(key2));
		
		System.out.println(a.get(new Key()));
	}
}


class Key {
	
	@Override
	public int hashCode() {
		return 1;
	}
}