package net.jimblackler.ancredible;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class Second {
  abstract int frequency();

  abstract String word();

  static Second create(int count, String word) {
    return new AutoValue_Second(count, word);
  }

}