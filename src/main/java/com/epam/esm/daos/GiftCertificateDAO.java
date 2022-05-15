package com.epam.esm.daos;

import java.sql.SQLException;
import java.util.List;

import com.epam.esm.entities.GiftCertificate;
import org.postgresql.util.PSQLException;

public interface GiftCertificateDAO {

	public List<GiftCertificate> getAllGiftCertificates();
	public GiftCertificate getGiftCertificateById(int id);
	public List<GiftCertificate> getAllSortedGiftCertificates(String sql);
	public List<GiftCertificate> getAllGiftCertificatesBySearching(String value) throws PSQLException;
	public void saveGiftCertificate(GiftCertificate giftCertificate);

	public void updateGiftCertificate(String sql, Object... params);

	public void deleteGiftCertificate(int id);

	public int getLastSavedGiftCertificate();

	public boolean isExistByCertificateName(String name);
	
	public boolean isExistByCertificateId(int certificateId);


}
