package controle;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import modelo.dao.CursoDAO;
import modelo.dao.TesteDAO;
import modelo.dominio.Curso;

@ManagedBean(name = "cursoMB")
@RequestScoped
public class CursoMB {
	private Curso curso = new Curso();
	private CursoDAO dao = new CursoDAO();
	private TesteDAO daoTeste = new TesteDAO();
	private List<Curso> lista = null;

	public List<Curso> getLista() throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return null;
		} else {
			this.lista = dao.lerTodos();
			return this.lista;
		}

	}

	public void setLista(List<Curso> lista) {

		this.lista = lista;
	}

	public int getListaSize() {
		if (this.lista == null)
			return 0;

		return this.lista.size();
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String acaoAbrirInclusao() throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return "";
		} else {
			return "/inserirCurso.jsf";
		}

	}

	public String acaoListar() {

		return "/index.jsf?faces-redirect=true";
	}

	public String acaoAbrirAlteracao(Integer codigo) throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return "";
		} else {
			this.curso = this.dao.lerPorId(codigo);
			return "/editarCurso.jsf";
		}
	}

	public String acaoSalvar() throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return "";
		} else {
			Curso c = this.dao.lerPorId(this.curso.getCodigo());
			if (c == null) {
				this.dao.salvar(this.curso);
				return "/index.jsf?faces-redirect=true";
			} else {
				FacesContext contexto = FacesContext.getCurrentInstance();
				contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código já cadastrado", null));
				return "";
			}

		}

	}
	public String acaoAlterar() throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return "";
		} else {
		
				this.dao.salvar(this.curso);
				return "/index.jsf?faces-redirect=true";

		}

	}

	public String acaoExcluir(Integer codigo) throws Exception {
		if (this.daoTeste.testar()) {
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conexão Perdida", null));
			return "";
		} else {
			Curso c = this.dao.lerPorId(codigo);
			this.dao.excluir(c);
			return "/index.jsf?faces-redirect=true";

		}
	}
}
