package com.epam.esm.service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.TagDAO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.MySqlException;
import com.epam.esm.response.entities.MyNotFoundException;
import com.epam.esm.response.entities.MyResponseBody;
import com.epam.esm.response.entities.MyResponseEntity;
import com.epam.esm.services.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDAO tagDao;

	@Autowired
	private GiftCertificateAndTagDAO gcatDao;


	@Override
	public List<Tag> getAllTags() {
		return tagDao.getAllTags();
	}

	@Override
	public Tag getTagById(int id) {
		if (!tagDao.isTagExist(id)) {
			throw new MyNotFoundException("Tag with id: " + id + " is not found to get");
		}
		return tagDao.getTagById(id);
	}
 
	@Override
	public MyResponseEntity saveTag(Tag tag) {
		if (isTagExist(tag.getName()))
			throw new AlreadyExistException("Tag name is already exist");
		int result = tagDao.saveTag(tag);
		if (result == 0)
			throw new MySqlException("Error occured during saving new Tag.");
		MyResponseBody responseBody = new MyResponseBody("'" + tag.getName() + "' is saved successfully", 9999);
		MyResponseEntity responseEntity = new MyResponseEntity(200, responseBody);
		return responseEntity;
	}

	@Override
	public MyResponseEntity deleteTag(int id) {
		if (!tagDao.isTagExist(id)) {
			throw new MyNotFoundException("Tag with id: " + id + " is not found to delete");
		}
		gcatDao.deleteAllByTagId(id);
		int result = tagDao.deleteTag(id);
		if (result == 0)
			throw new MySqlException("Error occured during deletion Tag.");
		MyResponseBody responseBody = new MyResponseBody("Tag with id: " + id + " is deleted successfully", 9999);
		MyResponseEntity responseEntity = new MyResponseEntity(200, responseBody);
		return responseEntity;
	}

	@Override
	public boolean isTagExist(String tagName) {
		return tagDao.isTagExist(tagName);
	}

}
