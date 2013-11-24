package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Pick;
import com.example.shdemo.domain.Zamowienie;

public interface SellingManager {
	
	//ZAMOWIENIE
	void addZamowienie(Zamowienie zamowienie);
	List<Zamowienie> getAllZamowienie();
	void deleteZamowienie(Zamowienie zamowienie);
	Zamowienie findByCustomerID(String customerID);
	
	Long addNewZamowienie(Zamowienie zamowienie);
	Zamowienie findZamowienieById(Long id);
	void deleteZamowienieID(Long id);
	void editZamowienieCustomer(Long id, String customer);
	
	//KOSTKA
	Long addNewPick(Pick pick);
	List<Pick> getAvailablePicks();
	void disposePick(Zamowienie zamowienie, Pick pick);
	
	Pick findPickById(Long id);
	void deletePickID(Long id);
	void editPickName(Long id, String name);
	List<Pick> getAllPick();

	List<Pick> getSoldPicks(Zamowienie zamowienie);
	void sellPick(Long zamowienieId, Long pickId);
	
//	void addClient(Person person);
//	List<Person> getAllClients();
//	void deleteClient(Person person);
//	Person findClientByPin(String pin);
//	
//	Long addNewCar(Car car);
//	List<Car> getAvailableCars();
//	void disposeCar(Person person, Car car);
//	Car findCarById(Long id);
//
//	List<Car> getOwnedCars(Person person);
//	void sellCar(Long personId, Long carId);

}
