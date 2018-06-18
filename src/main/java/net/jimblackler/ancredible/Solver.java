package net.jimblackler.ancredible;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solver {
  private final Map<String, List<Second>> pairs;

  Solver(Map<String, List<Second>> pairs) {
    this.pairs = pairs;
  }

  void solve(String string) {
    PriorityQueue<Result> results =
        new PriorityQueue<>((o1, o2) -> Integer.compare(o1.score(), o2.score()));
      solve("^", Letters.getCharacters(string), "", 0, results);
  }

  private void solve(String prior, byte[] remain, String soFar, int score,
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
      solve(prior2, remain2, soFar.isEmpty() ? second.word() : soFar + " " + second.word(),
          score + second.frequency(), results);
    }
  }
}