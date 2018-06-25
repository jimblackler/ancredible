package net.jimblackler.ancredible;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultiset;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Main {

  public static void main(String[] args) throws SQLException, OverflowException {

    MysqlDataSource dataSource = Database.getMysqlDataSource();
    Connection connection = dataSource.getConnection();

    Statement statement = connection.createStatement();
    statement.setFetchSize(Integer.MIN_VALUE);
    ResultSet resultSet = statement.executeQuery("SELECT WORD1 FROM PAIRS");

    Map<ByteBuffer, Multiset<String>> matches = Maps.newHashMap();

    while (resultSet.next()) {
      String word1 = resultSet.getString("WORD1");
      if (word1.equals("^")) {
        continue;
      }
      try {
        ByteBuffer letters = ByteBuffer.wrap(Letters.getCharacters(word1));
        matches.computeIfAbsent(letters, k -> HashMultiset.create()).add(word1);
      } catch (OverflowException ex) {
        ex.printStackTrace();
      }
    }
    resultSet.close();
    statement.close();

    //Solver.solve("It was the best of times, it was the worst of times", pairsSorted);
    Solver.solve(Letters.getCharacters("Kingston upon Thames"), matches);

  }
}