package com.staj.staj.Node.service;

import com.staj.staj.common_jpa.entity.AppUser;

public interface AppUserService {
    String registerUser(AppUser appUser);
    String setEmail(AppUser appUser, String email);
}
