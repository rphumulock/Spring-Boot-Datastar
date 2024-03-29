package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="games")
public class Game {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	public Game() {}
	
	public Game(int id, String name) {
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + "]";
	}

    public void setGameName(String game1) {
    }

	public void setGameDescription(String description1) {
	}
}











