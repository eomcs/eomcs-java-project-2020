package com.eomcs.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.ShardingKey;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionProxy implements Connection {

  Connection origin;

  public ConnectionProxy(Connection origin) {
    this.origin = origin;
  }

  public void realClose() throws SQLException {
    System.out.println("DB 연결을 정말 닫는다!");
    origin.close();
  }

  @Override
  public void close() throws SQLException {
    // 커넥션을 닫으라고 해도 닫지 않는다.
    // 별도로 추가한 realClose()호출할 때 진짜로 닫는다.
    System.out.println("내가 닫을 것 같으냐!");
    System.out.println("진짜 닫고 싶다면 realClose()를 호출하거라!");
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return origin.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return origin.isWrapperFor(iface);
  }

  @Override
  public Statement createStatement() throws SQLException {
    return origin.createStatement();
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return origin.prepareStatement(sql);
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    return origin.prepareCall(sql);
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {
    return origin.nativeSQL(sql);
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    origin.setAutoCommit(autoCommit);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return origin.getAutoCommit();
  }

  @Override
  public void commit() throws SQLException {
    origin.commit();
  }

  @Override
  public void rollback() throws SQLException {
    origin.rollback();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return origin.isClosed();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return origin.getMetaData();
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {
    origin.setReadOnly(readOnly);
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return origin.isReadOnly();
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {
    origin.setCatalog(catalog);
  }

  @Override
  public String getCatalog() throws SQLException {
    return origin.getCatalog();
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {
    origin.setTransactionIsolation(level);
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return origin.getTransactionIsolation();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return origin.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    origin.clearWarnings();
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.createStatement(resultSetType, resultSetConcurrency);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return origin.getTypeMap();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    origin.setTypeMap(map);
  }

  @Override
  public void setHoldability(int holdability) throws SQLException {
    origin.setHoldability(holdability);
  }

  @Override
  public int getHoldability() throws SQLException {
    return origin.getHoldability();
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return origin.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    return origin.setSavepoint(name);
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {
    origin.rollback(savepoint);
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    origin.releaseSavepoint(savepoint);
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return origin.prepareStatement(sql, autoGeneratedKeys);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return origin.prepareStatement(sql, columnIndexes);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return origin.prepareStatement(sql, columnNames);
  }

  @Override
  public Clob createClob() throws SQLException {
    return origin.createClob();
  }

  @Override
  public Blob createBlob() throws SQLException {
    return origin.createBlob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return origin.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return origin.createSQLXML();
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return origin.isValid(timeout);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    origin.setClientInfo(name, value);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    origin.setClientInfo(properties);
  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return origin.getClientInfo(name);
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return origin.getClientInfo();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return origin.createArrayOf(typeName, elements);
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return origin.createStruct(typeName, attributes);
  }

  @Override
  public void setSchema(String schema) throws SQLException {
    origin.setSchema(schema);
  }

  @Override
  public String getSchema() throws SQLException {
    return origin.getSchema();
  }

  @Override
  public void abort(Executor executor) throws SQLException {
    origin.abort(executor);
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    origin.setNetworkTimeout(executor, milliseconds);
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return origin.getNetworkTimeout();
  }

  @Override
  public void beginRequest() throws SQLException {
    origin.beginRequest();
  }

  @Override
  public void endRequest() throws SQLException {
    origin.endRequest();
  }

  @Override
  public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey,
      int timeout) throws SQLException {
    return origin.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
  }

  @Override
  public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
    return origin.setShardingKeyIfValid(shardingKey, timeout);
  }

  @Override
  public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey)
      throws SQLException {
    origin.setShardingKey(shardingKey, superShardingKey);
  }

  @Override
  public void setShardingKey(ShardingKey shardingKey) throws SQLException {
    origin.setShardingKey(shardingKey);
  }



}
