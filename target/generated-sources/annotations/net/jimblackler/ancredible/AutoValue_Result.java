

package net.jimblackler.ancredible;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Result extends Result {

  private final int score;
  private final String word;

  AutoValue_Result(
      int score,
      String word) {
    this.score = score;
    if (word == null) {
      throw new NullPointerException("Null word");
    }
    this.word = word;
  }

  @Override
  int score() {
    return score;
  }

  @Override
  String word() {
    return word;
  }

  @Override
  public String toString() {
    return "Result{"
         + "score=" + score + ", "
         + "word=" + word
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Result) {
      Result that = (Result) o;
      return (this.score == that.score())
           && (this.word.equals(that.word()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= score;
    h$ *= 1000003;
    h$ ^= word.hashCode();
    return h$;
  }

}
