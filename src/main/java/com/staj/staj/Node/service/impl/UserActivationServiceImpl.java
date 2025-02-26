package com.staj.staj.Node.service.impl;

import com.staj.staj.Node.service.UserActivationService;
import com.staj.staj.commonUtils.utils.CryptoTool;
import com.staj.staj.common_jpa.dao.AppUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    public UserActivationServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }
    @Override
    public boolean activation(String cryptoUserId) {
        var userId = cryptoTool.idOf(cryptoUserId);//дешифрует Id польз.
        var optional = appUserDAO.findById(userId);//находит польз в бд
        if (optional.isPresent()) {//если нашли польз
            var user = optional.get();
            user.setIsActive(true);//тоесть он есть в бд
            appUserDAO.save(user);
            return true;
        }
        return false;
    }
}
