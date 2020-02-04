package com.skenons.med;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skenons.med.data.Profile;
import com.skenons.med.repo.IProfileRepo;

//@RunWith(SpringRunner.class)
@SpringBootTest
class MedApplicationTests
{

	@Autowired
	IProfileRepo profiles;

	
	@Test
	public void test()
	{
		System.out.println("Generating profiles...");
		//profiles.save(new Profile("2510996800046","skenons.mail@gmail.com"	,"BoleC0le"	,"password123"	,"Bosko"	,"Vojinovic"	,"0604300501"	,"Pezos Bre 5a"));
		//profiles.save(new Profile("3423235134532","nidza@gmail.com"			,"Nikola1"	,"1233456"		,"Nikola"	,"Petrovic"		,"0613322345"	,"Mrzi Me 1a"));
		//profiles.save(new Profile("2514545432245","ljubaljuba@gmail.com"		,"Ljubica1"	,"ljuba112"		,"Ljubica"	,"Jelicic"		,"0621343134"	,"Mrzi Me 2b"));
		
	}
}
