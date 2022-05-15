package com.epam.esm.dao.impls;

import java.sql.SQLException;
import java.util.List;

import com.epam.esm.exceptions.MySqlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.daos.GiftCertificateDAO;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.rowmappers.GiftCertificateRowMapper;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificates() {
		String sql = "select * from gift_certificate";
		List<GiftCertificate> list = jdbcTemplate.query(sql, new GiftCertificateRowMapper());
		return list;
	}
	@Override
	public GiftCertificate getGiftCertificateById(int id) {
		String sql = "select * from gift_certificate where id=?";
		GiftCertificate giftCertificate = jdbcTemplate.queryForObject(sql, new GiftCertificateRowMapper(), id);
		return giftCertificate;
	}
	@Override
	public List<GiftCertificate> getAllSortedGiftCertificates(String sql){
		List<GiftCertificate> list = jdbcTemplate.query(sql, new GiftCertificateRowMapper());
		return list;
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificatesBySearching(String value){
		String sql = "select * from gift_certificate where name like ? or description like ? ";
		List<GiftCertificate> list = jdbcTemplate.query(sql, new GiftCertificateRowMapper(), new Object[]{"%"+value+"%", "%"+value+"%"});
		return list;
	}

	@Override
	public void saveGiftCertificate(GiftCertificate giftCertificate) {
		String sql = "insert into gift_certificate(name, description, price, duration, create_date, last_update_date) values(?,?,?,?,?,?)";
		jdbcTemplate.update(sql, giftCertificate.getName(), giftCertificate.getDescription(),
				giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
	}

	@Override
	public void updateGiftCertificate(String sql, Object... params) {
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void deleteGiftCertificate(int id) {
		String sql = "delete from gift_certificate where id=?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public int getLastSavedGiftCertificate() {
		String sql = "select id from gift_certificate order by 1 desc limit 1";
		Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
		return id;
	}

	@Override
	public boolean isExistByCertificateName(String name) {
		String sql = "SELECT EXISTS(SELECT FROM gift_certificate WHERE name = ?)";
		return jdbcTemplate.queryForObject(sql, Boolean.class, name);
	}

	@Override
	public boolean isExistByCertificateId(int certificateId) {
		String sql = "SELECT EXISTS(SELECT FROM gift_certificate WHERE id = ?)";
		return jdbcTemplate.queryForObject(sql, Boolean.class, certificateId);
	}

}
