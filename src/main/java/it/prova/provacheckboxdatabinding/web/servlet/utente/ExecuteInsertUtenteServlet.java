package it.prova.provacheckboxdatabinding.web.servlet.utente;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.provacheckboxdatabinding.dao.MockDB;
import it.prova.provacheckboxdatabinding.model.Utente;
import it.prova.provacheckboxdatabinding.utility.UtilityForm;

@WebServlet("/ExecuteInsertUtenteServlet")
public class ExecuteInsertUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// estraggo input
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String usernameParam = request.getParameter("username");
		String passwordParam = request.getParameter("password");
		String[] ruoliSelezionati = request.getParameterValues("ruoloInput");

		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Utente utenteInstance = UtilityForm.createUtenteFromParams(nomeParam, cognomeParam, usernameParam,
				passwordParam, null);

		// se la validazione non risulta ok
		if (!UtilityForm.validateUtenteBean(utenteInstance)) {

			request.setAttribute("mappaRuoliConSelezionati_attr",
					UtilityForm.buildCheckedRolesForPages(MockDB.LISTA_RUOLI, ruoliSelezionati));

			request.setAttribute("insert_utente_attr", utenteInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;
		}
		// se sono qui è tutto ok ma quando si inserisce bisogna ricordarsi che
		// dall'array di stringhe
		// bisogna convertire in long qui trucchiamo le cose per far funzionare lo show.
		// NOTA!!! Questa logica di 'deserializzazione' del dato andrebbe nei Service!!!
		for (String stringItem : ruoliSelezionati!=null?ruoliSelezionati:new String[] {}) {
			utenteInstance.getRuoli().add(MockDB.get(Long.valueOf(stringItem)));
		}
		//CODICE MOCK: metto id fittizio e data creazione
		utenteInstance.setDateCreated(new Date());
		utenteInstance.setId(345L);
		request.setAttribute("show_utente_attr", utenteInstance);

		request.setAttribute("successMessage", "Elemento inserito con successo");
		request.getRequestDispatcher("/utente/show.jsp").forward(request, response);
	}

}
