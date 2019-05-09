package modelo.dao;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelo.dominio.Curso;

public class CursoDAO {
	Curso curso;
	List<Curso> lista;
	Gson gson = new Gson();
	Type listType = new TypeToken<ArrayList<Curso>>() {
	}.getType();

	Util util = new Util();

	public Curso lerPorId(Integer id) throws Exception {
		String json = util.sendGet(WS.link + "curso/" + id, "GET");

		return this.curso = gson.fromJson(json, Curso.class);

	}

	@SuppressWarnings("unchecked")
	public List<Curso> lerTodos() throws Exception {

		String json = this.util.sendGet(WS.link + "curso/", "GET");

		return this.lista = (List<Curso>) gson.fromJson(json, this.listType);
	}

	public void excluir(Curso c) throws Exception {

		this.util.sendGet(WS.link + "curso/" + c.getCodigo(), "DELETE");

	}

	public Curso salvar(Curso c) throws Exception {
		Curso teste=this.lerPorId(c.getCodigo());
		
		if (teste!=null) {
			String json = this.gson.toJson(c);
			String url = WS.link + "curso/";
			String jsonRetorno = this.util.sendPost(url, json, "PUT");
			this.curso = this.gson.fromJson(jsonRetorno, Curso.class);
			return this.curso;
		} else {
			String json = this.gson.toJson(c);
			String url = WS.link + "curso/";
			String jsonRetorno = this.util.sendPost(url, json, "POST");
			this.curso = this.gson.fromJson(jsonRetorno, Curso.class);
			return this.curso;
		}

	}

}
