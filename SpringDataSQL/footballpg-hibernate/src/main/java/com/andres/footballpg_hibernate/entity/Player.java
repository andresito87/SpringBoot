package com.andres.footballpg_hibernate.entity;

import java.time.LocalDate;

public record Player(String name, Integer jerseyNumber, String position, LocalDate dateOfBirth) {

}
