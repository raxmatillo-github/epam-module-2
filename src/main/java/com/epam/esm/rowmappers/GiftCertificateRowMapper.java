package com.epam.esm.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.entities.GiftCertificate;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

	@Override
	public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

		GiftCertificate giftCertificate = new GiftCertificate();
		giftCertificate.setId(rs.getInt("id"));
		giftCertificate.setName(rs.getString("name"));
		giftCertificate.setDescription(rs.getString("description"));
		giftCertificate.setPrice(rs.getDouble("price"));
		giftCertificate.setDuration(rs.getInt("duration"));
		giftCertificate.setCreateDate(rs.getString("create_date"));
		giftCertificate.setLastUpdateDate(rs.getString("last_update_date"));

		return giftCertificate;
	}

}
