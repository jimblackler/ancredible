package net.jimblackler.ancredible;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

class Database {
  static MysqlDataSource getMysqlDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUser(Secrets.DB_USER);
    dataSource.setPassword(Secrets.DB_PASSWORD);
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("scraped");
    dataSource.setCharacterEncoding("UTF-8");
    dataSource.setAllowMultiQueries(true);
    return dataSource;
  }
}