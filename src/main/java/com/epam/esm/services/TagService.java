package com.epam.esm.services;

import java.util.List;

import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.Tag;
import com.epam.esm.response.entities.MyResponseEntity;

public interface TagService {

	public List<Tag> getAllTags();

	public Tag getTagById(int id);

	public MyResponseEntity saveTag(Tag tag);

	public MyResponseEntity deleteTag(int id);
	
	public boolean isTagExist(String tagName);
}
