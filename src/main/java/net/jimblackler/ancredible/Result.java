
package net.jimblackler.ancredible;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class Result {
  abstract int score();

  abstract String word();

  static Result create(int score, String word) {
    return new AutoValue_Result(score, word);
  }

}