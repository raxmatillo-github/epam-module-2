package epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.TagDAO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.MySqlException;
import com.epam.esm.response.entities.MyNotFoundException;
import com.epam.esm.response.entities.MyResponseEntity;
import com.epam.esm.service.impls.TagServiceImpl;

public class TagServiceTest {

	@Mock
	TagDAO dao;

	@Mock
	GiftCertificateAndTagDAO gcat;

	@InjectMocks
	private TagServiceImpl service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// Get all Tags

	@Test
	public void getAllTagsSuccess() {
		when(dao.getAllTags()).thenReturn(new ArrayList<>());
		List<Tag> allTags = service.getAllTags();
		assertNotNull(allTags);
		assertEquals(0, allTags.size());
	}

//	Get Tag by id

	// success
	@Test
	public void getTagByIdSuccess() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(true);
		when(dao.getTagById(tagId)).thenReturn(new Tag(1, "Test"));
		Tag tag = service.getTagById(1);
		assertEquals(1, tag.getId());
		assertEquals("Test", tag.getName());
	}

	// Fail
	@Test
	public void getTagByIdShouldThrowException() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(false);
		assertThrows(MyNotFoundException.class, () -> {
			service.getTagById(tagId);
		});
	}

	// Save new Tag

	@Test
	public void saveTagSuccess() {
		Tag tag = new Tag(1, "Test");
		when(dao.isTagExist(tag.getName())).thenReturn(false);
		when(dao.saveTag(tag)).thenReturn(1);
		MyResponseEntity entity = service.saveTag(tag);
		assertEquals(200, entity.getHttpStatus());
	}

	@Test
	public void saveTagShouldThrowAlreadyExistException() {
		Tag tag = new Tag(1, "Test");
		when(dao.isTagExist(tag.getName())).thenReturn(true);
		assertThrows(AlreadyExistException.class, () -> {
			service.saveTag(tag);
		});
	}

	@Test
	public void saveTagShouldThrowMySqlException() {
		Tag tag = new Tag(1, "Test");
		when(dao.isTagExist(tag.getName())).thenReturn(false);
		when(dao.saveTag(tag)).thenReturn(0);
		assertThrows(MySqlException.class, () -> {
			service.saveTag(tag);
		});
	}

	// Delete Tag

	@Test
	public void deleteTagSuccess() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(true);
		when(gcat.deleteAllByTagId(tagId)).thenReturn(1);
		when(dao.deleteTag(tagId)).thenReturn(1);
		MyResponseEntity resultEntity = service.deleteTag(tagId);
		assertNotNull(resultEntity);
		assertEquals(200, resultEntity.getHttpStatus());
	}

	@Test
	public void deleteTagShouldThrowMyNotFoundException() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(false);
		assertThrows(MyNotFoundException.class, () -> {
			service.deleteTag(tagId);
		});
	}

	@Test
	public void deleteTagShouldThrowMySqlException() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(true);
		when(gcat.deleteAllByTagId(tagId)).thenReturn(0);
		assertThrows(MySqlException.class, () -> {
			service.deleteTag(tagId);
		});
	}

	@Test
	public void deleteTagShouldThrowMySqlException2() {
		int tagId = 1;
		when(dao.isTagExist(tagId)).thenReturn(true);
		when(gcat.deleteAllByTagId(tagId)).thenReturn(1);
		when(dao.deleteTag(tagId)).thenReturn(0);
		assertThrows(MySqlException.class, () -> {
			service.deleteTag(tagId);
		});
	}

	// is Tag exist

	@Test
	public void isTagExistSuccessTrue() {
		String tagName = "Test";
		when(dao.isTagExist(tagName)).thenReturn(true);
		assertTrue(service.isTagExist(tagName));
	}

	@Test
	public void isTagExistSuccessFalse() {
		String tagName = "Test";
		when(dao.isTagExist(tagName)).thenReturn(false);
		assertFalse(service.isTagExist(tagName));
	}

}
