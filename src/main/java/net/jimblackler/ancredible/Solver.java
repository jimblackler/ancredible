package net.jimblackler.ancredible;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solver {

  static void solve(String string, Map<String, List<Second>> pairs) {
    PriorityQueue<Result> results =
        new PriorityQueue<>((o1, o2) -> Integer.compare(o1.score(), o2.score()));
    byte[] characters = Letters.getCharacters(string);
    Map<String, List<Second>> scrubbed = Maps.newHashMap();
    for (Map.Entry<String, List<Second>> entry : pairs.entrySet()) {
      String first = entry.getKey();
      if (!Letters.isSubset(Letters.getCharacters(first), characters)) {
        continue;
      }
      List<Second> out = Lists.newArrayList();
      for (Second second : entry.getValue()) {
        if (Letters.isSubset(second.letters(), characters)) {
          out.add(second);
        }
      }
      if (!out.isEmpty()) {

        scrubbed.put(first, out);
      }
    }

    solve(scrubbed, "^", characters, "", 0, results);
  }

  private static void solve(Map<String, List<Second>> pairs,
                            String prior, byte[] remain, String soFar, int score,
                            PriorityQueue<Result> results) {
    if (Letters.count(remain) == 0) {
      if (results.size() == 20) {
        Result lowest = results.peek();
        assert lowest != null;
        if (lowest.score() >= score) {
          return;
        }
        results.remove();
      }
      System.out.printf("%d %s\n", score, soFar);
      results.add(Result.create(score, soFar));
      return;
    }
    List<Second> seconds = pairs.get(prior);
    if (seconds == null) {
      return;
    }
    for (Second second : seconds) {
      byte[] characters = second.letters();
      if (!Letters.isSubset(characters, remain)) {
        continue;
      }

      byte[] remain2 = Letters.difference(remain, characters);
      String prior2 = second.word();
      solve(pairs, prior2, remain2, soFar.isEmpty() ? second.word() : soFar + " " + second.word(),
          score + second.frequency(), results);
    }
  }
}