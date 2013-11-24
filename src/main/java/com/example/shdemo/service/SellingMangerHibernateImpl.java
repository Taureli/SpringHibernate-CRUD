package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Pick;
import com.example.shdemo.domain.Zamowienie;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addZamowienie(Zamowienie zamowienie) {
		zamowienie.setId(null);
		sessionFactory.getCurrentSession().persist(zamowienie);
	}
	
	@Override
	public void deleteZamowienie(Zamowienie zamowienie) {
		zamowienie = (Zamowienie) sessionFactory.getCurrentSession().get(Zamowienie.class,
				zamowienie.getId());
		
		// lazy loading here
		for (Pick pick : zamowienie.getPicks()) {
			pick.setAv(false);
			sessionFactory.getCurrentSession().update(pick);
		}
		sessionFactory.getCurrentSession().delete(zamowienie);
	}

	//Kostki z zamowienia
	@Override
	public List<Pick> getSoldPicks(Zamowienie zamowienie) {
		zamowienie = (Zamowienie) sessionFactory.getCurrentSession().get(Zamowienie.class,
				zamowienie.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Pick> picks = new ArrayList<Pick>(zamowienie.getPicks());
		return picks;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Zamowienie> getAllZamowienie() {
		return sessionFactory.getCurrentSession().getNamedQuery("zamowienie.all")
				.list();
	}

	@Override
	public Zamowienie findByCustomerID(String customerID) {
		return (Zamowienie) sessionFactory.getCurrentSession().getNamedQuery("zamowienie.byCustomerID").setString("customerID", customerID).uniqueResult();
	}


	@Override
	public Long addNewZamowienie(Zamowienie zamowienie) {
		zamowienie.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(zamowienie);
	}
	
	@Override
	public Long addNewPick(Pick pick) {
		pick.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(pick);
	}

	@Override
	public void sellPick(Long zamowienieId, Long pickId) {
		Zamowienie zamowienie = (Zamowienie) sessionFactory.getCurrentSession().get(
				Zamowienie.class, zamowienieId);
		Pick pick = (Pick) sessionFactory.getCurrentSession()
				.get(Pick.class, pickId);
		pick.setAv(true);
		zamowienie.getPicks().add(pick);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pick> getAvailablePicks() {
		return sessionFactory.getCurrentSession().getNamedQuery("pick.unsold")
				.list();
	}
	@Override
	public void disposePick(Zamowienie zamowienie, Pick pick) {

		zamowienie = (Zamowienie) sessionFactory.getCurrentSession().get(Zamowienie.class,
				zamowienie.getId());
		pick = (Pick) sessionFactory.getCurrentSession().get(Pick.class,
				pick.getId());

		Pick toRemove = null;
		// lazy loading here (person.getCars)
		for (Pick aPick : zamowienie.getPicks())
			if (aPick.getId().compareTo(pick.getId()) == 0) {
				toRemove = aPick;
				break;
			}

		if (toRemove != null)
			zamowienie.getPicks().remove(toRemove);

		pick.setAv(false);
	}

	//-----------------------
	@Override
	public Pick findPickById(Long id) {
		return (Pick) sessionFactory.getCurrentSession().get(Pick.class, id);
	}
	
	@Override
	public Zamowienie findZamowienieById(Long id) {
		return (Zamowienie) sessionFactory.getCurrentSession().get(Zamowienie.class, id);
	}
	
	@Override
	public void deleteZamowienieID(Long id){
		Zamowienie zamowienieDel = (Zamowienie)sessionFactory.getCurrentSession().get(Zamowienie.class, id);
		
		sessionFactory.getCurrentSession().delete(zamowienieDel);
	}
	
	@Override
	public void deletePickID(Long id){
		Pick pickDel = (Pick)sessionFactory.getCurrentSession().get(Pick.class, id);
		
		sessionFactory.getCurrentSession().delete(pickDel);
	}
	
	@Override
	public void editZamowienieCustomer(Long id, String customer){
		Zamowienie zamowienieEdit = (Zamowienie)sessionFactory.getCurrentSession().get(Zamowienie.class, id);
		
		zamowienieEdit.setCustomer(customer);
		
		sessionFactory.getCurrentSession().save(zamowienieEdit);
	}
	
	@Override
	public void editPickName(Long id, String name){
		Pick pickEdit = (Pick)sessionFactory.getCurrentSession().get(Pick.class, id);
		
		pickEdit.setName(name);
		
		sessionFactory.getCurrentSession().save(pickEdit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pick> getAllPick() {
		return sessionFactory.getCurrentSession().getNamedQuery("pick.all").list();
	}

}
