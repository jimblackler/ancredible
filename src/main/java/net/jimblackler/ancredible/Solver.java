package net.jimblackler.ancredible;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;


import java.util.Comparator;
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
    solve("^", getCharacters(string), "", 0, results);
  }

  private static Multiset<Character> getCharacters(String string) {
    Multiset<Character> counter = HashMultiset.create();

    for (Character c : string.toLowerCase().toCharArray()) {
      if (c >= 'a' && c <= 'z') {
        counter.add(c);
      }
    }
    return counter;
  }

  private void solve(String prior, Multiset<Character> remain, String soFar, int score,
                     PriorityQueue<Result> results) {
    if (remain.isEmpty()) {
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
      Multiset<Character> characters = getCharacters(second.word());
      if (!isSubset(characters, remain)) {
        continue;
      }

      Multiset<Character> remain2 = Multisets.difference(remain, characters);
      String prior2 = second.word();
      solve(prior2, remain2, soFar.isEmpty() ? second.word() : soFar + " " + second.word(),
          score + second.frequency(), results);
    }

  }

  private static boolean isSubset(Multiset<Character> a, Multiset<Character> b) {
    for (Multiset.Entry<Character> entry : a.entrySet()) {
      if (b.count(entry.getElement()) < entry.getCount()) {
        return false;
      }
    }
    return true;
  }
}