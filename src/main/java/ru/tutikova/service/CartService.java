package ru.tutikova.service;

import org.apache.catalina.SessionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tutikova.dao.entity.VDetailInfoEntity;
import ru.tutikova.dao.entity.auth.SessionsEntity;
import ru.tutikova.dao.entity.cart.CartDetailsEntity;
import ru.tutikova.dao.entity.cart.CartEntity;
import ru.tutikova.dao.entity.cart.VCartInfoEntity;
import ru.tutikova.dao.repositories.SessionRepository;
import ru.tutikova.dao.repositories.cart.CartDetailsRepository;
import ru.tutikova.dao.repositories.cart.CartRepository;
import ru.tutikova.dao.repositories.cart.VCartInfoRepository;
import ru.tutikova.dto.UserDto;
import ru.tutikova.dto.cart.CartDto;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private VCartInfoRepository cartInfoRepository;

    private CartDetailsRepository cartDetailsRepository;

    private SessionRepository sessionRepository;

    private CartRepository cartRepository;

    @Autowired
    public CartService(VCartInfoRepository cartInfoRepository, CartDetailsRepository cartDetailsRepository,
                       SessionRepository sessionRepository, CartRepository cartRepository) {
        this.cartInfoRepository = cartInfoRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.sessionRepository = sessionRepository;
        this.cartRepository = cartRepository;
    }

    private SessionsEntity createSession (String sessionCode) {
        SessionsEntity sessionsEntity = new SessionsEntity();
        sessionsEntity.setSessionCode(sessionCode);

        sessionsEntity = sessionRepository.save(sessionsEntity);
        sessionRepository.flush();
        return sessionsEntity;
    }

    private CartEntity createCart (SessionsEntity sessionsEntity) {
        /*SessionsEntity sessionsEntity = sessionRepository.getSessionsEntityBySessionCode(sessionCode);

        if (sessionsEntity == null) {
            sessionsEntity = createSession(sessionCode);
        }*/

        CartEntity cartEntity = new CartEntity();
        cartEntity.setSessionId(sessionsEntity.getId());

        return cartRepository.save(cartEntity);
    }

    @Transactional
    public CartDto getCartStatistics (String sessionKey) {
        List<VCartInfoEntity> cartInfoEntityList = cartInfoRepository.getAllBySessionCode(sessionKey);
        VCartInfoEntity existingCardDetail = null;

        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalCount = 0;

        for (VCartInfoEntity entity : cartInfoEntityList) {
            totalCount += entity.getQuantity();
            totalPrice = totalPrice.add(entity.getPrice().multiply(BigDecimal.valueOf(entity.getQuantity())));
        }

        CartDto result = new CartDto();
        result.setCount(totalCount);
        result.setTotalSumm(totalPrice);

        return result;
    }

    private CartDetailsEntity createNewCartDetail (String sessionCode, CartDto dto) {
        SessionsEntity sessionsEntity = sessionRepository.getSessionsEntityBySessionCode(sessionCode);

        if (sessionsEntity == null) {
            sessionsEntity = createSession(sessionCode);
        }

        CartEntity cartEntity = cartRepository.getBySessionId(sessionsEntity.getId());

        if (cartEntity == null) {
            cartEntity = createCart(sessionsEntity);
        }

        /*if (cartInfoEntityList == null || cartInfoEntityList.isEmpty()) {
            CartEntity cartEntity = createCart(sessionCode);;
            cartEntityId = cartEntity.getId();
        } else {
            cartEntityId = cartInfoEntityList.get(0).getCartId();
        }*/

        CartDetailsEntity cartDetailsEntity = new CartDetailsEntity();
        cartDetailsEntity.setCartId(cartEntity.getId());
        cartDetailsEntity.setQuantity(dto.getCount());
        cartDetailsEntity.setStoreDetailId(dto.getId());

        return cartDetailsRepository.save(cartDetailsEntity);
    }

    public CartDto addToCart(CartDto dto) {
        SecurityContext sc = SecurityContextHolder.getContext();
        UserDto userDto = (UserDto)sc.getAuthentication();

        String sessionKey = userDto.getSessionId();

        VCartInfoEntity existingCartInfoEntity = cartInfoRepository.getBySessionCodeAndDetailId(sessionKey, dto.getId());

        if (existingCartInfoEntity == null) {
            CartDetailsEntity cartDetailsEntity = createNewCartDetail(sessionKey, dto);

            return getCartStatistics(sessionKey);
        }

        CartDetailsEntity exiatingCartDetailsEntity = cartDetailsRepository.getById(existingCartInfoEntity.getId());

        exiatingCartDetailsEntity.setQuantity(Math.min(exiatingCartDetailsEntity.getQuantity() + dto.getCount(), existingCartInfoEntity.getStoreQuantity()));
        cartDetailsRepository.save(exiatingCartDetailsEntity);

        return getCartStatistics(sessionKey);



    }



}
