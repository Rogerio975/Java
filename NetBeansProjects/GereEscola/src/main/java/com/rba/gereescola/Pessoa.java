/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rba.gereescola;

/**
 *
 * @author E144231
 */
public class Pessoa {
    //Atributos
	protected String nome , nacionalidade , naturalidade;

	//Métodos
	public Pessoa ( String nome , String nacionalidade , String naturalidade ) {
		this.nome = nome;
		this.nacionalidade = nacionalidade;
		this.naturalidade = naturalidade;
	}
	protected void atualizarNome ( String nome ) {
		this.nome = nome;
	}
	protected String recuperarNome ( ) {
		return this.nome;
	}
	protected String recuperarNacionalidade ( ) {
		return this.nacionalidade;
	}
	protected String recuperarNaturalidade ( ) {
		return this.naturalidade;
	} 
        
}
