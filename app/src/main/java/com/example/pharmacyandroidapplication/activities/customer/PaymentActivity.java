package com.example.pharmacyandroidapplication.activities.customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ItemPayAdapter;
import com.example.pharmacyandroidapplication.models.ItemPay;
import com.example.pharmacyandroidapplication.models.ShipmentInf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PaymentActivity extends AppCompatActivity {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Lấy ngày hiện tại
    LocalDate currentDate = LocalDate.now();

    // Thêm 3 ngày
    LocalDate datePlus3Days = currentDate.plusDays(3);

    // Chuyển đổi thành chuỗi với định dạng
    String formattedDate = datePlus3Days.format(formatter);
    String userId;
    String Tinh;
    int finalTotalPrice = 0;
    private RecyclerView recyclerView;
    private ItemPayAdapter adapter;
    private List<ItemPay> itemList;
    private ArrayList<ShipmentInf> addressList;
    private FirebaseDatabase database;
    private DatabaseReference shipmentRef;
    private ImageView imgmap;
    private LinearLayout haveaddress;
    private TextView detail;
    private TextView address;
    private TextView name_receiver;
    private TextView phone;
    private EditText noted;
    private TextView day_receiver;
    private Button close_button, btn_confirm, btn_add_address;
    private RadioGroup group_unit;
    private TextView sumhang,ship,sumprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        itemList = getIntent().getParcelableArrayListExtra("selectedItems");

// Kiểm tra xem selectedItems có rỗng hay không
        if (itemList != null && !itemList.isEmpty()) {
            // Ở đây bạn có thể sử dụng selectedItems theo nhu cầu của bạn
            // Ví dụ: Hiển thị danh sách các mục đã được chọn
            for (ItemPay item : itemList) {
                Log.d("Selected Item", item.getName() + " - Quantity: " + item.getQuantity());
            }
        } else {
            // Xử lý khi danh sách selectedItems rỗng
        }
        imgmap = findViewById(R.id.imgmap);
        haveaddress = findViewById(R.id.haveaddress);
        detail = findViewById(R.id.detail);
        address = findViewById(R.id.address);
        name_receiver = findViewById(R.id.name_receiver);
        phone = findViewById(R.id.phone);
        noted = findViewById(R.id.noted);
        day_receiver = findViewById(R.id.day_receiver);
        sumhang = findViewById(R.id.sumhang);
        int totalPrice = 0;

        for (ItemPay item : itemList) {
            totalPrice += item.getQuantity() * item.getPrice();
        }
        sumhang.setText(String.valueOf(totalPrice));
        ship = findViewById(R.id.ship);
        sumprice = findViewById(R.id.sumprice);
        LinearLayout have = findViewById(R.id.haveaddress);
        ImageView imgmap = findViewById(R.id.imgmap);
        LinearLayout no = findViewById(R.id.noaddress);
        Button add_address = findViewById(R.id.add_address);
        database = FirebaseDatabase.getInstance();
        shipmentRef = database.getReference("shipment details");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
        TextView change_address = findViewById(R.id.change);
        addressList = new ArrayList<>();

        finalTotalPrice = totalPrice;
        getAddressesByUserId(userId, new OnAddressesReceivedCallback() {
            @Override
            public void onAddressesReceived(List<ShipmentInf> addresses) {
                addressList.addAll(addresses);
                if (addresses.isEmpty()) {
                    have.setVisibility(View.GONE);
                    imgmap.setVisibility(View.VISIBLE);
                    no.setVisibility(View.VISIBLE);
                } else {
                    have.setVisibility(View.VISIBLE);
                    imgmap.setVisibility(View.GONE);
                    no.setVisibility(View.GONE);

                    ShipmentInf address_ship = addresses.get(0);
                    detail.setText(address_ship.getAddress_details());
                    Tinh = address_ship.getProvince();
                    if(Tinh.equals("Thành phố Hồ Chí Minh")){
                        day_receiver.setText("Đơn của bạn sẽ được giao trong 2 tiếng");
                        ship.setText("15000");
                        sumprice.setText(String.valueOf(finalTotalPrice + 15000));
                    }else{
                        day_receiver.setText("Từ 8:00 đến 16h00 ngày "+formattedDate);
                        ship.setText("30000");
                        sumprice.setText(String.valueOf(finalTotalPrice + 30000));
                    }
                    address.setText(address_ship.getCommune() + ", " + address_ship.getDistrict() + ", " + address_ship.getProvince());
                    name_receiver.setText(address_ship.getReceiverName());
                    phone.setText(address_ship.getPhone());
                }
            }
        });

        add_address.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, UserAddAddressesActivity.class);
            startActivity(intent);
        });

        change_address.setOnClickListener(v -> {
            if (!addressList.isEmpty()) {
                ShowDialogChangeAddress();
            } else {
                // Thêm log để kiểm tra
                Log.e("PaymentActivity", "Address list is empty, cannot show dialog");
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        itemList = new ArrayList<>();
//        itemList.add(new ItemPay("ID Product 1", "lọ", "Sản phẩm 1", 5, 120000, "https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro1.png?alt=media&token=2d6153e4-a7a7-4fdc-88f2-6a2f4b43378f"));
//        itemList.add(new ItemPay("ID Product 1", "lọ", "Sản phẩm 1", 5, 130000, "https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro1.png?alt=media&token=2d6153e4-a7a7-4fdc-88f2-6a2f4b43378f"));
//        itemList.add(new ItemPay("ID Product 1", "lọ", "Sản phẩm 1", 5, 140000, "https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro1.png?alt=media&token=2d6153e4-a7a7-4fdc-88f2-6a2f4b43378f"));
//        itemList.add(new ItemPay("ID Product 1", "lọ", "Sản phẩm 1", 5, 150000, "https://firebasestorage.googleapis.com/v0/b/harupharmacy-f4929.appspot.com/o/images%2Fpro1.png?alt=media&token=2d6153e4-a7a7-4fdc-88f2-6a2f4b43378f"));

        adapter = new ItemPayAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }

    private void ShowDialogChangeAddress() {
        Dialog dialog = new Dialog(PaymentActivity.this, R.style.FullScreenDialog);
        dialog.setContentView(R.layout.dialog_change_address);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(layoutParams);

        close_button = dialog.findViewById(R.id.close_button);
        btn_confirm = dialog.findViewById(R.id.btn_confirm);
        btn_add_address = dialog.findViewById(R.id.btn_add_address);
        group_unit = dialog.findViewById(R.id.group_unit);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (ShipmentInf shipment : addressList) {
            LinearLayout customRadioButtonLayout = (LinearLayout) inflater.inflate(R.layout.radiobutton_change_address, group_unit, false);
            RadioButton radioButton = customRadioButtonLayout.findViewById(R.id.radioButton);
            TextView tenNguoiNhan = customRadioButtonLayout.findViewById(R.id.tennguoinhan);
            TextView sdt = customRadioButtonLayout.findViewById(R.id.sdt);
            TextView addresss = customRadioButtonLayout.findViewById(R.id.addresss);
            TextView suaButton = customRadioButtonLayout.findViewById(R.id.Sua);

            tenNguoiNhan.setText(shipment.getReceiverName());
            sdt.setText(shipment.getPhone());
            addresss.setText(shipment.getAddress_details());

            customRadioButtonLayout.setOnClickListener(v -> radioButton.setChecked(true));

            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    for (int i = 0; i < group_unit.getChildCount(); i++) {
                        View child = group_unit.getChildAt(i);
                        if (child instanceof LinearLayout) {
                            RadioButton rb = ((LinearLayout) child).findViewById(R.id.radioButton);
                            if (rb != radioButton) {
                                rb.setChecked(false);
                            }
                        }
                    }
                }
            });

            suaButton.setOnClickListener(v -> {
                // Thực hiện hành động khi nút "Sửa" được bấm

                // Code để mở dialog hoặc activity để chỉnh sửa nội dung
            });

            group_unit.addView(customRadioButtonLayout);
        }

        btn_confirm.setOnClickListener(v -> {
            // Lấy id của RadioButton được chọn
            int selectedRadioButtonId = group_unit.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                // Lấy address_id từ tag của RadioButton được chọn
                String selectedAddressId = (String) selectedRadioButton.getTag();
                // Tìm địa chỉ trong addressList dựa trên address_id được chọn
                for (ShipmentInf address_ship : addressList) {
                    if (selectedAddressId.equals(address_ship.getAddress_id())) {
                        // Cập nhật các thành phần UI
                        detail.setText(address_ship.getAddress_details());
                        address.setText(address_ship.getCommune() + ", " + address_ship.getDistrict() + ", " + address_ship.getProvince());
                        name_receiver.setText(address_ship.getReceiverName());
                        phone.setText(address_ship.getPhone());
                        // Thoát khỏi vòng lặp vì đã tìm thấy địa chỉ được chọn
                        break;
                    }
                }
                // Đóng dialog sau khi cập nhật thông tin
                dialog.dismiss();
            } else {
                // Hiển thị thông báo hoặc xử lý khác khi không có radiobutton nào được chọn
            }
        });
        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, UserAddAddressesActivity.class);
                startActivity(intent);
            }
        });
        //Hiển thi
        dialog.show();
    }

    public interface OnAddressesReceivedCallback {
        void onAddressesReceived(List<ShipmentInf> addresses);
    }

    public void getAddressesByUserId(String userId, final OnAddressesReceivedCallback callback) {
        DatabaseReference shipmentRef = FirebaseDatabase.getInstance().getReference("shipment details");
        shipmentRef.orderByChild("user_id").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShipmentInf> addressList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String address_id = snapshot.child("address_id").getValue(String.class);
                    String district = snapshot.child("district").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String receiverName = snapshot.child("receiverName").getValue(String.class);
                    String province = snapshot.child("province").getValue(String.class);
                    String commune = snapshot.child("commune").getValue(String.class);
                    String address_details = snapshot.child("address_details").getValue(String.class);

                    ShipmentInf address = new ShipmentInf(address_id, userId, receiverName, phone, province, district, commune, address_details);
                    addressList.add(address);
                }
                if (callback != null) {
                    callback.onAddressesReceived(addressList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error reading data from Firebase", databaseError.toException());
            }
        });
    }

}


