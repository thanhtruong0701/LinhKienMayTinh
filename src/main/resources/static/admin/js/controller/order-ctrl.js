app.controller("order-ctrl", function($scope, $http){
	
	$scope.initialize = function(){
		$http.get("/rest/accounts").then(resp => {
			$scope.accounts = resp.data; // lấy list accounts
		})
		$http.get("/rest/orderStatuss").then(resp => {
			$scope.orderStatuss = resp.data; // lấy list orderStatus
		})
		$http.get("/rest/orderMethods").then(resp => {
			$scope.orderMethods = resp.data; // lấy list orderStatus
		})

		$http.get("/rest/ordersAdmin").then(resp => {
			$scope.orders = resp.data; // lấy list orders
			$scope.orders.forEach(order => {
				order.createDate = new Date(order.createDate)
			})
		
		});
		$scope.reset();
	}
	
	$scope.reset = function(){
		$scope.form = {
			orderStatus:{id: 'CXN'},
			createDate: new Date()
		}
	}
	
	$scope.edit = function(oder){
		$scope.form = angular.copy(oder);
		$(".nav-tabs a:eq(0)").tab("show");
	}
	
	$scope.details = function(order) {
		$http.get(`/rest/ordersAdmin/detail/${order.id}`).then(resp => {
			$scope.orderDetails = resp.data;
		})
		$(".nav-tabs a:eq(2)").tab("show");
	}

	$scope.update = function(){
		var order = angular.copy($scope.form);
		$http.put(`/rest/ordersAdmin/${order.id}`, order).then(resp => {
			var index = $scope.orders.findIndex(od => od.id == order.id);
			$scope.orders[index] = order;
			alert("Cập nhật đơn hàng thành công!");
			location.reload();
			$(".nav-tabs a:eq(1)").tab("show");
		})
		.catch(error => {
			alert("Lỗi cập nhật đơn hàng!");
			console.log("Error", error);
		});
	}
	
	$scope.confirm = function(order){
		if(confirm("Bạn muốn xác nhận gửi đơn hàng này ?")){
				$http.put(`/rest/ordersAdmin/status/${order.id}`).then(resp => {
				var index = $scope.orders.findIndex(od => od.id == order.id);
				$scope.orders[index] = order;
				alert("Xác nhận đơn hàng thành công !");
				location.reload();
			})
			.catch(error => {
				alert("Xác nhận đơn hàng thất bại !");
				console.log("Error", error);
			});
		}
	}

	$scope.delete = function(order){
		if(confirm("Bạn muốn xóa đơn hàng này?")){
			$http.delete(`/rest/ordersAdmin/${order.id}`).then(resp => {
				var index = $scope.orders.findIndex(od => od.id == order.id);
				$scope.orders.splice(index, 1);
				$scope.reset();
				alert("Xóa đơn hàng thành công !");
			}).catch(error => {
				alert("Lỗi xóa đơn hàng !");
				console.log("Error", error);
			})
		}
	}
	
	$scope.initialize();
	
	$scope.pager = {
		page: 0,
		size: 10,
		get orders(){
			if(this.page < 0){
				this.last();
			}
			if(this.page >= this.count){
				this.first();
			}
			var start = this.page*this.size;
			return $scope.orders.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0 * $scope.orders.length / this.size);
		},
		get array(){
			return new Array(Math.ceil(1.0 * $scope.orders.length / this.size));
		}
		,
		gotoPage(pageIndex){
			this.page = pageIndex;
		},
		first(){
			this.page = 0;
		},
		last(){
			this.page = this.count - 1;
		},
		next(){
			this.page++;
		},
		prev(){
			this.page--;
		}
	}
});