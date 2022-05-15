package com.epam.esm.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.entities.Tag;
import com.epam.esm.response.entities.MyResponseEntity;
import com.epam.esm.services.TagService;


@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{tagId}")
    public Tag getTag(@PathVariable int tagId) {
        return tagService.getTagById(tagId);
    }

    @PostMapping
    public ResponseEntity<MyResponseEntity> saveTag(@RequestBody Tag tag) {
        MyResponseEntity response = tagService.saveTag(tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<MyResponseEntity> deleteTag(@PathVariable int tagId) {
        MyResponseEntity response = tagService.deleteTag(tagId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
