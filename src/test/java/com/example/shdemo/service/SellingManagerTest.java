package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Pick;
import com.example.shdemo.domain.Zamowienie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager sellingManager;

	private final static String COMPANY_1 = "Dunlop";
	private final static String NAME_1 = "Big Stubby";
	private final static double MILIMETERS_1 = 1.0;
	private final static double PRICE_1 = 2.38;

	private final static String COMPANY_2 = "Twin Picks";
	private final static String NAME_2 = "Acoustic Hard";
	private final static double MILIMETERS_2 = 0.71;
	private final static double PRICE_2 = 8.20;

	private final static String CUSTOMER_1 = "RocknRoll";
	private final static String CUSTOMERID_1 = "10";

	private final static String CUSTOMER_2 = "Riff";
	private final static String CUSTOMERID_2 = "15";
	
	@Test
	public void addZamowienieCheck() {

		List<Zamowienie> retrievedZamowienia = sellingManager.getAllZamowienie();

		// Czyszczenie
		for (Zamowienie zamowienie : retrievedZamowienia) {
			if (zamowienie.getCustomerID().equals(CUSTOMERID_1)) {
				sellingManager.deleteZamowienie(zamowienie);
			}
		}

		Zamowienie zamowienie = new Zamowienie();
		zamowienie.setCustomer(CUSTOMER_1);
		zamowienie.setCustomerID(CUSTOMERID_1);
		// ... other properties here

		// Pin is Unique
		sellingManager.addZamowienie(zamowienie);

		Zamowienie retrievedZamowienie = sellingManager.findByCustomerID(CUSTOMERID_1);

		assertEquals(CUSTOMER_1, retrievedZamowienie.getCustomer());
		assertEquals(CUSTOMERID_1, retrievedZamowienie.getCustomerID());
		// ... check other properties here
	}

	@Test
	public void addPickCheck() {

		Pick pick = new Pick();
		pick.setCompany(COMPANY_1);
		pick.setName(NAME_1);
		pick.setMilimeters(MILIMETERS_1);
		pick.setPrice(PRICE_1);

		Long pickId = sellingManager.addNewPick(pick);
		
		//Sprawdzam czy dodanie zadzialalo
		List<Pick> allPicks = sellingManager.getAllPick();
		assertEquals(1, allPicks.size());

		//Sprawdzam czy dodano poprawnie
		Pick retrievedPick = sellingManager.findPickById(pickId);
		assertEquals(COMPANY_1, retrievedPick.getCompany());
		assertEquals(NAME_1, retrievedPick.getName());

	}

//	@Test
//	public void sellPickCheck() {
//
//		Zamowienie zamowienie = new Zamowienie();
//		zamowienie.setCustomer(CUSTOMER_2);
//		zamowienie.setCustomerID(CUSTOMERID_2);
//
//		sellingManager.addZamowienie(zamowienie);
//
//		Zamowienie retrievedZamowienie = sellingManager.findByCustomerID(CUSTOMERID_2);
//
//		Pick pick = new Pick();
//		pick.setCompany(COMPANY_2);
//		pick.setName(NAME_2);
//		pick.setMilimeters(MILIMETERS_2);
//		pick.setPrice(PRICE_2);
//
//		Long pickId = sellingManager.addNewPick(pick);
//
//		sellingManager.sellPick(retrievedZamowienie.getId(), pickId);
//
//		List<Pick> soldPicks = sellingManager.getSoldPicks(retrievedZamowienie);
//
//		assertEquals(1, soldPicks.size());
//		assertEquals(COMPANY_2, soldPicks.get(0).getCompany());
//		assertEquals(NAME_2, soldPicks.get(0).getName());
//	}

	@Test
	public void deletePickCheck() {
		
		Pick pick = new Pick();
		Pick pick2 = new Pick();
		
		pick.setCompany(COMPANY_1);
		pick.setName(NAME_1);
		pick.setMilimeters(MILIMETERS_1);
		pick.setPrice(PRICE_1);
		
		pick2.setCompany(COMPANY_2);
		pick2.setName(NAME_2);
		pick2.setMilimeters(MILIMETERS_2);
		pick2.setPrice(PRICE_2);

		Long pickId = sellingManager.addNewPick(pick);
		sellingManager.addNewPick(pick2);
		
		List<Pick> allPicks = sellingManager.getAllPick();
		int pom = allPicks.size();
		
		//Sprawdzam czy na pewno usunieto dobry element
		sellingManager.deletePickID(pickId);
		assertEquals(null, sellingManager.findZamowienieById(pickId));
		
		//Sprawdam, czy nie usunieto wszytskiego
		allPicks = sellingManager.getAllPick();
		assertEquals(pom-1, allPicks.size());
		
	}
	
	@Test
	public void deleteZamowienieCheck() {
		
		Zamowienie zamowienie = new Zamowienie();
		Zamowienie zamowienie2 = new Zamowienie();
		
		zamowienie.setCustomer(CUSTOMER_1);
		zamowienie.setCustomerID(CUSTOMERID_1);
		
		zamowienie2.setCustomer(CUSTOMER_2);
		zamowienie2.setCustomerID(CUSTOMERID_2);

		Long zamowienieId = sellingManager.addNewZamowienie(zamowienie);
		sellingManager.addNewZamowienie(zamowienie2);
		
		List<Zamowienie> allZamowienia = sellingManager.getAllZamowienie();
		int pom = allZamowienia.size();
		
		//Sprawdzam czy na pewno usunieto dobry element
		sellingManager.deleteZamowienieID(zamowienieId);
		assertEquals(null, sellingManager.findZamowienieById(zamowienieId));
		
		//Sprawdam, czy nie usunieto wszytskiego
		allZamowienia = sellingManager.getAllZamowienie();
		assertEquals(pom-1, allZamowienia.size());
		
	}
	
	@Test
	public void pickListCheck(){
		
		Pick pick = new Pick();
		Pick pick2 = new Pick();
		
		pick.setCompany(COMPANY_1);
		pick.setName(NAME_1);
		pick.setMilimeters(MILIMETERS_1);
		pick.setPrice(PRICE_1);
		
		pick2.setCompany(COMPANY_2);
		pick2.setName(NAME_2);
		pick2.setMilimeters(MILIMETERS_2);
		pick2.setPrice(PRICE_2);
		
		sellingManager.addNewPick(pick);
		sellingManager.addNewPick(pick2);
		
		List<Pick> allPicks = sellingManager.getAllPick();
		assertEquals(2, allPicks.size());
		
	}
	
	@Test
	public void zamowieniaListCheck(){
		
		Zamowienie zamowienie = new Zamowienie();
		Zamowienie zamowienie2 = new Zamowienie();
		
		zamowienie.setCustomer(CUSTOMER_1);
		zamowienie.setCustomerID(CUSTOMERID_1);
		
		zamowienie2.setCustomer(CUSTOMER_2);
		zamowienie2.setCustomerID(CUSTOMERID_2);
		
		sellingManager.addNewZamowienie(zamowienie);
		sellingManager.addNewZamowienie(zamowienie2);
		
		List<Zamowienie> allZamowienia = sellingManager.getAllZamowienie();
		assertEquals(2, allZamowienia.size());
		
	}
	
	@Test
	public void pickEditNameCheck(){
		
		Pick pick = new Pick();
		Pick pick2 = new Pick();
		
		pick.setCompany(COMPANY_1);
		pick.setName(NAME_1);
		pick.setMilimeters(MILIMETERS_1);
		pick.setPrice(PRICE_1);
		
		pick2.setCompany(COMPANY_2);
		pick2.setName(NAME_2);
		pick2.setMilimeters(MILIMETERS_2);
		pick2.setPrice(PRICE_2);
		
		Long pickId = sellingManager.addNewPick(pick);
		Long pickId2 = sellingManager.addNewPick(pick2);
		
		String newName = "test";
		
		sellingManager.editPickName(pickId, newName);
		
		//Sprawdzam czy zmieniono poprawnie nazwe
		Pick retrievedPick = sellingManager.findPickById(pickId);
		assertEquals(newName, retrievedPick.getName());
		
		//Sprawdzam czy nie zmieniono nazwy drugiego rekordu
		retrievedPick = sellingManager.findPickById(pickId2);
		assertEquals(NAME_2, retrievedPick.getName());
		
	}
	
	@Test
	public void zamowienieEditCustomerCheck(){
		
		Zamowienie zamowienie = new Zamowienie();
		Zamowienie zamowienie2 = new Zamowienie();
		
		zamowienie.setCustomer(CUSTOMER_1);
		zamowienie.setCustomerID(CUSTOMERID_1);
		
		zamowienie2.setCustomer(CUSTOMER_2);
		zamowienie2.setCustomerID(CUSTOMERID_2);
		
		Long zamowienieId = sellingManager.addNewZamowienie(zamowienie);
		Long zamowienieId2 = sellingManager.addNewZamowienie(zamowienie2);
		
		String newCustomer = "test";
		
		sellingManager.editZamowienieCustomer(zamowienieId, newCustomer);
		
		//Sprawdzam czy zmieniono poprawnie nazwe
		Zamowienie retrievedZamowienie = sellingManager.findZamowienieById(zamowienieId);
		assertEquals(newCustomer, retrievedZamowienie.getCustomer());
		
		//Sprawdzam czy nie zmieniono nazwy drugiego rekordu
		retrievedZamowienie = sellingManager.findZamowienieById(zamowienieId2);
		assertEquals(CUSTOMER_2, retrievedZamowienie.getCustomer());
		
	}

}
