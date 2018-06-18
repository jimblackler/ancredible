package net.jimblackler.ancredible;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class Main {

  public static void main(String[] args) throws SQLException {

    MysqlDataSource dataSource = Database.getMysqlDataSource();
    Connection connection = dataSource.getConnection();

    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT WORD1, WORD2 FROM PAIRS");

    Map<String, Multiset<String>> pairs = Maps.newHashMap();
    while (resultSet.next()) {
      String word1 = resultSet.getString("WORD1");
      String word2 = resultSet.getString("WORD2");
      Multiset<String> seconds = pairs.get(word1);
      if (seconds == null) {
        seconds = TreeMultiset.create();
        pairs.put(word1, seconds);
      }
      seconds.add(word2);
    }
    resultSet.close();
    statement.close();

    Map<String, List<Second>> pairsSorted = Maps.newHashMap();
    for (Map.Entry<String, Multiset<String>> entry : pairs.entrySet()) {
      List<Second> secondsOut = Lists.newArrayList();
      Multiset<String> seconds = entry.getValue();
      for (Multiset.Entry<String> entry2 : seconds.entrySet()) {
        secondsOut.add(Second.create(entry2.getCount(), entry2.getElement()));
      }
      Collections.sort(secondsOut, new Comparator<Second>() {
        public int compare(Second o1, Second o2) {
          return Integer.valueOf(o2.frequency()).compareTo(o1.frequency());
        }
      });
      pairsSorted.put(entry.getKey(), secondsOut);
    }

    Solver solver = new Solver(pairsSorted);
    solver.solve("It was the best of times, it was the worst of times");

  }
}