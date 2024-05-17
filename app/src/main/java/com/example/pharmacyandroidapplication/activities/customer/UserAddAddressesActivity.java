package com.example.pharmacyandroidapplication.activities.customer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;

public class UserAddAddressesActivity extends AppCompatActivity {
    private EditText add_address_txt_name, adđ_address_txt_phone;
    private TextView tvProvinceSpinner, tvDistrictSpinner, tvCommuneSpinner;
    private Spinner add_address_spn_province, add_address_spn_district, add_address_spn_commune;
    private ArrayAdapter<CharSequence> provinceAdapter, districtAdapter, communeAdapter;
    private String selectedProvince, selectedDistrict, selectedCommune;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_input);
        add_address_spn_province = findViewById(R.id.add_address_spn_province);

        add_address_spn_commune = findViewById(R.id.add_address_spn_commune);
        provinceAdapter = ArrayAdapter.createFromResource(this, R.array.province, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_address_spn_province.setAdapter(provinceAdapter);
        add_address_spn_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_address_spn_district = findViewById(R.id.add_address_spn_district);
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
    }
}
