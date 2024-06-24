//package com.sparta.redirect_outsourcing;
//
//import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
//import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
//import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemRepository;
//import com.sparta.redirect_outsourcing.domain.cart.repository.CartRepository;
//import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
//import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
//import com.sparta.redirect_outsourcing.domain.menu.repository.MenuRepository;
//import com.sparta.redirect_outsourcing.domain.order.entity.Order;
//import com.sparta.redirect_outsourcing.domain.order.entity.OrderDetail;
//import com.sparta.redirect_outsourcing.domain.order.repository.OrderRepository;
//import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
//import com.sparta.redirect_outsourcing.domain.restaurant.entity.RestaurntCategoryEnum;
//import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantRepository;
//import com.sparta.redirect_outsourcing.domain.user.entity.User;
//import com.sparta.redirect_outsourcing.domain.user.entity.UserRoleEnum;
//import com.sparta.redirect_outsourcing.domain.user.entity.UserStatusEnum;
//import com.sparta.redirect_outsourcing.domain.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataRunner implements ApplicationRunner {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final RestaurantRepository restaurantRepository;
//    private final MenuRepository menuRepository;
//    private final CartRepository cartRepository;
//    private final CartItemRepository cartItemRepository;
//    private final OrderRepository orderRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<User> users = createdUsers();
//        List<Restaurant> restaurants = createdRestaurants(users);
//        List<Menu> menus = createMenus(restaurants, users);
//        createOrders(users, restaurants, menus);
//    }
//
//    private List<User> createdUsers() {
//        List<User> users = new ArrayList<>();
//        for (int i = 1; i <= 4; ++i) {
//            User user = new User();
//            user.setUsername("test" + i);
//            user.setPassword(passwordEncoder.encode("testPWD@99"));
//            user.setUserRole(UserRoleEnum.ROLE_USER);
//            user.setUserStatus(UserStatusEnum.STATUS_NORMAL);
//            users.add(user);
//        }
//        return userRepository.saveAll(users);
//    }
//
//    private List<Restaurant> createdRestaurants(List<User> users) {
//        List<Restaurant> restaurants = List.of(
//                new Restaurant(users.get(0), "짱구네", "서울", RestaurntCategoryEnum.KOREAN, "소개 1"),
//                new Restaurant(users.get(1), "맘스터치", "부산", RestaurntCategoryEnum.WESTERN, "소개 2"),
//                new Restaurant(users.get(2), "홍콩반점", "대구", RestaurntCategoryEnum.CHINESE, "소개 3"),
//                new Restaurant(users.get(3), "오마카세", "강원도", RestaurntCategoryEnum.JAPANESE, "소개 4")
//        );
//        return restaurantRepository.saveAll(restaurants);
//    }
//
//    private List<Menu> createMenus(List<Restaurant> restaurants, List<User> users) {
//        List<Menu> menus = List.of(
//                new Menu("뿌링클", 17000, MenuCategoryEnum.MAIN, users.get(0), restaurants.get(0)),
//                new Menu("치즈볼", 5000, MenuCategoryEnum.SIDE, users.get(0), restaurants.get(0)),
//                new Menu("펩시제로", 2000, MenuCategoryEnum.DRINK, users.get(0), restaurants.get(0)),
//                new Menu("햄버거", 6000, MenuCategoryEnum.MAIN, users.get(1), restaurants.get(1)),
//                new Menu("감자튀김", 3000, MenuCategoryEnum.SIDE, users.get(1), restaurants.get(1)),
//                new Menu("코카콜라", 2000, MenuCategoryEnum.DRINK, users.get(1), restaurants.get(1)),
//                new Menu("짜장면", 6000, MenuCategoryEnum.MAIN, users.get(2), restaurants.get(2)),
//                new Menu("탕수육", 14000, MenuCategoryEnum.SIDE, users.get(2), restaurants.get(2)),
//                new Menu("사이다", 2000, MenuCategoryEnum.DRINK, users.get(2), restaurants.get(2)),
//                new Menu("초밥", 14000, MenuCategoryEnum.MAIN, users.get(3), restaurants.get(3)),
//                new Menu("어묵탕", 3000, MenuCategoryEnum.SIDE, users.get(3), restaurants.get(3)),
//                new Menu("사케", 6000, MenuCategoryEnum.DRINK, users.get(3), restaurants.get(3))
//        );
//        return menuRepository.saveAll(menus);
//    }
//
//    private void createOrders(List<User> users, List<Restaurant> restaurants, List<Menu> menus) {
//        Cart cart1 = new Cart();
//        cart1.setUsersId(users.get(0).getId());
//        Cart cart2 = new Cart();
//        cart1.setUsersId(users.get(1).getId());
//        Cart cart3 = new Cart();
//        cart1.setUsersId(users.get(2).getId());
//        Cart cart4 = new Cart();
//        cart1.setUsersId(users.get(3).getId());
//        Cart savedCart1 = cartRepository.save(cart1);
//        Cart savedCart2 = cartRepository.save(cart2);
//        Cart savedCart3 = cartRepository.save(cart3);
//        Cart savedCart4 = cartRepository.save(cart4);
//        List<CartItem> cartItems = List.of(
//                new CartItem(1L, 17000 * 1L, savedCart1, menus.get(0)), //뿌링클
//                new CartItem(1L, 5000 * 1L, savedCart1, menus.get(1)), //치즈볼
//                new CartItem(1L, 2000 * 1L, savedCart1, menus.get(2)), //펩시제로
//                new CartItem(2L, 6000 * 2L, savedCart2, menus.get(3)), //햄버거
//                new CartItem(2L, 3000 * 2L, savedCart2, menus.get(4)), //감자튀김
//                new CartItem(2L, 2000 * 2L, savedCart2, menus.get(5)), //코카콜라
//                new CartItem(3L, 6000 * 3L, savedCart3, menus.get(6)), //짜장면
//                new CartItem(1L, 14000 * 1L, savedCart3, menus.get(7)), //탕수육
//                new CartItem(1L, 2000 * 1L, savedCart3, menus.get(8)), //사이다
//                new CartItem(3L, 14000 * 3L, savedCart4, menus.get(9)), //초밥
//                new CartItem(1L, 3000 * 1L, savedCart4, menus.get(10)),//어묵탕
//                new CartItem(2L, 6000 * 2L, savedCart4, menus.get(11)) //사케
//        );
//        cartItemRepository.saveAll(cartItems);
//        Order order1 = Order.builder()
//                .user(users.get(0))
//                .address("제주도 어딘가")
//                .phoneNumber("010-1111-1111")
//                .restaurantName(restaurants.get(0).getName())
//                .build();
//        List<OrderDetail> orderDetails1 = new ArrayList<>();
//        int totalPrice = 0;
//        for (int i = 0; i < 3; ++i) {
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .menuName(menus.get(i).getName())
//                    .price(menus.get(i).getPrice())
//                    .quantity(cartItems.get(i).getQuantity().intValue())
//                    .order(order1)
//                    .build();
//            orderDetails1.add(orderDetail);
//        }
//        order1.setTotalPrice();
//        order1.setTotalPrice(cartItems.get(0).getQuantityPrice().intValue());
//
//        Order order2 = Order.builder()
//                .user(users.get(1))
//                .address("경기도 어딘가")
//                .phoneNumber("010-2222-2222")
//                .restaurantName(restaurants.get(1).getName())
//                .build();
//        Order order3 = Order.builder()
//                .user(users.get(2))
//                .address("부산 어딘가")
//                .phoneNumber("010-3333-3333")
//                .restaurantName(restaurants.get(2).getName())
//                .build();
//        Order order4 = Order.builder()
//                .user(users.get(3))
//                .address("대구 어딘가")
//                .phoneNumber("010-4444-4444")
//                .restaurantName(restaurants.get(3).getName())
//                .build();
//    }
//}