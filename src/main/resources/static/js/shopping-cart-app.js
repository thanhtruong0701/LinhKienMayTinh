const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function($scope, $http){
	$scope.cart = { // co tac dung trong toan bo giao dien
		items:[],
		// Thêm sản phẩm vào giỏ hàng
		add(id){
			var item = this.items.find(item => item.id == id); // kiểm tra xem trong items có sản phẩm mới thêm vào khoong
			if(item){ // nếu có thì chỉ tăng số lượng
				item.qty++;
				this.saveToLocalStorage();
			}else{ // nếu chưa có thì tải sp từ trên server về thông qua rest
				$http.get(`/rest/shop/${id}`).then(resp => {
					alert('Đã thêm sản phẩm vào giỏ hàng !');
					resp.data.qty = 1; // sau khi tải về gán sl = 1;
					this.items.push(resp.data); // thêm vào ds giỏ hàng (items)
					this.saveToLocalStorage(); // lưu vào local
				})
			}
		},
		
		// Xóa sản phẩm khỏi giỏ hàng
		remove(id){
			var index = this.items.findIndex(item => item.id == id); // tìm vị trí của sp trong giỏ hàng thông qua id truyền vào
			this.items.splice(index, 1); // khi có vị trí dùng phương thức splice để xóa 1 phần tử ra khỏi mảng
			this.saveToLocalStorage(); // xóa xong thì lưu lại
		},
		
		// Xóa sạch giỏ hàng
		clear(){
			this.items = []; // cho mảng thành rỗng
			this.saveToLocalStorage(); // lưu lại
		},
		
		// Tính tổng số lượng các mặt hàng trong giỏ hàng
		get count(){
			return this.items.map(item => item.qty) // dùng map để lấy qty
							 .reduce((total, qty) => total += qty, 0); // tính tổng qty
		},
		
		// Tính tổng thành tiền của các mặt hàng trong giỏ hàng
		get amount(){
			return this.items.map(item => item.qty * item.product_price) // dùng map để lấy qty * price ra giá
							 .reduce((total, qty) => total += qty, 0);
		},
		
		// Hàm lưu vào local
		saveToLocalStorage(){
			var json = JSON.stringify(angular.copy(this.items)); // dùng angular copy (items) và đổi mảng mặt hàng (items) sang JSON
			localStorage.setItem("cart", json); // lưu chuối json và localStorage với tên là "cart"
		},
		
		// Đọc giỏ hàng từ localStorage
		loadFromLocalStorage(){
			var json = localStorage.getItem("cart"); // lấy "cart" từ localstorage
			json != null ? this.items = JSON.parse(json) : this.items =  []; // nếu có thì chuyển sang JSON và gán vào item còn không thì gán rỗng
		},
		
		// tăng số lượng sp khi bấm
		increase(id){
			var item = this.items.find(item => item.id == id); // tìm item thông qua id
			item.qty++;
			this.saveToLocalStorage();
		},
		
		// giảm số lượng sản phẩm khi bấm
		reduce(id){
			var item = this.items.find(item => item.id == id); // tìm item thông qua id
			if(item.qty > 1){ // sl lớn hơn 1 mới cho giảm
				item.qty--;
			}
			this.saveToLocalStorage();
		}
	}
	
	$scope.payments = 1;
	$scope.initialize = function() {
		// load brands
		$http.get(`/rest/orderMethods`).then(resp => {
			$scope.items = resp.data;
		});
		console.log($scope.items)
		$scope.payments = 1;
	};
	
	var pay = 0;
	$scope.choosePayment = function() {
		pay = angular.copy($scope.payments);
	}
	
	$scope.initialize(); // load pt thanh toán
	$scope.cart.loadFromLocalStorage(); // tải lại toàn bộ mặt hàng trong local khi ứng dụng khởi tạo
	
	$scope.order = {
		createDate: new Date(), // lấy ngày hiện tại
		address: "",
		phonenumber: "",
		orderStatus:{id: "CXN"}, // CHỜ XN
		orderMethod:{id: 1}, // thanh toán khi nhận hàng
		account: {username: $("#username").val()}, // lấy username trong input có id là username
		get orderDetails() { // các mặt hàng trong giỏ chuyển sang chi tiết hóa đơn
			return $scope.cart.items.map(item => {
				return {
					product:{id: item.id},
					price: item.product_price,
					quantity: item.qty
				}
			});
		},
 		dh(){
			if (this.address == null || this.address == undefined || this.address.length <= 0) {
				alert("Vui lòng nhập địa chỉ giao hàng !")
			} else if(this.phonenumber == null || this.phonenumber == undefined || this.phonenumber.length <= 7) {
				alert("Vui lòng nhập số điện thoại !")
			} else{
				var order = angular.copy(this); // lấy order hiện tại
				$http.post("/rest/order", order).then(resp => { // post order lên đại chỉ
					alert("Đặt hàng thành công !");
					location.href = "/order/detail/" +resp.data.id; // chuyển sang trang chi tiết đơn hàng
					$scope.cart.clear(); // xóa giỏ hàng
				}).catch(error => {
					alert("Đặt hàng thất bại !");
					console.log(error);
				})
			}
		}
	}
	
	$scope.orderPaypal = {
		createDate: new Date(), // lấy ngày hiện tại
		address: "",
		phonenumber: "",
		orderStatus:{id: "CXN"}, // CHỜ XN
		orderMethod:{id: 2}, // thanh toán Paypal
		account: {username: $("#username").val()}, // lấy username trong input có id là username
		get orderDetails() { // các mặt hàng trong giỏ chuyển sang chi tiết hóa đơn
			return $scope.cart.items.map(item => {
				return {
					product:{id: item.id},
					price: item.product_price,
					quantity: item.qty
				}
			});
		},
 		dh(){
			if (this.address == null || this.address == undefined || this.address.length <= 0) {
				alert("Vui lòng nhập địa chỉ giao hàng !")
			} else if(this.phonenumber == null || this.phonenumber == undefined || this.phonenumber.length <= 7) {
				alert("Vui lòng nhập số điện thoại !")
			} else{
				var order = angular.copy(this); // lấy order hiện tại
				$http.post("/rest/order", order).then(resp => { // post order lên đại chỉ
					$scope.cart.clear(); // xóa giỏ hàng
				}).catch(error => {
					alert("Đặt hàng thất bại !");
					console.log(error);
				})
			}
		}
	}
	
	$scope.orderGuest = {
		createDate: new Date(), // lấy ngày hiện tại
		address: "",
		phonenumber: "",
		orderStatus:{id: "CXN"}, // CHỜ XN
		orderMethod:{id: 1}, // thanh toán khi nhận hàng
		get orderDetailGuests() { // các mặt hàng trong giỏ chuyển sang chi tiết hóa đơn
			return $scope.cart.items.map(item => {
				return {
					product:{id: item.id},
					price: item.product_price,
					quantity: item.qty
				}
			});
		},
 		dh(){
			if (this.address == null || this.address == undefined || this.address.length <= 0) {
				alert("Vui lòng nhập địa chỉ giao hàng !")
			} else if(this.phonenumber == null || this.phonenumber == undefined || this.phonenumber.length <= 7) {
				alert("Vui lòng nhập số điện thoại !")
			} else{
			var orderGuest = angular.copy(this); // lấy order hiện tại
			$http.post("/rest/order/guest", orderGuest).then(resp => { // post order lên đại chỉ
				alert("Đặt hàng thành công !");
				location.href = "/orderGuest/detail/" +resp.data.id; // chuyển sang trang chi tiết đơn hàng
				$scope.cart.clear(); // xóa giỏ hàng
			}).catch(error => {
				alert("Đặt hàng thất bại !");
				console.log(error);
			})
			}
		}
	}
	
	$scope.orderGuestPaypal = {
		createDate: new Date(), // lấy ngày hiện tại
		address: "",
		phonenumber: "",
		orderStatus:{id: "CXN"}, // CHỜ XN
		orderMethod:{id: 2}, // thanh toán paypal
		get orderDetailGuests() { // các mặt hàng trong giỏ chuyển sang chi tiết hóa đơn
			return $scope.cart.items.map(item => {
				return {
					product:{id: item.id},
					price: item.product_price,
					quantity: item.qty
				}
			});
		},
 		dh(){
			if (this.address == null || this.address == undefined || this.address.length <= 0) {
				alert("Vui lòng nhập địa chỉ giao hàng !")
			} else if(this.phonenumber == null || this.phonenumber == undefined || this.phonenumber.length <= 7) {
				alert("Vui lòng nhập số điện thoại !")
			} else{
			var orderGuest = angular.copy(this); // lấy order hiện tại
			$http.post("/rest/order/guest", orderGuest).then(resp => { // post order lên đại chỉ
				$scope.cart.clear(); // xóa giỏ hàng
			}).catch(error => {
				alert("Đặt hàng thất bại !");
				console.log(error);
			})
			}
		}
	}
})