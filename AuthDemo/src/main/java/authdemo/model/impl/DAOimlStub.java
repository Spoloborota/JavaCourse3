package authdemo.model.impl;

import authdemo.model.DAO;

public class DAOimlStub implements DAO {

	@Override
	public String getPassword(String name) {
		return "admin";
	}

}
