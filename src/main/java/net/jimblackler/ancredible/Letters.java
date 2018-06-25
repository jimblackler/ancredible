package net.jimblackler.ancredible;

public class Letters {
  static byte[] getCharacters(String string) throws OverflowException {
    byte[] counter = new byte[26];

    for (Character c : string.toLowerCase().toCharArray()) {
      if (c >= 'a' && c <= 'z') {
        if (counter[c - 'a'] == Byte.MAX_VALUE) {
          throw new OverflowException();
        }
        counter[c - 'a']++;
      }
    }
    return counter;
  }

  static int count(byte[] values) {
    int count = 0;
    for (int idx = 0; idx != 26; idx++) {
      count += values[idx];
    }
    return count;
  }

  /**
   * Is 'a' a subset of 'b'?
   */
  static boolean isSubset(byte[] a, byte[] b) {
    for (int idx = 0; idx != 26; idx++) {
      if (a[idx] > b[idx]) {
        return false;
      }
    }
    return true;
  }

  /**
   * What is a - b?
   */
  static byte[] difference(byte[] a, byte[] b) {
    byte[] counter = new byte[26];
    for (int idx = 0; idx != 26; idx++) {
      counter[idx] = (byte) (a[idx] - b[idx]);
    }
    return counter;
  }
}
