package com.epam.esm.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.response.entities.MyResponseEntity;
import com.epam.esm.services.GiftCertificateService;

import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateController {

	@Autowired
	private GiftCertificateService giftCertificateService;

	@GetMapping
	public List<GiftCertificateDTO> getGiftCertificates() {
		return giftCertificateService.getAllGiftCertificates();
	}

	@GetMapping("/{giftCertificateId}")
	public GiftCertificateDTO getGiftCertificate(@PathVariable int giftCertificateId) {
		return giftCertificateService.getGiftCertificateById(giftCertificateId);
	}
	@GetMapping("/tag")
	public List<GiftCertificateDTO> getAllGiftCertificatesByTagName(@RequestParam String name) {
		return giftCertificateService.getAllGiftCertificatesByTagName(name);
	}

	@GetMapping("/search")
	public List<GiftCertificateDTO> getGiftCertificatesBySearching(@RequestParam(required = false) String value){
		return giftCertificateService.getAllGiftCertificatesBySearching(value);
	}

	@GetMapping("/sort")
	public List<GiftCertificateDTO> getGiftCertificatesBySorting(@RequestParam(required = false) String orderBy,
											   @RequestParam(required = false) String type) {
		return giftCertificateService.getAllSortedGiftCertificates(orderBy, type);
	}
	

	@PostMapping
	public ResponseEntity<MyResponseEntity> saveGiftCertificate(@RequestBody GiftCertificateDTO request) {
		MyResponseEntity responseEntity = giftCertificateService.saveGiftCertificate(request);
		return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
	}

	@PutMapping("/{giftCertificateId}")
	public ResponseEntity<MyResponseEntity> updateGiftCertificate(@PathVariable int giftCertificateId,
			@RequestBody GiftCertificateDTO giftCertificateDto) {
		MyResponseEntity myResponseEntity = giftCertificateService.updateGiftCertificate(giftCertificateId,
				giftCertificateDto);
		return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
	}

	@DeleteMapping("/{giftCertificateId}")
	public ResponseEntity<MyResponseEntity> deleteGiftCertificate(@PathVariable int giftCertificateId) {
		MyResponseEntity myResponseEntity = giftCertificateService.deleteGiftCertificate(giftCertificateId);
		return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
	}

}
