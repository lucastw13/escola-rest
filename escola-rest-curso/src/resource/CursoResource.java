package resource;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.CursoDTO;
import entity.Curso;
import exception.DaoException;
import exception.ServiceException;
import exception.ServiceException.ServiceExceptionEnum;
import service.CursoService;

@Path("curso")
@Consumes("application/json")
@Produces("application/json")
public class CursoResource {

	private CursoService cursoService;
	Gson gson = new Gson();
	String json = null;
	List<Curso> lista = new ArrayList<Curso>();

	public CursoResource() {
		this.cursoService = new CursoService();

	}

	@GET
	@Path("{codigo}")
	public String buscarCurso(@PathParam("codigo") String codigo) {
		try {
			CursoDTO cursoDTO = cursoService.buscarCurso(new Integer(codigo).intValue());
			// Response resposta = Response.ok(cursoDTO).build();
			// return resposta;
			this.json = this.gson.toJson(cursoDTO);
			return this.json;
		} catch (DaoException e) {
			
			return "";
			// return Response.status(404).build();
		}
	}

	@POST
	public Response cadastrarCurso(String jsonRuim) throws ServiceException, DaoException, UnsupportedEncodingException {
		String json=URLDecoder.decode(jsonRuim.toString(), "UTF-8");
		CursoDTO cursoDTO=this.gson.fromJson(json, CursoDTO.class);
		try {
			cursoService.cadastrarCurso(cursoDTO);
			return Response.created(new URI("" + cursoDTO.getCodigo())).build();
		} catch (ServiceException e) {
			if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO)
				return Response.status(400).header("Motivo", e.getTipo()).build();
			if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO)
				return Response.status(400).header("Motivo", "Nome inv�lido").build();
			else
				return Response.status(400).header("Motivo", e.getMessage()).build();
		} catch (DaoException e) {
			return Response.status(400).header("Motivo", "Erro no banco de dados").build();
		} catch (URISyntaxException e) {
			throw new RuntimeException();
		}
	}

	@PUT
	public Response alterarCurso(String jsonRuim) throws UnsupportedEncodingException {
		String json=URLDecoder.decode(jsonRuim.toString(), "UTF-8");
		CursoDTO cursoDTO=this.gson.fromJson(json, CursoDTO.class);
		try {
			cursoService.alterarCurso(cursoDTO);
			return Response.created(new URI("" + cursoDTO.getCodigo())).build();
		} catch (ServiceException e) {
			if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO)
				return Response.status(400).header("Motivo", "C�digo inv�lido").build();
			if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO)
				return Response.status(400).header("Motivo", "Nome inv�lido").build();
			else
				return Response.status(400).header("Motivo", e.getMessage()).build();
		} catch (DaoException e) {
			return Response.status(400).header("Motivo", "Erro no banco de dados").build();
		} catch (URISyntaxException e) {
			throw new RuntimeException();
		}
	}

	@DELETE
	@Path("{codigo}")
	public Response removerCurso(@PathParam("codigo") String codigo) {
		try {
			cursoService.removerCurso(new Integer(codigo).intValue());
			Response resposta = Response.ok().build();
			return resposta;
		} catch (DaoException e) {
			return Response.status(404).build();
		}
	}

	@GET
	public String listarCursos() {

		for (Iterator<Curso> it = cursoService.listarCursos().iterator(); it.hasNext();) {
			Curso curso = (Curso) it.next();
			this.lista.add(curso);
		}
		this.json = this.gson.toJson(this.lista);
		return this.json;
	}
}