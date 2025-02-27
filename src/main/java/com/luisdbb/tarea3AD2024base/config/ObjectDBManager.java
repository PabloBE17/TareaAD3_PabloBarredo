package com.luisdbb.tarea3AD2024base.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;


public class ObjectDBManager {
	
	private EntityManagerFactory emf=Persistence.createEntityManagerFactory("objectdb://localhost:6136/bdtarea3ad_pablobarredo");
	
	private EntityManager em;
	
	public EntityManager getEntityManager() {
		if(em==null) {
			em=emf.createEntityManager();
			return em;
		}
		return em;
	}
	
	public void closeODB() {
		em.close();
	}


}
