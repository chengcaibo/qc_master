package com.qichong.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.qichong.model.Image;

@MappedTypes(Image.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ImageHandler extends BaseTypeHandler<Image> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Image parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public Image getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
		// return new Image(rs.getString(columnName));
	}

	@Override
	public Image getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
		// return new Image(rs.getString(columnIndex));
	}

	@Override
	public Image getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
		// return new Image(cs.getString(columnIndex));
	}

}
