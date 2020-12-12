package com.tcs.pricerestapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "price_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "price_id")
	private int priceId;
	private int productId;
	private int priceValue;
}


