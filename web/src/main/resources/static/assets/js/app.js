class App{

    //Domain
    static DOMAIN = location.origin;

    //API
    static BASE_URL_PROVINCE = "https://vapi.vnappmob.com/api/province";
//users
    static BASE_URL_USER = this.DOMAIN + "/api/users";
    static BASE_URL_ROLE = this.DOMAIN + "/api/roles";
//customers
    static BASE_URL_CUSTOMER = this.DOMAIN + "/api/customers";
//products
    static BASE_URL_PRODUCT = this.DOMAIN + "/api/products";
    static BASE_URL_CATEGORY = this.DOMAIN + "/api/categories";
    static BASE_URL_UNIT = this.DOMAIN + "/api/units";
    static BASE_URL_PUBLISHER = this.DOMAIN + "/api/publishers";
//items
    static BASE_URL_ITEM = this.DOMAIN + "/api/items";
    static BASE_URL_SUPPLIER = this.DOMAIN + "/api/suppliers";
    static BASE_URL_WAREHOUSE = this.DOMAIN + "/api/warehouses"
//order-items
    static BASE_URL_ORDER_ITEM = this.DOMAIN + "/api/order-items";
//orders
    static BASE_URL_ORDER = this.DOMAIN + "/api/orders";
//order-status

    static BASE_URL_ORDER_STATUS = this.DOMAIN + "/api/order-status"
//import-orders
    static BASE_URL_IMPORT_ORDER = this.DOMAIN + "/api/import-orders"

//Import Order
    static BASE_URL_IMPORT_ORDER = this.DOMAIN + "/api/import-orders";

//Import OrderItem
    static BASE_URL_IMPORT_ORDER_STATUS = this.DOMAIN + "/api/import-order-status";


//Auth login
    static BASE_URL_AUTH = this.DOMAIN + "/api/auth"



    //Cloudinary
    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/dd2kxcbou/image/upload";
    static BASE_URL_CLOUD_VIDEO = "https://res.cloudinary.com/dd2kxcbou/video/upload";
    static BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";

    //Error/Success
    static ERROR_400 = "error400";
    static ERROR_403 = "error403";
    static ERROR_404 = "error404";
    static ERROR_500 = "error500";
    static SUCCESS_CREATED = "Successful data generation !";
    static SUCCESS_UPDATED = "Data update successful !";
    static SUCCESS_DELETED = "Delete product successfully !";



    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                showConfirmButton: false,
                timer: 2500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                position: 'top-end',
                text: t,
                timer: 2500
            })
        }

        /*Hiển thị xóa*/
        static showConfirm(title, content) {
            return Swal.fire({
                title: title,
                text: content,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
            });
        }

        /*Hiển thị xác nhận chuyển trạng thái đơn hàng nên tắt icon*/
        static showConfirmUpdateStatus() {
            return Swal.fire({
                title: 'Xác nhận cập nhật trạng thái cho đơn hàng này?',
                icon: 'warning',   //success(đổi hình ảnh)
                showCancelButton: true,
                confirmButtonColor: '#3085D6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy thao tác!',

            });
        }
    }

    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                message: m,
            });
        }

        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                position: 'topRight',
                message: m,
            });
        }

    }

    static formatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }


}

class Customer {
    constructor(id, email, fullName, phone, urlImage, locationRegion, note, deleted, createAt, createBy, updateAt, updateBy) {
        this.id = id;
        this.email = email;
        this.fullName= fullName;
        this.phone = phone;
        this.urlImage = urlImage;
        this.note = note;
        // this.deleted = deleted;
        // this.createAt = createAt;
        // this.createBy = createBy;
        // this.updateAt = updateAt;
        // this.updateBy = updateBy;
        this.locationRegion = locationRegion;
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Product {
    constructor(id, title, sku, author, urlImage, describe, slug, costPrice, price, deleted, category, unit, publisher) {
        this.id = id;
        this.title = title;
        this.sku = sku;
        this.author = author;
        this.urlImage = urlImage;
        this.describe = describe;
        this.slug = slug;
        this.costPrice = costPrice;
        this.price = price;
        this.deleted = deleted;
        this.category = category;
        this.unit = unit;
        this.publisher = publisher;
    }
}

class Category {
    constructor(id, slug, title) {
        this.id = id;
        this.slug = slug;
        this.title = title;
    }
}

class Unit {
    constructor(id, title) {
        this.id = id;
        this.title =title;
    }
}

class Publisher {
    constructor(id, title) {
        this.id = id;
        this.title = title;
    }
}


class Order {
    constructor(id, grandTotal, describe, customer, orderStatus) {
        this.id = id;
        this.grandTotal = grandTotal;
        this.describe = describe;
        this.customer = customer;
        this.orderStatus = orderStatus;
    }
}

//Vinh làm
class Item {
    constructor(id, costPrice, totalCostPrice, price, quantity, sold, available, defective, product) {
        this.id = id;
        this.costPrice = costPrice;
        this.totalCostPrice = totalCostPrice;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
        this.available = available;
        this.defective = defective;
        this.product = product;
    }
}


class OrderItemCreate {
    constructor(id, quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}

class OrderStatus {
    constructor(id,titleEn,titleVi) {
        this.id = id;
        this.titleEn = titleEn;
        this.titleVi = titleVi;
    }
}


class OrderItem{
    constructor(id, productTitle, productSku, itemQuantity, itemPrice ,totalPrice, order, item, createAt,updateAt){
        this.id = id;
        this.productTitle = productTitle;
        this.productSku = productSku;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.order = order;
        this.item =item;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}

class Supplier {
    constructor(id, title, taxCode, note, fullName, email, phone, locationRegion) {
        this.id = id;
        this.title = title;
        this.taxCode = taxCode;
        this.note = note;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
    }
}

class Warehouse {
    constructor(id, title, email, phone, locationRegion) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
    }
}


class ImportOrder {
    constructor(id, createBy,grandQuantity, grandTotal, user, supplier, warehouse, importOrderStatus, createAt,updateAt){
        this.id = id;
        this.createBy = createBy
        this.grandQuantity = grandQuantity;
        this.grandTotal = grandTotal;
        this.user = user;
        this.supplier = supplier;
        this.warehouse = warehouse;
        this.importOrderStatus = importOrderStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}

class ImportOrderItem {
    constructor(id, productTitle, productSku, itemQuantity, available, itemPrice, totalPrice, importOrder, item) {
        this.id = id;
        this.productTitle = productTitle;
        this.productSku = productSku;
        this.itemQuantity = itemQuantity;
        this.available = available;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.importOrder = importOrder;
        this.item = item;
    }
}

class ImportOrderStatus {
    constructor(id, titleEn, titleVi) {
        this.id = id;
        this.titleEn = titleEn;
        this.titleVi = titleVi;
    }
}
class ImportItemCreate {
    constructor(id, quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}



// Vinh làm Order tất cả data, hiển thị dữ liệu ở phần Tổng quan
class OrderData {
    constructor(grandTotalDay, grandTotalMonth, countALL, countStatusPending, countStatusCompleted, countStatusCancel) {
        this.grandTotalDay = grandTotalDay;
        this.grandTotalMonth = grandTotalMonth;
        this.countALL = countALL;
        this.countStatusPending = countStatusPending;
        this.countStatusCompleted = countStatusCompleted;
        this.countStatusCancel = countStatusCancel;
    }
}


class User {
    constructor(id, username, password, fullName, phone, urlImage,role, createdAt,updatedAt) {
        this.id = id;
        this.username= username;
        this.password = password
        this.fullName = fullName;
        this.phone = phone;
        this.urlImage = urlImage;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
class Role {
    constructor(id, code, name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}

//Vinh làm sp để lấy được tổng số lượng, ở phần items
class SumQuantityItem {
    constructor(sumQuantity, sumAvailable, sumSold) {
        this.sumQuantity = sumQuantity;
        this.sumAvailable = sumAvailable;
        this.sumSold = sumSold;
    }
}

//Vinh làm sp để lấy đếm số lượng,và tổng đơn hàng đang chơ nhập ở phần import_order
class CountAndSumImportOrder {
    constructor(count, sumQuantity, sum) {
        this.count = count;
        this.sumQuantity = sumQuantity;
        this.sum = sum;

    }
}






