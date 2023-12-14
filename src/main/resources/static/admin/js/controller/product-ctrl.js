app.controller("product-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.cates = [];
	$scope.models = [];
	$scope.brands = [];
	$scope.form = {};
	$scope.newProduct = {};

	$scope.initialize = function() {
		//load product
		$http.get("/rest/shop").then(resp => {
			$scope.items = resp.data;
		});

		//load categories
		$http.get("/rest/categories").then(resp => {
			$scope.cates = resp.data;
		});

		//load models
		$http.get("/rest/models").then(resp => {
			$scope.models = resp.data;
		});

		//load brands
		$http.get("/rest/brands").then(resp => {
			$scope.brands = resp.data;
		});
	}

	$scope.initialize();

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab("show")

		// Tạo một phần tử HTML ảo để lấy nội dung văn bản
		var product_info_container = angular.element('<div>' + $scope.form.product_info + '</div>');
		// Extract text content from the virtual element
		var infoContent = "";
		angular.forEach(product_info_container.find('span'), function(span) {
			infoContent += angular.element(span).text().trim() + '\n';
		});
		
		$scope.form.product_info = infoContent.trim();


		// Tạo một phần tử HTML ảo để lấy nội dung văn bản
		var product_benefits_container = angular.element('<div>' + $scope.form.product_benefits + '</div>');
		var benefitsContent = "";
		angular.forEach(product_benefits_container.find('span'), function(span) {
			benefitsContent += angular.element(span).text().trim() + '\n';
		});
		
		$scope.form.product_benefits = benefitsContent.trim();

		// Tạo một đối tượng div để chứa nội dung HTML product_desc
		var product_desc_container = document.createElement("div");
		product_desc_container.innerHTML = $scope.form.product_desc;

		// Lấy tất cả các thẻ <td>
		var tdElements = product_desc_container.getElementsByTagName("td")
		var thElements = product_desc_container.getElementsByTagName("th")

		for (var i = 0; i < tdElements.length; i++) {
			thElements[i].setAttribute("style", "width: 200px");
			var tdContent = tdElements[i].innerHTML;
			// Thay thế tất cả thẻ <br> bằng thẻ xuống dòng trong <input>
			var inputContent = tdContent.replace(/<br\s*[\/]?>/gi, '\n');

			// Tính số dòng trong inputContent
			var numberOfLines = (inputContent.match(/\n/g) || []).length + 1;


			// Thay đổi id của textarea để làm cho nó duy nhất
			var textareaId = 'content_' + i;

			// Thay thế thẻ <td> bằng thẻ <textarea>
			tdElements[i].innerHTML = '<textarea class="form-control" id="' + textareaId + '"  oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;" rows="' + numberOfLines + '">' + inputContent + '</textarea>';

		}

		document.getElementById("productDesc").innerHTML = product_desc_container.innerHTML;

	}

	//upload image
	$scope.imageChanged = function(files, imageNumber) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post("/rest/upload/images/products", data, {
			transformRequest: angular.identity,
			headers: { "Content-Type": undefined }
		}).then(resp => {
			if (imageNumber === 1) {
				$scope.form.product_img1 = resp.data.name;
			} else if (imageNumber === 2) {
				$scope.form.product_img2 = resp.data.name;
			} else if (imageNumber === 3) {
				$scope.form.product_img3 = resp.data.name;
			} else {
				$scope.form.product_img4 = resp.data.name;
			}

		}).catch(error => {
			alert("Lỗi upload hình ảnh")
			console.log("Error", error);
		})
	}

	//reset Form
	$scope.reset = function() {
		$scope.edit($scope.newProduct)
		$scope.form.product_img1 = ""
		$scope.form.product_img2 = ""
		$scope.form.product_img3 = ""
		$scope.form.product_img4 = ""
	}

	//edit category_product_desc
	$scope.editCategoryProductDesc = function(categoryId) {
		if (categoryId === 1) {
			document.getElementById("productDesc").innerHTML = `<table class="table table-bordered table-striped">
        <tbody>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">CPU</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)"
                        style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">RAM</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)"
                        style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Ổ cứng</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)"
                        style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Card đồ họa</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)"
                        style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Màn hình</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)"
                        style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Cổng giao tiếp</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Bàn phím</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Audio</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Đọc thẻ nhớ</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Chuẩn LAN</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Chuẩn WIFI</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Bluetooth</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Webcam</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Hệ điều hành</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Pin</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Trọng lượng</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Màu sắc</th>
                <td>
                    <textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="ps-2" style="width: 200px;">Kích thước</th>
                <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
            </tr>
        </tbody>
    </table>`
		} else if (categoryId === 2) {
			document.getElementById("productDesc").innerHTML = `    <table class="table table-bordered table-striped">
        <tbody>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Socket</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Dòng CPU</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">CPU</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Số nhân</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Số luồng</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tốc độ Turbo tối đa của P-core</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tốc độ Turbo tối đa của E-core</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tốc độ cơ bản của P-core</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tốc độ cơ bản của E-core</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Điện năng tiêu thụ (Công suất)</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Bộ nhớ đệm</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Bo mạch chủ tương thích</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Bộ nhớ hỗ trợ tối đa</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Loại bộ nhớ</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Nhân đồ họa tích hợp</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Phiên bản PCI Express</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Số lượng PCIe lanes</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
        </tr>
        </tbody>
      </table>`
		} else if (categoryId === 3) {
			document.getElementById("productDesc").innerHTML = `      <table class="table table-bordered table-striped">
        <tbody>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Thương hiệu</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Model</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">tỷ lệ màn hình</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tấm nền</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Kích thước</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Độ sáng</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tỷ lệ tương phản</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Độ phân giải</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tần số quét</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Thời gian đáp ứng</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Góc nhìn</th>
            <td><textarea class="form-control"  rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Cổng giao tiếp</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>

          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Tính năng</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>

          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Kích thước ngoài</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>
          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Khối lượng</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>

          </tr>
          <tr>
            <th scope="row" class="ps-2" style="width: 200px;">Mức tiêu thụ điện</th>
            <td><textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea></td>

          </tr>
      
        </tbody>
      </table>`
		} else {
			document.getElementById("productDesc").innerHTML = `<textarea class="form-control" rows="1" oninput="showAllContent(this)" style="overflow-y: hidden; resize:none; width: 100%; word-wrap: break-word;overflow-wrap: break-word;"></textarea>`
		}
	}

	//thêm sản phẩm mới
	$scope.create = function() {
		var item = angular.copy($scope.form)

		// Lấy giá trị của textarea
		var infoContent = document.getElementById("productInfo").value;
		// Tách nội dung văn bản thành mảng các dòng
		var lines = infoContent.trim().split('\n');
		// Tạo một mảng để lưu trữ các dòng HTML được định dạng
		var formattedLines = [];
		// Bọc mỗi dòng trong thẻ <li> và <span> và đẩy vào mảng formattedLines
		for (var i = 0; i < lines.length; i++) {
			// Tách tên thuộc tính và giá trị
			var parts = lines[i].split(':');
			// Kiểm tra xem có đúng hai phần tử không
			if (parts.length === 2) {
				var attributeName = parts[0].trim();
				var attributeValue = parts[1].trim();

				// Tạo chuỗi HTML với thẻ <li>, <span>, và <strong>
				var formattedLine = '<li><span style="font-size:18px"><strong>' + attributeName + ':</strong> ' + attributeValue + '</span></li>';
				formattedLines.push(formattedLine);
			}

		}
		// Nếu muốn đặt nó trong thẻ <ul> với kiểu danh sách không có dấu chấm
		var formattedHTML = '<ul style="list-style: none">' + formattedLines.join('') + '</ul>';
		item.product_info = formattedHTML
		
		
		/////////////////////////
		var benefitsContent = document.getElementById("productBenefits").value;
		// Tách nội dung văn bản thành mảng các dòng
		var lines = benefitsContent.trim().split('\n');
		// Tạo một mảng để lưu trữ các dòng HTML được định dạng
		var formattedLines = [];
		// Bọc mỗi dòng trong thẻ <p> và <span> và đẩy vào mảng formattedLines
		for (var i = 0; i < lines.length; i++) {
			var formattedLine = '<p class="mb-1"><span style="font-size:18px">' + lines[i].trim() + '</span></p>';
			formattedLines.push(formattedLine);
		}
		// Nối các dòng HTML đã định dạng thành một chuỗi HTML duy nhất
		var formattedHTML = formattedLines.join('');
		// Gán chuỗi HTML đã định dạng vào $scope.form.product_benefits 
		item.product_benefits = formattedHTML;

		////////////////////////////////////////////////
		var tempElement = document.createElement("div")
		tempElement.innerHTML = document.getElementById("productDesc").innerHTML
		var tdElements = tempElement.getElementsByTagName("td")

		var textareas = document.getElementById("productDesc").querySelectorAll("textarea");

		for (var i = 0; i < textareas.length; i++) {
			var textareaContent = textareas[i].value
			tdElements[i].innerHTML = textareaContent
		}
		
		item.product_desc = tempElement.innerHTML
		console.log(item)
		
		//post san pjam len restController
		$http.post("/rest/shop",item).then(resp =>{
			$scope.items.push(resp.data);
			$scope.reset();
			alert("Thêm sản phẩm mới thành công")
		}).catch(error =>{
			alert("lỗi thêm mới sản phẩm")
			console.log(error);
		})
	}
	
	//Cập nhật sản phẩm
	$scope.update = function(){
		var item = angular.copy($scope.form)
		// Lấy giá trị của textarea
		var infoContent = document.getElementById("productInfo").value;
		// Tách nội dung văn bản thành mảng các dòng
		var lines = infoContent.trim().split('\n');
		// Tạo một mảng để lưu trữ các dòng HTML được định dạng
		var formattedLines = [];
		// Bọc mỗi dòng trong thẻ <li> và <span> và đẩy vào mảng formattedLines
		for (var i = 0; i < lines.length; i++) {
			// Tách tên thuộc tính và giá trị
			var parts = lines[i].split(':');
			// Kiểm tra xem có đúng hai phần tử không
			if (parts.length === 2) {
				var attributeName = parts[0].trim();
				var attributeValue = parts[1].trim();

				// Tạo chuỗi HTML với thẻ <li>, <span>, và <strong>
				var formattedLine = '<li><span style="font-size:18px"><strong>' + attributeName + ':</strong> ' + attributeValue + '</span></li>';
				formattedLines.push(formattedLine);
			}

		}
		// Nếu muốn đặt nó trong thẻ <ul> với kiểu danh sách không có dấu chấm
		var formattedHTML = '<ul style="list-style: none">' + formattedLines.join('') + '</ul>';
		item.product_info = formattedHTML
		
		
		/////////////////////////
		var benefitsContent = document.getElementById("productBenefits").value;
		// Tách nội dung văn bản thành mảng các dòng
		var lines = benefitsContent.trim().split('\n');
		// Tạo một mảng để lưu trữ các dòng HTML được định dạng
		var formattedLines = [];
		// Bọc mỗi dòng trong thẻ <p> và <span> và đẩy vào mảng formattedLines
		for (var i = 0; i < lines.length; i++) {
			var formattedLine = '<p class="mb-1"><span style="font-size:18px">' + lines[i].trim() + '</span></p>';
			formattedLines.push(formattedLine);
		}
		// Nối các dòng HTML đã định dạng thành một chuỗi HTML duy nhất
		var formattedHTML = formattedLines.join('');
		// Gán chuỗi HTML đã định dạng vào $scope.form.product_benefits 
		item.product_benefits = formattedHTML;

		////////////////////////////////////////////////
		var tempElement = document.createElement("div")
		tempElement.innerHTML = document.getElementById("productDesc").innerHTML
		var tdElements = tempElement.getElementsByTagName("td")

		var textareas = document.getElementById("productDesc").querySelectorAll("textarea");

		for (var i = 0; i < textareas.length; i++) {
			var textareaContent = textareas[i].value
			tdElements[i].innerHTML = textareaContent
		}
		
		item.product_desc = tempElement.innerHTML
		
		$http.put(`/rest/shop/${item.id}`, item).then(resp =>{
			var index =$scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công")
		}).catch(error =>{
			alert("Lỗi cập nhấ sản phẩm")
			console.log(error)
		})
	}
	
	//Delete sản phẩm
	$scope.delete = function(item){
		$http.delete(`/rest/shop/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index,1);
			$scope.reset();
			alert("Xóa sản phẩm thành công")
		}).catch(error => {
			alert("lỗi xóa sản phẩm")
			console.log(error)
		})
	}
	


})