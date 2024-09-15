package com.andres.footballpg_hibernate.entity;

import java.util.List;

public record Team(Integer id, String name, List<Player> players) {

}
