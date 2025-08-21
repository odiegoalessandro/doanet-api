package com.doanet.api.application.commands;


public record UpdateOngCommand(String name,
                               String email,
                               String phone) {
}
