package com.training.tinelli.sales.service.impl;

import com.training.tinelli.sales.domain.entity.Client;
import com.training.tinelli.sales.domain.entity.Order;
import com.training.tinelli.sales.domain.entity.OrderItem;
import com.training.tinelli.sales.domain.entity.Product;
import com.training.tinelli.sales.domain.enums.OrderStatus;
import com.training.tinelli.sales.domain.repository.OrderItemRepository;
import com.training.tinelli.sales.exception.BadRequestException;
import com.training.tinelli.sales.exception.NotFoundException;
import com.training.tinelli.sales.rest.dto.OrderDTO;
import com.training.tinelli.sales.rest.dto.OrderItemDTO;
import com.training.tinelli.sales.rest.dto.OrderStatusUpdateDTO;
import com.training.tinelli.sales.service.OrderService;
import com.training.tinelli.sales.domain.repository.ClientRepository;
import com.training.tinelli.sales.domain.repository.OrderRepository;
import com.training.tinelli.sales.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // são construtores obrigatórios, faz pra gente a injeção de dependência dos repos
public class OrderServiceImpl implements OrderService {

    private final ClientRepository clientRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final OrderItemRepository orderItemRepo;

    @Override
    public Order saveOrder(OrderDTO orderDTO) {
        Client client = clientRepo.findById(orderDTO.getClient()).orElseThrow(() -> new BadRequestException("Código do cliente inválido."));

        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setTotal(orderDTO.getTotal());
        order.setClient(client);
        order.setStatus(OrderStatus.REALIZADO);

        List<OrderItem> items = convertToListOrderItem(order, orderDTO.getItems());
        orderRepo.save(order);
        orderItemRepo.saveAll(items);

        order.setItems(items);

        return order;
    }

    @Override
    public Optional<Order> getFullOrderById(Integer id) {
        Optional<Order> order = orderRepo.findByIdFetchItems(id);
        return order;
    }

    @Override
    @Transactional
    public void updateOrderStatus(Integer id, OrderStatusUpdateDTO dto) {
        orderRepo.findById(id).map(order -> {
            order.setStatus(OrderStatus.valueOf(dto.getNewStatus())); // converte a string p/ um enum OrderStatus
            return orderRepo.save(order);
        }).orElseThrow(() -> new NotFoundException("Pedido não encontrado."));
    }

    private List<OrderItem> convertToListOrderItem(Order order, List<OrderItemDTO> itemsDTO) {
        if (itemsDTO.isEmpty()) throw new BadRequestException("Não é possível realizar um pedido sem itens");

        return itemsDTO.stream().map(itemDTO -> {
            Product product = productRepo.findById(itemDTO.getProduct())
                    .orElseThrow(() -> new BadRequestException("Código do produto inválido: " + itemDTO.getProduct()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setOrder(order);

            return item;
        }).collect(Collectors.toList());
    }
}
