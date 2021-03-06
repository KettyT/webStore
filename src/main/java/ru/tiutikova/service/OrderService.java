package ru.tiutikova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tiutikova.UserException;
import ru.tiutikova.dao.entity.cart.VCartInfoUserEntity;
import ru.tiutikova.dao.entity.order.OrderDetailsEntity;
import ru.tiutikova.dao.entity.order.OrderRefundRequestEntity;
import ru.tiutikova.dao.entity.order.OrdersEntity;
import ru.tiutikova.dao.entity.order.VOrderDetailEntity;
import ru.tiutikova.dao.repositories.cart.CartDetailsRepository;
import ru.tiutikova.dao.repositories.cart.CartRepository;
import ru.tiutikova.dao.repositories.cart.VCartInfoUserRepository;
import ru.tiutikova.dao.repositories.detail.StoredDetailsRepository;
import ru.tiutikova.dao.repositories.order.*;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.dto.order.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private CartDetailsRepository cartDetailsRepository;

    private VCartInfoUserRepository cartInfoUserRepository;

    private CartRepository cartRepository;

    private OrderRepository orderRepository;

    private OrderDetailsRepository orderDetailsRepository;

    private StoredDetailsRepository storedDetailsRepository;

    private VOrderDetailRepository viewOrderDetailRepository;

    private NativeOrderDao nativeOrderDao;

    private OrderRefundRequestRepository orderRefundRequestRepository;

    @Autowired
    public OrderService(CartDetailsRepository cartDetailsRepository, CartRepository cartRepository, OrderRepository orderRepository,
                        OrderDetailsRepository orderDetailsRepository, StoredDetailsRepository storedDetailsRepository, NativeOrderDao nativeOrderDao,
                        VCartInfoUserRepository cartInfoUserRepository, VOrderDetailRepository viewOrderDetailRepository,
                        OrderRefundRequestRepository orderRefundRequestRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.storedDetailsRepository = storedDetailsRepository;
        this.nativeOrderDao = nativeOrderDao;
        this.cartInfoUserRepository = cartInfoUserRepository;
        this.viewOrderDetailRepository = viewOrderDetailRepository;
        this.orderRefundRequestRepository = orderRefundRequestRepository;
    }

    private void decrementStoredDetailsQuantity(List<VCartInfoUserEntity> cartInfoUserEntityList) {
        Map<Integer, Integer> quantityMap = new HashMap<>();

        for (VCartInfoUserEntity cartInfoUserEntity : cartInfoUserEntityList) {
            quantityMap.put(cartInfoUserEntity.getDetailId(), cartInfoUserEntity.getStoreQuantity() - cartInfoUserEntity.getQuantity());
        }

        nativeOrderDao.updateStoreDetailsQuantity(quantityMap);
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

        if (cartInfoUserEntityList.isEmpty()) {
            throw new UserException("?????? ?????????????????? ??????????????. ???????????????? ???????????? ?? ?????????????? ?? ?????????????????? ??????????????.");
        }

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
                throw new UserException("???????????? " + cartDetailsEntity.getItemName() + " ???? ???????????? ???????????????? "
                        + cartDetailsEntity.getStoreQuantity() + ". ???????????? ???????????????? ????????????.");
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

        return new ResultDto(true, "?????? ?????????? ?????????????? ????????????????");
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

        Map<Integer, VOrderDetailEntity> integerorderDetailsEntityListMap = new HashMap<>();

        for (VOrderDetailEntity orderDetailEntity : orderDetailsEntityList) {
            integerorderDetailsEntityListMap.put(orderDetailEntity.getId(), orderDetailEntity);
        }


        List<Integer> orderDetailIdList = orderDetailsEntityList.stream().map((elm) -> elm.getId()).collect(Collectors.toList());

        List<OrderRefundRequestEntity> orderRefundRequestEntityList = orderRefundRequestRepository.getAllRefundRequestByOrderDetailList(orderDetailIdList);

        List<OrderRefundRequestDto> orderRefundRequestDtoList = orderRefundRequestEntityList.stream().map((OrderRefundRequestEntity orderRefundRequestEntity) -> {
            OrderRefundRequestDto orderRefundRequestDto = new OrderRefundRequestDto(orderRefundRequestEntity);

            VOrderDetailEntity orderDetailEntity = integerorderDetailsEntityListMap.get(orderRefundRequestDto.getOrderDetailId());

            orderRefundRequestDto.setOrderDetailName(orderDetailEntity.getName());
            orderRefundRequestDto.setOrderDetailPrice(orderDetailEntity.getPrice());

            return orderRefundRequestDto;
        }).collect(Collectors.toList());

        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

        for (VOrderDetailEntity orderDetailsEntity : orderDetailsEntityList) {
            orderDetailDtoList.add(new OrderDetailDto(orderDetailsEntity));
        }

        return new FullOrderInfoDto(entity, orderDetailDtoList, orderRefundRequestDtoList);
    }

    @Transactional
    public SimpleDto doRefund (RefundDto dto) {

        List<OrderRefundRequestEntity> refundList = new ArrayList<>();

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant nowInstant = localDateTime.toInstant(ZoneOffset.UTC);

        UserDto existingUserDto = (UserDto)SecurityContextHolder.getContext().getAuthentication();

        for (OrderDetailDto orderDetailDto : dto.getRefundedOrderList()) {
            OrderRefundRequestEntity refundEntity = new OrderRefundRequestEntity();
            refundEntity.setDateCreate(Timestamp.from(nowInstant));
            refundEntity.setStatus("created");
            refundEntity.setQuantity(orderDetailDto.getQuantity());
            refundEntity.setText(dto.getRefundReason());
            refundEntity.setOrderDetailId(orderDetailDto.getId());
            refundEntity.setUserId(existingUserDto.getId());

            refundList.add(refundEntity);
        }

        orderRefundRequestRepository.saveAll(refundList);

        return new ResultDto(true, "???????????? ?????????????? ??????????????");
    }

}
