package net.jimblackler.ancredible;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class Second {
  abstract int frequency();
  abstract String word();
  abstract byte[] letters();

  static Second create(int count, String word, byte[] letters) {
    return new AutoValue_Second(count, word, letters);
  }

}