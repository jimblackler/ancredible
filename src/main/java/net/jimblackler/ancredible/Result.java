
package net.jimblackler.ancredible;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Multiset;

import java.nio.ByteBuffer;

@AutoValue
abstract class Result {
  abstract int score();

  abstract Multiset<ByteBuffer> combos();

  static Result create(int score, Multiset<ByteBuffer> combos) {
    return new AutoValue_Result(score, combos);
  }

}