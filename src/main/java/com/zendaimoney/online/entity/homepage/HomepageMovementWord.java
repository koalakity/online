package com.zendaimoney.online.entity.homepage;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * MovementWord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MOVEMENT_WORD")
public class HomepageMovementWord implements java.io.Serializable {

	private static final long serialVersionUID = -7752340627675604464L;
	private BigDecimal wordId;
	private String wordContext;
	private String description;

	// Constructors

	/** default constructor */
	public HomepageMovementWord() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "WORD_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getWordId() {
		return this.wordId;
	}

	public void setWordId(BigDecimal wordId) {
		this.wordId = wordId;
	}

	@Column(name = "WORD_CONTEXT", length = 500)
	public String getWordContext() {
		return this.wordContext;
	}

	public void setWordContext(String wordContext) {
		this.wordContext = wordContext;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}