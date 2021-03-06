package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sessao {
	
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne private Sala sala;
	@ManyToOne private Filme filme;
	private LocalTime horario;
	private BigDecimal preco = BigDecimal.ZERO;
	@OneToMany(mappedBy = "sessao", fetch=FetchType.EAGER)
	private Set<Ingresso> ingressos = new HashSet<>();
	
	/**
     * @deprecated hibernate only
     */
	public Sessao() {}
	
	public Sessao(Sala sala, Filme filme, LocalTime horario) {
		this.horario = horario;
		this.filme = filme;
		this.sala = sala;
		this.preco = this.sala.getPreco().add(this.filme.getPreco());
	}
	
	public boolean isDisponivel(Lugar lugarSelecionado) {
		return ingressos.stream()
				.map(Ingresso::getLugar)
				.noneMatch(lugar -> lugar.equals(lugarSelecionado));
	}
	
	public Map<String, List<Lugar>> getMapaDeLugares() {
		return this.sala.getMapaDeLugares();
	}
	
	public LocalTime getHorarioTermino() {
		return this.horario.plusMinutes(filme.getDuracao().toMinutes());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Set<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
}
