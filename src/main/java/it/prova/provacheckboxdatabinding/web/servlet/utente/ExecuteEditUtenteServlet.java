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

@WebServlet("/ExecuteEditUtenteServlet")
public class ExecuteEditUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// estraggo input
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String usernameParam = request.getParameter("username");
		String passwordParam = request.getParameter("password");
		String statoParam = request.getParameter("stato");
		String[] ruoliSelezionati = request.getParameterValues("ruoloInput");

		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Utente utenteInstance = UtilityForm.createUtenteFromParams(nomeParam, cognomeParam, usernameParam,
				passwordParam, statoParam);

		// se la validazione non risulta ok
		if (!UtilityForm.validateUtenteBean(utenteInstance)) {

			request.setAttribute("mappaRuoliConSelezionati_attr",
					UtilityForm.buildCheckedRolesForPages(MockDB.LISTA_RUOLI, ruoliSelezionati));

			request.setAttribute("edit_utente_attr", utenteInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/utente/edit.jsp").forward(request, response);
			return;
		}
		// se sono qui è tutto ok ma quando si inserisce bisogna ricordarsi che
		// dall'array di stringhe
		// bisogna convertire in long qui trucchiamo le cose per far funzionare lo show
		if (ruoliSelezionati != null) {
			for (String stringItem : ruoliSelezionati)
				utenteInstance.getRuoli().add(MockDB.get(Long.valueOf(stringItem)));
		}
		//mettiamo una data di creazione giusto per la visualizzazione
		utenteInstance.setDateCreated(new Date());
		request.setAttribute("show_utente_attr", utenteInstance);

		request.setAttribute("successMessage", "Elemento modificato con successo");
		request.getRequestDispatcher("/utente/show.jsp").forward(request, response);
	}

}
