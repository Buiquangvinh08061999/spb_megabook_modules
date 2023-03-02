//package com.cg.cloudinary;
//
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class CloudinaryUploadUtil {
//    public final String IMAGE_UPLOAD_FOLDER = "product_avatar";
//
//
////    public Map buildImageUploadParams(ProductAvatar productAvatar) {
////        if (productAvatar == null || productAvatar.getId() == null)
////            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");
////
////        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, productAvatar.getId());
////
////        return ObjectUtils.asMap(
////                "public_id", publicId,
////                "overwrite", true,
////                "resource_type", "image"
////        );
////    }
////
////    public Map buildImageUploadParams(EmployeeAvatar employeeAvatar) {
////        if (employeeAvatar == null || employeeAvatar.getId() == null)
////            throw new DataInputException("Không thể upload hình ảnh của nhân viên chưa được lưu");
////
////        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, employeeAvatar.getId());
////
////        return ObjectUtils.asMap(
////                "public_id", publicId,
////                "overwrite", true,
////                "resource_type", "image"
////        );
////    }
////
////    public Map buildImageDestroyParams(Product product, String publicId) {
////        if (product == null || product.getId() == null)
////            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");
////
////        return ObjectUtils.asMap(
////                "public_id", publicId,
////                "overwrite", true,
////                "resource_type", "image"
////        );
////    }
//
//}
//
