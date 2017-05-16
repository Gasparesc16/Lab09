package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataSuLinea;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
	
	public List<Linea> getAllLinee() {
		// TODO Auto-generated method stub
		
		final String sql = 
							"SELECT id_linea,nome,velocita,intervallo,colore " + 
							"FROM linea " + 
							"ORDER BY nome ASC ";
		
		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Linea l = new Linea(rs.getInt("id_linea"), rs.getString("nome"),rs.getDouble("velocita"),rs.getDouble("intervallo"), rs.getString("colore"));
				linee.add(l);
				
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
		
		
	}


	public List<Connessione> getAllConnessioni(List<Fermata> fermate, List<Linea> linee) {
		// TODO Auto-generated method stub

		final String sql = "SELECT id_connessione,id_linea,id_stazP,id_stazA FROM connessione ";
		
		List<Connessione> connessioni = new ArrayList<Connessione>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
//				Linea linea = new Linea(rs.getInt("id_linea"));
//				Fermata partenza = new Fermata(rs.getInt("id_stazP"));
//				Fermata arrivo = new Fermata(rs.getInt("id_stazA"));
//				
//				Connessione c = new Connessione(rs.getInt("id_connessione"),linea,partenza,arrivo);
				
				// Cerco la fermata e la linea che sono presenti nelle liste passate come parametro e creo un oggetto di tipo Connessione
				
				int idLinea = rs.getInt("id_linea");
				int idStazP = rs.getInt("id_stazP");
				int idStazA = rs.getInt("id_stazA");
				
				// ATTENZIONE: Per poter mettere un riferimento lo devo andare a cercare all'interno di un pool affidabile

				Linea linea = linee.get(linee.indexOf(new Linea(idLinea)));
				Fermata partenza = fermate.get(fermate.indexOf(new Fermata(idStazP)));
				Fermata arrivo = fermate.get(fermate.indexOf(new Fermata(idStazA)));
				
				// N.B. gli ultimi 3 parametri non sono numeri ma sono dei puntatori ad oggetti presenti in memoria
				Connessione c = new Connessione(rs.getInt("id_connessione"),linea,partenza,arrivo);
				
				connessioni.add(c);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return connessioni;

	}
	

	public List<FermataSuLinea> getAllFermateSuLinea(List<Fermata> fermate, List<Linea> linee) {

		final String sql = "SELECT DISTINCT fermata.id_fermata, linea.id_linea FROM fermata, linea, connessione WHERE (fermata.id_fermata = connessione.id_stazP OR fermata.id_fermata = connessione.id_stazA) AND connessione.id_linea = linea.id_linea";
		List<FermataSuLinea> fermateSuLinea = new ArrayList<FermataSuLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int idLinea = rs.getInt("id_linea");
				int idFermata = rs.getInt("id_fermata");

				// Creo un nuovo oggetto, per trovarne uno esistente
				Linea linea = linee.get(linee.indexOf(new Linea(idLinea)));
				Fermata fermata = fermate.get(fermate.indexOf(new Fermata(idFermata)));

				FermataSuLinea fermataSuLinea = new FermataSuLinea(fermata, linea);
				fermata.addFermataSuLinea(fermataSuLinea);
				fermateSuLinea.add(fermataSuLinea);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermateSuLinea;
	}


	
}
 