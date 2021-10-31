package it.prova.provacheckboxdatabinding.dao;

import java.util.Arrays;
import java.util.List;

import it.prova.provacheckboxdatabinding.model.Ruolo;

public class MockDB {
	public static List<Ruolo> LISTA_RUOLI = null;

	static {
		// mock di lista ruoli
		LISTA_RUOLI = Arrays.asList(new Ruolo[] { new Ruolo(12L, "Administrator", "ROLE_ADMIN"),
				new Ruolo(23L, "Classic User", "ROLE_CLASSIC_USER"),
				new Ruolo(45L, "Gestione Manager User", "ROLE_MANAGER_USER"),
				new Ruolo(67L, "Classic Shopper User", "ROLE_SHOPPER_USER") });
	}

	public static Ruolo get(Long id) {
		for (Ruolo ruoloItem : LISTA_RUOLI) {
			if (ruoloItem.getId().equals(id))
				return ruoloItem;
		}
		return null;
	}
	
	public static Ruolo getRuoloManager() {
		for (Ruolo ruoloItem : LISTA_RUOLI) {
			if (ruoloItem.getCodice().equals("ROLE_MANAGER_USER"))
				return ruoloItem;
		}
		return null;
	}
}
