package com.example.demo.services;

import com.example.demo.entity.Game;

import java.util.List;
import java.util.Optional;


public interface GameService {

	List<Game> findAll();

	Optional<Game> findGame();

	List<Game> findBattle();

	Game findById(int theId);
	
	void save(Game game);
	
	void deleteById(int theId);
	
}
