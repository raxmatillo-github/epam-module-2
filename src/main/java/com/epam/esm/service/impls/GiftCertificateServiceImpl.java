package com.epam.esm.service.impls;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.epam.esm.exceptions.MyGenericException;
import com.epam.esm.exceptions.MySqlException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.GiftCertificateDAO;
import com.epam.esm.daos.TagDAO;
import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.response.entities.MyNotFoundException;
import com.epam.esm.response.entities.MyResponseBody;
import com.epam.esm.response.entities.MyResponseEntity;
import com.epam.esm.services.GiftCertificateService;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDAO giftCertificateDao;

    @Autowired
    private GiftCertificateAndTagDAO giftCertificateAndTagDao;

    @Autowired
    private TagDAO tagDao;

    @Override
    public List<GiftCertificateDTO> getAllGiftCertificates() {

        List<GiftCertificateDTO> result = new ArrayList<>();

        List<GiftCertificate> giftCertificates = giftCertificateDao.getAllGiftCertificates();

        for (GiftCertificate giftCertificate : giftCertificates) {
            GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
            result.add(populatedGiftCertificateDTO);
        }

        return result;
    }
    @Override
    public GiftCertificateDTO getGiftCertificateById(int id) {

        boolean isExist = giftCertificateDao.isExistByCertificateId(id);
        if (!isExist) {
            throw new MyNotFoundException("Gift Certificate with id: " + id + " is not found to get.");
        }

        GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(id);
        GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
        return populatedGiftCertificateDTO;
    }
    @Override
    public List<GiftCertificateDTO> getAllGiftCertificatesByTagName(String tagName) {
        if(tagName == null){
            throw new MyGenericException("Tag name is required");
        }
        List<GiftCertificateDTO> result = new ArrayList<>();

        int tagId = tagDao.getIdByTagName(tagName);
        List<Integer> giftCertificateIds = giftCertificateAndTagDao.findAllGiftCertificateIdsByTagId(tagId);
        for (int giftCertificateId : giftCertificateIds) {
            GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(giftCertificateId);
            GiftCertificateDTO giftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
            result.add(giftCertificateDTO);
        }
        return result;
    }
    @Override
    public List<GiftCertificateDTO> getAllSortedGiftCertificates(String orderBy, String type) {
        if (orderBy == null) orderBy = "name";
        if (type == null) type = "asc";
        String sql = "select * from gift_certificate order by " + orderBy + " " + type;
        List<GiftCertificateDTO> result = new ArrayList<>();

        List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getAllSortedGiftCertificates(sql);

        for (GiftCertificate giftCertificate : sortedGiftCertificates) {
            GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
            result.add(populatedGiftCertificateDTO);
        }
        return result;
    }
    @Override
    public List<GiftCertificateDTO> getAllGiftCertificatesBySearching(String value){
        if(value == null){
            throw new MyGenericException("Value is required to search");
        }
        List<GiftCertificateDTO> result = new ArrayList<>();
        List<GiftCertificate> giftCertificates = null;
        try {
            giftCertificates = giftCertificateDao.getAllGiftCertificatesBySearching(value);
        } catch (PSQLException e) {
            throw new MySqlException(e.getMessage());
        }
        for(GiftCertificate giftCertificate : giftCertificates){
            GiftCertificateDTO giftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
            result.add(giftCertificateDTO);
        }
        return result;
    }
    private GiftCertificateDTO populateGiftCertificateDTO(GiftCertificate giftCertificate) {

        GiftCertificateDTO result = new GiftCertificateDTO();

        result.setId(giftCertificate.getId());
        result.setName(giftCertificate.getName());
        result.setDescription(giftCertificate.getDescription());
        result.setPrice(giftCertificate.getPrice());
        result.setDuration(giftCertificate.getDuration());
        result.setCreateDate(giftCertificate.getCreateDate());
        result.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        List<Tag> tags = getGiftCertificateTags(giftCertificate.getId());
        result.setTags(tags);
        return result;
    }

    private List<Tag> getGiftCertificateTags(int giftCertificateId) {
        List<Tag> tags = new ArrayList<>();
        List<Integer> tagIds = giftCertificateAndTagDao.findAllTagIdsByGiftCertificateId(giftCertificateId);
        for (Integer tagId : tagIds) {
            Tag tag = tagDao.getTagById(tagId);
            tags.add(tag);
        }
        return tags;
    }

    @Override
    public MyResponseEntity saveGiftCertificate(GiftCertificateDTO giftCertificateDTO) {

        String giftCertificateName = giftCertificateDTO.getName();
        boolean isExist = giftCertificateDao.isExistByCertificateName(giftCertificateName);
        if (isExist) {
            throw new AlreadyExistException("'" + giftCertificateName + "' is already exist");
        }

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateName);
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setCreateDate(getTime());
        giftCertificate.setLastUpdateDate(getTime());

        giftCertificateDao.saveGiftCertificate(giftCertificate);
        int savedGiftCertificateId = giftCertificateDao.getLastSavedGiftCertificate();

        List<Tag> tags = giftCertificateDTO.getTags();
        List<Integer> savedTagIds = getShouldBeSavedTagIds(savedGiftCertificateId, tags);
        for (int savedTagId : savedTagIds) {
            giftCertificateAndTagDao.saveAssosiation(savedGiftCertificateId, savedTagId);
        }
        MyResponseBody responseBody = new MyResponseBody("'" + giftCertificateName + "' is saved successfully", 9999);
        MyResponseEntity responseEntity = new MyResponseEntity(200, responseBody);
        return responseEntity;
    }

    private String getTime() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

    private List<Integer> getShouldBeSavedTagIds(int giftCertificateId, List<Tag> tags) {
        List<Integer> shouldBeSavedTagIds = new ArrayList<>();
        for (Tag tag : tags) {
            boolean isExist = tagDao.isTagExist(tag.getName());
            if (!isExist)
                tagDao.saveTag(tag);
            int tagId = tagDao.getIdByTagName(tag.getName());
            boolean checkAssosiation = giftCertificateAndTagDao.checkAssosiation(giftCertificateId, tagId);
            if (!checkAssosiation)
                shouldBeSavedTagIds.add(tagId);
        }
        return shouldBeSavedTagIds;
    }

    @Override
    public MyResponseEntity updateGiftCertificate(int giftCertificateId, GiftCertificateDTO giftCertificateDto) {
        boolean isExist = giftCertificateDao.isExistByCertificateId(giftCertificateId);
        if (!isExist) {
            throw new MyNotFoundException(
                    "Gift Certificate with id: " + giftCertificateId + " is not found to update.");
        }

        ArrayList<Object> fields = new ArrayList<>();
        String sql = "update gift_certificate set ";
        int sqlLength = sql.length();
        if (giftCertificateDto.getName() != null) {
            sql += "name = ?";
            fields.add(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            if (sql.length() != sqlLength)
                sql += ", ";
            sql += "description = ?";
            fields.add(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != 0) {
            if (sql.length() != sqlLength)
                sql += ", ";
            sql += "price = ?";
            fields.add(giftCertificateDto.getPrice());
        }
        if (sql.length() != sqlLength) {
            sql += ", ";
        }
        if (giftCertificateDto.getTags().size() == 0) {
            throw new MySqlException("No field is added to update");
        }

        sql += " last_update_date = ? where id=?";
        fields.add(getTime());
        fields.add(giftCertificateId);
        Object[] params = fields.toArray();
        giftCertificateDao.updateGiftCertificate(sql, params);

        if (giftCertificateDto.getTags().size() != 0) {
            List<Integer> tagIds = getShouldBeSavedTagIds(giftCertificateId, giftCertificateDto.getTags());
            for (Integer tagId : tagIds) {
                giftCertificateAndTagDao.saveAssosiation(giftCertificateId, tagId);
            }
        }

        MyResponseBody responseBody = new MyResponseBody("Updated successfully", 9999);
        MyResponseEntity responseEntity = new MyResponseEntity(200, responseBody);
        return responseEntity;

    }

    @Override
    public MyResponseEntity deleteGiftCertificate(int id) {
        boolean isExist = giftCertificateDao.isExistByCertificateId(id);
        if (!isExist) {
            throw new MyNotFoundException("Gift Certificate with id: " + id + " is not found to delete.");
        }
        giftCertificateDao.deleteGiftCertificate(id);
        giftCertificateAndTagDao.deleteAllByGiftCertificateId(id);
        MyResponseBody responseBody = new MyResponseBody("Deleted successfully", 9999);
        MyResponseEntity responseEntity = new MyResponseEntity(200, responseBody);
        return responseEntity;
    }

}
