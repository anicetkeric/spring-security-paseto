package com.bootlabs.springsecuritypaseto.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String username, @NotNull String password) {}