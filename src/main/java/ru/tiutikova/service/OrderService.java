package ru.tiutikova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tiutikova.UserException;
import ru.tiutikova.dao.entity.cart.VCartInfoUserEntity;
import ru.tiutikova.dao.entity.order.OrderDetailsEntity;
import ru.tiutikova.dao.entity.order.OrdersEntity;
import ru.tiutikova.dao.entity.order.VOrderDetailEntity;
import ru.tiutikova.dao.repositories.cart.CartDetailsRepository;
import ru.tiutikova.dao.repositories.cart.CartRepository;
import ru.tiutikova.dao.repositories.cart.VCartInfoUserRepository;
import ru.tiutikova.dao.repositories.detail.StoredDetailsRepository;
import ru.tiutikova.dao.repositories.order.NaviveOrderDao;
import ru.tiutikova.dao.repositories.order.OrderDetailsRepository;
import ru.tiutikova.dao.repositories.order.OrderRepository;
import ru.tiutikova.dao.repositories.order.VOrderDetailRepository;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.dto.order.FullOrderInfoDto;
import ru.tiutikova.dto.order.OrderDetailDto;
import ru.tiutikova.dto.order.OrderDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private CartDetailsRepository cartDetailsRepository;

    private VCartInfoUserRepository cartInfoUserRepository;

    private CartRepository cartRepository;

    private OrderRepository orderRepository;

    private OrderDetailsRepository orderDetailsRepository;

    private StoredDetailsRepository storedDetailsRepository;

    private VOrderDetailRepository viewOrderDetailRepository;

    private NaviveOrderDao naviveOrderDao;

    @Autowired
    public OrderService(CartDetailsRepository cartDetailsRepository, CartRepository cartRepository, OrderRepository orderRepository,
                        OrderDetailsRepository orderDetailsRepository, StoredDetailsRepository storedDetailsRepository, NaviveOrderDao naviveOrderDao,
                        VCartInfoUserRepository cartInfoUserRepository, VOrderDetailRepository viewOrderDetailRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.storedDetailsRepository = storedDetailsRepository;
        this.naviveOrderDao = naviveOrderDao;
        this.cartInfoUserRepository = cartInfoUserRepository;
        this.viewOrderDetailRepository = viewOrderDetailRepository;
    }

    private void decrementStoredDetailsQuantity(List<VCartInfoUserEntity> cartInfoUserEntityList) {
        Map<Integer, Integer> quantityMap = new HashMap<>();

        for (VCartInfoUserEntity cartInfoUserEntity : cartInfoUserEntityList) {
            quantityMap.put(cartInfoUserEntity.getDetailId(), cartInfoUserEntity.getStoreQuantity() - cartInfoUserEntity.getQuantity());
        }

        naviveOrderDao.updateStoreDetailsQuantity(quantityMap);
    }

    private void clearCartByUserId(int userId, List<VCartInfoUserEntity> cartInfoUserEntityList) {

        if (cartInfoUserEntityList.isEmpty()) {
            return;
        }

        int cartId = cartInfoUserEntityList.get(0).getCartId();

        cartDetailsRepository.deleteAllByCartId(cartId);
//        cartRepository.deleteById();
    }

    @Transactional
    public ResultDto doOrder () {

        UserDto userDto = (UserDto)SecurityContextHolder.getContext().getAuthentication();

        int userId = userDto.getId();

        List<VCartInfoUserEntity> cartInfoUserEntityList = cartInfoUserRepository.getByUserId(userId);

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant nowInstant = localDateTime.toInstant(ZoneOffset.UTC);

        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setUserId(userId);
        ordersEntity.setNumber(1);
        ordersEntity.setDateDelivery(Timestamp.from(nowInstant));
        ordersEntity.setDateCreate(Timestamp.from(nowInstant));
        ordersEntity.setStatus("created");

        ordersEntity = orderRepository.save(ordersEntity);
        orderRepository.flush();

        List<OrderDetailsEntity> orderDetailsEntityList = new ArrayList<>();

        for (VCartInfoUserEntity cartDetailsEntity : cartInfoUserEntityList) {
            OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();

            if (cartDetailsEntity.getStoreQuantity() < cartDetailsEntity.getQuantity()) {
                throw new UserException("Товара " + cartDetailsEntity.getItemName() + " на складе осталось "
                        + cartDetailsEntity.getStoreQuantity() + ". Нельзя заказать больше.");
            }

            orderDetailsEntity.setPrice(cartDetailsEntity.getPrice());
            orderDetailsEntity.setQuantity(cartDetailsEntity.getQuantity());
            orderDetailsEntity.setRefundedQuantity(0);
            orderDetailsEntity.setStoredDetailId(cartDetailsEntity.getDetailId());
            orderDetailsEntity.setOrderId(ordersEntity.getId());

            orderDetailsEntityList.add(orderDetailsEntity);
        }

        orderDetailsRepository.saveAll(orderDetailsEntityList);

        decrementStoredDetailsQuantity(cartInfoUserEntityList);
        clearCartByUserId(userId, cartInfoUserEntityList);

        return new ResultDto(true, "Ваш заказ успешно оформлен");
    }

    public List<OrderDto> getAllOrder () {

        UserDto userDto = (UserDto)SecurityContextHolder.getContext().getAuthentication();

        List<OrdersEntity> orderList = orderRepository.getAllByUserIdOrderByDateCreateDesc(userDto.getId());

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (OrdersEntity entity : orderList) {
            orderDtoList.add(new OrderDto(entity));
        }

        return orderDtoList;
    }

    public FullOrderInfoDto getOrderById (SimpleDto dto) {

        UserDto userDto = (UserDto)SecurityContextHolder.getContext().getAuthentication();

        OrdersEntity entity = orderRepository.getByIdAndUserId(dto.getId(), userDto.getId());

        List<VOrderDetailEntity> orderDetailsEntityList = viewOrderDetailRepository.getAllByOrderIdOrderById(dto.getId());

        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

        for (VOrderDetailEntity orderDetailsEntity : orderDetailsEntityList) {
            orderDetailDtoList.add(new OrderDetailDto(orderDetailsEntity));
        }

        return new FullOrderInfoDto(entity, orderDetailDtoList);
    }

}
