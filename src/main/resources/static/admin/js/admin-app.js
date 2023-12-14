app = angular.module("app", ["ngRoute"]);
app.config(function ($routeProvider) {
  $routeProvider
    .when("/product", {
      templateUrl: "/admin/product/index.html",
      controller: "product-ctrl"
    })
    .when("/authorize", {
      templateUrl: "/admin/authority/index.html",
      controller: "authority-ctrl"
    })
    .when("/unauthorized", {
      templateUrl: "/admin/authority/unauthorized.html",
      controller: "authority-ctrl"
    })
    .when("/order", {
	  templateUrl: "/admin/order/index.html",
	  controller: "order-ctrl"
	})
	.when("/orderGuest", {
	  templateUrl: "/admin/orderGuest/index.html",
	  controller: "orderGuest-ctrl"
	})
    .otherwise({
      templateUrl: "/admin/home/index.html",
      controller: "home-ctrl"
    });
});

