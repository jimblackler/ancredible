package net.jimblackler.ancredible;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solver {

  static void solve(byte[] counts, Map<ByteBuffer, Multiset<String>> matches) {
    matches = scrub(counts, matches);
    PriorityQueue<Result> results =
        new PriorityQueue<>((o1, o2) -> Integer.compare(o1.score(), o2.score()));
    List<ByteBuffer> letterCombos = Lists.newArrayList(matches.keySet());
    solve(HashMultiset.create(), letterCombos, 0, counts, matches, results);
  }

  private static Map<ByteBuffer, Multiset<String>> scrub(byte[] counts, Map<ByteBuffer,
      Multiset<String>> matches) {
    Map<ByteBuffer, Multiset<String>> scrubbed = Maps.newHashMap();
    for (Map.Entry<ByteBuffer, Multiset<String>> entry : matches.entrySet()) {
      ByteBuffer first = entry.getKey();
      if (!Letters.isSubset(first.array(), counts)) {
        continue;
      }
      scrubbed.put(first, entry.getValue());
    }
    return scrubbed;
  }

  private static void solve(Multiset<ByteBuffer> soFar, List<ByteBuffer> letterCombos, int idx,
                            byte[] remain, Map<ByteBuffer, Multiset<String>> matches,
                            PriorityQueue<Result> results) {
    if (Letters.count(remain) == 0) {
      int score = 0;
      StringBuffer example = new StringBuffer();
      for (ByteBuffer chars : soFar) {
        Multiset<String> strings = matches.get(chars);
        List<Multiset.Entry<String>> entries = Lists.newArrayList(strings.entrySet());
        Multiset.Entry<String> entry = Collections.max(
            entries, (o1, o2) -> Integer.compare(o1.getCount(), o2.getCount()));
        score += entry.getCount();
        example.append(entry.getElement());
        example.append(" ");
      }
      if (results.size() == 20) {
        Result lowest = results.peek();
        assert lowest != null;
        if (lowest.score() >= score) {
          return;
        }
        results.remove();
      }
      results.add(Result.create(score, soFar));

      System.out.printf("%s (%d)\n", example, score);

      return;
    }
    while (idx < letterCombos.size()) {
      ByteBuffer use = letterCombos.get(idx);
      idx++;

      if (!Letters.isSubset(use.array(), remain)) {
        continue;
      }
      soFar.add(use);
      solve(soFar, letterCombos, idx, Letters.difference(remain, use.array()), matches, results);
      soFar.remove(use);
    }
  }
}