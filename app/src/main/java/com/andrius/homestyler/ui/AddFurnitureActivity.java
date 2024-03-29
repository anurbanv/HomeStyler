package com.andrius.homestyler.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrius.homestyler.R;
import com.andrius.homestyler.entity.Furniture;
import com.andrius.homestyler.util.ImageUtil;
import com.andrius.homestyler.view_model.FurnitureViewModel;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFurnitureActivity extends AppCompatActivity {

    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.spType)
    Spinner spType;
    @BindView(R.id.spColor)
    Spinner spColor;
    @BindView(R.id.etUrl)
    EditText etUrl;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.btnUploadModel)
    Button btnUploadModel;
    @BindView(R.id.btnUploadImage)
    Button btnUploadImage;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.tvImageFile)
    TextView tvImageFile;
    @BindView(R.id.tvModelFile)
    TextView tvModelFile;

    private File model;
    private File image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_furniture);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(view -> finish());

        FurnitureViewModel furnitureViewModel = ViewModelProviders.of(this).get(FurnitureViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.furniture_colors, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spColor.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.furniture_types, R.layout.spinner_item);
        spType.setAdapter(adapter);

        btnUploadImage.setOnClickListener(view -> {
            new ChooserDialog(this)
                    .withChosenListener((path, pathFile) ->
                    {
                        MainActivity.log(new File(path).length() + "");
                        File file = new File(path);
                        if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") ||
                                file.getName().endsWith(".jpeg")) {
                            image = file;
                            tvImageFile.setText(file.getName());
                        } else {
                            Toast.makeText(this, "not image", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build().show();
        });

        btnUploadModel.setOnClickListener(view -> {
            new ChooserDialog(this)
                    .withChosenListener((path, pathFile) ->
                    {
                        MainActivity.log(new File(path).length() + "");
                        File file = new File(path);
                        if (file.getName().endsWith(".sfb")) {
                            model = file;
                            tvModelFile.setText(file.getName());
                        } else {
                            Toast.makeText(this, "not model", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build().show();
        });

        btnSubmit.setOnClickListener(view -> {

            if (etPrice.getText().toString().isEmpty() || etUrl.getText().toString().isEmpty() ||
                    model == null || image == null) {
                Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show();
            } else {
                String color = spColor.getSelectedItem().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                String type = spType.getSelectedItem().toString();
                String url = etUrl.getText().toString();

                String imageBase64 = ImageUtil.getModelStringBase64(image);

                String modelBase64 = ImageUtil.getModelStringBase64(model);

                furnitureViewModel.insert(new Furniture(color, price, type, url, imageBase64, modelBase64));
                Toast.makeText(this, "furniture added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
