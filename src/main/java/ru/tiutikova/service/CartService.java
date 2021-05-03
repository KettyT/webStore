package ru.tiutikova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tiutikova.dao.entity.auth.SessionsEntity;
import ru.tiutikova.dao.entity.cart.CartDetailsEntity;
import ru.tiutikova.dao.entity.cart.CartEntity;
import ru.tiutikova.dao.entity.cart.IVCartInfoEntity;
import ru.tiutikova.dao.entity.cart.VCartInfoSessionEntity;
import ru.tiutikova.dao.repositories.SessionRepository;
import ru.tiutikova.dao.repositories.cart.CartDetailsRepository;
import ru.tiutikova.dao.repositories.cart.CartRepository;
import ru.tiutikova.dao.repositories.cart.VCartInfoSessionRepository;
import ru.tiutikova.dao.repositories.cart.VCartInfoUserRepository;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.dto.cart.CartDto;
import ru.tiutikova.dto.cart.FullCartInfoDto;
import ru.tiutikova.dto.cart.VCartInfoDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Service
public class CartService {

    private VCartInfoSessionRepository cartInfoSessionRepository;

    private VCartInfoUserRepository cartInfoUserRepository;

    private CartDetailsRepository cartDetailsRepository;

    private SessionRepository sessionRepository;

    private CartRepository cartRepository;

    @Autowired
    public CartService(VCartInfoSessionRepository cartInfoSessionRepository, VCartInfoUserRepository cartInfoUserRepository,
                       CartDetailsRepository cartDetailsRepository,
                       SessionRepository sessionRepository, CartRepository cartRepository) {
        this.cartInfoSessionRepository = cartInfoSessionRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.sessionRepository = sessionRepository;
        this.cartRepository = cartRepository;
        this.cartInfoUserRepository = cartInfoUserRepository;
    }

    private SessionsEntity createSession (String sessionCode) {
        SessionsEntity sessionsEntity = new SessionsEntity();
        sessionsEntity.setSessionCode(sessionCode);

        sessionsEntity = sessionRepository.save(sessionsEntity);
        sessionRepository.flush();
        return sessionsEntity;
    }

    private CartEntity createCart (SessionsEntity sessionsEntity, int userId) {
        /*SessionsEntity sessionsEntity = sessionRepository.getSessionsEntityBySessionCode(sessionCode);

        if (sessionsEntity == null) {
            sessionsEntity = createSession(sessionCode);
        }*/

        CartEntity cartEntity = new CartEntity();
        cartEntity.setSessionId(sessionsEntity.getId());

        if (userId > 0) {
            cartEntity.setUserId(userId);
        }

        cartEntity = cartRepository.save(cartEntity);
        cartRepository.flush();
        return cartEntity;
    }


    public CartDto getCartStatistics (int userId, String sessionKey) {
        List<? extends IVCartInfoEntity> cartInfoEntityList;
        if (userId > 0) {
            cartInfoEntityList = cartInfoUserRepository.getByUserId(userId);
        } else {
            cartInfoEntityList = cartInfoSessionRepository.getAllBySessionCode(sessionKey);
        }

        return getCartStatistics (cartInfoEntityList);
    }

    public CartDto getCartStatistics (List<? extends IVCartInfoEntity> cartInfoEntityList) {
        VCartInfoSessionEntity existingCardDetail = null;

        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalCount = 0;

        for (IVCartInfoEntity entity : cartInfoEntityList) {
            totalCount += entity.getQuantity();
            totalPrice = totalPrice.add(entity.getPrice().multiply(BigDecimal.valueOf(entity.getQuantity())));
        }

        CartDto result = new CartDto();
        result.setCount(totalCount);
        result.setTotalSumm(totalPrice);

        return result;

    }



    private CartDetailsEntity createNewCartDetail (String sessionCode, int userId, CartDto dto) {
        SessionsEntity sessionsEntity = sessionRepository.getSessionsEntityBySessionCode(sessionCode);

        if (sessionsEntity == null) {
            sessionsEntity = createSession(sessionCode);
        }

        CartEntity cartEntity = cartRepository.getBySessionId(sessionsEntity.getId());

        if (cartEntity == null) {
            cartEntity = createCart(sessionsEntity, userId);
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

    @Transactional
    public CartDto addToCart(CartDto dto) {
        SecurityContext sc = SecurityContextHolder.getContext();
        UserDto userDto = (UserDto)sc.getAuthentication();

        int userId = userDto.getId();

        String sessionKey = userDto.getSessionId();

        IVCartInfoEntity existingCartInfoEntity;

        if (userId > 0) {
            existingCartInfoEntity = cartInfoUserRepository.getByUserIdAndDetailId(userId, dto.getId());
        } else {
            existingCartInfoEntity = cartInfoSessionRepository.getBySessionCodeAndDetailId(sessionKey, dto.getId());
        }


        if (existingCartInfoEntity == null) {
            CartDetailsEntity cartDetailsEntity = createNewCartDetail(sessionKey, userId, dto);

            return getCartStatistics(userId, sessionKey);
        }

        CartDetailsEntity exiatingCartDetailsEntity = cartDetailsRepository.getById(existingCartInfoEntity.getId());

        exiatingCartDetailsEntity.setQuantity(Math.min(exiatingCartDetailsEntity.getQuantity() + dto.getCount(), existingCartInfoEntity.getStoreQuantity()));
        cartDetailsRepository.save(exiatingCartDetailsEntity);

        return getCartStatistics(userId, sessionKey);
    }

    public FullCartInfoDto getFullCartInfo() {
        FullCartInfoDto fullCartInfoDto = new FullCartInfoDto();
        SecurityContext sc = SecurityContextHolder.getContext();
        UserDto userDto = (UserDto)sc.getAuthentication();

        int userId = userDto.getId();
        String sessionKey = userDto.getSessionId();

        List<? extends IVCartInfoEntity> cartInfoEntityList;

        if (userId > 0) {
            cartInfoEntityList = cartInfoUserRepository.getByUserId(userId);
        } else {
            cartInfoEntityList = cartInfoSessionRepository.getAllBySessionCode(sessionKey);
        }

        CartDto cartDto = getCartStatistics (cartInfoEntityList);

        fullCartInfoDto.setCount(cartDto.getCount());
        fullCartInfoDto.setTotalSumm(cartDto.getTotalSumm());

        for (IVCartInfoEntity entity : cartInfoEntityList) {
            fullCartInfoDto.getCartDetailsDtooList().add(new VCartInfoDto(entity));
        }

        return fullCartInfoDto;
    }

    public FullCartInfoDto doOrder() {

        return null;
    }


}
