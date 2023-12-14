app.controller("orderGuest-ctrl", function($scope, $http){
	
	$scope.initialize = function(){
		$http.get("/rest/orderStatuss").then(resp => {
			$scope.orderStatuss = resp.data; // lấy list orderStatus
		})
		$http.get("/rest/orderMethods").then(resp => {
			$scope.orderMethods = resp.data; // lấy list orderStatus
		})

		$http.get("/rest/ordersGuestAdmin").then(resp => {
			$scope.ordersGuest = resp.data; // lấy list orders
			$scope.ordersGuest.forEach(orderGuest => {
				orderGuest.createDate = new Date(orderGuest.createDate)
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
	
	$scope.edit = function(orderGuest){
		$scope.form = angular.copy(orderGuest);
		$(".nav-tabs a:eq(0)").tab("show");
	}
	
	$scope.details = function(orderGuest) {
		$http.get(`/rest/ordersGuestAdmin/detail/${orderGuest.id}`).then(resp => {
			$scope.orderDetailsGuest = resp.data;
		})
		console.log($scope.orderDetailsGuest)
		$(".nav-tabs a:eq(2)").tab("show");
	}
	
	$scope.create = function(){
		var orderGuest = angular.copy($scope.form);
		$http.post(`/rest/ordersGuestAdmin`, orderGuest).then(resp => {
			resp.data.createDate = new Date(resp.data.createDate)
			$scope.items.push(resp.data);
			$scope.reset();
			alert("Tạo mới đơn hàng thành công!");
			location.reload();
			$(".nav-tabs a:eq(1)").tab("show");
		}).catch(error => {
			alert("Lỗi tạo mới đơn hàng !");
			console.log("Error", error);
		});
	}

	$scope.update = function(){
		var orderGuest = angular.copy($scope.form);
		$http.put(`/rest/ordersGuestAdmin/${orderGuest.id}`, orderGuest).then(resp => {
			var index = $scope.ordersGuest.findIndex(odg => odg.id == orderGuest.id);
			$scope.ordersGuest[index] = orderGuest;
			alert("Cập nhật đơn hàng thành công!");
			location.reload();
			$(".nav-tabs a:eq(1)").tab("show");
		})
		.catch(error => {
			alert("Lỗi cập nhật đơn hàng!");
			console.log("Error", error);
		});
	}
	
	$scope.confirm = function(orderGuest){
		if(confirm("Bạn muốn xác nhận gửi đơn hàng này ?")){
				$http.put(`/rest/ordersGuestAdmin/status/${orderGuest.id}`).then(resp => {
				var index = $scope.ordersGuest.findIndex(odg => odg.id == orderGuest.id);
				$scope.ordersGuest[index] = orderGuest;
				alert("Xác nhận đơn hàng thành công !");
				location.reload();
			})
			.catch(error => {
				alert("Xác nhận đơn hàng thất bại !");
				console.log("Error", error);
			});
		}
	}

	$scope.delete = function(orderGuest){
		if(confirm("Bạn muốn xóa đơn hàng này?")){
			$http.delete(`/rest/ordersGuestAdmin/${orderGuest.id}`).then(resp => {
				var index = $scope.ordersGuest.findIndex(odg => odg.id == orderGuest.id);
				$scope.ordersGuest.splice(index, 1);
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
		get ordersGuest(){
			if(this.page < 0){
				this.last();
			}
			if(this.page >= this.count){
				this.first();
			}
			var start = this.page*this.size;
			return $scope.ordersGuest.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0 * $scope.ordersGuest.length / this.size);
		},
		get array(){
			return new Array(Math.ceil(1.0 * $scope.ordersGuest.length / this.size));
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