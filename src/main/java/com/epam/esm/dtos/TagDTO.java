package com.epam.esm.dtos;

import java.util.ArrayList;
import java.util.List;

import com.epam.esm.entities.GiftCertificate;

public class TagDTO {

	private String name;
	private List<GiftCertificate> giftCertificates = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GiftCertificate> getGiftCertificates() {
		return giftCertificates;
	}

	public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
		this.giftCertificates = giftCertificates;
	}

}
