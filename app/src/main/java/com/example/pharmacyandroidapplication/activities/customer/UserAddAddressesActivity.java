package com.example.pharmacyandroidapplication.activities.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.admin.AddProductActivity;
import com.example.pharmacyandroidapplication.activities.admin.ProductManagementActivity;
import com.example.pharmacyandroidapplication.models.ShipmentInf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAddAddressesActivity extends AppCompatActivity {
    private EditText add_address_txt_name, add_address_txt_phone, add_address_txt_address_detail;
    private Button btn_save_add_address;
    private Spinner add_address_spn_province, add_address_spn_district, add_address_spn_commune;
    private ArrayAdapter<CharSequence> provinceAdapter, districtAdapter, communeAdapter;
    private String selectedProvince, selectedDistrict, selectedCommune;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference addressRef = database.getReference("shipment details");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_input);
        add_address_spn_province = findViewById(R.id.add_address_spn_province);
        add_address_spn_district = findViewById(R.id.add_address_spn_district);
        add_address_txt_address_detail = findViewById(R.id.add_address_txt_address_detail);
        btn_save_add_address = findViewById(R.id.btn_save_add_address);
        add_address_txt_name = findViewById(R.id.add_address_txt_name);
        add_address_txt_phone = findViewById(R.id.add_address_txt_phone);
        provinceAdapter = ArrayAdapter.createFromResource(this, R.array.province, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_address_spn_province.setAdapter(provinceAdapter);
        add_address_spn_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProvince = add_address_spn_province.getSelectedItem().toString();
                {
                    switch (selectedProvince) {
                        case "Chọn tỉnh thành phố": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_default_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Thành phố Hà Nội": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohanoi_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hà Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhagiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Cao Bằng": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhcaobang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bắc Kạn": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbackan_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Tuyên Quang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhtuyenquang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Lào Cai": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhlaocai_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Điện Biên": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhdienbien_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Lai Châu": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhlaichau_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Sơn La": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhsonla_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Yên Bái": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhyenbai_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hoà Bình": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhoabinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Thái Nguyên": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhthainguyen_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Lạng Sơn": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhlangson_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Quảng Ninh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhquangninh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bắc Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbacgiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Phú Thọ": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhphutho_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Vĩnh Phúc": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhvinhphuc_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bắc Ninh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbacninh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hải Dương": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhaiduong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Thành phố Hải Phòng": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohaiphong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hưng Yên": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhungyen_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Thái Bình": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhthaibinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hà Nam": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhanam_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Nam Định": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhnamdinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Ninh Bình": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhninhbinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Thanh Hóa": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhthanhhoa_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Nghệ An": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhnghean_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hà Tĩnh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhatinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Quảng Bình": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhquangbinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Quảng Trị": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhquangtri_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Thừa Thiên Huế": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhthuathienhue_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Thành phố Đà Nẵng": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodanang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Quảng Nam": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhquangnam_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Quảng Ngãi": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhquangngai_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bình Định": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbinhdinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Phú Yên": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhphuyen_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Khánh Hòa": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhkhanhhoa_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Ninh Thuận": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhninhthuan_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bình Thuận": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbinhthuan_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Kon Tum": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhkontum_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Gia Lai": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhgialai_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Đắk Lắk": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhdaklak_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Đắk Nông": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhdaknong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Lâm Đồng": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhlamdong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bình Phước": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbinhphuoc_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Tây Ninh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhtayninh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bình Dương": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbinhduong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Đồng Nai": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhdongnai_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bà Rịa - Vũng Tàu": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbariavungtau_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Thành phố Hồ Chí Minh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohochiminh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Long An": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhlongan_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Tiền Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhtiengiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bến Tre": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbentre_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Trà Vinh": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhtravinh_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Vĩnh Long": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhvinhlong_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Đồng Tháp": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhdongthap_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh An Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhangiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Kiên Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhkiengiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Thành phố Cần Thơ": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocantho_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Hậu Giang": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhhaugiang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Sóc Trăng": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhsoctrang_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Bạc Liêu": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhbaclieu_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        case "Tỉnh Cà Mau": {
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_tinhcamau_district, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            break;
                        }
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    add_address_spn_district.setAdapter(districtAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_address_spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_address_spn_commune = findViewById(R.id.add_address_spn_commune);
                selectedDistrict = add_address_spn_district.getSelectedItem().toString();
                switch (selectedDistrict) {
                    case "Chọn quận huyện": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_default_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Ba Đình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanbadinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hoàn Kiếm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhoankiem_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Tây Hồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quantayho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Long Biên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanlongbien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Cầu Giấy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quancaugiay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Đống Đa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quandongda_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hai Bà Trưng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhaibatrung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hoàng Mai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhoangmai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Thanh Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanthanhxuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sóc Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensocson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đông Anh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonganh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gia Lâm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengialam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Nam Từ Liêm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quannamtuliem_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Trì": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhtrif_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Bắc Từ Liêm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanbactuliem_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mê Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmelinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hà Đông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhadong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Sơn Tây": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixasontay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ba Vì": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbavi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phúc Thọ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuctho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đan Phượng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendanphuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoài Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoaiduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quốc Oai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquocoai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạch Thất": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthachthat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chương Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchuongmy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Oai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhoai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thường Tín": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuongtin_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ứng Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenunghoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỹ Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmyduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hà Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohagiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đồng Văn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendongvan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mèo Vạc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmeovac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Minh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenminh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quản Bạ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquanba_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vị Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvixuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Mê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacme_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoàng Su Phì": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoangsuphi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Xín Mần": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenxinman_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Quang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacquang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quang Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquangbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Cao Bằng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocaobang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bảo Lâm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbaolam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bảo Lạc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbaolac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hà Quảng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhaquang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trùng Khánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrungkhanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hạ Lang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhalang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quảng Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquanghoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoà An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoaan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nguyên Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennguyenbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạch An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthachan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành Phố Bắc Kạn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobackan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Pác Nặm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenpacnam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ba Bể": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbabe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ngân Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennganson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bạch Thông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbachthong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chợ Đồn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchodon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chợ Mới": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchomoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Na Rì": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennari_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tuyên Quang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotuyenquang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lâm Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlambinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Na Hang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennahang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chiêm Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchiemhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hàm Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhamyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensonduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Lào Cai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhpholaocai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bát Xát": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbatxat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Khương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongkhuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Si Ma Cai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensimacai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bảo Thắng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbaothang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bảo Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbaoyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Sa Pa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixasapa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Bàn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanban_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Điện Biên Phủ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodienbienphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị Xã Mường Lay": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixamuonglay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Nhé": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongnhe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Chà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongcha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tủa Chùa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuachua_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuần Giáo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuangiao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Điện Biên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendienbien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Điện Biên Đông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendienbiendong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Ảng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nậm Pồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennampo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Lai Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhpholaichau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tam Đường": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentamduongf_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Tè": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongte_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sìn Hồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensinho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phong Thổ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphongtho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Than Uyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Uyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nậm Nhùn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamnhun_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Sơn La": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphosonla_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quỳnh Nhai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquynhnhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thuận Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuanchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường La": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuongla_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phù Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mộc Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmocchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mai Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmaison_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sông Mã": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensongma_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sốp Cộp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensopcop_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vân Hồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Yên Bái": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphoyenbai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Nghĩa Lộ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixanghialo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lục Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlucyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mù Căng Chải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmucangchai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trấn Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentranyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trạm Tấu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentramtau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Chấn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanchan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hòa Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohoabinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đà Bắc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendabac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lương Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenluongson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kim Bôi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkimboi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cao Phong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencaophong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Lạc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanlac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mai Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmaichau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lạc Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlacson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lạc Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlacthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thái Nguyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothainguyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Sông Công": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphosongcong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Định Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendinhhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Lương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuluong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đồng Hỷ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonghy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Võ Nhai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvonhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đại Từ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendaitu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phổ Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophoyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphubinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Lạng Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhpholangson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tràng Định": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrangdinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Gia": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhgia_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Lãng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanlang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cao Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencaoloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Quan": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanquan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hữu Lũng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhuulung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chi Lăng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchilang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lộc Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlocbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đình Lập": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendinhlap_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hạ Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohalong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Móng Cái": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphomongcai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Cẩm Phả": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocampha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Uông Bí": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphouongbi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Liêu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhlieu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiên Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentienyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đầm Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendamha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hải Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhaiha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ba Chẽ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbache_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vân Đồn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvandon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Đông Triều": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixadongtrieu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Quảng Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaquangyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cô Tô": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencoto_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bắc Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobacgiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Thế": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenthe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lạng Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlanggiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lục Nam": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlucnam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lục Ngạn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlucngan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Động": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensondong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Dũng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyendung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Việt Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvietyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hiệp Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhiephoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Việt Trì": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphoviettri_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Phú Thọ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaphutho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đoan Hùng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendoanhung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hạ Hoà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhahoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Ba": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhba_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phù Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuninhf_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Lập": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenlap_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cẩm Khê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamkhe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tam Nông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentamnong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lâm Thao": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlamthao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Thuỷ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Vĩnh Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphovinhyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phúc Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophucyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lập Thạch": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlapthach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tam Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentamduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tam Đảo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentamdao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Lạc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenlac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Tường": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhtuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sông Lô": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensonglo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bắc Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobacninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Phong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenphong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Quế Võ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaquevo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiên Du": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentiendu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Từ Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotuson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Thuận Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixathuanthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gia Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiabinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lương Tài": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenluongtai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hải Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohaiduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Chí Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphochilinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Sách": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamsach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Kinh Môn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixakinhmon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kim Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkimthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cẩm Giàng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamgiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhgiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gia Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengialoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tứ Kỳ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuky_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ninh Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenninhgiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Miện": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhmien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hồng Bàng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhongbang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Ngô Quyền": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanngoquyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Lê Chân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanlechan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hải An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhaian_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Kiến An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quankienan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Đồ Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quandoson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Dương Kinh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanduongkinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thuỷ Nguyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuynguyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện An Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện An Lão": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanlao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kiến Thuỵ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkienthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiên Lãng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentienlang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Bảo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhbao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cát Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencathai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bạch Long Vĩ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbachlongvi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hưng Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohungyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Lâm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanlam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Văn Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvangiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenmy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Mỹ Hào": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixamyhao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ân Thi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanthi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Khoái Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkhoaichau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kim Động": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkimdong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiên Lữ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentienlu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phù Cừ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphucu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thái Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothaibinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quỳnh Phụ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquynhphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hưng Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhungha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đông Hưng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonghung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thái Thụy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthaithuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiền Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentienhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kiến Xương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkienxuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vũ Thư": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvuthu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phủ Lý": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophuly_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Duy Tiên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaduytien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kim Bảng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkimbang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Liêm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhliem_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Lục": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhluc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lý Nhân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlynhan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Nam Định": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphonamdinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỹ Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmyloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vụ Bản": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvuban_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ý Yên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nghĩa Hưng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennghiahung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Trực": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamtruc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trực Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrucninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Xuân Trường": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenxuantruong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Giao Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiaothuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hải Hậu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhaihau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Ninh Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphoninhbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tam Điệp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotamdiep_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nho Quan": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennhoquan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gia Viễn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiavien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoa Lư": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoalu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Khánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenkhanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kim Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkimson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Mô": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenmo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thanh Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothanhhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Bỉm Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabimson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Sầm Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphosamson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mường Lát": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmuonglat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quan Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquanhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bá Thước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbathuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quan Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquanson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lang Chánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlangchanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ngọc Lặc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenngoclac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cẩm Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạch Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthachthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hà Trung": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhatrung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Định": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyendinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thọ Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthoxuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thường Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuongxuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Triệu Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrieuson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thiệu Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthieuhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoằng Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoanghoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hậu Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhauloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nga Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenngason_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Như Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennhuxuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Như Thanh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennhuthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nông Cống": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennongcong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đông Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendongson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quảng Xương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquangxuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Nghi Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixanghison_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Vinh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphovinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Cửa Lò": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixacualo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Thái Hoà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixathaihoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quế Phong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquephong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quỳ Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquychau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kỳ Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkyson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tương Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuongduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nghĩa Đàn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennghiadan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quỳ Hợp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquyhop_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quỳnh Lưu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquynhluu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Con Cuông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenconcuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Kỳ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanky_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Anh Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Diễn Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendienchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Yên Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenyenthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đô Lương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendoluong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Chương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhchuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nghi Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennghiloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Đàn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamdan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hưng Nguyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhungnguyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hoàng Mai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahoangmai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hà Tĩnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohatinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hồng Lĩnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahonglinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hương Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhuongson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Thọ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenductho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vũ Quang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvuquang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nghi Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennghixuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Can Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencanloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hương Khê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhuongkhe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạch Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthachha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cẩm Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kỳ Anh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkyanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lộc Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlocha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Kỳ Anh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixakyanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành Phố Đồng Hới": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodonghoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Minh Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenminhhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuyên Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuyenhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quảng Trạch": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquangtrach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bố Trạch": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbotrach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quảng Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquangninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lệ Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlethuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Ba Đồn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabadon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Đông Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodongha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Quảng Trị": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaquangtri_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhlinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hướng Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhuonghoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gio Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiolinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đa Krông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakrong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cam Lộ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamlo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Triệu Phong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrieuphong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hải Lăng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhailang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cồn Cỏ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenconco_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Huế": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohue_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phong Điền": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphongdien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quảng Điền": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenquangdien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Vang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuvang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hương Thủy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahuongthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hương Trà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahuongtra_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện A Lưới": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenaluoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Đông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamdong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Liên Chiểu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanlienchieu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Thanh Khê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanthanhkhe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Hải Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanhaichau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Sơn Trà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quansontra_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Ngũ Hành Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quannguhanhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Cẩm Lệ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quancamle_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hòa Vang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoavang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoàng Sa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoangsa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tam Kỳ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotamky_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hội An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohoian_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tây Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentaygiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đông Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonggiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đại Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendailoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Điện Bàn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixadienban_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Duy Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduyxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Quế Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenqueson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Giang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamgiang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phước Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuocson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hiệp Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhiepduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thăng Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthangbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiên Phước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentienphuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Trà My": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbactramy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nam Trà My": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamtramy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Núi Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennuithanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nông Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennongson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Quảng Ngãi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphoquangngai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trà Bồng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrabong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Tịnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensontinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tư Nghĩa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentunghia_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensonha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Tây": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensontay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Minh Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenminhlong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nghĩa Hành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennghiahanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mộ Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmoduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Đức Phổ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaducpho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ba Tơ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbato_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lý Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlyson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Quy Nhơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphoquynhon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hoài Nhơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahoainhon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoài Ân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoaian_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phù Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphumy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Thạnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tây Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentayson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phù Cát": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphucat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã An Nhơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaannhon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuy Phước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuyphuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vân Canh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvancanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tuy Hoà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotuyhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Sông Cầu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixasongcau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đồng Xuân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendongxuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuy An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuyan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sơn Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensonhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sông Hinh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensonghinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tây Hoà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentayhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Hoà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Đông Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixadonghoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Nha Trang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphonhatrang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Cam Ranh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocamranh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cam Lâm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencamlam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vạn Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvanninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Ninh Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaninhhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Khánh Vĩnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkhanhvinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Diên Khánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendienkhanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Khánh Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkhanhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trường Sa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentruongsa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phan Rang-Tháp Chàm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophanrangthapcham_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bác Ái": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ninh Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenninhson_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ninh Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenninhhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ninh Phước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenninhphuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thuận Bắc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuanbac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thuận Nam": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuannam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phan Thiết": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophanthiet_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã La Gi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixalagi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuy Phong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuyphong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbacbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hàm Thuận Bắc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhamthuanbac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hàm Thuận Nam": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhamthuannam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tánh Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanhlinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduclinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hàm Tân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhamtan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Quí": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuqui_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Kon Tum": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphokontum_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk Glei": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakglei_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ngọc Hồi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenngochoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk Tô": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakto_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kon Plông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkonplong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kon Rẫy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkonray_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Sa Thầy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyensathay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tu Mơ Rông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentumorong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ia H' Drai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeniahdrai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Pleiku": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphopleiku_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã An Khê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaankhe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Ayun Pa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaayunpa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện KBang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkbang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đăk Đoa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakdoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chư Păh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchupah_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ia Grai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeniagrai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mang Yang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmangyang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kông Chro": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkongchro_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Cơ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenducco_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chư Prông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchuprong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chư Sê": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchuse_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đăk Pơ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakpo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ia Pa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeniapa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Pa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongpa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Thiện": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuthien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chư Pưh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchupuh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Buôn Ma Thuột": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobuonmathuot_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị Xã Buôn Hồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabuonho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ea H'leo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeneahleo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ea Súp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeneasup_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Buôn Đôn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbuondon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cư M'gar": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencumgar_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Búk": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongbuk_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Năng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongnang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ea Kar": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyeneakar_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện M'Đrắk": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmdrak_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Bông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongbong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Pắc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongpac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông A Na": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongana_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lắk": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlak_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cư Kuin": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencukuin_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Gia Nghĩa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphogianghia_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đăk Glong": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakglong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cư Jút": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencujut_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk Mil": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakmil_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Krông Nô": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkrongno_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk Song": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendaksong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đắk R'Lấp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendakrlap_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tuy Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentuyduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Đà Lạt": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodalat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bảo Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobaoloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đam Rông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendamrong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lạc Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlacduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lâm Hà": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlamha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đơn Dương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonduong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Trọng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenductrong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Di Linh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendilinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đạ Huoai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendahuoai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đạ Tẻh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendateh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cát Tiên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencattien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Phước Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaphuoclong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Đồng Xoài": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodongxoai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Bình Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabinhlong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bù Gia Mập": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbugiamap_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lộc Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlocninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bù Đốp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbudop_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hớn Quản": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhonquan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đồng Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendongphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bù Đăng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbudang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Chơn Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixachonthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Riềng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphurieng_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tây Ninh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotayninh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Biên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanbien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Dương Minh Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduongminhchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Châu Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchauthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Hòa Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixahoathanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gò Dầu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengodau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bến Cầu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbencau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Trảng Bàng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixatrangbang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thủ Dầu Một": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothudaumot_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bàu Bàng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbaubang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Dầu Tiếng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendautieng_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Bến Cát": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabencat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Giáo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphugiao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tân Uyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotanuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Dĩ An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphodian_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thuận An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothuanan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bắc Tân Uyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbactanuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Biên Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobienhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Long Khánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhpholongkhanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Cửu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhcuu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Định Quán": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendinhquan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trảng Bom": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrangbom_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thống Nhất": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthongnhat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cẩm Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencammy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Long Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlongthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Xuân Lộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenxuanloc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nhơn Trạch": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennhontrach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Vũng Tàu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphovungtau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bà Rịa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobaria_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Châu Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchauduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Xuyên Mộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenxuyenmoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Long Điền": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlongdien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đất Đỏ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendatdo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Phú Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaphumy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Côn Đảo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencondao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 1": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan1_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 12": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan12_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Gò Vấp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quangovap_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Bình Thạnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanbinhthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Tân Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quantanbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Tân Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quantanphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Phú Nhuận": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanphunhuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Thủ Đức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphothuduc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 3": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan3_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 10": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan10_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 11": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan11_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 4": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan4_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 5": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan5_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 6": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan6_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 8": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan8_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Bình Tân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanbinhtan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận 7": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quan7_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Củ Chi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencuchi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hóc Môn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhocmon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Chánh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhchanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Nhà Bè": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennhabe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cần Giờ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencangio_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Tân An": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotanan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Kiến Tường": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixakientuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Hưng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanhung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Hưng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhhung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mộc Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmochoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Thạnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạnh Hóa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhhoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Huệ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduchue_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đức Hòa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduchoa_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bến Lức": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbenluc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thủ Thừa": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthuthua_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Trụ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentantru_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cần Đước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencanduoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cần Giuộc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencangiuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Mỹ Tho": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphomytho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Gò Công": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixagocong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Cai Lậy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixacailay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Phước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanphuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cái Bè": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencaibe_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cai Lậy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencailay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chợ Gạo": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchogao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gò Công Tây": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengocongtay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gò Công Đông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengocongdong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Phú Đông": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanphudong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bến Tre": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobentre_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Chợ Lách": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencholach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỏ Cày Nam": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmocaynam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Giồng Trôm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiongtrom_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Đại": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhdai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ba Tri": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbatri_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạnh Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỏ Cày Bắc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmocaybac_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Trà Vinh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphotravinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Càng Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencanglong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cầu Kè": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencauke_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tiểu Cần": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentieucan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cầu Ngang": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencaungang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trà Cú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentracu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Duyên Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenduyenhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Duyên Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixaduyenhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Vĩnh Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphovinhlong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Long Hồ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlongho_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mang Thít": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmangthit_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện  Vũng Liêm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvungliem_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tam Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentambinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Bình Minh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixabinhminh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trà Ôn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentraon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Bình Tân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenbinhtan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Cao Lãnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocaolanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Sa Đéc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphosadec_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hồng Ngự": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohongngu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Hồng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanhong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hồng Ngự": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhongngu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tháp Mười": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthapmuoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cao Lãnh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencaolanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thanh Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhbinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lấp Vò": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlapvo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Lai Vung": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlaivung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Long Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhpholongxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Châu Đốc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphochaudoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện An Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Tân Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixatanchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phú Tân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphutan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Châu Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchauphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Tịnh Biên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixatinhbien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tri Tôn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentriton_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thoại Sơn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthoaison_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Rạch Giá": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphorachgia_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Hà Tiên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphohatien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kiên Lương": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkienluong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hòn Đất": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhondat_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Tân Hiệp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentanhiep_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Giồng Riềng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiongrieng_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Gò Quao": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengoquao_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện An Biên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanbien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện An Minh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenanminh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Thuận": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhthuan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Phú Quốc": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphophuquoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kiên Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkienhai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện U Minh Thượng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenuminhthuong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Giang Thành": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyengiangthanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Ninh Kiều": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanninhkieu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Ô Môn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanomon_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Bình Thuỷ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanbinhthuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Cái Răng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quancairang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Quận Thốt Nốt": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_quanthotnot_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cờ Đỏ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencodo_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thới Lai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthoilai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Vị Thanh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphovithanh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Ngã Bảy": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphongabay_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Châu Thành A": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenchauthanha_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phụng Hiệp": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphunghiep_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vị Thuỷ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvithuy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Long Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlongmy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Long Mỹ": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixalongmy_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Sóc Trăng": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphosoctrang_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Kế Sách": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenkesach_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỹ Tú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmytu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cù Lao Dung": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenculaodung_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Long Phú": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenlongphu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Mỹ Xuyên": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenmyxuyen_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Ngã Năm": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixanganam_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thạnh Trị": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthanhtri_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Vĩnh Châu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixavinhchau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trần Đề": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentrande_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Bạc Liêu": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphobaclieu_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hồng Dân": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhongdan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Phước Long": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenphuoclong_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Vĩnh Lợi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenvinhloi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thị xã Giá Rai": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thixagiarai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đông Hải": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendonghai_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Hoà Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenhoabinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Thành phố Cà Mau": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_thanhphocamau_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện U Minh": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenuminh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Thới Bình": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenthoibinh_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Trần Văn Thời": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyentranvanthoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Cái Nước": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyencainuoc_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Đầm Dơi": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyendamdoi_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Năm Căn": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyennamcan_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                    case "Huyện Ngọc Hiển": {
                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.array_huyenngochien_commune, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        break;
                    }
                }
                communeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                add_address_spn_commune.setAdapter(communeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_address_txt_name.getText().toString();
                String phone = add_address_txt_phone.getText().toString();
                String province = add_address_spn_province.getSelectedItem().toString();
                String district = add_address_spn_district.getSelectedItem().toString();
                String commune = add_address_spn_commune.getSelectedItem().toString();
                String detail_address = add_address_txt_address_detail.getText().toString();
                if (province.equals("Chọn tỉnh thành phố")) {
                    Toast.makeText(UserAddAddressesActivity.this, "Vui lòng chọn tỉnh thành phố", Toast.LENGTH_SHORT).show();
                } else {
                    if (district.equals("Chọn quận huyện"))
                        Toast.makeText(UserAddAddressesActivity.this, "Vui lòng chọn quận huyện", Toast.LENGTH_SHORT).show();
                    else {
                        if (commune.equals("Chọn phường xã"))
                            Toast.makeText(UserAddAddressesActivity.this, "Vui lòng chọn phường xã", Toast.LENGTH_SHORT).show();
                        else {
                            if(name.equals("")||phone.equals("")||detail_address.equals(""))
                                Toast.makeText(UserAddAddressesActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            else {
                            savedAddress();
                            Intent intent = new Intent(UserAddAddressesActivity.this, UserAddressesActivity.class);
                            startActivity(intent);}
                        }
                    }
                }
            }
        });
    }

    private void savedAddress() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            String name = add_address_txt_name.getText().toString();
            String phone = add_address_txt_phone.getText().toString();
            String province = add_address_spn_province.getSelectedItem().toString();
            String district = add_address_spn_district.getSelectedItem().toString();
            String commune = add_address_spn_commune.getSelectedItem().toString();
            String detail_address = add_address_txt_address_detail.getText().toString();
            DatabaseReference newaddressRef = addressRef.push();
            String id_address = newaddressRef.getKey().toString();
            ShipmentInf newShipmentnf = new ShipmentInf(id_address, userId, name, phone, province, district, commune, detail_address);
            newaddressRef.setValue(newShipmentnf);
        }
    }
}
