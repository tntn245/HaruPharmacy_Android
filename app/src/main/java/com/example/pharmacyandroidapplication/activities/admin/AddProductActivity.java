package com.example.pharmacyandroidapplication.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {
    private TextView add_txt_product_id;
    private ImageView add_img_product_img;
    private EditText add_txt_product_name, add_txt_product_price, add_txt_product_ingredient, add_txt_product_uses;
    private Spinner add_spn_product_type, add_spn_product_unit, add_spn_product_status;
    private Button btn_save_add_product, btn_cancel_add_product;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference("product");
    private DatabaseReference categoryRef = database.getReference("category");
    private DatabaseReference unitRef = database.getReference("unit");
    private ArrayList typeList, unitList;
    private ArrayAdapter<String> adapter, unitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        add_txt_product_name = findViewById(R.id.add_txt_product_name);
        add_img_product_img = findViewById(R.id.add_img_product_img);
        add_txt_product_price = findViewById(R.id.add_txt_product_price);
        add_txt_product_ingredient = findViewById(R.id.add_txt_product_ingredient);
        add_txt_product_uses = findViewById(R.id.add_txt_product_uses);
        add_spn_product_unit = findViewById(R.id.add_spn_product_unit);
        add_spn_product_status = findViewById(R.id.add_spn_product_status);
        add_spn_product_type = findViewById(R.id.add_spn_product_type);
        btn_save_add_product = findViewById(R.id.btn_save_add_product);
        btn_cancel_add_product = findViewById(R.id.btn_cancel_saved_add_product);

        typeList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveCategoryData("name");
        add_spn_product_type.setAdapter(adapter);

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Đang kinh doanh");
        statusList.add("Ngừng kinh doanh");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_spn_product_status.setAdapter(statusAdapter);

        unitList = new ArrayList<>();
        unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitList);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retrieveUnitData("unit_name");
        add_spn_product_unit.setAdapter(unitAdapter);
        btn_save_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewProduct();
//                setDefaultView();
                Intent intent = new Intent(AddProductActivity.this, ProductManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_cancel_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("NHAN", "Huy");
                Intent intent = new Intent(AddProductActivity.this, ProductManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean flag_produnit_exist;
    public void saveNewProduct() {
        String cate_name = add_spn_product_type.getSelectedItem().toString();
        String name = add_txt_product_name.getText().toString();
        String img = "img_link";
        String unit = add_spn_product_unit.getSelectedItem().toString();
        String ingredient = add_txt_product_ingredient.getText().toString();
        String uses = add_txt_product_uses.getText().toString();
        String priceStr = add_txt_product_price.getText().toString();
        int price = priceStr.isEmpty() ? 0 : Integer.parseInt(priceStr);
        int quantity = 0;
        boolean flag_valid = (add_spn_product_status.getSelectedItem().toString() == "Đang kinh doanh");

        if (name.isEmpty() || img.isEmpty() ||
                unit.isEmpty() || ingredient.isEmpty() || uses.isEmpty() ||
                priceStr.isEmpty() ) {
            Toast.makeText(AddProductActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();

        }


        flag_produnit_exist = false;
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
                    String productID = snapshot.getKey();
                    String productName = snapshot.child("name").getValue(String.class);

                    if(productName.equals(name)){
                        for (DataSnapshot unitSnapshot : snapshot.child("unitarr").getChildren()) {
                            String unitName = unitSnapshot.getKey().toString();
                            if (unit.equals(unitName)) {
                                flag_produnit_exist = true;
                                break;
                            }
                        }

                        if(!flag_produnit_exist){
                            // Add new unit in unitarr
                            DatabaseReference unitArrRef = snapshot.getRef().child("unitarr");
                            // Sử dụng setValue() để đặt dữ liệu trực tiếp cho đơn vị mới
                            unitArrRef.child(unit).child("name").setValue(unit);
                            unitArrRef.child(unit).child("price").setValue(price);
                            unitArrRef.child(unit).child("quantity").setValue(quantity);

                            // Tạo và hiển thị hộp thoại
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                            builder.setTitle("Sản phẩm đang có mô tả về thành phần và công dụng");
                            builder.setMessage("Bạn có muốn sử dụng mô tả (thành phần, công dụng) mới hay không?");

                            // Thêm nút "Đồng ý" vào hộp thoại
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    productRef.child(productID).child("name").setValue("newName").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Ghi đè thành công
                                                Toast.makeText(getApplicationContext(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Xử lý khi ghi đè không thành công
                                                Toast.makeText(getApplicationContext(), "Failed to update name", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });                                }
                            });

                            // Thêm nút "Hủy" vào hộp thoại


                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("NHAN", "HUY");
                                    // Xử lý khi người dùng nhấn nút "Hủy"

                                    // Ví dụ: đóng hộp thoại hoặc thực hiện hành động khác
                                }
                            });
                            // Hiển thị hộp thoại
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            Toast.makeText(AddProductActivity.this, "Sản phẩm với đơn vị "+unit+ " đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });











        // Tạo các đơn vị (units) cho sản phẩm

//        productRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Lấy dữ liệu từ snapshot và tạo đối tượng Product
//                    String nameInFirebase = snapshot.child("name").getValue(String.class);
//                    if (name.equals(nameInFirebase)) {
////                        Map<String, Object> units = new HashMap<>();
////                        units.put(unit, new Unit(unit, price));
////                        DatabaseReference unitRef = snapshot.getRef().child("units").getRef();
////                        unitRef.setValue(units);
////                        break;
//                        Log.e("trung ten roi", "onDataChange: ");
//                    } else {
//                        Log.e("chay bn", "onDataChange: " );
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void setDefaultView() {
        add_txt_product_id.setText("");
        add_txt_product_name.setText("");
        add_img_product_img.setImageResource(R.mipmap.ic_launcher);
        add_txt_product_price.setText("");
        add_txt_product_ingredient.setText("");
        add_txt_product_uses.setText("");
        add_spn_product_unit.setSelection(0);
        add_spn_product_status.setSelection(0);
        add_spn_product_type.setSelection(0);
    }

    public void retrieveCategoryData(String attr) {
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    typeList.add(item.child(attr).getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }

    public void retrieveUnitData(String attr) {
        unitRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unitList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    unitList.add(item.child(attr).getValue(String.class));
                }
                unitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
    }
}