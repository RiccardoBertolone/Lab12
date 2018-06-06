package it.polito.tdp.rivers.db;

import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		Model model = new Model() ;
		List<River> rivers = dao.getAllRivers(model.riversIdMap);
		List<Flow> flows = dao.getAllFlows(model.riversIdMap);
		
		for (River r : rivers) {
			List<Flow> f = dao.getAllFlowsByRiver(r.getId(), model.riversIdMap) ;
			r.setFlows(f);
		}
		
		System.out.println(rivers);
		
	}

}
