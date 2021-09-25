package ru.stonlex.global.database.query;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoteDatabaseQueryResult implements ResultSet {
    
    @NonNull ResultSet resultSet;
    
    @SneakyThrows
    @Override
    public boolean next() {
        return resultSet.next();
    }

    @SneakyThrows
    @Override
    public void close() {
        resultSet.close();
    }

    @SneakyThrows
    @Override
    public boolean wasNull() {
        return resultSet.wasNull();
    }

    @SneakyThrows
    @Override
    public String getString(int columnIndex) {
        return resultSet.getString(columnIndex);
    }

    @SneakyThrows
    @Override
    public boolean getBoolean(int columnIndex) {
        return resultSet.getBoolean(columnIndex);
    }

    @SneakyThrows
    @Override
    public byte getByte(int columnIndex) {
        return resultSet.getByte(columnIndex);
    }

    @SneakyThrows
    @Override
    public short getShort(int columnIndex) {
        return resultSet.getShort(columnIndex);
    }

    @SneakyThrows
    @Override
    public int getInt(int columnIndex) {
        return resultSet.getInt(columnIndex);
    }

    @SneakyThrows
    @Override
    public long getLong(int columnIndex) {
        return resultSet.getLong(columnIndex);
    }

    @SneakyThrows
    @Override
    public float getFloat(int columnIndex) {
        return resultSet.getFloat(columnIndex);
    }

    @SneakyThrows
    @Override
    public double getDouble(int columnIndex) {
        return resultSet.getDouble(columnIndex);
    }

    @SneakyThrows
    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) {
        return resultSet.getBigDecimal(columnIndex, scale);
    }

    @SneakyThrows
    @Override
    public byte[] getBytes(int columnIndex) {
        return resultSet.getBytes(columnIndex);
    }

    @SneakyThrows
    @Override
    public Date getDate(int columnIndex) {
        return resultSet.getDate(columnIndex);
    }

    @SneakyThrows
    @Override
    public Time getTime(int columnIndex) {
        return resultSet.getTime(columnIndex);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(int columnIndex) {
        return resultSet.getTimestamp(columnIndex);
    }

    @SneakyThrows
    @Override
    public InputStream getAsciiStream(int columnIndex) {
        return resultSet.getAsciiStream(columnIndex);
    }

    @SneakyThrows
    @Override
    public InputStream getUnicodeStream(int columnIndex) {
        return resultSet.getUnicodeStream(columnIndex);
    }

    @SneakyThrows
    @Override
    public InputStream getBinaryStream(int columnIndex) {
        return resultSet.getBinaryStream(columnIndex);
    }

    @SneakyThrows
    @Override
    public String getString(String columnLabel) {
        return resultSet.getString(columnLabel);
    }

    @SneakyThrows
    @Override
    public boolean getBoolean(String columnLabel) {
        return resultSet.getBoolean(columnLabel);
    }

    @SneakyThrows
    @Override
    public byte getByte(String columnLabel) {
        return resultSet.getByte(columnLabel);
    }

    @SneakyThrows
    @Override
    public short getShort(String columnLabel) {
        return resultSet.getShort(columnLabel);
    }

    @SneakyThrows
    @Override
    public int getInt(String columnLabel) {
        return resultSet.getInt(columnLabel);
    }

    @SneakyThrows
    @Override
    public long getLong(String columnLabel) {
        return resultSet.getLong(columnLabel);
    }

    @SneakyThrows
    @Override
    public float getFloat(String columnLabel) {
        return resultSet.getFloat(columnLabel);
    }

    @SneakyThrows
    @Override
    public double getDouble(String columnLabel) {
        return resultSet.getDouble(columnLabel);
    }

    @SneakyThrows
    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) {
        return resultSet.getBigDecimal(columnLabel, scale);
    }

    @SneakyThrows
    @Override
    public byte[] getBytes(String columnLabel) {
        return resultSet.getBytes(columnLabel);
    }

    @SneakyThrows
    @Override
    public Date getDate(String columnLabel) {
        return resultSet.getDate(columnLabel);
    }

    @SneakyThrows
    @Override
    public Time getTime(String columnLabel) {
        return resultSet.getTime(columnLabel);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(String columnLabel) {
        return resultSet.getTimestamp(columnLabel);
    }

    @SneakyThrows
    @Override
    public InputStream getAsciiStream(String columnLabel) {
        return resultSet.getAsciiStream(columnLabel);
    }

    @SneakyThrows
    @Override
    public InputStream getUnicodeStream(String columnLabel) {
        return resultSet.getUnicodeStream(columnLabel);
    }

    @SneakyThrows
    @Override
    public InputStream getBinaryStream(String columnLabel) {
        return resultSet.getBinaryStream(columnLabel);
    }

    @SneakyThrows
    @Override
    public SQLWarning getWarnings() {
        return resultSet.getWarnings();
    }

    @SneakyThrows
    @Override
    public void clearWarnings() {
        resultSet.clearWarnings();
    }

    @SneakyThrows
    @Override
    public String getCursorName() {
        return resultSet.getCursorName();
    }

    @SneakyThrows
    @Override
    public ResultSetMetaData getMetaData() {
        return resultSet.getMetaData();
    }

    @SneakyThrows
    @Override
    public Object getObject(int columnIndex) {
        return resultSet.getObject(columnIndex);
    }

    @SneakyThrows
    @Override
    public Object getObject(String columnLabel) {
        return resultSet.getObject(columnLabel);
    }

    @SneakyThrows
    @Override
    public int findColumn(String columnLabel) {
        return resultSet.findColumn(columnLabel);
    }

    @SneakyThrows
    @Override
    public Reader getCharacterStream(int columnIndex) {
        return resultSet.getCharacterStream(columnIndex);
    }

    @SneakyThrows
    @Override
    public Reader getCharacterStream(String columnLabel) {
        return resultSet.getCharacterStream(columnLabel);
    }

    @SneakyThrows
    @Override
    public BigDecimal getBigDecimal(int columnIndex) {
        return resultSet.getBigDecimal(columnIndex);
    }

    @SneakyThrows
    @Override
    public BigDecimal getBigDecimal(String columnLabel) {
        return resultSet.getBigDecimal(columnLabel);
    }

    @SneakyThrows
    @Override
    public boolean isBeforeFirst() {
        return resultSet.isBeforeFirst();
    }

    @SneakyThrows
    @Override
    public boolean isAfterLast() {
        return resultSet.isAfterLast();
    }

    @SneakyThrows
    @Override
    public boolean isFirst() {
        return resultSet.isFirst();
    }

    @SneakyThrows
    @Override
    public boolean isLast() {
        return resultSet.isLast();
    }

    @SneakyThrows
    @Override
    public void beforeFirst() {
        resultSet.beforeFirst();
    }

    @SneakyThrows
    @Override
    public void afterLast() {
        resultSet.afterLast();
    }

    @SneakyThrows
    @Override
    public boolean first() {
        return resultSet.first();
    }

    @SneakyThrows
    @Override
    public boolean last() {
        return resultSet.last();
    }

    @SneakyThrows
    @Override
    public int getRow() {
        return resultSet.getRow();
    }

    @SneakyThrows
    @Override
    public boolean absolute(int row) {
        return resultSet.absolute(row);
    }

    @SneakyThrows
    @Override
    public boolean relative(int rows) {
        return resultSet.relative(rows);
    }

    @SneakyThrows
    @Override
    public boolean previous() {
        return resultSet.previous();
    }

    @SneakyThrows
    @Override
    public void setFetchDirection(int direction) {
        resultSet.setFetchDirection(direction);
    }

    @SneakyThrows
    @Override
    public int getFetchDirection() {
        return resultSet.getFetchDirection();
    }

    @SneakyThrows
    @Override
    public void setFetchSize(int rows) {
        resultSet.setFetchSize(rows);
    }

    @SneakyThrows
    @Override
    public int getFetchSize() {
        return resultSet.getFetchSize();
    }

    @SneakyThrows
    @Override
    public int getType() {
        return resultSet.getType();
    }

    @SneakyThrows
    @Override
    public int getConcurrency() {
        return resultSet.getConcurrency();
    }

    @SneakyThrows
    @Override
    public boolean rowUpdated() {
        return resultSet.rowUpdated();
    }

    @SneakyThrows
    @Override
    public boolean rowInserted() {
        return resultSet.rowInserted();
    }

    @SneakyThrows
    @Override
    public boolean rowDeleted() {
        return resultSet.rowDeleted();
    }

    @SneakyThrows
    @Override
    public void updateNull(int columnIndex) {
        resultSet.updateNull(columnIndex);
    }

    @SneakyThrows
    @Override
    public void updateBoolean(int columnIndex, boolean value) {
        resultSet.updateBoolean(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateByte(int columnIndex, byte value) {
        resultSet.updateByte(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateShort(int columnIndex, short value) {
        resultSet.updateShort(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateInt(int columnIndex, int value) {
        resultSet.updateInt(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateLong(int columnIndex, long value) {
        resultSet.updateLong(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateFloat(int columnIndex, float value) {
        resultSet.updateFloat(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateDouble(int columnIndex, double value) {
        resultSet.updateDouble(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal value) {
        resultSet.updateBigDecimal(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateString(int columnIndex, String value) {
        resultSet.updateString(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateBytes(int columnIndex, byte[] value) {
        resultSet.updateBytes(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateDate(int columnIndex, Date value) {
        resultSet.updateDate(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateTime(int columnIndex, Time value) {
        resultSet.updateTime(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateTimestamp(int columnIndex, Timestamp value) {
        resultSet.updateTimestamp(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(int columnIndex, InputStream inputStream, int length) {
        resultSet.updateAsciiStream(columnIndex, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(int columnIndex, InputStream inputStream, int length) {
        resultSet.updateBinaryStream(columnIndex, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(int columnIndex, Reader reader, int length) {
        resultSet.updateCharacterStream(columnIndex, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateObject(int columnIndex, Object value, int scaleOrLength) {
        resultSet.updateObject(columnIndex, value, scaleOrLength);
    }

    @SneakyThrows
    @Override
    public void updateObject(int columnIndex, Object value) {
        resultSet.updateObject(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateNull(String columnLabel) {
        resultSet.updateNull(columnLabel);
    }

    @SneakyThrows
    @Override
    public void updateBoolean(String columnLabel, boolean value) {
        resultSet.updateBoolean(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateByte(String columnLabel, byte value) {
        resultSet.updateByte(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateShort(String columnLabel, short value) {
        resultSet.updateShort(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateInt(String columnLabel, int value) {
        resultSet.updateInt(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateLong(String columnLabel, long value) {
        resultSet.updateLong(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateFloat(String columnLabel, float value) {
        resultSet.updateFloat(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateDouble(String columnLabel, double value) {
        resultSet.updateDouble(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal value) {
        resultSet.updateBigDecimal(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateString(String columnLabel, String value) {
        resultSet.updateString(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateBytes(String columnLabel, byte[] value) {
        resultSet.updateBytes(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateDate(String columnLabel, Date value) {
        resultSet.updateDate(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateTime(String columnLabel, Time value) {
        resultSet.updateTime(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateTimestamp(String columnLabel, Timestamp value) {
        resultSet.updateTimestamp(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(String columnLabel, InputStream inputStream, int length) {
        resultSet.updateAsciiStream(columnLabel, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(String columnLabel, InputStream inputStream, int length) {
        resultSet.updateBinaryStream(columnLabel, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateObject(String columnLabel, Object value, int scaleOrLength) {
        resultSet.updateObject(columnLabel, value, scaleOrLength);
    }

    @SneakyThrows
    @Override
    public void updateObject(String columnLabel, Object value) {
        resultSet.updateObject(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void insertRow() {
        resultSet.insertRow();
    }

    @SneakyThrows
    @Override
    public void updateRow() {
        resultSet.updateRow();
    }

    @SneakyThrows
    @Override
    public void deleteRow() {
        resultSet.deleteRow();
    }

    @SneakyThrows
    @Override
    public void refreshRow() {
        resultSet.refreshRow();
    }

    @SneakyThrows
    @Override
    public void cancelRowUpdates() {
        resultSet.cancelRowUpdates();
    }

    @SneakyThrows
    @Override
    public void moveToInsertRow() {
        resultSet.moveToInsertRow();
    }

    @SneakyThrows
    @Override
    public void moveToCurrentRow() {
        resultSet.moveToCurrentRow();
    }

    @SneakyThrows
    @Override
    public Statement getStatement() {
        return resultSet.getStatement();
    }

    @SneakyThrows
    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) {
        return resultSet.getObject(columnIndex, map);
    }

    @SneakyThrows
    @Override
    public Ref getRef(int columnIndex) {
        return resultSet.getRef(columnIndex);
    }

    @SneakyThrows
    @Override
    public Blob getBlob(int columnIndex) {
        return resultSet.getBlob(columnIndex);
    }

    @SneakyThrows
    @Override
    public Clob getClob(int columnIndex) {
        return resultSet.getClob(columnIndex);
    }

    @SneakyThrows
    @Override
    public Array getArray(int columnIndex) {
        return resultSet.getArray(columnIndex);
    }

    @SneakyThrows
    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) {
        return resultSet.getObject(columnLabel, map);
    }

    @SneakyThrows
    @Override
    public Ref getRef(String columnLabel) {
        return resultSet.getRef(columnLabel);
    }

    @SneakyThrows
    @Override
    public Blob getBlob(String columnLabel) {
        return resultSet.getBlob(columnLabel);
    }

    @SneakyThrows
    @Override
    public Clob getClob(String columnLabel) {
        return resultSet.getClob(columnLabel);
    }

    @SneakyThrows
    @Override
    public Array getArray(String columnLabel) {
        return resultSet.getArray(columnLabel);
    }

    @SneakyThrows
    @Override
    public Date getDate(int columnIndex, Calendar cal) {
        return resultSet.getDate(columnIndex, cal);
    }

    @SneakyThrows
    @Override
    public Date getDate(String columnLabel, Calendar cal) {
        return resultSet.getDate(columnLabel, cal);
    }

    @SneakyThrows
    @Override
    public Time getTime(int columnIndex, Calendar cal) {
        return resultSet.getTime(columnIndex, cal);
    }

    @SneakyThrows
    @Override
    public Time getTime(String columnLabel, Calendar cal) {
        return resultSet.getTime(columnLabel, cal);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) {
        return resultSet.getTimestamp(columnIndex, cal);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) {
        return resultSet.getTimestamp(columnLabel, cal);
    }

    @SneakyThrows
    @Override
    public URL getURL(int columnIndex) {
        return resultSet.getURL(columnIndex);
    }

    @SneakyThrows
    @Override
    public URL getURL(String columnLabel) {
        return resultSet.getURL(columnLabel);
    }

    @SneakyThrows
    @Override
    public void updateRef(int columnIndex, Ref value) {
        resultSet.updateRef(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateRef(String columnLabel, Ref value) {
        resultSet.updateRef(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateBlob(int columnIndex, Blob value) {
        resultSet.updateBlob(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateBlob(String columnLabel, Blob value) {
        resultSet.updateBlob(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateClob(int columnIndex, Clob value) {
        resultSet.updateClob(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateClob(String columnLabel, Clob value) {
        resultSet.updateClob(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateArray(int columnIndex, Array value) {
        resultSet.updateArray(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateArray(String columnLabel, Array value) {
        resultSet.updateArray(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public RowId getRowId(int columnIndex) {
        return resultSet.getRowId(columnIndex);
    }

    @SneakyThrows
    @Override
    public RowId getRowId(String columnLabel) {
        return resultSet.getRowId(columnLabel);
    }

    @SneakyThrows
    @Override
    public void updateRowId(int columnIndex, RowId value) {
        resultSet.updateRowId(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateRowId(String columnLabel, RowId value) {
        resultSet.updateRowId(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public int getHoldability() {
        return resultSet.getHoldability();
    }

    @SneakyThrows
    @Override
    public boolean isClosed() {
        return resultSet.isClosed();
    }

    @SneakyThrows
    @Override
    public void updateNString(int columnIndex, String nString) {
        resultSet.updateNString(columnIndex, nString);
    }

    @SneakyThrows
    @Override
    public void updateNString(String columnLabel, String nString) {
        resultSet.updateNString(columnLabel, nString);
    }

    @SneakyThrows
    @Override
    public void updateNClob(int columnIndex, NClob nClob) {
        resultSet.updateNClob(columnIndex, nClob);
    }

    @SneakyThrows
    @Override
    public void updateNClob(String columnLabel, NClob nClob) {
        resultSet.updateNClob(columnLabel, nClob);
    }

    @SneakyThrows
    @Override
    public NClob getNClob(int columnIndex) {
        return resultSet.getNClob(columnIndex);
    }

    @SneakyThrows
    @Override
    public NClob getNClob(String columnLabel) {
        return resultSet.getNClob(columnLabel);
    }

    @SneakyThrows
    @Override
    public SQLXML getSQLXML(int columnIndex) {
        return resultSet.getSQLXML(columnIndex);
    }

    @SneakyThrows
    @Override
    public SQLXML getSQLXML(String columnLabel) {
        return resultSet.getSQLXML(columnLabel);
    }

    @SneakyThrows
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) {
        resultSet.updateSQLXML(columnIndex, xmlObject);
    }

    @SneakyThrows
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) {
        resultSet.updateSQLXML(columnLabel, xmlObject);
    }

    @SneakyThrows
    @Override
    public String getNString(int columnIndex) {
        return resultSet.getNString(columnIndex);
    }

    @SneakyThrows
    @Override
    public String getNString(String columnLabel) {
        return resultSet.getNString(columnLabel);
    }

    @SneakyThrows
    @Override
    public Reader getNCharacterStream(int columnIndex) {
        return resultSet.getNCharacterStream(columnIndex);
    }

    @SneakyThrows
    @Override
    public Reader getNCharacterStream(String columnLabel) {
        return resultSet.getNCharacterStream(columnLabel);
    }

    @SneakyThrows
    @Override
    public void updateNCharacterStream(int columnIndex, Reader reader, long length) {
        resultSet.updateNCharacterStream(columnIndex, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) {
        resultSet.updateNCharacterStream(columnLabel, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(int columnIndex, InputStream inputStream, long length) {
        resultSet.updateAsciiStream(columnIndex, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(int columnIndex, InputStream inputStream, long length) {
        resultSet.updateBinaryStream(columnIndex, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(int columnIndex, Reader reader, long length) {
        resultSet.updateCharacterStream(columnIndex, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(String columnLabel, InputStream inputStream, long length) {
        resultSet.updateAsciiStream(columnLabel, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(String columnLabel, InputStream inputStream, long length) {
        resultSet.updateBinaryStream(columnLabel, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) {
        resultSet.updateBlob(columnIndex, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) {
        resultSet.updateBlob(columnLabel, inputStream, length);
    }

    @SneakyThrows
    @Override
    public void updateClob(int columnIndex, Reader reader, long length) {
        resultSet.updateClob(columnIndex, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateClob(String columnLabel, Reader reader, long length) {
        resultSet.updateClob(columnLabel, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) {
        resultSet.updateNClob(columnIndex, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) {
        resultSet.updateNClob(columnLabel, reader, length);
    }

    @SneakyThrows
    @Override
    public void updateNCharacterStream(int columnIndex, Reader value) {
        resultSet.updateNCharacterStream(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) {
        resultSet.updateNCharacterStream(columnLabel, reader);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(int columnIndex, InputStream value) {
        resultSet.updateAsciiStream(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(int columnIndex, InputStream value) {
        resultSet.updateBinaryStream(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(int columnIndex, Reader value) {
        resultSet.updateCharacterStream(columnIndex, value);
    }

    @SneakyThrows
    @Override
    public void updateAsciiStream(String columnLabel, InputStream value) {
        resultSet.updateAsciiStream(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateBinaryStream(String columnLabel, InputStream value) {
        resultSet.updateBinaryStream(columnLabel, value);
    }

    @SneakyThrows
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) {
        resultSet.updateCharacterStream(columnLabel, reader);
    }

    @SneakyThrows
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) {
        resultSet.updateBlob(columnIndex, inputStream);
    }

    @SneakyThrows
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) {
        resultSet.updateBlob(columnLabel, inputStream);
    }

    @SneakyThrows
    @Override
    public void updateClob(int columnIndex, Reader reader) {
        resultSet.updateClob(columnIndex, reader);
    }

    @SneakyThrows
    @Override
    public void updateClob(String columnLabel, Reader reader) {
        resultSet.updateClob(columnLabel, reader);
    }

    @SneakyThrows
    @Override
    public void updateNClob(int columnIndex, Reader reader) {
        resultSet.updateNClob(columnIndex, reader);
    }

    @SneakyThrows
    @Override
    public void updateNClob(String columnLabel, Reader reader) {
        resultSet.updateNClob(columnLabel, reader);
    }

    @SneakyThrows
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) {
        return resultSet.getObject(columnIndex, type);
    }

    @SneakyThrows
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) {
        return resultSet.getObject(columnLabel, type);
    }

    @SneakyThrows
    @Override
    public <T> T unwrap(Class<T> typeClass) {
        return resultSet.unwrap(typeClass);
    }

    @SneakyThrows
    @Override
    public boolean isWrapperFor(Class<?> typeClass) {
        return resultSet.isWrapperFor(typeClass);
    }

}
