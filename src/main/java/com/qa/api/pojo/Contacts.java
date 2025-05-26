package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {
	
	private String firstName;
	private String lastName;
	private String birthdate;
	private String email;
	private String phone;
	private String street1;
	private String street2;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String country;

}