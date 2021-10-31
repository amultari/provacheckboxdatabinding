package it.prova.provacheckboxdatabinding.web.servlet.utente;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.provacheckboxdatabinding.dao.MockDB;
import it.prova.provacheckboxdatabinding.model.StatoUtente;
import it.prova.provacheckboxdatabinding.model.Utente;
import it.prova.provacheckboxdatabinding.utility.UtilityForm;

@WebServlet("/PrepareEditUtenteServlet")
public class PrepareEditUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// CODICE MOCK ===>> nella realtà qui c'è inerazione con la base dati
		// preparo un utente mock
		Utente utenteInstance = new Utente("marior", 23L, "Mario", "Rossi", StatoUtente.ATTIVO);
		// gli attribuisco un ruolo
		utenteInstance.getRuoli().add(MockDB.getRuoloManager());

		request.setAttribute("mappaRuoliConSelezionati_attr",
				UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(MockDB.LISTA_RUOLI, utenteInstance.getRuoli()));
		request.setAttribute("edit_utente_attr", utenteInstance);
		request.getRequestDispatcher("/utente/edit.jsp").forward(request, response);
	}

}
