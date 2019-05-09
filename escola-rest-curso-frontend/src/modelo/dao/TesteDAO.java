package modelo.dao;

import com.google.gson.Gson;

public class TesteDAO {

	Gson gson = new Gson();

	Util util = new Util();

	public Boolean testar() throws Exception {
		try {
			this.util.sendGet(WS.link + "curso/", "GET");
			return false;
		} catch (Exception e) {

			e.printStackTrace();
			return true;
		}

	}

}
