package com.spps.mandal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import android.Manifest;


import com.spps.mandal.Adapter.CountrySpinnerAdapter;
import com.spps.mandal.Adapter.GuruSwamiSpinnerAdapter;
import com.spps.mandal.Adapter.MandalSpinnerAdapter;
import com.spps.mandal.Connectivity.CitySpinnerList;
import com.spps.mandal.Connectivity.CountrySpinnerList;
import com.spps.mandal.Connectivity.DistrictSpinnerList;
import com.spps.mandal.Connectivity.GuruSwamiSpinnerList;
import com.spps.mandal.Connectivity.MandalSpinnerList;
import com.spps.mandal.Connectivity.StateSpinnerList;
import com.spps.mandal.CropImage.CropImage;
import com.spps.mandal.InternetConnectivity.NetworkChangeReceiver;

import org.json.JSONException;

import static android.view.View.VISIBLE;

public class Register extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int PIC_CAMERA_CROP = 3;
    private static final int PIC_GALLERY_CROP = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    private static final int READ_STORAGE_PERMISSION_REQUEST = 6;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 7;
    int counter =2;

    private ProgressDialog progressDialog = null;
    public String registerResponseResult;

    public AlertDialog alertDialog;
    public ArrayAdapter<String> dialogAdapter;
    String webMethodName = "AddUserDetail";

    EditText txtFullName;
    EditText txtMobileNo;
    EditText txtPassword;
    EditText txtAddress;
    EditText txtEmail;
    EditText txtCountry;
    EditText txtState;
    EditText txtArea;
    EditText txtCity;
    EditText txtPinCode;
    EditText txtNoOfyear;
    EditText txtTrustName;
    EditText txtMandalName;
    EditText txtEstRegNo;
    TextView res;

    RadioGroup rgUserRole;
    RadioButton rbGuruSwami;
    RadioButton rbNormalUser;

    Button selectImageButton1;
    Button btnRegister;
    ImageView firstImage;
    String currentImagePath;
    String currentImagePath2;
    Spinner spMandal;
    Spinner spGuruSwami;

    public String userRole;
//    public String country = "";
//    public String state = "";
    //public String city = "";
    public String area = "";
    public String pincode = "";
    public String noOfYear = "";
    public String trustName = "";
    public String estRegNo = "";

    public String fullName = "";
    public String mobileNo;
    public String Password = "";
    public String address;
    public String email;
    public String displayText;

    Bitmap imageToShow;
    String timeStamp;
    File storageDir;
    File originalFile;
    File originalFile2;
    File cropFile;
    String imageBase64String;
    String imageBase64String2;
    String firstImagePath = "";
    String firstImageName = "";

    String asycTaskCounter;
    LinearLayout imageViewLinearLayout;
    boolean doubleBackToExitPressedOnce = false;

    RelativeLayout layoutForGuruSwami;
    RelativeLayout layoutForNormalUser;

    private MandalSpinnerAdapter mandalSpinnerAdapter;
    private GuruSwamiSpinnerAdapter guruSwamiSpinnerAdapter;
    private int mandalId;
    private int guruSwamiId;
    private String mandalName;
    private String guruSwamiMandalName;
    private String guruSwamiName;

    private String[] mandalArrayList;
    private ProgressDialog mandalDailogbox;
    private List<String> mandalNameList = new ArrayList<String>();
    private List<String> mandalIdList = new ArrayList<String>();

    private String[] guruSwamiNameArrayList;
    private ProgressDialog guruSwamiDailogbox;
    private List<String> guruSwamiNameList = new ArrayList<String>();
    private List<String> guruSwamiIdList = new ArrayList<String>();

    Spinner country;
    Spinner state;
    Spinner district;
    Spinner city;

    String countryName;
    String countryId;
    private String[] countryArrayList;
    private CountrySpinnerAdapter countrySpinnerAdapter;
    private ProgressDialog countryDialogBox;
    private List<String> countryIdList = new ArrayList<String>();
    private List<String> countryNameList = new ArrayList<String>();

    String stateName;
    String stateId;
    private String[]stateArrayList;
    //private DivisionSpinnerAdapter stateSpinnerAdapter;
    private ProgressDialog stateDialogBox;
    private List<String> stateIdList = new ArrayList<String>();
    private List<String> stateNameList = new ArrayList<String>();

//    String districtName;
//    String districtId;
//    private String[]districtArrayList;
//    //private DivisionSpinnerAdapter districtSpinnerAdapter;
//    private ProgressDialog districtDialogBox;
//    private List<String> districtIdList = new ArrayList<String>();
//    private List<String> districtNameList = new ArrayList<String>();

    String cityName;
    String cityId;
    private String[]cityArrayList;
    //private DivisionSpinnerAdapter citySpinnerAdapter;
    private ProgressDialog cityDialogBox;
    private List<String> cityIdList = new ArrayList<String>();
    private List<String> cityNameList = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        layoutForGuruSwami = (RelativeLayout) findViewById(R.id.layoutForGuruSwami);
        layoutForNormalUser = (RelativeLayout) findViewById(R.id.layoutForNormalUser);
        spMandal = (Spinner) findViewById(R.id.spMandal);
        spGuruSwami = (Spinner) findViewById(R.id.spGuruSwami);
        rgUserRole = (RadioGroup) findViewById(R.id.radiogroupUserType);
        rbGuruSwami = (RadioButton) findViewById(R.id.userGuruSwami);
        rbNormalUser = (RadioButton) findViewById(R.id.userNormal);
        txtFullName = (EditText) findViewById(R.id.fname);
        txtMobileNo = (EditText) findViewById(R.id.contact);
        txtPassword = (EditText) findViewById(R.id.password);
        txtAddress = (EditText) findViewById(R.id.address);
        txtEmail = (EditText) findViewById(R.id.email);
        country = (Spinner) findViewById(R.id.spinnerForCountry);
        state = (Spinner) findViewById(R.id.spinnerForState);
        city = (Spinner) findViewById(R.id.spinnerForCity);
        txtArea = (EditText) findViewById(R.id.area);

        txtPinCode = (EditText) findViewById(R.id.pincode);
        txtNoOfyear = (EditText) findViewById(R.id.sevaYear);
        txtTrustName = (EditText) findViewById(R.id.trustName);
        txtMandalName = (EditText) findViewById(R.id.mandalName);
        txtEstRegNo = (EditText) findViewById(R.id.estRegNo);

        res = (TextView) findViewById(R.id.txtRes);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        selectImageButton1 = (Button) this.findViewById(R.id.btnImage1);
        firstImage = (ImageView) this.findViewById(R.id.firstImage);
        imageViewLinearLayout = (LinearLayout) findViewById(R.id.imageViewLinearLayout);

        imageViewLinearLayout.setVisibility(View.GONE);
        layoutForGuruSwami.setVisibility(View.GONE);

        selectImageButton1.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        firstImage.setOnClickListener(this);

        rgUserRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.userGuruSwami) {
                    rbGuruSwami = (RadioButton) findViewById(R.id.userGuruSwami);
                    userRole = "Guruswamy";
                    layoutForGuruSwami.setVisibility(VISIBLE);
                    layoutForNormalUser.setVisibility(View.GONE);

                } else if (i == R.id.userNormal) {
                    rbNormalUser = (RadioButton) findViewById(R.id.userNormal);
                    userRole = "Normaluser";
                    layoutForNormalUser.setVisibility(VISIBLE);
                    layoutForGuruSwami.setVisibility(View.GONE);
                }
            }
        });

        getCountrydDetails();
        getStateDetails();
        getcityDetails();

        getMandalNGuruSwami();
    }
    private void getCountrydDetails() {
        countryArrayList = new String[]{
                "Select Country"
        };
        countryNameList = new ArrayList<>(Arrays.asList(countryArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(Register.this, R.layout.spiner_item,countryNameList);
        getListOfCountry();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countrySpinnerAdapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {

                    countryName = parent.getItemAtPosition(position).toString();
                    countryId = countryIdList.get(position);

                    stateNameList.clear();
                    stateIdList.clear();
                    getListOfState();
                    state.setSelection(stateNameList.indexOf(0));
                    countrySpinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfCountry() {
        countryDialogBox = ProgressDialog.show(this, "", "Fetching Country Please Wait...", true);
        CountrySpinnerList countrySpinnerList = new CountrySpinnerList(Register.this);
        countrySpinnerList.FetchAllcountry(countryNameList, countryIdList, countrySpinnerAdapter,countryDialogBox,counter);
    }

    private void getStateDetails() {
        stateArrayList = new String[]{
                "Select State"
        };
        stateNameList = new ArrayList<>(Arrays.asList(stateArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(Register.this, R.layout.spiner_item,stateNameList);
       // getListOfState();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(countrySpinnerAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {

                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = stateIdList.get(position);
                    cityNameList.clear();
                    cityIdList.clear();
                    getListOfcity();
                    city.setSelection(cityNameList.indexOf(0));
                    countrySpinnerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfState() {
        stateDialogBox = ProgressDialog.show(this, "", "Fetching State Please Wait...", true);
        StateSpinnerList stateSpinnerList = new StateSpinnerList(Register.this);
        stateSpinnerList.FetchAllState(stateNameList, stateIdList, countrySpinnerAdapter, countryId,stateDialogBox,counter);
    }

    private void getcityDetails() {
        cityArrayList = new String[]{
                "Select City"
        };
        cityNameList = new ArrayList<>(Arrays.asList(cityArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(Register.this, R.layout.spiner_item,cityNameList);
        //getListOfcity();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(countrySpinnerAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    cityName = parent.getItemAtPosition(position).toString();
                    cityId = cityIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfcity() {
        cityDialogBox = ProgressDialog.show(this, "", "Fetching City Please Wait...", true);
        CitySpinnerList citySpinnerList = new CitySpinnerList(Register.this);
        citySpinnerList.FetchAllCity(cityNameList, cityIdList, countrySpinnerAdapter,countryId, stateId,cityDialogBox,counter);
    }

    private void getMandalNGuruSwami() {

        mandalArrayList = new String[]{
                "Select Mandal"
        };
        mandalNameList = new ArrayList<>(Arrays.asList(mandalArrayList));
        mandalSpinnerAdapter = new MandalSpinnerAdapter(this, R.layout.spiner_item, mandalNameList);
        fetchMandalName();
        mandalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMandal.setAdapter(mandalSpinnerAdapter);
        spMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mandalName = (String) parent.getItemAtPosition(position);
                    mandalId = Integer.parseInt(mandalIdList.get(position));

                    guruSwamiNameList.clear();
                    guruSwamiIdList.clear();
                    fetchGuruSwamiName();
                    spGuruSwami.setSelection(guruSwamiNameList.indexOf(0));
                    guruSwamiSpinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        guruSwamiNameArrayList = new String[]{
                "Select GuruSwamy"
        };
        guruSwamiNameList = new ArrayList<>(Arrays.asList(guruSwamiNameArrayList));
        guruSwamiSpinnerAdapter = new GuruSwamiSpinnerAdapter(this, R.layout.spiner_item, guruSwamiNameList);
        fetchGuruSwamiName();
        guruSwamiSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGuruSwami.setAdapter(guruSwamiSpinnerAdapter);
        spGuruSwami.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    guruSwamiName = (String) parent.getItemAtPosition(position);
                    guruSwamiId = Integer.parseInt(guruSwamiIdList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchMandalName() {
        MandalSpinnerList mandalSpinnerList = new MandalSpinnerList(this);
        mandalSpinnerList.FetchAllMandalList(mandalNameList, mandalIdList, mandalSpinnerAdapter);
    }

    public void fetchGuruSwamiName() {
        GuruSwamiSpinnerList guruSwamiSpinnerList = new GuruSwamiSpinnerList(this);
        guruSwamiSpinnerList.FetchAllGurSwamiList(guruSwamiNameList, guruSwamiIdList, guruSwamiSpinnerAdapter, mandalName);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnImage1) {
            if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestWriteStoragePermission();
            } else {
                if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestReadStoragePermission();
                } else {
                    createImageFormSelectImageDialogChooser();
                }
            }
        } else if (v.getId() == R.id.btnRegister) {
            fullName = txtFullName.getText().toString();
            mobileNo = txtMobileNo.getText().toString();
            Password = txtPassword.getText().toString();
            address = txtAddress.getText().toString();
            email = txtEmail.getText().toString();
            area = txtArea.getText().toString();
            pincode = txtPinCode.getText().toString();
            noOfYear = txtNoOfyear.getText().toString();
            trustName = txtTrustName.getText().toString();
            estRegNo = txtEstRegNo.getText().toString();

            int selectedId = rgUserRole.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            userRole = radioButton.getText().toString() ;
            if(userRole.equals("Normal User")){
                userRole = "Normaluser";
            }
            else if(userRole.equals("Guru Swamy")){
                userRole = "Guruswamy";
                mandalName =  txtMandalName.getText().toString();
            }

            if (txtFullName.getText().toString().isEmpty() || txtFullName.equals("")) {
                Toast.makeText(Register.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
            } else if ((txtMobileNo.length() < 10 || txtMobileNo.length() > 12)) {
                Toast.makeText(Register.this, "Mobile No is not valid", Toast.LENGTH_LONG).show();
            } else if (txtPassword.getText().toString().isEmpty() || txtPassword.equals("")) {
                Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            } else if (txtAddress.getText().toString().isEmpty() || txtAddress.equals("")) {
                Toast.makeText(Register.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
            } else if (countryName==null || countryName.equals("")) {
                Toast.makeText(Register.this, "Please Enter Country", Toast.LENGTH_LONG).show();
            }else if (stateName == null || stateName.equals("")) {
                Toast.makeText(Register.this, "Please Enter State", Toast.LENGTH_LONG).show();
            } else if (txtArea.getText().toString().isEmpty() || txtArea.equals("")) {
                Toast.makeText(Register.this, "Please Enter Area", Toast.LENGTH_LONG).show();
            } else if (cityName == null || cityName.equals("")) {
                Toast.makeText(Register.this, "Please Enter City", Toast.LENGTH_LONG).show();
            }  else if (txtNoOfyear.getText().toString().isEmpty() || txtNoOfyear.equals("")) {
                Toast.makeText(Register.this, "Please Enter NO Of Years", Toast.LENGTH_LONG).show();
            } else {
                if (userRole.equals("Normal User")) {
                    userRole = "Normaluser";
                    if (mandalName == null || mandalName.isEmpty()) {
                        Toast.makeText(Register.this, "Please select Mandal.", Toast.LENGTH_LONG).show();
                    } else if (guruSwamiName == null || guruSwamiName.isEmpty()) {
                        Toast.makeText(Register.this, "Please select GuruSwami.", Toast.LENGTH_LONG).show();
                    }

                } else if(userRole.equals("Guru Swamy")) {
                    userRole = "Guruswamy";
                    if (txtTrustName.getText().toString().isEmpty() || txtTrustName.equals("")) {
                        Toast.makeText(Register.this, "Please Enter Trust Name", Toast.LENGTH_LONG).show();
                    } else if (txtMandalName.getText().toString().isEmpty() || txtMandalName.equals("")) {
                        Toast.makeText(Register.this, "Please Enter Mandal Name", Toast.LENGTH_LONG).show();
                    } else if (txtEstRegNo.getText().toString().isEmpty() || txtEstRegNo.equals("")) {
                        Toast.makeText(Register.this, "Please Enter Est Register No.", Toast.LENGTH_LONG).show();
                    }
                }

                progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();

                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            int year = Integer.parseInt(noOfYear);
            displayText = WebService.CreteUser(fullName, mobileNo, Password, email, address, countryId,stateId, area, cityId, pincode, year, firstImagePath, userRole, mandalName, guruSwamiName, trustName, estRegNo, webMethodName);
            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
            builder.setTitle("Result");
            builder.setMessage(displayText);
            android.app.AlertDialog alert1 = builder.create();
            alert1.show();
            if (displayText.equals("New User Successfully Created")) {
                Toast.makeText(Register.this, "Registration Successful, Please Login In.", Toast.LENGTH_LONG).show();
                Intent first = new Intent(Register.this, Login.class);
                startActivity(first);
            }
        }
    }

    private void createImageFormSelectImageDialogChooser() {
        dialogAdapter = new ArrayAdapter<String>(Register.this, android.R.layout.select_dialog_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.alertDialogListNames));
                return view;
            }
        };
        dialogAdapter.add("Take from Camera");
        dialogAdapter.add("Select from Gallery");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Register.this, R.style.AlertDialogCustom));
        builder.setTitle("Select Image");
        builder.setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    alertDialog.dismiss();
                    if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestCameraPermission();
                    } else {
                        new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                    }
                } else if (which == 1) {
                    alertDialog.dismiss();
                    new SelectGalleryImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }


    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == CAMERA_REQUEST) {
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), bmOptions);

                    doCropping(originalFile, PIC_CAMERA_CROP);
                } else if (requestCode == GALLERY_REQUEST) {
                    Uri uri = intent.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    currentImagePath = cursor.getString(columnIndex);
                    cursor.close();
                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile2.getAbsolutePath(), bmOptions);

                    doCropping(originalFile2, PIC_GALLERY_CROP);
                } else if (requestCode == PIC_CAMERA_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    String filename = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String);
                } else if (requestCode == PIC_GALLERY_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
                    //String filename=currentImagePath.substring(currentImagePath.lastIndexOf("/")+1);
                    imageBase64String2 = createBase64StringFromImageFile(originalFile2);
                    //this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    this.imageToShow = saveGalleryCropBitmap(imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String2);

                    //setBitmapToImage(this.imageToShow);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Register.this, "Did not taken any image!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(e.getMessage(), "Error");
            Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String createBase64StringFromImageFile(File originalFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setBitmapToImage(Bitmap imageToShow, String imageBase64String) {
        imageViewLinearLayout.setVisibility(VISIBLE);
        if (Objects.equals(firstImagePath, "")) {
            firstImage.setImageBitmap(imageToShow);
            //firstImagePath = cropFile.getAbsolutePath();
            //firstImagePath = originalFile.getAbsolutePath();
            //firstImagePath = "data:image/png;base64,"+imageBase64String;
            firstImagePath = imageBase64String;
            firstImageName = splitImageName(currentImagePath);
        }

    }

    private String splitImageName(String currentImagePath) {
        String imageName = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
        return imageName;
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void doCropping(File image, int request_code) {

        Intent cropIntent = new Intent(this, CropImage.class);

        cropIntent.putExtra("image-path", currentImagePath);
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);

        try {
            startActivityForResult(cropIntent, request_code);
        } catch (Exception e) {
            Toast.makeText(Register.this, "Crop Error", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap saveCameraCropBitmap(String filename, Bitmap imageToShow) {
        FileOutputStream outStream = null;

        cropFile = new File(currentImagePath);
        if (cropFile.exists()) {
            cropFile.delete();
            cropFile = new File(storageDir + ".png");
        }
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private Bitmap saveGalleryCropBitmap(Bitmap imageToShow) {
        FileOutputStream outStream = null;

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        cropFile = new File(storageDir + ".png");
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = new File(storageDir + ".png");
        try {
            currentImagePath = image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestWriteStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }

    public class SelectCameraImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
            return null;
        }
    }

    public class SelectGalleryImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            } else {
                Toast.makeText(Register.this, "CAMERA permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                Toast.makeText(Register.this, "Write storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createImageFormSelectImageDialogChooser();
            } else {
                Toast.makeText(Register.this, "Read storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

//
//    private class AsyncCallWS extends AsyncTask<String, Void, Void>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected void onPostExecute(Void aVoid)
//        {
//            if(asycTaskCounter=="1"){
//                try {
//                    JSONArray jArr = new JSONArray(displayText);
//                    String[] userRoleList = new String[]{
//                            "Select Role"
//                    };
//                    ArrayList<String> stringRoleArrayList = new ArrayList<String>();
//                    stringRoleArrayList = new ArrayList<>(Arrays.asList(userRoleList));
//
//                    for (int count = 0; count < jArr.length(); count++) {
//                        JSONObject obj = jArr.getJSONObject(count);
//                        String spMandal = obj.getString("RoleName");
//                        stringRoleArrayList.add(obj.getString("RoleName"));
//                    }
//
//                    Spinner spMandal = (Spinner) findViewById(R.id.spMandal);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this, R.layout.spiner_item, stringRoleArrayList);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spMandal.setAdapter(adapter);
//                } catch (JSONException ex) {
//                    ex.printStackTrace();
//                }
//            }else{
//                res.setText(displayText);
//                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
//                builder.setTitle("Result");
//                builder.setMessage(displayText);
//                android.app.AlertDialog alert1 = builder.create();
//                alert1.show();
//                if(displayText.equals("New User Successfully Created")){
//                    Intent first = new Intent(Register.this,Login.class);
//                    startActivity(first);
//                }
//            }
//            super.onPostExecute(aVoid);
//        }
//        @Override
//        protected Void doInBackground(String... params) {
//            if(asycTaskCounter.equals("1")){
//                //displayText = WebService.FetchRole("ShowRole");
//
//            }else{
//                //displayText = WebService.CreteUser(DelBoy_Fname, DBoyMName, DelBoy_Lname, DelBoy_Contact, Password, DelBoy_Add, DelBoy_Email, firstImagePath, secondImagePath,userRole, "CreateUser");
//                progressDialog.dismiss();
//            }
//            return null;
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Register.this.finish();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}


